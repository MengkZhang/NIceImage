package com.xlkj.beautifulpicturehouse.common.http;

import android.util.SparseArray;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Administrator on 2017/12/20.
 * Retrofit单例模式封装 避免每次写重复的操作
 */

public class RetrofitSingleton {
    // 管理不同HostType的单例
    private static SparseArray<RetrofitSingleton> sInstanceManager = new SparseArray<>(HostType.TYPE_COUNT);
    private Retrofit mRetrofit;

    //内部类
//    private static class MySingletonHandler {
//        private static RetrofitSingleton instance = new RetrofitSingleton();
//    }

//    private RetrofitSingleton() {
    private RetrofitSingleton(@HostType.HostTypeChecker int hostType) {
        if (mRetrofit == null) {
            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder().connectTimeout(200, TimeUnit.SECONDS)
                    .writeTimeout(200, TimeUnit.SECONDS)
                    .readTimeout(200, TimeUnit.SECONDS);
            mRetrofit = new Retrofit.Builder().client(httpClientBuilder.build())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(JacksonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                    .baseUrl(HttpMethods.BASE_URL)
                    .baseUrl(HttpMethods.getHost(hostType))
                    .build();
        }
    }

//    public static RetrofitSingleton getInstance() {
    public static RetrofitSingleton getInstance(int hostType) {
//        return MySingletonHandler.instance;
        RetrofitSingleton instance = sInstanceManager.get(hostType);
        if (instance == null) {
            instance = new RetrofitSingleton(hostType);
            sInstanceManager.put(hostType, instance);
            return instance;
        } else {
            return instance;
        }

    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }
}
