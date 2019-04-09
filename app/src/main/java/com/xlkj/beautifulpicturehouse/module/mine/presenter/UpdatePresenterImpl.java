package com.xlkj.beautifulpicturehouse.module.mine.presenter;

import android.util.Log;

import com.xlkj.beautifulpicturehouse.module.mine.bean.CheckUpdateReqBean;
import com.xlkj.beautifulpicturehouse.module.mine.bean.CheckUpdateResBean;
import com.xlkj.beautifulpicturehouse.module.mine.contract.MineContract;
import com.xlkj.beautifulpicturehouse.module.mine.model.UpdateModelImpl;

/**
 * Created by Administrator on 2018/1/15.
 * 检测跟新presenter
 */

public class UpdatePresenterImpl implements MineContract.IUpdatePresenter,MineContract.OnUpdateListener {
    public static final String TAG = "-->UpdatePresenterImpl";
    private MineContract.IUpdateModel mModel;
    private MineContract.IUpdateView mView;

    public UpdatePresenterImpl() {
        mModel = new UpdateModelImpl();
    }

    @Override
    public void attachUpdate(MineContract.IUpdateView view) {
        this.mView = view;
    }

    @Override
    public void getDataUpdate(CheckUpdateReqBean bean) {
        mModel.loadDataUpdate(bean,this);
    }

    @Override
    public void detachUpdate() {
        mView = null;
    }

    @Override
    public void onErrorUpdate() {
        try {
            if (mView != null) {
                mView.showErrorUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"onError异常");
        }
    }

    @Override
    public void onReqSuccessUpdate(CheckUpdateResBean bean) {
        mView.setDataUpdate(bean);
    }

    public void interruptHttp() {
        mModel.interruptUpdate();
    }

}
