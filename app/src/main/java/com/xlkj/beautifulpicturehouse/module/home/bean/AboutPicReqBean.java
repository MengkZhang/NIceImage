package com.xlkj.beautifulpicturehouse.module.home.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/8.
 * 相关美图请求参数的bean
 */

public class AboutPicReqBean implements Serializable {
    private String action;
    private String imageId;
    private String userId;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
