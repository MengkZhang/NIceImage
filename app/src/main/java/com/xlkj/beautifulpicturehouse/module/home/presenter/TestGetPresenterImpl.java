package com.xlkj.beautifulpicturehouse.module.home.presenter;

import android.util.Log;

import com.xlkj.beautifulpicturehouse.module.home.bean.HomeHeadResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.WeatherBean;
import com.xlkj.beautifulpicturehouse.module.home.contract.HomeContract;
import com.xlkj.beautifulpicturehouse.module.home.model.TestGetModelImpl;

/**
 * Created by Administrator on 2017/12/20.
 */

public class TestGetPresenterImpl implements HomeContract.ITestGetPresenter,HomeContract.OnTestGetListener {

    public static final String TAG = "-->TestGetPresenterImpl";
    private HomeContract.ITestGetView mView;
    private HomeContract.ITestGetModel mModel;

    public TestGetPresenterImpl() {
        mModel = new TestGetModelImpl();
    }

    @Override
    public void attach(HomeContract.ITestGetView view) {
        this.mView = view;
    }

    @Override
    public void getData(String action) {
        mModel.loadData(action,this);
    }

    @Override
    public void detach() {
        mView = null;
    }

    @Override
    public void onError() {
        try {
            if (mView != null) {
                mView.showError();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"onError异常");
        }
    }

    @Override
    public void onRequestSuccess(HomeHeadResBean bean) {
        mView.setData(bean);
    }

    public void interruptHttp() {
        mModel.interruptHttp();
    }
}
