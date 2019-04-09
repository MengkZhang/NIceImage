package com.xlkj.beautifulpicturehouse.module.home.view.ui.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.common.constant.BeautyContent;
import com.xlkj.beautifulpicturehouse.common.view.ui.activity.base.BaseStateActivity;
import com.xlkj.beautifulpicturehouse.common.view.widget.SwipeBackLayout;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomeHeadResBean;
import com.xlkj.beautifulpicturehouse.module.home.view.adapter.HomeMoreTypeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页 更多分类 显示全部标签
 */
public class HomeMoreTypeActivity extends BaseStateActivity {

    public static final String TAG = "-->>HomeMoreTypeActivi";
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
//    private List<String> mList = new ArrayList<>();
    private List<HomeHeadResBean.DataBean.TagListBean> mList = new ArrayList<>();
    private HomeMoreTypeAdapter mAdapter;
    private SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.activity_home_more_type);
        mSwipeBackLayout = new SwipeBackLayout(this);
        initTestData();
        initView();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mSwipeBackLayout.attachToActivity(this,SwipeBackLayout.EDGE_LEFT);
        // 触摸边缘变为屏幕宽度的1/2
        mSwipeBackLayout.setEdgeSize(getResources().getDisplayMetrics().widthPixels / 2);
    }

    public void onResume() {
        super.onResume();
        try {
            MobclickAgent.onResume(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void onPause() {
        super.onPause();
        try {
            MobclickAgent.onPause(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initTestData() {
        // TODO: 2018/2/6 报错
        //获取tag数据
        try {
            List<HomeHeadResBean.DataBean.TagListBean> tagList = BeautyContent.getTagList();
//        for (int i = 0; i < tagList.size(); i++) {
//            mList.add(tagList.get(i).getTagName());
//        }
            mList.addAll(tagList);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"initTestData异常");
        }

    }

    /**
     * 初始化控件
     */
    private void initView() {
        //返回键
        findViewById(R.id.beauty_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //标题
        ((TextView)findViewById(R.id.beauty_title)).setText("全部标签");
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_ahmt);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_ahmt);
        //改变加载显示的颜色
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_blue_light);
        //下拉刷新
        setRefresh();
        mLayoutManager = new GridLayoutManager(this, 4);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new HomeMoreTypeAdapter(this, mList);
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 下拉刷新
     */
    private void setRefresh() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    mSwipeRefreshLayout.setRefreshing(false);
//                    isRefresh = true;
//                    page = 1;
//                    loadData();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "setRefresh下拉刷新异常");
                }
            }
        });
    }
}
