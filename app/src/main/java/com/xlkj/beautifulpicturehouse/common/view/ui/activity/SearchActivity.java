package com.xlkj.beautifulpicturehouse.common.view.ui.activity;

import android.app.SearchableInfo;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.umeng.analytics.MobclickAgent;
import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.common.util.PreferencesUtils;
import com.xlkj.beautifulpicturehouse.module.home.bean.DefSearchResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.SearchReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.SearchResBean;
import com.xlkj.beautifulpicturehouse.module.home.contract.HomeContract;
import com.xlkj.beautifulpicturehouse.module.home.presenter.DefSearchPresenterImpl;
import com.xlkj.beautifulpicturehouse.module.home.presenter.SearchPresenterImpl;
import com.xlkj.beautifulpicturehouse.module.home.view.adapter.SearchAdapter;
import com.xlkj.beautifulpicturehouse.module.home.view.adapter.DefSearchAdapter;
import com.xlkj.beautifulpicturehouse.module.home.view.adapter.SearchVideoAdapter;
import com.xlkj.beautifulpicturehouse.module.home.view.ui.activity.HomeTypeActivity;
import com.xlkj.beautifulpicturehouse.module.video.view.ui.activity.VideoTypeActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索activity
 */
public class SearchActivity extends AppCompatActivity implements HomeContract.ISearchView,HomeContract.IDefSearchView {

    public static final String TAG = "-->SearchActivity";
    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
//    private HomeMoreTypeAdapter mAdapter;
    private DefSearchAdapter mAdapter;
    //热门标签列表的数据
    private List<DefSearchResBean.DataBean> mList = new ArrayList<>();
    private LinearLayout mRelativeLayout;
    private EditText mEditText;
    private TextView tvSearch;
    private LinearLayout mRepalceLayout;
    private RecyclerView mRecyclerViewSearch;
    //水平的视频列表
    private RecyclerView mRecyclerViewSearchVideo;
    private LinearLayoutManager mLinearLayoutManager;
    private SearchPresenterImpl mSearchPresenter;
    //搜索到的图片数组数据
    private List<SearchResBean.DataBean.ImageListBean> mPics = new ArrayList<>();
    //搜索到的视频数组数据
    private List<SearchResBean.DataBean.VideoListBean> mVideos = new ArrayList<>();
    private SearchAdapter mSearchAdapter;
    //默认搜索列表p
    private DefSearchPresenterImpl mDefSearchPresenter;
    private int page = 1;
    //正在加载的对话框
    private KProgressHUD mKProgressHUD;
    //更多按钮
    private TextView tvMore;
    private String mS;
    private View rlSearchEmpty;
    //水平视频列表的manager
    private LinearLayoutManager mLinearLayoutManager1;
    //视频列表adapter
    private SearchVideoAdapter mSearchVideoAdapter;
    //点击视频更多按钮
    private TextView tvMoreVideo;
    //视频横条布局
    private RelativeLayout rlVideoLayout;
    //图片横条布局
    private RelativeLayout rlPicLayout;
    //标记图片为空
    private boolean isPicEmpty;
    //标记视频为空
    private boolean isVideoEmpty;
    //清除搜索内容的按钮
    private ImageView ivClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        bindPresenter();
        initView();
        initLoadingDialog();
        initEvent();
        //获取默认搜索列表
        initTestData();
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
     * 初始化加载对话框
     */
    private void initLoadingDialog() {
        mKProgressHUD = KProgressHUD.create(SearchActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("请稍等")
                //.setDetailsLabel("Downloading data")
                .setDetailsLabel("  数据搜索中......  ")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
    }

    /**
     * 绑定presenter
     */
    private void bindPresenter() {
        //服务器返回的搜索列表
        mDefSearchPresenter = new DefSearchPresenterImpl(this);
        mDefSearchPresenter.attachDefSearch(this);
        //点击了搜索
        mSearchPresenter = new SearchPresenterImpl();
        mSearchPresenter.attachSearch(this);
    }

    private void initEvent() {
        //点击搜索
        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mS = mEditText.getText().toString();
                if (!mS.isEmpty()) {
                    //显示正在加载的对话框
                    mKProgressHUD.show();
                    loadSearchData(mS);
                } else {
                    Toast.makeText(SearchActivity.this, "请输入关键字", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
        //点击清空搜索内容
        ivClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditText.setText("");
            }
        });

    }

    /**
     * 加载搜索数据
     * @param s
     */
    private void loadSearchData(String s) {
        SearchReqBean bean = new SearchReqBean();
        String uid = PreferencesUtils.getString(this, "uid");
        if (uid != null && !uid.isEmpty() && !uid.equals("")) {
            bean.setUid(uid);
        } else {
            bean.setUid("");
        }
        //0 点击搜索 1 其他搜索
        bean.setType("0");
        bean.setPage(page+"");
        bean.setAction("search");
        bean.setSearchKey(s);
        mSearchPresenter.getDataSearch(bean);
    }

    private void initTestData() {
        String action = "searchList";
        mDefSearchPresenter.getDataDefSearch(action);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        //清除搜索内容的按钮
        ivClear = (ImageView) findViewById(R.id.iv_clear_bstl);
        //视频横条布局
        rlVideoLayout = (RelativeLayout) findViewById(R.id.rl_video_layout_as);
        //图片横条布局
        rlPicLayout = (RelativeLayout) findViewById(R.id.rl_pic_layout_as);
        //水平的视频列表
        mRecyclerViewSearchVideo = (RecyclerView)findViewById(R.id.rv_search_video_al);
        mLinearLayoutManager1 = new LinearLayoutManager(SearchActivity.this);
        mLinearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerViewSearchVideo.setLayoutManager(mLinearLayoutManager1);

        //水平的图片列表
        mRecyclerViewSearch = (RecyclerView)findViewById(R.id.rv_search_al);
        mLinearLayoutManager = new LinearLayoutManager(SearchActivity.this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerViewSearch.setLayoutManager(mLinearLayoutManager);

        //所搜为空的布局
        rlSearchEmpty = findViewById(R.id.rl_search_empty_as);
        //点击更多视频按钮
        tvMoreVideo = (TextView) findViewById(R.id.tv_more_video_as);
        tvMoreVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, VideoTypeActivity.class);
                //如果是标签页的跳转 这里需要传标签的id
                intent.putExtra("typeName",mS);
                //0 点击搜索中的更多传的是关键字  1 其他搜索（视频标签到列表）传的是id
                intent.putExtra("byId","0");
                //标题
                intent.putExtra("title",mS);
                SearchActivity.this.startActivity(intent);
            }
        });
        //点击更多按钮
        tvMore = (TextView) findViewById(R.id.tv_more_as);
        tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, HomeTypeActivity.class);
                //clickIdSearch: 0 是点击搜索  1 不是
                intent.putExtra("clickIdSearch","0");
                //isEnterFromFollowTag是否是从我关注的标签页面进入列表页的 0：是  1：不是
                intent.putExtra("isEnterFromFollowTag","1");
                intent.putExtra("searchId",mS);
                intent.putExtra("typeName",mS);
                SearchActivity.this.startActivity(intent);
            }
        });
        //替换后的布局
        mRepalceLayout = (LinearLayout) findViewById(R.id.ll_search_content_sa);
        //将会被动态替换的布局
        mRelativeLayout = (LinearLayout) findViewById(R.id.rl_content_replace_as);
        //返回键
        findViewById(R.id.beauty_back_bstl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //文本编辑控件
        mEditText = (EditText) findViewById(R.id.et_beauty_title);
        //点击搜索按钮
        tvSearch = (TextView) findViewById(R.id.tv_search_bstl);
        //热门标签列表
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_as);
        mLayoutManager = new GridLayoutManager(this, 4);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new DefSearchAdapter(this, mList);
        mRecyclerView.setAdapter(mAdapter);
        //热门标签点击事件
        mAdapter.setOnDefItemClickListener(new DefSearchAdapter.OnDefItemClickListener() {
            @Override
            public void onDefClick(View v, String id, String name) {
                Intent intent = new Intent(SearchActivity.this, HomeTypeActivity.class);
                //clickIdSearch: 0 是（需要隐藏关注）  1 不是（不需要隐藏关注）
                intent.putExtra("clickIdSearch","1");
                //isEnterFromFollowTag是否是从我关注的标签页面进入列表页的 0：是（需要隐藏关注）  1：不是（不需要隐藏关注）
                intent.putExtra("isEnterFromFollowTag","1");
                intent.putExtra("searchId",id);
                intent.putExtra("typeName",name);
                SearchActivity.this.startActivity(intent);
            }

        });
    }

    /**
     * 搜索失败回调
     */
    @Override
    public void showErrorSearch() {
        Log.e(TAG,"数据失败啦啦啦");
        mKProgressHUD.dismiss();
        rlSearchEmpty.setVisibility(View.VISIBLE);
    }

    /**
     * 搜索成功回调
     * @param bean
     */
    @Override
    public void setDataSearch(SearchResBean bean) {
        mKProgressHUD.dismiss();
        mRelativeLayout.setVisibility(View.GONE);
        mRepalceLayout.setVisibility(View.VISIBLE);
        rlSearchEmpty.setVisibility(View.GONE);
        if (bean != null && bean.getData() != null) {
            List<SearchResBean.DataBean.ImageListBean> imageList = bean.getData().getImageList();
            if (imageList != null && imageList.size() != 0) {
                //有图片数据
                mPics.clear();
                mPics.addAll(bean.getData().getImageList());
                mSearchAdapter = new SearchAdapter(SearchActivity.this, mPics);
                mRecyclerViewSearch.setAdapter(mSearchAdapter);
                rlPicLayout.setVisibility(View.VISIBLE);
                isPicEmpty = false;
            } else {
                //没有图片数据
                Log.e(TAG,"没有搜索到图片数据");
                rlPicLayout.setVisibility(View.GONE);
                isPicEmpty = true;
            }
            List<SearchResBean.DataBean.VideoListBean> videoList = bean.getData().getVideoList();
            if (videoList != null && videoList.size() != 0) {
                mVideos.clear();
                mVideos.addAll(videoList);
                mSearchVideoAdapter = new SearchVideoAdapter(SearchActivity.this, mVideos);
                mRecyclerViewSearchVideo.setAdapter(mSearchVideoAdapter);
                rlVideoLayout.setVisibility(View.VISIBLE);
                isVideoEmpty = false;
            } else {
                //没有搜索到视频数据
                Log.e(TAG,"没有搜索到视频数据");
                rlVideoLayout.setVisibility(View.GONE);
                isVideoEmpty = true;
            }
            //如果data 不为空 图片和视频同时为空
            if (isPicEmpty && isVideoEmpty) {
                Log.e(TAG,"没有搜索到数据");
                mRepalceLayout.setVisibility(View.GONE);
                rlSearchEmpty.setVisibility(View.VISIBLE);
                Toast.makeText(this, "没有搜索到相关数据~", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            Log.e(TAG,"没有搜索到数据");
            mRepalceLayout.setVisibility(View.GONE);
            rlSearchEmpty.setVisibility(View.VISIBLE);
            Toast.makeText(this, "没有搜索到相关数据~", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindPresenter();
    }

    /**
     * 解绑presenter
     */
    private void unbindPresenter() {
        //默认搜索列表的presenter
        mDefSearchPresenter.detachDefSearch();
        mDefSearchPresenter.interruptHttp();
        mSearchPresenter.detachSearch();
        mSearchPresenter.interruptHttp();
    }

    /**
     * 返回键监听
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            try {
                if (mRepalceLayout.getVisibility() == View.VISIBLE) {
                    mRepalceLayout.setVisibility(View.GONE);
                    mRelativeLayout.setVisibility(View.VISIBLE);
                    return false;
                } else if (rlSearchEmpty.getVisibility() == View.VISIBLE) {
                    rlSearchEmpty.setVisibility(View.GONE);
                    mRelativeLayout.setVisibility(View.VISIBLE);
                    return false;
                } else {
                    finish();
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG,"点击返回键异常");
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 获取默认搜索列表失败：要做加载失败视图的显示
     */
    @Override
    public void showErrorDefSearch() {
        // TODO: 2018/1/2 失败
        Log.e(TAG,"showErrorDefSearch获取默认搜索列表失败");
    }

    /**
     * 获取默认搜索列表成功
     * @param bean
     */
    @Override
    public void setDataDefSearch(DefSearchResBean bean) {
        if (bean != null && bean.getData() != null) {
            Log.e(TAG,"搜索到的数据："+bean.getData().toString());
            List<DefSearchResBean.DataBean> data = bean.getData();
            mList.addAll(data);
            mAdapter.notifyDataSetChanged();
        } else {
            //数据为空 需要做处理
            Log.e(TAG,"setDataDefSearch获取默认搜索列表数据为空");
        }
    }
}
