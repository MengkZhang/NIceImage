package com.xlkj.beautifulpicturehouse.module.home.model;

import android.util.Log;

import com.xlkj.beautifulpicturehouse.common.http.HostType;
import com.xlkj.beautifulpicturehouse.common.http.RetrofitSingleton;
import com.xlkj.beautifulpicturehouse.module.home.apiservice.HomeApiService;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomeHotNewResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomeHotPicResMgqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomePicGoodChoiceReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.HotListPicReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomeHotPicResBean;
import com.xlkj.beautifulpicturehouse.module.home.contract.HomeContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2017/12/21.
 */

public class HotPicListModelImpl implements HomeContract.IHotPicListModel {

    public static final String TAG = "-->HotPicListModelImpl";
    private Call<HomeHotNewResBean> mCall;

    @Override
    public void loadDataHotPicList(HomePicGoodChoiceReqBean bean, final HomeContract.OnHotPicListListener listener) {
        Retrofit retrofit = RetrofitSingleton.getInstance(HostType.MTW_INFO).getRetrofit();
        HomeApiService apiService = retrofit.create(HomeApiService.class);
        mCall = apiService.requestHotNew(bean.getuId(),bean.getTypeId(),bean.getAction(),bean.getPage());
        mCall.enqueue(new Callback<HomeHotNewResBean>() {
            @Override
            public void onResponse(Call<HomeHotNewResBean> call, Response<HomeHotNewResBean> response) {
                if (response != null && response.body() != null) {
                    listener.onRequestSuccessHotPicList(response.body());
                } else {
                    listener.onErrorHotPicList();
                }
            }

            @Override
            public void onFailure(Call<HomeHotNewResBean> call, Throwable t) {
                Log.e(TAG,"请求失败："+t.getMessage());
                listener.onErrorHotPicList();
            }
        });
    }

    @Override
    public void interruptHttpHotPicList() {
        try {
            if (mCall != null && !mCall.isCanceled()) {
                mCall.cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"interruptHttpHotPicList打断网络异常");
        }
    }
}
