package com.xlkj.beautifulpicturehouse.common.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by zhang on 2017/9/6.
 * 获取APP版本号的帮助类
 */
public class AppVersionUtil {
    /**
     * 获取APP的版本号
     * @param activity：activity
     * @return：返回值 版本号
     */
    public static String getAppVersion(Activity activity) {
        String version = "";
        try {
            PackageInfo info = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
            version = "" + info.versionName;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getAppVersionCode(Context context) {
        try {
            String pkName = context.getPackageName();
            int versionCode = context.getPackageManager().getPackageInfo(
                    pkName, 0).versionCode;
            return versionCode;
        } catch (Exception e) {
        }
        //return -1;
        return 1;
    }

}
