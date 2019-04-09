package com.xlkj.beautifulpicturehouse.module.mine.view.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.xlkj.beautifulpicturehouse.BeautyApplication;
import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.common.constant.BeautyContent;
import com.xlkj.beautifulpicturehouse.module.mine.bean.MyCollectionResBean;
import com.xlkj.beautifulpicturehouse.module.mine.view.adapter.MyCollectionAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * 我的收藏图片fragment
 */
public class MyCollectionPicFragment extends Fragment {

    private View mView;
    private List<MyCollectionResBean.DataBean.ImageListBean> mImageList;
    private RecyclerView mRecyclerView;
    ////空布局
    private RelativeLayout rlEmpty;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_my_collection_pic, container, false);
        mContext = BeautyApplication.appContext;
        getDataFromActivity();
        initView();
        return mView;
    }

    private void initView() {
        //空布局
        rlEmpty = (RelativeLayout) mView.findViewById(R.id.rl_empty_fmcp);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.rv_fmcp);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        if (mImageList != null && mImageList.size() != 0) {
            //有数据
            MyCollectionAdapter myCollectionAdapter = new MyCollectionAdapter(mContext, mImageList);
            mRecyclerView.setAdapter(myCollectionAdapter);
        } else {
            //没有数据-显示空布局
            rlEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void getDataFromActivity() {
        mImageList = BeautyContent.getImageList();
    }

}
