package com.xlkj.beautifulpicturehouse.module.home.contract;

import com.xlkj.beautifulpicturehouse.module.home.bean.AboutPicReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.AboutPicResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.AboutTagReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.AboutTagResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.CollectionReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.CollectionResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.CommentListReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.CommentListResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.CommentSubmitReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.CommentSubmitResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.ConfigResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.DefSearchResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.FollowReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.FollowResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.GoodDetailReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.GoodDetailResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomeHeadResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomeHotNewResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomeHotPicResMgqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomePicGoodChoiceReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomePicGoodChoiceResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.HotListPicReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomeHotPicResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.SearchReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.SearchResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.WeatherBean;

/**
 * Created by Administrator on 2017/12/20.
 * 首页接口协议类
 */

public class HomeContract {
    //---------------------------进入APP请求配置文件部分----------------------------------
    public interface IConfigModel {
        void loadDataConfig(String action);
    }

    //---------------------------头部banner部分----------------------------------
    public interface ITestGetModel {
        void loadData(String action,OnTestGetListener listener);
        void interruptHttp();
    }

    public interface ITestGetView {
        void showError();
        void setData(HomeHeadResBean bean);
    }

    public interface ITestGetPresenter {
        void attach(ITestGetView view);
        void getData(String action);
        void detach();
    }

    public interface OnTestGetListener {
        //请求失败
        void onError();
        //请求成功
        void onRequestSuccess(HomeHeadResBean bean);
    }

    //---------------------------热门 最新 图片列表 部分----------------------------------
    public interface IHotPicListModel {
//        void loadDataHotPicList(HotListPicReqBean bean,OnHotPicListListener listener);
        void loadDataHotPicList(HomePicGoodChoiceReqBean bean,OnHotPicListListener listener);
        void interruptHttpHotPicList();
    }

    public interface IHotPicListView {
        void showErrorHotPicList();
        void setDataHotPicList(HomeHotNewResBean bean);
    }

    public interface IHotPicListPresenter {
        void attachHotPicList(IHotPicListView view);
        void getDataHotPicList(HomePicGoodChoiceReqBean bean);
        void detachHotPicList();
    }

    public interface OnHotPicListListener {
        void onErrorHotPicList();
        void onRequestSuccessHotPicList(HomeHotNewResBean bean);
    }

    //---------------------------精选 部分----------------------------------
    public interface IGoodChoiceModel {
        void loadDataGoodChoice(HomePicGoodChoiceReqBean bean, OnGoodChoiceListener listener);
        void interruptGoodChoice();
    }

    public interface IGoodChoiceView {
        void showErrorGoodChoice();
        void setDataGoodChoice(HomePicGoodChoiceResBean bean);
    }

    public interface IGoodchoicePresenter {
        void attachGoodChoice(IGoodChoiceView view);
        void getDataGoodChoice(HomePicGoodChoiceReqBean bean);
        void detachGoodChoice();
    }

    public interface OnGoodChoiceListener {
        void onErrorGoodChoice();
        void onRequestSuccessGoodChoice(HomePicGoodChoiceResBean bean);
    }

    //---------------------------搜索（包括首页和视频的搜索） 部分----------------------------------
    public interface ISearchModel {
        void loadDataSearch(SearchReqBean bean, OnSearchListener listener);
        void interruptSearch();
    }

    public interface ISearchView {
        void showErrorSearch();
        void setDataSearch(SearchResBean bean);
    }

    public interface ISearchPresenter {
        void attachSearch(ISearchView view);
        void getDataSearch(SearchReqBean bean);
        void detachSearch();
    }

    public interface OnSearchListener {
        void onErrorSearch();
        void onRequestSuccessSearch(SearchResBean bean);
    }

    //---------------------------默认搜索列表 部分----------------------------------
    public interface IDefSearchModel {
        void loadDataDefSearch(String action, OnDefSearchListener listener);
        void interruptDefSearch();
    }

    public interface IDefSearchView {
        void showErrorDefSearch();
        void setDataDefSearch(DefSearchResBean bean);
    }

    public interface IDefSearchPresenter {
        void attachDefSearch(IDefSearchView view);
        void getDataDefSearch(String action);
        void detachDefSearch();
    }

    public interface OnDefSearchListener {
        void onErrorDefSearch();
        void onRequestSuccessDefSearch(DefSearchResBean bean);
    }

    //---------------------------精选详情 部分----------------------------------
    public interface IGoodDetailModel {
        void loadDataGoodDetail(GoodDetailReqBean bean, OnGoodDetailListener listener);
        void interruptGoodDetail();
    }

    public interface IGoodDetailView {
        void showErrorGoodDetail();
        void setDataGoodDetail(GoodDetailResBean bean);
    }

    public interface IGoodDetailPresenter {
        void attachGoodDetail(IGoodDetailView view);
        void getDataGoodDetail(GoodDetailReqBean bean);
        void detachGoodDetail();
    }

    public interface OnGoodDetailListener {
        void onErrorGoodDetail();
        void onRequestSuccessGoodDetail(GoodDetailResBean bean);
    }

    //---------------------------收藏或者取消收藏 部分----------------------------------
    public interface ICollectionModel {
        void loadDataCollection(CollectionReqBean bean, OnCollectionListener listener);
        void interruptCollection();
    }

    public interface ICollectionView {
        void showErrorCollection();
        void setDataCollection(CollectionResBean bean);
    }

    public interface ICollectionPresenter {
        void attachCollection(ICollectionView view);
        void getDataCollection(CollectionReqBean bean);
        void detachCollection();
    }

    public interface OnCollectionListener {
        void onErrorCollection();
        void onRequestSuccessCollection(CollectionResBean bean);
    }

    //---------------------------关注或取消关注 部分----------------------------------
    public interface IFollowModel {
        void loadDataFollow(FollowReqBean bean, OnFollowListener listener);
        void interruptFollow();
    }

    public interface IFollowView {
        void showErrorFollow();
        void setDataFollow(FollowResBean bean);
    }

    public interface IFollowPresenter {
        void attachFollow(IFollowView view);
        void getDataFollow(FollowReqBean bean);
        void detachFollow();
    }

    public interface OnFollowListener {
        void onErrorFollow();
        void onRequestSuccessFollow(FollowResBean bean);
    }

    //---------------------------相关美图 部分----------------------------------
    public interface IAboutPicModel {
        void loadDataAboutPic(AboutPicReqBean bean, OnAboutPicListener listener);
        void interruptAboutPic();
    }

    public interface IAboutPicView {
        void showErrorAboutPic();
        void setDataAboutPic(AboutPicResBean bean);
    }

    public interface IAboutPicPresenter {
        void attachAboutPic(IAboutPicView view);
        void getDataAboutPic(AboutPicReqBean bean);
        void detachAboutPic();
    }

    public interface OnAboutPicListener {
        void onErrorAboutPic();
        void onRequestSuccessAboutPic(AboutPicResBean bean);
    }

    //---------------------------相关标签 部分----------------------------------
    public interface IAboutTagModel {
        void loadDataAboutTag(AboutTagReqBean bean, OnAboutTagListener listener);
        void interruptAboutTag();
    }

    public interface IAboutTagView {
        void showErrorAboutTag();
        void setDataAboutTag(AboutTagResBean bean);
    }

    public interface IAboutTagPresenter {
        void attachAboutTag(IAboutTagView view);
        void getDataAboutTag(AboutTagReqBean bean);
        void detachAboutTag();
    }

    public interface OnAboutTagListener {
        void onErrorAboutTag();
        void onRequestSuccessAboutTag(AboutTagResBean bean);
    }

    //---------------------------提交评论 部分----------------------------------
    public interface ICommentSubmitModel {
        void loadDataCommentSubmit(CommentSubmitReqBean bean, OnCommentSubmitListener listener);
        void interruptCommentSubmit();
    }

    public interface ICommentSubmitView {
        void showErrorCommentSubmit();
        void setDataCommentSubmit(CommentSubmitResBean bean);
    }

    public interface ICommentSubmitPresenter {
        void attachCommentSubmit(ICommentSubmitView view);
        void getDataCommentSubmit(CommentSubmitReqBean bean);
        void detachCommentSubmit();
    }

    public interface OnCommentSubmitListener {
        void onErrorCommentSubmit();
        void onRequestSuccessCommentSubmit(CommentSubmitResBean bean);
    }

    //---------------------------获取评论列表 部分----------------------------------
    public interface ICommentListModel {
        void loadDataCommentList(CommentListReqBean bean, OnCommentListListener listener);
        void interruptCommentList();
    }

    public interface ICommentListView {
        void showErrorCommentList();
        void setDataCommentList(CommentListResBean bean);
    }

    public interface ICommentListPresenter {
        void attachCommentList(ICommentListView view);
        void getDataCommentList(CommentListReqBean bean);
        void detachCommentList();
    }

    public interface OnCommentListListener {
        void onErrorCommentList();
        void onRequestSuccessCommentList(CommentListResBean bean);
    }

    //---------------------------XXOO 部分----------------------------------
}
