package com.xlkj.beautifulpicturehouse.module.mine.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/27.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetTokenOpenIdBean implements Serializable {

    /**
     * access_token : 5_IV4AwvgoxMt4fBQlwSZF5hgFYTvBF8lN1YE4sCBTkP8_5kmTnW3CPJi9rxvw4XLUlyf-qGRfYJVRfnLhxB1xtC0eVAACjjKoLiUaXhh3ilI
     * expires_in : 7200
     * refresh_token : 5_TCVOD--iOLq1LpacfzboG6VC5u2G8xpu-H2XcTGrDobzgc37QC-79ZCW5Sb8B_MU-kaLFbF75QikPD1zaZc-kAUX4zS_u7jvELwVQZlMJf8
     * openid : oyUjF0WUQB4locJa4waz99DWTVqQ
     * scope : snsapi_userinfo
     * unionid : opPrW0w54_V7Z2wZf4fbWtIaGazA
     */

    public String access_token;
    public int expires_in;
    public String refresh_token;
    public String openid;
    public String scope;
    public String unionid;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
