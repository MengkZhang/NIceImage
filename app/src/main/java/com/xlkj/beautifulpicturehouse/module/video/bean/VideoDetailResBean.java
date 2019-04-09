package com.xlkj.beautifulpicturehouse.module.video.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/11.
 * 视频详情页返回值bean
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoDetailResBean implements Serializable {

    /**
     * status : 1
     * msg :
     * data : {"userName":"小清新","followId":"34","isFollower":0,"avatarurl":"http://t2.hddhhn.com/uploads/tu/201708/9999/00a28c6c34.jpg","fansCount":"0","playCount":"0","videoCount":"0","totalPage":1,"videoList":[{"videoId":"36","isVip":0,"imageUrl":"http://zsyj-video-show.oss-cn-beijing.aliyuncs.com/img/5a4483f4885aa.jpg","videoUrl":"http://zsyj-video-show.oss-cn-beijing.aliyuncs.com/video/5a4483f4885aa.mp4","isCollect":0}]}
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
         * userName : 小清新
         * followId : 34
         * isFollower : 0
         * avatarurl : http://t2.hddhhn.com/uploads/tu/201708/9999/00a28c6c34.jpg
         * fansCount : 0
         * playCount : 0
         * videoCount : 0
         * totalPage : 1
         * isCollect:0
         * videoList : [{"videoId":"36","isVip":0,"imageUrl":"http://zsyj-video-show.oss-cn-beijing.aliyuncs.com/img/5a4483f4885aa.jpg","videoUrl":"http://zsyj-video-show.oss-cn-beijing.aliyuncs.com/video/5a4483f4885aa.mp4","isCollect":0}]
         */

        private String userName;
        private String followId;
        private int isFollower;
        private String avatarurl;
        private String fansCount;
        private String playCount;
        private String videoCount;
        private int totalPage;
        private int isCollect;
        private List<VideoListBean> videoList;

        public int getIsCollect() {
            return isCollect;
        }

        public void setIsCollect(int isCollect) {
            this.isCollect = isCollect;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getFollowId() {
            return followId;
        }

        public void setFollowId(String followId) {
            this.followId = followId;
        }

        public int getIsFollower() {
            return isFollower;
        }

        public void setIsFollower(int isFollower) {
            this.isFollower = isFollower;
        }

        public String getAvatarurl() {
            return avatarurl;
        }

        public void setAvatarurl(String avatarurl) {
            this.avatarurl = avatarurl;
        }

        public String getFansCount() {
            return fansCount;
        }

        public void setFansCount(String fansCount) {
            this.fansCount = fansCount;
        }

        public String getPlayCount() {
            return playCount;
        }

        public void setPlayCount(String playCount) {
            this.playCount = playCount;
        }

        public String getVideoCount() {
            return videoCount;
        }

        public void setVideoCount(String videoCount) {
            this.videoCount = videoCount;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public List<VideoListBean> getVideoList() {
            return videoList;
        }

        public void setVideoList(List<VideoListBean> videoList) {
            this.videoList = videoList;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class VideoListBean implements Serializable{
            /**
             * videoId : 36
             * isVip : 0
             * imageUrl : http://zsyj-video-show.oss-cn-beijing.aliyuncs.com/img/5a4483f4885aa.jpg
             * videoUrl : http://zsyj-video-show.oss-cn-beijing.aliyuncs.com/video/5a4483f4885aa.mp4
             * isCollect : 0
             */

            private String videoId;
            private int isVip;
            private String imageUrl;
            private String videoUrl;
            private int isCollect;

            public String getVideoId() {
                return videoId;
            }

            public void setVideoId(String videoId) {
                this.videoId = videoId;
            }

            public int getIsVip() {
                return isVip;
            }

            public void setIsVip(int isVip) {
                this.isVip = isVip;
            }

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }

            public String getVideoUrl() {
                return videoUrl;
            }

            public void setVideoUrl(String videoUrl) {
                this.videoUrl = videoUrl;
            }

            public int getIsCollect() {
                return isCollect;
            }

            public void setIsCollect(int isCollect) {
                this.isCollect = isCollect;
            }
        }
    }
}
