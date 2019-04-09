package com.xlkj.beautifulpicturehouse.module.mine.view.ui.activity;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.umeng.analytics.MobclickAgent;
import com.xlkj.beautifulpicturehouse.R;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * 下载的视频播放页面
 */
public class LocalVideoPlayActivity extends AppCompatActivity {

    private JZVideoPlayerStandard mJzVideoPlayerStandard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_video_play);
        String mVideoUrl = getIntent().getStringExtra("url");
        mJzVideoPlayerStandard = (JZVideoPlayerStandard) findViewById(R.id.jc_alvp);
        if (mVideoUrl != null) {
            mJzVideoPlayerStandard.setUp(mVideoUrl
                    , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");
        }
        //设置自动播放
        mJzVideoPlayerStandard.startButton.performClick();
        //当点击了全屏时则activity切换成横屏
        JZVideoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
        finish();
    }
    @Override
    protected void onPause() {
        super.onPause();
        try {
            JZVideoPlayer.releaseAllVideos();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("-->Loacl|Video","释放节操异常");
        }
        try {
            MobclickAgent.onPause(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onResume() {
        super.onResume();
        try {
            MobclickAgent.onResume(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
