package com.xlkj.beautifulpicturehouse.module.mine.presenter;

import android.util.Log;

import com.xlkj.beautifulpicturehouse.module.home.bean.AboutTagReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.AboutTagResBean;
import com.xlkj.beautifulpicturehouse.module.mine.bean.MyFootStayReqBean;
import com.xlkj.beautifulpicturehouse.module.mine.bean.MyFootStayResBean;
import com.xlkj.beautifulpicturehouse.module.mine.contract.MineContract;
import com.xlkj.beautifulpicturehouse.module.mine.model.MyFootStayModelImpl;

/**
 * Created by Administrator on 2018/1/10.
 * 我的足迹presenter
 */

public class MyFootStayPresenterImpl implements MineContract.IMyFootStayPresenter,MineContract.OnMyFootStayListener {
    public static final String TAG = "-->MyFollowPresenterI";
    private MineContract.IMyFootStayModel mModel;
    private MineContract.IMyFootStayView mView;

    /**可是这样写会内存泄露，因为如果在网络请求的过程中Activity就关闭了，Presenter还持有了V层的引用，也就是MainActivity，就会内存泄露。*/
//    public MyFootStayPresenterImpl(MineContract.IMyFootStayView mView) {
//         this.mView = mView;
//         mModel = new MyFootStayModelImpl();
//    }

    /**下面就来解决这个问题，我们将P层和V层的关联抽出两个方法，一个绑定，一个解绑，在需要的时候进行绑定V层，不需要的时候进行解绑就可以了。*/
    /**我们只需要修改上面Presenter中的构造代码，不需要在构造中传递V层了，然后再写一个绑定和解绑的方法，最后修改Activity创建Presenter时进行绑定，在onDestroy中进行解绑。*/
    public MyFootStayPresenterImpl() {
         mModel = new MyFootStayModelImpl();
    }

    @Override
    public void attachMyFootStay(MineContract.IMyFootStayView view) {
        this.mView = view;
    }

    @Override
    public void getDataMyFootStay(MyFootStayReqBean bean) {
        mModel.loadDataFootStay(bean,this);
    }

    @Override
    public void detachMyFootStay() {
        mView = null;
    }

    @Override
    public void onErrorMyFootStay() {
        try {
            if (mView != null) {
                mView.showErrorFootStay();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"onError异常");
        }
    }

    @Override
    public void onReqSuccessMyFootStay(MyFootStayResBean bean) {
        mView.setDataFootStay(bean);
    }

    public void interruptHttp() {
        mModel.interruptMyFootStay();
    }

}
