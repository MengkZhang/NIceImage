package com.xlkj.beautifulpicturehouse.common.manager;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.xlkj.beautifulpicturehouse.common.constant.Constant;
import com.xlkj.beautifulpicturehouse.common.http.HostType;
import com.xlkj.beautifulpicturehouse.common.http.RetrofitSingleton;
import com.xlkj.beautifulpicturehouse.common.util.AppVersionUtil;
import com.xlkj.beautifulpicturehouse.common.view.ui.dialog.UpdateDialog;
import com.xlkj.beautifulpicturehouse.module.mine.apiservice.MineApiService;
import com.xlkj.beautifulpicturehouse.module.mine.bean.CheckUpdateReqBean;
import com.xlkj.beautifulpicturehouse.module.mine.bean.CheckUpdateResBean;
import com.xlkj.beautifulpicturehouse.module.mine.view.ui.activity.AboutUsActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2018/1/25.
 * 检测升级的单例模式管理者类
 */

public class UpdateManager {

    //更新检测ing的对话框
    private KProgressHUD mKProgressHUDUpdateLoading;
    //apk文件的路径
    String apkPath = Environment.getExternalStorageDirectory().getAbsolutePath() +
            "/MTWVideo/video/" + "mtw_ver" + ".apk";
    private UpdateManager() {

    }

    public static UpdateManager single = new UpdateManager();

    public static UpdateManager getInstance() {
        return single;
    }

    public void checkUpdate(final Context mContext,boolean isMainActivity, final UpdateCallBack mUpdateCallBack) {
        initLoadingDialog(mContext);
        //如果是主页进来则先不显示检测中的对话框
        if (isMainActivity) {
            mKProgressHUDUpdateLoading.dismiss();
        } else {
            mKProgressHUDUpdateLoading.show();
        }
        //网络请求
        Retrofit retrofit = RetrofitSingleton.getInstance(HostType.MTW_INFO).getRetrofit();
        MineApiService apiService = retrofit.create(MineApiService.class);
        CheckUpdateReqBean bean = new CheckUpdateReqBean();
        bean.setAction("checkVersion");
        bean.setVersionCode(AppVersionUtil.getAppVersionCode(mContext) + "");
        Call<CheckUpdateResBean> call = apiService.requestUpdate(bean.getAction(), bean.getVersionCode());
        call.enqueue(new Callback<CheckUpdateResBean>() {
            @Override
            public void onResponse(Call<CheckUpdateResBean> call, Response<CheckUpdateResBean> response) {
                if (response != null && response.body() != null) {
                    //检测成功
                    CheckUpdateResBean.DataBean data = response.body().getData();
                    if (data != null) {
                        int isUpdate = data.getIsUpdate();
                        if (isUpdate == 1) {
                            //需要升级
                            try {
                                String remark = response.body().getData().getUpdateDesc();
                                final String updateurl = response.body().getData().getApkUrl();
                                final UpdateDialog updateDialog = new UpdateDialog(mContext, remark);
                                updateDialog.show();
                                if (updateDialog.isShowing()) {
                                    mKProgressHUDUpdateLoading.dismiss();
                                }
                                //点击升级
                                updateDialog.setOnUpdateListener(new UpdateDialog.OnUpdateListener() {
                                    @Override
                                    public void updateMethod() {
                                        try {
                                            updateDialog.dismiss();
                                            downloadApk(mContext,updateurl,mUpdateCallBack);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            Log.e("-->UpdateManager","升级下载apk异常");
                                        }
                                    }
                                });
                                //dialog的取消点击事件
                                updateDialog.setOnCancelListener(new UpdateDialog.OnCancelListener() {
                                    @Override
                                    public void cancelMethod() {
                                        hindDialog();
                                        updateDialog.dismiss();
                                        Toast.makeText(mContext, "取消升级", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e("-->UpdateManager","检测更新异常");
                            }
                        } else {
                            //不需要升级
                            hindDialog();
//                            Toast.makeText(mContext, "已经是最新版本啦~", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //检测数据data为空
                        mUpdateCallBack.onFailed("检测data数据为空");
                    }
                } else {
                    //检测失败
                    mUpdateCallBack.onFailed("检测数据为空");
                }
            }

            @Override
            public void onFailure(Call<CheckUpdateResBean> call, Throwable t) {
                //检测失败
                mUpdateCallBack.onFailed("检测失败");
            }
        });
    }


    /**
     * 下载apk文件
     * @param mContext
     * @param updateurl
     * @param mUpdateCallBack
     */
    private void downloadApk(final Context mContext, String updateurl, final UpdateCallBack mUpdateCallBack) {
        mKProgressHUDUpdateLoading.setLabel(" 下载中 ");
        mKProgressHUDUpdateLoading.show();
        FileDownloader.getImpl().create(updateurl)
                .setPath(apkPath)
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.e("-->UpdateManager","pending==");
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        double progress = (((double) soFarBytes) / ((double) totalBytes)) * 100;
                        mKProgressHUDUpdateLoading.setLabel("    "+(int)progress+"%    ");
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        mKProgressHUDUpdateLoading.dismiss();
                        Toast.makeText(mContext, "下载完成", Toast.LENGTH_SHORT).show();
                        try {
                            installAPK(mUpdateCallBack);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("-->UpdateManager","安装apk文件异常");
                        }
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.e("-->UpdateManager","paused==");
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        Log.e("-->UpdateManager","error==");
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        Log.e("-->UpdateManager","warn==");
                    }
                }).start();
    }

    /**
     * 安装apk
     * @param mUpdateCallBack
     */
    private void installAPK(UpdateCallBack mUpdateCallBack) {
        mUpdateCallBack.onSuccess(apkPath);
    }

    /**
     * 初始化等待加载ing对话框
     * @param mContext
     */
    private void initLoadingDialog(Context mContext) {
        mKProgressHUDUpdateLoading = KProgressHUD.create(mContext)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(" 检测中 ")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }

    /**
     * 隐藏dialog
     */
    private void hindDialog() {
        mKProgressHUDUpdateLoading.dismiss();
    }

    public interface UpdateCallBack {
        void onSuccess(String path);
        void onFailed(String msg);
    }

}
