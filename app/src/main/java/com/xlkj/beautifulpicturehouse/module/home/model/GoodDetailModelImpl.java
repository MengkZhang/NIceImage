package com.xlkj.beautifulpicturehouse.module.home.model;

import android.util.Log;

import com.xlkj.beautifulpicturehouse.common.http.HostType;
import com.xlkj.beautifulpicturehouse.common.http.RetrofitSingleton;
import com.xlkj.beautifulpicturehouse.module.home.apiservice.HomeApiService;
import com.xlkj.beautifulpicturehouse.module.home.bean.GoodDetailReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.GoodDetailResBean;
import com.xlkj.beautifulpicturehouse.module.home.contract.HomeContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2018/1/2.
 */

public class GoodDetailModelImpl implements HomeContract.IGoodDetailModel {
    public static final String TAG = "-->>GoodDetailModelImpl";
    private Call<GoodDetailResBean> mCall;

    @Override
    public void loadDataGoodDetail(final GoodDetailReqBean bean, final HomeContract.OnGoodDetailListener listener) {
        Retrofit retrofit = RetrofitSingleton.getInstance(HostType.MTW_INFO).getRetrofit();
        HomeApiService apiService = retrofit.create(HomeApiService.class);
        mCall = apiService.requestGoodDetail(bean.getAction(),bean.getImageId(),bean.getFollowId(),bean.getUserId());
        mCall.enqueue(new Callback<GoodDetailResBean>() {
            @Override
            public void onResponse(Call<GoodDetailResBean> call, Response<GoodDetailResBean> response) {
                if (response != null && response.body() != null) {
                    listener.onRequestSuccessGoodDetail(response.body());
                }
            }

            @Override
            public void onFailure(Call<GoodDetailResBean> call, Throwable t) {
                listener.onErrorGoodDetail();
            }
        });
    }

    @Override
    public void interruptGoodDetail() {
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
