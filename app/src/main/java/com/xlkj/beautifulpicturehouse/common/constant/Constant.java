package com.xlkj.beautifulpicturehouse.common.constant;

import android.os.Environment;

/**
 * Created by Administrator on 2017/12/23.
 * 常量类
 */

public class Constant {

    //百度云的ak
    public static final String ak = "7f9c7cdcc0ec4448accf1ca74f5308d7";

    //QQ群
    public static final String QQ_GROUP = "332559872";

    //QQ包名
    public static final String QQ_PACKAGE_NAME = "com.tencent.mobileqq";

    // TODO: 2018/1/4  微信appid 美图屋：暂时还没授权登录的权限 需要运营开通（300块）
    public static final String WEIXIN_APP_ID = "wxf73c7692e45ed123";
    public static final String WEIXIN_SECRET = "dc0b1f553fb5186aa278268d3629ba03";

    //2017/12/28 icon的网络地址
    public static final String QQ_SHARE_ICON = "http://m.260111.com/app2018/applog.png";

    //QQ的APPID和key美图屋-审核通过
    public static final String QQ_APP_ID = "101448789";
    public static final String QQ_APP_KEY = "7ea083ec6024d0caf28f5f9e795f3e03";

    //分享的地址
//    public static final String SHARE_URL = "http://vs.zsyj.com.cn/info/recharge/shape.php";
    public static final String SHARE_URL = "http://t1.hddhhn.com/app2018/app.html";

    //保存配置文件对象key
    public static final String CONFIG_CACHE_KEY = "CONFIG_CACHE_KEY";

    //bugly的APPID-林哥私人账号（QQ大号）
    public static final String BUGLY_APPID = "3c17bec885";

    //apk文件的路径
    public static final String apkPath = Environment.getExternalStorageDirectory().getAbsolutePath() +
            "/MTWVideo/video/" + "mtw_ver" + ".apk";

    //http://mobile.umeng.com/analytics 3437941602@qq.com  yuyou123456
    //UMENG统计的APPid
    public static final String UMENG_APP_ID = "5a6fd8aca40fa359d1000104";

    //支付宝收款二维码-周文海
    public static final String ALI_PAY_CODE_Z = "FKX01115KUHEVCBHF0QXA6";


}
