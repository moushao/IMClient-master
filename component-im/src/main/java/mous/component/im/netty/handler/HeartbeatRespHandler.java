package mous.component.im.netty.handler;


import component.baselib.utils.LogUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import mous.component.im.entry.IMMessage;
import mous.component.im.netty.NettyTcpClient;

public class HeartbeatRespHandler extends ChannelInboundHandlerAdapter {

    private NettyTcpClient imsClient;

    public HeartbeatRespHandler(NettyTcpClient imsClient) {
        this.imsClient = imsClient;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        IMMessage imMessage = (IMMessage) msg;
        if (imMessage.command == -1) {
            LogUtils.e("IM", "收到服务端心跳响应");
        } else {
            // 消息透传
            LogUtils.e("IM", "透传给消息处理器");
            ctx.fireChannelRead(imMessage);
        }
    }
}
