package mous.component.im.netty;


import mous.component.im.netty.event.IMSClientInterface;

/**
 * <p>@ProjectName:     NettyChat</p>
 * <p>@ClassName:       IMSClientFactory.java</p>
 * <p>@PackageName:     com.freddy.im</p>
 * <p>@Description:     ims实例工厂方法</p>
 * <p>@author:          FreddyChen</p>
 * <p>@date:            2019/03/31 20:54</p>
 * <p>@email:           chenshichao@outlook.com</p>
 */
public class IMSClientFactory {

    public static IMSClientInterface getIMSClient() {
        return NettyTcpClient.getInstance();
    }
}
