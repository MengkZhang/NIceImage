package com.xlkj.beautifulpicturehouse.module.video.model;

import android.util.Log;

import com.xlkj.beautifulpicturehouse.common.http.HostType;
import com.xlkj.beautifulpicturehouse.common.http.RetrofitSingleton;
import com.xlkj.beautifulpicturehouse.common.util.MD5Util;
import com.xlkj.beautifulpicturehouse.common.util.StringUtils;
import com.xlkj.beautifulpicturehouse.module.video.apiservice.VideoApiService;
import com.xlkj.beautifulpicturehouse.module.video.bean.HotVideoListReqBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.HotVideoListResBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoListReqBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoListResBean;
import com.xlkj.beautifulpicturehouse.module.video.contract.VideoContract;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2017/12/22.
 */

public class HotVideoListModelImpl implements VideoContract.IHotVideoListModel {
    public static final String TAG = "-->HotVideoListModelIm";
//    private static Map<String, String> map = new HashMap<>();
    private Call<VideoListResBean> mCall;

    @Override
    public void loadDataHotVideoList(VideoListReqBean bean, final VideoContract.OnHotVideoListListener listener) {
//        map.clear();
//        map.put("pid", bean.getPid());
//        map.put("sid", bean.getSid());
//        map.put("version", bean.getVersion());
//        map.put("sver", bean.getSver());
//        map.put("noncestr", bean.getNoncestr());
//        map.put("timestamp", bean.getTimestamp());
//        map.put("uuid", bean.getUuid());
//        map.put("uid", bean.getUid());
//        map.put("token", bean.getToken());
//        map.put("mid", bean.getMid());
//        map.put("page", bean.getPage());
//        //参数按照参数名ASCII码从小到大排序（字典序）
//        String formatUrlMap = StringUtils.formatUrlMap(map, true);
//        String sign = null;
//        try {
//            sign = MD5Util.getMD5("keyValue=" + "zsyj1234" + "&" + formatUrlMap).toUpperCase();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        map.put("sign", sign);

        Retrofit retrofit = RetrofitSingleton.getInstance(HostType.MTW_INFO).getRetrofit();
        VideoApiService apiService = retrofit.create(VideoApiService.class);
//        mCall = apiService.reqDataVideoList(map);
        mCall = apiService.requestVideoList(bean.getAction(),bean.getTypeId(),bean.getPage(),bean.getUserId());
        mCall.enqueue(new Callback<VideoListResBean>() {
            @Override
            public void onResponse(Call<VideoListResBean> call, Response<VideoListResBean> response) {
                if (response != null && response.body() != null) {
                    listener.onReqSuccessHotVideoList(response.body());
                } else {
                    listener.onErrorHotVideoList();
                }
            }

            @Override
            public void onFailure(Call<VideoListResBean> call, Throwable t) {
                Log.e(TAG,"请求失败："+t.getMessage());
                Log.e(TAG,"请求失败："+t.getLocalizedMessage());
                Log.e(TAG,"请求失败："+t.getCause());
                listener.onErrorHotVideoList();
            }
        });
    }

    @Override
    public void interruptHotVideoList() {
        if (mCall != null && !mCall.isCanceled()) {
            mCall.cancel();
        }
    }
}
