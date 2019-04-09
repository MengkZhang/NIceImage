package com.xlkj.beautifulpicturehouse.module.video.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/9.
 * 波主个人页返回到视频详情页bean
 */

public class ReturnBean implements Serializable {
    private boolean isReturn;

    public boolean isReturn() {
        return isReturn;
    }

    public void setReturn(boolean aReturn) {
        isReturn = aReturn;
    }
}
