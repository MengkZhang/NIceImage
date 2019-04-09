package com.xlkj.beautifulpicturehouse.module.mine.apiservice;

import com.xlkj.beautifulpicturehouse.module.home.bean.AboutTagResBean;
import com.xlkj.beautifulpicturehouse.module.mine.bean.CheckUpdateResBean;
import com.xlkj.beautifulpicturehouse.module.mine.bean.GetTokenOpenIdBean;
import com.xlkj.beautifulpicturehouse.module.mine.bean.LoginResBean;
import com.xlkj.beautifulpicturehouse.module.mine.bean.MyCollectionResBean;
import com.xlkj.beautifulpicturehouse.module.mine.bean.MyFollowResBean;
import com.xlkj.beautifulpicturehouse.module.mine.bean.MyFootStayResBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/12/20.
 */

public interface MineApiService {

    /**
     * 微信登录第一次访问官方接口：通过code获取token和openid
     * 地址：https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
     * @return
     */
    @GET("access_token")
    Call<GetTokenOpenIdBean> requestFromCodeGetToeknOpenId(
            @Query("appid") String appid,
            @Query("secret") String secret,
            @Query("code") String code,
            @Query("grant_type") String grant_type
    );

    /**
     * APP服务器的登录接口
     * @param action
     * @param loginType
     * @param userName
     * @param avatarUrl
     * @param openId
     * @param gender
     * @param age
     * @return
     */
    @GET("index.php")
    Call<LoginResBean> requestAppLogin(
        @Query("action") String action,
        @Query("loginType") String loginType,
        @Query("userName") String userName,
        @Query("avatarUrl") String avatarUrl,
        @Query("openId") String openId,
        @Query("gender") String gender,
        @Query("age") String age
    );

    /**
     * 我的关注
     * http://api.app.27270.com/api/index.php?action=followList&userId=1
     * @param action
     * @param userId
     * @return
     */
    @GET("index.php")
    Call<MyFollowResBean> requestMyFollow(
            @Query("action") String action,
            @Query("userId") String userId
    );

    /**
     * 我的收藏
     * http://api.app.27270.com/api/index.php?action=collectionList&userId=1
     * @param action
     * @param userId
     * @return
     */
    @GET("index.php")
    Call<MyCollectionResBean> requestMyCollection(
            @Query("action") String action,
            @Query("userId") String userId
    );

    /**
     * 我的足迹
     * http://api.app.27270.com/api/index.php?action=historyList&userId=1
     * @param action
     * @param userId
     * @return
     */
    @GET("index.php")
    Call<MyFootStayResBean> requestMyFootStay(
            @Query("action") String action,
            @Query("userId") String userId
    );

    /**
     * 检测升级
     * http://api.app.27270.com/api/index.php?action=checkVersion&versionCode=1
     * @param action
     * @param versionCode
     * @return
     */
    @GET("index.php")
    Call<CheckUpdateResBean> requestUpdate(
            @Query("action") String action,
            @Query("versionCode") String versionCode
    );


}
