package mous.component.im.netty.event;

/**
 * 类名: {@link IMConnectListener}
 * <br/> 功能描述:IM引擎回调应用层的连接监听
 * <br/> 作者: MouShao
 * <br/> 时间: 2019/8/2
 */

public interface IMConnectListener {
    void connectStatus(int status);
}
