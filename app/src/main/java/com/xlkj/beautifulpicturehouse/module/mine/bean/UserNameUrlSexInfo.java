package com.xlkj.beautifulpicturehouse.module.mine.bean;

import java.io.Serializable;

/**
 * Created by 1 on 2017/9/30.
 * 用户头像昵称性别
 * 包括微信登录 QQ登录等的用户共同的信息
 */
public class UserNameUrlSexInfo implements Serializable {
    private String nickName;
    private String headUrl;
    private String sex;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
