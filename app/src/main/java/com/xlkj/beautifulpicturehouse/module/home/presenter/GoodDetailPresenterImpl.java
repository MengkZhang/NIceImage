package com.xlkj.beautifulpicturehouse.module.home.presenter;

import android.util.Log;

import com.xlkj.beautifulpicturehouse.module.home.bean.GoodDetailReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.GoodDetailResBean;
import com.xlkj.beautifulpicturehouse.module.home.contract.HomeContract;
import com.xlkj.beautifulpicturehouse.module.home.model.GoodDetailModelImpl;

/**
 * Created by Administrator on 2018/1/2.
 */

public class GoodDetailPresenterImpl implements HomeContract.IGoodDetailPresenter,HomeContract.OnGoodDetailListener {
    public static final String TAG = "-->GoodDetailPresenterI";
    private HomeContract.IGoodDetailModel mModel;
    private HomeContract.IGoodDetailView mView;

    public GoodDetailPresenterImpl() {
        mModel = new GoodDetailModelImpl();
    }

    @Override
    public void attachGoodDetail(HomeContract.IGoodDetailView view) {
        this.mView = view;
    }

    @Override
    public void getDataGoodDetail(GoodDetailReqBean bean) {
        mModel.loadDataGoodDetail(bean,this);
    }

    @Override
    public void detachGoodDetail() {
        mView = null;
    }

    @Override
    public void onErrorGoodDetail() {
        try {
            if (mView != null) {
                mView.showErrorGoodDetail();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"onError异常");
        }
    }

    @Override
    public void onRequestSuccessGoodDetail(GoodDetailResBean bean) {
        mView.setDataGoodDetail(bean);
    }

    public void interruptHttp() {
        mModel.interruptGoodDetail();
    }
}
