package com.xlkj.beautifulpicturehouse.module.mine.model;

import android.util.Log;

import com.xlkj.beautifulpicturehouse.common.http.HostType;
import com.xlkj.beautifulpicturehouse.common.http.RetrofitSingleton;
import com.xlkj.beautifulpicturehouse.module.home.bean.AboutTagReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.AboutTagResBean;
import com.xlkj.beautifulpicturehouse.module.mine.apiservice.MineApiService;
import com.xlkj.beautifulpicturehouse.module.mine.bean.MyCollectionReqBean;
import com.xlkj.beautifulpicturehouse.module.mine.bean.MyFollowResBean;
import com.xlkj.beautifulpicturehouse.module.mine.contract.MineContract;
import com.xlkj.beautifulpicturehouse.module.video.apiservice.VideoApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2018/1/9.
 * 我的关注model
 */

public class MyFollowModelImpl implements MineContract.IMyFollowModel {

    public static final String TAG = "-->MyFollowModelImpl";
    private Call<MyFollowResBean> mCall;

    @Override
    public void loadDataMyFollow(MyCollectionReqBean bean, final MineContract.OnMyFollowListener listener) {
        Retrofit retrofit = RetrofitSingleton.getInstance(HostType.MTW_INFO).getRetrofit();
        MineApiService service = retrofit.create(MineApiService.class);
        mCall = service.requestMyFollow(bean.getAction(),bean.getUserId());
        mCall.enqueue(new Callback<MyFollowResBean>() {
            @Override
            public void onResponse(Call<MyFollowResBean> call, Response<MyFollowResBean> response) {
                if (response != null && response.body() != null) {
                    listener.onReqSuccessMyFollow(response.body());
                }
            }

            @Override
            public void onFailure(Call<MyFollowResBean> call, Throwable t) {
                listener.onErrorMyFollow();
            }
        });
    }

    @Override
    public void interruptMyFollow() {
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
