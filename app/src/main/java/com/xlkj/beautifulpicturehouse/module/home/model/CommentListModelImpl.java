package com.xlkj.beautifulpicturehouse.module.home.model;

import android.util.Log;

import com.xlkj.beautifulpicturehouse.common.http.HostType;
import com.xlkj.beautifulpicturehouse.common.http.RetrofitSingleton;
import com.xlkj.beautifulpicturehouse.module.home.apiservice.HomeApiService;
import com.xlkj.beautifulpicturehouse.module.home.bean.CommentListReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.CommentListResBean;
import com.xlkj.beautifulpicturehouse.module.home.contract.HomeContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2018/1/15.
 * 获取评论列表model
 */

public class CommentListModelImpl implements HomeContract.ICommentListModel {
    public static final String TAG = "-->>CommentListModelImpl";
    private Call<CommentListResBean> mCall;

    @Override
    public void loadDataCommentList(CommentListReqBean bean, final HomeContract.OnCommentListListener listener) {
        Retrofit retrofit = RetrofitSingleton.getInstance(HostType.MTW_INFO).getRetrofit();
        HomeApiService apiService = retrofit.create(HomeApiService.class);
        mCall = apiService.requestCpmmentList(bean.getAction(), bean.getTypeId(), bean.getIspicture(), bean.getPage());
        mCall.enqueue(new Callback<CommentListResBean>() {
            @Override
            public void onResponse(Call<CommentListResBean> call, Response<CommentListResBean> response) {
                if (response != null && response.body() != null) {
                    listener.onRequestSuccessCommentList(response.body());
                }
            }

            @Override
            public void onFailure(Call<CommentListResBean> call, Throwable t) {
                listener.onErrorCommentList();
            }
        });
    }

    @Override
    public void interruptCommentList() {
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
