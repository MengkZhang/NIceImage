package com.xlkj.beautifulpicturehouse.module.mine.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/12.
 * 我的收藏返回值bean
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MyCollectionResBean implements Serializable {

    /**
     * status : 1
     * msg :
     * data : {"imageList":[{"imageId":"129719_0","imageUrl":"http://t1.hddhhn.com/uploads/tu/201508/0005/1.jpg","typeName":"性感女郎梦娜Vanessa浴室湿身照"},{"imageId":"142298_0","imageUrl":"http://t1.hddhhn.com/uploads/tu/201603/6/553c23ceog0.jpg","typeName":"秀人网黄歆苑学生制服诱惑写真"},{"imageId":"142298_3","imageUrl":"http://t1.hddhhn.com/uploads/tu/201603/6/5myezqmp4ko.jpg","typeName":"秀人网黄歆苑学生制服诱惑写真"},{"imageId":"144074_0","imageUrl":"http://t1.hddhhn.com/uploads/tu/201603/190/pfr0s5fcdr1.jpg","typeName":"短发妹子史雯Swan比基尼湿身写真套图"},{"imageId":"167881_0","imageUrl":"http://t1.hddhhn.com/uploads/tu/201611/202/1.jpg","typeName":"战姝羽大秀美妙身材性感图片"},{"imageId":"167881_5","imageUrl":"http://t1.hddhhn.com/uploads/tu/201611/202/6.jpg","typeName":"战姝羽大秀美妙身材性感图片"},{"imageId":"168653_1","imageUrl":"http://t1.hddhhn.com/uploads/tu/201612/238/azhrzuprprt.jpg","typeName":"性感姐妹花林蕾&amp;杏儿kitty女仆私房照"},{"imageId":"168653_2","imageUrl":"http://t1.hddhhn.com/uploads/tu/201612/238/ok0fuxkttyq.jpg","typeName":"性感姐妹花林蕾&amp;杏儿kitty女仆私房照"},{"imageId":"168653_3","imageUrl":"http://t1.hddhhn.com/uploads/tu/201612/238/2r0vqrcy0mh.jpg","typeName":"性感姐妹花林蕾&amp;杏儿kitty女仆私房照"},{"imageId":"168653_4","imageUrl":"http://t1.hddhhn.com/uploads/tu/201612/238/uy03riddaow.jpg","typeName":"性感姐妹花林蕾&amp;杏儿kitty女仆私房照"},{"imageId":"168653_5","imageUrl":"http://t1.hddhhn.com/uploads/tu/201612/238/4qjvncwea3x.jpg","typeName":"性感姐妹花林蕾&amp;杏儿kitty女仆私房照"},{"imageId":"168657_0","imageUrl":"http://t1.hddhhn.com/uploads/tu/201612/238/5qguvhnph2p.jpg","typeName":"长发气质美女林蕾大尺度写真"},{"imageId":"168657_1","imageUrl":"http://t1.hddhhn.com/uploads/tu/201612/238/bkjttj2o0tx.jpg","typeName":"长发气质美女林蕾大尺度写真"},{"imageId":"168657_3","imageUrl":"http://t1.hddhhn.com/uploads/tu/201612/238/gapuqdwihcn.jpg","typeName":"长发气质美女林蕾大尺度写真"},{"imageId":"168657_5","imageUrl":"http://t1.hddhhn.com/uploads/tu/201612/238/sfr3oetj3ou.jpg","typeName":"长发气质美女林蕾大尺度写真"},{"imageId":"168724_0","imageUrl":"http://t1.hddhhn.com/uploads/tu/201612/276/1.jpg","typeName":"性感美女赵小米Kitty小清新图片"},{"imageId":"182100_0","imageUrl":"http://t1.hddhhn.com/uploads/tu/201704/9999/58f6c7ddba.jpg","typeName":"丰满美女模特Cherry Quahst性感私房照"},{"imageId":"248569_7","imageUrl":"http://t1.hddhhn.com/uploads/tu/201712/9999/c64475364f.jpg","typeName":"妖娆长腿美女粉嫩比基尼大秀酥胸性感图片"},{"imageId":"252754_0","imageUrl":"http://t1.hddhhn.com/uploads/tu/201801/9999/989a4ee69d.jpg","typeName":"清纯美女黑色吊带泳衣湿身诱惑白嫩双腿撩人写真"},{"imageId":"252754_3","imageUrl":"http://t1.hddhhn.com/uploads/tu/201801/9999/4cf7091523.jpg","typeName":"清纯美女黑色吊带泳衣湿身诱惑白嫩双腿撩人写真"}],"videoList":[{"userId":"34","userName":"小清新","avatar":"http://t2.hddhhn.com/uploads/tu/201708/9999/00a28c6c34.jpg","typeName":"女神"},{"userId":"33","userName":"维纳斯的三女儿","avatar":"http://t2.hddhhn.com/uploads/tu/201606/54/03uc3xlqhzo.jpg","typeName":"诱人蜜桃臀健身美女"},{"userId":"35","userName":"fanny","avatar":"http://t2.hddhhn.com/uploads/tu/201708/9999/9698da1617.jpg","typeName":"美女"}]}
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
        private List<ImageListBean> imageList;
        private List<VideoListBean> videoList;

        public List<ImageListBean> getImageList() {
            return imageList;
        }

        public void setImageList(List<ImageListBean> imageList) {
            this.imageList = imageList;
        }

        public List<VideoListBean> getVideoList() {
            return videoList;
        }

        public void setVideoList(List<VideoListBean> videoList) {
            this.videoList = videoList;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ImageListBean implements Serializable  {
            /**
             * imageId : 129719_0
             * imageUrl : http://t1.hddhhn.com/uploads/tu/201508/0005/1.jpg
             * typeName : 性感女郎梦娜Vanessa浴室湿身照
             */

            private String imageId;
            private String imageUrl;
            private String typeName;

            public String getImageId() {
                return imageId;
            }

            public void setImageId(String imageId) {
                this.imageId = imageId;
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
        public static class VideoListBean implements Serializable  {
            /**
             * userId : 34
             * userName : 小清新
             * avatar : http://t2.hddhhn.com/uploads/tu/201708/9999/00a28c6c34.jpg
             * typeName : 女神
             * video
             */

            private String userId;
            private String userName;
            private String avatar;
            private String typeName;
            private String video;

            public String getVideo() {
                return video;
            }

            public void setVideo(String video) {
                this.video = video;
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

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
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
