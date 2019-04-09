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
import com.xlkj.beautifulpicturehouse.common.manager.videoScanner.ScannerAsyncTask;

/**
 * A simple {@link Fragment} subclass.
 * 我的下载--视频
 */
public class MyDownLoadVideoFragment extends Fragment {


    private View mView;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private LinearLayout mLinearLayout;

    public MyDownLoadVideoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_my_down_load_video, container, false);
        initView();
        testAdaptData();
        return mView;
    }

    private void initView() {
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.rv_ma);
        mProgressBar = (ProgressBar) mView.findViewById(R.id.pd_ma);
        mLinearLayout = (LinearLayout) mView.findViewById(R.id.ll_no_data_amd);
    }

    private void testAdaptData() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/MTWVideo/video/";
        new ScannerAsyncTask(getContext(),mProgressBar,mRecyclerView,path,mLinearLayout).execute();
    }

}
