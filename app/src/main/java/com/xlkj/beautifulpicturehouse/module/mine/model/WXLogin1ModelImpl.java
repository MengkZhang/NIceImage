package com.xlkj.beautifulpicturehouse.module.mine.model;

import android.util.Log;

import com.xlkj.beautifulpicturehouse.common.http.HostType;
import com.xlkj.beautifulpicturehouse.common.http.RetrofitSingleton;
import com.xlkj.beautifulpicturehouse.module.mine.apiservice.MineApiService;
import com.xlkj.beautifulpicturehouse.module.mine.bean.GetTokenOpenIdBean;
import com.xlkj.beautifulpicturehouse.module.mine.bean.ReqGetTokenOpenIdBean;
import com.xlkj.beautifulpicturehouse.module.mine.contract.MineContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2017/12/27.
 */

public class WXLogin1ModelImpl implements MineContract.IWXLogin1Model {
    public static final String TAG = "-->WXLogin1ModelImpl";
    private Call<GetTokenOpenIdBean> mCall;

    @Override
    public void loadDataWXLogin1(ReqGetTokenOpenIdBean bean, final MineContract.OnWXLogin1Listener listener) {
        Retrofit retrofit = RetrofitSingleton.getInstance(HostType.WE_CHAT1).getRetrofit();
        MineApiService apiService = retrofit.create(MineApiService.class);
        mCall = apiService.requestFromCodeGetToeknOpenId(bean.getAppid(),bean.getSecret(),bean.getCode(),bean.getGrant_type());
        mCall.enqueue(new Callback<GetTokenOpenIdBean>() {
            @Override
            public void onResponse(Call<GetTokenOpenIdBean> call, Response<GetTokenOpenIdBean> response) {
                Log.e(TAG,"model请求到的数据："+response.body().toString());
                if (response != null && response.body() != null) {
                    listener.onReqSuccessWXLogin1(response.body());
                } else {
                    listener.onErrorWXLogin1();
                }
            }

            @Override
            public void onFailure(Call<GetTokenOpenIdBean> call, Throwable t) {
                Log.e(TAG,"请求失败："+t.getMessage());
                listener.onErrorWXLogin1();
            }
        });
    }


    @Override
    public void interruptWXLogin1() {
        if (mCall != null && !mCall.isCanceled()) {
            mCall.cancel();
        }
    }
}
