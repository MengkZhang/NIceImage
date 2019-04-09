package com.xlkj.beautifulpicturehouse.module.video.view.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
import com.xlkj.beautifulpicturehouse.common.util.StringUtils;
import com.xlkj.beautifulpicturehouse.module.home.bean.HotToRefreshVideoBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.Refresh2hotBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.RefreshToHotVideoBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.HotVideoListReqBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.HotVideoListResBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoListReqBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoListResBean;
import com.xlkj.beautifulpicturehouse.module.video.contract.VideoContract;
import com.xlkj.beautifulpicturehouse.module.video.presenter.HotVideoListPresenterImpl;
import com.xlkj.beautifulpicturehouse.module.video.view.adapter.VideoHotAdapter;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.functions.Action1;

/**
 * A simple {@link Fragment} subclass.
 * VideoHotFragment视频最热页面
 *  * 生命周期方法调用顺序
 setUserVisibleHint: isVisibleToUser = false
 onAttach
 onCreate
 setUserVisibleHint: isVisibleToUser = true
 onCreateView
 onActivityCreated
 onStart
 onResume
 onPause
 onStop
 onDestroyView
 onDestroy
 onDetach
 */
public class VideoHotFragment extends Fragment implements VideoContract.IHotVideoListView {

    public static final String TAG = "-->VideoHotFragment";
    //tab的栏目id
    public static final String MID = "mId";
    private View mView;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private HotVideoListPresenterImpl mHotVideoListPresenter;
    private List<VideoListResBean.DataBean.TypeListBean> mVideoList = new ArrayList<>();
    private VideoHotAdapter mVideoHotAdapter;
    private LoadService mLoadService;
    //服务器返回的总页数
    private int mPageCount;
    //请求传递的页数
    private int page = 1;
    //板块栏目mid 用来请求不同页面下的不同数据-同一个Fragment加载不同数据
    private String mId;
    /**
     * 控件是否初始化完成
     */
    private boolean isViewCreated;
    /**
     * 数据是否加载完毕
     */
    private boolean isLoadDataCompleted;
    /**
     *  加载loadSir的布局
     */
    private RelativeLayout mRelativeLayout;
    /**
     *  是否刷新 默认不刷新
     */
    private boolean isRefresh = false;
    /**
     * 是否是加载更多 默认不是
     */
    private boolean isLoadMore = false;
    private Context mContext;

    /**
     * 获取fragment
     * @param mId
     * @return
     */
    public static VideoHotFragment newInstance(String mId) {
        VideoHotFragment fragment = new VideoHotFragment();
        Bundle bundle = new Bundle();
        bundle.putString(MID,mId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHotVideoListPresenter = new HotVideoListPresenterImpl();
        mHotVideoListPresenter.attachHotVideoList(this);
        Log.e(TAG,"onCreate生命周期方法执行");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_video_hot, container, false);
        mContext = BeautyApplication.appContext;
        if (getArguments() != null) {
            mId = getArguments().getString(MID);
        }
        Log.e(TAG,"获取的栏目id="+mId);
        initView();
        //视图加载完成
        isViewCreated = true;
        return mView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(TAG,"onViewCreated生命周期方法");
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
            getData();
            Log.e(TAG,"onActivityCreated生命周期方法执行");
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //viewpager滑动加载数据的三个条件： 对用户可见 视图初始化完成 第一次数据没有加载完成
        if (isVisibleToUser && isViewCreated && !isLoadDataCompleted) {
            //加载数据
            getData();
        }
    }

    /**
     * RxBus接收数据
     */
    private void initRxBus() {
        Subscription subscription = RxBus.getInstance().doSubscribe(RefreshToHotVideoBean.class, new Action1<RefreshToHotVideoBean>() {
            @Override
            public void call(RefreshToHotVideoBean bean) {
                int position = bean.getPosition();
                ////传递viewpager当前的位置 0 1 对应fragment中的mid 2 1  刷新时只刷新对应的当前页面的数据 否则会刷新所有fragment的数据 如果有100个fragment 刷新一波程序就挂掉了 这显然不科学
                if (mId != null) {
                    if ((position == 0 && mId.equals("2")) || (position == 1 && mId.equals("1"))) {
                        setRefresh();
                    }
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
     * 刷新操作
     */
    private void setRefresh() {
        isRefresh = true;
        page = 1;
        getData();
    }

    /**
     * 加载loadSir
     */
    private void initLoadSir() {
//        try {
            mLoadService = LoadSir.getDefault().register(mRelativeLayout, new Callback.OnReloadListener() {
                @Override
                public void onReload(View v) {
                    // FIXME: 2017/12/23 重新加载数据
                    //显示正在加载数据
                    mLoadService.showCallback(LoadingCallback.class);
                    page = 1;
                    getData();
                }
            }).setCallBack(EmptyCallback.class, new Transport() {
                @Override
                public void order(Context context, View view) {

                }
            });

//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e(TAG, "onCreateView时异常");
//        }
        //发送正在加载数据的消息
        PostUtil.postCallbackDelayed(mLoadService, LoadingCallback.class);
    }

    /**
     * 请求数据(测试参数)
     */
    private void getData() {
        VideoListReqBean bean = new VideoListReqBean();
        bean.setAction("videoList");
        bean.setPage(page + "");
        bean.setTypeId(mId);
        String uid = PreferencesUtils.getString(mContext, "uid");
        if (uid != null && !uid.isEmpty() && !uid.equals("")) {
            bean.setUserId(uid);
        } else {
            bean.setUserId("");
        }
        mHotVideoListPresenter.getDataHotVideoList(bean);

    }


    /**
     * 初始化控件
     */
    private void initView() {
        mRelativeLayout = (RelativeLayout) mView.findViewById(R.id.rl_content_fvh);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.rv_fvh);
        mLayoutManager = new GridLayoutManager(mContext, 2);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mVideoHotAdapter = new VideoHotAdapter(mContext, mVideoList);
        mRecyclerView.setAdapter(mVideoHotAdapter);
        setOnScroll();
    }

    /**
     * 给recyclerview设置滑动监听事件
     */
    private void setOnScroll() {
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
                            if (page < mPageCount) {
                                //加载中
                                mVideoHotAdapter.setFootView(0);
                                page++;
                                getData();
                            } else {
                                //全部加载 我是有底线的
                                mVideoHotAdapter.setFootView(4);
                            }
                        } else {
                            //网路断开
                            mVideoHotAdapter.setFootView(2);
                        }
                    } else {
                        //还没滑动打底部
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
    public void showErrorHotVideoList() {
        Log.e(TAG, "访问数据失败。。");
        if (isRefresh) {
            /**刷新*/
            //发送消息 告诉SwipeRefresh刷新失败
            HotToRefreshVideoBean refreshBean = new HotToRefreshVideoBean();
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
            mVideoHotAdapter.setFootView(2);
            //还原加载更多的状态 设置成不是加载更多
            if (isLoadMore) {
                isLoadMore = false;
            }
            Toast.makeText(mContext, "加载失败", Toast.LENGTH_SHORT).show();
        } else {
            /**不是刷新也不是加载更多的逻辑*/
            //显示加载失败
            PostUtil.postCallbackDelayed(mLoadService, ErrorCallback.class);
        }
    }

    @Override
    public void setDataHotVideoList(VideoListResBean bean) {

        if (bean != null && bean.getData() != null && bean.getData().getTypeList() != null) {
            isLoadDataCompleted = true;
            //显示加载成功
            PostUtil.postSuccessDelayed(mLoadService);
            //如果是刷新 则先清空一下数据 注意了 这里的刷新是HomeFragment中的刷新用RxBus发送的消息 而不是本页面的刷新
            if (isRefresh) {
                //发送消息 告诉SwipeRefresh刷新成功
                HotToRefreshVideoBean refreshBean = new HotToRefreshVideoBean();
                refreshBean.setRefreshSuccess(true);
                RxBus.getInstance().post(refreshBean);
                mVideoList.clear();
            }
            List<VideoListResBean.DataBean.TypeListBean> typeList = bean.getData().getTypeList();
            mVideoList.addAll(typeList);
            mVideoHotAdapter.notifyDataSetChanged();
            //获取总页数
            mPageCount = bean.getData().getTotalPage();
            //还原刷新的状态 设置成不是刷新
            if (isRefresh) {
                isRefresh = false;
            }
            //还原加载更多的状态 设置成不是加载更多
            if (isLoadMore) {
                isLoadMore = false;
            }
        } else {
            //数据为空
            PostUtil.postCallbackDelayed(mLoadService, EmptyCallback.class);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消RxBus的订阅
        RxBus.getInstance().unSubscribe(this);
        mHotVideoListPresenter.detachHotVideoList();
        mHotVideoListPresenter.interruptHotVideoList();
    }
}
