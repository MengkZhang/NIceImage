package com.xlkj.beautifulpicturehouse.module.mine.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/5.
 *  全部的用户信息 包括官方返回的头像 昵称 性别 和APP服务器返回的UID以及VIP信息
 */

public class AllUserInfo implements Serializable {
    private String nickName;
    private String headUrl;
    private String uId;
    private int isVip;
    private int vipDayCount;
    private int vipLevel;

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

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public int getIsVip() {
        return isVip;
    }

    public void setIsVip(int isVip) {
        this.isVip = isVip;
    }

    public int getVipDayCount() {
        return vipDayCount;
    }

    public void setVipDayCount(int vipDayCount) {
        this.vipDayCount = vipDayCount;
    }

    public int getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(int vipLevel) {
        this.vipLevel = vipLevel;
    }
}
