package com.xlkj.beautifulpicturehouse.module.mine.presenter;

import android.util.Log;

import com.xlkj.beautifulpicturehouse.module.mine.bean.LoginReqBean;
import com.xlkj.beautifulpicturehouse.module.mine.bean.LoginResBean;
import com.xlkj.beautifulpicturehouse.module.mine.contract.MineContract;
import com.xlkj.beautifulpicturehouse.module.mine.model.AppLoginModelImpl;

/**
 * Created by Administrator on 2018/1/5.
 * app服务器的登录presenter
 */

public class AppLoginPresenterImpl implements MineContract.IAppLoginPresenter,MineContract.OnAppLoginListener {

    public static final String TAG = "-->AppLoginPresenterI";
    private MineContract.IAppLoginModel mModel;
    private MineContract.IAppLoginView mView;

    public AppLoginPresenterImpl() {
        mModel = new AppLoginModelImpl();
    }

    @Override
    public void attachAppLogin(MineContract.IAppLoginView view) {
        this.mView = view;
    }

    @Override
    public void getDataAppLogin(LoginReqBean bean) {
        mModel.loadDataAppLogin(bean,this);
    }

    @Override
    public void detachAppLogin() {
        mView = null;
    }

    @Override
    public void onErrorAppLogin() {
        try {
            if (mView != null) {
                mView.showErrorAppLogin();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"onError异常");
        }
    }

    @Override
    public void onReqSuccessAppLogin(LoginResBean bean) {
        try {
            if (mView != null) {
                mView.setDataAppLogin(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"onReqSuccessAppLogin异常");
        }
    }

    public void interruptHttp() {
        //即取消网络请求
        mModel.interruptAppLogin();
    }
}
