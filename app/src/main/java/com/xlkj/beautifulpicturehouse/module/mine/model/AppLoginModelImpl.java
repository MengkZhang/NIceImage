package com.xlkj.beautifulpicturehouse.module.mine.model;

import android.util.Log;

import com.xlkj.beautifulpicturehouse.common.http.HostType;
import com.xlkj.beautifulpicturehouse.common.http.RetrofitSingleton;
import com.xlkj.beautifulpicturehouse.module.mine.apiservice.MineApiService;
import com.xlkj.beautifulpicturehouse.module.mine.bean.LoginReqBean;
import com.xlkj.beautifulpicturehouse.module.mine.bean.LoginResBean;
import com.xlkj.beautifulpicturehouse.module.mine.contract.MineContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2018/1/5.
 * app服务器的登录model
 */

public class AppLoginModelImpl implements MineContract.IAppLoginModel {

    public static final String TAG = "-->AppLoginModelImpl";
    private Call<LoginResBean> mCall;

    @Override
    public void loadDataAppLogin(LoginReqBean bean, final MineContract.OnAppLoginListener listener) {
        Retrofit retrofit = RetrofitSingleton.getInstance(HostType.MTW_INFO).getRetrofit();
        MineApiService apiService = retrofit.create(MineApiService.class);
        mCall = apiService.requestAppLogin(bean.getAction(), bean.getLoginType(), bean.getUserName(), bean.getAvatarUrl(), bean.getOpenId(),bean.getGender(), bean.getAge());
        mCall.enqueue(new Callback<LoginResBean>() {
            @Override
            public void onResponse(Call<LoginResBean> call, Response<LoginResBean> response) {
                try {
                    if (response != null && response.body() != null) {
                        listener.onReqSuccessAppLogin(response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG,"onResponse异常");
                }
            }

            @Override
            public void onFailure(Call<LoginResBean> call, Throwable t) {
                try {
                    listener.onErrorAppLogin();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG,"onFailure异常");
                }
            }
        });

    }

    @Override
    public void interruptAppLogin() {
        if (mCall != null && !mCall.isCanceled()) {
            mCall.cancel();
        }
    }
}
