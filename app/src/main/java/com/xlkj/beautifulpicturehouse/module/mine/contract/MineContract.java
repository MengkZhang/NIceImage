package com.xlkj.beautifulpicturehouse.module.mine.contract;

import com.xlkj.beautifulpicturehouse.module.home.bean.AboutTagReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.AboutTagResBean;
import com.xlkj.beautifulpicturehouse.module.mine.bean.CheckUpdateReqBean;
import com.xlkj.beautifulpicturehouse.module.mine.bean.CheckUpdateResBean;
import com.xlkj.beautifulpicturehouse.module.mine.bean.GetTokenOpenIdBean;
import com.xlkj.beautifulpicturehouse.module.mine.bean.LoginReqBean;
import com.xlkj.beautifulpicturehouse.module.mine.bean.LoginResBean;
import com.xlkj.beautifulpicturehouse.module.mine.bean.MyCollectionReqBean;
import com.xlkj.beautifulpicturehouse.module.mine.bean.MyCollectionResBean;
import com.xlkj.beautifulpicturehouse.module.mine.bean.MyFollowResBean;
import com.xlkj.beautifulpicturehouse.module.mine.bean.MyFootStayReqBean;
import com.xlkj.beautifulpicturehouse.module.mine.bean.MyFootStayResBean;
import com.xlkj.beautifulpicturehouse.module.mine.bean.ReqGetTokenOpenIdBean;

/**
 * Created by Administrator on 2017/12/27.
 * 我的  协议类
 */

public class MineContract {
    //----------------------微信登录 用code获取token和openId-----------------------
    public interface IWXLogin1Model {
        void loadDataWXLogin1(ReqGetTokenOpenIdBean bean,OnWXLogin1Listener listener);
        void interruptWXLogin1();
    }

    public interface IWXLogin1View {
        void showErrorWXLogin1();
        void setDataWXLogin1(GetTokenOpenIdBean bean);
    }

    public interface IWXLogin1Presenter {
        void attachWXLogin1(IWXLogin1View view);
        void getDataWXLogin1(ReqGetTokenOpenIdBean bean);
        void detachWXLogin1();
    }

    public interface OnWXLogin1Listener {
        void onErrorWXLogin1();
        void onReqSuccessWXLogin1(GetTokenOpenIdBean bean);
    }


    //----------------------登录：APP服务器的login-----------------------
    public interface IAppLoginModel {
        void loadDataAppLogin(LoginReqBean bean, OnAppLoginListener listener);
        void interruptAppLogin();
    }

    public interface IAppLoginView {
        void showErrorAppLogin();
        void setDataAppLogin(LoginResBean bean);
    }

    public interface IAppLoginPresenter {
        void attachAppLogin(IAppLoginView view);
        void getDataAppLogin(LoginReqBean bean);
        void detachAppLogin();
    }

    public interface OnAppLoginListener {
        void onErrorAppLogin();
        void onReqSuccessAppLogin(LoginResBean bean);
    }

    //----------------------我的关注 部分-----------------------
    public interface IMyFollowModel {
        void loadDataMyFollow(MyCollectionReqBean bean, OnMyFollowListener listener);
        void interruptMyFollow();
    }

    public interface IMyFollowView {
        void showErrorMyFollow();
        void setDataMyFollow(MyFollowResBean bean);
    }

    public interface IMyFollowPresenter {
        void attachMyFollow(IMyFollowView view);
        void getDataMyFollow(MyCollectionReqBean bean);
        void detachMyFollow();
    }

    public interface OnMyFollowListener {
        void onErrorMyFollow();
        void onReqSuccessMyFollow(MyFollowResBean bean);
    }

    //----------------------我的收藏-----------------------
    public interface IMyCollectionModel {
        void loadDataCollection(MyCollectionReqBean bean, OnMyCollectionListener listener);
        void interruptMyCollection();
    }

    public interface IMyCollectionView {
        void showErrorMyCollection();
        void setDataMyCollection(MyCollectionResBean bean);
    }

    public interface IMyCollectionPresenter {
        void attachMyCollection(IMyCollectionView view);
        void getDataMyCollection(MyCollectionReqBean bean);
        void detachMyCollection();
    }

    public interface OnMyCollectionListener {
        void onErrorMyCollection();
        void onReqSuccessMyCollection(MyCollectionResBean bean);
    }

    //----------------------我的足迹-----------------------
    public interface IMyFootStayModel {
        void loadDataFootStay(MyFootStayReqBean bean, OnMyFootStayListener listener);
        void interruptMyFootStay();
    }

    public interface IMyFootStayView {
        void showErrorFootStay();
        void setDataFootStay(MyFootStayResBean bean);
    }

    public interface IMyFootStayPresenter {
        void attachMyFootStay(IMyFootStayView view);
        void getDataMyFootStay(MyFootStayReqBean bean);
        void detachMyFootStay();
    }

    public interface OnMyFootStayListener {
        void onErrorMyFootStay();
        void onReqSuccessMyFootStay(MyFootStayResBean bean);
    }

    //----------------------检测更新-----------------------
    // TODO: 2018/1/15 需要调整
    public interface IUpdateModel {
        void loadDataUpdate(CheckUpdateReqBean bean, OnUpdateListener listener);
        void interruptUpdate();
    }

    public interface IUpdateView {
        void showErrorUpdate();
        void setDataUpdate(CheckUpdateResBean bean);
    }

    public interface IUpdatePresenter {
        void attachUpdate(IUpdateView view);
        void getDataUpdate(CheckUpdateReqBean bean);
        void detachUpdate();
    }

    public interface OnUpdateListener {
        void onErrorUpdate();
        void onReqSuccessUpdate(CheckUpdateResBean bean);
    }

    //----------------------微信登录 用code获取token和openId-----------------------

}
