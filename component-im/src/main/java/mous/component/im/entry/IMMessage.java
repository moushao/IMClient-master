package mous.component.im.entry;

import mous.component.im.common.IMCommands;

/**
 * Created by MouShao on 2019/4/30.
 */

public class IMMessage {
    /**
     * 消息类型,所有值来自于{@link IMCommands}
     */
    public int command;
    /**
     * 标识符,好像没什么卵用
     */
    public boolean flag;
    /**
     * 其他消息
     */
    public String msg = "";
    /**
     * 实体消息内容,json字符串
     */
    public String entity;
    /**
     * 接受人
     */
    public String receiveID;
    /**
     * 发送人
     */
    public String sendID;
    /**
     * 消息编号
     */
    public String messageID;
}
