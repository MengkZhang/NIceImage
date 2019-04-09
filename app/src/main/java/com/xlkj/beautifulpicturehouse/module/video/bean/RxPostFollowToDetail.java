package com.xlkj.beautifulpicturehouse.module.video.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/16.
 * 关注页面的状态到详情页面回调bean
 */

public class RxPostFollowToDetail implements Serializable {
    private boolean isMyFollow;

    public boolean isMyFollow() {
        return isMyFollow;
    }

    public void setMyFollow(boolean myFollow) {
        isMyFollow = myFollow;
    }
}
