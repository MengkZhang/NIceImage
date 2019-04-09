package com.xlkj.beautifulpicturehouse.module.mine.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/5.
 * 登录APP服务器成功后封装的VIP信息
 */

public class LoginVIPInfo implements Serializable {
    private int isVip;
    private int vipDayCount;
    private int vipLevel;

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
