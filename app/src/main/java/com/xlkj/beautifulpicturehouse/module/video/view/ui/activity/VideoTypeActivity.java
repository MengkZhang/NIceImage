package com.xlkj.beautifulpicturehouse.module.video.view.ui.activity;

import android.content.Context;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.kingja.loadsir.core.Transport;
import com.umeng.analytics.MobclickAgent;
import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.common.constant.Constant;
import com.xlkj.beautifulpicturehouse.common.load.PostUtil;
import com.xlkj.beautifulpicturehouse.common.load.callback.EmptyCallback;
import com.xlkj.beautifulpicturehouse.common.load.callback.ErrorCallback;
import com.xlkj.beautifulpicturehouse.common.load.callback.LoadingCallback;
import com.xlkj.beautifulpicturehouse.common.util.NetUtil;
import com.xlkj.beautifulpicturehouse.common.util.PreferencesUtils;
import com.xlkj.beautifulpicturehouse.common.util.StringUtils;
import com.xlkj.beautifulpicturehouse.common.view.ui.activity.base.BaseStateActivity;
import com.xlkj.beautifulpicturehouse.common.view.ui.dialog.share.DisplayUtil;
import com.xlkj.beautifulpicturehouse.common.view.widget.SwipeBackLayout;
import com.xlkj.beautifulpicturehouse.module.video.bean.HotVideoListReqBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.HotVideoListResBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoListReqBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoListResBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoSearchReqBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoSearchResBean;
import com.xlkj.beautifulpicturehouse.module.video.contract.VideoContract;
import com.xlkj.beautifulpicturehouse.module.video.presenter.HotVideoListPresenterImpl;
import com.xlkj.beautifulpicturehouse.module.video.presenter.VideoSearchPresenterImpl;
import com.xlkj.beautifulpicturehouse.module.video.view.adapter.VideoHotAdapter;
import com.xlkj.beautifulpicturehouse.module.video.view.adapter.VideoSearchMoreAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 视频分类activity
 */
public class VideoTypeActivity extends BaseStateActivity implements VideoContract.IVideoSearchView{

    public static final String TAG = "-->>VideoTypeActivity-";
    private SwipeBackLayout mSwipeBackLayout;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    //搜索视频更多presenter
    private VideoSearchPresenterImpl mVideoSearchPresenterImpl;
    private List<VideoSearchResBean.DataBean.VideoBean> mVideoList = new ArrayList<>();
    private VideoSearchMoreAdapter mVideoHotAdapter;
    //服务器返回的总页数
    private int mPageCount;
    //请求传递的页数
    private int page = 1;
    private LoadService mLoadService;
    //是否刷新 默认不刷新
    private boolean isRefresh = false;
    //上一个页面传来的搜索关键字  --如果是标签页的跳转 这里需要传标签的id
    private String mTypeName;
    //0 点击搜索中的更多传的是关键字  1 其他搜索（视频标签到列表）传的是id
    private String mById;
    //上个页面传过来的标题
    private String mTitle;
    private TextView tvTitle;
    //内容跟布局用来加载loadSir
    private LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.activity_video_type);
        mSwipeBackLayout = new SwipeBackLayout(this);
        getDataFromUp();
        mVideoSearchPresenterImpl = new VideoSearchPresenterImpl();
        mVideoSearchPresenterImpl.attachVideoSearch(this);
        initView();
        loadData();
        initLoadSir();
    }

    /**
     * 获取上一个页面的数据
     */
    private void getDataFromUp() {
        mTypeName = getIntent().getStringExtra("typeName");
        mById = getIntent().getStringExtra("byId");
        mTitle = getIntent().getStringExtra("title");
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mSwipeBackLayout.attachToActivity(this,SwipeBackLayout.EDGE_LEFT);
        // 触摸边缘变为屏幕宽度的1/2
        mSwipeBackLayout.setEdgeSize(getResources().getDisplayMetrics().widthPixels / 2);
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
     * 请求数据(测试参数)
     */
    private void loadData() {
        VideoSearchReqBean bean = new VideoSearchReqBean();
        //0 点击搜索中的更多传的是关键字  1 其他搜索（视频标签到列表）传的是id
        if (mById != null) {
            if (mById.equals("0")) {
                bean.setType("0");
            } else {
                bean.setType("1");
            }
        } else {
            bean.setType("0");
        }
        bean.setAction("searchVideo");
        bean.setPage(page + "");
        bean.setSearchKey(mTypeName);
//        http://api.app.27270.com/api/index.php?action=searchVideo&searchKey=%E5%A5%B3&page=1
        mVideoSearchPresenterImpl.getDataVideoSearch(bean);
    }

    private void initView() {
        //内容跟布局
        mLinearLayout = (LinearLayout) findViewById(R.id.ll_content_avt);
        //分享
        findViewById(R.id.tv_follow_btl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share();
            }
        });
        //返回键
        findViewById(R.id.beauty_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //标题
        tvTitle = (TextView) findViewById(R.id.beauty_title);
        if (mTitle != null) {
            tvTitle.setText(mTitle);
        }
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_avt);
        //改变加载显示的颜色
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_blue_light);
        //下拉刷新
        setRefresh();
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_avt);
        mLayoutManager = new GridLayoutManager(this, 2);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mVideoHotAdapter = new VideoSearchMoreAdapter(this, mVideoList);
        mRecyclerView.setAdapter(mVideoHotAdapter);
        setOnScroll();
    }

    /**
     * 点击分享
     */
    private void share() {
        DisplayUtil.showShareDialog(this, "美女屋", "宅男们都震精啦!!!~", Constant.SHARE_URL);
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
                        if (NetUtil.isConnected(VideoTypeActivity.this)) {
                            if (page < mPageCount) {
                                //加载中
                                mVideoHotAdapter.setFootView(0);
                                page++;
                                loadData();
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

    /**
     * 下拉刷新
     */
    private void setRefresh() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    isRefresh = true;
                    page = 1;
                    loadData();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "setRefresh下拉刷新异常");
                }
            }
        });
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindPresenter();
    }

    private void unbindPresenter() {
        mVideoSearchPresenterImpl.detachVideoSearch();
        mVideoSearchPresenterImpl.interruptHttp();
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
     * 搜索回调失败
     */
    @Override
    public void showErrorVideoSearch() {
        Log.e(TAG, "图片数据请求失败");
        //这里逻辑应该是在不是刷新的状态下才执行的
        if (!isRefresh) {
            //发送请求失败的消息
            PostUtil.postCallbackDelayed(mLoadService, ErrorCallback.class);
        } else {
            //刷新失败的逻辑
            Toast.makeText(this, "刷新失败~~", Toast.LENGTH_SHORT).show();
            //停止刷新
            mSwipeRefreshLayout.setRefreshing(false);
            //还原刷新的状态 设置成不是刷新
            if (isRefresh) {
                isRefresh = false;
            }
        }
    }

    /**
     * 搜索回调成功
     * @param bean
     */
    @Override
    public void setDataVideoSearch(VideoSearchResBean bean) {

        //停止刷新
        mSwipeRefreshLayout.setRefreshing(false);
        if (bean != null) {
            VideoSearchResBean.DataBean data = bean.getData();
            if (data != null) {
                //获取总页数
                mPageCount = data.getTotalPage();
                List<VideoSearchResBean.DataBean.VideoBean> video = data.getVideo();
                if (video != null && video.size() != 0) {
                    //显示加载成功
                    PostUtil.postSuccessDelayed(mLoadService);
                    if (isRefresh) {
                        mVideoList.clear();
                        //Toast.makeText(this, "刷新成功", Toast.LENGTH_SHORT).show();
                    }
                    mVideoList.addAll(video);
                    mVideoHotAdapter.notifyDataSetChanged();
                    //获取总页数
                    mPageCount = bean.getData().getTotalPage();
                    //还原刷新的状态 设置成不是刷新
                    if (isRefresh) {
                        isRefresh = false;
                    }

                } else {
                    //数据为空
                    PostUtil.postCallbackDelayed(mLoadService, EmptyCallback.class);
                    Log.e(TAG,"video数组W为空");
                }
            } else {
                Log.e(TAG,"setDataVideoSearch data为空");
            }
        } else {
            //数据为空
            PostUtil.postCallbackDelayed(mLoadService, EmptyCallback.class);
        }
    }
}
