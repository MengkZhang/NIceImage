package com.xlkj.beautifulpicturehouse.module.mine.view.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.TintContextWrapper;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.kingja.loadsir.core.Transport;
import com.umeng.analytics.MobclickAgent;
import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.common.load.PostUtil;
import com.xlkj.beautifulpicturehouse.common.load.callback.EmptyCallback;
import com.xlkj.beautifulpicturehouse.common.load.callback.ErrorCallback;
import com.xlkj.beautifulpicturehouse.common.load.callback.LoadingCallback;
import com.xlkj.beautifulpicturehouse.common.util.PreferencesUtils;
import com.xlkj.beautifulpicturehouse.common.view.ui.activity.base.BaseStateActivity;
import com.xlkj.beautifulpicturehouse.common.view.widget.SwipeBackLayout;
import com.xlkj.beautifulpicturehouse.module.home.bean.AboutTagReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.AboutTagResBean;
import com.xlkj.beautifulpicturehouse.module.mine.bean.MyFootStayReqBean;
import com.xlkj.beautifulpicturehouse.module.mine.bean.MyFootStayResBean;
import com.xlkj.beautifulpicturehouse.module.mine.contract.MineContract;
import com.xlkj.beautifulpicturehouse.module.mine.presenter.MyFootStayPresenterImpl;
import com.xlkj.beautifulpicturehouse.module.mine.view.adapter.MyFootStayAdapter;
import com.xlkj.beautifulpicturehouse.module.mine.view.adapter.MyFootStayHistoryAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的足迹activity
 */
public class MyFootStayActivity extends BaseStateActivity implements MineContract.IMyFootStayView {

    public static final String TAG = "-->MyFootStayActivity";
    private RecyclerView mRecyclerView;
    //加载loadSir的布局
    private LinearLayout mLinearLayout;
    //loadSir
    private LoadService mLoadService;
    //我的足迹p
    private MyFootStayPresenterImpl mMyFootStayPresenter;
    //今天数据
    private List<MyFootStayResBean.DataBean.TodayListBean> mList = new ArrayList<>();
    //历史数据
    private List<MyFootStayResBean.DataBean.HistoryListBean> mListHistory = new ArrayList<>();
    private MyFootStayAdapter mMyFootStayAdapter;
    private SwipeBackLayout mSwipeBackLayout;
    //今天
    private RelativeLayout rlToday;
    //历史
    private RelativeLayout rlHistory;
    //rvHistory
    private RecyclerView rvHistory;
    private MyFootStayHistoryAdapter mHistoryAdapter;
    //今天足迹是否为空-默认不是
    private boolean todayIsEmpty = false;
    //历史足迹是否为空-默认不是
    private boolean historyIsEmpty = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.activity_my_foot_stay);
        mSwipeBackLayout = new SwipeBackLayout(this);
        bindPresenter();
        initView();
        loadData();
        initLoadSir();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mSwipeBackLayout.attachToActivity(this,SwipeBackLayout.EDGE_LEFT);
        // 触摸边缘变为屏幕宽度的1/2
        mSwipeBackLayout.setEdgeSize(getResources().getDisplayMetrics().widthPixels / 2);
    }

    /**
     * 绑定p
     */
    private void bindPresenter() {
        mMyFootStayPresenter = new MyFootStayPresenterImpl();
        mMyFootStayPresenter.attachMyFootStay(this);
    }

    /**
     * 解绑p
     */
    private void unbindPresenter() {
        if (mMyFootStayPresenter != null) {
            mMyFootStayPresenter.detachMyFootStay();
            mMyFootStayPresenter.interruptHttp();
        }
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
     * 请求数据
     */
    private void loadData() {
        MyFootStayReqBean bean = new MyFootStayReqBean();
        bean.setAction("historyList");
        String uid = PreferencesUtils.getString(this, "uid");
        if (uid != null && !uid.isEmpty() && !uid.equals("")) {
            bean.setUserId(uid);
        } else {
            bean.setUserId("");
        }
        mMyFootStayPresenter.getDataMyFootStay(bean);
    }

    private void initView() {
        //返回键
        findViewById(R.id.beauty_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                testException();
                finish();
            }
        });
        //隐藏分享
        findViewById(R.id.tv_follow_btl).setVisibility(View.GONE);
        //今天布局
        rlToday = (RelativeLayout) findViewById(R.id.rl_today_amfs);
        //历史布局
        rlHistory = (RelativeLayout) findViewById(R.id.rl_history_amfs);
        //加载loadSir的布局
        mLinearLayout = (LinearLayout) findViewById(R.id.ll_content_amfs);
        //历史列表
        rvHistory = (RecyclerView) findViewById(R.id.rv_history_amf);
        rvHistory.setLayoutManager(new LinearLayoutManager(MyFootStayActivity.this));
        mHistoryAdapter = new MyFootStayHistoryAdapter(this, mListHistory);
        rvHistory.setAdapter(mHistoryAdapter);
        rvHistory.setNestedScrollingEnabled(false);
        //今天列表
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_amf);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MyFootStayActivity.this));
        mMyFootStayAdapter = new MyFootStayAdapter(this, mList);
        mRecyclerView.setAdapter(mMyFootStayAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);
    }

    private void testException() {
        String str = null;
        Toast.makeText(this, str.toString() + "", Toast.LENGTH_SHORT).show();
    }

    /**
     * 我的足迹失败回调
     */
    @Override
    public void showErrorFootStay() {
        Log.e(TAG,"请求数据失败");
        showFailedLoadSir();
    }

    /**
     * 我的足迹回调成功
     * @param bean
     */
    @Override
    public void setDataFootStay(MyFootStayResBean bean) {
        Log.e(TAG,"我的足迹数据bean:"+bean.toString());
        if (bean != null) {
            showSuccessLoadSir();
            MyFootStayResBean.DataBean data = bean.getData();
            if (data != null) {
                //今天数据
                List<MyFootStayResBean.DataBean.TodayListBean> todayList = data.getTodayList();
                if (todayList != null && todayList.size() != 0) {
                    mList.addAll(todayList);
                    mMyFootStayAdapter.notifyDataSetChanged();
                    rlToday.setVisibility(View.VISIBLE);
                    todayIsEmpty = false;
                } else {
                    Log.e(TAG,"今天数据为空");
                    rlToday.setVisibility(View.GONE);
                    todayIsEmpty = true;
                }

                //历史数据
                List<MyFootStayResBean.DataBean.HistoryListBean> historyList = data.getHistoryList();
                if (historyList != null && historyList.size() != 0) {
                    mListHistory.addAll(historyList);
                    mHistoryAdapter.notifyDataSetChanged();
                    rlHistory.setVisibility(View.VISIBLE);
                    historyIsEmpty = false;
                } else {
                    Log.e(TAG,"历史数据为空");
                    rlHistory.setVisibility(View.GONE);
                    historyIsEmpty = true;
                }
                if (todayIsEmpty && historyIsEmpty) {
                    // TODO: 2018/1/18 数据为空的布局显示
                    showFailedLoadSir();
                }

            } else {
                Log.e(TAG,"setDataFootStay data数据为空");
                showFailedLoadSir();
            }

        } else {
            Log.e(TAG,"数据为空");
            showFailedLoadSir();
        }
    }
}
