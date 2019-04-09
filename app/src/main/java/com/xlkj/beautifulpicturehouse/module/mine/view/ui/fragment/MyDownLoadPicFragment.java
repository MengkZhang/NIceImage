package com.xlkj.beautifulpicturehouse.module.mine.view.ui.fragment;


import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.common.manager.videoScanner.ScannerPicAsyncTask;

/**
 * A simple {@link Fragment} subclass.
 * 我的下载--图片
 */
public class MyDownLoadPicFragment extends Fragment {


    private View mView;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private LinearLayout mLinearLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_my_down_load_pic, container, false);
        initView();
        testAdaptData();
        return mView;
    }

    private void testAdaptData() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/meinvwu/";
        new ScannerPicAsyncTask(getContext(),mProgressBar,mRecyclerView,path,mLinearLayout).execute();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.rv_ma_pic);
        mProgressBar = (ProgressBar) mView.findViewById(R.id.pd_ma_pic);
        mLinearLayout = (LinearLayout) mView.findViewById(R.id.ll_no_data_amd_pic);
    }

}
