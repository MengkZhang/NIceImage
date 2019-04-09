package com.xlkj.beautifulpicturehouse.module.video.model;

import android.util.Log;

import com.xlkj.beautifulpicturehouse.common.http.HostType;
import com.xlkj.beautifulpicturehouse.common.http.RetrofitSingleton;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomeHotPicResMgqBean;
import com.xlkj.beautifulpicturehouse.module.video.apiservice.VideoApiService;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoAboutReqBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoAboutResBean;
import com.xlkj.beautifulpicturehouse.module.video.contract.VideoContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2017/12/29.
 * 相关视频
 */

public class VideoAboutModelImpl implements VideoContract.IVideoAboutModel {

    public static final String TAG = "-->>VideoAboutModelImpl";
    private Call<VideoAboutResBean> mCall;

    @Override
    public void loadDataVideoAbout(VideoAboutReqBean bean, final VideoContract.OnVideoAboutListener listener) {
        Retrofit retrofit = RetrofitSingleton.getInstance(HostType.TEST_PIC).getRetrofit();
        VideoApiService service = retrofit.create(VideoApiService.class);
        mCall = service.requestAboutVideo(bean.getCanal(), bean.getUserId(), bean.getToken(), bean.getPlat(), bean.getService(), bean.getAnchor_id(), bean.getPage(), bean.getPageSize(), bean.getCheck());
        mCall.enqueue(new Callback<VideoAboutResBean>() {
            @Override
            public void onResponse(Call<VideoAboutResBean> call, Response<VideoAboutResBean> response) {
                try {
                    if (response != null && response.body() != null) {
                        listener.onReqSuccessVideoAbout(response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG,"onResponse异常");
                }
            }

            @Override
            public void onFailure(Call<VideoAboutResBean> call, Throwable t) {
                listener.onErrorVideoAbout();
            }
        });

    }

    @Override
    public void interruptVideoAbout() {
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
