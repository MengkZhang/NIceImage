package com.xlkj.beautifulpicturehouse.common.view.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by Administrator on 2018/1/26.
 * 继承的节操控件：为了能在不是全屏播放的时候显示返回键
 */

public class MyJZVideoPlayerStandard extends JZVideoPlayerStandard {
    public MyJZVideoPlayerStandard(Context context) {
        super(context);
    }

    public MyJZVideoPlayerStandard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(Context context) {
        super.init(context);
    }

    @Override
    public void setUp(Object[] dataSourceObjects, int defaultUrlMapIndex, int screen, Object... objects) {
        super.setUp(dataSourceObjects, defaultUrlMapIndex, screen, objects);
        //显示返回按钮
        backButton.setVisibility(VISIBLE);
        //设置标题只能是一行字
        titleTextView.setMaxLines(1);
        titleTextView.setEllipsize(TextUtils.TruncateAt.END);
    }
}
