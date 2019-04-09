package com.xlkj.beautifulpicturehouse.module;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.umeng.analytics.MobclickAgent;
import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.SplashActivity;
import com.xlkj.beautifulpicturehouse.common.manager.RxPermissionManager;
import com.xlkj.beautifulpicturehouse.common.manager.UpdateManager;
import com.xlkj.beautifulpicturehouse.common.util.NetUtil;
import com.xlkj.beautifulpicturehouse.common.view.ui.activity.base.BaseStateActivity;
import com.xlkj.beautifulpicturehouse.module.home.apiservice.HomeApiService;
import com.xlkj.beautifulpicturehouse.module.home.bean.WeatherBean;
import com.xlkj.beautifulpicturehouse.module.home.view.ui.fragment.HomeFragment;
import com.xlkj.beautifulpicturehouse.module.mine.bean.CheckUpdateResBean;
import com.xlkj.beautifulpicturehouse.module.mine.view.ui.activity.AboutUsActivity;
import com.xlkj.beautifulpicturehouse.module.mine.view.ui.fragment.MineFragment;
import com.xlkj.beautifulpicturehouse.module.video.view.ui.fragment.VideoFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends BaseStateActivity {

    private RadioGroup mRadioGroup;
    private RadioButton mBtnHome;
    private RadioButton mBtnVideo;
    private RadioButton mBtnMine;
    private FrameLayout mflContent;
    private List<Fragment> mFragments;
    private Fragment mFromFragment;
    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        checkUpdate();
        initFragment();
        intFirstContent();
        initEvent();
//        testData();
    }


    /**
     * 检测升级
     */
    private void checkUpdate() {
        try {
            UpdateManager.getInstance().checkUpdate(MainActivity.this,true, new UpdateManager.UpdateCallBack() {
                @Override
                public void onSuccess(String path) {
                    if (path != null) {
                        installApk(path);
                    } else {
                        //安装失败
                        Toast.makeText(MainActivity.this, "安装失败", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailed(String msg) {
                    Toast.makeText(MainActivity.this, "检测失败", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("-->MainActivity","检测跟新异常");
        }
    }

    /**
     * 安装apk
     * @param path
     */
    private void installApk(String path) {
        try {
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
            intent.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
            //在当前activity退出的时候,会调用之前的activity的onActivityResult方法
            //requestCode : 请求码,用来标示是从哪个activity跳转过来
            //ABC  a -> c    b-> c  ,c区分intent是从哪个activity传递过来的,这时候就要用到请求码
            startActivityForResult(intent, 0);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("MainActivity","安装应用异常");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 0) {
                //安装后进入APP引导页
                //2017/10/12 跳转到引导页
                Intent intent = new Intent(this, SplashActivity.class);
                startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("-->MainActivity","安装apk完成后的activity回调异常");
        }
    }

    /**
     * 给radiogroup设置监听
     */
    private void initEvent() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()) {

                    case R.id.rb_home_activity_main:
                        addContent(mFragments.get(0));
                        mFromFragment = mFragments.get(0);
                        break;

                    case R.id.rb_video_activity_main:
                        addContent(mFragments.get(1));
                        mFromFragment = mFragments.get(1);
                        break;

                    case R.id.rb_mine_activity_main:
                        addContent(mFragments.get(2));
                        mFromFragment = mFragments.get(2);
                        break;
                }
            }
        });
    }

    /**
     * 替换布局
     *
     * @param fragment
     */
    private void addContent(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (!fragment.isAdded()) {
            ft.hide(mFromFragment).add(R.id.fl_content_activity_main, fragment);
        } else {
            ft.hide(mFromFragment).show(fragment);
        }
        ft.commit();
    }

    /**
     * 初始化第一个页面
     */
    private void intFirstContent() {
        mFromFragment = mFragments.get(0);
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content_activity_main, mFromFragment).commit();
    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {
        mFragments = new ArrayList<>();
        mFragments.add(new HomeFragment());
        mFragments.add(new VideoFragment());
        mFragments.add(new MineFragment());
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mRadioGroup = (RadioGroup) findViewById(R.id.rg_activity_main);
        mBtnHome = (RadioButton) findViewById(R.id.rb_home_activity_main);
        mBtnVideo = (RadioButton) findViewById(R.id.rb_video_activity_main);
        mBtnMine = (RadioButton) findViewById(R.id.rb_mine_activity_main);
        mflContent = (FrameLayout) findViewById(R.id.fl_content_activity_main);

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

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(this, "再次点击退出程序",
                    Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            goHome(this);
        }
    }

    /**
     * 回到桌面
     *
     * @param context 上下文
     */
    public static void goHome(Context context) {
        try {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(startMain);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
