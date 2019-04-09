package com.xlkj.beautifulpicturehouse.module.home.presenter;

import android.util.Log;

import com.xlkj.beautifulpicturehouse.module.home.bean.AboutTagReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.AboutTagResBean;
import com.xlkj.beautifulpicturehouse.module.home.contract.HomeContract;
import com.xlkj.beautifulpicturehouse.module.home.model.AboutTagModelImpl;

/**
 * Created by Administrator on 2018/1/9.
 * 相关标签presenter
 */

public class AboutTagPresenterImpl implements HomeContract.IAboutTagPresenter,HomeContract.OnAboutTagListener {

    public static final String TAG = "-->AboutTagPresenterI";
    private HomeContract.IAboutTagModel mModel;
    private HomeContract.IAboutTagView mView;

    public AboutTagPresenterImpl() {
        mModel = new AboutTagModelImpl();
    }

    @Override
    public void attachAboutTag(HomeContract.IAboutTagView view) {
        this.mView = view;
    }

    @Override
    public void getDataAboutTag(AboutTagReqBean bean) {
        mModel.loadDataAboutTag(bean,this);
    }

    @Override
    public void detachAboutTag() {
        mView = null;
    }

    @Override
    public void onErrorAboutTag() {
        try {
            if (mView != null) {
                mView.showErrorAboutTag();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"onError异常");
        }
    }

    @Override
    public void onRequestSuccessAboutTag(AboutTagResBean bean) {
        mView.setDataAboutTag(bean);
    }

    public void interruptHttp() {
        mModel.interruptAboutTag();
    }
}
