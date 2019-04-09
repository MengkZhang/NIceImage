package com.xlkj.beautifulpicturehouse.module.home.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/15.
 * 获取评论列表的返回值bean
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentListResBean implements Serializable {

    /**
     * status : 1
     * msg :
     * data : {"totapage":1,"commentList":[{"commentId":"1","content":"\"很棒\"","time":"1516010494","userName":"短发夏天","avatarUlr":"http://q.qlogo.cn/qqapp/101448789/FE245BD2217E9E2AC261AD839D84E51A/100"},{"commentId":"2","content":"\"很棒\"","time":"1516010503","userName":"短发夏天","avatarUlr":"http://q.qlogo.cn/qqapp/101448789/FE245BD2217E9E2AC261AD839D84E51A/100"},{"commentId":"3","content":"\"很棒\"","time":"1516010503","userName":"短发夏天","avatarUlr":"http://q.qlogo.cn/qqapp/101448789/FE245BD2217E9E2AC261AD839D84E51A/100"},{"commentId":"4","content":"\"很棒\"","time":"1516010503","userName":"短发夏天","avatarUlr":"http://q.qlogo.cn/qqapp/101448789/FE245BD2217E9E2AC261AD839D84E51A/100"},{"commentId":"5","content":"\"很棒\"","time":"1516010503","userName":"短发夏天","avatarUlr":"http://q.qlogo.cn/qqapp/101448789/FE245BD2217E9E2AC261AD839D84E51A/100"}]}
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
         * totapage : 1
         * commentList : [{"commentId":"1","content":"\"很棒\"","time":"1516010494","userName":"短发夏天","avatarUlr":"http://q.qlogo.cn/qqapp/101448789/FE245BD2217E9E2AC261AD839D84E51A/100"},{"commentId":"2","content":"\"很棒\"","time":"1516010503","userName":"短发夏天","avatarUlr":"http://q.qlogo.cn/qqapp/101448789/FE245BD2217E9E2AC261AD839D84E51A/100"},{"commentId":"3","content":"\"很棒\"","time":"1516010503","userName":"短发夏天","avatarUlr":"http://q.qlogo.cn/qqapp/101448789/FE245BD2217E9E2AC261AD839D84E51A/100"},{"commentId":"4","content":"\"很棒\"","time":"1516010503","userName":"短发夏天","avatarUlr":"http://q.qlogo.cn/qqapp/101448789/FE245BD2217E9E2AC261AD839D84E51A/100"},{"commentId":"5","content":"\"很棒\"","time":"1516010503","userName":"短发夏天","avatarUlr":"http://q.qlogo.cn/qqapp/101448789/FE245BD2217E9E2AC261AD839D84E51A/100"}]
         */

        private int totapage;
        private List<CommentListBean> commentList;

        public int getTotapage() {
            return totapage;
        }

        public void setTotapage(int totapage) {
            this.totapage = totapage;
        }

        public List<CommentListBean> getCommentList() {
            return commentList;
        }

        public void setCommentList(List<CommentListBean> commentList) {
            this.commentList = commentList;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class CommentListBean implements Serializable {
            /**
             * commentId : 1
             * content : "很棒"
             * time : 1516010494
             * userName : 短发夏天
             * avatarUlr : http://q.qlogo.cn/qqapp/101448789/FE245BD2217E9E2AC261AD839D84E51A/100
             */

            private String commentId;
            private String content;
            private long time;
            private String userName;
            private String avatarUlr;

            public String getCommentId() {
                return commentId;
            }

            public void setCommentId(String commentId) {
                this.commentId = commentId;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getAvatarUlr() {
                return avatarUlr;
            }

            public void setAvatarUlr(String avatarUlr) {
                this.avatarUlr = avatarUlr;
            }
        }
    }
}
