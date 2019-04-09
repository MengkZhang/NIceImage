package com.xlkj.beautifulpicturehouse.module.home.model;

import android.util.Log;

import com.xlkj.beautifulpicturehouse.common.http.HostType;
import com.xlkj.beautifulpicturehouse.common.http.RetrofitSingleton;
import com.xlkj.beautifulpicturehouse.module.home.apiservice.HomeApiService;
import com.xlkj.beautifulpicturehouse.module.home.bean.CollectionReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.CollectionResBean;
import com.xlkj.beautifulpicturehouse.module.home.contract.HomeContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2018/1/5.
 * 收藏model
 */

public class CollectionModelImpl implements HomeContract.ICollectionModel {

    public static final String TAG = "-->>CollectionModelI->";
    private Call<CollectionResBean> mCall;

    @Override
    public void loadDataCollection(CollectionReqBean bean, final HomeContract.OnCollectionListener listener) {
        Retrofit retrofit = RetrofitSingleton.getInstance(HostType.MTW_INFO).getRetrofit();
        HomeApiService apiService = retrofit.create(HomeApiService.class);
        mCall = apiService.requestCollection(bean.getAction(), bean.getTypeId(), bean.getUserId(), bean.getIsPicture(), bean.getIsCollect());
        mCall.enqueue(new Callback<CollectionResBean>() {
            @Override
            public void onResponse(Call<CollectionResBean> call, Response<CollectionResBean> response) {
                if (response != null && response.body() != null) {
                    listener.onRequestSuccessCollection(response.body());
                }
            }

            @Override
            public void onFailure(Call<CollectionResBean> call, Throwable t) {
                listener.onErrorCollection();
            }
        });
    }

    @Override
    public void interruptCollection() {
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
