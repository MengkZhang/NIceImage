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
import com.xlkj.beautifulpicturehouse.common.util.FileUtils;
import com.xlkj.beautifulpicturehouse.common.util.NetUtil;
import com.xlkj.beautifulpicturehouse.common.util.PreferencesUtils;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomePicGoodChoiceReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomePicGoodChoiceResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.Hot2RefreshBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.Refresh2hotBean;
import com.xlkj.beautifulpicturehouse.module.home.contract.HomeContract;
import com.xlkj.beautifulpicturehouse.module.home.presenter.GoodchoicePresenterImpl;
import com.xlkj.beautifulpicturehouse.module.home.view.adapter.HomeGoodChoiceAdapter;
import com.xlkj.beautifulpicturehouse.module.mine.bean.LoginIsRefreshDataBean;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.functions.Action1;

/**
 * A simple {@link Fragment} subclass.
 * 首页精选fragment
 */
public class HomeGoodChoiceFragment extends Fragment implements HomeContract.IGoodChoiceView {

    public static final String TAG = "-->HomeGoodChoiceFrag";
    public static final String MID = "mId";

    private View mView;
    private RecyclerView mRecyclerView;
    //精选presenter
    private GoodchoicePresenterImpl mGoodchoicePresenter;
    private int page = 1;
    private List<HomePicGoodChoiceResBean.DataBean.TypeListBean> mList = new ArrayList<>();
    private HomeGoodChoiceAdapter mHomeGoodChoiceAdapter;
    //栏目tab的id
    private String mId = "3";
    //服务器返回的总页数
    private int mTotalPage = 1;
    private LoadService mLoadService;
    private boolean isVisible;
    private boolean isPrepared;
    //用来存放loadSir的布局
    private RelativeLayout mRelativeLayout;
    //是否刷新 默认不刷新
    private boolean isRefresh = false;
    /**
     * 控件是否初始化完成
     */
    private boolean isViewCreated;
    /**
     * 数据是否加载完毕
     */
    private boolean isLoadDataCompleted;
    private LinearLayoutManager mLayoutManager;
    private Context mContext;

    /**
     * 获取fragment
     * @param mId：栏目tab的id
     * @return
     */
    public static HomeGoodChoiceFragment newInstance(String mId) {
        HomeGoodChoiceFragment fragment = new HomeGoodChoiceFragment();
        Bundle bundle = new Bundle();
        bundle.putString(MID, mId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGoodchoicePresenter = new GoodchoicePresenterImpl();
        mGoodchoicePresenter.attachGoodChoice(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home_good_choice, container, false);
        mContext = BeautyApplication.appContext;
        mId = getArguments().getString(MID);
        initView();
        //视图加载完成
        isViewCreated = true;
        Log.e(TAG,"生命周期方法onCreateView执行");
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isPrepared = true;
        //当第一次数据加载没有完成就初始化loadSir
        if (!isLoadDataCompleted) {
            initLoadSir();
        }
        initRxBus();
    }

    private void initRxBus() {
        /**接收刷新相关的数据*/
        Subscription subscription = RxBus.getInstance().doSubscribe(Refresh2hotBean.class, new Action1<Refresh2hotBean>() {
            @Override
            public void call(Refresh2hotBean bean) {
                setRefresh();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e(TAG,"是否接受数据22："+throwable.getMessage());
            }
        });
        RxBus.getInstance().addSubscription(this,subscription);

        /**接收登录成功后 带uid(或退出登录后不带uid)获取的列表数据：刷新的操作*/
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //这里是ViewPager加载第一页的数据，onActivityCreated方法只会执行一次 不能用来初始化initLoadSir
        if (getUserVisibleHint()) {
            //加载数据
            loadData();
            Log.e(TAG,"onActivityCreated生命周期方法执行");
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
        try {
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
     * 请求数据
     */
    private void loadData() {
            HomePicGoodChoiceReqBean bean = new HomePicGoodChoiceReqBean();
            String uid = PreferencesUtils.getString(BeautyApplication.appContext, "uid");
            if (uid != null && !uid.isEmpty() && !uid.equals("")) {
                bean.setuId(uid);
            } else {
                bean.setuId("");
            }
            String action = "typeList";
            bean.setAction(action);
            bean.setTypeId(mId);
            bean.setPage(page + "");
            mGoodchoicePresenter.getDataGoodChoice(bean);
    }

    private void initView() {
        mRelativeLayout = (RelativeLayout) mView.findViewById(R.id.rl_content_fhgc);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.rv_fhgc);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mHomeGoodChoiceAdapter = new HomeGoodChoiceAdapter(mContext, mList);
        mRecyclerView.setAdapter(mHomeGoodChoiceAdapter);
        //上拉加载更多
        scrollEvent();
    }

    /**
     * 上拉加载更多
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

                        if (NetUtil.isConnected(mContext)) {
                            if (page < mTotalPage) {
                                //加载中
                                mHomeGoodChoiceAdapter.setFootView(0);
                                page++;
                                loadData();
                            } else {
                                //全部加载 我是有底线的
                                mHomeGoodChoiceAdapter.setFootView(4);
                            }
                        } else {
                            //网路断开
                            mHomeGoodChoiceAdapter.setFootView(2);
                        }

                    } else {
                        Log.e(TAG,"还没滑动到底部");
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

    @Override
    public void showErrorGoodChoice() {
        Log.e(TAG, "showErrorGoodChoice请求失败");
        //显示加载失败
        if (!isRefresh) {
            //不是刷新失败的逻辑
            //显示加载失败
            PostUtil.postCallbackDelayed(mLoadService, ErrorCallback.class);
        } else {
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
        }
    }

    @Override
    public void setDataGoodChoice(HomePicGoodChoiceResBean bean) {
        Log.e(TAG, "精选请求到数据：" + bean.getData().typeList.toString());
        if (bean != null && bean.getData() != null && bean.getData().getTypeList() != null) {
            //设置加载数据成功完成
            isLoadDataCompleted = true;
            //显示加载成功
            PostUtil.postSuccessDelayed(mLoadService);
            List<HomePicGoodChoiceResBean.DataBean.TypeListBean> typeList = bean.getData().getTypeList();
            //如果是刷新 则先清空一下数据 注意了 这里的刷新是HomeFragment中的刷新用RxBus发送的消息 而不是本页面的刷新
            if (isRefresh) {
                //发送消息 告诉SwipeRefresh刷新成功
                Hot2RefreshBean refreshBean = new Hot2RefreshBean();
                refreshBean.setRefreshSuccess(true);
                RxBus.getInstance().post(refreshBean);
                mList.clear();
            }
            mList.addAll(typeList);
            mHomeGoodChoiceAdapter.notifyDataSetChanged();
            //获取总页数
            HomePicGoodChoiceResBean.DataBean data = bean.getData();
            mTotalPage = data.getTotalPage();
            //还原刷新的状态 设置成不是刷新
            if (isRefresh) {
                isRefresh = false;
            }
            //缓存第一页的数据到文件
            cacheDataToFile(data);
        } else {
            Log.e(TAG, "精选数据为空");
            PostUtil.postCallbackDelayed(mLoadService, EmptyCallback.class);
        }
    }

    /**
     * 缓存第一页的数据到文件
     *
     * @param data
     */
    private void cacheDataToFile(HomePicGoodChoiceResBean.DataBean data) {
        try {
            boolean goodChoiceData = FileUtils.saveCachDataToFile(mContext, "good_choice_data", data);
            if (goodChoiceData) {
                Log.e(TAG, "缓存数据成功");
            } else {
                Log.e(TAG, "缓存数据失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "cacheDataToFile缓存数据异常");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消RxBus的订阅
        RxBus.getInstance().unSubscribe(this);
        if (mGoodchoicePresenter != null) {
            mGoodchoicePresenter.detachGoodChoice();
            mGoodchoicePresenter.interruptHttp();
        }
    }


}
