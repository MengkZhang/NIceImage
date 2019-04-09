package com.xlkj.beautifulpicturehouse.module.home.bean;

/**
 * Created by Administrator on 2018/1/2.
 */

public class SearchReqBean {
//    http://api.app.27270.com/api/index.php?action=search&searchKey=%E5%A5%B3%E7%A5%9E&page=4
    private String uid;
    private String action;
    private String searchKey;
    private String page;
    private String type;
    private String typeVideo;

    public String getTypeVideo() {
        return typeVideo;
    }

    public void setTypeVideo(String typeVideo) {
        this.typeVideo = typeVideo;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

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
