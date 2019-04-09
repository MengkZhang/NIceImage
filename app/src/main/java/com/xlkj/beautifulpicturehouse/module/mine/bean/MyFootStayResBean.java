package com.xlkj.beautifulpicturehouse.module.mine.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/15.
 * 我的足迹返回值bean
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class MyFootStayResBean implements Serializable {

    /**
     * status : 1
     * msg :
     * data : {"todayList":[{"imageId":"169781","type":"0","userId":"1","userName":"","video":"","imageUrl":"http://t1.hddhhn.com/uploads/tu/201612/515/st6.png","typeName":"中国性感女神杨子芯写真套图"},{"imageId":"168668","type":"0","userId":"1","userName":"","video":"","imageUrl":"http://t1.hddhhn.com/uploads/tu/201612/239/st1.png","typeName":"长发清新女神周思超最新写真图片"},{"imageId":"167881","type":"0","userId":"1","userName":"","video":"","imageUrl":"http://t1.hddhhn.com/uploads/tu/201611/202/st1.png","typeName":"战姝羽大秀美妙身材性感图片"},{"imageId":"3853","type":"0","userId":"1","userName":"","video":"","imageUrl":"http://t1.hddhhn.com/uploads/tu/meinv/911/65.jpg","typeName":"另类性感美女惊艳写真"}],"historyList":[{"imageId":"182100","type":"0","userId":"1","userName":"","video":"","imageUrl":"http://t1.hddhhn.com/uploads/tu/201704/330/c1.jpg","typeName":"丰满美女模特Cherry Quahst性感私房照"},{"imageId":"168724","type":"0","userId":"1","userName":"","video":"","imageUrl":"http://t1.hddhhn.com/uploads/tu/201612/276/st1.png","typeName":"性感美女赵小米Kitty小清新图片"},{"imageId":"36","type":"1","userId":"1","userName":"小清新","video":"http://zsyj-video-show.oss-cn-beijing.aliyuncs.com/video/5a4483f4885aa.mp4","imageUrl":"http://zsyj-video-show.oss-cn-beijing.aliyuncs.com/img/5a4483f4885aa.jpg","typeName":"女神"}]}
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
        private List<TodayListBean> todayList;
        private List<HistoryListBean> historyList;

        public List<TodayListBean> getTodayList() {
            return todayList;
        }

        public void setTodayList(List<TodayListBean> todayList) {
            this.todayList = todayList;
        }

        public List<HistoryListBean> getHistoryList() {
            return historyList;
        }

        public void setHistoryList(List<HistoryListBean> historyList) {
            this.historyList = historyList;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class TodayListBean implements Serializable {
            /**
             * imageId : 169781
             * type : 0
             * userId : 1
             * userName :
             * video :
             * imageUrl : http://t1.hddhhn.com/uploads/tu/201612/515/st6.png
             * typeName : 中国性感女神杨子芯写真套图
             */

            private String imageId;
            private int type;
            private String userId;
            private String userName;
            private String video;
            private String imageUrl;
            private String typeName;

            public String getImageId() {
                return imageId;
            }

            public void setImageId(String imageId) {
                this.imageId = imageId;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

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

            public String getVideo() {
                return video;
            }

            public void setVideo(String video) {
                this.video = video;
            }

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }

            public String getTypeName() {
                return typeName;
            }

            public void setTypeName(String typeName) {
                this.typeName = typeName;
            }
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class HistoryListBean implements Serializable{
            /**
             * imageId : 182100
             * type : 0
             * userId : 1
             * userName :
             * video :
             * imageUrl : http://t1.hddhhn.com/uploads/tu/201704/330/c1.jpg
             * typeName : 丰满美女模特Cherry Quahst性感私房照
             */

            private String imageId;
            private int type;
            private String userId;
            private String userName;
            private String video;
            private String imageUrl;
            private String typeName;

            public String getImageId() {
                return imageId;
            }

            public void setImageId(String imageId) {
                this.imageId = imageId;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

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

            public String getVideo() {
                return video;
            }

            public void setVideo(String video) {
                this.video = video;
            }

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
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
