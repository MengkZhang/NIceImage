package com.xlkj.beautifulpicturehouse.module.home.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/5.
 * 关注或取消关注的返回值bean
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class FollowResBean implements Serializable {

    /**
     * status : 1
     * state : 1
     * msg : 关注成功！
     * data :
     */

    private int status;
    private int state;
    private String msg;
    private String data;

    public int getState() {
        return state;
    }

    public void setState(int state) {
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
