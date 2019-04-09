//package com.xlkj.beautifulpicturehouse.module.home.view.ui.activity;
//
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.support.annotation.Nullable;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.WindowManager;
//import android.widget.Toast;
//
//import com.xlkj.beautifulpicturehouse.R;
//import com.xlkj.beautifulpicturehouse.common.view.ui.dialog.share.DisplayUtil;
//import com.xlkj.beautifulpicturehouse.common.view.widget.SwipeBackLayout;
//import com.xlkj.beautifulpicturehouse.module.home.bean.GoodDetailReqBean;
//import com.xlkj.beautifulpicturehouse.module.home.bean.GoodDetailResBean;
//import com.xlkj.beautifulpicturehouse.module.home.contract.HomeContract;
//import com.xlkj.beautifulpicturehouse.module.home.presenter.GoodDetailPresenterImpl;
//import com.xlkj.beautifulpicturehouse.module.home.view.adapter.HomeGoodChoiceDetailAdapter;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 首页精选详情
// */
//public class HomeGoodChoiceDetailActivity extends AppCompatActivity implements HomeContract.IGoodDetailView {
//    public static final String TAG = "-->HomeGoodChoiceDe";
//    private SwipeRefreshLayout mSwipeRefreshLayout;
//    private RecyclerView mRecyclerView;
//    private GridLayoutManager mLayoutManager;
//    private HomeGoodChoiceDetailAdapter mGoodChoiceDetailAdapter;
//    private GoodDetailPresenterImpl mGoodDetailPresenter;
//    private List<GoodDetailResBean.DataBean.InfoBean.ListBean> mList = new ArrayList<>();
//    //是否刷新 默认不刷新
//    private boolean isRefresh = false;
//    private SwipeBackLayout mSwipeBackLayout;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //根本没有导航栏的写法
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        setContentView(R.layout.activity_home_good_choice_detail);
//        mSwipeBackLayout = new SwipeBackLayout(this);
//        bindPresenter();
//        loadData();
//        initView();
//
//    }
//
//
//    @Override
//    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        mSwipeBackLayout.attachToActivity(this,SwipeBackLayout.EDGE_LEFT);
//        // 触摸边缘变为屏幕宽度的1/2
//        mSwipeBackLayout.setEdgeSize(getResources().getDisplayMetrics().widthPixels / 2);
//    }
//
//    private void loadData() {
//        GoodDetailReqBean bean = new GoodDetailReqBean();
//        bean.setCanal("original");
//        bean.setUserId("5275");
//        bean.setToken("0e7a8963241611da87cb90d14cbf2a84");
//        bean.setPlat("38");
//        bean.setService("VipPicture.VipChoiceContent");
//        bean.setSpecialid("34");
//        bean.setPage("1");
//        bean.setPageSize("24");
//        bean.setCheck("38be187bbc7a77912ae2d818dce308fc");
//        mGoodDetailPresenter.getDataGoodDetail(bean);
//
//    }
//
//    private void bindPresenter() {
//        mGoodDetailPresenter = new GoodDetailPresenterImpl(this);
//        mGoodDetailPresenter.attachGoodDetail(this);
//    }
//
//    private void initView() {
//        //返回键
//        findViewById(R.id.iv_back_ahgcd).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//        //分享
//        findViewById(R.id.iv_share_ahgcd).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                share();
//            }
//        });
//        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_ahgcd);
//        setRefresh();
//        mRecyclerView = (RecyclerView) findViewById(R.id.rv_ahgcd);
//        mLayoutManager = new GridLayoutManager(this, 3);
//        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        mGoodChoiceDetailAdapter = new HomeGoodChoiceDetailAdapter(this, mList);
//        mRecyclerView.setAdapter(mGoodChoiceDetailAdapter);
//    }
//
//    /**
//     * 分享
//     */
//    private void share() {
//        DisplayUtil.showShareDialog(this, "美女屋", "宅男们都震精啦!!!~", "http://vs.zsyj.com.cn/info/recharge/shape.php");
//    }
//
//    /**
//     * 下拉刷新
//     */
//    private void setRefresh() {
//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                try {
//                    isRefresh = true;
//                    loadData();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    Log.e(TAG, "setRefresh下拉刷新异常");
//                }
//            }
//        });
//    }
//
//    /**
//     * 失败
//     */
//    @Override
//    public void showErrorGoodDetail() {
//        Log.e(TAG, "失败了");
//        if (!isRefresh) {
//            //这里逻辑应该是在不是刷新的状态下才执行的
//        } else {
//            //刷新失败的逻辑
//            Toast.makeText(this, "刷新失败~~", Toast.LENGTH_SHORT).show();
//            //停止刷新
//            mSwipeRefreshLayout.setRefreshing(false);
//            //还原刷新的状态 设置成不是刷新
//            if (isRefresh) {
//                isRefresh = false;
//            }
//        }
//    }
//
//    @Override
//    public void setDataGoodDetail(GoodDetailResBean bean) {
//        Log.e(TAG, "数据=" + bean.toString());
//        mSwipeRefreshLayout.setRefreshing(false);
//        if (bean != null && bean.getData() != null) {
//            //如果是刷新 则先清空一下数据
//            if (isRefresh) {
//                mList.clear();
//            }
//            mList.addAll(bean.getData().getInfo().getList());
//            mGoodChoiceDetailAdapter.notifyDataSetChanged();
//            //还原刷新的状态 设置成不是刷新
//            if (isRefresh) {
//                Toast.makeText(this, "刷新成功", Toast.LENGTH_SHORT).show();
//                isRefresh = false;
//            }
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        unbindPresenter();
//    }
//
//    private void unbindPresenter() {
//        mGoodDetailPresenter.interruptHttp();
//        mGoodDetailPresenter.detachGoodDetail();
//    }
//}
