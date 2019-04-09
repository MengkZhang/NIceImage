package com.xlkj.beautifulpicturehouse;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.umeng.analytics.MobclickAgent;
import com.xlkj.beautifulpicturehouse.common.manager.GlobalThreadPool;
import com.xlkj.beautifulpicturehouse.module.MainActivity;

/**
 * 闪屏页
 * 功能一：延迟两秒进入到主页
 * todo可能有其他闪屏广告之类的需要补充
 */
public class SplashActivity extends AppCompatActivity {

    public static final String TAG = "-->>SplashActivity-->>";

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (isFinishing()) {
                return;
            }
            //进入到主页或其他
            enterHomeOrOther();
        }
    };

    /**
     * 进入到主页或其他页
     */
    private void enterHomeOrOther() {
        try {
            GlobalThreadPool.getInstance().getGlobalExecutorService().execute(new Runnable() {
                @Override
                public void run() {
                    go2main();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"进入到主页enterHomeOrOther()异常");
            go2main();
        }
    }

    /**
     * 到主页
     */
    private void go2main() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //根本没有导航栏的写法
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_splash);
        //延迟两秒到主页
        mHandler.sendMessageDelayed(Message.obtain(),2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
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
}
