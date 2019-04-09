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
import com.xlkj.beautifulpicturehouse.module.mine.bean.MyFollowResBean;
import com.xlkj.beautifulpicturehouse.module.mine.view.adapter.MyFollowMasterAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * 我关注的波主fragment
 */
public class MyFollowMasterFragment extends Fragment {


    private View mView;
    private RecyclerView mRecyclerView;
    private List<String> mList = new ArrayList<>();
    private MyFollowMasterAdapter mMyFollowMasterAdapter;
    private List<MyFollowResBean.DataBean.UserListBean> mUserList;
    private RelativeLayout rlEmpty;
    private Context mContext;

    public MyFollowMasterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_my_follow_master, container, false);
        mContext = BeautyApplication.appContext;
        getDataFromActivity();
        initView();
        return mView;
    }

    private void getDataFromActivity() {
        mUserList = BeautyContent.getFollowUserList();
    }

    private void initView() {
        //空布局
        rlEmpty = (RelativeLayout) mView.findViewById(R.id.rl_empty_fmfm);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.rv_fmf);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        if (mUserList != null && mUserList.size() != 0) {
            mMyFollowMasterAdapter = new MyFollowMasterAdapter(mContext, mUserList);
            mRecyclerView.setAdapter(mMyFollowMasterAdapter);
        } else {
            //没有数据显示空布局
            rlEmpty.setVisibility(View.VISIBLE);
        }
    }



}
