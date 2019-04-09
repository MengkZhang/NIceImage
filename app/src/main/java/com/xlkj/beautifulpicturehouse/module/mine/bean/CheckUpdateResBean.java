package com.xlkj.beautifulpicturehouse.module.mine.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/15.
 * 检测更新返回值bean
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckUpdateResBean implements Serializable {


    /**
     * status : 1
     * msg :
     * data : {"apkUrl":"http://apk.zsyj.com.cn/soft/HbksZqb-admin-4.1.9.apk","updateDesc":"新版本的27270 APP端","isUpdate":1}
     */

    private int status;
    private String msg;
    private DataBean data;

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
    public static class DataBean implements Serializable {
        /**
         * apkUrl : http://apk.zsyj.com.cn/soft/HbksZqb-admin-4.1.9.apk
         * updateDesc : 新版本的27270 APP端
         * isUpdate : 1
         */

        private String apkUrl;
        private String updateDesc;
        private int isUpdate;

        public String getApkUrl() {
            return apkUrl;
        }

        public void setApkUrl(String apkUrl) {
            this.apkUrl = apkUrl;
        }

        public String getUpdateDesc() {
            return updateDesc;
        }

        public void setUpdateDesc(String updateDesc) {
            this.updateDesc = updateDesc;
        }

        public int getIsUpdate() {
            return isUpdate;
        }

        public void setIsUpdate(int isUpdate) {
            this.isUpdate = isUpdate;
        }
    }
}
