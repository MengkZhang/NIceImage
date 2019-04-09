package com.xlkj.beautifulpicturehouse.module.video.presenter;

import android.util.Log;

import com.xlkj.beautifulpicturehouse.module.video.bean.HotVideoListReqBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.HotVideoListResBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoListReqBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoListResBean;
import com.xlkj.beautifulpicturehouse.module.video.contract.VideoContract;
import com.xlkj.beautifulpicturehouse.module.video.model.HotVideoListModelImpl;

/**
 * Created by Administrator on 2017/12/22.
 */

public class HotVideoListPresenterImpl implements VideoContract.IHotVideoListPresenter, VideoContract.OnHotVideoListListener {

    public static final String TAG = "-->HotVideoListPresent";
    private VideoContract.IHotVideoListView mView;
    private VideoContract.IHotVideoListModel mModel;

    public HotVideoListPresenterImpl() {
        mModel = new HotVideoListModelImpl();
    }

    @Override
    public void attachHotVideoList(VideoContract.IHotVideoListView view) {
        this.mView = view;
    }

    @Override
    public void getDataHotVideoList(VideoListReqBean bean) {
        mModel.loadDataHotVideoList(bean,this);
    }

    @Override
    public void detachHotVideoList() {
        mView = null;
    }

    public void interruptHotVideoList() {
        mModel.interruptHotVideoList();
    }

    @Override
    public void onErrorHotVideoList() {
        try {
            if (mView != null) {
                mView.showErrorHotVideoList();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"onError异常");
        }
    }

    @Override
    public void onReqSuccessHotVideoList(VideoListResBean bean) {
        mView.setDataHotVideoList(bean);
    }
}
