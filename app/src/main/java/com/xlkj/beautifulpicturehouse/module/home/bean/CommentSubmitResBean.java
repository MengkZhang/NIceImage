package com.xlkj.beautifulpicturehouse.module.home.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/15.
 * 提交评论返回值bean
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentSubmitResBean implements Serializable {

    /**
     * status : 1
     * msg :
     * data : {"commentId":48,"avatarUlr":"http://q.qlogo.cn/qqapp/101448789/FE245BD2217E9E2AC261AD839D84E51A/100","userName":"短发夏天","time":1516006325,"content":"\u201c很厉害\u201d"}
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
    public static class DataBean implements Serializable{
        /**
         * commentId : 48
         * avatarUlr : http://q.qlogo.cn/qqapp/101448789/FE245BD2217E9E2AC261AD839D84E51A/100
         * userName : 短发夏天
         * time : 1516006325
         * content : “很厉害”
         */

        private int commentId;
        private String avatarUlr;
        private String userName;
        private long time;
        private String content;

        public int getCommentId() {
            return commentId;
        }

        public void setCommentId(int commentId) {
            this.commentId = commentId;
        }

        public String getAvatarUlr() {
            return avatarUlr;
        }

        public void setAvatarUlr(String avatarUlr) {
            this.avatarUlr = avatarUlr;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
