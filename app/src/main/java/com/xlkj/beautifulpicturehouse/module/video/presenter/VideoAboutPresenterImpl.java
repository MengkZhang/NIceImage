package com.xlkj.beautifulpicturehouse.module.video.presenter;

import android.util.Log;

import com.xlkj.beautifulpicturehouse.module.video.bean.VideoAboutReqBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoAboutResBean;
import com.xlkj.beautifulpicturehouse.module.video.contract.VideoContract;
import com.xlkj.beautifulpicturehouse.module.video.model.VideoAboutModelImpl;

/**
 * Created by Administrator on 2017/12/29.
 */

public class VideoAboutPresenterImpl implements VideoContract.IVideoAboutPresenter,VideoContract.OnVideoAboutListener {

    public static final String TAG = "-->TestGetPresenterImpl";
    private VideoContract.IVideoAboutModel mModel;
    private VideoContract.IVideoAboutView mView;

    public VideoAboutPresenterImpl(VideoContract.IVideoAboutView mView) {
        this.mView = mView;
        mModel = new VideoAboutModelImpl();
    }

    @Override
    public void attachVideoAbout(VideoContract.IVideoAboutView view) {
        this.mView = view;
    }

    @Override
    public void getDataVideoAbout(VideoAboutReqBean bean) {
        mModel.loadDataVideoAbout(bean,this);
    }

    @Override
    public void detachVideoAbout() {
        mView = null;
    }

    @Override
    public void onErrorVideoAbout() {
        try {
            if (mView != null) {
                mView.showErrorVideoAbout();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"onError异常");
        }
    }

    @Override
    public void onReqSuccessVideoAbout(VideoAboutResBean bean) {
        mView.setDataVideoAbout(bean);
    }

    public void interruptHttp() {
        mModel.interruptVideoAbout();
    }
}
