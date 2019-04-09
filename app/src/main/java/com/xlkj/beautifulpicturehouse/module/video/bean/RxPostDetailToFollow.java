package com.xlkj.beautifulpicturehouse.module.video.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/16.
 * 详情页面到关注页面的状态回调bean
 */

public class RxPostDetailToFollow implements Serializable {
    private boolean isMyFollow;

    public boolean isMyFollow() {
        return isMyFollow;
    }

    public void setMyFollow(boolean myFollow) {
        isMyFollow = myFollow;
    }
}
