package com.xlkj.beautifulpicturehouse.module.home.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/17.
 * 发送关闭图片详情的activity的消息bean
 */

public class RxPostFinishPicDetailBean implements Serializable {
    private boolean isFinishPicDetail;

    public boolean isFinishPicDetail() {
        return isFinishPicDetail;
    }

    public void setFinishPicDetail(boolean finishPicDetail) {
        isFinishPicDetail = finishPicDetail;
    }
}
