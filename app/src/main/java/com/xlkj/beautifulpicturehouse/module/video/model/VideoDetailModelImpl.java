package com.xlkj.beautifulpicturehouse.module.video.model;

import android.util.Log;

import com.xlkj.beautifulpicturehouse.common.http.HostType;
import com.xlkj.beautifulpicturehouse.common.http.RetrofitSingleton;
import com.xlkj.beautifulpicturehouse.module.video.apiservice.VideoApiService;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoDetailReqBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoDetailResBean;
import com.xlkj.beautifulpicturehouse.module.video.contract.VideoContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2018/1/11.
 * 视频详情页model
 */

public class VideoDetailModelImpl implements VideoContract.IVideoDetailModel {
    public static final String TAG = "-->VideoDetailModelImpl";
    private Call<VideoDetailResBean> mCall;

    @Override
    public void loadDataVideoDetail(VideoDetailReqBean bean, final VideoContract.OnVideoDetailListener listener) {
        Retrofit retrofit = RetrofitSingleton.getInstance(HostType.MTW_INFO).getRetrofit();
        VideoApiService service = retrofit.create(VideoApiService.class);
        mCall = service.requestVideoDetail(bean.getAction(), bean.getVideoId(),bean.getFollowId(), bean.getPage(), bean.getUserId());
        mCall.enqueue(new Callback<VideoDetailResBean>() {
            @Override
            public void onResponse(Call<VideoDetailResBean> call, Response<VideoDetailResBean> response) {
                if (response != null && response.body() != null) {
                    listener.onReqSuccessVideoDetail(response.body());
                }
            }

            @Override
            public void onFailure(Call<VideoDetailResBean> call, Throwable t) {
                listener.onErrorVideoDetail();
            }
        });
    }

    @Override
    public void interruptVideoDetail() {
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
