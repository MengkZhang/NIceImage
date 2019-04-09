package com.xlkj.beautifulpicturehouse.module.video.view.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.umeng.analytics.MobclickAgent;
import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.common.constant.BeautyContent;
import com.xlkj.beautifulpicturehouse.common.manager.CollectionManager;
import com.xlkj.beautifulpicturehouse.common.manager.CommentSubmitManager;
import com.xlkj.beautifulpicturehouse.common.manager.FollowManager;
import com.xlkj.beautifulpicturehouse.common.util.NetUtil;
import com.xlkj.beautifulpicturehouse.common.util.PreferencesUtils;
import com.xlkj.beautifulpicturehouse.common.view.ui.activity.base.BaseStateActivity;
import com.xlkj.beautifulpicturehouse.common.view.ui.activity.base.BaseSwipeBackActivity;
import com.xlkj.beautifulpicturehouse.common.view.ui.dialog.share.DisplayUtil;
import com.xlkj.beautifulpicturehouse.common.view.widget.MyJZVideoPlayerStandard;
import com.xlkj.beautifulpicturehouse.module.home.bean.CollectionResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.CommentListReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.CommentListResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.CommentSubmitResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.FollowResBean;
import com.xlkj.beautifulpicturehouse.module.home.contract.HomeContract;
import com.xlkj.beautifulpicturehouse.module.home.presenter.CommentListPresenterImpl;
import com.xlkj.beautifulpicturehouse.module.mine.view.ui.activity.BoMasterDetailActivity;
import com.xlkj.beautifulpicturehouse.module.mine.view.ui.activity.LoginActivity;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoDetailReqBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoDetailResBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoListResBean;
import com.xlkj.beautifulpicturehouse.module.video.contract.VideoContract;
import com.xlkj.beautifulpicturehouse.module.video.presenter.VideoDetailPresenterImpl;
import com.xlkj.beautifulpicturehouse.module.video.view.adapter.AboutVideoCommentListAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 新的视频详情页 -仿今日头条视频详情页
 */
public class VideoDetailActivity extends BaseStateActivity implements VideoContract.IVideoDetailView ,HomeContract.ICommentListView{

    public static final String TAG = "-->VideoDetailActivity";
    private String mImageUrl;
    private String mVideoUrl;
    private String mTypeName;
    //自定义的节操控件
    private MyJZVideoPlayerStandard mJzVideoPlayerStandard;
    //上一个页面传过来的数据
    private VideoListResBean.DataBean.TypeListBean mData;
    ////相关视频和评论列表相关
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    //请求视频详情页的p
    private VideoDetailPresenterImpl mVideoDetailPresenter;
    //请求视频详情页需要传的页数
    private int page = 1;
    //评论列表数据
    private List<CommentListResBean.DataBean.CommentListBean> mListComment = new ArrayList<>();
    //相关视频和列表的adapter
    private AboutVideoCommentListAdapter mAdapter;
    //获取评论列表presenter
    private CommentListPresenterImpl mCommentListPresenter;
    //头像
    private CircleImageView ivHead;
    //名字
    private TextView tvName;
    //粉丝数
    private TextView tvFens;
    //评论列表分页加载要传的参数page
    private int commentPage = 1;
    //服务器返回的评论总页数
    private int mTotapage;
    //头像和关注的区域
    private RelativeLayout rlHeadFollowLayout;
    //关注波主控件
    RelativeLayout rlFollowBoMaster;
    //关注或者已经关注的文本
    TextView tvFollowTxt;
    //本地关注的状态-默认是不关注的状态
    private boolean isFollowState = false;
    //收藏控件
    private RelativeLayout rlCollection;
    //收藏图标
    private ImageView ivColletion;
    //本地收藏的状态-默认不是收藏状态
    private boolean isCollectState = false;
    //提交评论控件
    private RelativeLayout rlSubmitComment;
    //提交评论时加载对话框
    private KProgressHUD mKProgressHUD;
    //举报控件
    private RelativeLayout rlReport;
    //下载控件
    private RelativeLayout rlDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_video);
        bindPresenter();
        getDataFromUp();
        initView();
        loadData();
        loadDataCommentList();
        initLoadingDialog();
    }

    /**
     * 绑定presenter
     */
    private void bindPresenter() {
        //视频详情
        mVideoDetailPresenter = new VideoDetailPresenterImpl();
        mVideoDetailPresenter.attachVideoDetail(this);
        //评论列表
        mCommentListPresenter = new CommentListPresenterImpl();
        mCommentListPresenter.attachCommentList(this);
    }
    /**
     * 解绑presenter
     */
    private void unbindPresenter() {
        //视频详情
        if (mVideoDetailPresenter != null) {
            mVideoDetailPresenter.detachVideoDetail();
            mVideoDetailPresenter.interruptHotVideoList();
        }
        //获取评论列表
        if (mCommentListPresenter != null) {
            mCommentListPresenter.detachCommentList();
            mCommentListPresenter.interruptHttp();
        }
    }

    /**
     * 从上个页面传过来的数据
     */
    private void getDataFromUp() {
        mData = (VideoListResBean.DataBean.TypeListBean) getIntent().getSerializableExtra("dataBean");
    }

    /**
     * 初始化控件
     */
    private void initView() {
        //ijkPlayer相关
        mJzVideoPlayerStandard = (MyJZVideoPlayerStandard) findViewById(R.id.videoplayer);
        mJzVideoPlayerStandard.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        jcPlayLogic();
        //相关视频和评论列表相关
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_about_video_comment_list_atv);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new AboutVideoCommentListAdapter(this, mListComment);
        mRecyclerView.setAdapter(mAdapter);
        scrollEvent();
        //头像
        ivHead = (CircleImageView) findViewById(R.id.civ_head_ulhl);
        tvName = (TextView) findViewById(R.id.tv_name_ulhl);
        tvFens = (TextView) findViewById(R.id.tv_fens_ulhl);
        //头像区域
        rlHeadFollowLayout = (RelativeLayout) findViewById(R.id.rl_user_follow_atv);
        //关注控件
        rlFollowBoMaster = (RelativeLayout) findViewById(R.id.rl_follow_ulhl);
        tvFollowTxt = (TextView) findViewById(R.id.tv_is_follow_ulhl);
        rlFollowBoMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                follow();
            }
        });
        //收藏控件
        rlCollection = (RelativeLayout)findViewById(R.id.rl_collection_vdf);
        ivColletion = (ImageView)findViewById(R.id.iv_collection_vdf);
        rlCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collection();
            }
        });
        //点击提交评论控件
        rlSubmitComment = (RelativeLayout) findViewById(R.id.rl_comment_vdf);
        rlSubmitComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitComment();
            }
        });
        //举报控件
        rlReport = (RelativeLayout) findViewById(R.id.rl_report_vdf);
        rlReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                report();
            }
        });
        //下载控件
        rlDownload = (RelativeLayout) findViewById(R.id.rl_download_vdf);
        rlDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                download();
            }
        });
    }


    /**
     * 初始化等待加载ing对话框
     */
    private void initLoadingDialog() {
        mKProgressHUD = KProgressHUD.create(VideoDetailActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(" 提交中 ")
                //.setDetailsLabel("Downloading data")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
    }

    /**
     * 上拉加载更多
     */
    private void scrollEvent() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int mLastVisibleItemPosition;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                try {
                    int totalItemCount = mLinearLayoutManager.getItemCount();
                    if (newState == RecyclerView.SCROLL_STATE_IDLE && (mLastVisibleItemPosition == totalItemCount - 1)) {
                        if (NetUtil.isConnected(VideoDetailActivity.this)) {
                            if (page < mTotapage) {
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
                        Log.e(TAG, "还没滑动到底部");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "滑动onScrollStateChanged异常");
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mLastVisibleItemPosition = mLinearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    /**
     * 节操播放的逻辑
     */
    private void jcPlayLogic() {
        mVideoUrl = mData.getVideoUrl();
        mImageUrl = mData.getImageUrl();
        mTypeName = mData.getTypeName();
        if (mVideoUrl != null) {
            if (mTypeName != null) {
                mJzVideoPlayerStandard.setUp(mVideoUrl
                        , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, mTypeName);
            } else {
                mJzVideoPlayerStandard.setUp(mVideoUrl
                        , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "标题");
            }
            //设置自动播放
            mJzVideoPlayerStandard.startButton.performClick();
            //当点击了全屏时则activity切换成横屏
            JZVideoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        }
        if (mImageUrl != null) {
            Glide.with(this).load(mImageUrl).into(mJzVideoPlayerStandard.thumbImageView);
        }
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        try {
            JZVideoPlayer.releaseAllVideos();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"释放节操异常");
        }
        try {
            MobclickAgent.onPause(this);
        } catch (Exception e) {
            e.printStackTrace();
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


    /**
     * 没有收藏的状态
     */
    private void notCollectionState() {
        //改变背景图片
        isCollectState = false;
        ivColletion.setBackgroundResource(R.drawable.shoucang_hei_);
    }
    /**
     * 已经收藏的状态
     */
    private void collectionState() {
        //改变背景图片
        isCollectState = true;
        ivColletion.setBackgroundResource(R.drawable.collect_p);
    }

    /**
     * 没被关注过的状态
     */
    private void notFollowState() {
        isFollowState = false;
        tvFollowTxt.setText("关注");
        rlFollowBoMaster.setBackgroundResource(R.drawable.follow_checked_shape);
    }

    /**
     * 被关注过的状态
     */
    private void followState() {
        isFollowState = true;
        tvFollowTxt.setText("已关注");
        rlFollowBoMaster.setBackgroundResource(R.drawable.follow_unchecked_shape);
    }

    /**
     * 跳转到波主页面
     * @param userName
     * @param followId
     */
    private void setClickToBoMaster(final String userName, final String followId) {
        rlHeadFollowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mFollowName = getIntent().getStringExtra("bo_name");
//                mFollowId = getIntent().getStringExtra("followId");
                Intent intent = new Intent(VideoDetailActivity.this, BoMasterDetailActivity.class);
                intent.putExtra("bo_name",userName);
                intent.putExtra("followId",followId);
                startActivity(intent);
            }
        });
    }

    /**
     * 到登录
     */
    private void jump2login() {
        Intent intent = new Intent(VideoDetailActivity.this, LoginActivity.class);
        VideoDetailActivity.this.startActivity(intent);
    }
    /**
     * 异步单任务下载
     */
    private void asyncDownload() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/MTWVideo/video/" + "mtw" + BeautyContent.getContentBean().getVideoId() + ".mp4";
        FileDownloader.getImpl().create(BeautyContent.getContentBean().getVideoUrl())
                .setPath(path)
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        Toast.makeText(VideoDetailActivity.this, "下载完成请到我的下载中查看", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {

                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {

                    }
                }).start();
    }

    /**
     * 请求下载数据
     */
    private void download() {
        try {
            String uid = PreferencesUtils.getString(VideoDetailActivity.this, "uid");
            if (uid != null && !uid.isEmpty() && !uid.equals("")) {
                Toast.makeText(VideoDetailActivity.this, "正在下载", Toast.LENGTH_SHORT).show();
                // TODO: 2018/1/11 显示gif图片
                asyncDownload();
            } else {
                //到登录
                jump2login();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "download下载异常");
        }
    }

    /**
     * todo请求举报数据
     */
    private void report() {
        try {
            String uid = PreferencesUtils.getString(VideoDetailActivity.this, "uid");
            if (uid != null && !uid.isEmpty() && !uid.equals("")) {
                Toast.makeText(VideoDetailActivity.this, "举报成功", Toast.LENGTH_SHORT).show();
            } else {
                //到登录
                jump2login();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"report点击举报异常");
        }
    }

    /**
     * 请求提交评论数据
     */
    private void submitComment() {
        try {
            String uid = PreferencesUtils.getString(VideoDetailActivity.this, "uid");
            if (uid != null &&!uid.isEmpty() && !uid.equals("")) {
                String videoId = mData.getVideoId();
                CommentSubmitManager.getInstance().setCommentSubmit(VideoDetailActivity.this, videoId, "1", new CommentSubmitManager.CommentSubmitCallBack() {
                    @Override
                    public void onFailed(String msg) {
                        Toast.makeText(VideoDetailActivity.this, "评论失败", Toast.LENGTH_SHORT).show();
                        mKProgressHUD.dismiss();
                    }

                    @Override
                    public void onSuccess(CommentSubmitResBean bean) {
                        try {
                            if (bean != null) {
                                Toast.makeText(VideoDetailActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                                mKProgressHUD.dismiss();
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
                                    mListComment.add(0,commentListBean);
                                    mAdapter.notifyDataSetChanged();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e(TAG,"setDataCommentSubmit提交评论回调成功异常");
                        }
                    }
                });
            } else {
                /**到登录*/
                jump2login();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"提交评论异常");
        }
    }


    /**
     * 请求收藏数据
     */
    private void collection() {
        try {
            //获取用户id
            String uid = PreferencesUtils.getString(VideoDetailActivity.this, "uid");
            if (uid != null && !uid.isEmpty() && !uid.equals("")) {
                CollectionManager.getInstance().setCollectionData(isCollectState, uid, mData.getVideoId(), "1", new CollectionManager.CollectionCallBack() {
                    @Override
                    public void onSuccess(CollectionResBean bean) {
                        try {
                            if (bean != null) {
                                String state = bean.getState();
                                if (state.equals("1")) {
                                    //改变背景图片
                                    collectionState();
                                } else {
                                    //还原背景图片
                                    notCollectionState();
                                }
                                String msg = bean.getMsg();
                                Toast.makeText(VideoDetailActivity.this, msg + "", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e(TAG, "setDataCollection数据为空");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e(TAG,"setDataCollection收藏成功回调异常");
                        }
                    }

                    @Override
                    public void onFailed(String errorMsg) {
                        Toast.makeText(VideoDetailActivity.this, "收藏失败~", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                /**到登录*/
                jump2login();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"collection请求收藏数据异常");
        }
    }

    /**
     * 请求关注的数据
     */
    private void follow() {
        try {
            //获取用户id
            String uid = PreferencesUtils.getString(VideoDetailActivity.this, "uid");
            if (uid != null && !uid.isEmpty() && !uid.equals("")) {
                FollowManager.getInstance().setFollow(isFollowState, rlFollowBoMaster, tvFollowTxt, uid, mData.getFollowId(), 0, new FollowManager.FollowCallBack() {
                    @Override
                    public void onSuccess(FollowResBean bean) {
                        try {
                            if (bean != null) {
                                int state = bean.getState();
                                if (state == 1) {
                                    //关注成功
                                    followState();
                                } else {
                                    //取消关注成功
                                    notFollowState();
                                }
                                Toast.makeText(VideoDetailActivity.this, bean.getMsg() + "", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e(TAG,"follow关注成功回调异常");
                        }
                    }

                    @Override
                    public void onFailed(String errorMsg) {
                        Toast.makeText(VideoDetailActivity.this, ""+errorMsg, Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                /**到登录*/
                jump2login();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"follow请求关注的数据异常");
        }
    }

    /**
     * 请求视频详情页数据
     */
    private void loadData() {
        VideoDetailReqBean bean = new VideoDetailReqBean();
        bean.setAction("videoDetail");
        if (mData != null && mData.getFollowId() != null) {
            bean.setFollowId(mData.getFollowId());
            String videoId = mData.getVideoId();
            bean.setVideoId(videoId);
        } else {
            bean.setVideoId("");
            bean.setFollowId("");
        }
        bean.setPage(page + "");
        String uid = PreferencesUtils.getString(this, "uid");
        if (uid != null && !uid.isEmpty() && !uid.equals("")) {
            bean.setUserId(uid);
        } else {
            bean.setUserId("");
        }
        mVideoDetailPresenter.getDataVideoDetail(bean);
    }

    /**
     * 获取评论列表数据
     */
    private void loadDataCommentList() {
//        typeId	int	图片or视频Id
//        isPicture	int	0图片 1视频
//        page	int	分页
        String videoId = mData.getVideoId();
        CommentListReqBean bean = new CommentListReqBean();
        bean.setAction("commentList");
        bean.setIspicture("1");
        bean.setTypeId(videoId);
        bean.setPage(commentPage + "");
        mCommentListPresenter.getDataCommentList(bean);
    }

    /**
     * 视频详情回调失败
     */
    @Override
    public void showErrorVideoDetail() {
        Log.e(TAG,"请求视频详情页数据失败");
    }

    /**
     * 视频详情回调成功
     * @param bean
     */
    @Override
    public void setDataVideoDetail(VideoDetailResBean bean) {
        if (bean != null) {
            VideoDetailResBean.DataBean data = bean.getData();
            if (data != null) {
                String avatarurl = data.getAvatarurl();
                String userName = data.getUserName();
                String followId = data.getFollowId();
                String fansCount = data.getFansCount();
                int isFollower = data.getIsFollower();
                int isCollect = data.getIsCollect();
                if (isFollower == 1) {
                    //已关注的状态
                    followState();
                } else {
                    //没被关注的状态
                    notFollowState();
                }
                if (isCollect == 1) {
                    //已经收藏的状态
                    collectionState();
                } else {
                    //没有收藏的状态
                    notCollectionState();
                }

                int totalPage = data.getTotalPage();
                if (userName != null) {
                    tvName.setText(userName);
                }
                if (fansCount != null) {
                    tvFens.setText("粉丝:"+fansCount);
                }
                if (avatarurl != null) {
                    Glide.with(VideoDetailActivity.this).load(avatarurl).into(ivHead);
                }
                if (userName != null && followId != null) {
                    setClickToBoMaster(userName,followId);
                }
                final List<VideoDetailResBean.DataBean.VideoListBean> videoList = data.getVideoList();
                mAdapter.setHeadData(videoList);
                //刷新指定的条目-这里只刷新position=0的头部
                mAdapter.setRefreshSomeoneItem();
                //头部水平条目的点击事件
                mAdapter.setOnHeadItemClickListener(new AboutVideoCommentListAdapter.OnHeadItemClickListener() {
                    @Override
                    public void headClick(View view, int position) {
                        jump2detail(position, videoList);
                    }
                });
            } else {
                // TODO: 2018/1/26 data数据为空
            }
        } else {
            // TODO: 2018/1/26 bean数据为空
        }
    }

    /**
     * 到视频详情页
     * 自己跳自己：先关闭自己
     * @param position
     * @param videoList
     */
    private void jump2detail(int position, List<VideoDetailResBean.DataBean.VideoListBean> videoList) {
        try {
            VideoDetailActivity.this.finish();
            Intent intent = new Intent(VideoDetailActivity.this, VideoDetailActivity.class);
            VideoDetailResBean.DataBean.VideoListBean videoListBean = videoList.get(position);
            VideoListResBean.DataBean.TypeListBean typeListBean = new VideoListResBean.DataBean.TypeListBean();
            String videoUrl = videoListBean.getVideoUrl();
            int isVip = videoListBean.getIsVip();
            String imageUrl = videoListBean.getImageUrl();
            String videoId = videoListBean.getVideoId();
            typeListBean.setVideoUrl(videoUrl);
            typeListBean.setVideoId(videoId);
            typeListBean.setTypeName("相关视频");
            typeListBean.setIsVip(isVip);
            typeListBean.setFollowId(mData.getFollowId());
            typeListBean.setImageUrl(imageUrl);
            intent.putExtra("dataBean",typeListBean);
            VideoDetailActivity.this.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"jump2detail跳转异常");
        }
    }


    /**
     * 获取评论列表回调失败
     */
    @Override
    public void showErrorCommentList() {
        // TODO: 2018/1/26 获取评论列表失败
    }

    /**
     * 获取评论列表回调成功
     * @param bean
     */
    @Override
    public void setDataCommentList(CommentListResBean bean) {
        if (bean != null) {
            CommentListResBean.DataBean data = bean.getData();
            if (data != null) {
                mTotapage = data.getTotapage();
                List<CommentListResBean.DataBean.CommentListBean> commentList = data.getCommentList();
                mListComment.addAll(commentList);
                mAdapter.notifyDataSetChanged();
            } else {
                // TODO: 2018/1/26 data为空
            }
        } else {
            // TODO: 2018/1/26 bean为空
        }
    }
}
