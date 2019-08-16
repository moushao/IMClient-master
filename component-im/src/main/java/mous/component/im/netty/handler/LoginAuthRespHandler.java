package mous.component.im.netty.handler;

import component.baselib.utils.LogUtils;
import io.netty.channel.ChannelHandlerContext;
import mous.component.im.common.IMCommands;
import mous.component.im.entry.IMMessage;
import mous.component.im.netty.NettyTcpClient;

/**
 * 类名: {@link LoginAuthRespHandler}
 * <br/> 功能描述:登录认证消息响应处理handler
 * <br/> 作者: MouShao
 * <br/> 时间: 2019/8/2
 * <br/> 最后修改者:
 * <br/> 最后修改内容:
 */
public class LoginAuthRespHandler extends ReadHandler {


    public LoginAuthRespHandler(NettyTcpClient imsClient) {
        super(imsClient);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        final IMMessage imMessage = super.getJsonIMessage(msg);
        if (imMessage == null)
            return;
        if (imMessage.command == IMCommands.LOG_IN) {
            LogUtils.e("IM", "登录成功");
            //                CThreadPoolExecutor.runOnMainThread(new Runnable() {
            //                    @Override
            //                    public void run() {
            //                        ToastUtils.showToast(MyApplication.getApplication().getApplicationContext(), imMessage
            //                                .ReceiveID);
            //                    }
            //                });
            // 握手成功，马上先发送一条心跳消息，至于心跳机制管理，交由HeartbeatHandler
            //sendBeatMsg();
            // 添加心跳消息管理handler
            // imsClient.addHeartbeatHandler();
        } else if (imMessage.command == IMCommands.LOG_OUT) {
            //登录退出
            imsClient.reLoginIM();
        } else {
            // 消息透传
            LogUtils.e("IM", "消息透传给心跳");
            ctx.fireChannelRead(imMessage);
        }
    }
}
