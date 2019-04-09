package com.xlkj.beautifulpicturehouse.module.video.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/19.
 * 相关视频回到详情播放bean
 */

public class RxPostPlayBean implements Serializable{
    private boolean isPlay;
    private int position;
    private List<VideoDetailResBean.DataBean.VideoListBean> videoList;

    public boolean isPlay() {
        return isPlay;
    }

    public void setPlay(boolean play) {
        isPlay = play;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public List<VideoDetailResBean.DataBean.VideoListBean> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<VideoDetailResBean.DataBean.VideoListBean> videoList) {
        this.videoList = videoList;
    }
}
