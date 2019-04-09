package com.xlkj.beautifulpicturehouse.module.home.view.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.kingja.loadsir.core.Transport;
import com.xlkj.beautifulpicturehouse.BeautyApplication;
import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.common.load.PostUtil;
import com.xlkj.beautifulpicturehouse.common.load.callback.EmptyCallback;
import com.xlkj.beautifulpicturehouse.common.load.callback.ErrorCallback;
import com.xlkj.beautifulpicturehouse.common.load.callback.LoadingCallback;
import com.xlkj.beautifulpicturehouse.common.rxbus.RxBus;
import com.xlkj.beautifulpicturehouse.common.util.NetUtil;
import com.xlkj.beautifulpicturehouse.common.util.PreferencesUtils;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomeHotNewResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomeHotPicResMgqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomePicGoodChoiceReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.Hot2RefreshBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.HotListPicReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomeHotPicResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.Refresh2hotBean;
import com.xlkj.beautifulpicturehouse.module.home.contract.HomeContract;
import com.xlkj.beautifulpicturehouse.module.home.presenter.HotPicListPresenterImpl;
import com.xlkj.beautifulpicturehouse.module.home.view.adapter.HomeHotAdapter;
import com.xlkj.beautifulpicturehouse.module.mine.bean.LoginIsRefreshDataBean;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.functions.Action1;


/**
 * A simple {@link Fragment} subclass.
 * 首页 热门fragment
 * 生命周期方法调用顺序
 * setUserVisibleHint: isVisibleToUser = false
 * onAttach
 * onCreate
 * setUserVisibleHint: isVisibleToUser = true
 * onCreateView
 * onActivityCreated
 * onStart
 * onResume
 * onPause
 * onStop
 * onDestroyView
 * onDestroy
 * onDetach
 */
public class HomeHotFragment extends Fragment implements HomeContract.IHotPicListView {

    public static final String TAG = "-->>HomeHotFragment-->>";
    //tab的栏目id
    public static final String MID = "mId";

    private View view;
    private RecyclerView mRecyclerView;
    private HomeHotAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private HotPicListPresenterImpl mHotPicListPresenter;
    private LoadService mLoadService;
    //放最热图片数据列表
    private List<HomeHotNewResBean.DataBean.TypeListBean> mHotPicList = new ArrayList<>();
    //服务器获取的所有列表数据的总页数
    private int mPagetotal;
    //请求传递的页数
    private int page = 1;
    //是否刷新 默认不刷新
    private boolean isRefresh = false;
    //是否是加载更多 默认不是
    private boolean isLoadMore = false;
    //板块栏目mid 用来请求不同页面下的不同数据-同一个Fragment加载不同数据
    private String mId;
    //加载loadSir的布局
    private RelativeLayout mRelativeLayout;
    /**
     * 控件是否初始化完成
     */
    private boolean isViewCreated;
    /**
     * 数据是否加载完毕
     */
    private boolean isLoadDataCompleted;
    private Context mContext;

    /**
     * 获取fragment
     *
     * @param mId
     * @return
     */
    public static HomeHotFragment newInstance(String mId) {
        HomeHotFragment fragment = new HomeHotFragment();
        Bundle bundle = new Bundle();
        bundle.putString(MID, mId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = BeautyApplication.appContext;
        mHotPicListPresenter = new HotPicListPresenterImpl();
        mHotPicListPresenter.attachHotPicList(this);
        Log.e(TAG, "onCreate生命周期方法执行");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_test, container, false);
        if (getArguments() != null) {
            mId = getArguments().getString(MID);
        }
        Log.e(TAG, "获取的栏目id=" + mId);
        initView();
        //视图加载完成
        isViewCreated = true;
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(TAG, "onViewCreated生命周期方法");
        //当第一次数据加载没有完成就初始化loadSir
        if (!isLoadDataCompleted) {
            initLoadSir();
        }
        initRxBus();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //这里是ViewPager加载第一页的数据，onActivityCreated方法只会执行一次 不能用来初始化initLoadSir
        if (getUserVisibleHint()) {
            //加载数据
            loadData();
            Log.e(TAG, "onActivityCreated生命周期方法执行");
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //viewpager滑动加载数据的三个条件： 对用户可见 视图初始化完成 第一次数据没有加载完成
        if (isVisibleToUser && isViewCreated && !isLoadDataCompleted) {
            //加载数据
            loadData();
        }
    }

    /**
     * 加载loadSir
     */
    private void initLoadSir() {
//        try {
        mLoadService = LoadSir.getDefault().register(mRelativeLayout, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                // TODO: 2017/12/21 重新加载的逻辑
                mLoadService.showCallback(LoadingCallback.class);
                page = 1;
                loadData();
            }
        }).setCallBack(EmptyCallback.class, new Transport() {
            @Override
            public void order(Context context, View view) {

            }
        });
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e(TAG, "初始化load异常initLoadSir");
//        }
        //发送正在加载数据的消息
        PostUtil.postCallbackDelayed(mLoadService, LoadingCallback.class);

    }

    /**
     * RxBus接收数据
     */
    private void initRxBus() {
        /**接收刷新相关的数据*/
        Subscription subscription = RxBus.getInstance().doSubscribe(Refresh2hotBean.class, new Action1<Refresh2hotBean>() {
            @Override
            public void call(Refresh2hotBean bean) {
                ////传递viewpager当前的位置 0 1 2对应fragment中的mid 2 1 3  刷新时只刷新对应的当前页面的数据 否则会刷新所有fragment的数据 如果有100个fragment 刷新一波程序就挂掉了 这显然不科学
                int position = bean.getPosition();
                if (mId != null) {
                    if ((position == 0 && mId.equals("2")) || (position == 1 && mId.equals("1")) || (position == 2 && mId.equals("3"))) {
                        setRefresh();
                    }
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e(TAG, "是否接受数据22：" + throwable.getMessage());
            }
        });
        RxBus.getInstance().addSubscription(this, subscription);

        /**接收登录成功后 带uid获取的列表数据：刷新的操作*/
        Subscription subscription1 = RxBus.getInstance().doSubscribe(LoginIsRefreshDataBean.class, new Action1<LoginIsRefreshDataBean>() {
            @Override
            public void call(LoginIsRefreshDataBean bean) {
                boolean refreshData = bean.isRefreshData();
                if (refreshData) {
                    setRefresh();
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e(TAG, "是否接受数据22：" + throwable.getMessage());
            }
        });
        RxBus.getInstance().addSubscription(this, subscription1);

    }

    /**
     * 刷新操作
     */
    private void setRefresh() {
        isRefresh = true;
        page = 1;
        loadData();
    }

    /**
     * 请求数据
     */
    private void loadData() {
        HomePicGoodChoiceReqBean bean = new HomePicGoodChoiceReqBean();
        //获取uid
        String uid = PreferencesUtils.getString(mContext, "uid");
        if (uid != null && !uid.isEmpty() && !uid.equals("")) {
            bean.setuId(uid);
        } else {
            //uid为空
            bean.setuId("");
        }
        bean.setPage(page + "");
        bean.setTypeId(mId);
        bean.setAction("typeList");
        mHotPicListPresenter.getDataHotPicList(bean);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mRelativeLayout = (RelativeLayout) view.findViewById(R.id.rl_content_ft);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_ft);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mLayoutManager.setSmoothScrollbarEnabled(true);
        mLayoutManager.setAutoMeasureEnabled(true);
        mAdapter = new HomeHotAdapter(mContext, mHotPicList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        // FIXME: 2017/12/20 禁止recyclerview在竖直方向的滑动 暂时解决NestedScrollView嵌套recyclerview滑动卡顿现象 (mahuaer  这种解决方案水的一逼 昨晚看了我去年在成都写的解决方案 完美解决 nice)
        mRecyclerView.setFocusable(true);
        //上拉加载更多
        scrollEvent();
    }

    /**
     * 列表的滚动事件
     */
    private void scrollEvent() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int mLastVisibleItemPosition;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                try {
                    int totalItemCount = mLayoutManager.getItemCount();
                    if (newState == RecyclerView.SCROLL_STATE_IDLE && (mLastVisibleItemPosition == totalItemCount - 1)) {
                        //改变加载的状态
                        isLoadMore = true;
                        if (NetUtil.isConnected(mContext)) {
                            if (page < mPagetotal) {
                                //加载中
                                mAdapter.setFootView(0);
                                page++;
                                loadData();
                            } else {
                                //全部加载 我是有底线的
                                mAdapter.setFootView(4);
                            }
                        } else {
                            //网路断开
                            mAdapter.setFootView(2);
                        }

                    } else {
                        Log.e(TAG, "还没滑动到底部");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "滑动onScrollStateChanged异常");
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mLastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    /**
     * 请求图片列表数据失败回调
     */
    @Override
    public void showErrorHotPicList() {
        Log.e(TAG, "图片数据请求失败");
        if (isRefresh) {
            /**刷新*/
            //刷新失败的逻辑
            //发送消息 告诉SwipeRefresh刷新失败
            Hot2RefreshBean refreshBean = new Hot2RefreshBean();
            refreshBean.setRefreshSuccess(false);
            RxBus.getInstance().post(refreshBean);
            //停止刷新
            //mSwipeRefreshLayout.setRefreshing(false);
            //还原刷新的状态 设置成不是刷新
            if (isRefresh) {
                isRefresh = false;
            }
        } else if (isLoadMore) {
            /**加载更多*/
            //加载更多失败的逻辑
            mAdapter.setFootView(2);
            //还原加载更多的状态 设置成不是加载更多
            if (isLoadMore) {
                isLoadMore = false;
            }
            Toast.makeText(mContext, "加载失败", Toast.LENGTH_SHORT).show();
        } else {
            /**不是刷新也不是加载更多的逻辑*/
            //不是刷新失败的逻辑
            //显示加载失败
            PostUtil.postCallbackDelayed(mLoadService, ErrorCallback.class);
        }

    }

    /**
     * 请求图片列表数据成功回调
     *
     * @param bean
     */
    @Override
    public void setDataHotPicList(HomeHotNewResBean bean) {
        if (bean != null && bean.getData() != null) {
            //设置加载数据成功完成
            isLoadDataCompleted = true;
            //显示加载成功
            PostUtil.postSuccessDelayed(mLoadService);
            //如果是刷新 则先清空一下数据 注意了 这里的刷新是HomeFragment中的刷新用RxBus发送的消息 而不是本页面的刷新
            if (isRefresh) {
                //发送消息 告诉SwipeRefresh刷新成功
                Hot2RefreshBean refreshBean = new Hot2RefreshBean();
                refreshBean.setRefreshSuccess(true);
                RxBus.getInstance().post(refreshBean);
                mHotPicList.clear();
            }
            mHotPicList.addAll(bean.getData().getTypeList());
            mAdapter.notifyDataSetChanged();
            //获取所有数据的页数
            mPagetotal = bean.getData().getTotalPage();
            //还原刷新的状态 设置成不是刷新
            if (isRefresh) {
                isRefresh = false;
            }
            //还原加载更多的状态 设置成不是加载更多
            if (isLoadMore) {
                isLoadMore = false;
            }
        } else {
            PostUtil.postCallbackDelayed(mLoadService, EmptyCallback.class);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消RxBus的订阅
        RxBus.getInstance().unSubscribe(this);
        //取消网络请求 避免内存溢出
        mHotPicListPresenter.detachHotPicList();
        mHotPicListPresenter.interruptHttpHotPicList();
    }
}
