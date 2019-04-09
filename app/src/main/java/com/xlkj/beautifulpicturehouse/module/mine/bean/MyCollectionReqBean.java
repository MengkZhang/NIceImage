package com.xlkj.beautifulpicturehouse.module.mine.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/12.
 * 我的收藏请求参数bean
 */

public class MyCollectionReqBean implements Serializable {
//    action=collectionList&userId=1
    private String action;
    private String userId;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

