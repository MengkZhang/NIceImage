package com.xlkj.beautifulpicturehouse.module.video.contract;

import com.xlkj.beautifulpicturehouse.module.home.bean.AboutTagReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.AboutTagResBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.HotVideoListReqBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.HotVideoListResBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoAboutReqBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoAboutResBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoDetailReqBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoDetailResBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoFollowReqBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoFollowResBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoHeadResBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoListReqBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoListResBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoSearchReqBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoSearchResBean;

/**
 * Created by Administrator on 2017/12/22.
 * 视频协议类
 */

public class VideoContract {

    //----------------------------视频头部部分------------------------------
    public interface IVideoHeadModel {
        void loadDataVideoHead(String action,OnVideoHeadListener listener);
        void interruptVideoHead();
    }

    public interface IVideoHeadView {
        void showErrorVideoHead();
        void setDataVideoHead(VideoHeadResBean bean);
    }

    public interface IVideoHeadPresenter {
        void attachVideoHead(IVideoHeadView view);
        void getDataVideoHead(String action);
        void detachVideoHead();
    }

    public interface OnVideoHeadListener {
        void onErrorVideoHead();
        void onReqSuccessVideoHead(VideoHeadResBean bean);
    }

    //----------------------------视频列表部分（掌上愿景的post key-value请求以及加密方式 先保留 以后做参考）------------------------------
    public interface IHotVideoListModel {
        void loadDataHotVideoList(VideoListReqBean bean,OnHotVideoListListener listener);
        void interruptHotVideoList();
    }

    public interface IHotVideoListView {
        void showErrorHotVideoList();
        void setDataHotVideoList(VideoListResBean bean);
    }

    public interface IHotVideoListPresenter {
        void attachHotVideoList(IHotVideoListView view);
        void getDataHotVideoList(VideoListReqBean bean);
        void detachHotVideoList();
    }

    public interface OnHotVideoListListener {
        void onErrorHotVideoList();
        void onReqSuccessHotVideoList(VideoListResBean bean);
    }

    //----------------------------视频详情部分------------------------------
    public interface IVideoDetailModel {
        void loadDataVideoDetail(VideoDetailReqBean bean, OnVideoDetailListener listener);
        void interruptVideoDetail();
    }

    public interface IVideoDetailView {
        void showErrorVideoDetail();
        void setDataVideoDetail(VideoDetailResBean bean);
    }

    public interface IVideoDetailPresenter {
        void attachVideoDetail(IVideoDetailView view);
        void getDataVideoDetail(VideoDetailReqBean bean);
        void detachVideoDetail();
    }

    public interface OnVideoDetailListener {
        void onErrorVideoDetail();
        void onReqSuccessVideoDetail(VideoDetailResBean bean);
    }

    //----------------------------相关视频部分------------------------------
    public interface IVideoAboutModel {
        void loadDataVideoAbout(VideoAboutReqBean bean, OnVideoAboutListener listener);
        void interruptVideoAbout();
    }

    public interface IVideoAboutView {
        void showErrorVideoAbout();
        void setDataVideoAbout(VideoAboutResBean bean);
    }

    public interface IVideoAboutPresenter {
        void attachVideoAbout(IVideoAboutView view);
        void getDataVideoAbout(VideoAboutReqBean bean);
        void detachVideoAbout();
    }

    public interface OnVideoAboutListener {
        void onErrorVideoAbout();
        void onReqSuccessVideoAbout(VideoAboutResBean bean);
    }

    //----------------------------视频关注 部分------------------------------
    public interface IVideoFollowModel {
        void loadDataVideoFollow(VideoFollowReqBean bean, OnVideoFollowListener listener);
        void interruptVideoFollow();
    }

    public interface IVideoFollowView {
        void showErrorVideoFollow();
        void setDataVideoFollow(VideoFollowResBean bean);
    }

    public interface IVideoFollowPresenter {
        void attachVideoFollow(IVideoFollowView view);
        void getDataVideoFollow(VideoFollowReqBean bean);
        void detachVideoFollow();
    }

    public interface OnVideoFollowListener {
        void onErrorVideoFollow();
        void onReqSuccessVideoFollow(VideoFollowResBean bean);
    }

    //----------------------------搜索视频更多部分------------------------------
    public interface IVideoSearchModel {
        void loadDataVideoSearch(VideoSearchReqBean bean, OnVideoSearchListener listener);
        void interruptVideoSearch();
    }

    public interface IVideoSearchView {
        void showErrorVideoSearch();
        void setDataVideoSearch(VideoSearchResBean bean);
    }

    public interface IVideoSearchPresenter {
        void attachVideoSearch(IVideoSearchView view);
        void getDataVideoSearch(VideoSearchReqBean bean);
        void detachVideoSearch();
    }

    public interface OnVideoSearchListener {
        void onErrorVideoSearch();
        void onReqSuccessVideoSearch(VideoSearchResBean bean);
    }

    //----------------------------OOXX部分------------------------------

}
