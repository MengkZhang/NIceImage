package com.xlkj.beautifulpicturehouse.module.home.model;

import android.util.Log;

import com.xlkj.beautifulpicturehouse.common.http.HostType;
import com.xlkj.beautifulpicturehouse.common.http.RetrofitSingleton;
import com.xlkj.beautifulpicturehouse.module.home.apiservice.HomeApiService;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomePicGoodChoiceReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomePicGoodChoiceResBean;
import com.xlkj.beautifulpicturehouse.module.home.contract.HomeContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2017/12/29.
 * 精选
 */

public class GoodChoiceModelImpl implements HomeContract.IGoodChoiceModel {
    public static final String TAG = "-->>GoodChoiceModelImpl";
    private Call<HomePicGoodChoiceResBean> mCall;

    @Override
    public void loadDataGoodChoice(HomePicGoodChoiceReqBean bean, final HomeContract.OnGoodChoiceListener listener) {
        Retrofit retrofit = RetrofitSingleton.getInstance(HostType.MTW_INFO).getRetrofit();
        HomeApiService apiService = retrofit.create(HomeApiService.class);
        mCall = apiService.requestGoodChoice(bean.getuId(),bean.getTypeId(), bean.getAction(), bean.getPage());
        mCall.enqueue(new Callback<HomePicGoodChoiceResBean>() {
            @Override
            public void onResponse(Call<HomePicGoodChoiceResBean> call, Response<HomePicGoodChoiceResBean> response) {
                try {
                    if (response != null && response.body() != null) {
                        listener.onRequestSuccessGoodChoice(response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG,"onResponse异常");
                }
            }

            @Override
            public void onFailure(Call<HomePicGoodChoiceResBean> call, Throwable t) {
                listener.onErrorGoodChoice();
            }
        });

    }

    @Override
    public void interruptGoodChoice() {
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
