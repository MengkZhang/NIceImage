package com.xlkj.beautifulpicturehouse.module.video.presenter;

import android.util.Log;

import com.xlkj.beautifulpicturehouse.module.home.bean.AboutTagReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.AboutTagResBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoHeadResBean;
import com.xlkj.beautifulpicturehouse.module.video.contract.VideoContract;
import com.xlkj.beautifulpicturehouse.module.video.model.VideoHeadModelImpl;

/**
 * Created by Administrator on 2018/1/9.
 * 视频页头部presenter
 */

public class VideoHeadPresenterImpl implements VideoContract.IVideoHeadPresenter,VideoContract.OnVideoHeadListener {
    public static final String TAG = "-->VideoHeadPresenterI";
    private VideoContract.IVideoHeadModel mModel;
    private VideoContract.IVideoHeadView mView;

    public VideoHeadPresenterImpl() {
        mModel = new VideoHeadModelImpl();
    }

    @Override
    public void attachVideoHead(VideoContract.IVideoHeadView view) {
        this.mView = view;
    }

    @Override
    public void getDataVideoHead(String action) {
        mModel.loadDataVideoHead(action,this);
    }

    @Override
    public void detachVideoHead() {
        mView = null;
    }

    @Override
    public void onErrorVideoHead() {
        try {
            if (mView != null) {
                mView.showErrorVideoHead();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"onError异常");
        }
    }

    @Override
    public void onReqSuccessVideoHead(VideoHeadResBean bean) {
        mView.setDataVideoHead(bean);
    }

    public void interruptHttp() {
        mModel.interruptVideoHead();
    }

}
