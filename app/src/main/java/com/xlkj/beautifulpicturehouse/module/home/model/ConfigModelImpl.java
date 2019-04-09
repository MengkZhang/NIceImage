package com.xlkj.beautifulpicturehouse.module.home.model;

import android.util.Log;

import com.xlkj.beautifulpicturehouse.BeautyApplication;
import com.xlkj.beautifulpicturehouse.common.constant.Constant;
import com.xlkj.beautifulpicturehouse.common.http.HostType;
import com.xlkj.beautifulpicturehouse.common.http.RetrofitSingleton;
import com.xlkj.beautifulpicturehouse.common.util.FileUtils;
import com.xlkj.beautifulpicturehouse.module.home.apiservice.HomeApiService;
import com.xlkj.beautifulpicturehouse.module.home.bean.ConfigResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.ConfigSaveBean;
import com.xlkj.beautifulpicturehouse.module.home.contract.HomeContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2018/1/16.
 * 请求配置model
 */

public class ConfigModelImpl implements HomeContract.IConfigModel {
    public static final String TAG = "-->ConfigModelImpl";

    @Override
    public void loadDataConfig(String action) {
        Retrofit retrofit = RetrofitSingleton.getInstance(HostType.MTW_INFO).getRetrofit();
        HomeApiService apiService = retrofit.create(HomeApiService.class);
        Call<ConfigResBean> call = apiService.requestConfig(action);
        call.enqueue(new Callback<ConfigResBean>() {
            @Override
            public void onResponse(Call<ConfigResBean> call, Response<ConfigResBean> response) {
                if (response != null && response.body() != null) {
                    Log.e(TAG,"配置信息："+response.body().toString());
                    try {
                        //获取qq群信息
                        String qq = response.body().getData().getQq();
                        ConfigSaveBean saveBean = new ConfigSaveBean();
                        saveBean.setQQ(qq);
                        //保存配置信息到文件
                        FileUtils.saveCachDataToFile(BeautyApplication.appContext, Constant.CONFIG_CACHE_KEY, saveBean);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(TAG, "保存配置信息到文件异常");
                    }
                } else {
                    Log.e(TAG, "获取配置信息为空");
                }
            }

            @Override
            public void onFailure(Call<ConfigResBean> call, Throwable t) {
                Log.e(TAG, "获取配置失败");
            }
        });
    }
}
