package mous.component.im.netty.event;


import mous.component.im.entry.IMMessage;
import mous.component.im.netty.MsgTimeoutTimerManager;

/**
 * <p>@ProjectName:     NettyChat</p>
 * <p>@ClassName:       IMSClientInterface.java</p>
 * <p>@PackageName:     com.freddy.im.interf</p>
 * <b>
 * <p>@Description:     ims抽象接口，需要切换到其它方式实现im功能，实现此接口即可</p>
 * </b>
 * <p>@author:          FreddyChen</p>
 * <p>@date:            2019/03/31 20:04</p>
 * <p>@email:           chenshichao@outlook.com</p>
 */
public interface IMSClientInterface {

    /**
     * 初始化
     *
     * @param listener ims消息回调
     * @param callback ims连接状态回调
     */
    void init(IMMessageListener listener, IMSConnectStatusCallback callback);

    /**
     * 重置连接，也就是重连
     * 首次连接也可认为是重连
     */
    void resetConnect();

    /**
     * 重置连接，也就是重连
     * 首次连接也可认为是重连
     * 重载
     *
     * @param isFirst 是否首次连接
     */
    void resetConnect(boolean isFirst);

    /**
     * 关闭连接，同时释放资源
     */
    void close();

    /**
     * 标识ims是否已关闭
     *
     * @return
     */
    boolean isClosed();

    /**
     * 发送消息
     */
    void sendMsg(IMMessage msg);

    /**
     * 发送消息
     * 重载
     *
     * @param msg
     * @param isJoinTimeoutManager 是否加入发送超时管理器
     */
    void sendMsg(IMMessage msg, boolean isJoinTimeoutManager);

    /**
     * 接受handler传回来的消息
     *
     * @param msg
     */
    void receivedMsg(IMMessage msg);

    /**
     * 登录结果
     *
     * @param msg
     */
    void loginResult(IMMessage msg);

    /**
     * 获取重连间隔时长
     *
     * @return
     */
    int getReconnectInterval();


    /**
     * 获取应用层消息发送超时重发次数
     *
     * @return
     */
    int getResendCount();

    /**
     * 获取消息发送超时定时器
     *
     * @return
     */
    MsgTimeoutTimerManager getMsgTimeoutTimerManager();

    /**
     * 重新登录IM
     */
    void reLoginIM();
}
