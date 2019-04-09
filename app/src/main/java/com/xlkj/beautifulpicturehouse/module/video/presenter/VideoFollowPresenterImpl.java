package com.xlkj.beautifulpicturehouse.module.video.presenter;

import android.util.Log;

import com.xlkj.beautifulpicturehouse.module.video.bean.VideoFollowReqBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoFollowResBean;
import com.xlkj.beautifulpicturehouse.module.video.contract.VideoContract;
import com.xlkj.beautifulpicturehouse.module.video.model.VideoFollowModelImpl;

/**
 * Created by Administrator on 2018/1/3.
 * 视频关注页
 */

public class VideoFollowPresenterImpl implements VideoContract.IVideoFollowPresenter,VideoContract.OnVideoFollowListener {

    public static final String TAG = "-->VideoFollowPresenter";
    private VideoContract.IVideoFollowModel mModel;
    private VideoContract.IVideoFollowView mView;

    public VideoFollowPresenterImpl(VideoContract.IVideoFollowView mView) {
        this.mView = mView;
        mModel = new VideoFollowModelImpl();
    }

    @Override
    public void attachVideoFollow(VideoContract.IVideoFollowView view) {
        this.mView = view;
    }

    @Override
    public void getDataVideoFollow(VideoFollowReqBean bean) {
        mModel.loadDataVideoFollow(bean,this);
    }

    @Override
    public void detachVideoFollow() {
        mView = null;
    }

    @Override
    public void onErrorVideoFollow() {
        try {
            if (mView != null) {
                mView.showErrorVideoFollow();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"onError异常");
        }
    }

    @Override
    public void onReqSuccessVideoFollow(VideoFollowResBean bean) {
        mView.setDataVideoFollow(bean);
    }

    public void interruptHttp() {
        mModel.interruptVideoFollow();
    }

}
