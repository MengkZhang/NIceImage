package com.xlkj.beautifulpicturehouse.module.mine.view.ui.activity;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.xlkj.beautifulpicturehouse.module.mine.bean.MyCollectionReqBean;
import com.xlkj.beautifulpicturehouse.module.mine.bean.MyFollowResBean;
import com.xlkj.beautifulpicturehouse.module.mine.contract.MineContract;
import com.xlkj.beautifulpicturehouse.module.mine.presenter.MyFollowPresenterImpl;
import com.xlkj.beautifulpicturehouse.module.mine.view.ui.fragment.MyFollowMasterFragment;
import com.xlkj.beautifulpicturehouse.module.mine.view.ui.fragment.MyFollowTagFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的关注activity
 */
public class MyFollowActivity extends BaseStateActivity implements MineContract.IMyFollowView{

    public static final String TAG = "-->MyFollowActivity";
    public static final String MY_FOLLOW = "我的关注";
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
    private MyFollowTabAdapter mMyFollowTabAdapter;
    //我的关注presenter
    private MyFollowPresenterImpl mMyFollowPresenter;
    //加载loadSir的布局
    private LinearLayout mLinearLayout;
    //loadSir
    private LoadService mLoadService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_follow);
        bindPresenter();
        initViews();
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

    private void initViews() {
        //返回键
        findViewById(R.id.beauty_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //标题
        ((TextView)findViewById(R.id.beauty_title)).setText(MY_FOLLOW);
        //隐藏分享
        findViewById(R.id.tv_follow_btl).setVisibility(View.GONE);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout_amf);
        mViewPager = (ViewPager) findViewById(R.id.view_pager_amf);
        mLinearLayout = (LinearLayout) findViewById(R.id.ll_content_amf);
    }

    /**
     * 请求数据
     */
    private void loadData() {
        MyCollectionReqBean bean = new MyCollectionReqBean();
        bean.setAction("followList");
        String uid = PreferencesUtils.getString(this, "uid");
        if (!uid.isEmpty() && uid != null && !uid.equals("")) {
            bean.setUserId(uid);
        } else {
            bean.setUserId("");
        }
        mMyFollowPresenter.getDataMyFollow(bean);
    }

    private void bindPresenter() {
        mMyFollowPresenter = new MyFollowPresenterImpl();
        mMyFollowPresenter.attachMyFollow(this);
    }

    private void unbindPresenter() {
        mMyFollowPresenter.interruptHttp();
        mMyFollowPresenter.detachMyFollow();
    }

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

   private void initTitleFragment() {
        tabTitles.add("标签");
        tabTitles.add("播主");
        mFragments.add(new MyFollowTagFragment());
        mFragments.add(new MyFollowMasterFragment());
    }

    private void initViewPager() {
        mMyFollowTabAdapter = new MyFollowTabAdapter(getSupportFragmentManager());
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(mMyFollowTabAdapter);
    }

    /**
     * 我的关注失败回调
     */
    @Override
    public void showErrorMyFollow() {
        Log.e(TAG,"访问数据失败");
        showFailedLoadSir();
    }

    /**
     * 我的关注成功回调
     * @param bean
     */
    @Override
    public void setDataMyFollow(MyFollowResBean bean) {
        Log.e(TAG,"我的关注数据："+bean.toString());
        if (bean != null) {
            showSuccessLoadSir();
            // TODO: 2018/1/9 把数据传过去
            MyFollowResBean.DataBean data = bean.getData();
            if (data != null) {
                initTitleFragment();
                initViewPager();
                if (data.getTagList() != null && data.getTagList().size() != 0) {
                    //有标签数据
                    List<MyFollowResBean.DataBean.TagListBean> tagList = data.getTagList();
                    BeautyContent.setFollowTagList(tagList);
                } else {
                    //没有标签数据
                    BeautyContent.setFollowTagList(null);
                }
                List<MyFollowResBean.DataBean.UserListBean> userList = data.getUserList();
                if (userList != null && userList.size() != 0) {
                    //有波主数据
                    BeautyContent.setFollowUserList(userList);
                } else {
                    //没有波主数据
                    BeautyContent.setFollowUserList(null);
                }

            } else {
                Log.e(TAG,"没有数据，没有标签和波主数据");
                noTagsUsers();
            }
        } else {
            Log.e(TAG,"数据为空");
            showFailedLoadSir();
        }
    }

    /**
     * 没有标签和波主数据
     */
    private void noTagsUsers() {
        //没有标签数据
        BeautyContent.setFollowTagList(null);
        //没有波主数据
        BeautyContent.setFollowUserList(null);
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
