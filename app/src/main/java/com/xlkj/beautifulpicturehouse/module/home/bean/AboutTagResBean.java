package com.xlkj.beautifulpicturehouse.module.home.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/9.
 * 详情页的相关标签的返回值bean
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AboutTagResBean implements Serializable {

    /**
     * status : 1
     * msg :
     * data : {"tag":[{"tagName":"大尺度","tagId":"31"},{"tagName":"气质","tagId":"174"},{"tagName":"长发","tagId":"331"}],"isCollect":1}
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
    public static class DataBean implements Serializable {
        /**
         * tag : [{"tagName":"大尺度","tagId":"31"},{"tagName":"气质","tagId":"174"},{"tagName":"长发","tagId":"331"}]
         * isCollect : 1
         */

        public int isCollect;
        public List<TagBean> tag;

        public int getIsCollect() {
            return isCollect;
        }

        public void setIsCollect(int isCollect) {
            this.isCollect = isCollect;
        }

        public List<TagBean> getTag() {
            return tag;
        }

        public void setTag(List<TagBean> tag) {
            this.tag = tag;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class TagBean implements Serializable {
            /**
             * tagName : 大尺度
             * tagId : 31
             */

            public String tagName;
            public String tagId;

            public String getTagName() {
                return tagName;
            }

            public void setTagName(String tagName) {
                this.tagName = tagName;
            }

            public String getTagId() {
                return tagId;
            }

            public void setTagId(String tagId) {
                this.tagId = tagId;
            }
        }
    }
}
