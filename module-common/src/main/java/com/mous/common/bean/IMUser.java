package com.mous.common.bean;

/**
 * 类名: {@link IMUser}
 * <br/> 功能描述:IM通信用户实体类
 * <br/> 作者: MouShao
 * <br/> 时间: 2019/8/19
 * <br/> 最后修改者:
 * <br/> 最后修改内容:
 */
public class IMUser {
    private String userID;
    private String password;
    private String nickName;
    private String headImg;
    private String sex;
    private String autograph;

    public IMUser(String userID, String nickName) {
        this.userID = userID;
        this.nickName = nickName;
    }

    public String getUserID() {
        return userID == null ? "" : userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password == null ? "" : password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName == null ? "" : nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImg() {
        return headImg == null ? "" : headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getSex() {
        return sex == null ? "" : sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAutograph() {
        return autograph == null ? "" : autograph;
    }

    public void setAutograph(String autograph) {
        this.autograph = autograph;
    }
}
