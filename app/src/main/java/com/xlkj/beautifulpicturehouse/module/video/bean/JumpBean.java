package com.xlkj.beautifulpicturehouse.module.video.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/9.
 * RxBus从videoDetailFragment页点击到上一个activity执行viewpager的跳转的bean
 */

public class JumpBean implements Serializable {
    private boolean isJump;

    public boolean isJump() {
        return isJump;
    }

    public void setJump(boolean jump) {
        isJump = jump;
    }
}
