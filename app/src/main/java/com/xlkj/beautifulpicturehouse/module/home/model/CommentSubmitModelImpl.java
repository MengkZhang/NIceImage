package com.xlkj.beautifulpicturehouse.module.home.model;

import android.util.Log;

import com.xlkj.beautifulpicturehouse.common.http.HostType;
import com.xlkj.beautifulpicturehouse.common.http.RetrofitSingleton;
import com.xlkj.beautifulpicturehouse.module.home.apiservice.HomeApiService;
import com.xlkj.beautifulpicturehouse.module.home.bean.CommentSubmitReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.CommentSubmitResBean;
import com.xlkj.beautifulpicturehouse.module.home.contract.HomeContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2018/1/15.
 * 提交评论model
 */

public class CommentSubmitModelImpl implements HomeContract.ICommentSubmitModel {
    public static final String TAG = "-->>CommentSubmitModelI";
    private Call<CommentSubmitResBean> mCall;

    @Override
    public void loadDataCommentSubmit(CommentSubmitReqBean bean, final HomeContract.OnCommentSubmitListener listener) {
        Retrofit retrofit = RetrofitSingleton.getInstance(HostType.MTW_INFO).getRetrofit();
        HomeApiService apiService = retrofit.create(HomeApiService.class);
        mCall = apiService.requestCommentSubmit(bean.getAction(), bean.getUserId(), bean.getTypeId(), bean.getIsPicture(), bean.getContent());
        mCall.enqueue(new Callback<CommentSubmitResBean>() {
            @Override
            public void onResponse(Call<CommentSubmitResBean> call, Response<CommentSubmitResBean> response) {
                if (response != null && response.body() != null) {
                    listener.onRequestSuccessCommentSubmit(response.body());
                }
            }

            @Override
            public void onFailure(Call<CommentSubmitResBean> call, Throwable t) {
                listener.onErrorCommentSubmit();
            }
        });
    }

    @Override
    public void interruptCommentSubmit() {
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
