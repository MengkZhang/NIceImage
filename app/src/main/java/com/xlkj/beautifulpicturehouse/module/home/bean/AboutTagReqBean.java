package com.xlkj.beautifulpicturehouse.module.home.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/9.
 * 详情页的相关标签的请求bean
 */

public class AboutTagReqBean implements Serializable {
    private String action;
    private String typeId;
    private String userId;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
