package com.xlkj.beautifulpicturehouse.module.video.view.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.common.constant.BeautyContent;
import com.xlkj.beautifulpicturehouse.common.load.PostUtil;
import com.xlkj.beautifulpicturehouse.common.load.callback.ErrorCallback;
import com.xlkj.beautifulpicturehouse.common.rxbus.RxBus;
import com.xlkj.beautifulpicturehouse.common.util.NetUtil;
import com.xlkj.beautifulpicturehouse.common.util.PreferencesUtils;
import com.xlkj.beautifulpicturehouse.module.home.bean.Hot2RefreshBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.ReturnBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.RxPostDetailToFollow;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoDetailReqBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoDetailResBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoFollowReqBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoFollowResBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoListResBean;
import com.xlkj.beautifulpicturehouse.module.video.contract.VideoContract;
import com.xlkj.beautifulpicturehouse.module.video.model.VideoDetailModelImpl;
import com.xlkj.beautifulpicturehouse.module.video.presenter.VideoDetailPresenterImpl;
import com.xlkj.beautifulpicturehouse.module.video.presenter.VideoFollowPresenterImpl;
import com.xlkj.beautifulpicturehouse.module.video.view.adapter.VideoFollowAdapter;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.functions.Action1;

/**
 * A simple {@link Fragment} subclass.
 * 视频我的关注fragment
 *  * *  * 生命周期方法调用顺序
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
public class VideoFollowFragment extends Fragment implements VideoContract.IVideoDetailView{

    public static final String TAG = "-->VideoFollowFragment";
    private View mView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private ImageView ivBack;
    //服务器获取的所有列表数据的总页数
    private int mPagetotal;
    //请求传递的页数
    private int page = 1;
    //是否刷新 默认不刷新
    private boolean isRefresh = false;
    //是否是加载更多 默认不是
    private boolean isLoadMore = false;
    /**
     * 控件是否初始化完成
     */
    private boolean isViewCreated;
    /**
     * 数据是否加载完毕
     */
    private boolean isLoadDataCompleted;
    private VideoDetailPresenterImpl mVideoDetailPresenter;
    private List<VideoDetailResBean.DataBean.VideoListBean> mList = new ArrayList<>();
    private VideoFollowAdapter mVideoFollowAdapter;
    private VideoDetailResBean.DataBean mVideoDetailData;
    //波主tvName
    private TextView tvName;
    //本地收藏的状态-默认不是收藏状态
    private boolean isCollectState = false;

    public static VideoFollowFragment newInstance(String mId) {
        VideoFollowFragment fragment = new VideoFollowFragment();
        Bundle bundle = new Bundle();
        bundle.putString("mId",mId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindPresenter();
    }

    private void bindPresenter() {
        mVideoDetailPresenter = new VideoDetailPresenterImpl();
        mVideoDetailPresenter.attachVideoDetail(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_video_follow, container, false);
        getDataFromActivity();
        initView();
        initRxBus();
        isViewCreated = true;
        return mView;
    }

    /**
     * RxBus接收其他页面的消息
     */
    private void initRxBus() {
        /**接收从视频播放页到关注页的关注状态改变的消息*/
//        Subscription subscription = RxBus.getInstance().doSubscribe(RxPostDetailToFollow.class, new Action1<RxPostDetailToFollow>() {
//            @Override
//            public void call(RxPostDetailToFollow bean) {
//                boolean myFollow = bean.isMyFollow();
//                if (myFollow) {
//                    //需要改变关注的状态
//
//                } else {
//                    Log.e(TAG,"不需要改变关注的状态");
//                }
//            }
//        }, new Action1<Throwable>() {
//            @Override
//            public void call(Throwable throwable) {
//                Log.e(TAG,"是否接受数据："+throwable.getMessage());
//            }
//        });
//        RxBus.getInstance().addSubscription(this,subscription);
    }

    /**
     * 解绑RX
     */
    private void unbindRxBus() {
        RxBus.getInstance().unSubscribe(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
     * 从activity中读取数据
     */
    private void getDataFromActivity() {
        mVideoDetailData = BeautyContent.getVideoDetailData();
    }

    private void loadData() {
        VideoDetailReqBean bean = new VideoDetailReqBean();
        bean.setAction("videoDetail");
        VideoListResBean.DataBean.TypeListBean mData = BeautyContent.getContentBean();
        if (mData != null && mData.getFollowId() != null) {
            bean.setFollowId(mData.getFollowId());
            bean.setVideoId(mData.getVideoId());
        } else {
            bean.setVideoId("");
            bean.setFollowId("");
        }
        bean.setPage(page + "");
        String uid = PreferencesUtils.getString(getContext(), "uid");
        if (uid != null && !uid.isEmpty() && uid.equals("")) {
            bean.setUserId(uid);
        } else {
            bean.setUserId("");
        }
        mVideoDetailPresenter.getDataVideoDetail(bean);
    }

    private void initView() {
        //名称
        tvName = (TextView) mView.findViewById(R.id.tv_name_hvfl);
        if (mVideoDetailData != null && mVideoDetailData.getUserName() != null) {
            tvName.setText(mVideoDetailData.getUserName());
        } else {
            Log.e(TAG,"波主名字数据为空");
        }
        //返回键
        ivBack = (ImageView) mView.findViewById(R.id.iv_back_fvf);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2018/1/9 RxBus发送消息 返回到viewpager第一页
                ReturnBean mReturnBean = new ReturnBean();
                mReturnBean.setReturn(true);
                RxBus.getInstance().post(mReturnBean);
            }
        });
        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.srl_fvf);
        setRefresh();
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.rv_fvf);
        mLayoutManager = new GridLayoutManager(getContext(), 3);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mVideoFollowAdapter = new VideoFollowAdapter(getContext(), mList);
        mRecyclerView.setAdapter(mVideoFollowAdapter);
        //上拉加载更多
        scrollEvent();
    }

    /**
     * 加载更多
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
                        if (NetUtil.isConnected(getContext())) {
                            if (page < mPagetotal) {
                                //加载中
                                mVideoFollowAdapter.setFootView(0);
                                page++;
                                loadData();
                            } else {
                                //全部加载 我是有底线的
                                mVideoFollowAdapter.setFootView(4);
                            }
                        } else {
                            //网路断开
                            mVideoFollowAdapter.setFootView(2);
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

    /**
     * 刷新
     */
    private void setRefresh() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                page = 1;
                loadData();
            }
        });
    }

    /*@Override
    public void showErrorVideoFollow() {
        if (isRefresh) {
            *//**刷新*//*
            //刷新失败的逻辑
            //停止刷新
            mSwipeRefreshLayout.setRefreshing(false);
            //还原刷新的状态 设置成不是刷新
            if (isRefresh) {
                isRefresh = false;
            }
            Toast.makeText(getContext(), "刷新失败", Toast.LENGTH_SHORT).show();
        } else if (isLoadMore) {
            *//**加载更多*//*
            //加载更多失败的逻辑
            mVideoFollowAdapter.setFootView(2);
            //还原加载更多的状态 设置成不是加载更多
            if (isLoadMore) {
                isLoadMore = false;
            }
            Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
        } else {
            *//**不是刷新也不是加载更多的逻辑*//*
            //不是刷新失败的逻辑
            //显示加载失败
        }
    }

    @Override
    public void setDataVideoFollow(VideoFollowResBean bean) {
        if (bean != null) {
            //设置加载数据成功完成
            isLoadDataCompleted = true;
            if (isRefresh) {
                mSwipeRefreshLayout.setRefreshing(false);
                mList.clear();
                Toast.makeText(getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
            }
            mList.addAll(bean.getData().getInfo().getVedio());
            mVideoFollowAdapter.notifyDataSetChanged();
            mPagetotal = bean.getData().getInfo_size();
            //还原刷新的状态 设置成不是刷新
            if (isRefresh) {
                isRefresh = false;
            }
            //还原加载更多的状态 设置成不是加载更多
            if (isLoadMore) {
                isLoadMore = false;
            }
        }
    }*/

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindRxBus();
        unbindPresenter();
    }

    private void unbindPresenter() {
        if (mVideoDetailPresenter != null) {
            mVideoDetailPresenter.interruptHotVideoList();
            mVideoDetailPresenter.detachVideoDetail();
        }
    }

    /**
     * 波主的全部视频回调失败
     */
    @Override
    public void showErrorVideoDetail() {
        if (isRefresh) {
            /**刷新*/
            //刷新失败的逻辑
            //停止刷新
            mSwipeRefreshLayout.setRefreshing(false);
            //还原刷新的状态 设置成不是刷新
            if (isRefresh) {
                isRefresh = false;
            }
            Toast.makeText(getContext(), "刷新失败", Toast.LENGTH_SHORT).show();
        } else if (isLoadMore) {
            /**加载更多*/
            //加载更多失败的逻辑
            mVideoFollowAdapter.setFootView(2);
            //还原加载更多的状态 设置成不是加载更多
            if (isLoadMore) {
                isLoadMore = false;
            }
            Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
        } else {
            /**不是刷新也不是加载更多的逻辑*/
            //不是刷新失败的逻辑
            //显示加载失败
        }
    }

    /**
     * 波主的全部视频回调成功
     * @param bean
     */
    @Override
    public void setDataVideoDetail(VideoDetailResBean bean) {
        if (bean != null) {
            //设置加载数据成功完成
            isLoadDataCompleted = true;
            if (isRefresh) {
                mSwipeRefreshLayout.setRefreshing(false);
                mList.clear();
                //Toast.makeText(getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
            }
            mList.addAll(bean.getData().getVideoList());
            mVideoFollowAdapter.notifyDataSetChanged();
            mPagetotal = bean.getData().getTotalPage();
            //还原刷新的状态 设置成不是刷新
            if (isRefresh) {
                isRefresh = false;
            }
            //还原加载更多的状态 设置成不是加载更多
            if (isLoadMore) {
                isLoadMore = false;
            }
        }
    }
}
