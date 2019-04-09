package com.xlkj.beautifulpicturehouse.module.home.model;

import android.util.Log;

import com.xlkj.beautifulpicturehouse.common.http.HostType;
import com.xlkj.beautifulpicturehouse.common.http.RetrofitSingleton;
import com.xlkj.beautifulpicturehouse.module.home.apiservice.HomeApiService;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomeHeadResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.WeatherBean;
import com.xlkj.beautifulpicturehouse.module.home.contract.HomeContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2017/12/20.
 */

public class TestGetModelImpl implements HomeContract.ITestGetModel {
    public static final String TAG = "-->>TestGetModelImpl->";
    private Call<HomeHeadResBean> mCall;

    @Override
    public void loadData(String action, final HomeContract.OnTestGetListener listener) {
        Retrofit retrofit = RetrofitSingleton.getInstance(HostType.MTW_INFO).getRetrofit();
        HomeApiService apiService = retrofit.create(HomeApiService.class);
        mCall = apiService.requestHomeHeadData(action);
        mCall.enqueue(new Callback<HomeHeadResBean>() {
            @Override
            public void onResponse(Call<HomeHeadResBean> call, Response<HomeHeadResBean> response) {
//                try {
                    if (response != null && response.body() != null) {
                        listener.onRequestSuccess(response.body());
                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    Log.e(TAG,"onResponse异常");
//                }
            }

            @Override
            public void onFailure(Call<HomeHeadResBean> call, Throwable t) {
                listener.onError();
            }
        });
    }

    /**
     * 取消网络操作
     */
    @Override
    public void interruptHttp() {
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
