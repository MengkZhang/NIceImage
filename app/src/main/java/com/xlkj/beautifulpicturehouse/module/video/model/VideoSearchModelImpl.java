package com.xlkj.beautifulpicturehouse.module.video.model;

import android.util.Log;

import com.xlkj.beautifulpicturehouse.common.http.HostType;
import com.xlkj.beautifulpicturehouse.common.http.RetrofitSingleton;
import com.xlkj.beautifulpicturehouse.module.video.apiservice.VideoApiService;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoSearchReqBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoSearchResBean;
import com.xlkj.beautifulpicturehouse.module.video.contract.VideoContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2018/1/16.
 * 搜索视频更多model
 */

public class VideoSearchModelImpl implements VideoContract.IVideoSearchModel {

    public static final String TAG = "-->VideoSearchModelImpl";
    private Call<VideoSearchResBean> mCall;

    @Override
    public void loadDataVideoSearch(VideoSearchReqBean bean, final VideoContract.OnVideoSearchListener listener) {
        Retrofit retrofit = RetrofitSingleton.getInstance(HostType.MTW_INFO).getRetrofit();
        VideoApiService service = retrofit.create(VideoApiService.class);
        //0 点击搜索中的更多传的是关键字  1 其他搜索（视频标签到列表）传的是id
        if (bean.getType().equals("0")) {
            mCall = service.requestVideoSearch(bean.getAction(), bean.getSearchKey(), bean.getPage());
        } else {
            mCall = service.requestVideoSearchById(bean.getAction(), bean.getSearchKey(), bean.getPage());
        }
        mCall.enqueue(new Callback<VideoSearchResBean>() {
            @Override
            public void onResponse(Call<VideoSearchResBean> call, Response<VideoSearchResBean> response) {
                if (response != null && response.body() != null) {
                    listener.onReqSuccessVideoSearch(response.body());
                }
            }

            @Override
            public void onFailure(Call<VideoSearchResBean> call, Throwable t) {
                listener.onErrorVideoSearch();
            }
        });
    }

    @Override
    public void interruptVideoSearch() {
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
