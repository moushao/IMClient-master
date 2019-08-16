package mous.component.im.netty;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import component.baselib.common.MyApp;
import component.baselib.utils.LogUtils;
import component.data.common.Constants;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GenericFutureListener;
import mous.component.im.entry.IMMessage;
import mous.component.im.netty.event.IMMessageListener;
import mous.component.im.netty.event.IMSClientInterface;
import mous.component.im.netty.event.IMSConnectStatusCallback;
import mous.component.im.netty.handler.HeartbeatHandler;
import mous.component.im.netty.handler.HeartbeatRespHandler;
import mous.component.im.netty.handler.LoginAuthRespHandler;
import mous.component.im.netty.handler.TCPChannelInitializerHandler;
import mous.component.im.netty.handler.TCPReadHandler;

/**
 * Created by dell on 2019/7/27.
 */

public class NettyTcpClient implements IMSClientInterface {
    private static volatile NettyTcpClient instance;

    private Bootstrap bootstrap;
    private Channel channel;

    private boolean isClosed = false;//标识ims是否已关闭
    private IMSConnectStatusCallback mIMSConnectStatusCallback;//ims连接状态回调监听器
    private IMMessageListener messageListener;//ims消息回调
    private ExecutorServiceFactory loopGroup;//线程池工厂

    private volatile boolean isReconnecting = false;// 是否正在进行重连
    private int connectStatus = IMSConfig.CONNECT_STATE_FAILURE;// ims连接状态，初始化为连接失败
    // 重连间隔时长
    private int reconnectInterval = IMSConfig.DEFAULT_RECONNECT_BASE_DELAY_TIME;
    //心跳间隔时间
    private int heartbeatInterval = IMSConfig.DEFAULT_HEARTBEAT_INTERVAL_FOREGROUND;
    //应用在后台时心跳间隔时间
    private int foregroundHeartbeatInterval = IMSConfig.DEFAULT_HEARTBEAT_INTERVAL_FOREGROUND;
    //应用在前台时心跳间隔时间
    private int backgroundHeartbeatInterval = IMSConfig.DEFAULT_HEARTBEAT_INTERVAL_BACKGROUND;
    //app前后台状态
    private int appStatus = IMSConfig.APP_STATUS_FOREGROUND;
    //消息发送超时重发次数
    private int resendCount = IMSConfig.DEFAULT_RESEND_COUNT;
    //消息发送失败重发间隔时长
    private MsgTimeoutTimerManager msgTimeoutTimerManager;//消息发送超时定时器管理

    private NettyTcpClient() {
    }

    public static NettyTcpClient getInstance() {
        if (null == instance) {
            synchronized (NettyTcpClient.class) {
                if (null == instance) {
                    instance = new NettyTcpClient();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化
     *
     * @param callback ims连接状态回调
     */
    @Override
    public void init(IMMessageListener listener, IMSConnectStatusCallback callback) {
        close();
        isClosed = false;
        this.messageListener = listener;
        this.mIMSConnectStatusCallback = callback;
        loopGroup = new ExecutorServiceFactory();
        loopGroup.initBossLoopGroup();//初始化重连线程组
        msgTimeoutTimerManager = new MsgTimeoutTimerManager(this);
        resetConnect(true);//进行第一次连接
    }

    /**
     * 初始化bootstrap
     */
    private void initBootstrap() {
        EventLoopGroup loopGroup = new NioEventLoopGroup(4);
        bootstrap = new Bootstrap();
        bootstrap.group(loopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)//设置禁用nagle算法
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, IMSConfig.DEFAULT_CONNECT_TIMEOUT)//设置连接超时时长
                .handler(new TCPChannelInitializerHandler(this))//设置初始化Channel
                .option(ChannelOption.SO_KEEPALIVE, true);//设置该选项以后，如果在两小时内没有数据的通信时，TCP会自动发送一个活动探测数据报文
    }

    @Override
    public void sendMsg(IMMessage msg) {
        this.sendMsg(msg, true);
    }

    @Override//接收到服务端业务消息,回调
    public void receivedMsg(IMMessage msg) {
        if (messageListener != null) {
            messageListener.receivedMsg(msg);
        }
    }

    @Override//登录结果消息回调
    public void loginResult(IMMessage msg) {

    }

    /**
     * 关闭channel
     */
    private void closeChannel() {
        try {
            if (channel != null) {
                channel.pipeline().remove(TCPReadHandler.class.getSimpleName());
                channel.pipeline().remove(LoginAuthRespHandler.class.getSimpleName());
                channel.pipeline().remove(HeartbeatRespHandler.class.getSimpleName());
                channel.pipeline().remove(HeartbeatHandler.class.getSimpleName());
                channel.close();
                channel.eventLoop().shutdownGracefully();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            channel = null;
        }
    }

    /**
     * 发送消息
     * 重载
     *
     * @param msg
     * @param isJoinTimeoutManager 是否加入发送超时管理器
     */
    @Override
    public void sendMsg(final IMMessage msg, boolean isJoinTimeoutManager) {
        if (connectStatus != IMSConfig.CONNECT_STATE_SUCCESSFUL && isJoinTimeoutManager) {
            //msgTimeoutTimerManager.add(msg);
        }
        if (channel == null) {
            LogUtils.e("IM", "发送消息失败:Channel为空");
            resetConnect();
            return;
        }

        try {
            final String data = new Gson().toJson(msg);
            channel.writeAndFlush(Unpooled.copiedBuffer(data, CharsetUtil.UTF_8))
                    .addListener(new GenericFutureListener<ChannelFuture>() {
                        @Override
                        public void operationComplete(ChannelFuture future) throws Exception {
                            if (future.isSuccess()) {
                                // msgTimeoutTimerManager.remove(msg.MsgBody.MessageID);
                                LogUtils.e("IM", data);
                            }
                        }
                    });
        } catch (Exception ex) {
        }
    }

    @Override
    public void resetConnect() {
        this.resetConnect(false);
    }

    @Override
    public void resetConnect(boolean isFirst) {
        if (!isFirst) {
            try {
                Thread.sleep(IMSConfig.DEFAULT_RECONNECT_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //只有第一个调用者才能赋值并调用重连
        if (!isClosed && !isReconnecting) {
            synchronized (this) {
                if (!isClosed && !isReconnecting) {
                    isReconnecting = true;//标识正在进行重连
                    onConnectStatusCallback(IMSConfig.CONNECT_STATE_CONNECTING);//回调ims连接状态
                    closeChannel();//先关闭channel
                    loopGroup.execBossTask(new ResetConnectRunnable(isFirst));//执行重连任务
                }
            }
        }
    }


    /**
     * 获取应用层消息发送超时重发次数
     */
    @Override
    public int getResendCount() {
        return resendCount;
    }


    /**
     * 获取消息发送超时定时器
     *
     * @return
     */
    @Override
    public MsgTimeoutTimerManager getMsgTimeoutTimerManager() {
        return msgTimeoutTimerManager;
    }

    @Override
    public void reLoginIM() {
        if (connectStatus == IMSConfig.CONNECT_STATE_SUCCESSFUL) {
            mIMSConnectStatusCallback.onConnected();
        }
    }

    /**
     * 标识ims是否已关闭
     */
    @Override
    public boolean isClosed() {
        return isClosed;
    }

    /**
     * 获取重连间隔时长
     */

    @Override
    public int getReconnectInterval() {
        return reconnectInterval;
    }

    /**
     * 获取线程池
     *
     * @return
     */
    public ExecutorServiceFactory getLoopGroup() {
        return loopGroup;
    }

    /**
     * 设置app前后台状态
     *
     * @param appStatus
     */
    //@Override
    public void setAppStatus(int appStatus) {
        this.appStatus = appStatus;
        if (this.appStatus == IMSConfig.APP_STATUS_FOREGROUND) {
            heartbeatInterval = foregroundHeartbeatInterval;
        } else if (this.appStatus == IMSConfig.APP_STATUS_BACKGROUND) {
            heartbeatInterval = backgroundHeartbeatInterval;
        }
        addHeartbeatHandler();
    }


    /**
     * 添加心跳消息管理handler
     */
    public void addHeartbeatHandler() {
        if (channel == null || !channel.isActive() || channel.pipeline() == null) {
            return;
        }

        try {
            //之前存在的读写超时handler，先移除掉，再重新添加
            if (channel.pipeline().get(IdleStateHandler.class.getSimpleName()) != null) {
                channel.pipeline().remove(IdleStateHandler.class.getSimpleName());
            }
            //3次心跳没响应，代表连接已断开
            channel.pipeline().addFirst(IdleStateHandler.class.getSimpleName(), new IdleStateHandler(
                    heartbeatInterval * 3, heartbeatInterval, 0, TimeUnit.MILLISECONDS));

            //重新添加HeartbeatHandler
            if (channel.pipeline().get(HeartbeatHandler.class.getSimpleName()) != null) {
                channel.pipeline().remove(HeartbeatHandler.class.getSimpleName());
            }
            if (channel.pipeline().get(TCPReadHandler.class.getSimpleName()) != null) {
                channel.pipeline().addBefore(TCPReadHandler.class.getSimpleName(), HeartbeatHandler.class
                                .getSimpleName(),
                        new HeartbeatHandler(this));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("添加心跳消息管理handler失败，reason：" + e.getMessage());
        }
    }

    /**
     * 重连任务
     */
    private class ResetConnectRunnable implements Runnable {

        private boolean isFirst;

        public ResetConnectRunnable(boolean isFirst) {
            this.isFirst = isFirst;
        }

        @Override
        public void run() {
            //非首次进行重连，执行到这里即代表已经连接失败，回调连接状态到应用层
            if (!isFirst) {
                onConnectStatusCallback(IMSConfig.CONNECT_STATE_FAILURE);
            }

            try {
                //重连时，释放工作线程组，也就是停止心跳
                loopGroup.destroyWorkLoopGroup();

                while (!isClosed) {
                    if (!isNetworkAvailable()) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        continue;
                    }

                    //网络可用才进行连接
                    int status;
                    if ((status = reConnect()) == IMSConfig.CONNECT_STATE_SUCCESSFUL) {
                        onConnectStatusCallback(status);
                        //连接成功，跳出循环
                        break;
                    }

                    if (status == IMSConfig.CONNECT_STATE_FAILURE) {
                        onConnectStatusCallback(status);
                        try {
                            Thread.sleep(IMSConfig.DEFAULT_RECONNECT_INTERVAL);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } finally {
                //标识重连任务停止
                isReconnecting = false;
            }
        }
    }


    /**
     * 重连，首次连接也认为是第一次重连
     *
     * @return
     */
    private int reConnect() {
        //未关闭才去连接
        if (!isClosed) {
            try {
                //先释放EventLoop线程组
                if (bootstrap != null) {
                    bootstrap.group().shutdownGracefully();
                }
            } finally {
                bootstrap = null;
            }

            //初始化bootstrap
            initBootstrap();
            return connectServer();
        }
        return IMSConfig.CONNECT_STATE_FAILURE;
    }

    /**
     * 连接服务器
     *
     * @return
     */
    private int connectServer() {
        //TODO
        //如果服务器地址无效，直接回调连接状态，不再进行连接

        for (int j = 1; j <= IMSConfig.DEFAULT_RECONNECT_COUNT; j++) {
            //如果ims已关闭，或网络不可用，直接回调连接状态，不再进行连接
            if (isClosed || !isNetworkAvailable()) {
                return IMSConfig.CONNECT_STATE_FAILURE;
            }
            LogUtils.e("IM", "第" + j + "次重连");
            //回调连接状态
            if (connectStatus != IMSConfig.CONNECT_STATE_CONNECTING) {
                onConnectStatusCallback(IMSConfig.CONNECT_STATE_CONNECTING);
            }

            try {
                toServer();//连接服务器
                if (channel != null) {//channel不为空，即认为连接已成功
                    return IMSConfig.CONNECT_STATE_SUCCESSFUL;
                } else {
                    //连接失败，则线程休眠n * 重连间隔时长
                    Thread.sleep(j * getReconnectInterval());
                }
            } catch (InterruptedException e) {
                close();
                break;// 线程被中断，则强制关闭
            }
        }

        //执行到这里，代表连接失败
        return IMSConfig.CONNECT_STATE_FAILURE;
    }

    /**
     * 真正连接服务器的地方
     */
    private void toServer() {
        String currentHost = Constants.IM_HOST;
        int currentPort = Constants.IM_PORT;
        try {
            channel = bootstrap.connect(currentHost, currentPort).sync().channel();
            if (channel != null)
                LogUtils.e("IM", "Channel连接成功");
            else
                LogUtils.e("IM", "Channel连接失败");
        } catch (Exception e) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
                LogUtils.e("IM", String.format("睡了500秒" + e1.getMessage()));
            }
            LogUtils.e("IM", String.format("Channel连接失败" + e.getMessage()));
            channel = null;
        }
    }

    /**
     * 回调ims连接状态
     *
     * @param connectStatus
     */
    private void onConnectStatusCallback(int connectStatus) {
        this.connectStatus = connectStatus;
        switch (connectStatus) {
            case IMSConfig.CONNECT_STATE_CONNECTING: {
                System.out.println("ims连接中...");
                if (mIMSConnectStatusCallback != null) {
                    mIMSConnectStatusCallback.onConnecting();
                }
                break;
            }

            case IMSConfig.CONNECT_STATE_SUCCESSFUL: {
                if (mIMSConnectStatusCallback != null) {
                    mIMSConnectStatusCallback.onConnected();
                }
                break;
            }

            case IMSConfig.CONNECT_STATE_FAILURE:
            default: {
                System.out.println("ims连接失败");
                if (mIMSConnectStatusCallback != null) {
                    mIMSConnectStatusCallback.onConnectFailed();
                }
                break;
            }
        }
    }

    /**
     * 关闭连接，同时释放资源
     */
    @Override
    public void close() {
        if (isClosed) {
            return;
        }
        isClosed = true;
        //关闭channel
        closeChannel();
        //关闭bootstrap
        try {
            if (bootstrap != null) {
                bootstrap.group().shutdownGracefully();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            //释放线程池
            if (loopGroup != null) {
                loopGroup.destroy();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            isReconnecting = false;
            bootstrap = null;
        }
    }


    /**
     * 从应用层获取网络是否可用
     */
    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) MyApp.getApplication().getSystemService(Context
                .CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }
}

