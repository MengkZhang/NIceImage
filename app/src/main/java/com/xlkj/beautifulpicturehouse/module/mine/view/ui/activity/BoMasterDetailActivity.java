package com.xlkj.beautifulpicturehouse.module.mine.view.ui.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.common.constant.BeautyContent;
import com.xlkj.beautifulpicturehouse.common.rxbus.RxBus;
import com.xlkj.beautifulpicturehouse.common.util.NetUtil;
import com.xlkj.beautifulpicturehouse.common.util.PreferencesUtils;
import com.xlkj.beautifulpicturehouse.common.view.widget.SwipeBackLayout;
import com.xlkj.beautifulpicturehouse.module.video.bean.ReturnBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoDetailReqBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoDetailResBean;
import com.xlkj.beautifulpicturehouse.module.video.contract.VideoContract;
import com.xlkj.beautifulpicturehouse.module.video.presenter.VideoDetailPresenterImpl;
import com.xlkj.beautifulpicturehouse.module.video.view.adapter.VideoFollowAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 波主详情页activity
 */
public class BoMasterDetailActivity extends AppCompatActivity implements VideoContract.IVideoDetailView{

    //波主id
    private String mFollowId;
    //波主name
    private String mFollowName;
    //用户id
    private String mUid;
    public static final String TAG = "-->BoMasterDetailA";
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
    private VideoDetailPresenterImpl mVideoDetailPresenter;
    private List<VideoDetailResBean.DataBean.VideoListBean> mList = new ArrayList<>();
    private VideoFollowAdapter mVideoFollowAdapter;
    //波主tvName
    private TextView tvName;
    private SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //根本没有导航栏的写法
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.fragment_video_follow);
        mSwipeBackLayout = new SwipeBackLayout(this);
        mFollowName = getIntent().getStringExtra("bo_name");
        mFollowId = getIntent().getStringExtra("followId");
        mUid = PreferencesUtils.getString(this, "uid");
        bindPresenter();
        initView();
        loadData();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mSwipeBackLayout.attachToActivity(this,SwipeBackLayout.EDGE_LEFT);
        // 触摸边缘变为屏幕宽度的1/2
        mSwipeBackLayout.setEdgeSize(getResources().getDisplayMetrics().widthPixels / 2);
    }

    private void bindPresenter() {
        mVideoDetailPresenter = new VideoDetailPresenterImpl();
        mVideoDetailPresenter.attachVideoDetail(this);
    }

    private void unbindPresenter() {
        if (mVideoDetailPresenter != null) {
            mVideoDetailPresenter.detachVideoDetail();
            mVideoDetailPresenter.interruptHotVideoList();
        }
    }

    @Override
    public void onDestroy() {
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
     * 请求数据
     */
    private void loadData() {
        VideoDetailReqBean bean = new VideoDetailReqBean();
        bean.setAction("videoDetail");
        bean.setVideoId("");
        bean.setFollowId(mFollowId);
        bean.setPage(page + "");
        bean.setUserId(mUid);
        mVideoDetailPresenter.getDataVideoDetail(bean);
    }

    private void initView() {
        //名称
        tvName = (TextView) findViewById(R.id.tv_name_hvfl);
        if (mFollowName != null) {
            tvName.setText(mFollowName);
        }
        //返回键
        ivBack = (ImageView) findViewById(R.id.iv_back_fvf);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_fvf);
        setRefresh();
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_fvf);
        mLayoutManager = new GridLayoutManager(this, 3);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mVideoFollowAdapter = new VideoFollowAdapter(this, mList);
        mVideoFollowAdapter.setFollowId(mFollowId);
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
                        if (NetUtil.isConnected(BoMasterDetailActivity.this)) {
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
            Toast.makeText(this, "刷新失败", Toast.LENGTH_SHORT).show();
        } else if (isLoadMore) {
            /**加载更多*/
            //加载更多失败的逻辑
            mVideoFollowAdapter.setFootView(2);
            //还原加载更多的状态 设置成不是加载更多
            if (isLoadMore) {
                isLoadMore = false;
            }
            Toast.makeText(this, "加载失败", Toast.LENGTH_SHORT).show();
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
            if (bean.getData() != null) {
                BeautyContent.setVideoDetailData(bean.getData());
            }
            //设置加载数据成功完成
            if (isRefresh) {
                mSwipeRefreshLayout.setRefreshing(false);
                mList.clear();
                //Toast.makeText(this, "刷新成功", Toast.LENGTH_SHORT).show();
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
