package com.xlkj.beautifulpicturehouse.module.home.apiservice;

import com.xlkj.beautifulpicturehouse.module.home.bean.AboutPicResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.AboutTagResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.CollectionResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.CommentListResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.CommentSubmitResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.ConfigResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.DefSearchResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.FollowResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.GoodDetailResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomeHeadResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomeHotNewResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomeHotPicResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomeHotPicResMgqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomePicGoodChoiceResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.SearchResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.WeatherBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoFollowResBean;

import java.util.PriorityQueue;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/12/20.
 */

public interface HomeApiService {
    /**
     * 请求配置文件
     * http://api.app.27270.com/api/index.php?action=getConfigInfo
     * @param action
     * @return
     */
    @GET("index.php")
    Call<ConfigResBean> requestConfig(@Query("action") String action);

    /**
     * 请求首页顶部数据接口
     * @param action
     * @return Call
     * http://api.app.27270.com/api/index.php?action=getInfoList
     */
    @GET("index.php")
//    Call<HomeHeadResBean> requestWeather(@Path("cityId") String cityId);
    Call<HomeHeadResBean> requestHomeHeadData(@Query("action") String action);

    /**
     * 请求默认搜索列表数据
     * http://api.app.27270.com/api/index.php?action=searchList
     * @param action
     * @return
     */
    @GET("index.php")
    Call<DefSearchResBean> requestDefSearch(@Query("action") String action);

    /**
     * 测试请求图片
     *  http://s.3987.com/beauty-api-1.1/Public/demo/index.php?canal=original&userId=5275&token=0e7a8963241611da87cb90d14cbf2a84&plat=38&service=VipPicture.VipNewPictureList&page=1&pageSize=20&check=b0fe804a86c9c15d9ce434f5a58ba018
     * @return
     */
    @GET("index.php")
    Call<HomeHotPicResMgqBean> requestPic(
            @Query("canal") String canal,
            @Query("userId") String userId,
            @Query("token") String token,
            @Query("plat") String plat,
            @Query("service") String service,
            @Query("page") String page,
            @Query("pageSize") String pageSize,
            @Query("check") String check
    );

    /**
     * 首页请求精选数据 该接口不适合热门等
     * http://api.app.27270.com/api/index.php?action=typeList&typeId=3
     * @param typeId
     * @param action
     * @param page
     * @return
     */
    @GET("index.php")
    Call<HomePicGoodChoiceResBean> requestGoodChoice(
            @Query("userId") String uId,
            @Query("typeId") String typeId,
            @Query("action") String action,
            @Query("page") String page
    );

    /**
     * 首页请求热门 最新
     * http://api.app.27270.com/api/index.php?action=typeList&typeId=1
     * http://api.app.27270.com/api/index.php?action=typeList&typeId=2
     * @param typeId
     * @param action
     * @param page
     * @return
     */
    @GET("index.php")
    Call<HomeHotNewResBean> requestHotNew(
            @Query("userId") String uId,
            @Query("typeId") String typeId,
            @Query("action") String action,
            @Query("page") String page
    );

    /**
     * 搜索：点击输入文字搜索
     * http://api.app.27270.com/api/index.php?action=search&searchKey=女神&page=1
     * @param action
     * @param searchKey
     * @param page
     * @return
     */
    @GET("index.php")
    Call<SearchResBean> requestSearch(
            @Query("userId") String userId,
            @Query("action") String action,
            @Query("searchKey") String searchKey,
            @Query("typeVideo") String typeVideo,
            @Query("page") String page
    );

    /**
     * 带id的搜索
     * @param action
     * @param searchId
     * @param page
     * @return
     */
    @GET("index.php")
    Call<SearchResBean> requestSearchById(
            @Query("userId") String userId,
            @Query("action") String action,
            @Query("searchId") String searchId,
            @Query("typeVideo") String typeVideo,
            @Query("page") String page
    );

    /**
     * 收藏或者取消收藏
     * @param action
     * @param typeId
     * @param userId
     * @param isPicture
     * @param isCollect
     * @return
     */
    @GET("index.php")
    Call<CollectionResBean> requestCollection(
            @Query("action") String action,
            @Query("typeId") String typeId,
            @Query("userId") String userId,
            @Query("isPicture") String isPicture,
            @Query("isCollect") String isCollect
    );

    /**
     * 关注或取消关注
     * @param action
     * @param followId
     * @param userId
     * @param isFollow
     * @param type
     * @return
     */
    @GET("index.php")
    Call<FollowResBean> requestFollow(
            @Query("action") String action,
            @Query("followId") String followId,
            @Query("userId") String userId,
            @Query("isFollow") String isFollow,
            @Query("type") String type
    );

    /**
     * 相关美图
     * http://api.app.27270.com/api/index.php?action=relatedPictureList&imageId=221292
     * @param action
     * @param imageId
     * @param userId
     * @return
     */
    @GET("index.php")
    Call<AboutPicResBean> requestAboutPic(
            @Query("action") String action,
            @Query("imageId") String imageId,
            @Query("userId") String userId
    );

    /**
     * 请求相关标签
     * http://api.app.27270.com/api/index.php?action=showtags&typeId=168657_0&userId=1
     * @param action
     * @param typeId
     * @param userId
     * @return
     */
    @GET("index.php")
    Call<AboutTagResBean> requestAboutTag(
            @Query("action") String action,
            @Query("typeId") String typeId,
            @Query("userId") String userId
    );

    /**
     * 精选详情页
     * http://api.app.27270.com/api/index.php?action=selectedList&imageId=230035&userId=1
     * @param action
     * @param imageId
     * @param userId
     * @return
     */
    @GET("index.php")
    Call<GoodDetailResBean> requestGoodDetail(
            @Query("action") String action,
            @Query("imageId") String imageId,
            @Query("followId") String followId,
            @Query("userId") String userId
    );

    /**
     * 提交评论
     * http://api.app.27270.com/api/index.php?action=comment&userId=1&typeId=169781&isPicture=0&content=%E2%80%9C%E5%BE%88%E5%8E%89%E5%AE%B3%E2%80%9D
     * @param action
     * @param userId
     * @param typeId
     * @param isPicture
     * @param content
     * @return
     */
    @GET("index.php")
    Call<CommentSubmitResBean> requestCommentSubmit(
            @Query("action") String action,
            @Query("userId") String userId,
            @Query("typeId") String typeId,
            @Query("isPicture") String isPicture,
            @Query("content") String content
    );

    /**
     * 获取评论列表
     * http://api.app.27270.com/api/index.php?action=commentList&typeId=169781&isPicture=0&page=1
     * @param action
     * @param typeId
     * @param ispicture
     * @param page
     * @return
     */
    @GET("index.php")
    Call<CommentListResBean> requestCpmmentList(
            @Query("action") String action,
            @Query("typeId") String typeId,
            @Query("isPicture") String ispicture,
            @Query("page") String page
    );

}
