package com.xlkj.beautifulpicturehouse.module.home.presenter;

import android.util.Log;

import com.xlkj.beautifulpicturehouse.module.home.bean.HomeHotNewResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomeHotPicResMgqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomePicGoodChoiceReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.HotListPicReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomeHotPicResBean;
import com.xlkj.beautifulpicturehouse.module.home.contract.HomeContract;
import com.xlkj.beautifulpicturehouse.module.home.model.HotPicListModelImpl;

/**
 * Created by Administrator on 2017/12/21.
 */

public class HotPicListPresenterImpl implements HomeContract.IHotPicListPresenter, HomeContract.OnHotPicListListener {

    public static final String TAG = "-->HotPicListPresenterI";
    private HomeContract.IHotPicListView mView;
    private HomeContract.IHotPicListModel mModel;

    public HotPicListPresenterImpl() {
        mModel = new HotPicListModelImpl();
    }

    @Override
    public void attachHotPicList(HomeContract.IHotPicListView view) {
        this.mView = view;
    }

    @Override
    public void getDataHotPicList(HomePicGoodChoiceReqBean bean) {
        mModel.loadDataHotPicList(bean,this);
    }

    @Override
    public void detachHotPicList() {
        try {
            if (mView != null) {
                mView = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"detachHotPicList异常");
        }
    }

    @Override
    public void onErrorHotPicList() {
        try {
            if (mView!= null) {
                mView.showErrorHotPicList();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"onErrorHotPicList异常");
        }
    }

    @Override
    public void onRequestSuccessHotPicList(HomeHotNewResBean bean) {
        mView.setDataHotPicList(bean);
    }

    public void interruptHttpHotPicList() {
        mModel.interruptHttpHotPicList();
    }
}
