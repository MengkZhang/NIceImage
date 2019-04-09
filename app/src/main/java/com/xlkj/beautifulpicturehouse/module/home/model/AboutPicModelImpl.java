package com.xlkj.beautifulpicturehouse.module.home.model;

import android.util.Log;

import com.xlkj.beautifulpicturehouse.common.http.HostType;
import com.xlkj.beautifulpicturehouse.common.http.RetrofitSingleton;
import com.xlkj.beautifulpicturehouse.module.home.apiservice.HomeApiService;
import com.xlkj.beautifulpicturehouse.module.home.bean.AboutPicReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.AboutPicResBean;
import com.xlkj.beautifulpicturehouse.module.home.contract.HomeContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2018/1/8.
 * 相关美图model
 */

public class AboutPicModelImpl implements HomeContract.IAboutPicModel {

    public static final String TAG = "-->>AboutPicModelImpl->";
    private Call<AboutPicResBean> mCall;

    @Override
    public void loadDataAboutPic(AboutPicReqBean bean, final HomeContract.OnAboutPicListener listener) {
        Retrofit retrofit = RetrofitSingleton.getInstance(HostType.MTW_INFO).getRetrofit();
        HomeApiService apiService = retrofit.create(HomeApiService.class);
        mCall = apiService.requestAboutPic(bean.getAction(), bean.getImageId(), bean.getUserId());
        mCall.enqueue(new Callback<AboutPicResBean>() {
            @Override
            public void onResponse(Call<AboutPicResBean> call, Response<AboutPicResBean> response) {
                if (response != null && response.body() != null) {
                    listener.onRequestSuccessAboutPic(response.body());
                }
            }

            @Override
            public void onFailure(Call<AboutPicResBean> call, Throwable t) {
                listener.onErrorAboutPic();
            }
        });
    }

    @Override
    public void interruptAboutPic() {
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
