package com.xlkj.beautifulpicturehouse.module.video.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/10.
 * 视频头部返回数据bean
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoHeadResBean implements Serializable {

    /**
     * status : 1
     * msg :
     * data : {"bannerList":[{"imageUrl":"http://t2.hddhhn.com/uploads/tu/201710/41/0ed4095501.jpg","url":"5"},{"imageUrl":"http://t2.hddhhn.com/uploads/tu/201709/172/b3211f54e9.jpg","url":"6"},{"imageUrl":"http://t2.hddhhn.com/uploads/tu/201708/155/5f52638beb_1.jpg","url":"7"},{"imageUrl":"http://t1.27270.com/uploads/tu/201711/245/72c15a9d1e.jpg","url":"8"}],"tagList":[{"imageUrl":"http://t2.hddhhn.com/uploads/tu/201607/205/st6.png","tagName":"成熟","tagId":"8"},{"imageUrl":"http://t2.hddhhn.com/uploads/tu/201708/911/e7aac757e1.jpg","tagName":"可爱","tagId":"7"},{"imageUrl":"http://t1.27270.com/uploads/tu/201712/290/d8e62c5713.jpg","tagName":"蕾丝","tagId":"6"},{"imageUrl":"http://t1.27270.com/uploads/tu/201712/83/559ed3d59f.jpg","tagName":"制服","tagId":"5"},{"imageUrl":"http://t1.27270.com/uploads/tu/201712/264/85e23443a0.jpg","tagName":"黑丝","tagId":"4"},{"imageUrl":"http://t2.hddhhn.com/uploads/tu/201708/265/3551cd6bd3_1.jpg","tagName":"长裙","tagId":"3"},{"imageUrl":"http://t2.hddhhn.com/uploads/tu/201705/219/c12.jpg","tagName":"短裙","tagId":"2"},{"imageUrl":"http://t2.hddhhn.com/uploads/150730/8-150I0095542427.jpg","tagName":"短裤","tagId":"1"}],"showNumber":"8","typeList":[{"typeName":"热门","typeId":1},{"typeName":"最新","typeId":2}]}
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
         * bannerList : [{"imageUrl":"http://t2.hddhhn.com/uploads/tu/201710/41/0ed4095501.jpg","url":"5"},{"imageUrl":"http://t2.hddhhn.com/uploads/tu/201709/172/b3211f54e9.jpg","url":"6"},{"imageUrl":"http://t2.hddhhn.com/uploads/tu/201708/155/5f52638beb_1.jpg","url":"7"},{"imageUrl":"http://t1.27270.com/uploads/tu/201711/245/72c15a9d1e.jpg","url":"8"}]
         * tagList : [{"imageUrl":"http://t2.hddhhn.com/uploads/tu/201607/205/st6.png","tagName":"成熟","tagId":"8"},{"imageUrl":"http://t2.hddhhn.com/uploads/tu/201708/911/e7aac757e1.jpg","tagName":"可爱","tagId":"7"},{"imageUrl":"http://t1.27270.com/uploads/tu/201712/290/d8e62c5713.jpg","tagName":"蕾丝","tagId":"6"},{"imageUrl":"http://t1.27270.com/uploads/tu/201712/83/559ed3d59f.jpg","tagName":"制服","tagId":"5"},{"imageUrl":"http://t1.27270.com/uploads/tu/201712/264/85e23443a0.jpg","tagName":"黑丝","tagId":"4"},{"imageUrl":"http://t2.hddhhn.com/uploads/tu/201708/265/3551cd6bd3_1.jpg","tagName":"长裙","tagId":"3"},{"imageUrl":"http://t2.hddhhn.com/uploads/tu/201705/219/c12.jpg","tagName":"短裙","tagId":"2"},{"imageUrl":"http://t2.hddhhn.com/uploads/150730/8-150I0095542427.jpg","tagName":"短裤","tagId":"1"}]
         * showNumber : 8
         * typeList : [{"typeName":"热门","typeId":1},{"typeName":"最新","typeId":2}]
         */

        public String showNumber;
        public List<BannerListBean> bannerList;
        public List<TagListBean> tagList;
        public List<TypeListBean> typeList;

        public String getShowNumber() {
            return showNumber;
        }

        public void setShowNumber(String showNumber) {
            this.showNumber = showNumber;
        }

        public List<BannerListBean> getBannerList() {
            return bannerList;
        }

        public void setBannerList(List<BannerListBean> bannerList) {
            this.bannerList = bannerList;
        }

        public List<TagListBean> getTagList() {
            return tagList;
        }

        public void setTagList(List<TagListBean> tagList) {
            this.tagList = tagList;
        }

        public List<TypeListBean> getTypeList() {
            return typeList;
        }

        public void setTypeList(List<TypeListBean> typeList) {
            this.typeList = typeList;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class BannerListBean implements Serializable {
            /**
             * imageUrl : http://t2.hddhhn.com/uploads/tu/201710/41/0ed4095501.jpg
             * url : 5
             */

            public String imageUrl;
            public String url;
            public String typeName;

            public String getTypeName() {
                return typeName;
            }

            public void setTypeName(String typeName) {
                this.typeName = typeName;
            }

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class TagListBean implements Serializable {
            /**
             * imageUrl : http://t2.hddhhn.com/uploads/tu/201607/205/st6.png
             * tagName : 成熟
             * tagId : 8
             */

            public String imageUrl;
            public String tagName;
            public String tagId;

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }

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

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class TypeListBean implements Serializable{
            /**
             * typeName : 热门
             * typeId : 1
             */

            public String typeName;
            public int typeId;

            public String getTypeName() {
                return typeName;
            }

            public void setTypeName(String typeName) {
                this.typeName = typeName;
            }

            public int getTypeId() {
                return typeId;
            }

            public void setTypeId(int typeId) {
                this.typeId = typeId;
            }
        }
    }
}
