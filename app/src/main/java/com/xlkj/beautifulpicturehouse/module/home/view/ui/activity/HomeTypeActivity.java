package com.xlkj.beautifulpicturehouse.module.home.view.ui.activity;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageView;
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
import com.xlkj.beautifulpicturehouse.common.manager.FollowManager;
import com.xlkj.beautifulpicturehouse.common.util.NetUtil;
import com.xlkj.beautifulpicturehouse.common.util.PreferencesUtils;
import com.xlkj.beautifulpicturehouse.common.view.ui.activity.base.BaseStateActivity;
import com.xlkj.beautifulpicturehouse.common.view.ui.dialog.share.DisplayUtil;
import com.xlkj.beautifulpicturehouse.common.view.widget.SwipeBackLayout;
import com.xlkj.beautifulpicturehouse.module.home.bean.FollowReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.FollowResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomeHotNewResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomeHotPicResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomeHotPicResMgqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomePicGoodChoiceReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.HotListPicReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.SearchReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.SearchResBean;
import com.xlkj.beautifulpicturehouse.module.home.contract.HomeContract;
import com.xlkj.beautifulpicturehouse.module.home.presenter.FollowPresenterImpl;
import com.xlkj.beautifulpicturehouse.module.home.presenter.HotPicListPresenterImpl;
import com.xlkj.beautifulpicturehouse.module.home.presenter.SearchPresenterImpl;
import com.xlkj.beautifulpicturehouse.module.home.view.adapter.HomeTypeAdapter;
import com.xlkj.beautifulpicturehouse.module.mine.view.ui.activity.LoginActivity;
import com.xlkj.beautifulpicturehouse.module.mine.view.ui.activity.MyFollowActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页中分类的activity 如美胸 性感 欧美 日韩 护士 短裙等
 */
public class HomeTypeActivity extends BaseStateActivity implements HomeContract.ISearchView/*,HomeContract.IFollowView*/{

    public static final String TAG = "-->>HomeTypeActivity-";
    public static final String YFOLLOW = "已关注";
    public static final String FOLLOW = "关注";
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private SearchPresenterImpl mSearchPresenter;
    //放最热图片数据列表
    private List<SearchResBean.DataBean.ImageListBean> mHotPicList = new ArrayList<>();
    //服务器获取的所有列表数据的总页数
    private int mPagetotal;
    //请求传递的页数
    private int page = 1;
    private HomeTypeAdapter mAdapter;
    private LoadService mLoadService;
    //是否刷新 默认不刷新
    private boolean isRefresh = false;
    //是否是加载更多 默认不是
    private boolean isLoadMore = false;
    private SwipeBackLayout mSwipeBackLayout;
    //加载状态的布局 加载失败 重试等状态
    private RelativeLayout mRelativeLayout;
    private String mSearchId;
    private String mTypeName;
    //isEnterFromFollowTag是否是从我关注的标签页面进入列表页的 0：是（需要隐藏关注）  1：不是（不需要隐藏关注）
    private String mIsEnterFromFollowTag;
    private TextView tvTitle;
    private String mClickIdSearch;
    private ImageView tvFollow;
    //点击关注
    private RelativeLayout rlFollow;
    //关注或取消关注presenter
//    private FollowPresenterImpl mFollowPresenter;
    //本地关注的状态-默认不是关注状态
    private boolean isFollowState = false;
    ////关注或已经关注的显示文本
    private TextView tvFollowOrCancel;
    //服务器返回的标签是否被关注过：0 没被关注 1 已被关注
    private int mIsFollow;
    //点击查看我的关注
    private TextView tvToMyFollow;
    //关注的跟布局
    private RelativeLayout rlFollowRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.activity_home_type);
        bindPresenter();
        mSwipeBackLayout = new SwipeBackLayout(this);
        getDataFromUp();
        initView();
        loadData();
        initLoadSir();
    }

    /**
     * 绑定操作
     */
    private void bindPresenter() {
//        mSearchPresenter = new SearchPresenterImpl(this);
        mSearchPresenter = new SearchPresenterImpl();
        mSearchPresenter.attachSearch(this);
        //关注或取消关注presenter实例化
//        mFollowPresenter = new FollowPresenterImpl(this);
//        mFollowPresenter.attachFollow(this);
    }

    /**
     * 解绑操作
     */
    private void unbindPresenter() {
        if (mSearchPresenter != null) {
            mSearchPresenter.interruptHttp();
            mSearchPresenter.detachSearch();
        }
//        if (mFollowPresenter != null) {
//            mFollowPresenter.interruptHttp();
//            mFollowPresenter.detachFollow();
//        }
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

    private void getDataFromUp() {
        //clickIdSearch: 0 是点击搜索  1 不是
//        intent.putExtra("clickIdSearch","1");
        mClickIdSearch = getIntent().getStringExtra("clickIdSearch");
        mSearchId = getIntent().getStringExtra("searchId");
        mTypeName = getIntent().getStringExtra("typeName");
        mIsEnterFromFollowTag = getIntent().getStringExtra("isEnterFromFollowTag");
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
            mLoadService = LoadSir.getDefault().register(mRelativeLayout, new Callback.OnReloadListener() {
                @Override
                public void onReload(View v) {
                    mLoadService.showCallback(LoadingCallback.class);
                    loadData();
                }
            }).setCallBack(EmptyCallback.class, new Transport() {
                @Override
                public void order(Context context, View view) {
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
        SearchReqBean bean = new SearchReqBean();
        //0 点击搜索 1 其他搜索
        //clickIdSearch: 0 是点击搜索  1 不是
        String uid = PreferencesUtils.getString(this, "uid");
        if (uid != null && !uid.isEmpty() && !uid.equals("")) {
            bean.setUid(uid);
        } else {
            bean.setUid("");
        }
        if (mClickIdSearch.equals("1")) {
            bean.setType("1");
        } else {
            bean.setType("0");
        }
        bean.setTypeVideo("2");
        bean.setPage(page+"");
        bean.setAction("search");
        bean.setSearchKey(mSearchId);
        mSearchPresenter.getDataSearch(bean);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        //如果clickIdSearch=0 则隐藏关注栏目
        rlFollowRootView = (RelativeLayout) findViewById(R.id.rl_follow_content_tfl);
        //判断如果是搜索关键字进来的 或者从我的关注页面进来的 则隐藏关注页面
        if (mClickIdSearch != null) {
            if(mClickIdSearch.equals("0") || mIsEnterFromFollowTag.equals("0")) {
                rlFollowRootView.setVisibility(View.GONE);
            } else {
                rlFollowRootView.setVisibility(View.VISIBLE);
            }
        } else {
            rlFollowRootView.setVisibility(View.GONE);
        }
        //点击查看我的关注
        tvToMyFollow = (TextView) findViewById(R.id.tv_my_follow_tfl);
        tvToMyFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jump2myFollow();
            }
        });
        //点击关注
        rlFollow = (RelativeLayout) findViewById(R.id.rl_follow_tfl);
        //关注或已经关注的显示文本
        tvFollowOrCancel = (TextView) findViewById(R.id.tv_is_follow_tfl);
        rlFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                follow();
            }
        });
        //点击分享
        tvFollow = (ImageView) findViewById(R.id.tv_follow_btl);
        tvFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2018/1/8 分享
                share();
            }
        });
        //加载状态的布局 加载失败 重试等状态
        mRelativeLayout = (RelativeLayout) findViewById(R.id.rl_re_load_aht);
        //返回键
        findViewById(R.id.beauty_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //标题
        tvTitle = ((TextView) findViewById(R.id.beauty_title));
        tvTitle.setText(mTypeName);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_aht);
        //改变加载显示的颜色
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_blue_light);
        //下拉刷新
        setRefresh();
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_aht);
        mLayoutManager = new GridLayoutManager(this, 2);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new HomeTypeAdapter(this, mHotPicList);
        mRecyclerView.setAdapter(mAdapter);
        //上拉加载更多
        setOnScroll();
        //滚动到列表顶部
        toTop();

    }

    /**
     * 滚动到列表顶部
     */
    private void toTop() {
        // 2018/1/15 点击滚动到顶部rl_title_btl
        findViewById(R.id.rl_title_btl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mLayoutManager.scrollToPositionWithOffset(1, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG,"滚动到顶部异常");
                }
            }
        });
    }

    /**
     * 分享
     */
    private void share() {
        DisplayUtil.showShareDialog(this, "美女屋", "宅男们都震精啦!!!~", Constant.SHARE_URL);
    }

    /**
     * 到我的关注页面
     */
    private void jump2myFollow() {
        String uid = PreferencesUtils.getString(this, "uid");
        if (uid != null && !uid.isEmpty() && !uid.equals("")) {
            Intent intent = new Intent(this, MyFollowActivity.class);
            startActivity(intent);
        } else {
            jump2login();
        }
    }

    /**
     * 关注或者取消关注
     */
    private void follow() {
        //followId	string	被关注人Id
        //userId	string	用户Id
        //isFollow	int	0取消 1关注
        //type	int	关注类型0人 1标签
       /* FollowReqBean bean = new FollowReqBean();
        String followId;
        if (!isFollowState) {
            //点击就关注
            int isFollow = 1;
            bean.setIsFollow(isFollow + "");
        } else {
            //点击就取消关注
            int isFollow = 0;
            bean.setIsFollow(isFollow + "");
        }
        int type = 1;
        //获取用户id
        String uid = PreferencesUtils.getString(this, "uid");
        if (uid != null && !uid.isEmpty() && !uid.equals("")) {
            bean.setUserId(uid);
        } else {
            *//**到登录*//*
            jump2login();
            //如果uid为空不需要执行下面的逻辑
            return;
        }
        if (mSearchId != null && !mSearchId.isEmpty() && !mSearchId.equals("")) {
            followId = mSearchId;
        } else {
            followId = "";    
        }
        bean.setAction("follow");
        bean.setType(type+"");
        bean.setFollowId(followId);
        mFollowPresenter.getDataFollow(bean);*/

        //获取用户id
        String uid = PreferencesUtils.getString(this, "uid");
        if (uid != null && !uid.isEmpty() && !uid.equals("")) {
            FollowManager.getInstance().setFollow(isFollowState, rlFollow, tvFollowOrCancel, uid, mSearchId, 1, new FollowManager.FollowCallBack() {
                @Override
                public void onSuccess(FollowResBean bean) {
                    if (bean != null) {
                        int state = bean.getState();
                        if (state == 1) {
                            //关注成功
                            followState();
                        } else {
                            //取消关注成功
                            notFollowState();
                        }
                    }
                    Toast.makeText(HomeTypeActivity.this, "" + bean.getMsg()+"", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailed(String errorMsg) {
                    Toast.makeText(HomeTypeActivity.this, "error:"+errorMsg, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            jump2login();
        }

    }

    /**
     * 关注或取消关注失败回调
     */
    /*@Override
    public void showErrorFollow() {
        Log.e(TAG,"关注失败");
//        notFollowState();
        Toast.makeText(this, "关注失败", Toast.LENGTH_SHORT).show();
    }

    *//**
     * 关注或取消关注成功回调
     * @param bean
     *//*
    @Override
    public void setDataFollow(FollowResBean bean) {
        Log.e(TAG,"关注成功："+bean.getMsg());
        if (bean != null) {
            int state = bean.getState();
            if (state == 1) {
                //关注成功
                followState();
            } else {
                //取消关注成功
                notFollowState();
            }
        }
        Toast.makeText(this, bean.getMsg()+"", Toast.LENGTH_SHORT).show();
    }*/

    /**
     * 没被关注过的状态
     */
    private void notFollowState() {
        isFollowState = false;
        tvFollowOrCancel.setText(FOLLOW);
        rlFollow.setBackgroundResource(R.drawable.follow_checked_shape);
    }

    /**
     * 被关注过的状态
     */
    private void followState() {
        isFollowState = true;
        tvFollowOrCancel.setText(YFOLLOW);
        rlFollow.setBackgroundResource(R.drawable.follow_unchecked_shape);
    }

    /**
     * 到登录页
     */
    private void jump2login() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
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
                        if (NetUtil.isConnected(HomeTypeActivity.this)) {
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
    protected void onDestroy() {
        super.onDestroy();
        unbindPresenter();
    }


    @Override
    public void showErrorSearch() {
        Log.e(TAG, "图片数据请求失败");
        //这里逻辑应该是在不是刷新的状态下才执行的
        /*if (!isRefresh) {
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
        }*/

        if (isRefresh) {
            /**刷新*/
            //刷新失败的逻辑
            Toast.makeText(this, "刷新失败~~", Toast.LENGTH_SHORT).show();
            //停止刷新
            mSwipeRefreshLayout.setRefreshing(false);
            //还原刷新的状态 设置成不是刷新
            if (isRefresh) {
                isRefresh = false;
            }
        } else if (isLoadMore) {
            /**加载更多*/
            mAdapter.setFootView(2);
            //还原加载更多的状态 设置成不是加载更多
            if (isLoadMore) {
                isLoadMore = false;
            }
            Toast.makeText(this, "加载失败", Toast.LENGTH_SHORT).show();
        } else {
            /**不是刷新也不是加载更多的逻辑*/
            //发送请求失败的消息
            PostUtil.postCallbackDelayed(mLoadService, ErrorCallback.class);
        }
    }

    @Override
    public void setDataSearch(SearchResBean bean) {
       //停止刷新
        mSwipeRefreshLayout.setRefreshing(false);
        if (bean != null && bean.getData() != null) {
            PostUtil.postSuccessDelayed(mLoadService);
            //如果是刷新 则先清空一下数据
            if (isRefresh) {
                mHotPicList.clear();
            }
            mHotPicList.addAll(bean.getData().getImageList());
            mAdapter.notifyDataSetChanged();
            //获取所有数据的页数
            mPagetotal = bean.getData().getTotalPage();
            //获取是否被关注过
            mIsFollow = bean.getData().getIsFollow();
            if (mIsFollow == 1) {
                //已关注的状态
                followState();
            } else {
                //没被关注的状态
                notFollowState();
            }
            //还原刷新的状态 设置成不是刷新
            if (isRefresh) {
                //Toast.makeText(this, "刷新成功", Toast.LENGTH_SHORT).show();
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


}
