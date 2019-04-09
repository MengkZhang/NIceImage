package com.xlkj.beautifulpicturehouse.module.video.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/16.
 * 搜索视频请求参数bean
 */

public class VideoSearchReqBean implements Serializable {
//    http://api.app.27270.com/api/index.php?action=searchVideo&searchKey=%E5%A5%B3&page=1
    private String action;
    private String searchKey;
    private String page;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
