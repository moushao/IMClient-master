package com.mous.common.message;


import java.io.Serializable;

public class LastChatMessage implements Serializable {
    /**
     * 消息ID
     */
    public String messageID;
    /**
     * 头像图标
     */
    public String iconRes;
    /**
     * title名称
     */
    public String title = "";
    /**
     * 简要内容
     */
    public String lastContent;
    public String time;
    /**
     * 是否为群聊 0:单聊 1:群聊
     */
    public boolean isGroup;
    /**
     * 未读消息数目
     */
    public int UnReadCount = 0;
    /**
     * 单聊时talkingID可能和senderID一致,群聊时候talkingID为群ID,senderID为发送消息人的ID
     */
    public String talkingID;
    /**
     * 发送人ID
     */
    public String senderID;

    /**
     * 我自己的ID,同登录ID
     */
    public String mySelfID;

    /**
     * 群id
     */
    public String groupId;

    /**
     * 群名称
     */
    public String groupName;

    /**
     * 聊天对象的状态 0:在线 1:离线 2:忙碌
     */
    public int talkingStatus;

}
