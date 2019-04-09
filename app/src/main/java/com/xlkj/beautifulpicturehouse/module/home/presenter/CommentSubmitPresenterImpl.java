package com.xlkj.beautifulpicturehouse.module.home.presenter;

import android.util.Log;

import com.bumptech.glide.load.resource.bitmap.BitmapEncoder;
import com.xlkj.beautifulpicturehouse.module.home.bean.CommentSubmitReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.CommentSubmitResBean;
import com.xlkj.beautifulpicturehouse.module.home.contract.HomeContract;
import com.xlkj.beautifulpicturehouse.module.home.model.CommentSubmitModelImpl;

/**
 * Created by Administrator on 2018/1/15.
 * 提交评论presenter
 */

public class CommentSubmitPresenterImpl implements HomeContract.ICommentSubmitPresenter,HomeContract.OnCommentSubmitListener {

    public static final String TAG = "-->CommentSubmitP";
    private HomeContract.ICommentSubmitModel mModel;
    private HomeContract.ICommentSubmitView mView;

    public CommentSubmitPresenterImpl() {
        mModel = new CommentSubmitModelImpl();
    }

    @Override
    public void attachCommentSubmit(HomeContract.ICommentSubmitView view) {
        this.mView = view;
    }

    @Override
    public void getDataCommentSubmit(CommentSubmitReqBean bean) {
        mModel.loadDataCommentSubmit(bean,this);
    }

    @Override
    public void detachCommentSubmit() {
        mView = null;
    }

    @Override
    public void onErrorCommentSubmit() {
        try {
            if (mView != null) {
                mView.showErrorCommentSubmit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"onError异常");
        }
    }

    @Override
    public void onRequestSuccessCommentSubmit(CommentSubmitResBean bean) {
        mView.setDataCommentSubmit(bean);
    }

    public void interruptHttp() {
        mModel.interruptCommentSubmit();
    }
}
