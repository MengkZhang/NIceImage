package com.xlkj.beautifulpicturehouse.module.home.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/25.
 * 从刷新到数据的回调实体
 */

public class RefreshToHotVideoBean implements Serializable {
    private boolean isRefreshData;
    private int position;

    public boolean isRefreshData() {
        return isRefreshData;
    }

    public void setRefreshData(boolean refreshData) {
        isRefreshData = refreshData;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
