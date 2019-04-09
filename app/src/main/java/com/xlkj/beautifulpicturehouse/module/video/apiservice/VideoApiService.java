package com.xlkj.beautifulpicturehouse.module.video.apiservice;

import com.xlkj.beautifulpicturehouse.module.home.bean.AboutTagResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomeHotPicResMgqBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.HotVideoListResBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoAboutResBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoDetailResBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoFollowResBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoHeadResBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoListResBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoSearchResBean;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/12/22.
 */

public interface VideoApiService {
    /**
     * 上传文件
     * @param file
     * @return
     */
    @Multipart
    @POST("upload.php")
    Call<ResponseBody> upload(@Part("description") RequestBody description, @Part MultipartBody.Part file);

    /**
     * 上传key value的方式
     * @param postMap
     * @return
     */
    @FormUrlEncoded
    @POST("/interface/GetContent.php")
    Call<HotVideoListResBean> reqDataVideoList(@FieldMap Map<String, String> postMap);

    @FormUrlEncoded
    @POST("/interface/GetContent.php")
    Call<HotVideoListResBean> reqDataVideoList(@Field("pid") String pid,
                                               @Field("sid") String sid,
                                               @Field("version") String version,
                                               @Field("sver") String sver,
                                               @Field("noncestr") String noncestr,
                                               @Field("uuid") String uuid,
                                               @Field("timestamp") String timestamp,
                                               @Field("uid") String uid,
                                               @Field("token") String token,
                                               @Field("mid") String mid,
                                               @Field("page") String page,
                                               @Field("sign") String sign
                                               );

    /**
     * 请求相关视频
     * @param canal
     * @param userId
     * @param token
     * @param plat
     * @param service
     * @param anchor_id
     * @param page
     * @param pageSize
     * @param check
     * @return
     */
    @GET("index.php")
    Call<VideoAboutResBean> requestAboutVideo(
            @Query("canal") String canal,
            @Query("userId") String userId,
            @Query("token") String token,
            @Query("plat") String plat,
            @Query("service") String service,
            @Query("anchor_id") String anchor_id,
            @Query("page") String page,
            @Query("pageSize") String pageSize,
            @Query("check") String check
    );

    /**
     * 请求视频关注页：魅果圈数据
     * @param canal
     * @param userId
     * @param token
     * @param plat
     * @param service
     * @param anchor_id
     * @param page
     * @param pageSize
     * @param check
     * @return
     */
    @GET("index.php")
    Call<VideoFollowResBean> requestVideoFollow(
            @Query("canal") String canal,
            @Query("userId") String userId,
            @Query("token") String token,
            @Query("plat") String plat,
            @Query("service") String service,
            @Query("anchor_id") String anchor_id,
            @Query("page") String page,
            @Query("pageSize") String pageSize,
            @Query("check") String check
    );

    /**
     * 视频头部数据
     * http://api.app.27270.com/api/index.php?action=getVideoInfoList
     * @param action
     * @return
     */
    @GET("index.php")
    Call<VideoHeadResBean> requestVideoHead(
            @Query("action") String action
    );

    /**
     * 请求视频列表
     * http://api.app.27270.com/api/index.php?action=videoList&typeId=2&page=1&userId=1
     * @param action
     * @param typeId
     * @param page
     * @param userId
     * @return
     */
    @GET("index.php")
    Call<VideoListResBean> requestVideoList(
            @Query("action") String action,
            @Query("typeId") String typeId,
            @Query("page") String page,
            @Query("userId") String userId
    );

    /**
     * 请求视频详情页
     * http://api.app.27270.com/api/index.php?action=videoDetail&followId=34&userId=1&page=1
     * @param action
     * @param followId
     * @param userId
     * @return
     */
    @GET("index.php")
    Call<VideoDetailResBean> requestVideoDetail(
            @Query("action") String action,
            @Query("videoId") String videoId,
            @Query("followId") String followId,
            @Query("page") String page,
            @Query("userId") String userId
    );

    /**
     * 请求搜索更多视频(手动输入关键字)
     * http://api.app.27270.com/api/index.php?action=searchVideo&searchKey=%E5%A5%B3&page=1
     * @param action
     * @param videoId
     * @param page
     * @return
     */
    @GET("index.php")
    Call<VideoSearchResBean> requestVideoSearch(
            @Query("action") String action,
            @Query("searchKey") String videoId,
            @Query("page") String page
    );

    /**
     * 请求搜索更多视频(按照id搜索)
     * http://api.app.27270.com/api/index.php?action=searchVideo&searchId=7&page=1
     * @param action
     * @param searchId
     * @param page
     * @return
     */
    @GET("index.php")
    Call<VideoSearchResBean> requestVideoSearchById(
            @Query("action") String action,
            @Query("searchId") String searchId,
            @Query("page") String page
    );


}
