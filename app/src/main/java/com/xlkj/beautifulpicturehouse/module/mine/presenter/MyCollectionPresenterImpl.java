package com.xlkj.beautifulpicturehouse.module.mine.presenter;

import android.util.Log;

import com.xlkj.beautifulpicturehouse.module.home.bean.AboutTagReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.AboutTagResBean;
import com.xlkj.beautifulpicturehouse.module.mine.bean.MyCollectionReqBean;
import com.xlkj.beautifulpicturehouse.module.mine.bean.MyCollectionResBean;
import com.xlkj.beautifulpicturehouse.module.mine.contract.MineContract;
import com.xlkj.beautifulpicturehouse.module.mine.model.MyCollectionModelImpl;

/**
 * Created by Administrator on 2018/1/10.
 * 我的收藏presenter
 *
 */

public class MyCollectionPresenterImpl implements MineContract.IMyCollectionPresenter,MineContract.OnMyCollectionListener {

    public static final String TAG = "-->MyFollowPresenterI";
    private MineContract.IMyCollectionModel mModel;
    private MineContract.IMyCollectionView mView;

    public MyCollectionPresenterImpl() {
        mModel = new MyCollectionModelImpl();
    }

    @Override
    public void attachMyCollection(MineContract.IMyCollectionView view) {
        this.mView = view;
    }

    @Override
    public void getDataMyCollection(MyCollectionReqBean bean) {
        mModel.loadDataCollection(bean,this);
    }

    @Override
    public void detachMyCollection() {
        mView = null;
    }

    @Override
    public void onErrorMyCollection() {
        try {
            if (mView != null) {
                mView.showErrorMyCollection();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"onError异常");
        }
    }

    @Override
    public void onReqSuccessMyCollection(MyCollectionResBean bean) {
        mView.setDataMyCollection(bean);
    }

    public void interruptHttp() {
        mModel.interruptMyCollection();
    }
}
