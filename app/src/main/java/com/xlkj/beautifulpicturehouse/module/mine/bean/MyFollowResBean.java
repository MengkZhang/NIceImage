package com.xlkj.beautifulpicturehouse.module.mine.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/12.
 * 我的关注返回值bean
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MyFollowResBean implements Serializable
{

    /**
     * status : 1
     * msg :
     * data : {"tagList":[{"tagId":"2024","tagName":"性感美女"},{"tagId":"2347","tagName":"高跟鞋美女"},{"tagId":"2346","tagName":"黑丝美腿"},{"tagId":"62","tagName":"大胸"},{"tagId":"2042","tagName":"红唇"},{"tagId":"1949","tagName":"销魂.性感"},{"tagId":"2348","tagName":"可爱美女"},{"tagId":"2345","tagName":"衬衫美女"},{"tagId":"2324","tagName":"海边美女"},{"tagId":"2291","tagName":"清凉美女"},{"tagId":"875","tagName":"女神"},{"tagId":"2202","tagName":"软妹子"},{"tagId":"782","tagName":"韩国美女"},{"tagId":"2015","tagName":"李菲儿"},{"tagId":"1868","tagName":"妲己"},{"tagId":"1875","tagName":"动人"},{"tagId":"1924","tagName":"ParidaKumsop"},{"tagId":"1892","tagName":"宋智珍"},{"tagId":"780","tagName":"魅妍社"}],"userList":[{"userId":"35","userName":"fanny","avatar":"http://t2.hddhhn.com/uploads/tu/201708/9999/9698da1617.jpg"},{"userId":"34","userName":"小清新","avatar":"http://t2.hddhhn.com/uploads/tu/201708/9999/00a28c6c34.jpg"},{"userId":"33","userName":"维纳斯的三女儿","avatar":"http://t2.hddhhn.com/uploads/tu/201606/54/03uc3xlqhzo.jpg"}]}
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
        private List<TagListBean> tagList;
        private List<UserListBean> userList;

        public List<TagListBean> getTagList() {
            return tagList;
        }

        public void setTagList(List<TagListBean> tagList) {
            this.tagList = tagList;
        }

        public List<UserListBean> getUserList() {
            return userList;
        }

        public void setUserList(List<UserListBean> userList) {
            this.userList = userList;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class TagListBean implements Serializable {
            /**
             * tagId : 2024
             * tagName : 性感美女
             */

            private String tagId;
            private String tagName;

            public String getTagId() {
                return tagId;
            }

            public void setTagId(String tagId) {
                this.tagId = tagId;
            }

            public String getTagName() {
                return tagName;
            }

            public void setTagName(String tagName) {
                this.tagName = tagName;
            }
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class UserListBean implements Serializable {
            /**
             * userId : 35
             * userName : fanny
             * avatar : http://t2.hddhhn.com/uploads/tu/201708/9999/9698da1617.jpg
             */

            private String userId;
            private String userName;
            private String avatar;

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }
        }
    }
}
