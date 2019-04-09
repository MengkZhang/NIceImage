package com.xlkj.beautifulpicturehouse.module.home.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/5.
 * 收藏请求参数bean
 */

public class CollectionReqBean implements Serializable {
     private String action;
     private String typeId;
     private String userId;
     private String isPicture;
     private String isCollect;

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

    public String getIsPicture() {
        return isPicture;
    }

    public void setIsPicture(String isPicture) {
        this.isPicture = isPicture;
    }

    public String getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(String isCollect) {
        this.isCollect = isCollect;
    }
}
