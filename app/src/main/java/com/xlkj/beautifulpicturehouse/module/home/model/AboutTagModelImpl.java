package com.xlkj.beautifulpicturehouse.module.home.model;

import android.util.Log;

import com.xlkj.beautifulpicturehouse.common.http.HostType;
import com.xlkj.beautifulpicturehouse.common.http.RetrofitSingleton;
import com.xlkj.beautifulpicturehouse.module.home.apiservice.HomeApiService;
import com.xlkj.beautifulpicturehouse.module.home.bean.AboutTagReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.AboutTagResBean;
import com.xlkj.beautifulpicturehouse.module.home.contract.HomeContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2018/1/9.
 * 相关标签model
 */

public class AboutTagModelImpl implements HomeContract.IAboutTagModel {

    public static final String TAG = "-->>AboutTagModelImpl->";
    private Call<AboutTagResBean> mCall;

    @Override
    public void loadDataAboutTag(AboutTagReqBean bean, final HomeContract.OnAboutTagListener listener) {
        Retrofit retrofit = RetrofitSingleton.getInstance(HostType.MTW_INFO).getRetrofit();
        HomeApiService apiService = retrofit.create(HomeApiService.class);
        mCall = apiService.requestAboutTag(bean.getAction(), bean.getTypeId(), bean.getUserId());
        mCall.enqueue(new Callback<AboutTagResBean>() {
            @Override
            public void onResponse(Call<AboutTagResBean> call, Response<AboutTagResBean> response) {
                if (response != null && response.body() != null) {
                    listener.onRequestSuccessAboutTag(response.body());
                }
            }

            @Override
            public void onFailure(Call<AboutTagResBean> call, Throwable t) {
                listener.onErrorAboutTag();
            }
        });
    }

    @Override
    public void interruptAboutTag() {
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
