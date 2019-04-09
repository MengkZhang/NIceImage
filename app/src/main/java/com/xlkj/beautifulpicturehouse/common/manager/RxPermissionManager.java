package com.xlkj.beautifulpicturehouse.common.manager;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;

import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;

/**
 * Created by Administrator on 2018/1/22.
 * initRxPermission帮助类
 */

public class RxPermissionManager {
    private RxPermissionManager() {

    }

    public static RxPermissionManager single = new RxPermissionManager();

    public static RxPermissionManager getInstance() {
        return single;
    }

    public void initRxPermission(final Activity mActivity, final String tips, final OnInitRxPermissionListener listener, String... permissions) {
        RxPermissions rxPermissions = new RxPermissions(mActivity);
        rxPermissions.request(permissions)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            //同意权限
                            listener.onSuccess();
                        } else {
                            //不同意权限 给提示
                            final AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                            builder.setTitle("提示");
                            builder.setMessage(tips);
                            builder.setCancelable(false);
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                    dialogInterface.dismiss();
                                    listener.onCancel();
                                    mActivity.startActivity(getAppDetailSettingIntent(mActivity));
                                }
                            });
                            builder.create().show();
                        }
                    }
                });
    }

    /**
     * 打开设置详情页面
     * @param mActivity
     * @return
     */
    private static Intent getAppDetailSettingIntent(Activity mActivity) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", mActivity.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", mActivity.getPackageName());
        }
        return localIntent;
    }

    public interface OnInitRxPermissionListener {
        void onSuccess();

        void onCancel();
    }


}
