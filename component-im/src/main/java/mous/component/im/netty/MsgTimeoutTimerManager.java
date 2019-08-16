package mous.component.im.netty;

import android.text.TextUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import mous.component.im.entry.IMMessage;
import mous.component.im.netty.event.IMSClientInterface;

/**
 * <p>@ProjectName:     NettyChat</p>
 * <p>@ClassName:       MsgTimeoutTimerManager.java</p>
 * <p>@PackageName:     com.freddy.im</p>
 * <b>
 * <p>@Description:     消息发送超时管理器，用于管理消息定时器的新增、移除等</p>
 * </b>
 * <p>@author:          FreddyChen</p>
 * <p>@date:            2019/04/09 22:42</p>
 * <p>@email:           chenshichao@outlook.com</p>
 */
public class MsgTimeoutTimerManager {

    private Map<String, MsgTimeoutTimer> mMsgTimeoutMap = new ConcurrentHashMap<>();
    private IMSClientInterface imsClient;// ims客户端

    public MsgTimeoutTimerManager(IMSClientInterface imsClient) {
        this.imsClient = imsClient;
    }

    /**
     * 添加消息到发送超时管理器
     *
     * @param msg
     */
    public void add(IMMessage msg) {
        if (msg == null || TextUtils.isEmpty(msg.entity)) {
            return;
        }
        String msgId = msg.messageID;
        if (!mMsgTimeoutMap.containsKey(msgId)) {
            MsgTimeoutTimer timer = new MsgTimeoutTimer(imsClient, msg);
            mMsgTimeoutMap.put(msgId, timer);
        }

        System.out.println("添加消息超发送超时管理器，message=" + msg + "\t当前管理器消息数：" + mMsgTimeoutMap.size());
    }

    /**
     * 从发送超时管理器中移除消息，并停止定时器
     *
     * @param msgId
     */
    public void remove(String msgId) {
        if (TextUtils.isEmpty(msgId)) {
            return;
        }

        MsgTimeoutTimer timer = mMsgTimeoutMap.remove(msgId);
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /**
     * 重连成功回调，重连并握手成功时，重发消息发送超时管理器中所有的消息
     */
    public synchronized void onResetConnected() {
        for (Iterator<Map.Entry<String, MsgTimeoutTimer>> it = mMsgTimeoutMap.entrySet().iterator(); it.hasNext(); ) {
            it.next().getValue().sendMsg();
        }
    }
}
