package com.xlkj.beautifulpicturehouse.module.home.presenter;

import android.util.Log;

import com.bumptech.glide.load.resource.bitmap.BitmapEncoder;
import com.xlkj.beautifulpicturehouse.module.home.bean.CommentListReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.CommentListResBean;
import com.xlkj.beautifulpicturehouse.module.home.contract.HomeContract;
import com.xlkj.beautifulpicturehouse.module.home.model.CommentListModelImpl;

/**
 * Created by Administrator on 2018/1/15.
 * 获取评论列表presenter
 */

public class CommentListPresenterImpl implements HomeContract.ICommentListPresenter,HomeContract.OnCommentListListener {
    public static final String TAG = "-->CommentListP";
    private HomeContract.ICommentListModel mModel;
    private HomeContract.ICommentListView mView;

    public CommentListPresenterImpl() {
        mModel = new CommentListModelImpl();
    }

    @Override
    public void attachCommentList(HomeContract.ICommentListView view) {
        this.mView = view;
    }

    @Override
    public void getDataCommentList(CommentListReqBean bean) {
        mModel.loadDataCommentList(bean,this);
    }

    @Override
    public void detachCommentList() {
        mView = null;
    }

    @Override
    public void onErrorCommentList() {
        try {
            if (mView != null) {
                mView.showErrorCommentList();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"onError异常");
        }
    }

    @Override
    public void onRequestSuccessCommentList(CommentListResBean bean) {
        mView.setDataCommentList(bean);
    }

    public void interruptHttp() {
        mModel.interruptCommentList();
    }

}
