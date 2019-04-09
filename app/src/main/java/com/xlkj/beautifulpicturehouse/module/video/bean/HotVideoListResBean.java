package com.xlkj.beautifulpicturehouse.module.video.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1 on 2017/9/30.
 * 最热视频列表
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HotVideoListResBean implements Serializable {
    /**
     * Header : {"Result":0,"Msg":"查询成功"}
     * Content : [{"id":"39","title":"视频滴滴","url":"http://video.jiecao.fm/8/18/%E5%A4%A7%E5%AD%A6.mp4","voice":"1","cover_url":"","size":"2.00","downs":"43","money":"0"},{"id":"37","title":"视频6","url":"http://video.jiecao.fm/8/18/%E5%A4%A7%E5%AD%A6.mp4","voice":"1","cover_url":"http%3A%2F%2F118.178.234.14%3A3612%2FPublic%2Fuploads%2Fiv%2F2017-10-09%2F59db213cddfa4.jpg","size":"11.00","downs":"23","money":"0"},{"id":"31","title":"视频6","url":"http://video.jiecao.fm/8/18/%E5%A4%A7%E5%AD%A6.mp4","voice":"1","cover_url":"http%3A%2F%2F118.178.234.14%3A3612%2FPublic%2Fuploads%2Fiv%2F2017-10-09%2F59db213cddfa4.jpg","size":"11.00","downs":"23","money":"0"},{"id":"25","title":"视频6","url":"http://video.jiecao.fm/8/18/%E5%A4%A7%E5%AD%A6.mp4","voice":"1","cover_url":"http%3A%2F%2F118.178.234.14%3A3612%2FPublic%2Fuploads%2Fiv%2F2017-10-09%2F59db213cddfa4.jpg","size":"11.00","downs":"23","money":"0"},{"id":"19","title":"视频6","url":"http://video.jiecao.fm/8/18/%E5%A4%A7%E5%AD%A6.mp4","voice":"1","cover_url":"http%3A%2F%2F118.178.234.14%3A3612%2FPublic%2Fuploads%2Fiv%2F2017-10-09%2F59db213cddfa4.jpg","size":"11.00","downs":"23","money":"0"},{"id":"13","title":"视频6","url":"http://video.jiecao.fm/8/18/%E5%A4%A7%E5%AD%A6.mp4","voice":"1","cover_url":"http%3A%2F%2F118.178.234.14%3A3610%2FUploads%2F2017-10-12%2F59decd0e2aa1c.png","size":"11.00","downs":"23","money":"0"},{"id":"7","title":"视频6","url":"http://video.jiecao.fm/8/18/%E5%A4%A7%E5%AD%A6.mp4","voice":"1","cover_url":"http%3A%2F%2F118.178.234.14%3A3610%2FUploads%2F2017-10-12%2F59deccf060223.png","size":"11.00","downs":"23","money":"0"},{"id":"290","title":"","url":"http://v1.dwstatic.com/zbshenqi/201708/25/video/3ad7a91ab1db9f59817c77f378f30000.mp4","voice":"1","cover_url":"zsyj-video-show.oss-cn-beijing.aliyuncs.com%2Fimg%2F3ad7a91ab1db9f59817c77f378f30000.jpg","size":"1.44","downs":"1","money":"0"},{"id":"289","title":"","url":"http://v1.dwstatic.com/desktop/201706/06/video/3ad7a9197b223659f92cafffb0ff0000.mp4","voice":"1","cover_url":"zsyj-video-show.oss-cn-beijing.aliyuncs.com%2Fimg%2F3ad7a9197b223659f92cafffb0ff0000.jpg","size":"13.19","downs":"1","money":"0"},{"id":"288","title":"","url":"http://v1.dwstatic.com/desktop/201708/21/video/3ad7a91951939a59ec8fce81cf810000.mp4","voice":"1","cover_url":"zsyj-video-show.oss-cn-beijing.aliyuncs.com%2Fimg%2F3ad7a91951939a59ec8fce81cf810000.jpg","size":"4.31","downs":"1","money":"0"}]
     * PageCount : 29
     */

    public HeaderBean Header;
    public int PageCount;
//    public List<ContentBean> Content;
    public ArrayList<ContentBean> Content;

    public HeaderBean getHeader() {
        return Header;
    }

    public void setHeader(HeaderBean Header) {
        this.Header = Header;
    }

    public int getPageCount() {
        return PageCount;
    }

    public void setPageCount(int PageCount) {
        this.PageCount = PageCount;
    }

    public List<ContentBean> getContent() {
        return Content;
    }

//    public void setContent(List<ContentBean> Content) {
    public void setContent(ArrayList<ContentBean> Content) {
        this.Content = Content;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class HeaderBean implements Serializable {
        /**
         * Result : 0
         * Msg : 查询成功
         */

        public int Result;
        public String Msg;

        public int getResult() {
            return Result;
        }

        public void setResult(int Result) {
            this.Result = Result;
        }

        public String getMsg() {
            return Msg;
        }

        public void setMsg(String Msg) {
            this.Msg = Msg;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ContentBean implements Serializable {
        /**
         * id : 39
         * title : 视频滴滴
         * url : http://video.jiecao.fm/8/18/%E5%A4%A7%E5%AD%A6.mp4
         * voice : 1
         * cover_url :
         * size : 2.00
         * downs : 43
         * money : 0
         */

        /**
         * 自己加的记录测出来的宽高
         */
        public int picWidth = -1;
        public int picHeight = -1;

        public String id;
        public String title;
        public String url;
        public String voice;
        public String cover_url;
        public String size;
        public String downs;
        public String money;
        public String short_url;
        public String time_long;

        public String getTime_long() {
            return time_long;
        }

        public void setTime_long(String time_long) {
            this.time_long = time_long;
        }

        public String getShort_url() {
            return short_url;
        }

        public void setShort_url(String short_url) {
            this.short_url = short_url;
        }

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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getVoice() {
            return voice;
        }

        public void setVoice(String voice) {
            this.voice = voice;
        }

        public String getCover_url() {
            return cover_url;
        }

        public void setCover_url(String cover_url) {
            this.cover_url = cover_url;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getDowns() {
            return downs;
        }

        public void setDowns(String downs) {
            this.downs = downs;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }
    }


    /**
     * Header : {"Result":0,"Msg":"查询成功"}
     * Content : [{"title":"我的显示器1","url":"http://video.jiecao.fm/8/18/å¤§å­¦.mp4","voice":"1","cover_url":"http://118.178.234.14:3612/Public/uploads/iv/2017-10-09/59db213cddfa4.jpg","size":"5.66","downs":"1","money":"50"},{"title":"我的显示器5","url":"http://video.jiecao.fm/8/18/å¤§å­¦.mp4","voice":"2","cover_url":"http://118.178.234.14:3612/Public/uploads/iv/2017-10-09/59db213cddfa4.jpg","size":"5.66","downs":"0","money":"0"},{"title":"我的显示器4","url":"http://video.jiecao.fm/8/18/å¤§å­¦.mp4","voice":"1","cover_url":"http://118.178.234.14:3612/Public/uploads/iv/2017-10-09/59db213cddfa4.jpg","size":"5.66","downs":"0","money":"101"},{"title":"我的显示器3","url":"http://video.jiecao.fm/8/18/å¤§å­¦.mp4","voice":"1","cover_url":"http://118.178.234.14:3612/Public/uploads/iv/2017-10-09/59db213cddfa4.jpg","size":"5.66","downs":"0","money":"80"},{"title":"我的显示器2","url":"http://video.jiecao.fm/8/18/å¤§å­¦.mp4","voice":"1","cover_url":"http://118.178.234.14:3612/Public/uploads/iv/2017-10-09/59db213cddfa4.jpg","size":"5.66","downs":"0","money":"50"}]
     */

//    public HeaderBean Header;
//    public List<ContentBean> Content;
//
//    public HeaderBean getHeader() {
//        return Header;
//    }
//
//    public void setHeader(HeaderBean Header) {
//        this.Header = Header;
//    }
//
//    public List<ContentBean> getContent() {
//        return Content;
//    }
//
//    public void setContent(List<ContentBean> Content) {
//        this.Content = Content;
//    }
//
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public static class HeaderBean implements Serializable {
//        /**
//         * Result : 0
//         * Msg : 查询成功
//         */
//
//        static int Result;
//        static String Msg;
//
//        public int getResult() {
//            return Result;
//        }
//
//        public void setResult(int Result) {
//            this.Result = Result;
//        }
//
//        public String getMsg() {
//            return Msg;
//        }
//
//        public void setMsg(String Msg) {
//            this.Msg = Msg;
//        }
//    }
//
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public static class ContentBean implements Serializable {
//
//        /**
//         * 自己加的记录测出来的宽高
//         */
//        public int picWidth = -1;
//        public int picHeight = -1;
//
//        /**
//         * title : 我的显示器1
//         * url : http://video.jiecao.fm/8/18/å¤§å­¦.mp4
//         * voice : 1
//         * cover_url : http://118.178.234.14:3612/Public/uploads/iv/2017-10-09/59db213cddfa4.jpg
//         * size : 5.66
//         * downs : 1
//         * money : 50
//         */
//
//        public String id;
//        public String title;
//        public String url;
//        public String voice;
//        public String cover_url;
//        public String size;
//        public String downs;
//        public String money;
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//        public String getTitle() {
//            return title;
//        }
//
//        public void setTitle(String title) {
//            this.title = title;
//        }
//
//        public String getUrl() {
//            return url;
//        }
//
//        public void setUrl(String url) {
//            this.url = url;
//        }
//
//        public String getVoice() {
//            return voice;
//        }
//
//        public void setVoice(String voice) {
//            this.voice = voice;
//        }
//
//        public String getCover_url() {
//            return cover_url;
//        }
//
//        public void setCover_url(String cover_url) {
//            this.cover_url = cover_url;
//        }
//
//        public String getSize() {
//            return size;
//        }
//
//        public void setSize(String size) {
//            this.size = size;
//        }
//
//        public String getDowns() {
//            return downs;
//        }
//
//        public void setDowns(String downs) {
//            this.downs = downs;
//        }
//
//        public String getMoney() {
//            return money;
//        }
//
//        public void setMoney(String money) {
//            this.money = money;
//        }
//    }


}
