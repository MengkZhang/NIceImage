package com.xlkj.beautifulpicturehouse.module.home.presenter;

import android.util.Log;

import com.xlkj.beautifulpicturehouse.module.home.bean.DefSearchResBean;
import com.xlkj.beautifulpicturehouse.module.home.contract.HomeContract;
import com.xlkj.beautifulpicturehouse.module.home.model.DefSearchModelImpl;

/**
 * Created by Administrator on 2018/1/2.
 */

public class DefSearchPresenterImpl implements HomeContract.IDefSearchPresenter,HomeContract.OnDefSearchListener {
    public static final String TAG = "-->DefSearchPresenterI";
    private HomeContract.IDefSearchModel mModel;
    private HomeContract.IDefSearchView mView;

    public DefSearchPresenterImpl(HomeContract.IDefSearchView mView) {
        this.mView = mView;
        mModel = new DefSearchModelImpl();
    }

    @Override
    public void attachDefSearch(HomeContract.IDefSearchView view) {
        this.mView = view;
    }

    @Override
    public void getDataDefSearch(String action) {
        mModel.loadDataDefSearch(action,this);
    }

    @Override
    public void detachDefSearch() {
        mView = null;
    }

    @Override
    public void onErrorDefSearch() {
        try {
            if (mView != null) {
                mView.showErrorDefSearch();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"onError异常");
        }
    }

    @Override
    public void onRequestSuccessDefSearch(DefSearchResBean bean) {
        mView.setDataDefSearch(bean);
    }

    public void interruptHttp() {
        mModel.interruptDefSearch();
    }
}
