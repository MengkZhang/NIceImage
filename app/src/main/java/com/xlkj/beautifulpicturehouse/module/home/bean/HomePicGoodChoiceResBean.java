package com.xlkj.beautifulpicturehouse.module.home.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/12/29.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class HomePicGoodChoiceResBean implements Serializable {

    /**
     * status : 1
     * msg :
     * data : {"typeList":[{"imageUrl":"http://t1.27270.com/uploads/tu/201711/245/72c15a9d1e.jpg","imageId":"230035"},{"imageUrl":"http://t1.hddhhn.com/uploads/tu/201710/41/0ed4095501.jpg","imageId":"232865"},{"imageUrl":"http://t1.hddhhn.com/uploads/tu/201709/172/b3211f54e9.jpg","imageId":"225248"},{"imageUrl":"http://t1.hddhhn.com/uploads/tu/201708/864/b35a7a67072.jpg","imageId":"223643"},{"imageUrl":"http://t1.hddhhn.com/uploads/tu/201507/184/bb.jpg","imageId":"49193"},{"imageUrl":"http://t1.hddhhn.com/uploads/tu/201506/002/hdd.jpg","imageId":"90139"},{"imageUrl":"http://t1.hddhhn.com/uploads/tu/201501/505/hd.jpg","imageId":"7691"},{"imageUrl":"http://t1.hddhhn.com/uploads/tu/201501/502/hd.jpg","imageId":"7625"},{"imageUrl":"http://t1.hddhhn.com/uploads/tu/201412/536/hd.jpg","imageId":"7569"},{"imageUrl":"http://t1.hddhhn.com/uploads/tu/201412/080/hd.jpg","imageId":"7337"},{"imageUrl":"http://t1.hddhhn.com/uploads/tu/201412/008/hd.jpg","imageId":"7265"},{"imageUrl":"http://t1.hddhhn.com/uploads/tu/201411/158/hd.jpg","imageId":"7247"},{"imageUrl":"http://t1.hddhhn.com/uploads/tu/201411/119/hd.jpg","imageId":"7208"},{"imageUrl":"","imageId":"2461"}],"totalPage":1}
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
         * typeList : [{"imageUrl":"http://t1.27270.com/uploads/tu/201711/245/72c15a9d1e.jpg","imageId":"230035"},{"imageUrl":"http://t1.hddhhn.com/uploads/tu/201710/41/0ed4095501.jpg","imageId":"232865"},{"imageUrl":"http://t1.hddhhn.com/uploads/tu/201709/172/b3211f54e9.jpg","imageId":"225248"},{"imageUrl":"http://t1.hddhhn.com/uploads/tu/201708/864/b35a7a67072.jpg","imageId":"223643"},{"imageUrl":"http://t1.hddhhn.com/uploads/tu/201507/184/bb.jpg","imageId":"49193"},{"imageUrl":"http://t1.hddhhn.com/uploads/tu/201506/002/hdd.jpg","imageId":"90139"},{"imageUrl":"http://t1.hddhhn.com/uploads/tu/201501/505/hd.jpg","imageId":"7691"},{"imageUrl":"http://t1.hddhhn.com/uploads/tu/201501/502/hd.jpg","imageId":"7625"},{"imageUrl":"http://t1.hddhhn.com/uploads/tu/201412/536/hd.jpg","imageId":"7569"},{"imageUrl":"http://t1.hddhhn.com/uploads/tu/201412/080/hd.jpg","imageId":"7337"},{"imageUrl":"http://t1.hddhhn.com/uploads/tu/201412/008/hd.jpg","imageId":"7265"},{"imageUrl":"http://t1.hddhhn.com/uploads/tu/201411/158/hd.jpg","imageId":"7247"},{"imageUrl":"http://t1.hddhhn.com/uploads/tu/201411/119/hd.jpg","imageId":"7208"},{"imageUrl":"","imageId":"2461"}]
         * totalPage : 1
         */

        public int totalPage;
        public List<TypeListBean> typeList;

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public List<TypeListBean> getTypeList() {
            return typeList;
        }

        public void setTypeList(List<TypeListBean> typeList) {
            this.typeList = typeList;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class TypeListBean implements Serializable  {
            /**
             * imageUrl : http://t1.27270.com/uploads/tu/201711/245/72c15a9d1e.jpg
             * imageId : 230035
             */

            public String imageUrl;
            public String imageId;
            public String followId;

            public String getFollowId() {
                return followId;
            }

            public void setFollowId(String followId) {
                this.followId = followId;
            }

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }

            public String getImageId() {
                return imageId;
            }

            public void setImageId(String imageId) {
                this.imageId = imageId;
            }
        }
    }
}
