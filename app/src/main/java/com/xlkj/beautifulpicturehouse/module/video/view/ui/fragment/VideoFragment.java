package com.xlkj.beautifulpicturehouse.module.video.view.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.xlkj.beautifulpicturehouse.BeautyApplication;
import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.common.load.PostUtil;
import com.xlkj.beautifulpicturehouse.common.load.callback.ErrorCallback;
import com.xlkj.beautifulpicturehouse.common.load.callback.LoadingCallback;
import com.xlkj.beautifulpicturehouse.common.rxbus.RxBus;
import com.xlkj.beautifulpicturehouse.common.util.FileUtils;
import com.xlkj.beautifulpicturehouse.common.view.ui.activity.SearchActivity;
import com.xlkj.beautifulpicturehouse.module.home.bean.AboutTagReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.AboutTagResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.Hot2RefreshBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.HotToRefreshVideoBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.RefreshToHotVideoBean;
import com.xlkj.beautifulpicturehouse.module.home.view.ui.fragment.HomeHotFragment;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoHeadResBean;
import com.xlkj.beautifulpicturehouse.module.video.contract.VideoContract;
import com.xlkj.beautifulpicturehouse.module.video.presenter.VideoHeadPresenterImpl;
import com.xlkj.beautifulpicturehouse.module.video.view.adapter.VideoTypeAdapter;
import com.xlkj.beautifulpicturehouse.module.video.view.ui.activity.VideoTypeActivity;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.functions.Action1;


/**
 * VideoFragment
 *
 */
public class VideoFragment extends Fragment implements VideoContract.IVideoHeadView {

    public static final String TAG = "-->VideoFragment-";
    private View mView;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    //tab数据
    private List<String> titleList = new ArrayList<>();
    //存放viewpager中的fragment
    private List<Fragment> mFragments = new ArrayList<>();
    private VideoTabAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView rvType;
    //存放包臀等标签分类的数据
    private List<VideoHeadResBean.DataBean.TagListBean> typeData = new ArrayList<>();
    //视频头部的loadSir
    private LoadService mLoadService;
    //视频头部的presenter
    private VideoHeadPresenterImpl mVideoHeadPresenter;
    //头部网格中的四张图片
    private ImageView ivNet1;
    private ImageView ivNet2;
    private ImageView ivNet3;
    private ImageView ivNet4;
    private VideoTypeAdapter mVideoTypeAdapter;
    //viewpager的当前选中的位置 默认是0
    private int mViewPagerCurrentPosition = 0;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_video, container, false);
        mContext = BeautyApplication.appContext;
        bindPresenter();
        initView();
        setViewPager();
        getViewPagerCurrentPage();
        initRxBus();
        try {
            mLoadService = LoadSir.getDefault().register(mView, new Callback.OnReloadListener() {
                @Override
                public void onReload(View v) {
                    mLoadService.showCallback(LoadingCallback.class);
                    loadData();
                }
            });
            return mLoadService.getLoadLayout();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"加载loadSir异常");
            return mView;
        }
    }

    /**
     * 解绑p
     */
    private void unbindPresenter() {
        if (mVideoHeadPresenter != null) {
            mVideoHeadPresenter.interruptHttp();
            mVideoHeadPresenter.detachVideoHead();
        }
    }

    /**
     * 绑定p
     */
    private void bindPresenter() {
        mVideoHeadPresenter = new VideoHeadPresenterImpl();
        mVideoHeadPresenter.attachVideoHead(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //请求视频头部的数据
        loadData();
    }

    /**
     * 请求头部数据
     */
    private void loadData() {
        mVideoHeadPresenter.getDataVideoHead("getVideoInfoList");
    }

    /**
     * 初始化RxBus
     */
    private void initRxBus() {
        Subscription subscription = RxBus.getInstance().doSubscribe(HotToRefreshVideoBean.class, new Action1<HotToRefreshVideoBean>() {
            @Override
            public void call(HotToRefreshVideoBean bean) {
                boolean refreshSuccess = bean.isRefreshSuccess();
                mSwipeRefreshLayout.setRefreshing(false);
                if (refreshSuccess) {
                    //Toast.makeText(getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
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
     * 设置viewpager
     */
    private void setViewPager() {
        mAdapter = new VideoTabAdapter(getActivity().getSupportFragmentManager(), titleList);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(mAdapter);
    }

    /**
     * 显示加载失败
     */
    private void showFailLoadSir() {
        // TODO: 2018/1/9 需要捕获异常
        //显示加载失败
        PostUtil.postCallbackDelayed(mLoadService, ErrorCallback.class);
    }

    /**
     * 显示加载成功
     */
    private void showSuccessLoadSir() {
        // TODO: 2018/1/9 需要捕获异常
        //显示加载成功
        PostUtil.postSuccessDelayed(mLoadService);
    }

    /**
     * 视频头部数据回调失败
     */
    @Override
    public void showErrorVideoHead() {
//        Toast.makeText(getActivity().getApplicationContext(), "访问数据失败", Toast.LENGTH_SHORT).show();
        //2018/1/10 从缓存中取数据
        getVideoHeadDataFromCache();
        Log.e(TAG,"请求头部数据失败");
    }

    /**
     * 视频头部数据回调成功
     * @param bean
     */
    @Override
    public void setDataVideoHead(VideoHeadResBean bean) {
        Log.e(TAG,"头部数据："+bean.toString());
        if (bean != null) {
            //显示加载成功
            showSuccessLoadSir();
            VideoHeadResBean.DataBean data = bean.getData();
            if (data != null) {
                //banner相关数据
                bannerData(data);
                //tag相关数据 包臀XXOO 等
                tagData(data);
                //tab相关数据
                tabData(data);
                //缓存数据到文件
                cacheDataToFile(data);
            } else {
                Log.e(TAG,"data数据为空");
                //2018/1/10 从缓存中取数据
                getVideoHeadDataFromCache();
            }

        } else {
            Log.e(TAG,"bean数据为空");
            //2018/1/10 从缓存中取数据
            getVideoHeadDataFromCache();
        }
    }

    /**
     * 从缓存中取数据
     */
    private void getVideoHeadDataFromCache() {
        try {
            VideoHeadResBean.DataBean data = (VideoHeadResBean.DataBean)FileUtils.getCacheDataFromFile(mContext,"video_head_data");
            if (data != null) {
                //显示加载成功
                showSuccessLoadSir();
                //banner相关数据
                bannerData(data);
                //tag相关数据 美胸 清纯 等
                tagData(data);
                //tab相关数据
                tabData(data);
            } else {
                showFailLoadSir();
            }
        } catch (Exception e) {
            e.printStackTrace();
            showFailLoadSir();
            Log.e(TAG,"获取数据失败 从缓存中取数据异常");
        }
    }

    /**
     * 缓存video头部的数据到文件
     * @param data
     */
    private void cacheDataToFile(VideoHeadResBean.DataBean data) {
        try {
            boolean videoHeadData = FileUtils.saveCachDataToFile(mContext, "video_head_data", data);
            if (videoHeadData) {
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
    private void tabData(VideoHeadResBean.DataBean data) {
        List<VideoHeadResBean.DataBean.TypeListBean> typeList = data.getTypeList();
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
     * 关联tabLayout和ViewPager
     * @param typeList
     */
    private void setUpTabLayoutViewPager(List<VideoHeadResBean.DataBean.TypeListBean> typeList) {
        try {
            for (int i = 0; i < typeList.size(); i++) {
                //栏目id
                int mId = typeList.get(i).getTypeId();
                VideoHotFragment videoHotFragment = VideoHotFragment.newInstance(mId + "");
                mFragments.add(videoHotFragment);
            }
            //刷新出数据
            mAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"setUpTabLayoutViewPager时VideoFragment异常");
        }
    }

    /**
     * tag相关数据 包臀XXOO 等
     * @param data
     */
    private void tagData(VideoHeadResBean.DataBean data) {
        try {
            List<VideoHeadResBean.DataBean.TagListBean> tagList = data.getTagList();
            if (tagList != null && tagList.size() != 0) {
                typeData.addAll(tagList);
                //刷新出数据
                mVideoTypeAdapter.notifyDataSetChanged();
            } else {
                Log.e(TAG,"tag数据为空");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"tagData数据异常");
        }
    }

    /**
     * 网格中的四张图片
     * @param data
     */
    private void bannerData(VideoHeadResBean.DataBean data) {
        try {
            final List<VideoHeadResBean.DataBean.BannerListBean> bannerList = data.getBannerList();
            if (bannerList != null && bannerList.size() != 0) {
                if (bannerList.size() >= 4) {
                    Glide.with(mContext).load(bannerList.get(0).getImageUrl()).placeholder(R.drawable.ori_yqsbb).crossFade().into(ivNet1);
                    Glide.with(mContext).load(bannerList.get(1).getImageUrl()).placeholder(R.drawable.ori_yqsbb).crossFade().into(ivNet2);
                    Glide.with(mContext).load(bannerList.get(2).getImageUrl()).placeholder(R.drawable.ori_yqsbb).crossFade().into(ivNet3);
                    Glide.with(mContext).load(bannerList.get(3).getImageUrl()).placeholder(R.drawable.ori_yqsbb).crossFade().into(ivNet4);
                    try {
                        ivNet1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                jump2videoType(bannerList.get(0).getUrl(),bannerList.get(0).getTypeName());
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(TAG,"点击第一张图片异常");
                    }
                    try {
                        ivNet2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                jump2videoType(bannerList.get(1).getUrl(),bannerList.get(1).getTypeName());
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(TAG,"点击第二张图片异常");
                    }
                    try {
                        ivNet3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                jump2videoType(bannerList.get(2).getUrl(),bannerList.get(2).getTypeName());
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(TAG,"点击第三张图片异常");
                    }
                    try {
                        ivNet4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                jump2videoType(bannerList.get(3).getUrl(),bannerList.get(3).getTypeName());
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(TAG,"点击第四张图片异常");
                    }
                }
            } else {
                Log.e(TAG,"网格中四站图片为空");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"网格中的四张图片异常");
        }
    }

    /**
     * 四张图片的点击事件-到视频分类的列表页
     * @param typeName
     * @param title
     */
    private void jump2videoType(String typeName,String title) {
        try {
            Intent intent = new Intent(mContext, VideoTypeActivity.class);
            //如果是标签页的跳转 这里需要传标签的id
            intent.putExtra("typeName",typeName);
            //0 点击搜索中的更多传的是关键字  1 其他搜索（视频标签到列表）传的是id
            intent.putExtra("byId","1");
            //标题
            intent.putExtra("title",title);
            //Calling startActivity() from outside of an Activity context requires the FLAG_ACTIVITY_NEW_TASK flag. Is this really what you want?
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("-->VideoFragment","jump2videoType跳转异常");
        }
    }


    /**
     * 自定义适配器
     */
    class VideoTabAdapter extends FragmentStatePagerAdapter {

        private List<String> titleList;

        public VideoTabAdapter(FragmentManager fm,List<String> titleList) {
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

    /**
     * 初始化控件
     */
    private void initView() {
        //头部网格中的四张图片
        ivNet1 = (ImageView) mView.findViewById(R.id.iv_net1_fv);
        ivNet2 = (ImageView) mView.findViewById(R.id.iv_net2_fv);
        ivNet3 = (ImageView) mView.findViewById(R.id.iv_net3_fv);
        ivNet4 = (ImageView) mView.findViewById(R.id.iv_net4_fv);
        //分类的recyclerview 水平滑动控件
        rvType = (RecyclerView) mView.findViewById(R.id.rv_type_fv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvType.setLayoutManager(linearLayoutManager);
        mVideoTypeAdapter = new VideoTypeAdapter(mContext, typeData);
        rvType.setAdapter(mVideoTypeAdapter);

        //搜索
        ((RelativeLayout)mView.findViewById(R.id.rl_search_fv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(mContext, SearchActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "到sou搜索页面异常");
                }
            }
        });
        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.srl_fv);
        //改变加载显示的颜色
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_blue_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                mSwipeRefreshLayout.setRefreshing(false);
                // TODO: 2017/12/25 RxBus发送消息
                RefreshToHotVideoBean bean = new RefreshToHotVideoBean();
                bean.setPosition(mViewPagerCurrentPosition);
                bean.setRefreshData(true);
                RxBus.getInstance().post(bean);
            }
        });
        //监听 AppBarLayout Offset 变化，动态设置 SwipeRefreshLayout 是否可用
        ((AppBarLayout) mView.findViewById(R.id.app_bar_fv)).addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    mSwipeRefreshLayout.setEnabled(true);
                } else {
                    mSwipeRefreshLayout.setEnabled(false);
                }
            }
        });
        mTabLayout = (TabLayout) mView.findViewById(R.id.tab_layout_fv);
        mViewPager = (ViewPager) mView.findViewById(R.id.vp_fv);
    }



    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindPresenter();
        unbindRxBus();
    }

    /**
     * 解绑Rx
     */
    private void unbindRxBus() {
        //取消rxbus的订阅
        RxBus.getInstance().unSubscribe(this);
    }
}
