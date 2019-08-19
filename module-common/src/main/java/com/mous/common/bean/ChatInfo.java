package com.mous.common.bean;

import android.text.TextUtils;

import java.io.Serializable;

public class ChatInfo implements Serializable {
    /**
     * 唯一性的messageID
     */
    public String messageID;

    /**
     * 存入数据库的行数
     */
    public Long rowID;
    /**
     * 头像图标
     */
    public String iconRes;
    /**
     * 简要内容
     */
    public String content = "";
    /**
     * 是否为群聊 0:单聊 1:群聊
     */
    public boolean isGroup;
    /**
     * 单聊时talkingID可能和senderID一致,群聊时候talkingID为群ID,senderID为发送消息人的ID
     */
    public String talkingID;

    /**
     * 发送人ID
     */
    public String senderID;
    /**
     * 发送人ID
     */
    public String senderName;
    /**
     * 我自己的ID,同登录ID
     */
    public String mySelfID;

    // 消息类型
    public int infoType;

    // 文件名称
    public String fileName = "";

    // 文件路径
    public String filePath = "";

    // 文件本地路径
    public String fileLocalPath = "";

    // 发送时间
    public String time;

    /**
     * 是否为我发送的消息,数据库存储0为不是,1为是
     */

    public boolean isMySend;

    //群ID
    public String groupID;

    //群名称
    public String groupName;


    public ChatInfo() {

    }

    public String getChatInfoID() {
        if (TextUtils.isEmpty(talkingID)){
            return senderID + "&" + mySelfID;
        }else {
            return talkingID + "&" + mySelfID;
        }
    }

}
