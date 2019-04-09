package com.xlkj.beautifulpicturehouse.module.video.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/29.
 */

public class VideoAboutReqBean implements Serializable {
    //http://s.3987.com/beauty-api-1.1/Public/demo/index.php?
    // canal=original&userId=5275&
    // token=0e7a8963241611da87cb90d14cbf2a84&
    // plat=38&
    // service=VipPicture.AnchorDetail&
    // anchor_id=1214&
    // page=1&
    // pageSize=30&
    // check=4d227dad03cfc1eaac0f797f173279cb
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
