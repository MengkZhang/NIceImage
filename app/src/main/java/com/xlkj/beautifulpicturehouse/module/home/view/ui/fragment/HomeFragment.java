package com.xlkj.beautifulpicturehouse.module.home.view.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.kingja.loadsir.core.Transport;
import com.xlkj.beautifulpicturehouse.BeautyApplication;
import com.xlkj.beautifulpicturehouse.common.constant.BeautyContent;
import com.xlkj.beautifulpicturehouse.common.load.PostUtil;
import com.xlkj.beautifulpicturehouse.common.load.callback.EmptyCallback;
import com.xlkj.beautifulpicturehouse.common.load.callback.ErrorCallback;
import com.xlkj.beautifulpicturehouse.common.load.callback.LoadingCallback;
import com.xlkj.beautifulpicturehouse.common.rxbus.RxBus;
import com.xlkj.beautifulpicturehouse.common.util.FileUtils;
import com.xlkj.beautifulpicturehouse.common.util.GlideImageLoader;
import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.common.view.ui.activity.SearchActivity;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomeHeadResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomeTagBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.Hot2RefreshBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.Refresh2hotBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.WeatherBean;
import com.xlkj.beautifulpicturehouse.module.home.contract.HomeContract;
import com.xlkj.beautifulpicturehouse.module.home.presenter.TestGetPresenterImpl;
import com.xlkj.beautifulpicturehouse.module.home.view.adapter.HomeTagTypeAdapter;
import com.xlkj.beautifulpicturehouse.module.home.view.ui.activity.HomeMoreTypeActivity;
import com.xlkj.beautifulpicturehouse.module.home.view.ui.activity.HomeTypeActivity;
import com.xlkj.beautifulpicturehouse.module.video.view.ui.fragment.VideoHotFragment;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.functions.Action1;


/**
 * HomeFragment
 */
public class HomeFragment extends Fragment implements HomeContract.ITestGetView{

    public static final String TAG = "-->>HomeFragment->";
    private View mView;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<String> titleList = new ArrayList<>();
    private List<Fragment> mFragments = new ArrayList<>();
    private HomeTabAdapter mAdapter;
    private Banner mBanner;
    private List<String>  images = new ArrayList();
    private List<String>  titles = new ArrayList();
    private TestGetPresenterImpl mPresenter;
    private AppBarLayout mAppBarLayout;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView rvType;
    //请求首页头部数据的action参数
    private String homeHeadAction = "getInfoList";
    //存放头部的tags数据
    private List<HomeHeadResBean.DataBean.TagListBean> mTagList = new ArrayList<>();
    //要显示的tag个数
    private int mShowSize;
    //首页中tag的adapter
    private HomeTagTypeAdapter mHomeTagTypeAdapter;
    //loadSir
    private LoadService mLoadService1;
    //viewpager的当前位置 默认是0
    private int mViewPagerCurrentPosition = 0;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home2, container, false);
        mContext = BeautyApplication.appContext;
        mPresenter = new TestGetPresenterImpl();
        //绑定
        mPresenter.attach(this);
        initView();
        initRxBus();
        try {
            mLoadService1 = LoadSir.getDefault().register(mView, new Callback.OnReloadListener() {
                @Override
                public void onReload(View v) {
                    mLoadService1.showCallback(LoadingCallback.class);
                    //请求首页上面部分的数据
                    mPresenter.getData(homeHeadAction);
                }
            });
            return mLoadService1.getLoadLayout();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"加载loadSir异常");
            return mView;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // TODO: 2017/12/28  在这里请求网络
        //请求首页上面部分的数据
        mPresenter.getData(homeHeadAction);
    }

    /**
     * 初始化RxBus
     */
    private void initRxBus() {
        Subscription subscription = RxBus.getInstance().doSubscribe(Hot2RefreshBean.class, new Action1<Hot2RefreshBean>() {
            @Override
            public void call(Hot2RefreshBean bean) {
                boolean refreshSuccess = bean.isRefreshSuccess();
                mSwipeRefreshLayout.setRefreshing(false);
                if (refreshSuccess) {
                    //Toast.makeText(getContext(), "刷新成功"+mViewPagerCurrentPosition, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "刷新失败", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e(TAG,"是否接受数据22："+throwable.getMessage());
            }
        });
        RxBus.getInstance().addSubscription(this,subscription);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.srl_fh2);
        //改变加载显示的颜色
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_blue_light);
        mAppBarLayout = (AppBarLayout) mView.findViewById(R.id.app_bar_fh2);
        //监听 AppBarLayout Offset 变化，动态设置 SwipeRefreshLayout 是否可用
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    mSwipeRefreshLayout.setEnabled(true);
                } else {
                    mSwipeRefreshLayout.setEnabled(false);
                }
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // TODO: 2017/12/25 RxBus发送消息
                Refresh2hotBean bean = new Refresh2hotBean();
                //传递viewpager当前的位置 0 1 2对应fragment中的mid
                bean.setPosition(mViewPagerCurrentPosition);
                bean.setRefreshData(true);
                RxBus.getInstance().post(bean);
            }
        });
        //tabLayout和ViewPager
        mTabLayout = (TabLayout) mView.findViewById(R.id.tab_layout_fh);
        mViewPager = (ViewPager) mView.findViewById(R.id.view_pager_fh);
        mAdapter = new HomeTabAdapter(getActivity().getSupportFragmentManager(), titleList);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(mAdapter);
        getViewPagerCurrentPage();
        //设置缓存的view个数2 + 1 （当前正在显示的1）
        //mViewPager.setOffscreenPageLimit(1);
        //banner
        mBanner = (Banner) mView.findViewById(R.id.banner_fh);
        //分类的RecyclerView 水平滑动控件 美胸 性感清纯等
        rvType = (RecyclerView) mView.findViewById(R.id.rv_type_fh2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvType.setLayoutManager(linearLayoutManager);
        mHomeTagTypeAdapter = new HomeTagTypeAdapter(mContext, mTagList);
        rvType.setAdapter(mHomeTagTypeAdapter);
        //搜索
        mView.findViewById(R.id.rl_search_fh2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(mContext, SearchActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("-->HomeFragment","到搜索详情异常");
                }
            }
        });

    }

    /**
     * 获取viewpager当前选中页的位置
     */
    private void getViewPagerCurrentPage() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //position就是当前选中的position
                mViewPagerCurrentPosition = position;
                Log.e(TAG, "getViewPagerCurrentPage当前位置00：：" + position);
                //设置收藏显示的状态
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    /**
     * 网络访问失败回调
     */
    @Override
    public void showError() {
//        Toast.makeText(getActivity().getApplicationContext(), "访问数据失败", Toast.LENGTH_SHORT).show();
        //从缓存中取数据
        getHomeHeadDataFromCache();

    }

    /**
     * 从缓存中获取home头部数据
     */
    private void getHomeHeadDataFromCache() {
        // TODO: 2017/12/28 退出登录需要删除这个文件
        try {
            HomeHeadResBean.DataBean homeHeadData = (HomeHeadResBean.DataBean) FileUtils.getCacheDataFromFile(mContext, "home_head_data");
            if (homeHeadData != null) {
                //显示加载成功
                PostUtil.postSuccessDelayed(mLoadService1);
                //banner相关数据
                bannerData(homeHeadData);
                //tag相关数据 美胸 清纯 等
                tagData(homeHeadData);
                //tab相关数据
                tabData(homeHeadData);
            } else {
                showFailed();
            }
        } catch (Exception e) {
            e.printStackTrace();
            showFailed();
            Log.e(TAG,"获取数据失败 从缓存中取数据异常");
        }
    }

    private void showFailed() {
        //显示加载失败
        PostUtil.postCallbackDelayed(mLoadService1, ErrorCallback.class);
    }

    /**
     * 网络访问成功回调
     * @param bean
     */
    @Override
    public void setData(HomeHeadResBean bean) {
        // TODO: 2017/12/28 这波数据必须要缓存一下
//        try {
            if (bean != null) {
                //显示加载成功
                PostUtil.postSuccessDelayed(mLoadService1);
                HomeHeadResBean.DataBean data = bean.getData();
                if (data != null) {
                    //banner相关数据
                    bannerData(data);
                    //tag相关数据 美胸 清纯 等
                    tagData(data);
                    //tab相关数据
                    tabData(data);
                    //缓存数据到文件
                    cacheDataToFile(data);
                } else {
                    Log.e(TAG,"数据为空11");
                    //从缓存中取数据
                    getHomeHeadDataFromCache();
                }
            } else {
                Log.e(TAG,"跟数据为空");
                //从缓存中取数据
                getHomeHeadDataFromCache();
            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e(TAG,"setData请求成功后数据异常");
//            //从缓存中取数据
//            getHomeHeadDataFromCache();
//        }
    }

    /**
     * 缓存home头部的数据到文件
     * @param bean
     */
    private void cacheDataToFile(HomeHeadResBean.DataBean bean) {
        try {
            boolean homeHeadData = FileUtils.saveCachDataToFile(mContext, "home_head_data", bean);
            if (homeHeadData) {
                Log.e(TAG,"缓存数据成功");
            } else {
                Log.e(TAG,"缓存数据失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"cacheDataToFile缓存数据异常");
        }
    }

    /**
     * tab相关数据
     * @param data
     */
    private void tabData(HomeHeadResBean.DataBean data) {
        //tabLayout的tab数据
        List<HomeHeadResBean.DataBean.TypeListBean> typeList = data.getTypeList();
        if (typeList != null && typeList.size() != 0) {
            for (int i = 0; i < typeList.size(); i++) {
                titleList.add(typeList.get(i).getTypeName());
            }
            setUpTabLayoutViewPager(typeList);
        } else {
            Log.e(TAG,"tab数据为空");
        }
    }

    /**
     * tag相关数据 美胸 清纯 等
     * @param data
     */
    private void tagData(HomeHeadResBean.DataBean data) {
        try {
            //首页要显示的标签个数
            String showNumber = data.getShowNumber();
            try {
                mShowSize = Integer.parseInt(showNumber);
            } catch (Exception e){
                e.printStackTrace();
                Log.e(TAG,"showNumber转换异常");
                mShowSize = 1;
            }
            //分类tag数据 欧美 日韩 等
            List<HomeHeadResBean.DataBean.TagListBean> tagList = data.getTagList();
            if (tagList != null && tagList.size() != 0) {
                //存储tagList到临时变量 以方便到所有的分类的页面使用
                BeautyContent.setTagList(tagList);
                //区分是否是要在首页显示的标签
                for (int i = 0; i < mShowSize; i++) {
                    mTagList.add(tagList.get(i));
                }
                //刷新刷出数据
                mHomeTagTypeAdapter.notifyDataSetChanged();
            } else {
                Log.e(TAG,"tag数据为空");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"tagData数据异常");
        }
    }

    /**
     * banner相关数据
     * @param data
     */
    private void bannerData(HomeHeadResBean.DataBean data) {
        try {
            //banner数据
            List<HomeHeadResBean.DataBean.BannerListBean> bannerList = data.getBannerList();
            if (bannerList != null && bannerList.size() != 0) {
                setBanner(bannerList);
            } else {
                Log.e(TAG,"banner数据为空");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"bannerData数据异常");
        }
    }

    /**
     * 关联tabLayout和ViewPager
     * @param typeList
     */
    private void setUpTabLayoutViewPager(List<HomeHeadResBean.DataBean.TypeListBean> typeList) {
//        try {
            for (int i = 0; i < typeList.size(); i++) {
                if (typeList.get(i).getTypeId() == 3) {
                    //精选
                    try {
                        String mId = "3";
                        HomeGoodChoiceFragment fragment = HomeGoodChoiceFragment.newInstance(mId);
                        mFragments.add(fragment);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(TAG,"setUpTabLayoutViewPager时HomeGoodChoiceFragment异常");
                    }
                } else {
                    //最新 和 热门
                    try {
                        HomeHotFragment homeHotFragment = HomeHotFragment.newInstance(typeList.get(i).getTypeId() + "");
                        mFragments.add(homeHotFragment);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(TAG,"setUpTabLayoutViewPager时HomeFragment异常");
                    }
                }
            }
            //刷新刷出数据
            mAdapter.notifyDataSetChanged();
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e(TAG,"setUpTabLayoutViewPager数据异常");
//        }
    }

    /**
     * 设置banner
     * @param bannerList
     */
    private void setBanner(final List<HomeHeadResBean.DataBean.BannerListBean> bannerList) {
        for (int i = 0; i < bannerList.size(); i++) {
            images.add(bannerList.get(i).getImageUrl());
            titles.add(bannerList.get(i).getUrl());
        }
        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mBanner.setImages(images);
        //设置banner动画效果
        //mBanner.setBannerAnimation(Transformer.DepthPage);
        mBanner.setBannerAnimation(Transformer.Default);
        //设置标题集合（当banner样式有显示title时）
        mBanner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(2000);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.RIGHT);
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();
        //点击事件
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                jump2bannerDetail(position, bannerList);
            }
        });
    }

    /**
     * 到banner详情页
     * @param position
     * @param bannerList
     */
    private void jump2bannerDetail(int position, List<HomeHeadResBean.DataBean.BannerListBean> bannerList) {
        try {
            Intent intent = new Intent(mContext, HomeTypeActivity.class);
            //clickIdSearch: 0 是  1 不是
            intent.putExtra("clickIdSearch","1");
            //isEnterFromFollowTag是否是从我关注的标签页面进入列表页的 0：是  1：不是
            intent.putExtra("isEnterFromFollowTag","1");
            intent.putExtra("searchId",bannerList.get(position).getUrl());
//            intent.putExtra("typeName",bannerList.get(position).getTypename());
            intent.putExtra("typeName",bannerList.get(position).getTypeName());
            //Calling startActivity() from outside of an Activity context requires the FLAG_ACTIVITY_NEW_TASK flag. Is this really what you want?
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"banner跳转异常");
        }
    }

    /**
     * 自定义适配器
     */
    class HomeTabAdapter extends FragmentStatePagerAdapter {

        private List<String> titleList;

        public HomeTabAdapter(FragmentManager fm) {
            super(fm);
        }

        public HomeTabAdapter(FragmentManager fm,List<String> titleList) {
            super(fm);
            this.titleList = titleList;
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
            return titleList.get(position);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        mBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        mBanner.stopAutoPlay();
    }

    /**
     * 解除绑定
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消RxBus的订阅
        RxBus.getInstance().unSubscribe(this);

        //取消网络请求 避免内存溢出
        try {
            if (mPresenter != null) {
                mPresenter.detach();
                mPresenter.interruptHttp();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"onDestroy打断网络异常");
        }
    }
}
