package com.xlkj.beautifulpicturehouse.module.home.presenter;

import android.util.Log;

import com.xlkj.beautifulpicturehouse.module.home.bean.CollectionReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.CollectionResBean;
import com.xlkj.beautifulpicturehouse.module.home.contract.HomeContract;
import com.xlkj.beautifulpicturehouse.module.home.model.CollectionModelImpl;

/**
 * Created by Administrator on 2018/1/5.
 * 收藏或者取消收藏presenter
 */

public class CollectionPresenterImpl implements HomeContract.ICollectionPresenter,HomeContract.OnCollectionListener {

    public static final String TAG = "-->CollectionPresenterI";
    private HomeContract.ICollectionView mView;
    private HomeContract.ICollectionModel mModel;

    public CollectionPresenterImpl() {
        mModel = new CollectionModelImpl();
    }

    @Override
    public void attachCollection(HomeContract.ICollectionView view) {
        this.mView = view;
    }

    @Override
    public void getDataCollection(CollectionReqBean bean) {
        mModel.loadDataCollection(bean,this);
    }

    @Override
    public void detachCollection() {
        mView = null;
    }

    @Override
    public void onErrorCollection() {
        try {
            if (mView != null) {
                mView.showErrorCollection();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"onError异常");
        }
    }

    @Override
    public void onRequestSuccessCollection(CollectionResBean bean) {
        mView.setDataCollection(bean);
    }

    public void interruptHttp() {
        mModel.interruptCollection();
    }

}
