package com.xlkj.beautifulpicturehouse.module.video.presenter;

import android.util.Log;

import com.xlkj.beautifulpicturehouse.module.video.bean.VideoDetailReqBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoDetailResBean;
import com.xlkj.beautifulpicturehouse.module.video.contract.VideoContract;
import com.xlkj.beautifulpicturehouse.module.video.model.VideoDetailModelImpl;

/**
 * Created by Administrator on 2018/1/11.
 * 视频详情presenter
 */

public class VideoDetailPresenterImpl implements VideoContract.IVideoDetailPresenter,VideoContract.OnVideoDetailListener {
    public static final String TAG = "-->VideoDetailPresenter";
    public VideoContract.IVideoDetailModel mModel;
    public VideoContract.IVideoDetailView mView;

    public VideoDetailPresenterImpl() {
        mModel = new VideoDetailModelImpl();
    }

    @Override
    public void attachVideoDetail(VideoContract.IVideoDetailView view) {
        this.mView = view;
    }

    @Override
    public void getDataVideoDetail(VideoDetailReqBean bean) {
        mModel.loadDataVideoDetail(bean,this);
    }

    @Override
    public void detachVideoDetail() {
        mView = null;
    }

    @Override
    public void onErrorVideoDetail() {
        try {
            if (mView != null) {
                mView.showErrorVideoDetail();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"onError异常");
        }
    }

    @Override
    public void onReqSuccessVideoDetail(VideoDetailResBean bean) {
        mView.setDataVideoDetail(bean);
    }

    public void interruptHotVideoList() {
        mModel.interruptVideoDetail();
    }

}
