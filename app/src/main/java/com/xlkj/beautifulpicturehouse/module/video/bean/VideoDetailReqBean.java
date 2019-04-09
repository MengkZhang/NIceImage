package com.xlkj.beautifulpicturehouse.module.video.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/11.
 * 视频详情页请求参数bean
 */

public class VideoDetailReqBean implements Serializable {
    private String action;
    private String followId;
    private String videoId;
    private String userId;
    private String page;

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getFollowId() {
        return followId;
    }

    public void setFollowId(String followId) {
        this.followId = followId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
