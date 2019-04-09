package com.xlkj.beautifulpicturehouse.module.mine.view.ui.activity;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.common.view.ui.activity.base.BaseStateActivity;
import com.xlkj.beautifulpicturehouse.module.mine.view.ui.fragment.MyDownLoadPicFragment;
import com.xlkj.beautifulpicturehouse.module.mine.view.ui.fragment.MyDownLoadVideoFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的下载activity
 */
public class MyDownLoadActivity extends BaseStateActivity {

    public static final String MY_DOWNLOAD = "我的下载";
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    /**
     * tab的标题
     */
    private List<String> tabTitles = new ArrayList<>();
    /**
     * viewpager中的fragment
     */
    private List<Fragment> mFragments = new ArrayList<>();
    private MyDownloadTabAdapter mMyDownloadTabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_down_load);
        initTitleFragment();
        initView();
    }

    private void initView() {
        //返回键
        findViewById(R.id.beauty_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //标题
        ((TextView)findViewById(R.id.beauty_title)).setText(MY_DOWNLOAD);
        //隐藏分享
        findViewById(R.id.tv_follow_btl).setVisibility(View.GONE);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout_amdl);
        mViewPager = (ViewPager) findViewById(R.id.view_pager_amdl);
        mMyDownloadTabAdapter = new MyDownloadTabAdapter(getSupportFragmentManager());
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(mMyDownloadTabAdapter);
    }

    private void initTitleFragment() {
        tabTitles.add("图片");
        tabTitles.add("视频");
        mFragments.add(new MyDownLoadPicFragment());
        mFragments.add(new MyDownLoadVideoFragment());
    }

    /**
     * 自定义适配器
     */
    class MyDownloadTabAdapter extends FragmentStatePagerAdapter {

        public MyDownloadTabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles.get(position);
        }
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
}
