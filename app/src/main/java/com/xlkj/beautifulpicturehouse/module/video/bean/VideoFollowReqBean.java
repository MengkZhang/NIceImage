package com.xlkj.beautifulpicturehouse.module.video.bean;

/**
 * Created by Administrator on 2018/1/3.
 */

public class VideoFollowReqBean {
private String canal;
private String userId;
private String token;
private String plat;
private String service;
private String anchor_id;
private String page;
private String pageSize;
private String check;

    public String getCanal() {
        return canal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPlat() {
        return plat;
    }

    public void setPlat(String plat) {
        this.plat = plat;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getAnchor_id() {
        return anchor_id;
    }

    public void setAnchor_id(String anchor_id) {
        this.anchor_id = anchor_id;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }
}
