package com.xlkj.beautifulpicturehouse.module.home.model;

import android.util.Log;

import com.xlkj.beautifulpicturehouse.common.http.HostType;
import com.xlkj.beautifulpicturehouse.common.http.RetrofitSingleton;
import com.xlkj.beautifulpicturehouse.module.home.apiservice.HomeApiService;
import com.xlkj.beautifulpicturehouse.module.home.bean.FollowReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.FollowResBean;
import com.xlkj.beautifulpicturehouse.module.home.contract.HomeContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2018/1/8.
 * 关注或取消关注model
 */

public class FollowModelImpl implements HomeContract.IFollowModel {

    public static final String TAG = "-->>FollowModelImpl->";
    private Call<FollowResBean> mCall;

    @Override
    public void loadDataFollow(FollowReqBean bean, final HomeContract.OnFollowListener listener) {
        Retrofit retrofit = RetrofitSingleton.getInstance(HostType.MTW_INFO).getRetrofit();
        HomeApiService apiService = retrofit.create(HomeApiService.class);
        mCall = apiService.requestFollow(bean.getAction(), bean.getFollowId(), bean.getUserId(), bean.getIsFollow(), bean.getType());
        mCall.enqueue(new Callback<FollowResBean>() {
            @Override
            public void onResponse(Call<FollowResBean> call, Response<FollowResBean> response) {
                if (response != null && response.body() != null) {
                    listener.onRequestSuccessFollow(response.body());
                }
            }

            @Override
            public void onFailure(Call<FollowResBean> call, Throwable t) {
                listener.onErrorFollow();
            }
        });

    }

    @Override
    public void interruptFollow() {
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
