package com.xlkj.beautifulpicturehouse.common.constant;

import com.xlkj.beautifulpicturehouse.common.manager.videoScanner.VideoInfo;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomeHeadResBean;
import com.xlkj.beautifulpicturehouse.module.mine.bean.MyCollectionResBean;
import com.xlkj.beautifulpicturehouse.module.mine.bean.MyFollowResBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.HotVideoListResBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoDetailResBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoListResBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/12/27.
 * 存储全局临时变量
 */

public class BeautyContent implements Serializable {
    //微信登录第一步 官网返回的code
    private static String code;

    //首页头部的tagList
    private static List<HomeHeadResBean.DataBean.TagListBean> tagList;

    //视频详情要传的bean
    private static VideoListResBean.DataBean.TypeListBean contentBean;

    //本地图片列表到详情要传的data
    private static List<VideoInfo> videoInfo;

    //列表传到详情的tags
    private static List<String> tags;

    //视频详情页的bean数据
    private static VideoDetailResBean.DataBean videoDetailData;

    //我的关注图片数据
    private static List<MyCollectionResBean.DataBean.ImageListBean> imageList;

    //我的关注视频数据
    private static List<MyCollectionResBean.DataBean.VideoListBean> videoList;

    //我关注的标签数据
    private static List<MyFollowResBean.DataBean.TagListBean> followTagList;

    //我关注的波主数据
    private static List<MyFollowResBean.DataBean.UserListBean> followUserList;

    //视频详情的fragment中关注成功-需要在follow的fragment中显示状态
    private static boolean isDetailFollow;

    public static boolean isIsDetailFollow() {
        return isDetailFollow;
    }

    public static void setIsDetailFollow(boolean isDetailFollow) {
        BeautyContent.isDetailFollow = isDetailFollow;
    }

    public static List<MyFollowResBean.DataBean.TagListBean> getFollowTagList() {
        return followTagList;
    }

    public static void setFollowTagList(List<MyFollowResBean.DataBean.TagListBean> followTagList) {
        BeautyContent.followTagList = followTagList;
    }

    public static List<MyFollowResBean.DataBean.UserListBean> getFollowUserList() {
        return followUserList;
    }

    public static void setFollowUserList(List<MyFollowResBean.DataBean.UserListBean> followUserList) {
        BeautyContent.followUserList = followUserList;
    }

    public static List<MyCollectionResBean.DataBean.VideoListBean> getVideoList() {
        return videoList;
    }

    public static void setVideoList(List<MyCollectionResBean.DataBean.VideoListBean> videoList) {
        BeautyContent.videoList = videoList;
    }

    public static List<MyCollectionResBean.DataBean.ImageListBean> getImageList() {
        return imageList;
    }

    public static void setImageList(List<MyCollectionResBean.DataBean.ImageListBean> imageList) {
        BeautyContent.imageList = imageList;
    }

    public static VideoDetailResBean.DataBean getVideoDetailData() {
        return videoDetailData;
    }

    public static void setVideoDetailData(VideoDetailResBean.DataBean videoDetailData) {
        BeautyContent.videoDetailData = videoDetailData;
    }

    public static List<String> getTags() {
        return tags;
    }

    public static void setTags(List<String> tags) {
        BeautyContent.tags = tags;
    }

    public static List<VideoInfo> getVideoInfo() {
        return videoInfo;
    }

    public static void setVideoInfo(List<VideoInfo> videoInfo) {
        BeautyContent.videoInfo = videoInfo;
    }

    public static VideoListResBean.DataBean.TypeListBean getContentBean() {
        return contentBean;
    }

    public static void setContentBean(VideoListResBean.DataBean.TypeListBean contentBean) {
        BeautyContent.contentBean = contentBean;
    }

    public static List<HomeHeadResBean.DataBean.TagListBean> getTagList() {
        return tagList;
    }

    public static void setTagList(List<HomeHeadResBean.DataBean.TagListBean> tagList) {
        BeautyContent.tagList = tagList;
    }

    public static String getCode() {
        return code;
    }

    public static void setCode(String code) {
        BeautyContent.code = code;
    }
}
