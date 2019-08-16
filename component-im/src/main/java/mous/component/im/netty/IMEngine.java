package mous.component.im.netty;

import component.baselib.utils.CThreadPoolExecutor;
import mous.component.im.entry.IMMessage;
import mous.component.im.netty.event.IMConnectListener;
import mous.component.im.netty.event.IMMessageListener;
import mous.component.im.netty.event.IMSClientInterface;
import mous.component.im.netty.event.IMSConnectStatusCallback;

/**
 * 类名: {@link IMEngine}
 * <br/> 功能描述:应用层的imsClient启动器
 * <br/> 作者: MouShao
 * <br/> 时间: 2019/8/5
 * <br/> 最后修改者:
 * <br/> 最后修改内容:
 */
public class IMEngine {

    private static volatile IMEngine INSTANCE;

    private IMSClientInterface imsClient;

    private IMConnectListener connectListener;

    private IMEngine() {
    }


    public static IMEngine getInstance() {
        if (null == INSTANCE) {
            synchronized (IMEngine.class) {
                if (null == INSTANCE) {
                    INSTANCE = new IMEngine();
                }
            }
        }
        return INSTANCE;
    }

    public synchronized void init() {
        imsClient = IMSClientFactory.getIMSClient();
        imsClient.init(messageListener, connectStatusCallback);
    }

    public void receiveMsg(final IMMessage message) {
        CThreadPoolExecutor.runInBackground(new Runnable() {
            @Override
            public void run() {
            }
        });
    }


    /**
     * 重载发送消息
     */
    public void sendMessage(final IMMessage msg) {
        this.sendMessage(msg, true);
    }

    /**
     * 发送消息
     */
    public void sendMessage(final IMMessage msg, final boolean isJoinTimeoutManager) {
        CThreadPoolExecutor.runInBackground(new Runnable() {
            @Override
            public void run() {
                imsClient.sendMsg(msg, isJoinTimeoutManager);
            }
        });
    }


    private IMMessageListener messageListener = new IMMessageListener() {
        @Override
        public void loginResult(IMMessage imMsg) {

        }

        @Override
        public void receivedMsg(IMMessage imMsg) {
            receiveMsg(imMsg);
        }

        @Override
        public void sendBeatMsg() {
            // IMSendManager.getInstance().sendHeartBeatMessage();
        }
    };


    private IMSConnectStatusCallback connectStatusCallback = new IMSConnectStatusCallback() {
        @Override
        public void onConnecting() {
            if (connectListener != null)
                connectListener.connectStatus(IMSConfig.CONNECT_STATE_CONNECTING);
        }

        @Override
        public void onConnected() {
            //只有当前登陆用户不为空,并且账户不为空才重新登录,切换账户会重连导致空指针
            if (connectListener != null)
                connectListener.connectStatus(IMSConfig.CONNECT_STATE_SUCCESSFUL);
        }

        @Override
        public void onConnectFailed() {
            if (connectListener != null)
                connectListener.connectStatus(IMSConfig.CONNECT_STATE_FAILURE);
        }
    };

    public void close() {
        connectListener = null;
        if (imsClient != null)
            imsClient.close();
    }

    public void setConnectListener(IMConnectListener connectListener) {
        this.connectListener = connectListener;
    }
}
