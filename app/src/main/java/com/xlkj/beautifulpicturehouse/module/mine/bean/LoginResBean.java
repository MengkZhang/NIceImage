package com.xlkj.beautifulpicturehouse.module.mine.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/5.
 * 登录返回的bean
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResBean implements Serializable {

    /**
     * status : 1
     * msg : 登陆成功!
     * data : {"isVip":0,"vipLevel":0,"vipDayCount":0,"token":1515127136,"userId":"1"}
     */

    public int status;
    public String msg;
    public DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataBean implements Serializable{
        /**
         * isVip : 0
         * vipLevel : 0
         * vipDayCount : 0
         * token : 1515127136
         * userId : 1
         */

        public int isVip;
        public int vipLevel;
        public int vipDayCount;
        public int token;
        public String userId;

        public int getIsVip() {
            return isVip;
        }

        public void setIsVip(int isVip) {
            this.isVip = isVip;
        }

        public int getVipLevel() {
            return vipLevel;
        }

        public void setVipLevel(int vipLevel) {
            this.vipLevel = vipLevel;
        }

        public int getVipDayCount() {
            return vipDayCount;
        }

        public void setVipDayCount(int vipDayCount) {
            this.vipDayCount = vipDayCount;
        }

        public int getToken() {
            return token;
        }

        public void setToken(int token) {
            this.token = token;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
