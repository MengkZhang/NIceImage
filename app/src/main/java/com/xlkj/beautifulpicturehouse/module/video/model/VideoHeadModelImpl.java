package com.xlkj.beautifulpicturehouse.module.video.model;

import android.util.Log;

import com.xlkj.beautifulpicturehouse.common.http.HostType;
import com.xlkj.beautifulpicturehouse.common.http.RetrofitSingleton;
import com.xlkj.beautifulpicturehouse.module.home.bean.AboutTagReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.AboutTagResBean;
import com.xlkj.beautifulpicturehouse.module.video.apiservice.VideoApiService;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoHeadResBean;
import com.xlkj.beautifulpicturehouse.module.video.contract.VideoContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2018/1/9.
 * 视频页头部model
 */

public class VideoHeadModelImpl implements VideoContract.IVideoHeadModel {

    public static final String TAG = "-->VideoHeadModelImpl";
    private Call<VideoHeadResBean> mCall;

    @Override
    public void loadDataVideoHead(String action, final VideoContract.OnVideoHeadListener listener) {
        Retrofit retrofit = RetrofitSingleton.getInstance(HostType.MTW_INFO).getRetrofit();
        VideoApiService service = retrofit.create(VideoApiService.class);
        mCall = service.requestVideoHead(action);
        mCall.enqueue(new Callback<VideoHeadResBean>() {
            @Override
            public void onResponse(Call<VideoHeadResBean> call, Response<VideoHeadResBean> response) {
                if (response != null && response.body() != null) {
                    listener.onReqSuccessVideoHead(response.body());
                }
            }

            @Override
            public void onFailure(Call<VideoHeadResBean> call, Throwable t) {
                listener.onErrorVideoHead();
            }
        });
    }

    @Override
    public void interruptVideoHead() {
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
