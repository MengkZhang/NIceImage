package com.xlkj.beautifulpicturehouse.common.manager;

import com.xlkj.beautifulpicturehouse.common.http.HostType;
import com.xlkj.beautifulpicturehouse.common.http.RetrofitSingleton;
import com.xlkj.beautifulpicturehouse.module.home.apiservice.HomeApiService;
import com.xlkj.beautifulpicturehouse.module.home.bean.CollectionReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.CollectionResBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2018/1/26.
 * 收藏的单例模式管理者
 */

public class CollectionManager {

    private CollectionManager() {

    }

    public static CollectionManager single = new CollectionManager();

    public static CollectionManager getInstance() {
        return single;
    }

    public void setCollectionData(boolean isCollectionState, String uId, String typeId, String isPic, final CollectionCallBack mCollectionCallBack) {
        //收藏或者取消收藏的操作
        CollectionReqBean bean = new CollectionReqBean();
        bean.setAction("collect");
        bean.setUserId(uId);
        bean.setTypeId(typeId);
        //获取收藏的状态看是否被收藏过
        //isCollect	int	0取消 1收藏
        if (!isCollectionState) {
            bean.setIsCollect("1");
        } else {
            bean.setIsCollect("0");
        }
        bean.setIsPicture(isPic);
        //网络请求
        Retrofit retrofit = RetrofitSingleton.getInstance(HostType.MTW_INFO).getRetrofit();
        HomeApiService apiService = retrofit.create(HomeApiService.class);
        Call<CollectionResBean> call = apiService.requestCollection(bean.getAction(), bean.getTypeId(), bean.getUserId(), bean.getIsPicture(), bean.getIsCollect());
        call.enqueue(new Callback<CollectionResBean>() {
            @Override
            public void onResponse(Call<CollectionResBean> call, Response<CollectionResBean> response) {
                if (response != null && response.body() != null) {
                    mCollectionCallBack.onSuccess(response.body());
                } else {
                    mCollectionCallBack.onFailed("请求数据为空");
                }
            }

            @Override
            public void onFailure(Call<CollectionResBean> call, Throwable t) {
                mCollectionCallBack.onFailed("请求失败："+t.getMessage());
            }
        });

    }

    public interface CollectionCallBack {
        void onSuccess(CollectionResBean bean);
        void onFailed(String errorMsg);
    }

}
