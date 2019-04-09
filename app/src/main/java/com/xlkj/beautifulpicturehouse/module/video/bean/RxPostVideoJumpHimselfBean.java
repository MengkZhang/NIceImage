package com.xlkj.beautifulpicturehouse.module.video.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/17.
 * 相关视频的自己跳自己的bean
 */

public class RxPostVideoJumpHimselfBean implements Serializable {
    private boolean isJumpHimself;
    private int position;
    private List<VideoDetailResBean.DataBean.VideoListBean> mList;

    public boolean isJumpHimself() {
        return isJumpHimself;
    }

    public void setJumpHimself(boolean jumpHimself) {
        isJumpHimself = jumpHimself;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public List<VideoDetailResBean.DataBean.VideoListBean> getList() {
        return mList;
    }

    public void setList(List<VideoDetailResBean.DataBean.VideoListBean> list) {
        mList = list;
    }
}
