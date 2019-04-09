package com.xlkj.beautifulpicturehouse.module.mine.model;

import android.util.Log;

import com.xlkj.beautifulpicturehouse.common.http.HostType;
import com.xlkj.beautifulpicturehouse.common.http.RetrofitSingleton;
import com.xlkj.beautifulpicturehouse.module.home.bean.AboutTagReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.AboutTagResBean;
import com.xlkj.beautifulpicturehouse.module.mine.apiservice.MineApiService;
import com.xlkj.beautifulpicturehouse.module.mine.bean.MyFootStayReqBean;
import com.xlkj.beautifulpicturehouse.module.mine.bean.MyFootStayResBean;
import com.xlkj.beautifulpicturehouse.module.mine.contract.MineContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2018/1/10.
 * 我的足迹model
 */

public class MyFootStayModelImpl implements MineContract.IMyFootStayModel {

    public static final String TAG = "-->MyFootStayModelImpl";
    private Call<MyFootStayResBean> mCall;

    @Override
    public void loadDataFootStay(MyFootStayReqBean bean, final MineContract.OnMyFootStayListener listener) {
        Retrofit retrofit = RetrofitSingleton.getInstance(HostType.MTW_INFO).getRetrofit();
        MineApiService service = retrofit.create(MineApiService.class);
        mCall = service.requestMyFootStay(bean.getAction(), bean.getUserId());
        mCall.enqueue(new Callback<MyFootStayResBean>() {
            @Override
            public void onResponse(Call<MyFootStayResBean> call, Response<MyFootStayResBean> response) {
                if (response != null && response.body() != null) {
                    listener.onReqSuccessMyFootStay(response.body());
                }
            }

            @Override
            public void onFailure(Call<MyFootStayResBean> call, Throwable t) {
                listener.onErrorMyFootStay();
            }
        });
    }

    @Override
    public void interruptMyFootStay() {
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
