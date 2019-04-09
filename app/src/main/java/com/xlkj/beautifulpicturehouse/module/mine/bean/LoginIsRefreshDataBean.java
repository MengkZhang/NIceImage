package com.xlkj.beautifulpicturehouse.module.mine.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/5.
 * 登录成功后 RxBus发送消息到首页重新获取数据的bean
 */

public class LoginIsRefreshDataBean implements Serializable {
    private boolean isRefreshData;

    public boolean isRefreshData() {
        return isRefreshData;
    }

    public void setRefreshData(boolean refreshData) {
        isRefreshData = refreshData;
    }
}