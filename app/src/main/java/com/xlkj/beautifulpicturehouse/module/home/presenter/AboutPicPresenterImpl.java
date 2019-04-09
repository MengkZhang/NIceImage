package com.xlkj.beautifulpicturehouse.module.home.presenter;

import android.util.Log;

import com.xlkj.beautifulpicturehouse.module.home.bean.AboutPicReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.AboutPicResBean;
import com.xlkj.beautifulpicturehouse.module.home.contract.HomeContract;
import com.xlkj.beautifulpicturehouse.module.home.model.AboutPicModelImpl;

/**
 * Created by Administrator on 2018/1/8.
 * 相关美图presenter
 */

public class AboutPicPresenterImpl implements HomeContract.IAboutPicPresenter,HomeContract.OnAboutPicListener {

    public static final String TAG = "-->AboutPicPresenterI";
    private HomeContract.IAboutPicModel mModel;
    private HomeContract.IAboutPicView mView;

    public AboutPicPresenterImpl() {
        mModel = new AboutPicModelImpl();
    }

    @Override
    public void attachAboutPic(HomeContract.IAboutPicView view) {
        this.mView = view;
    }

    @Override
    public void getDataAboutPic(AboutPicReqBean bean) {
        mModel.loadDataAboutPic(bean,this);
    }

    @Override
    public void detachAboutPic() {
        mView = null;
    }

    @Override
    public void onErrorAboutPic() {
        try {
            if (mView != null) {
                mView.showErrorAboutPic();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"onError异常");
        }
    }

    @Override
    public void onRequestSuccessAboutPic(AboutPicResBean bean) {
        mView.setDataAboutPic(bean);
    }

    public void interruptHttp() {
        mModel.interruptAboutPic();
    }
}
