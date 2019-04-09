package com.xlkj.beautifulpicturehouse.module.mine.model;

import android.util.Log;

import com.xlkj.beautifulpicturehouse.common.http.HostType;
import com.xlkj.beautifulpicturehouse.common.http.RetrofitSingleton;
import com.xlkj.beautifulpicturehouse.module.home.bean.AboutTagReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.AboutTagResBean;
import com.xlkj.beautifulpicturehouse.module.mine.apiservice.MineApiService;
import com.xlkj.beautifulpicturehouse.module.mine.bean.MyCollectionReqBean;
import com.xlkj.beautifulpicturehouse.module.mine.bean.MyCollectionResBean;
import com.xlkj.beautifulpicturehouse.module.mine.contract.MineContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2018/1/10.
 * 我的收藏model
 */

public class MyCollectionModelImpl implements MineContract.IMyCollectionModel {

    public static final String TAG = "-->MyCollectionModelI";
    private Call<MyCollectionResBean> mCall;

    @Override
    public void loadDataCollection(MyCollectionReqBean bean, final MineContract.OnMyCollectionListener listener) {
        Retrofit retrofit = RetrofitSingleton.getInstance(HostType.MTW_INFO).getRetrofit();
        MineApiService service = retrofit.create(MineApiService.class);
        mCall = service.requestMyCollection(bean.getAction(),bean.getUserId());
        mCall.enqueue(new Callback<MyCollectionResBean>() {
            @Override
            public void onResponse(Call<MyCollectionResBean> call, Response<MyCollectionResBean> response) {
                if (response != null && response.body() != null) {
                    listener.onReqSuccessMyCollection(response.body());
                }
            }

            @Override
            public void onFailure(Call<MyCollectionResBean> call, Throwable t) {
                listener.onErrorMyCollection();
            }
        });
    }

    @Override
    public void interruptMyCollection() {
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
