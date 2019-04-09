package com.xlkj.beautifulpicturehouse.module.mine.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/27.
 */

public class ReqGetTokenOpenIdBean implements Serializable {
//    @Query("appid") String appid,
//    @Query("secret") String secret,
//    @Query("code") String code,
//    @Query("grant_type") String grant_type
    private String appid;
    private String secret;
    private String code;
    private String grant_type;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }
}
