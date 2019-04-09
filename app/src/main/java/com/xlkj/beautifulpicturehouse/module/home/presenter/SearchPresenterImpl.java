package com.xlkj.beautifulpicturehouse.module.home.presenter;

import android.util.Log;

import com.xlkj.beautifulpicturehouse.module.home.bean.SearchReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.SearchResBean;
import com.xlkj.beautifulpicturehouse.module.home.contract.HomeContract;
import com.xlkj.beautifulpicturehouse.module.home.model.SearchModelImpl;

/**
 * Created by Administrator on 2018/1/2.
 */

public class SearchPresenterImpl implements HomeContract.ISearchPresenter,HomeContract.OnSearchListener {
    public static final String TAG = "-->SearchPresenterImpl";
    private HomeContract.ISearchModel mModel;
    private HomeContract.ISearchView mView;

//    public SearchPresenterImpl(HomeContract.ISearchView mView) {
//        this.mView = mView;
//        mModel = new SearchModelImpl();
//    }
    public SearchPresenterImpl() {
        mModel = new SearchModelImpl();
    }

    @Override
    public void attachSearch(HomeContract.ISearchView view) {
        this.mView = view;
    }

    @Override
    public void getDataSearch(SearchReqBean bean) {
        mModel.loadDataSearch(bean,this);
    }

    @Override
    public void detachSearch() {
        mView = null;
    }

    public void interruptHttp() {
        mModel.interruptSearch();
    }

    @Override
    public void onErrorSearch() {
        try {
            if (mView != null) {
                mView.showErrorSearch();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"onError异常");
        }
    }

    @Override
    public void onRequestSuccessSearch(SearchResBean bean) {
        try {
            if (mView != null) {
                mView.setDataSearch(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"onRequestSuccessSearch异常");
        }
    }
}
