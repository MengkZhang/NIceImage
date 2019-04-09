package com.xlkj.beautifulpicturehouse.module.video.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/16.
 * 搜索视频返回值bean
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoSearchResBean implements Serializable {

    /**
     * status : 1
     * msg :
     * data : {"video":[{"imageUrl":"http://zsyj-video-show.oss-cn-beijing.aliyuncs.com/img/5a4483f4885aa.jpg","videoUrl":"http://zsyj-video-show.oss-cn-beijing.aliyuncs.com/video/5a4483f4885aa.mp4","videoId":"36","isVip":0,"followId":"34","typeName":"女神"},{"imageUrl":"http://zsyj-video-show.oss-cn-beijing.aliyuncs.com/img/5a2e4041cec5d.jpg","videoUrl":"http://zsyj-video-show.oss-cn-beijing.aliyuncs.com/video/5a2e4041cec5d.mp4","videoId":"38","isVip":0,"followId":"35","typeName":"美女"},{"imageUrl":"http://s.3987.com/uploadfile/2018/0105/thumb_2018010513431829580.jpg","videoUrl":"http://s.3987.com/uploadfile/video/2018/0105/2018010513431820135.mp4","videoId":"37","isVip":0,"followId":"33","typeName":"诱人蜜桃臀健身美女"}],"totalPage":1}
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
         * video : [{"imageUrl":"http://zsyj-video-show.oss-cn-beijing.aliyuncs.com/img/5a4483f4885aa.jpg","videoUrl":"http://zsyj-video-show.oss-cn-beijing.aliyuncs.com/video/5a4483f4885aa.mp4","videoId":"36","isVip":0,"followId":"34","typeName":"女神"},{"imageUrl":"http://zsyj-video-show.oss-cn-beijing.aliyuncs.com/img/5a2e4041cec5d.jpg","videoUrl":"http://zsyj-video-show.oss-cn-beijing.aliyuncs.com/video/5a2e4041cec5d.mp4","videoId":"38","isVip":0,"followId":"35","typeName":"美女"},{"imageUrl":"http://s.3987.com/uploadfile/2018/0105/thumb_2018010513431829580.jpg","videoUrl":"http://s.3987.com/uploadfile/video/2018/0105/2018010513431820135.mp4","videoId":"37","isVip":0,"followId":"33","typeName":"诱人蜜桃臀健身美女"}]
         * totalPage : 1
         */

        private int totalPage;
        private List<VideoBean> video;

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public List<VideoBean> getVideo() {
            return video;
        }

        public void setVideo(List<VideoBean> video) {
            this.video = video;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class VideoBean implements Serializable{
            /**
             * imageUrl : http://zsyj-video-show.oss-cn-beijing.aliyuncs.com/img/5a4483f4885aa.jpg
             * videoUrl : http://zsyj-video-show.oss-cn-beijing.aliyuncs.com/video/5a4483f4885aa.mp4
             * videoId : 36
             * isVip : 0
             * followId : 34
             * typeName : 女神
             */

            private String imageUrl;
            private String videoUrl;
            private String videoId;
            private int isVip;
            private String followId;
            private String typeName;

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

            public String getFollowId() {
                return followId;
            }

            public void setFollowId(String followId) {
                this.followId = followId;
            }

            public String getTypeName() {
                return typeName;
            }

            public void setTypeName(String typeName) {
                this.typeName = typeName;
            }
        }
    }
}
