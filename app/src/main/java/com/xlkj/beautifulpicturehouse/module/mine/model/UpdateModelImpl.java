package com.xlkj.beautifulpicturehouse.module.mine.model;

import android.util.Log;

import com.xlkj.beautifulpicturehouse.common.http.HostType;
import com.xlkj.beautifulpicturehouse.common.http.RetrofitSingleton;
import com.xlkj.beautifulpicturehouse.module.mine.apiservice.MineApiService;
import com.xlkj.beautifulpicturehouse.module.mine.bean.CheckUpdateReqBean;
import com.xlkj.beautifulpicturehouse.module.mine.bean.CheckUpdateResBean;
import com.xlkj.beautifulpicturehouse.module.mine.contract.MineContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2018/1/15.
 * 检测更新model
 */

public class UpdateModelImpl implements MineContract.IUpdateModel {

    public static final String TAG = "-->UpdateModelImpl";
    private Call<CheckUpdateResBean> mCall;

    @Override
    public void loadDataUpdate(CheckUpdateReqBean bean, final MineContract.OnUpdateListener listener) {
        Retrofit retrofit = RetrofitSingleton.getInstance(HostType.MTW_INFO).getRetrofit();
        MineApiService service = retrofit.create(MineApiService.class);
        mCall = service.requestUpdate(bean.getAction(), bean.getVersionCode());
        mCall.enqueue(new Callback<CheckUpdateResBean>() {
            @Override
            public void onResponse(Call<CheckUpdateResBean> call, Response<CheckUpdateResBean> response) {
                if (response != null && response.body() != null) {
                    listener.onReqSuccessUpdate(response.body());
                }
            }

            @Override
            public void onFailure(Call<CheckUpdateResBean> call, Throwable t) {
                listener.onErrorUpdate();
            }
        });
    }

    @Override
    public void interruptUpdate() {
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
