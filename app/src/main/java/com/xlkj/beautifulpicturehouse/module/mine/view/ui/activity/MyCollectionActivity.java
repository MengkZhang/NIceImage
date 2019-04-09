package com.xlkj.beautifulpicturehouse.module.mine.view.ui.activity;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.kingja.loadsir.core.Transport;
import com.umeng.analytics.MobclickAgent;
import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.common.constant.BeautyContent;
import com.xlkj.beautifulpicturehouse.common.load.PostUtil;
import com.xlkj.beautifulpicturehouse.common.load.callback.EmptyCallback;
import com.xlkj.beautifulpicturehouse.common.load.callback.ErrorCallback;
import com.xlkj.beautifulpicturehouse.common.load.callback.LoadingCallback;
import com.xlkj.beautifulpicturehouse.common.util.PreferencesUtils;
import com.xlkj.beautifulpicturehouse.common.view.ui.activity.base.BaseStateActivity;
import com.xlkj.beautifulpicturehouse.module.home.bean.AboutTagReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.AboutTagResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.CollectionResBean;
import com.xlkj.beautifulpicturehouse.module.home.contract.HomeContract;
import com.xlkj.beautifulpicturehouse.module.mine.bean.MyCollectionReqBean;
import com.xlkj.beautifulpicturehouse.module.mine.bean.MyCollectionResBean;
import com.xlkj.beautifulpicturehouse.module.mine.contract.MineContract;
import com.xlkj.beautifulpicturehouse.module.mine.presenter.MyCollectionPresenterImpl;
import com.xlkj.beautifulpicturehouse.module.mine.view.adapter.MyCollectionAdapter;
import com.xlkj.beautifulpicturehouse.module.mine.view.ui.fragment.MyCollectionPicFragment;
import com.xlkj.beautifulpicturehouse.module.mine.view.ui.fragment.MyCollectionVideoFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的收藏activity
 */
public class MyCollectionActivity extends BaseStateActivity implements MineContract.IMyCollectionView{

    public static final String TAG = "-->MyCollectionActivity";
    public static final String MY_COLLECTION = "我的收藏";
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private MyFollowTabAdapter mMyFollowTabAdapter;
    private MyCollectionPresenterImpl mMyCollectionPresenter;
    //加载loadSir的布局
    private LinearLayout mLinearLayout;
    //loadSir
    private LoadService mLoadService;

    //tab的标题
    private List<String> tabTitles = new ArrayList<>();
    //viewpager中的fragment
    private List<Fragment> mFragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_my_collection);
        setContentView(R.layout.activity_my_follow);
        bindPresenter();
        initView();
        loadData();
        initLoadSir();
    }

    /**
     * 重新加载数据
     */
    private void initLoadSir() {
        try {
            mLoadService = LoadSir.getDefault().register(mLinearLayout, new Callback.OnReloadListener() {
                @Override
                public void onReload(View v) {
                    // TODO: 2017/12/21 重新加载的逻辑
                    mLoadService.showCallback(LoadingCallback.class);
                    loadData();
                }
            }).setCallBack(EmptyCallback.class, new Transport() {
                @Override
                public void order(Context context, View view) {
                    // TODO: 2017/12/21 没有数据
//                    Toast.makeText(VideoDetailActivity.this, "没有数据啊亲", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "初始化load异常initLoadSir");
        }
        //发送正在加载数据的消息
        PostUtil.postCallbackDelayed(mLoadService, LoadingCallback.class);
    }

    /**
     * 发送请求成功的消息
     */
    private void showSuccessLoadSir() {
        PostUtil.postSuccessDelayed(mLoadService);
    }

    /**
     * 发送请求失败的消息
     */
    private void showFailedLoadSir() {
        //发送请求失败的消息
        PostUtil.postCallbackDelayed(mLoadService, ErrorCallback.class);
    }

    /**
     * 绑定p
     */
    private void bindPresenter() {
        mMyCollectionPresenter = new MyCollectionPresenterImpl();
        mMyCollectionPresenter.attachMyCollection(this);
    }

    /**
     * 解绑p
     */
    private void unbindPresenter() {
        if (mMyCollectionPresenter != null) {
            mMyCollectionPresenter.detachMyCollection();
            mMyCollectionPresenter.interruptHttp();
        }
    }

    /**
     * 请求数据
     */
    private void loadData() {
        MyCollectionReqBean bean = new MyCollectionReqBean();
        bean.setAction("collectionList");
        String uid = PreferencesUtils.getString(this, "uid");
        if (!uid.isEmpty() && uid != null && !uid.equals("")) {
            bean.setUserId(uid);
        } else {
            bean.setUserId("");
        }
        mMyCollectionPresenter.getDataMyCollection(bean);
    }

    /**
     * 页面onDestroy做解绑操作
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindPresenter();
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

    /**
     * 初始化控件
     */
    private void initView() {
        //返回键
        findViewById(R.id.beauty_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyCollectionActivity.this.finish();
            }
        });
        //标题
        ((TextView)findViewById(R.id.beauty_title)).setText(MY_COLLECTION);
        //隐藏分享
        findViewById(R.id.tv_follow_btl).setVisibility(View.GONE);
        //加载loadSir的布局
        mLinearLayout = (LinearLayout) findViewById(R.id.ll_content_amf);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout_amf);
        mViewPager = (ViewPager) findViewById(R.id.view_pager_amf);
    }


    /**
     * 访问我的收藏数据失败回调
     */
    @Override
    public void showErrorMyCollection() {
        Log.e(TAG,"请求数据失败");
        showFailedLoadSir();
    }

    /**
     * 访问我的收藏数据成功回调
     * @param bean
     */
    @Override
    public void setDataMyCollection(MyCollectionResBean bean) {
        if (bean != null) {
            showSuccessLoadSir();
            // TODO: 2018/1/10 适配数据
            MyCollectionResBean.DataBean data = bean.getData();
            if (data != null) {
                initTitleFragment();
                initViewPager();
                if (data.getImageList() != null && data.getImageList().size() != 0) {
                    List<MyCollectionResBean.DataBean.ImageListBean> imageList = data.getImageList();
                    //有图片数据
                    BeautyContent.setImageList(imageList);
                } else {
                    //没有图片数据
                    BeautyContent.setImageList(null);
                }
                if (data.getVideoList() != null && data.getVideoList().size() != 0) {
                    //有视频数据
                    List<MyCollectionResBean.DataBean.VideoListBean> videoList = data.getVideoList();
                    BeautyContent.setVideoList(videoList);
                } else {
                    //没有视频数据
                    BeautyContent.setVideoList(null);
                }
            } else {
                Log.e(TAG,"没有数据，没有图片和视频数据");
                noImgsVideos();
            }

        } else {
            Log.e(TAG,"数据为空");
            showFailedLoadSir();
        }
    }

    /**
     * 没有图片数据没有视频数据
     */
    private void noImgsVideos() {
        //没有图片数据
        BeautyContent.setImageList(null);
        //没有视频数据
        BeautyContent.setVideoList(null);
    }

    private void initTitleFragment() {
        tabTitles.add("美图");
        tabTitles.add("视频");
        mFragments.add(new MyCollectionPicFragment());
        mFragments.add(new MyCollectionVideoFragment());
    }

    private void initViewPager() {
        mMyFollowTabAdapter = new MyFollowTabAdapter(getSupportFragmentManager());
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(mMyFollowTabAdapter);
    }
    /**
     * 自定义适配器
     */
    class MyFollowTabAdapter extends FragmentStatePagerAdapter {

        public MyFollowTabAdapter(FragmentManager fm) {
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
}
