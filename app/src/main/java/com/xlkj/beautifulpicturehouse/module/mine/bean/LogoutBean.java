package com.xlkj.beautifulpicturehouse.module.mine.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/4.
 */

public class LogoutBean implements Serializable {
    private boolean isLogout;

    public boolean isLogout() {
        return isLogout;
    }

    public void setLogout(boolean logout) {
        isLogout = logout;
    }
}
