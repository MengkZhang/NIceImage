package com.xlkj.beautifulpicturehouse.module.home.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/5.
 * 收藏 返回值bean
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CollectionResBean implements Serializable {

    /**
     * status : 1
     * msg : 收藏成功！
     * data :
     */

    public int status;
    public String msg;
    public String data;
    public String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
