package com.xlkj.beautifulpicturehouse.module.video.model;

import android.util.Log;

import com.xlkj.beautifulpicturehouse.common.http.HostType;
import com.xlkj.beautifulpicturehouse.common.http.RetrofitSingleton;
import com.xlkj.beautifulpicturehouse.module.video.apiservice.VideoApiService;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoFollowReqBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoFollowResBean;
import com.xlkj.beautifulpicturehouse.module.video.contract.VideoContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2018/1/3.
 * 视频关注
 */

public class VideoFollowModelImpl implements VideoContract.IVideoFollowModel {
    public static final String TAG = "-->>VideoFollowModelIm";
    private Call<VideoFollowResBean> mCall;

    @Override
    public void loadDataVideoFollow(VideoFollowReqBean bean, final VideoContract.OnVideoFollowListener listener) {
        Retrofit retrofit = RetrofitSingleton.getInstance(HostType.TEST_PIC).getRetrofit();
        VideoApiService service = retrofit.create(VideoApiService.class);
        mCall = service.requestVideoFollow(bean.getCanal(), bean.getUserId(), bean.getToken(), bean.getPlat(), bean.getService(), bean.getAnchor_id(), bean.getPage(), bean.getPageSize(), bean.getCheck());
        mCall.enqueue(new Callback<VideoFollowResBean>() {
            @Override
            public void onResponse(Call<VideoFollowResBean> call, Response<VideoFollowResBean> response) {
                if (response != null && response.body() != null) {
                    listener.onReqSuccessVideoFollow(response.body());
                }
            }

            @Override
            public void onFailure(Call<VideoFollowResBean> call, Throwable t) {
                listener.onErrorVideoFollow();
            }
        });

    }

    @Override
    public void interruptVideoFollow() {
        try {
            if (mCall != null && !mCall.isCanceled()) {
                mCall.cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"interruptHttp打断网络请求异常");
        }
    }
}
