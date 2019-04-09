package com.xlkj.beautifulpicturehouse.module.home.model;

import android.util.Log;

import com.xlkj.beautifulpicturehouse.common.http.HostType;
import com.xlkj.beautifulpicturehouse.common.http.RetrofitSingleton;
import com.xlkj.beautifulpicturehouse.module.home.apiservice.HomeApiService;
import com.xlkj.beautifulpicturehouse.module.home.bean.DefSearchResBean;
import com.xlkj.beautifulpicturehouse.module.home.contract.HomeContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2018/1/2.
 */

public class DefSearchModelImpl implements HomeContract.IDefSearchModel {
    public static final String TAG = "-->>DefSearchModelImpl";
    private Call<DefSearchResBean> mCall;

    @Override
    public void loadDataDefSearch(String action, final HomeContract.OnDefSearchListener listener) {
        Retrofit retrofit = RetrofitSingleton.getInstance(HostType.MTW_INFO).getRetrofit();
        HomeApiService apiService = retrofit.create(HomeApiService.class);
        mCall = apiService.requestDefSearch(action);
        mCall.enqueue(new Callback<DefSearchResBean>() {
            @Override
            public void onResponse(Call<DefSearchResBean> call, Response<DefSearchResBean> response) {
                if (response != null && response.body() != null) {
                    listener.onRequestSuccessDefSearch(response.body());
                }
            }

            @Override
            public void onFailure(Call<DefSearchResBean> call, Throwable t) {
                listener.onErrorDefSearch();
            }
        });
    }

    @Override
    public void interruptDefSearch() {
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
