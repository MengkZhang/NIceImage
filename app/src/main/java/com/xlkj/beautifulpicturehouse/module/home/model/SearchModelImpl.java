package com.xlkj.beautifulpicturehouse.module.home.model;

import android.util.Log;

import com.xlkj.beautifulpicturehouse.common.http.HostType;
import com.xlkj.beautifulpicturehouse.common.http.RetrofitSingleton;
import com.xlkj.beautifulpicturehouse.module.home.apiservice.HomeApiService;
import com.xlkj.beautifulpicturehouse.module.home.bean.SearchReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.SearchResBean;
import com.xlkj.beautifulpicturehouse.module.home.contract.HomeContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2018/1/2.
 */

public class SearchModelImpl implements HomeContract.ISearchModel {
    public static final String TAG = "-->>SearchModelImpl";
    private Call<SearchResBean> mCall;

    @Override
    public void loadDataSearch(SearchReqBean bean, final HomeContract.OnSearchListener listener) {
        Retrofit retrofit = RetrofitSingleton.getInstance(HostType.MTW_INFO).getRetrofit();
        HomeApiService apiService = retrofit.create(HomeApiService.class);
        //0 点击搜索 1 其他搜索
        if (bean.getType().equals("0")) {
            mCall = apiService.requestSearch(bean.getUid(),bean.getAction(),bean.getSearchKey(),bean.getTypeVideo(),bean.getPage());
        } else {
            mCall = apiService.requestSearchById(bean.getUid(),bean.getAction(),bean.getSearchKey(),bean.getTypeVideo(),bean.getPage());
        }
        mCall.enqueue(new Callback<SearchResBean>() {
            @Override
            public void onResponse(Call<SearchResBean> call, Response<SearchResBean> response) {
                if (response != null && response.body() != null) {
                    listener.onRequestSuccessSearch(response.body());
                }
            }

            @Override
            public void onFailure(Call<SearchResBean> call, Throwable t) {
                listener.onErrorSearch();
            }
        });

    }

    @Override
    public void interruptSearch() {
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
