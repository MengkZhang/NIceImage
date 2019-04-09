package com.xlkj.beautifulpicturehouse.module.home.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/25.
 */

public class Hot2RefreshBean implements Serializable {
    private boolean isRefreshSuccess;

    private String mId;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public boolean isRefreshSuccess() {
        return isRefreshSuccess;
    }

    public void setRefreshSuccess(boolean refreshSuccess) {
        isRefreshSuccess = refreshSuccess;
    }
}
