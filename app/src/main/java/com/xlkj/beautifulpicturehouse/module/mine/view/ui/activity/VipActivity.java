package com.xlkj.beautifulpicturehouse.module.mine.view.ui.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.common.view.ui.activity.base.BaseStateActivity;
import com.xlkj.beautifulpicturehouse.common.view.widget.SwipeBackLayout;
import com.xlkj.beautifulpicturehouse.module.mine.bean.VipResBean;
import com.xlkj.beautifulpicturehouse.module.mine.view.adapter.VIPAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * vip通道activity
 */
public class VipActivity extends BaseStateActivity {

    private RecyclerView mRecyclerView;
    private List<VipResBean> mList = new ArrayList<>();
    private VIPAdapter mVIPAdapter;
    private SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.activity_vip);
        initView();
        mSwipeBackLayout = new SwipeBackLayout(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mSwipeBackLayout.attachToActivity(this,SwipeBackLayout.EDGE_LEFT);
        // 触摸边缘变为屏幕宽度的1/2
        mSwipeBackLayout.setEdgeSize(getResources().getDisplayMetrics().widthPixels / 2);
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
        //隐藏分享
        findViewById(R.id.tv_follow_btl).setVisibility(View.GONE);
        //设置标题
        ((TextView)findViewById(R.id.beauty_title)).setText("VIP会员中心");
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_av);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(VipActivity.this));
        testList();
        mVIPAdapter = new VIPAdapter(this, mList);
        mRecyclerView.setAdapter(mVIPAdapter);
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

    private void testList() {
        VipResBean vipResBean = new VipResBean();
        vipResBean.setName("砖石VIP");
        vipResBean.setPrice("￥160.0");
        vipResBean.setMonth("12");
        mList.add(vipResBean);
        VipResBean vipResBean2 = new VipResBean();
        vipResBean2.setName("黄金VIP");
        vipResBean2.setPrice("￥90.0");
        vipResBean2.setMonth("6");
        mList.add(vipResBean2);
        VipResBean vipResBean3 = new VipResBean();
        vipResBean3.setName("白银VIP");
        vipResBean3.setPrice("￥50.0");
        vipResBean3.setMonth("3");
        mList.add(vipResBean3);
        VipResBean vipResBean4 = new VipResBean();
        vipResBean4.setName("青铜VIP");
        vipResBean4.setPrice("￥35.0");
        vipResBean4.setMonth("2");
        mList.add(vipResBean4);
        VipResBean vipResBean5 = new VipResBean();
        vipResBean5.setName("普通VIP");
        vipResBean5.setPrice("￥20.0");
        vipResBean5.setMonth("1");
        mList.add(vipResBean5);

    }
}
