package com.xlkj.beautifulpicturehouse.module.home.view.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapEncoder;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.umeng.analytics.MobclickAgent;
import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.common.constant.BeautyContent;
import com.xlkj.beautifulpicturehouse.common.constant.Constant;
import com.xlkj.beautifulpicturehouse.common.manager.CommentSubmitManager;
import com.xlkj.beautifulpicturehouse.common.manager.GlobalThreadPool;
import com.xlkj.beautifulpicturehouse.common.manager.glideprogress.ProgressInterceptor;
import com.xlkj.beautifulpicturehouse.common.manager.glideprogress.ProgressListener;
import com.xlkj.beautifulpicturehouse.common.rxbus.RxBus;
import com.xlkj.beautifulpicturehouse.common.util.PreferencesUtils;
import com.xlkj.beautifulpicturehouse.common.util.SaveImageUtils;
import com.xlkj.beautifulpicturehouse.common.view.ui.dialog.VipPayToastDialog;
import com.xlkj.beautifulpicturehouse.common.view.ui.dialog.share.BottomDialog;
import com.xlkj.beautifulpicturehouse.common.view.ui.dialog.share.DisplayUtil;
import com.xlkj.beautifulpicturehouse.common.view.widget.ZoomImageView;
import com.xlkj.beautifulpicturehouse.module.home.bean.AboutPicReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.AboutPicResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.AboutTagReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.AboutTagResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.CollectionReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.CollectionResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.CommentListReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.CommentListResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.CommentSubmitReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.CommentSubmitResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomeHotNewResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomeHotPicResMgqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.Hot2RefreshBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.RxPostCommentBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.RxPostFinishPicDetailBean;
import com.xlkj.beautifulpicturehouse.module.home.contract.HomeContract;
import com.xlkj.beautifulpicturehouse.module.home.presenter.AboutPicPresenterImpl;
import com.xlkj.beautifulpicturehouse.module.home.presenter.AboutTagPresenterImpl;
import com.xlkj.beautifulpicturehouse.module.home.presenter.CollectionPresenterImpl;
import com.xlkj.beautifulpicturehouse.module.home.presenter.CommentListPresenterImpl;
import com.xlkj.beautifulpicturehouse.module.home.presenter.CommentSubmitPresenterImpl;
import com.xlkj.beautifulpicturehouse.module.home.view.adapter.AboutPicAdapter;
import com.xlkj.beautifulpicturehouse.module.home.view.adapter.AboutTagAdapter;
import com.xlkj.beautifulpicturehouse.module.home.view.adapter.CommentListAdapter;
import com.xlkj.beautifulpicturehouse.module.mine.view.ui.activity.LoginActivity;
import com.xlkj.beautifulpicturehouse.module.mine.view.ui.activity.VipActivity;
import com.youth.banner.transformer.DefaultTransformer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.functions.Action1;

/**
 * 图片详情页
 */
public class PicDetailActivity extends AppCompatActivity implements HomeContract.ICollectionView, HomeContract.IAboutPicView,HomeContract.IAboutTagView,HomeContract.ICommentSubmitView,HomeContract.ICommentListView {

    public static final String TAG = "-->>PicDetailActivity";
    private ViewPager mViewPager;
    //上一个页面传过来的图片数据
    private ArrayList<HomeHotNewResBean.DataBean.TypeListBean.ImagListBean> mData;
    //图片数据
    private ArrayList<ZoomImageView> mImageViews;
    //上一个页面传递过来的位置
    private int mPosition;
    //viewpager当前选中的位置
    private int mCurrentPosition = 0;
    //图片的总张数和当前选中的第几张
    private TextView tvPicCount;
    //图片标题
    private TextView tvPicTitle;
    //状态栏下面的标题栏
    private RelativeLayout rlTitleBar;
    //传过来的图片标题
    private String mPicTitle = "";
    //传过来的条目id
    private String mItemId = "";
    //收藏或者取消收藏presenter
    private CollectionPresenterImpl mCollectionPresenter;
    private TextView tvCollection;
    //本地收藏的状态-默认不是收藏状态
    private boolean isCollectState = false;
    //冒泡框
    private PopupWindow popupWindow;
    //点击弹出冒泡框
    private ImageView ivPP;
    //相关标签的水平列表
    private RecyclerView rv_pp_tag;
    //相关美图的水平列表
    private RecyclerView rv_pp_about_pic;
    //相关美图presenter
    private AboutPicPresenterImpl mAboutPicPresenter;
    //相关美图的列表数据
    private List<AboutPicResBean.DataBean.RelatedListBeanX> aboutPicList = new ArrayList<>();
    //相关美图adapter
    private AboutPicAdapter mAboutPicAdapter;
    //相关标签presenter
    private AboutTagPresenterImpl mAboutTagPresenter;
    //相关标签数据
    private List<AboutTagResBean.DataBean.TagBean> mAboutTags = new ArrayList<>();
    //相关标签adapter
    private AboutTagAdapter mAboutTagAdapter;
    //提交评论presenter
//    private CommentSubmitPresenterImpl mCommentSubmitPresenter;
    //底部显示发表评论的dialog
    private BottomDialog mBottomDialog;
    //加载对话框
    private KProgressHUD mKProgressHUD;
    //获取评论列表presenter
    private CommentListPresenterImpl mCommentListPresenter;
    //评论列表
    private RecyclerView rvCommentList;
    //评论列表数据
    private List<CommentListResBean.DataBean.CommentListBean> mListComment = new ArrayList<>();
    //评论列表adapter
    private CommentListAdapter mCommentListAdapter;
    //跟路径
    private RelativeLayout rlParentRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //根本没有导航栏的写法
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_pic_detail);
        getDataFromUp();
        bindPresenter();
        loadDataAboutPic();
        loadDataAboutTag();
        loadDataCommentList();
        initView();
        initImgs();
        setViewPager();
        getViewPagerCurrentPage();
        setHeadTitle(mPosition + 1);
        setPicTile();
        setCollectionState();
        initPopupWindow();
        initRxBus();
        initLoadingDialog();
    }

    /**
     * 获取评论列表数据
     */
    private void loadDataCommentList() {
        try {
            CommentListReqBean bean = new CommentListReqBean();
            bean.setAction("commentList");
            bean.setIspicture("0");
            bean.setTypeId(mItemId);
            bean.setPage("1");
            mCommentListPresenter.getDataCommentList(bean);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"loadDataCommentList异常");
        }
    }

    /**
     * 初始化等待加载ing对话框
     */
    private void initLoadingDialog() {
        try {
            mKProgressHUD = KProgressHUD.create(PicDetailActivity.this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel(" 提交中 ")
                    //.setDetailsLabel("Downloading data")
//                .setDetailsLabel("  更新检测中......  ")
                    .setCancellable(true)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"initLoadingDialog异常");
        }
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
     * 接收消息
     */
    private void initRxBus() {
        /**接收请求发表评论的消息*/
//        Subscription subscription = RxBus.getInstance().doSubscribe(RxPostCommentBean.class, new Action1<RxPostCommentBean>() {
//            @Override
//            public void call(RxPostCommentBean bean) {
//                boolean submitComment = bean.isSubmitComment();
//                if (submitComment) {
//                    mKProgressHUD.show();
//                    String content = bean.getContent();
//                    mBottomDialog = bean.getBottomDialog();
//                    loadCommentSubmitData(content);
//                } else {
//                    Log.e(TAG,"接收到的消息是不提交评论");
//                }
//            }
//        }, new Action1<Throwable>() {
//            @Override
//            public void call(Throwable throwable) {
//                Log.e(TAG,"Action1 是否接受数据："+throwable.getMessage());
//            }
//        });
//        RxBus.getInstance().addSubscription(this,subscription);

    }

    /**
     * 解绑RxBus
     */
    private void unbindRxBus() {
        RxBus.getInstance().unSubscribe(this);
    }

    /**
     * 请求发表评论的数据
     * @param content
     */
//    private void loadCommentSubmitData(String content) {
//        CommentSubmitReqBean bean = new CommentSubmitReqBean();
//        String uid = PreferencesUtils.getString(this, "uid");
//        String isPicture = "0";
//        String typeId = mItemId;
//        bean.setAction("comment");
//        bean.setContent(content);
//        bean.setIsPicture(isPicture);
//        bean.setTypeId(typeId);
//        bean.setUserId(uid);
//        mCommentSubmitPresenter.getDataCommentSubmit(bean);
//    }

    /**
     * 获取相关标签
     */
    private void loadDataAboutTag() {
        try {
            AboutTagReqBean bean = new AboutTagReqBean();
            bean.setAction("showtags");
            bean.setTypeId(mItemId);
            String uid = PreferencesUtils.getString(this, "uid");
            if (uid != null && !uid.equals("") && !uid.isEmpty()) {
                bean.setUserId(uid);
            } else {
                bean.setUserId("");
            }
            mAboutTagPresenter.getDataAboutTag(bean);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"loadDataAboutTag异常");
        }
    }

    /**
     * 获取相关美图
     */
    private void loadDataAboutPic() {
        try {
            AboutPicReqBean bean = new AboutPicReqBean();
            if (mData != null && mData.get(mPosition) != null) {
                String imageId = mData.get(mPosition).getImageId();
                bean.setImageId(imageId);
            }
            bean.setAction("relatedPictureList");
            String uid = PreferencesUtils.getString(this, "uid");
            if (uid != null && !uid.equals("") && !uid.isEmpty()) {
                bean.setUserId(uid);
            } else {
                bean.setUserId("");
            }
            mAboutPicPresenter.getDataAboutPic(bean);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"loadDataAboutPic异常");
        }
    }

    /**
     * 初始化冒泡框
     */
    private void initPopupWindow() {
        try {
            View popupView = getLayoutInflater().inflate(R.layout.layout_pp_about, null);
            // 初始化冒泡框的控件
            initPopupLayoutView(popupView);
            popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(false);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"initPopupWindow异常");
        }
    }

    /**
     * 冒泡框中的列表的点击事件回调
     */
    private void aboutPicAdapterClickEvent() {
        mAboutPicAdapter.setOnItemAboutClickListener(new AboutPicAdapter.OnItemAboutClickListener() {
            @Override
            public void onItemClick(View v, int position, List<AboutPicResBean.DataBean.RelatedListBeanX> mList) {
                try {
                    jump2detail(position,0,mList);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG,"自己跳转自己异常");
                }
            }
        });
    }

    /**
     * 跳转到详情：activity为singleTop时 自己跳自己 必先关闭自己
     */
    private void jump2detail(int position, int localPosition, List<AboutPicResBean.DataBean.RelatedListBeanX> mList) {
        finish();
        Intent intent = new Intent(PicDetailActivity.this, PicDetailActivity.class);
        ArrayList<AboutPicResBean.DataBean.RelatedListBeanX.RelatedListBean> relatedList = mList.get(position).getRelatedList();
        String id = mList.get(position).getRelatedList().get(0).getImageId();
        String name = mList.get(position).getRelatedList().get(0).getTypeName();
        ArrayList<HomeHotNewResBean.DataBean.TypeListBean.ImagListBean> imagList = new ArrayList<>();
        for (int i = 0; i < relatedList.size(); i++) {
            HomeHotNewResBean.DataBean.TypeListBean.ImagListBean bean = new HomeHotNewResBean.DataBean.TypeListBean.ImagListBean();
            String imageId = relatedList.get(i).getImageId();
            String imageUrl = relatedList.get(i).getImageUrl();
            bean.setImageId(imageId);
            bean.setImageUrl(imageUrl);
            imagList.add(bean);
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable("data",imagList);
        intent.putExtras(bundle);
        intent.putExtra("position",localPosition);
        intent.putExtra("picTitle",name);
        intent.putExtra("itemId",id);
        PicDetailActivity.this.startActivity(intent);
    }

    /**
     * 初始化冒泡框的控件
     * @param popupView
     */
    private void initPopupLayoutView(View popupView) {
        //相关标签的水平列表：这里的数据可以传过来
        rv_pp_tag = (RecyclerView) popupView.findViewById(R.id.rv_tag_lpa);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_pp_tag.setLayoutManager(linearLayoutManager2);
        mAboutTagAdapter = new AboutTagAdapter(this, mAboutTags);
        rv_pp_tag.setAdapter(mAboutTagAdapter);

        //相关美图的水平列表：这里的数据需要再访问服务器
        rv_pp_about_pic = (RecyclerView) popupView.findViewById(R.id.rv_about_pic_lpa);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_pp_about_pic.setLayoutManager(linearLayoutManager);
        mAboutPicAdapter = new AboutPicAdapter(this, aboutPicList);
        rv_pp_about_pic.setAdapter(mAboutPicAdapter);
        aboutPicAdapterClickEvent();

        //评论列表
        rvCommentList = (RecyclerView) popupView.findViewById(R.id.rv_comment_list_lpa);
        rvCommentList.setLayoutManager(new LinearLayoutManager(PicDetailActivity.this));
        mCommentListAdapter = new CommentListAdapter(this, mListComment);
        rvCommentList.setAdapter(mCommentListAdapter);
        rvCommentList.setNestedScrollingEnabled(false);
    }

    /**
     * 设置收藏显示的状态
     */
    private void setCollectionState() {
        try {
            //判断是否被收藏，0是否，1是
            int isCollect = mData.get(mCurrentPosition).getIsCollect();
            if (isCollect == 1) {
                //已经收藏
                isCollectState = true;
                //改变背景图片
                Drawable drawable = getResources().getDrawable(R.drawable.collect_p);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
                tvCollection.setCompoundDrawables(null, drawable, null, null);
            } else {
                //没有收藏
                isCollectState =  false;
                //改变背景图片
                Drawable drawable = getResources().getDrawable(R.drawable.collect_n);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
                tvCollection.setCompoundDrawables(null, drawable, null, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"setCollectionState异常");
        }
    }

    /**
     * 绑定presenter
     */
    private void bindPresenter() {
        //获取评论列表
        mCommentListPresenter = new CommentListPresenterImpl();
        mCommentListPresenter.attachCommentList(this);
        //提交评论
//        mCommentSubmitPresenter = new CommentSubmitPresenterImpl(this);
//        mCommentSubmitPresenter.attachCommentSubmit(this);
        //收藏
        mCollectionPresenter = new CollectionPresenterImpl();
        mCollectionPresenter.attachCollection(this);
        //实例化相关美图p
        mAboutPicPresenter = new AboutPicPresenterImpl();
        mAboutPicPresenter.attachAboutPic(this);
        //相关标签
        mAboutTagPresenter = new AboutTagPresenterImpl();
        mAboutTagPresenter.attachAboutTag(this);
    }

    /**
     * 解绑presenter
     */
    private void unbindPresenter() {
        //获取评论列表
        if (mCommentListPresenter != null) {
            mCommentListPresenter.detachCommentList();
            mCommentListPresenter.interruptHttp();
        }
        //提交评论
//        if (mCommentSubmitPresenter != null) {
//            mCommentSubmitPresenter.interruptHttp();
//            mCommentSubmitPresenter.detachCommentSubmit();
//        }
        //收藏
        if (mCollectionPresenter != null) {
            mCollectionPresenter.detachCollection();
            mCollectionPresenter.interruptHttp();
        }
        //解绑相关美图p
        if (mAboutPicPresenter != null) {
            mAboutPicPresenter.detachAboutPic();
            mAboutPicPresenter.interruptHttp();
        }
        //相关标签
        if (mAboutTagPresenter != null) {
            mAboutTagPresenter.detachAboutTag();
            mAboutTagPresenter.interruptHttp();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindPresenter();
        unbindRxBus();
    }


    /**
     * 设置标题
     */
    private void setPicTile() {
        try {
            tvPicTitle.setText(mPicTitle);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"setPicTile异常");
        }
    }

    /**
     * 设置标题中选中的position
     */
    public void setHeadTitle(int position) {
        try {
            tvPicCount.setText("" + "(" + position + "/" + mData.size() + ")");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "setHeadTitle设置标题异常");
        }
    }

    /**
     * 获取viewpager当前选中页的位置
     */
    private void getViewPagerCurrentPage() {
        try {
            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    //position就是当前选中的position
                    Log.e(TAG, "当前位置00：：" + position);
                    mCurrentPosition = position;
                    setHeadTitle(position + 1);
                    //设置收藏显示的状态
                    setCollectionState();
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"getViewPagerCurrentPage异常");
        }
    }


    /**
     * 设置viewpagr
     */
    private void setViewPager() {
        try {
            PagerAdapter adapter = new PagerAdapter() {
                @Override
                public int getCount() {
                    return mImageViews.size();
                }

                // 滑动切换的时候销毁当前的View
                @Override
                public void destroyItem(ViewGroup container, int position,
                                        Object object) {
                    container.removeView(mImageViews.get(position));
                }

                // 每次滑动的时候生成的View
                @Override
                public Object instantiateItem(ViewGroup container, int position) {
                    container.addView(mImageViews.get(position));
                    return mImageViews.get(position);
                }

                @Override
                public boolean isViewFromObject(View view, Object object) {
                    return view == object;
                }
            };
            mViewPager.setAdapter(adapter);
            mViewPager.setPageTransformer(false, new DefaultTransformer());
            jump2someone(adapter);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"setViewPager异常");
        }
    }

    /**
     * 设置ViewPager跳转到指定页面
     *
     * @param adapter
     */
    private void jump2someone(PagerAdapter adapter) {
        //先强制设定跳转到指定页面
        try {
            //参数mCurItem是系统自带的
            Field field = mViewPager.getClass().getField("mCurItem");
            field.setAccessible(true);
            field.setInt(mViewPager, mPosition);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //然后调用下面的函数刷新数据
        adapter.notifyDataSetChanged();
        //再调用setCurrentItem()函数设置一次
        mViewPager.setCurrentItem(mPosition);
    }

    /**
     * 初始化图片数据
     */
    private void initImgs() {
        try {
            mImageViews = new ArrayList<>();
            for (int i = 0; i < mData.size(); i++) {
                ZoomImageView imageView = new ZoomImageView(PicDetailActivity.this);
                Glide.with(this).load(mData.get(i).getImageUrl())/*.placeholder(R.drawable.morenqelyebl).crossFade()*/.into(imageView);
                mImageViews.add(imageView);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"initImgs异常");
        }
//        for (int i = 0; i < mData.size(); i++) {
//            final View view = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.progress_layout, rlParentRootView);
//            final TextView tvProgress = (TextView) view.findViewById(R.id.tv_p_pl);
//            final String url = mData.get(i).getImageUrl();
//            ZoomImageView imageView = new ZoomImageView(PicDetailActivity.this);
////            Glide.with(this).load(mData.get(i).getImageUrl())/*.placeholder(R.drawable.morenqelyebl).crossFade()*/.into(imageView);
//            ProgressInterceptor.addListener(url, new ProgressListener() {
//                @Override
//                public void onProgress(int progress) {
//                    Log.e(TAG,"进度="+progress);
//                    tvProgress.setText(""+progress);
//                    if (progress == 100) {
//                        view.setVisibility(View.GONE);
//                    }
//                }
//            });
//            final int finalI = i;
//            Glide.with(this).load(url).diskCacheStrategy(DiskCacheStrategy.NONE).into(new GlideDrawableImageViewTarget(imageView) {
//                @Override
//                public void onLoadStarted(Drawable placeholder) {
//                    super.onLoadStarted(placeholder);
////                progressDialog.show();
//                    view.setVisibility(View.VISIBLE);
//                }
//
//                @Override
//                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
//                    super.onResourceReady(resource, animation);
////                progressDialog.dismiss();
//                    view.setVisibility(View.GONE);
//                    ProgressInterceptor.removeListener(url);
//                }
//            });
//            mImageViews.add(imageView);
//        }
    }

    /**
     * 从上一个页面获取传过来的数据
     */
    private void getDataFromUp() {
        mData = (ArrayList<HomeHotNewResBean.DataBean.TypeListBean.ImagListBean>) getIntent().getSerializableExtra("data");
        mPosition = getIntent().getIntExtra("position", 0);
        mPicTitle = getIntent().getStringExtra("picTitle");
        mItemId = getIntent().getStringExtra("itemId");
    }

    private void initView() {
        //跟路径
        rlParentRootView = (RelativeLayout) findViewById(R.id.rl_parent_apd);
        //点击弹出冒泡框
        ivPP = (ImageView) findViewById(R.id.iv_pp_about_apd);
        ivPP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testShowAboutPicPopupWindow();
            }
        });
        //状态栏下面的标题栏
        rlTitleBar = (RelativeLayout) findViewById(R.id.rl_title_bpdtl);
        //点击下载
        ((TextView) findViewById(R.id.tv_download_bpdbl)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadPic();
            }
        });
        //点击收藏
        tvCollection = (TextView) findViewById(R.id.tv_collection_bpdbl);
        tvCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    collectOrCancel();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG,"点击收藏异常");
                }
            }
        });
        //点击分享
        ((TextView) findViewById(R.id.tv_share_bpdbl)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share();
            }
        });
        //点击评论
        ((TextView) findViewById(R.id.tv_comment_bpdbl)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comment();
            }
        });
        //点击返回
        ((TextView) findViewById(R.id.beauty_back_bpdtl)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //点击举报
        ((ImageView) findViewById(R.id.iv_report_bpdtl)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                report();
            }
        });
        tvPicCount = (TextView) findViewById(R.id.beauty_titles_bpdtl);
        tvPicTitle = (TextView) findViewById(R.id.beauty_title_bpdtl);
        mViewPager = (ViewPager) findViewById(R.id.vp_apd);
    }

    /**
     * 举报
     */
    private void report() {
        //获取用户id
        String uid = PreferencesUtils.getString(PicDetailActivity.this, "uid");
        if (uid != null && !uid.isEmpty() && !uid.equals("")) {
            Toast.makeText(PicDetailActivity.this, "举报成功", Toast.LENGTH_SHORT).show();
        } else {
            jump2login();
        }
    }

    /**
     * 评论
     */
    private void comment() {
        //获取用户id
        String uid = PreferencesUtils.getString(PicDetailActivity.this, "uid");
        if (uid != null && !uid.isEmpty() && !uid.equals("")) {
//            DisplayUtil.showComment(this);
            commentSubmit();
        } else {
            jump2login();
        }
    }

    /**
     * 提交评论
     */
    private void commentSubmit() {
//        String uid = PreferencesUtils.getString(this, "uid");
//        String isPicture = "0";
//        String typeId = mItemId;
//        bean.setAction("comment");
//        bean.setContent(content);
//        bean.setIsPicture(isPicture);
//        bean.setTypeId(typeId);
//        bean.setUserId(uid);
        CommentSubmitManager.getInstance().setCommentSubmit(this, mItemId, "0", new CommentSubmitManager.CommentSubmitCallBack() {
            @Override
            public void onFailed(String msg) {
                Toast.makeText(PicDetailActivity.this, "评论失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(CommentSubmitResBean bean) {
                if (bean != null) {
                    Toast.makeText(PicDetailActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                    mKProgressHUD.dismiss();
                    if (mBottomDialog != null) {
                        mBottomDialog.dismiss();
                    }
                    //2018/1/16 把成功的内容添加到评论列表-本地操作不需要访问服务器
                    CommentSubmitResBean.DataBean data = bean.getData();
                    if (data != null) {
                        String content = data.getContent();
                        String avatarUlr = data.getAvatarUlr();
                        long time = data.getTime();
                        String userName = data.getUserName();
                        int commentId = data.getCommentId();
                        CommentListResBean.DataBean.CommentListBean commentListBean = new CommentListResBean.DataBean.CommentListBean();
                        commentListBean.setAvatarUlr(avatarUlr);
                        commentListBean.setContent(content);
                        commentListBean.setTime(time);
                        commentListBean.setUserName(userName);
                        commentListBean.setCommentId(commentId+"");
                        mListComment.add(commentListBean);
                        mCommentListAdapter.notifyDataSetChanged();
                    }
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
     * 弹出测试冒泡框
     */
    private void testShowAboutPicPopupWindow() {
        //显示弹出冒泡框
        popupWindow.showAsDropDown(ivPP, 0, 0);
    }

    /**
     * 收藏或者取消收藏
     */
    private void collectOrCancel() {
        //获取用户id
        String uid = PreferencesUtils.getString(PicDetailActivity.this, "uid");
        if (uid != null && !uid.isEmpty() && !uid.equals("")) {
            //收藏或者取消收藏的操作
            String imageId = mData.get(mCurrentPosition).getImageId();
            CollectionReqBean bean = new CollectionReqBean();
            bean.setAction("collect");
            bean.setUserId(uid);
            bean.setTypeId(imageId);
            //获取收藏的状态看是否被收藏过
            int isCollect = mData.get(mCurrentPosition).getIsCollect();
            //isCollect	int	0取消 1收藏
            if (!isCollectState) {
                //没有被收藏过 点击收藏
                bean.setIsCollect("1");
            } else {
                //已经收藏过 点击取消收藏
                bean.setIsCollect("0");
            }
            //isPicture	int	0图片 1视频
            bean.setIsPicture("0");
            mCollectionPresenter.getDataCollection(bean);
        } else {
            //到登录页面
            jump2login();
        }
    }

    /**
     * 到登录页
     */
    private void jump2login() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    /**
     * 下载图片到相册的操作
     */
    private void downloadPic() {
        String uid = PreferencesUtils.getString(this, "uid");
        if (uid != null && !uid.equals("") && !uid.isEmpty()) {
            //showOpenVipDialog();
            // TODO: 2018/1/17 需要按后面需求处理
            asyncDown();
        } else {
            //到登录
            jump2login();
        }
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                executeDownload();
//            }
//        }).start();
    }

    /**
     * 显示开通vip的dialog
     */
    private void showOpenVipDialog() {
        final VipPayToastDialog vipPayToastDialog = new VipPayToastDialog(PicDetailActivity.this, "");
        vipPayToastDialog.show();
        vipPayToastDialog.setOnCancelListener(new VipPayToastDialog.OnCancelListener() {
            @Override
            public void cancelMethod() {
                vipPayToastDialog.dismiss();
            }
        });
        vipPayToastDialog.setOnUpdateListener(new VipPayToastDialog.OnUpdateListener() {
            @Override
            public void updateMethod() {
                vipPayToastDialog.dismiss();
                Intent intent = new Intent(PicDetailActivity.this, VipActivity.class);
                PicDetailActivity.this.startActivity(intent);
            }
        });
    }

    /**
     * 异步下载
     */
    private void asyncDown() {
        //下载操作
        try {
            GlobalThreadPool.getInstance().getGlobalExecutorService().execute(new Runnable() {
                @Override
                public void run() {
                    executeDownload();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "downloadPic下载图片异常");
        }
    }

    /**
     * 执行下载操作
     */
    private void executeDownload() {
        try {
            if (mData != null && mData.get(mCurrentPosition) != null) {
                final Bitmap mBitmap = Glide.with(PicDetailActivity.this)//上下文
                        .load(mData.get(mCurrentPosition).getImageUrl())//url
                        .asBitmap() //必须
//                    .centerCrop()
                        .into(720, 1280)
                        .get();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SaveImageUtils.saveImageToGallery(PicDetailActivity.this, mBitmap,mData.get(mCurrentPosition).getImageId());
                    }
                });
            } else {
                Log.e(TAG,"下载时data为空");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "保存图片异常");
        }

    }

    /**
     * 收藏或者取消收藏失败
     */
    @Override
    public void showErrorCollection() {
        Log.e(TAG, "showErrorCollection收藏或者取消收藏失败");
        //设置收藏的状态是false
        isCollectState = false;
        Toast.makeText(this, "收藏失败~", Toast.LENGTH_SHORT).show();
    }

    /**
     * 收藏或者取消收藏成功
     * @param bean
     */
    @Override
    public void setDataCollection(CollectionResBean bean) {
        Log.e(TAG, "setDataCollection收藏或者取消收藏成功：" + bean.toString());
        if (bean != null) {
            String state = bean.getState();
            /**服务器返回的状态只有state==1才是收藏成功 其他状态都是没有收藏成功*/
            if (state.equals("1")) {
                //设置收藏的状态是true
                isCollectState = true;
                //改变背景图片
                Drawable drawable = getResources().getDrawable(R.drawable.collect_p);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
                tvCollection.setCompoundDrawables(null, drawable, null, null);
            } else {
                //设置收藏的状态是false
                isCollectState = false;
                //还原背景图片
                Drawable drawable = getResources().getDrawable(R.drawable.collect_n);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
                tvCollection.setCompoundDrawables(null, drawable, null, null);
            }
            String msg = bean.getMsg();
            Toast.makeText(this, msg + "", Toast.LENGTH_SHORT).show();
        } else {
            Log.e(TAG, "setDataCollection数据为空");
        }
    }

    /**
     * 相关美图回调失败
     */
    @Override
    public void showErrorAboutPic() {
        Log.e(TAG, "showErrorAboutPic相关美图获取失败");
    }

    /**
     * 相关美图获取成功回调
     * @param bean
     */
    @Override
    public void setDataAboutPic(AboutPicResBean bean) {
        Log.e(TAG, "showErrorAboutPic相关美图获取成功=" + bean.getData().toString());
        if (bean != null && bean.getData() != null) {
            List<AboutPicResBean.DataBean.RelatedListBeanX> relatedList = bean.getData().getRelatedList();
            aboutPicList.addAll(relatedList);
            mAboutPicAdapter.notifyDataSetChanged();
        } else {
            Log.e(TAG, "setDataAboutPic相关美图为空");
        }
    }

    /**
     * 相关标签回调失败
     */
    @Override
    public void showErrorAboutTag() {
        Log.e(TAG,"相关标签回调失败");
    }

    /**
     * 相关标签回调成功
     * @param bean
     */
    @Override
    public void setDataAboutTag(AboutTagResBean bean) {
        Log.e(TAG,"相关标签回调成功："+bean.getData().getTag().get(0).getTagName());
        if (bean != null && bean.getData() != null && bean.getData().getTag()!= null && bean.getData().getTag().size() != 0) {
            List<AboutTagResBean.DataBean.TagBean> tags = bean.getData().getTag();
            mAboutTags.addAll(tags);
            mAboutTagAdapter.notifyDataSetChanged();
        } else {
            Log.e(TAG,"标签数据为空");
        }
    }

    /**
     * 发表评论回调失败
     */
    @Override
    public void showErrorCommentSubmit() {
        Toast.makeText(this, "评论失败", Toast.LENGTH_SHORT).show();
        mKProgressHUD.dismiss();
    }

    /**
     * 发表评论回调成功
     * @param bean
     */
    @Override
    public void setDataCommentSubmit(CommentSubmitResBean bean) {
        if (bean != null) {
            Toast.makeText(this, "评论成功", Toast.LENGTH_SHORT).show();
            mKProgressHUD.dismiss();
            if (mBottomDialog != null) {
                mBottomDialog.dismiss();
            }
            //2018/1/16 把成功的内容添加到评论列表-本地操作不需要访问服务器
            CommentSubmitResBean.DataBean data = bean.getData();
            if (data != null) {
                String content = data.getContent();
                String avatarUlr = data.getAvatarUlr();
                long time = data.getTime();
                String userName = data.getUserName();
                int commentId = data.getCommentId();
                CommentListResBean.DataBean.CommentListBean commentListBean = new CommentListResBean.DataBean.CommentListBean();
                commentListBean.setAvatarUlr(avatarUlr);
                commentListBean.setContent(content);
                commentListBean.setTime(time);
                commentListBean.setUserName(userName);
                commentListBean.setCommentId(commentId+"");
                mListComment.add(commentListBean);
                mCommentListAdapter.notifyDataSetChanged();
            }
        }

    }

    /**
     * 获取评论列表回调失败
     */
    @Override
    public void showErrorCommentList() {
        Log.e(TAG,"获取评论列表失败");
    }

    /**
     * 获取评论列表回调成功
     * @param bean
     */
    @Override
    public void setDataCommentList(CommentListResBean bean) {
        Log.e(TAG,"获取评论列表成功bean:"+bean.getData().toString());
        if (bean != null) {
            CommentListResBean.DataBean data = bean.getData();
            if (data != null) {
                List<CommentListResBean.DataBean.CommentListBean> commentList = data.getCommentList();
                if (commentList != null && commentList.size() != 0) {
                    mListComment.addAll(commentList);
                    mCommentListAdapter.notifyDataSetChanged();
                } else {
                    Log.e(TAG,"setDataCommentList评论列表为空");
                }
            } else {
                Log.e(TAG,"setDataCommentList data为空");
            }
        } else {
            Log.e(TAG,"setDataCommentList bean为空");
        }
    }
}
