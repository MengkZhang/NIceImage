package com.xlkj.beautifulpicturehouse.module.home.presenter;

import android.util.Log;

import com.xlkj.beautifulpicturehouse.module.home.bean.FollowReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.FollowResBean;
import com.xlkj.beautifulpicturehouse.module.home.contract.HomeContract;
import com.xlkj.beautifulpicturehouse.module.home.model.FollowModelImpl;

/**
 * Created by Administrator on 2018/1/8.
 * 关注或取消关注的presenter
 */

public class FollowPresenterImpl implements HomeContract.IFollowPresenter,HomeContract.OnFollowListener {

    public static final String TAG = "-->FollowPresenterImpl";
    private HomeContract.IFollowModel mModel;
    private HomeContract.IFollowView mView;

    public FollowPresenterImpl() {
        mModel = new FollowModelImpl();
    }

    @Override
    public void attachFollow(HomeContract.IFollowView view) {
        this.mView = view;
    }

    @Override
    public void getDataFollow(FollowReqBean bean) {
        mModel.loadDataFollow(bean,this);
    }

    @Override
    public void detachFollow() {
        mView = null;
    }

    @Override
    public void onErrorFollow() {
        try {
            if (mView != null) {
                mView.showErrorFollow();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"onError异常");
        }
    }

    @Override
    public void onRequestSuccessFollow(FollowResBean bean) {
        mView.setDataFollow(bean);
    }

    public void interruptHttp() {
        mModel.interruptFollow();
    }

}
