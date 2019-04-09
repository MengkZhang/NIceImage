package com.xlkj.beautifulpicturehouse.common.view.ui.activity.base;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xlkj.beautifulpicturehouse.common.util.StateUtil;
import com.xlkj.beautifulpicturehouse.common.view.widget.SwipeBackLayout;


/**
 * Created by lin on 2017/1/19.
 * 沉浸式状态栏
 */
public abstract class BaseStateActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StateUtil.setState(this);
        StateUtil.StatusBarLightMode(this);
    }



}
