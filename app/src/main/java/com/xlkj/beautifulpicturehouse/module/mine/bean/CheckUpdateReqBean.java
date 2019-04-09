package com.xlkj.beautifulpicturehouse.module.mine.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/15.
 * 检测跟新请求参数bean
 */

public class CheckUpdateReqBean implements Serializable {
    private String versionCode;
    private String action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }
}
