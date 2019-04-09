package com.xlkj.beautifulpicturehouse.module.mine.view.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.umeng.analytics.MobclickAgent;
import com.xlkj.beautifulpicturehouse.BeautyApplication;
import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.SplashActivity;
import com.xlkj.beautifulpicturehouse.common.constant.Constant;
import com.xlkj.beautifulpicturehouse.common.manager.glideprogress.ProgressInterceptor;
import com.xlkj.beautifulpicturehouse.common.manager.glideprogress.ProgressListener;
import com.xlkj.beautifulpicturehouse.common.util.AppVersionUtil;
import com.xlkj.beautifulpicturehouse.common.util.FileUtils;
import com.xlkj.beautifulpicturehouse.common.util.GlideImgManager;
import com.xlkj.beautifulpicturehouse.common.util.MySystemIntent;
import com.xlkj.beautifulpicturehouse.common.view.ui.activity.base.BaseSwipeBackActivity;
import com.xlkj.beautifulpicturehouse.common.view.ui.dialog.UpdateDialog;
import com.xlkj.beautifulpicturehouse.common.view.ui.dialog.share.DisplayUtil;
import com.xlkj.beautifulpicturehouse.common.view.widget.SwipeBackLayout;
import com.xlkj.beautifulpicturehouse.module.home.bean.ConfigSaveBean;
import com.xlkj.beautifulpicturehouse.module.mine.bean.CheckUpdateReqBean;
import com.xlkj.beautifulpicturehouse.module.mine.bean.CheckUpdateResBean;
import com.xlkj.beautifulpicturehouse.module.mine.contract.MineContract;
import com.xlkj.beautifulpicturehouse.module.mine.presenter.UpdatePresenterImpl;

import java.io.File;
import java.lang.annotation.Target;

public class AboutUsActivity extends BaseSwipeBackActivity implements View.OnClickListener,MineContract.IUpdateView {

    public static final String TAG = "-->AboutUsActivity";
    private TextView mTvBack;
    private TextView mTitle;
    private TextView mVersion;
    private RelativeLayout mQQ;
    private RelativeLayout mUpdate;
    private RelativeLayout mRecommond;
    //qq群
    private TextView mTvQQ;
    //检测跟新presenter
    private UpdatePresenterImpl mUpdatePresenter;
    //更新检测ing的对话框
    private KProgressHUD mKProgressHUDUpdateLoading;
    //apk文件的路径
    String apkPath = Environment.getExternalStorageDirectory().getAbsolutePath() +
            "/MTWVideo/video/" + "mtw_ver" + ".apk";
    //本地的QQ群信息
    private String mQq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        getLocalConfig();
        bindPresenter();
        initLoadingDialog();
        initView();
    }

    /**
     * 获取保存在本地的配置信息
     */
    private void getLocalConfig() {
        try {
            ConfigSaveBean mConfigSaveBean = (ConfigSaveBean)FileUtils.getCacheDataFromFile(this,Constant.CONFIG_CACHE_KEY);
            if (mConfigSaveBean != null) {
                mQq = mConfigSaveBean.getQQ();
            } else {
                mQq = Constant.QQ_GROUP;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"getLocalConfig异常");
            mQq = Constant.QQ_GROUP;
        }
    }

    private void bindPresenter() {
        mUpdatePresenter = new UpdatePresenterImpl();
        mUpdatePresenter.attachUpdate(this);
    }

    private void unbindPresenter() {
        if (mUpdatePresenter != null) {
            //view设置为空
            mUpdatePresenter.detachUpdate();
            //再取消网络操作
            mUpdatePresenter.interruptHttp();
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
     * 初始化等待加载ing对话框
     */
    private void initLoadingDialog() {
        mKProgressHUDUpdateLoading = KProgressHUD.create(AboutUsActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(" 检测中 ")
                //.setDetailsLabel("Downloading data")
//                .setDetailsLabel("  更新检测中......  ")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        //图片icon
        ImageView imageView = (ImageView) findViewById(R.id.iv_icon_about_me);
//        testLoadImageView(imageView);
        //隐藏分享
        findViewById(R.id.tv_follow_btl).setVisibility(View.GONE);
        //qq群
        mTvQQ = (TextView) findViewById(R.id.tv_qq_group_aau);
        mTvQQ.setText("QQ群："+ mQq);
        mTitle = (TextView)findViewById(R.id.beauty_title);
        mTitle.setText("关于我们");
        String version = AppVersionUtil.getAppVersion(this);
        mVersion = (TextView)findViewById(R.id.tv_app_name_about_us_activity);
        mVersion.setText("美图屋"+"V"+version);
        mTvBack = (TextView)findViewById(R.id.beauty_back);
        mQQ = (RelativeLayout)findViewById(R.id.rl_qq_aau);
        mUpdate = (RelativeLayout)findViewById(R.id.rl_update_aau);
        mRecommond = (RelativeLayout)findViewById(R.id.rl_recommond_aau);
        mTvBack.setOnClickListener(this);
        mVersion.setOnClickListener(this);
        mQQ.setOnClickListener(this);
        mUpdate.setOnClickListener(this);
        mRecommond.setOnClickListener(this);
    }

    private void testLoadImageView(ImageView imageView) {
        final String url = "https://upload-images.jianshu.io/upload_images/9132321-45963955820baf71.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/580";
        ProgressInterceptor.addListener(url, new ProgressListener() {
            @Override
            public void onProgress(int progress) {
                Log.e(TAG,"进度="+progress);
            }
        });
        Glide.with(this).load(url).diskCacheStrategy(DiskCacheStrategy.NONE).into(new GlideDrawableImageViewTarget(imageView) {
            @Override
            public void onLoadStarted(Drawable placeholder) {
                super.onLoadStarted(placeholder);
//                progressDialog.show();
            }

            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                super.onResourceReady(resource, animation);
//                progressDialog.dismiss();
                ProgressInterceptor.removeListener(url);
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            //返回
            case R.id.beauty_back:
                finish();
                break;

            //qq
            case R.id.rl_qq_aau:
                tuneUpQQ();
                break;

            //检测更新
            case R.id.rl_update_aau:
                //testNullException();
                checkUpdate();
                break;

            //推荐好友
            case R.id.rl_recommond_aau:
//                share2friend();
                share2friendQQWeChat();
                break;
        }
    }

    /**
     * 测试空指针异常
     */
    private void testNullException() {
        String string = null;
        Log.e(TAG,string.toString());
    }

    /**
     * 分享到QQ微信
     */
    private void share2friendQQWeChat() {
        DisplayUtil.showShareDialog(this, "美女屋", "宅男们都震精啦!!!~", Constant.SHARE_URL);
    }

    /**
     * 推荐好友调用系统分享文字
     */
    private void share2friend() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "一款令宅男们都震精了的APP~~");
        intent.setType("text/plain");
        //设置分享列表的标题，并且每次都显示分享列表
        startActivity(Intent.createChooser(intent, "分享到"));
    }

    /**
     * 检测更新
     */
    private void checkUpdate() {
        mKProgressHUDUpdateLoading.show();
        CheckUpdateReqBean bean = new CheckUpdateReqBean();
        bean.setAction("checkVersion");
        bean.setVersionCode(AppVersionUtil.getAppVersionCode(AboutUsActivity.this) + "");
        mUpdatePresenter.getDataUpdate(bean);


    }

    /**
     * 下载apk文件
     * @param updateurl
     */
    private void downloadApk(String updateurl) {
        mKProgressHUDUpdateLoading.setLabel(" 下载中 ");
        mKProgressHUDUpdateLoading.show();
        FileDownloader.getImpl().create(updateurl)
                .setPath(apkPath)
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.e(TAG,"pending==");
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        double progress = (((double) soFarBytes) / ((double) totalBytes)) * 100;
                        mKProgressHUDUpdateLoading.setLabel("    "+(int)progress+"%    ");
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        mKProgressHUDUpdateLoading.dismiss();
                        Toast.makeText(AboutUsActivity.this, "下载完成", Toast.LENGTH_SHORT).show();
                        try {
                            installAPK();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e(TAG,"安装apk文件异常");
                        }
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.e(TAG,"paused==");
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        Log.e(TAG,"error==");
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        Log.e(TAG,"warn==");
                    }
                }).start();
    }


    /**
     * 安装apk
     */
    private void installAPK() {
        /**
         *  <intent-filter>
         <action android:name="android.intent.action.VIEW" />
         <category android:name="android.intent.category.DEFAULT" />
         <data android:scheme="content" /> //content : 从内容提供者中获取数据  content://
         <data android:scheme="file" /> // file : 从文件中获取数据
         <data android:mimeType="application/vnd.android.package-archive" />
         </intent-filter>
         */
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(new File(apkPath)), "application/vnd.android.package-archive");
        //在当前activity退出的时候,会调用之前的activity的onActivityResult方法
        //requestCode : 请求码,用来标示是从哪个activity跳转过来
        //ABC  a -> c    b-> c  ,c区分intent是从哪个activity传递过来的,这时候就要用到请求码
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            //安装后进入APP引导页
            //2017/10/12 跳转到引导页
            Intent intent = new Intent(this, SplashActivity.class);
            startActivity(intent);
        }
    }

    /**
     * 调起qq
     */
    private void tuneUpQQ() {
        try {
            if (MySystemIntent.isHaveApp(this, Constant.QQ_PACKAGE_NAME) == false) {
                Toast.makeText(this, "您的手机未安装QQ，请加微信客服反馈问题", Toast.LENGTH_SHORT).show();
            } else {

                String qqUrl = "mqqapi://card/show_pslcard?src_type=internal&version=1&uin=" + mQq/*Constant.QQ_GROUP*/
                        + "&card_type=group&source=qrcode";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(qqUrl)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检测跟新失败回调
     */
    @Override
    public void showErrorUpdate() {
        Log.e(TAG,"检测更新失败");
        hindDialog();
        Toast.makeText(this, "检测更新失败", Toast.LENGTH_SHORT).show();
    }

    /**
     * 隐藏dialog
     */
    private void hindDialog() {
        mKProgressHUDUpdateLoading.dismiss();
    }

    /**
     * 检测跟新成功回调
     * @param bean
     */
    @Override
    public void setDataUpdate(CheckUpdateResBean bean) {
        // 在回调成功的方法中添加显示正在加载的对话框
        if (bean != null && bean.getData() != null) {
            int isUpdate = bean.getData().getIsUpdate();
            if (isUpdate == 1) {
                //需要升级
                try {
                    String remark = bean.getData().getUpdateDesc();
                    final String updateurl = bean.getData().getApkUrl();
                    final UpdateDialog updateDialog = new UpdateDialog(this, remark);
                    updateDialog.show();
                    //点击升级
                    updateDialog.setOnUpdateListener(new UpdateDialog.OnUpdateListener() {
                        @Override
                        public void updateMethod() {
                            try {
                                updateDialog.dismiss();
                                downloadApk(updateurl);
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e(TAG,"升级下载apk异常");
                            }
                        }
                    });
                    //dialog的取消点击事件
                    updateDialog.setOnCancelListener(new UpdateDialog.OnCancelListener() {
                        @Override
                        public void cancelMethod() {
                            hindDialog();
                            updateDialog.dismiss();
                            Toast.makeText(AboutUsActivity.this, "取消升级", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG,"检测更新异常");
                }
            } else {
                //不需要升级
                hindDialog();
                Toast.makeText(this, "已经是最新版本啦~", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.e(TAG,"检测跟新数据为空");
        }
    }
}
