package com.xlkj.beautifulpicturehouse.module.home.bean;

/**
 * Created by Administrator on 2018/1/2.
 * 精选详情页的请求参数bean
 */

public class GoodDetailReqBean {
//    ?action=selectedList&imageId=230035&userId=1
    private String action;
    private String imageId;
    private String userId;
    private String followId;

    public String getFollowId() {
        return followId;
    }

    public void setFollowId(String followId) {
        this.followId = followId;
    }

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
