package com.xlkj.beautifulpicturehouse.module.home.view.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import com.xlkj.beautifulpicturehouse.common.util.PreferencesUtils;
import com.xlkj.beautifulpicturehouse.common.view.ui.activity.base.BaseStateActivity;
import com.xlkj.beautifulpicturehouse.common.view.ui.dialog.share.DisplayUtil;
import com.xlkj.beautifulpicturehouse.common.view.widget.SwipeBackLayout;
import com.xlkj.beautifulpicturehouse.module.home.bean.FollowReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.FollowResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.GoodDetailReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.GoodDetailResBean;
import com.xlkj.beautifulpicturehouse.module.home.contract.HomeContract;
import com.xlkj.beautifulpicturehouse.module.home.presenter.FollowPresenterImpl;
import com.xlkj.beautifulpicturehouse.module.home.presenter.GoodDetailPresenterImpl;
import com.xlkj.beautifulpicturehouse.module.home.view.adapter.HomeGoodChoiceDetailsAdapter;
import com.xlkj.beautifulpicturehouse.module.home.view.adapter.HomeGoodChoiceDetailsPriceAdapter;
import com.xlkj.beautifulpicturehouse.module.mine.view.ui.activity.LoginActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 精选详情页activity
 */
public class HomeGoodChoiceDetailsActivity extends BaseStateActivity implements HomeContract.IGoodDetailView,HomeContract.IFollowView {

    public static final String TAG = "-->HomeGoodChoiceD";
    public static final String YFOLLOW = "已关注";
    public static final String FOLLOW = "关注";
    //免费区列表
    private RecyclerView rvFree;
    //收费区列表
    private RecyclerView rvPrice;
    //头部图片
    private ImageView ivHead;
    //标题
    private TextView tvTitle;
    //二级标题
    private TextView tvTitle2;
    //精选详情p
    private GoodDetailPresenterImpl mGoodDetailPresenter;
    //上一个页面的itemId
    private String mImageId;
    //封面url
    private String mImageUrl;
    private String mFollowId;
    //免费列表数据
    private ArrayList<ArrayList<GoodDetailResBean.DataBean.SelectedImgListBean>> mFreeList = new ArrayList<>();
    //付费列表数据
    private ArrayList<ArrayList<GoodDetailResBean.DataBean.ImgListBean>> mPayList = new ArrayList<>();
    //免费adapter
    private HomeGoodChoiceDetailsAdapter freeAdapter;
    //付费adapter
    private HomeGoodChoiceDetailsPriceAdapter payAdapter;
    //加载mLoadService的布局
    private LinearLayout mLinearLayout;
    //mLoadService
    private LoadService mLoadService;
    //点击关注
    private RelativeLayout rlFollow;
    //关注显示文本
    private TextView tvFollow;
    //关注或取消关注presenter
    private FollowPresenterImpl mFollowPresenter;
    //vip专享区文本
    private TextView tvVipTxt;
    //本地关注的状态-默认不是关注状态
    private boolean isFollowState = false;
    //右滑返回
    private SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //根本没有导航栏的写法
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.activity_home_good_choice_details);
        mSwipeBackLayout = new SwipeBackLayout(this);
        bindPresenter();
        getDataFromUp();
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

    private void initLoadSir() {
        try {
            mLoadService = LoadSir.getDefault().register(mLinearLayout, new Callback.OnReloadListener() {
                @Override
                public void onReload(View v) {
                    mLoadService.showCallback(LoadingCallback.class);
                    loadData();
                }
            }).setCallBack(EmptyCallback.class, new Transport() {
                @Override
                public void order(Context context, View view) {
                   //Toast.makeText(VideoDetailActivity.this, "没有数据啊亲", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "初始化load异常initLoadSir");
        }
        //发送正在加载数据的消息
        PostUtil.postCallbackDelayed(mLoadService, LoadingCallback.class);
    }

    private void getDataFromUp() {
        mImageId = getIntent().getStringExtra("imageId");
        mImageUrl = getIntent().getStringExtra("imageUrl");
        mFollowId = getIntent().getStringExtra("followId");
    }

    /**
     * 请求数据
     */
    private void loadData() {
        GoodDetailReqBean bean = new GoodDetailReqBean();
        bean.setAction("selectedList");
        bean.setImageId(mImageId);
        String uid = PreferencesUtils.getString(this, "uid");
        if (uid != null && !uid.equals("") && !uid.isEmpty()) {
            bean.setUserId(uid);
        } else {
            bean.setUserId("");
        }
        bean.setFollowId(mFollowId);
        mGoodDetailPresenter.getDataGoodDetail(bean);
    }

    private void bindPresenter() {
        //关注
        mFollowPresenter = new FollowPresenterImpl();
        mFollowPresenter.attachFollow(this);
        //详情
        mGoodDetailPresenter = new GoodDetailPresenterImpl();
        mGoodDetailPresenter.attachGoodDetail(this);
    }

    private void unbindPresenter() {
        //关注
        if (mFollowPresenter != null) {
            mFollowPresenter.detachFollow();
            mFollowPresenter.interruptHttp();
        }
        //详情
        if (mGoodDetailPresenter != null) {
            mGoodDetailPresenter.detachGoodDetail();
            mGoodDetailPresenter.interruptHttp();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindPresenter();
    }

    private void initView() {
        //vip专享区文本
        tvVipTxt = (TextView) findViewById(R.id.tv_videos_hvfl);
        //点击关注
        rlFollow = (RelativeLayout) findViewById(R.id.rl_follow_hhgdl);
        rlFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                follow();
            }
        });
        //关注显示文本
        tvFollow = (TextView) findViewById(R.id.tv_is_follow_hhgdl);
        //加载loadSir的布局
        mLinearLayout = (LinearLayout) findViewById(R.id.ll_load_sir_ahgcd);
        //标题
        tvTitle = (TextView) findViewById(R.id.tv_title_hhgdl);
        //二级标题
        tvTitle2 = (TextView) findViewById(R.id.tv_lite_title_hhgdl);
        //头部图片
        ivHead = (ImageView) findViewById(R.id.iv_hhgdl);
        if (mImageUrl != null) {
            Glide.with(this).load(mImageUrl).into(ivHead);
        }
        //分享键
        findViewById(R.id.iv_share_ahgcd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisplayUtil.showShareDialog(HomeGoodChoiceDetailsActivity.this, "美女屋", "宅男们都震精啦!!!~", Constant.SHARE_URL);
            }
        });
        //返回键
        findViewById(R.id.iv_back_ahgcd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //免费列表
        rvFree = (RecyclerView) findViewById(R.id.rv_free_ahgcd);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvFree.setLayoutManager(mLayoutManager);
        freeAdapter = new HomeGoodChoiceDetailsAdapter(this, mFreeList);
        rvFree.setAdapter(freeAdapter);
        //禁止列表竖直滑动
        rvFree.setNestedScrollingEnabled(false);

        //收费列表
        rvPrice = (RecyclerView) findViewById(R.id.rv_price_ahgcd);
        GridLayoutManager mLayoutManager2 = new GridLayoutManager(this, 3);
        mLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        rvPrice.setLayoutManager(mLayoutManager2);
        payAdapter = new HomeGoodChoiceDetailsPriceAdapter(this, mPayList);
        rvPrice.setAdapter(payAdapter);
        //禁止列表竖直滑动
        rvPrice.setNestedScrollingEnabled(false);
    }

    /**
     * 到登录页
     */
    private void jump2login() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    /**
     * 关注或者取消关注
     */
    private void follow() {
        //followId	string	被关注人Id
        //userId	string	用户Id
        //isFollow	int	0取消 1关注
        //type	int	关注类型0人 1标签
        FollowReqBean bean = new FollowReqBean();
        String followId;
        int type = 1;
        //获取用户id
        String uid = PreferencesUtils.getString(this, "uid");
        if (uid != null && !uid.isEmpty() && !uid.equals("")) {
            bean.setUserId(uid);
        } else {
            /**到登录*/
            jump2login();
            //如果uid为空不需要执行下面的逻辑
            return;
        }
        if (!isFollowState) {
            //点击就关注
            int isFollow = 1;
            bean.setIsFollow(isFollow + "");
        } else {
            //点击就取消关注
            int isFollow = 0;
            bean.setIsFollow(isFollow + "");
        }
        if (mImageId != null && !mImageId.isEmpty() && !mImageId.equals("")) {
            followId = mImageId;
        } else {
            followId = "";
        }
        bean.setAction("follow");
        bean.setType(type+"");
        bean.setFollowId(mFollowId);
        mFollowPresenter.getDataFollow(bean);
    }

    /**
     * 没被关注过的状态
     */
    private void notFollowState() {
        isFollowState = false;
        tvFollow.setText(FOLLOW);
        rlFollow.setBackgroundResource(R.drawable.follow_checked_shape);
    }

    /**
     * 被关注过的状态
     */
    private void followState() {
        isFollowState = true;
        tvFollow.setText(YFOLLOW);
        rlFollow.setBackgroundResource(R.drawable.follow_unchecked_shape);
    }

    /**
     * 精选详情数据回调失败
     */
    @Override
    public void showErrorGoodDetail() {
        Log.e(TAG,"数据失败");
        errorDataState();

    }

    /**
     * 发送请求失败的消息
     */
    private void errorDataState() {
        //发送请求失败的消息
        PostUtil.postCallbackDelayed(mLoadService, ErrorCallback.class);
    }
    /**
     * 发送请求成功的消息
     */
    private void successDataState() {
        //发送请求成功的消息
        PostUtil.postSuccessDelayed(mLoadService);
    }

    /**
     * 精选详情数据回调成功
     * @param bean
     */
    @Override
    public void setDataGoodDetail(GoodDetailResBean bean) {
        Log.e(TAG,"精选数据："+bean.toString());
        if (bean != null) {
            successDataState();
            GoodDetailResBean.DataBean data = bean.getData();
            int mIsFollow = data.getIsFollower();
            if (mIsFollow == 1) {
                //已关注的状态
                followState();
            } else {
                //没被关注的状态
                notFollowState();
            }
            if (data != null) {
                String tagName = data.getTagName();
                if (tagName != null && !tagName.equals("null")) {
                    tvTitle.setText(tagName);
                } else {
                    tvTitle.setText("美女屋");
                }
                String typeName = data.getTypeName();
                if (typeName != null) {
                    tvTitle2.setText(typeName);
                } else {
                    tvTitle2.setText("宅男们都震精啦~");
                }
                //免费列表
                ArrayList<ArrayList<GoodDetailResBean.DataBean.SelectedImgListBean>> freeList = data.getSelectedImgList();
                if (freeList != null && freeList.size() != 0) {
                    mFreeList.addAll(freeList);
                    freeAdapter.notifyDataSetChanged();
                } else {
                    Log.e(TAG,"免费列表为空");
                }
                //付费列表
                ArrayList<ArrayList<GoodDetailResBean.DataBean.ImgListBean>> payList = data.getImgList();
                if (payList != null && payList.size() != 0) {
                    mPayList.addAll(payList);
                    payAdapter.notifyDataSetChanged();
                } else {
                    Log.e(TAG,"付费列表为空");
                    //vip专享区文本隐藏
                    tvVipTxt.setVisibility(View.GONE);
                }

            } else {
                Log.e(TAG,"data数据为空");
                errorDataState();
            }
        } else {
            Log.e(TAG,"数据为空");
            errorDataState();
        }
    }

    /**
     * 关注或取消关注失败回调
     */
    @Override
    public void showErrorFollow() {
        Log.e(TAG,"关注失败");
//        notFollowState();
        Toast.makeText(this, "关注失败", Toast.LENGTH_SHORT).show();
    }

    /**
     * 关注或取消关注成功回调
     * @param bean
     */
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
    }
}
