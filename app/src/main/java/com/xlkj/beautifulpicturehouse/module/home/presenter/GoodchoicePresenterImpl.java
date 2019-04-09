package com.xlkj.beautifulpicturehouse.module.home.presenter;

import android.util.Log;

import com.xlkj.beautifulpicturehouse.module.home.bean.HomePicGoodChoiceReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomePicGoodChoiceResBean;
import com.xlkj.beautifulpicturehouse.module.home.contract.HomeContract;
import com.xlkj.beautifulpicturehouse.module.home.model.GoodChoiceModelImpl;

/**
 * Created by Administrator on 2017/12/29.
 */

public class GoodchoicePresenterImpl implements HomeContract.IGoodchoicePresenter,HomeContract.OnGoodChoiceListener {

    public static final String TAG = "-->GoodchoicePresenterI";
    private HomeContract.IGoodChoiceModel mModel;
    private HomeContract.IGoodChoiceView mView;

    public GoodchoicePresenterImpl() {
        mModel = new GoodChoiceModelImpl();
    }

    @Override
    public void attachGoodChoice(HomeContract.IGoodChoiceView view) {
        this.mView = view;
    }

    @Override
    public void getDataGoodChoice(HomePicGoodChoiceReqBean bean) {
        mModel.loadDataGoodChoice(bean,this);
    }

    @Override
    public void detachGoodChoice() {
        mView = null;
    }

    @Override
    public void onErrorGoodChoice() {
        try {
            if (mView != null) {
                mView.showErrorGoodChoice();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"onError异常");
        }
    }

    @Override
    public void onRequestSuccessGoodChoice(HomePicGoodChoiceResBean bean) {
        mView.setDataGoodChoice(bean);
    }

    public void interruptHttp() {
        mModel.interruptGoodChoice();
    }
}
