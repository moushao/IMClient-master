package mous.component.im.netty.handler;


import io.netty.channel.ChannelHandlerContext;
import mous.component.im.entry.IMMessage;
import mous.component.im.netty.NettyTcpClient;

/**
 * <p>@ProjectName:     NettyChat</p>
 * <p>@ClassName:       TCPReadHandler.java</p>
 * <p>@PackageName:     com.freddy.im.netty</p>
 * <b>
 * <p>@Description:     消息接收处理handler</p>
 * </b>
 * <p>@author:          FreddyChen</p>
 * <p>@date:            2019/04/07 21:40</p>
 * <p>@email:           chenshichao@outlook.com</p>
 */
public class TCPReadHandler extends ReadHandler {


    public TCPReadHandler(NettyTcpClient imsClient) {
        super(imsClient);
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        IMMessage message = (IMMessage) msg;
        imsClient.receivedMsg(message);
/*        switch (message.Command) {
            case IMConstants.AddGroup://创建群
            case IMConstants.QUIT_GROUP://退出群
            case IMConstants.PTP_TEXT://单聊消息
            case IMConstants.PTP_FILE://文件消息
            case IMConstants.PTP_AUDIENCE://语音消息
            case IMConstants.PTP_VIDEO://视频消息
            case IMConstants.PTP_IMAGE://图片消息
            case IMConstants.GROUP_TEXT://群聊消息
            case IMConstants.GROUP_FILE://群聊文件消息
            case IMConstants.GROUP_AUDIENCE://群聊语音消息
            case IMConstants.GROUP_VIDEO://群聊视频消息
            case IMConstants.GROUP_IMAGE://群聊图片消息
                imsClient.receivedMsg(message);
                break;
            case IMConstants.LOGIN:
                imsClient.loginResult(message);
                break;
            case IMConstants.LOGOUT:
                break;
            case IMConstants.GetOffLineMsg:
                break;
            case IMConstants.NotifyOffline:
                break;
            case IMConstants.GetFriend:
                break;
        }*/
        // 接收消息，由消息转发器转发到应用层
    }
}
