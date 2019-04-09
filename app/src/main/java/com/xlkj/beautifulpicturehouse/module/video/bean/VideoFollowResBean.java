package com.xlkj.beautifulpicturehouse.module.video.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/3.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoFollowResBean implements Serializable {

    /**
     * ret : 200
     * data : {"code":0,"msg":"主播详情获取成功","info_size":20,"info":{"id":"1180","nickname":"维纳斯的三女儿","thumb":"http://s.3987.com/uploadfile/2017/0930/2017093013394153964.jpg","description":"","play_num":"14847","fansNum":93,"is_follow":true,"vedioNum":584,"vedio":[{"id":"3668","title":"妩媚妖娆黑色文胸美女","thumb":"http://s.3987.com/uploadfile/2018/0102/thumb_2018010214224146986.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2018/0102/thumb_2018010214224146986.jpg","hits":"0"},{"id":"3667","title":"妩媚热舞短裤美女","thumb":"http://s.3987.com/uploadfile/2018/0102/thumb_2018010214213637243.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2018/0102/thumb_2018010214213637243.jpg","hits":"0"},{"id":"3666","title":"透视肚兜性感美女","thumb":"http://s.3987.com/uploadfile/2018/0102/thumb_2018010214203036915.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2018/0102/thumb_2018010214203036915.jpg","hits":"0"},{"id":"3665","title":"妩媚性感身姿撩人贴墙蹲美女","thumb":"http://s.3987.com/uploadfile/2018/0102/5a4b246ce593f.png","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2018/0102/5a4b246ce593f.png","hits":"0"},{"id":"3662","title":"娇嫩白皙肌肤性感女司机","thumb":"http://s.3987.com/uploadfile/2018/0102/5a4b23217af68.png","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2018/0102/5a4b23217af68.png","hits":"3"},{"id":"3660","title":"白嫩肌肤性感比基尼美女","thumb":"http://s.3987.com/uploadfile/2018/0102/thumb_2018010214103273774.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2018/0102/thumb_2018010214103273774.jpg","hits":"2"},{"id":"3659","title":"性感比基尼丰韵熟女","thumb":"http://s.3987.com/uploadfile/2018/0102/5a4b2207a9269.png","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2018/0102/5a4b2207a9269.png","hits":"3"},{"id":"3663","title":"大胸靓丽姐妹花","thumb":"http://s.3987.com/uploadfile/2018/0102/5a4b2391eb199.png","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2018/0102/5a4b2391eb199.png","hits":"1"},{"id":"3661","title":"酒店性感丽质美女","thumb":"http://s.3987.com/uploadfile/2018/0102/thumb_2018010214121509167.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2018/0102/thumb_2018010214121509167.jpg","hits":"0"},{"id":"3653","title":"靓丽性感比基尼美女2","thumb":"http://s.3987.com/uploadfile/2018/0102/thumb_2018010210512634284.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2018/0102/thumb_2018010210512634284.jpg","hits":"24"},{"id":"3652","title":"靓丽性感比基尼美女","thumb":"http://s.3987.com/uploadfile/2018/0102/5a4af3848c9ec.png","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2018/0102/5a4af3848c9ec.png","hits":"13"},{"id":"3649","title":"诱人身材尽显妩媚美女","thumb":"http://s.3987.com/uploadfile/2018/0102/5a4af20c3f506.png","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2018/0102/5a4af20c3f506.png","hits":"32"},{"id":"3648","title":"完美身材性感撩人美女","thumb":"http://s.3987.com/uploadfile/2018/0102/5a4af191e4874.png","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2018/0102/5a4af191e4874.png","hits":"11"},{"id":"3608","title":"白嫩美胸俏丽美人","thumb":"http://s.3987.com/uploadfile/2017/1229/5a45a2951ad26.png","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1229/5a45a2951ad26.png","hits":"120"},{"id":"3606","title":"露背长裙性感诱人美乳翘臀美女","thumb":"http://s.3987.com/uploadfile/2017/1229/thumb_2017122909595480899.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1229/thumb_2017122909595480899.jpg","hits":"13"},{"id":"3602","title":"露胸旗袍巨乳美女","thumb":"http://s.3987.com/uploadfile/2017/1229/thumb_2017122909532562504.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1229/thumb_2017122909532562504.jpg","hits":"13"},{"id":"3603","title":"傲人巨乳性感短裙美女","thumb":"http://s.3987.com/uploadfile/2017/1229/thumb_2017122909551835164.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1229/thumb_2017122909551835164.jpg","hits":"27"},{"id":"3601","title":"吊带红裙网袜诱惑性感美女","thumb":"http://s.3987.com/uploadfile/2017/1229/thumb_2017122909512825867.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1229/thumb_2017122909512825867.jpg","hits":"15"},{"id":"3600","title":"妖艳性感成熟御姐","thumb":"http://s.3987.com/uploadfile/2017/1229/thumb_2017122909471494399.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1229/thumb_2017122909471494399.jpg","hits":"12"},{"id":"3599","title":"丰姿韵味十足魅惑美女","thumb":"http://s.3987.com/uploadfile/2017/1229/thumb_2017122909454884507.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1229/thumb_2017122909454884507.jpg","hits":"11"},{"id":"3573","title":"丰姿成熟韵味御姐","thumb":"http://s.3987.com/uploadfile/2017/1228/5a4485cd8a720.png","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1228/5a4485cd8a720.png","hits":"70"},{"id":"3565","title":"妩媚撩人巨乳美女","thumb":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122813382347365.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122813382347365.jpg","hits":"21"},{"id":"3564","title":"喷张白皙巨乳可爱美女","thumb":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122813363705949.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122813363705949.jpg","hits":"15"},{"id":"3570","title":"性感露背皮裤翘臀美女2","thumb":"http://s.3987.com/uploadfile/2017/1228/5a4484d877298.png","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1228/5a4484d877298.png","hits":"30"},{"id":"3569","title":"性感露背皮裤翘臀美女","thumb":"http://s.3987.com/uploadfile/2017/1228/5a44848e49f93.png","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1228/5a44848e49f93.png","hits":"33"},{"id":"3567","title":"妖艳性感火辣美女","thumb":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122813405199055.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122813405199055.jpg","hits":"12"},{"id":"3568","title":"撩人姿势性感熟女","thumb":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122813421083216.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122813421083216.jpg","hits":"20"},{"id":"3566","title":"魅惑撩人心弦巨乳美女","thumb":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122813392444956.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122813392444956.jpg","hits":"41"},{"id":"3537","title":"鹅黄短裙妩媚贴墙蹲翘臀美女","thumb":"http://s.3987.com/uploadfile/2017/1227/thumb_2017122710553729280.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1227/thumb_2017122710553729280.jpg","hits":"65"},{"id":"3538","title":"妩媚撩人热舞翘臀美女","thumb":"http://s.3987.com/uploadfile/2017/1227/thumb_2017122710565931402.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1227/thumb_2017122710565931402.jpg","hits":"33"}]}}
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
         * info_size : 20
         * info : {"id":"1180","nickname":"维纳斯的三女儿","thumb":"http://s.3987.com/uploadfile/2017/0930/2017093013394153964.jpg","description":"","play_num":"14847","fansNum":93,"is_follow":true,"vedioNum":584,"vedio":[{"id":"3668","title":"妩媚妖娆黑色文胸美女","thumb":"http://s.3987.com/uploadfile/2018/0102/thumb_2018010214224146986.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2018/0102/thumb_2018010214224146986.jpg","hits":"0"},{"id":"3667","title":"妩媚热舞短裤美女","thumb":"http://s.3987.com/uploadfile/2018/0102/thumb_2018010214213637243.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2018/0102/thumb_2018010214213637243.jpg","hits":"0"},{"id":"3666","title":"透视肚兜性感美女","thumb":"http://s.3987.com/uploadfile/2018/0102/thumb_2018010214203036915.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2018/0102/thumb_2018010214203036915.jpg","hits":"0"},{"id":"3665","title":"妩媚性感身姿撩人贴墙蹲美女","thumb":"http://s.3987.com/uploadfile/2018/0102/5a4b246ce593f.png","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2018/0102/5a4b246ce593f.png","hits":"0"},{"id":"3662","title":"娇嫩白皙肌肤性感女司机","thumb":"http://s.3987.com/uploadfile/2018/0102/5a4b23217af68.png","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2018/0102/5a4b23217af68.png","hits":"3"},{"id":"3660","title":"白嫩肌肤性感比基尼美女","thumb":"http://s.3987.com/uploadfile/2018/0102/thumb_2018010214103273774.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2018/0102/thumb_2018010214103273774.jpg","hits":"2"},{"id":"3659","title":"性感比基尼丰韵熟女","thumb":"http://s.3987.com/uploadfile/2018/0102/5a4b2207a9269.png","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2018/0102/5a4b2207a9269.png","hits":"3"},{"id":"3663","title":"大胸靓丽姐妹花","thumb":"http://s.3987.com/uploadfile/2018/0102/5a4b2391eb199.png","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2018/0102/5a4b2391eb199.png","hits":"1"},{"id":"3661","title":"酒店性感丽质美女","thumb":"http://s.3987.com/uploadfile/2018/0102/thumb_2018010214121509167.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2018/0102/thumb_2018010214121509167.jpg","hits":"0"},{"id":"3653","title":"靓丽性感比基尼美女2","thumb":"http://s.3987.com/uploadfile/2018/0102/thumb_2018010210512634284.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2018/0102/thumb_2018010210512634284.jpg","hits":"24"},{"id":"3652","title":"靓丽性感比基尼美女","thumb":"http://s.3987.com/uploadfile/2018/0102/5a4af3848c9ec.png","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2018/0102/5a4af3848c9ec.png","hits":"13"},{"id":"3649","title":"诱人身材尽显妩媚美女","thumb":"http://s.3987.com/uploadfile/2018/0102/5a4af20c3f506.png","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2018/0102/5a4af20c3f506.png","hits":"32"},{"id":"3648","title":"完美身材性感撩人美女","thumb":"http://s.3987.com/uploadfile/2018/0102/5a4af191e4874.png","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2018/0102/5a4af191e4874.png","hits":"11"},{"id":"3608","title":"白嫩美胸俏丽美人","thumb":"http://s.3987.com/uploadfile/2017/1229/5a45a2951ad26.png","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1229/5a45a2951ad26.png","hits":"120"},{"id":"3606","title":"露背长裙性感诱人美乳翘臀美女","thumb":"http://s.3987.com/uploadfile/2017/1229/thumb_2017122909595480899.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1229/thumb_2017122909595480899.jpg","hits":"13"},{"id":"3602","title":"露胸旗袍巨乳美女","thumb":"http://s.3987.com/uploadfile/2017/1229/thumb_2017122909532562504.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1229/thumb_2017122909532562504.jpg","hits":"13"},{"id":"3603","title":"傲人巨乳性感短裙美女","thumb":"http://s.3987.com/uploadfile/2017/1229/thumb_2017122909551835164.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1229/thumb_2017122909551835164.jpg","hits":"27"},{"id":"3601","title":"吊带红裙网袜诱惑性感美女","thumb":"http://s.3987.com/uploadfile/2017/1229/thumb_2017122909512825867.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1229/thumb_2017122909512825867.jpg","hits":"15"},{"id":"3600","title":"妖艳性感成熟御姐","thumb":"http://s.3987.com/uploadfile/2017/1229/thumb_2017122909471494399.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1229/thumb_2017122909471494399.jpg","hits":"12"},{"id":"3599","title":"丰姿韵味十足魅惑美女","thumb":"http://s.3987.com/uploadfile/2017/1229/thumb_2017122909454884507.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1229/thumb_2017122909454884507.jpg","hits":"11"},{"id":"3573","title":"丰姿成熟韵味御姐","thumb":"http://s.3987.com/uploadfile/2017/1228/5a4485cd8a720.png","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1228/5a4485cd8a720.png","hits":"70"},{"id":"3565","title":"妩媚撩人巨乳美女","thumb":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122813382347365.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122813382347365.jpg","hits":"21"},{"id":"3564","title":"喷张白皙巨乳可爱美女","thumb":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122813363705949.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122813363705949.jpg","hits":"15"},{"id":"3570","title":"性感露背皮裤翘臀美女2","thumb":"http://s.3987.com/uploadfile/2017/1228/5a4484d877298.png","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1228/5a4484d877298.png","hits":"30"},{"id":"3569","title":"性感露背皮裤翘臀美女","thumb":"http://s.3987.com/uploadfile/2017/1228/5a44848e49f93.png","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1228/5a44848e49f93.png","hits":"33"},{"id":"3567","title":"妖艳性感火辣美女","thumb":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122813405199055.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122813405199055.jpg","hits":"12"},{"id":"3568","title":"撩人姿势性感熟女","thumb":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122813421083216.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122813421083216.jpg","hits":"20"},{"id":"3566","title":"魅惑撩人心弦巨乳美女","thumb":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122813392444956.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122813392444956.jpg","hits":"41"},{"id":"3537","title":"鹅黄短裙妩媚贴墙蹲翘臀美女","thumb":"http://s.3987.com/uploadfile/2017/1227/thumb_2017122710553729280.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1227/thumb_2017122710553729280.jpg","hits":"65"},{"id":"3538","title":"妩媚撩人热舞翘臀美女","thumb":"http://s.3987.com/uploadfile/2017/1227/thumb_2017122710565931402.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1227/thumb_2017122710565931402.jpg","hits":"33"}]}
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
        public static class InfoBean implements Serializable{
            /**
             * id : 1180
             * nickname : 维纳斯的三女儿
             * thumb : http://s.3987.com/uploadfile/2017/0930/2017093013394153964.jpg
             * description :
             * play_num : 14847
             * fansNum : 93
             * is_follow : true
             * vedioNum : 584
             * vedio : [{"id":"3668","title":"妩媚妖娆黑色文胸美女","thumb":"http://s.3987.com/uploadfile/2018/0102/thumb_2018010214224146986.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2018/0102/thumb_2018010214224146986.jpg","hits":"0"},{"id":"3667","title":"妩媚热舞短裤美女","thumb":"http://s.3987.com/uploadfile/2018/0102/thumb_2018010214213637243.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2018/0102/thumb_2018010214213637243.jpg","hits":"0"},{"id":"3666","title":"透视肚兜性感美女","thumb":"http://s.3987.com/uploadfile/2018/0102/thumb_2018010214203036915.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2018/0102/thumb_2018010214203036915.jpg","hits":"0"},{"id":"3665","title":"妩媚性感身姿撩人贴墙蹲美女","thumb":"http://s.3987.com/uploadfile/2018/0102/5a4b246ce593f.png","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2018/0102/5a4b246ce593f.png","hits":"0"},{"id":"3662","title":"娇嫩白皙肌肤性感女司机","thumb":"http://s.3987.com/uploadfile/2018/0102/5a4b23217af68.png","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2018/0102/5a4b23217af68.png","hits":"3"},{"id":"3660","title":"白嫩肌肤性感比基尼美女","thumb":"http://s.3987.com/uploadfile/2018/0102/thumb_2018010214103273774.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2018/0102/thumb_2018010214103273774.jpg","hits":"2"},{"id":"3659","title":"性感比基尼丰韵熟女","thumb":"http://s.3987.com/uploadfile/2018/0102/5a4b2207a9269.png","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2018/0102/5a4b2207a9269.png","hits":"3"},{"id":"3663","title":"大胸靓丽姐妹花","thumb":"http://s.3987.com/uploadfile/2018/0102/5a4b2391eb199.png","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2018/0102/5a4b2391eb199.png","hits":"1"},{"id":"3661","title":"酒店性感丽质美女","thumb":"http://s.3987.com/uploadfile/2018/0102/thumb_2018010214121509167.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2018/0102/thumb_2018010214121509167.jpg","hits":"0"},{"id":"3653","title":"靓丽性感比基尼美女2","thumb":"http://s.3987.com/uploadfile/2018/0102/thumb_2018010210512634284.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2018/0102/thumb_2018010210512634284.jpg","hits":"24"},{"id":"3652","title":"靓丽性感比基尼美女","thumb":"http://s.3987.com/uploadfile/2018/0102/5a4af3848c9ec.png","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2018/0102/5a4af3848c9ec.png","hits":"13"},{"id":"3649","title":"诱人身材尽显妩媚美女","thumb":"http://s.3987.com/uploadfile/2018/0102/5a4af20c3f506.png","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2018/0102/5a4af20c3f506.png","hits":"32"},{"id":"3648","title":"完美身材性感撩人美女","thumb":"http://s.3987.com/uploadfile/2018/0102/5a4af191e4874.png","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2018/0102/5a4af191e4874.png","hits":"11"},{"id":"3608","title":"白嫩美胸俏丽美人","thumb":"http://s.3987.com/uploadfile/2017/1229/5a45a2951ad26.png","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1229/5a45a2951ad26.png","hits":"120"},{"id":"3606","title":"露背长裙性感诱人美乳翘臀美女","thumb":"http://s.3987.com/uploadfile/2017/1229/thumb_2017122909595480899.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1229/thumb_2017122909595480899.jpg","hits":"13"},{"id":"3602","title":"露胸旗袍巨乳美女","thumb":"http://s.3987.com/uploadfile/2017/1229/thumb_2017122909532562504.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1229/thumb_2017122909532562504.jpg","hits":"13"},{"id":"3603","title":"傲人巨乳性感短裙美女","thumb":"http://s.3987.com/uploadfile/2017/1229/thumb_2017122909551835164.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1229/thumb_2017122909551835164.jpg","hits":"27"},{"id":"3601","title":"吊带红裙网袜诱惑性感美女","thumb":"http://s.3987.com/uploadfile/2017/1229/thumb_2017122909512825867.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1229/thumb_2017122909512825867.jpg","hits":"15"},{"id":"3600","title":"妖艳性感成熟御姐","thumb":"http://s.3987.com/uploadfile/2017/1229/thumb_2017122909471494399.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1229/thumb_2017122909471494399.jpg","hits":"12"},{"id":"3599","title":"丰姿韵味十足魅惑美女","thumb":"http://s.3987.com/uploadfile/2017/1229/thumb_2017122909454884507.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1229/thumb_2017122909454884507.jpg","hits":"11"},{"id":"3573","title":"丰姿成熟韵味御姐","thumb":"http://s.3987.com/uploadfile/2017/1228/5a4485cd8a720.png","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1228/5a4485cd8a720.png","hits":"70"},{"id":"3565","title":"妩媚撩人巨乳美女","thumb":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122813382347365.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122813382347365.jpg","hits":"21"},{"id":"3564","title":"喷张白皙巨乳可爱美女","thumb":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122813363705949.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122813363705949.jpg","hits":"15"},{"id":"3570","title":"性感露背皮裤翘臀美女2","thumb":"http://s.3987.com/uploadfile/2017/1228/5a4484d877298.png","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1228/5a4484d877298.png","hits":"30"},{"id":"3569","title":"性感露背皮裤翘臀美女","thumb":"http://s.3987.com/uploadfile/2017/1228/5a44848e49f93.png","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1228/5a44848e49f93.png","hits":"33"},{"id":"3567","title":"妖艳性感火辣美女","thumb":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122813405199055.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122813405199055.jpg","hits":"12"},{"id":"3568","title":"撩人姿势性感熟女","thumb":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122813421083216.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122813421083216.jpg","hits":"20"},{"id":"3566","title":"魅惑撩人心弦巨乳美女","thumb":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122813392444956.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1228/thumb_2017122813392444956.jpg","hits":"41"},{"id":"3537","title":"鹅黄短裙妩媚贴墙蹲翘臀美女","thumb":"http://s.3987.com/uploadfile/2017/1227/thumb_2017122710553729280.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1227/thumb_2017122710553729280.jpg","hits":"65"},{"id":"3538","title":"妩媚撩人热舞翘臀美女","thumb":"http://s.3987.com/uploadfile/2017/1227/thumb_2017122710565931402.jpg","anchor_id":"1180","vedio":"http://s.3987.com/uploadfile/2017/1227/thumb_2017122710565931402.jpg","hits":"33"}]
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
                 * id : 3668
                 * title : 妩媚妖娆黑色文胸美女
                 * thumb : http://s.3987.com/uploadfile/2018/0102/thumb_2018010214224146986.jpg
                 * anchor_id : 1180
                 * vedio : http://s.3987.com/uploadfile/2018/0102/thumb_2018010214224146986.jpg
                 * hits : 0
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
