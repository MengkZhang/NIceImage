package com.xlkj.beautifulpicturehouse;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.kingja.loadsir.core.LoadSir;
import com.liulishuo.filedownloader.FileDownloader;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;
import com.xlkj.beautifulpicturehouse.common.constant.Constant;
import com.xlkj.beautifulpicturehouse.common.load.callback.EmptyCallback;
import com.xlkj.beautifulpicturehouse.common.load.callback.ErrorCallback;
import com.xlkj.beautifulpicturehouse.common.load.callback.LoadingCallback;
import com.xlkj.beautifulpicturehouse.common.load.callback.TimeoutCallback;
import com.xlkj.beautifulpicturehouse.module.MainActivity;
import com.xlkj.beautifulpicturehouse.module.home.model.ConfigModelImpl;

/**
 * Created by zhang on 2017/12/19.
 */

public class BeautyApplication extends Application {

    public static Context appContext;
    public static IWXAPI weiXinApi = null;
    public static Tencent mTencent = null;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        //初始化检测内存泄漏工具-在debug版本中使用
        if (initLeaks()) return;
        // Normal app init code...
        initCrashHandler();
        initConfig();
        initBugly();
        initFileDownLoader();
        initLoadSir();
        initWeChat();
        initQQ();
    }

    /**
     * 配置bugly
     * 为了保证运营数据的准确性，建议不要在异步线程初始化Bugly。
     * 第三个参数为SDK调试模式开关，调试模式的行为特性如下：
     * 输出详细的Bugly SDK的Log；
     * 每一条Crash都会被立即上报；
     * 自定义日志将会在Logcat中输出。
     * 建议在测试阶段建议设置成true，发布时设置为false。
     */
    private void initBugly() {
        CrashReport.initCrashReport(getApplicationContext(), Constant.BUGLY_APPID, true);
//        CrashReport.testJavaCrash();
    }

    /**
     * 获取配置信息
     */
    private void initConfig() {
        ConfigModelImpl presenter = new ConfigModelImpl();
        presenter.loadDataConfig("getConfigInfo");
    }

    /**
     * 实例化全局异常捕获类
     */
    private void initCrashHandler() {
        Thread.setDefaultUncaughtExceptionHandler(restartHandler);
    }

    /**
     * 创建服务用于捕获崩溃异常
     */
    private Thread.UncaughtExceptionHandler restartHandler = new Thread.UncaughtExceptionHandler() {
        public void uncaughtException(Thread thread, Throwable ex) {
            //发生崩溃异常时,重启应用
            restartApp();
        }
    };

    /**
     * 重启App
     */
    public void restartApp(){
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
        //结束进程之前可以把你程序的注销或者退出代码放在这段代码之前
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 初始化下载库
     */
    private void initFileDownLoader() {
        FileDownloader.init(this);
    }

    /**
     * 初始化检测内存泄漏工具-在debug版本中使用
     * @return
     */
    private boolean initLeaks() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return true;
        }
        LeakCanary.install(this);
        return false;
    }

    /**
     * 注册到QQ
     */
    private void initQQ() {
        //QQ初始化
        mTencent = Tencent.createInstance(Constant.QQ_APP_ID, appContext);
    }

    /**
     * 注册到微信
     */
    private void initWeChat() {
        weiXinApi = WXAPIFactory.createWXAPI(appContext, Constant.WEIXIN_APP_ID,true);
        weiXinApi.registerApp(Constant.WEIXIN_APP_ID);
    }

    /**
     * 初始化loadSir
     */
    private void initLoadSir() {
        LoadSir.beginBuilder()
                .addCallback(new ErrorCallback())
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new TimeoutCallback())
                /*.addCallback(new CustomCallback())*/
                .setDefaultCallback(LoadingCallback.class)
                .commit();
    }

    /**
     * 获取微信Api对象
     *
     * @return
     */
    public static IWXAPI getWeiXinApi() {
        return weiXinApi;
    }

    /**
     * 获取QQApi对象
     *
     * @return
     */
    public static Tencent getTencentApi() {
        return mTencent;
    }
}
