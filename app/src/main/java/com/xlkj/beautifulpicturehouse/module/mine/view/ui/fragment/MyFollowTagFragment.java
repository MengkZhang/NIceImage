package com.xlkj.beautifulpicturehouse.module.mine.view.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.xlkj.beautifulpicturehouse.BeautyApplication;
import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.common.constant.BeautyContent;
import com.xlkj.beautifulpicturehouse.module.mine.bean.MyFollowResBean;
import com.xlkj.beautifulpicturehouse.module.mine.view.adapter.MyFollowTagsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * 我关注的标签fragment
 */
public class MyFollowTagFragment extends Fragment {

    private View mView;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private MyFollowTagsAdapter mMyFollowTagsAdapter;
    private List<MyFollowResBean.DataBean.TagListBean> mFollowTagList;
    //空布局
    private RelativeLayout rlEmpty;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_my_follow_tag, container, false);
        mContext = BeautyApplication.appContext;
        getDataFromActivity();
        initView();
        return mView;
    }

    private void getDataFromActivity() {
        mFollowTagList = BeautyContent.getFollowTagList();
    }

    private void initView() {
        //空布局
        rlEmpty = (RelativeLayout) mView.findViewById(R.id.rl_empty_fmft);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.rv_fmft);
        mLayoutManager = new GridLayoutManager(mContext, 4);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        if (mFollowTagList != null && mFollowTagList.size() != 0) {
            mMyFollowTagsAdapter = new MyFollowTagsAdapter(mContext, mFollowTagList);
            mRecyclerView.setAdapter(mMyFollowTagsAdapter);
        } else {
            //没有数据 显示空布局
            rlEmpty.setVisibility(View.VISIBLE);
        }
    }


}
