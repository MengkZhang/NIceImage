package com.xlkj.beautifulpicturehouse.module.mine.view.ui.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.umeng.analytics.MobclickAgent;
import com.xlkj.beautifulpicturehouse.BeautyApplication;
import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.common.manager.ClearUserDataAsyncTask;
import com.xlkj.beautifulpicturehouse.common.rxbus.RxBus;
import com.xlkj.beautifulpicturehouse.common.util.FileUtils;
import com.xlkj.beautifulpicturehouse.common.util.PreferencesUtils;
import com.xlkj.beautifulpicturehouse.common.view.ui.activity.base.BaseStateActivity;
import com.xlkj.beautifulpicturehouse.common.view.widget.SwipeBackLayout;
import com.xlkj.beautifulpicturehouse.module.mine.bean.LocalUserInfo;
import com.xlkj.beautifulpicturehouse.module.mine.bean.LoginIsRefreshDataBean;
import com.xlkj.beautifulpicturehouse.module.mine.bean.LogoutBean;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 用户信息activity
 */
public class UserInfoActivity extends BaseStateActivity {

    private SwipeBackLayout mSwipeBackLayout;
    private LocalUserInfo mUserInfo;
    private CircleImageView mImageView;
    private TextView tvSex;
    private TextView tvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.activity_user_info);
        getLocalUserInfo();
        mSwipeBackLayout = new SwipeBackLayout(this);
        initView();
        setHead();
    }

    private void setHead() {
        if (mUserInfo != null) {
            Glide.with(this).load(mUserInfo.getHeadUrl()).into(mImageView);
            tvSex.setText(mUserInfo.getSex());
            tvName.setText(mUserInfo.getNickName());
        }
    }

    /**
     * 获取本地用户信息
     */
    private void getLocalUserInfo() {
        mUserInfo = (LocalUserInfo) FileUtils.getCacheDataFromFile(this, "user_info");
    }

    /**
     * 初始化控件
     */
    private void initView() {
        //隐藏分享
        findViewById(R.id.tv_follow_btl).setVisibility(View.GONE);
        //头像
        mImageView = (CircleImageView) findViewById(R.id.iv_head_aui);
        //性别
        tvSex = (TextView) findViewById(R.id.tv_sex_aui);
        //昵称
        tvName = (TextView) findViewById(R.id.tv_name_aui);
        //返回键
        ((TextView)findViewById(R.id.beauty_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //标题
        ((TextView)findViewById(R.id.beauty_title)).setText("个人信息");
        //退出登录
        findViewById(R.id.rl_logout_aui).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showExitDialog();
            }
        });
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

    /**
     * 显示退出对话框
     */
    private void showExitDialog() {
        String msg = "退出当前账号，数据不能同步，也不能使用VIP权限，是否退出?";
        AlertDialog.Builder builder = new AlertDialog.Builder(UserInfoActivity.this);
        AlertDialog dialog = builder.create();
        builder.setTitle("退出确认")
                .setMessage(msg)
                .setPositiveButton("确认退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        exitLogin();
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    /**
     * 退出登录
     */
    private void exitLogin() {
        // TODO: 2018/1/4 这里是退出腾讯QQ登录
        BeautyApplication.mTencent.logout(UserInfoActivity.this);
        //删除用户信息
        new ClearUserDataAsyncTask(UserInfoActivity.this, new ClearUserDataAsyncTask.ClearUserInfoCallBack() {
            @Override
            public void success(boolean b) {
                if (b) {
                    //删除用户信息成功
                    rxLogout();
                    UserInfoActivity.this.finish();
                } else {
                    //删除用户信息失败
                    rxLogout();
                    UserInfoActivity.this.finish();
                }
                //发送消息--首页重新获取不带uid参数的数据列表
                LoginIsRefreshDataBean loginIsRefreshDataBean = new LoginIsRefreshDataBean();
                loginIsRefreshDataBean.setRefreshData(true);
                RxBus.getInstance().post(loginIsRefreshDataBean);
            }

            @Override
            public void error(String msg) {

            }
        }).execute();
        // TODO: 2018/1/4 不是是否要发送消息
    }

    /**
     * 发送退出消息
     */
    private void rxLogout() {
        LogoutBean bean = new LogoutBean();
        bean.setLogout(true);
        RxBus.getInstance().post(bean);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mSwipeBackLayout.attachToActivity(this,SwipeBackLayout.EDGE_LEFT);
        // 触摸边缘变为屏幕宽度的1/2
        mSwipeBackLayout.setEdgeSize(getResources().getDisplayMetrics().widthPixels / 2);
    }
}
