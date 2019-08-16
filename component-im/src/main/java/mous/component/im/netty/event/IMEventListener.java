package mous.component.im.netty.event;


import mous.component.im.entry.IMMessage;

/**
 * Created by MouShao on 2019/4/30.
 */

public interface IMEventListener {
    void receiveMessage(IMMessage message);
}
