package com.xlkj.beautifulpicturehouse.module.mine.presenter;

import com.xlkj.beautifulpicturehouse.module.mine.bean.GetTokenOpenIdBean;
import com.xlkj.beautifulpicturehouse.module.mine.bean.ReqGetTokenOpenIdBean;
import com.xlkj.beautifulpicturehouse.module.mine.contract.MineContract;
import com.xlkj.beautifulpicturehouse.module.mine.model.WXLogin1ModelImpl;

/**
 * Created by Administrator on 2017/12/27.
 */

public class WXLogin1PresenterImpl implements MineContract.IWXLogin1Presenter,MineContract.OnWXLogin1Listener {

    private MineContract.IWXLogin1View mView;
    private MineContract.IWXLogin1Model mModel;

    public WXLogin1PresenterImpl(MineContract.IWXLogin1View view) {
        this.mView = mView;
        mModel = new WXLogin1ModelImpl();
    }

    @Override
    public void attachWXLogin1(MineContract.IWXLogin1View view) {
        this.mView = view;
    }

    @Override
    public void getDataWXLogin1(ReqGetTokenOpenIdBean bean) {
        mModel.loadDataWXLogin1(bean,this);
    }

    @Override
    public void detachWXLogin1() {
        mView = null;
    }

    @Override
    public void onErrorWXLogin1() {
        mView.showErrorWXLogin1();
    }

    @Override
    public void onReqSuccessWXLogin1(GetTokenOpenIdBean bean) {
        mView.setDataWXLogin1(bean);
    }

    public void interruptIWXLogin1() {
        mModel.interruptWXLogin1();
    }
}
