package mous.component.im.netty.event;


import mous.component.im.entry.IMMessage;

/**
 * 类名: {@link IMMessageListener}
 * <br/> 功能描述:IM消息回调接口
 * <br/> 作者: MouShao
 * <br/> 时间: 2019/8/2
 * <br/> 最后修改者:
 * <br/> 最后修改内容:
 */
public interface IMMessageListener {
    void loginResult(IMMessage imMsg);

    void receivedMsg(IMMessage imMsg);

    void sendBeatMsg();
}
