package com.xlkj.beautifulpicturehouse.module.home.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/2.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class DefSearchResBean implements Serializable {

    /**
     * status : 1
     * msg :
     * data : [{"searchKey":"长腿美女，","isVip":0,"searchId":"2351"},{"searchKey":"长腿美女，大奶，吊带","isVip":0,"searchId":"2350"},{"searchKey":"大尺度人体艺","isVip":0,"searchId":"2349"},{"searchKey":"可爱美女","isVip":0,"searchId":"2348"},{"searchKey":"高跟鞋美女","isVip":0,"searchId":"2347"},{"searchKey":"黑丝美腿","isVip":0,"searchId":"2346"},{"searchKey":"衬衫美女","isVip":0,"searchId":"2345"},{"searchKey":"女制服诱惑","isVip":0,"searchId":"2344"},{"searchKey":"日本美","isVip":0,"searchId":"2343"},{"searchKey":"妲己_Toxic","isVip":0,"searchId":"2340"}]
     */

    public int status;
    public String msg;
    public List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataBean implements Serializable {
        /**
         * searchKey : 长腿美女，
         * isVip : 0
         * searchId : 2351
         */

        public String searchKey;
        public int isVip;
        public String searchId;

        public String getSearchKey() {
            return searchKey;
        }

        public void setSearchKey(String searchKey) {
            this.searchKey = searchKey;
        }

        public int getIsVip() {
            return isVip;
        }

        public void setIsVip(int isVip) {
            this.isVip = isVip;
        }

        public String getSearchId() {
            return searchId;
        }

        public void setSearchId(String searchId) {
            this.searchId = searchId;
        }
    }
}
