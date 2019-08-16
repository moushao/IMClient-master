package mous.component.im.netty.event;

/**
 * 类名: {@link IMSConnectStatusCallback}
 * <br/> 功能描述:IMS连接状态回调
 * <br/> 作者: MouShao
 * <br/> 时间: 2019/8/2
 * <br/> 最后修改者:
 * <br/> 最后修改内容:
 */
public interface IMSConnectStatusCallback {

    /**
     * ims连接中
     */
    void onConnecting();

    /**
     * ims连接成功
     */
    void onConnected();

    /**
     * ims连接失败
     */
    void onConnectFailed();
}
