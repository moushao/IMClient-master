package mous.component.im.netty.handler;

import com.google.gson.Gson;

import component.baselib.utils.LogUtils;
import io.netty.buffer.UnpooledHeapByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import mous.component.im.entry.IMMessage;
import mous.component.im.netty.NettyTcpClient;

/**
 * Created by MouShao on 2019/7/29.
 */

public class ReadHandler extends ChannelInboundHandlerAdapter {
    public NettyTcpClient imsClient;

    public ReadHandler(NettyTcpClient imsClient) {
        this.imsClient = imsClient;
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LogUtils.e("IM", "channelInactive");
        closeChannelAndReConnect(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LogUtils.e("IM", "channelInactive:" + cause.getMessage());
        closeChannelAndReConnect(ctx);
    }

    public void closeChannelAndReConnect(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        if (channel != null) {
            channel.close();
            ctx.close();
        }
        // 触发重连
        imsClient.resetConnect(false);
    }

    public IMMessage getJsonIMessage(Object msg) {
        try {

            UnpooledHeapByteBuf s = (UnpooledHeapByteBuf) msg;
            byte[] byteArray = new byte[s.writerIndex()];
            s.readBytes(byteArray);
            String result = new String(byteArray);
            LogUtils.e("IM", result);
            ReferenceCountUtil.release(s);
            return new Gson().fromJson(result, IMMessage.class);
        } catch (Exception e) {
            return null;
        }
    }

}
