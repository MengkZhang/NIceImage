package com.xlkj.beautifulpicturehouse.module.mine.presenter;

import android.util.Log;

import com.xlkj.beautifulpicturehouse.module.home.bean.AboutTagReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.AboutTagResBean;
import com.xlkj.beautifulpicturehouse.module.mine.bean.MyCollectionReqBean;
import com.xlkj.beautifulpicturehouse.module.mine.bean.MyFollowResBean;
import com.xlkj.beautifulpicturehouse.module.mine.contract.MineContract;
import com.xlkj.beautifulpicturehouse.module.mine.model.MyFollowModelImpl;

/**
 * Created by Administrator on 2018/1/9.
 * 我的关注presenter
 */

public class MyFollowPresenterImpl implements MineContract.IMyFollowPresenter,MineContract.OnMyFollowListener {

    public static final String TAG = "-->MyFollowPresenterI";
    private MineContract.IMyFollowModel mModel;
    private MineContract.IMyFollowView mView;

    public MyFollowPresenterImpl() {
        mModel = new MyFollowModelImpl();
    }

    @Override
    public void attachMyFollow(MineContract.IMyFollowView view) {
        this.mView = view;
    }

    @Override
    public void getDataMyFollow(MyCollectionReqBean bean) {
        mModel.loadDataMyFollow(bean,this);
    }

    @Override
    public void detachMyFollow() {
        mView = null;
    }

    @Override
    public void onErrorMyFollow() {
        try {
            if (mView != null) {
                mView.showErrorMyFollow();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"onError异常");
        }
    }

    @Override
    public void onReqSuccessMyFollow(MyFollowResBean bean) {
        mView.setDataMyFollow(bean);
    }

    public void interruptHttp() {
        mModel.interruptMyFollow();
    }
}
