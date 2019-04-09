package com.xlkj.beautifulpicturehouse.module.video.presenter;

import android.util.Log;

import com.xlkj.beautifulpicturehouse.module.video.bean.VideoSearchReqBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoSearchResBean;
import com.xlkj.beautifulpicturehouse.module.video.contract.VideoContract;
import com.xlkj.beautifulpicturehouse.module.video.model.VideoSearchModelImpl;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/16.
 * 搜索更多视频presenter
 */

public class VideoSearchPresenterImpl implements VideoContract.IVideoSearchPresenter,VideoContract.OnVideoSearchListener {

    public static final String TAG = "-->VideoSearchP";
    private VideoContract.IVideoSearchModel mModel;
    private VideoContract.IVideoSearchView mView;

    public VideoSearchPresenterImpl() {
        mModel = new VideoSearchModelImpl();
    }

    @Override
    public void attachVideoSearch(VideoContract.IVideoSearchView view) {
        this.mView = view;
    }

    @Override
    public void getDataVideoSearch(VideoSearchReqBean bean) {
        mModel.loadDataVideoSearch(bean,this);
    }

    @Override
    public void detachVideoSearch() {
        mView = null;
    }

    @Override
    public void onErrorVideoSearch() {
        try {
            if (mView != null) {
                mView.showErrorVideoSearch();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"onError异常");
        }
    }

    @Override
    public void onReqSuccessVideoSearch(VideoSearchResBean bean) {
        mView.setDataVideoSearch(bean);
    }

    public void interruptHttp() {
        mModel.interruptVideoSearch();
    }
}
