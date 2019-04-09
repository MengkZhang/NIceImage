package com.xlkj.beautifulpicturehouse.module.video.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/12/29.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoAboutResBean implements Serializable {

    /**
     * ret : 200
     * data : {"code":0,"msg":"主播详情获取成功","info_size":1,"info":{"id":"1214","nickname":"晓晓1995","thumb":"http://s.3987.com/uploadfile/2017/1208/2017120813404020030.jpg","description":"","play_num":"61","fansNum":2,"is_follow":false,"vedioNum":6,"vedio":[{"id":"3593","title":"白皙清纯美女2","thumb":"http://s.3987.com/uploadfile/2017/1229/5a45993c4ac65.png","anchor_id":"1214","vedio":"http://s.3987.com/uploadfile/2017/1229/5a45993c4ac65.png","hits":"3"},{"id":"3582","title":"性感妖娆连体衣美女","thumb":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122814031389697.jpg","anchor_id":"1214","vedio":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122814031389697.jpg","hits":"26"},{"id":"3583","title":"火辣性感车模","thumb":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122814045414558.jpg","anchor_id":"1214","vedio":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122814045414558.jpg","hits":"7"},{"id":"3580","title":"开叉长裙诱人豪乳气质美女","thumb":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122814005060951.jpg","anchor_id":"1214","vedio":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122814005060951.jpg","hits":"9"},{"id":"3073","title":"性感红裙妩媚车模","thumb":"http://s.3987.com/uploadfile/2017/1208/5a2a267203d1d.png","anchor_id":"1214","vedio":"http://s.3987.com/uploadfile/2017/1208/5a2a267203d1d.png","hits":"10"},{"id":"3072","title":"完美身材性感车模","thumb":"http://s.3987.com/uploadfile/2017/1208/5a2a25ee65c68.png","anchor_id":"1214","vedio":"http://s.3987.com/uploadfile/2017/1208/5a2a25ee65c68.png","hits":"6"}]}}
     * msg :
     */

    public int ret;
    public DataBean data;
    public String msg;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataBean implements Serializable {
        /**
         * code : 0
         * msg : 主播详情获取成功
         * info_size : 1
         * info : {"id":"1214","nickname":"晓晓1995","thumb":"http://s.3987.com/uploadfile/2017/1208/2017120813404020030.jpg","description":"","play_num":"61","fansNum":2,"is_follow":false,"vedioNum":6,"vedio":[{"id":"3593","title":"白皙清纯美女2","thumb":"http://s.3987.com/uploadfile/2017/1229/5a45993c4ac65.png","anchor_id":"1214","vedio":"http://s.3987.com/uploadfile/2017/1229/5a45993c4ac65.png","hits":"3"},{"id":"3582","title":"性感妖娆连体衣美女","thumb":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122814031389697.jpg","anchor_id":"1214","vedio":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122814031389697.jpg","hits":"26"},{"id":"3583","title":"火辣性感车模","thumb":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122814045414558.jpg","anchor_id":"1214","vedio":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122814045414558.jpg","hits":"7"},{"id":"3580","title":"开叉长裙诱人豪乳气质美女","thumb":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122814005060951.jpg","anchor_id":"1214","vedio":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122814005060951.jpg","hits":"9"},{"id":"3073","title":"性感红裙妩媚车模","thumb":"http://s.3987.com/uploadfile/2017/1208/5a2a267203d1d.png","anchor_id":"1214","vedio":"http://s.3987.com/uploadfile/2017/1208/5a2a267203d1d.png","hits":"10"},{"id":"3072","title":"完美身材性感车模","thumb":"http://s.3987.com/uploadfile/2017/1208/5a2a25ee65c68.png","anchor_id":"1214","vedio":"http://s.3987.com/uploadfile/2017/1208/5a2a25ee65c68.png","hits":"6"}]}
         */

        public int code;
        public String msg;
        public int info_size;
        public InfoBean info;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getInfo_size() {
            return info_size;
        }

        public void setInfo_size(int info_size) {
            this.info_size = info_size;
        }

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class InfoBean implements Serializable {
            /**
             * id : 1214
             * nickname : 晓晓1995
             * thumb : http://s.3987.com/uploadfile/2017/1208/2017120813404020030.jpg
             * description :
             * play_num : 61
             * fansNum : 2
             * is_follow : false
             * vedioNum : 6
             * vedio : [{"id":"3593","title":"白皙清纯美女2","thumb":"http://s.3987.com/uploadfile/2017/1229/5a45993c4ac65.png","anchor_id":"1214","vedio":"http://s.3987.com/uploadfile/2017/1229/5a45993c4ac65.png","hits":"3"},{"id":"3582","title":"性感妖娆连体衣美女","thumb":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122814031389697.jpg","anchor_id":"1214","vedio":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122814031389697.jpg","hits":"26"},{"id":"3583","title":"火辣性感车模","thumb":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122814045414558.jpg","anchor_id":"1214","vedio":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122814045414558.jpg","hits":"7"},{"id":"3580","title":"开叉长裙诱人豪乳气质美女","thumb":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122814005060951.jpg","anchor_id":"1214","vedio":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122814005060951.jpg","hits":"9"},{"id":"3073","title":"性感红裙妩媚车模","thumb":"http://s.3987.com/uploadfile/2017/1208/5a2a267203d1d.png","anchor_id":"1214","vedio":"http://s.3987.com/uploadfile/2017/1208/5a2a267203d1d.png","hits":"10"},{"id":"3072","title":"完美身材性感车模","thumb":"http://s.3987.com/uploadfile/2017/1208/5a2a25ee65c68.png","anchor_id":"1214","vedio":"http://s.3987.com/uploadfile/2017/1208/5a2a25ee65c68.png","hits":"6"}]
             */

            public String id;
            public String nickname;
            public String thumb;
            public String description;
            public String play_num;
            public int fansNum;
            public boolean is_follow;
            public int vedioNum;
            public List<VedioBean> vedio;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getPlay_num() {
                return play_num;
            }

            public void setPlay_num(String play_num) {
                this.play_num = play_num;
            }

            public int getFansNum() {
                return fansNum;
            }

            public void setFansNum(int fansNum) {
                this.fansNum = fansNum;
            }

            public boolean isIs_follow() {
                return is_follow;
            }

            public void setIs_follow(boolean is_follow) {
                this.is_follow = is_follow;
            }

            public int getVedioNum() {
                return vedioNum;
            }

            public void setVedioNum(int vedioNum) {
                this.vedioNum = vedioNum;
            }

            public List<VedioBean> getVedio() {
                return vedio;
            }

            public void setVedio(List<VedioBean> vedio) {
                this.vedio = vedio;
            }

            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class VedioBean implements Serializable{
                /**
                 * id : 3593
                 * title : 白皙清纯美女2
                 * thumb : http://s.3987.com/uploadfile/2017/1229/5a45993c4ac65.png
                 * anchor_id : 1214
                 * vedio : http://s.3987.com/uploadfile/2017/1229/5a45993c4ac65.png
                 * hits : 3
                 */

                public String id;
                public String title;
                public String thumb;
                public String anchor_id;
                public String vedio;
                public String hits;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getThumb() {
                    return thumb;
                }

                public void setThumb(String thumb) {
                    this.thumb = thumb;
                }

                public String getAnchor_id() {
                    return anchor_id;
                }

                public void setAnchor_id(String anchor_id) {
                    this.anchor_id = anchor_id;
                }

                public String getVedio() {
                    return vedio;
                }

                public void setVedio(String vedio) {
                    this.vedio = vedio;
                }

                public String getHits() {
                    return hits;
                }

                public void setHits(String hits) {
                    this.hits = hits;
                }
            }
        }
    }
}
