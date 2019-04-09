package com.xlkj.beautifulpicturehouse.module.mine.view.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.tencent.connect.UserInfo;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.umeng.analytics.MobclickAgent;
import com.xlkj.beautifulpicturehouse.BeautyApplication;
import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.common.rxbus.RxBus;
import com.xlkj.beautifulpicturehouse.common.util.FileUtils;
import com.xlkj.beautifulpicturehouse.common.util.PreferencesUtils;
import com.xlkj.beautifulpicturehouse.common.view.ui.activity.SearchActivity;
import com.xlkj.beautifulpicturehouse.module.mine.bean.AllUserInfo;
import com.xlkj.beautifulpicturehouse.module.mine.bean.LocalUserInfo;
import com.xlkj.beautifulpicturehouse.module.mine.bean.LoginIsRefreshDataBean;
import com.xlkj.beautifulpicturehouse.module.mine.bean.LoginReqBean;
import com.xlkj.beautifulpicturehouse.module.mine.bean.LoginResBean;
import com.xlkj.beautifulpicturehouse.module.mine.bean.LoginVIPInfo;
import com.xlkj.beautifulpicturehouse.module.mine.contract.MineContract;
import com.xlkj.beautifulpicturehouse.module.mine.presenter.AppLoginPresenterImpl;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 登录activity
 */
public class LoginActivity extends AppCompatActivity implements MineContract.IAppLoginView {

    public static final String TAG = "-->LoginActivity";
    private QQCallBack mQQCallBack;
    private AppLoginPresenterImpl mAppLoginPresenter;
    //qq官方返回的用户信息 包括头像 昵称 性别等
    private LocalUserInfo mLocalUserInfo;
    //等待加载ing的对话框
    private KProgressHUD mKProgressHUD;
    private RelativeLayout rlQQLogin;
    private RelativeLayout rlWeChatLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.activity_login);
        bindPresenter();
        initView();
        initLoadingDialog();
    }

    /**
     * 初始化等待加载ing对话框
     */
    private void initLoadingDialog() {
        mKProgressHUD = KProgressHUD.create(LoginActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("请稍等")
              //.setDetailsLabel("Downloading data")
                .setDetailsLabel("  数据加载中......  ")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
    }

    private void bindPresenter() {
        mAppLoginPresenter = new AppLoginPresenterImpl();
        mAppLoginPresenter.attachAppLogin(this);
    }

    private void unbindPresenter() {
        if (mAppLoginPresenter != null) {
            mAppLoginPresenter.detachAppLogin();
            mAppLoginPresenter.interruptHttp();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindPresenter();
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

    private void initView() {
        /**微信登录*/
        rlWeChatLogin = (RelativeLayout) findViewById(R.id.wechat_login_dialog);
        rlWeChatLogin.setVisibility(View.INVISIBLE);
        rlWeChatLogin.setClickable(false);
        rlWeChatLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weChatLogin();
            }
        });
        /**qq登录*/
        rlQQLogin = (RelativeLayout) findViewById(R.id.qq_login_dialog);
        rlQQLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qqLogin();
            }
        });
    }

    /**
     * 调起qq登录
     */
    private void qqLogin() {
        mQQCallBack = new QQCallBack();
        if (!BeautyApplication.mTencent.isSessionValid()) {
            showProgress();
            BeautyApplication.mTencent.login(LoginActivity.this,"all",mQQCallBack);
        }
    }

    /**
     * QQ登录访问APP服务器失败
     */
    @Override
    public void showErrorAppLogin() {
        Log.e(TAG,"showErrorAppLogin QQ登录访问APP服务器失败");
    }

    /**
     * qq登录访问APP服务器成功
     * @param bean
     */
    @Override
    public void setDataAppLogin(LoginResBean bean) {
        Log.e(TAG,"qq登录访问APP服务器成功="+bean.toString());
        if (bean != null) {
            if (bean.getData() != null) {
                int isVip = bean.getData().getIsVip();
                String uId = bean.getData().getUserId();
                int token = bean.getData().getToken();
                int vipDayCount = bean.getData().getVipDayCount();
                int vipLevel = bean.getData().getVipLevel();
                //隐藏进度条
                hindProgress();
                // TODO: 2018/1/5 缓存用户信息到本地 退出登录时需要清空一下
                //缓存UID
                cacheUid(uId);
                //缓存VIP信息
                cacheVip(isVip, vipDayCount, vipLevel);
                //发送消息——让MineFragment改变头像昵称VIP相关信息等状态
                AllUserInfo info = new AllUserInfo();
                info.setuId(uId);
                info.setHeadUrl(mLocalUserInfo.getHeadUrl());
                info.setNickName(mLocalUserInfo.getNickName());
                info.setIsVip(isVip);
                info.setVipDayCount(vipDayCount);
                info.setVipLevel(vipLevel);
                RxBus.getInstance().post(info);
                //发送消息--首页重新获取带uid参数的数据列表
                LoginIsRefreshDataBean loginIsRefreshDataBean = new LoginIsRefreshDataBean();
                loginIsRefreshDataBean.setRefreshData(true);
                RxBus.getInstance().post(loginIsRefreshDataBean);
                //关闭本页面
                LoginActivity.this.finish();
            } else {
                Log.e(TAG,"setDataAppLogin data为空");
                hindProgress();
                Toast.makeText(this, "登录失败data为空", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            // TODO: 2018/1/5 登录失败 
            Log.e(TAG,"setDataAppLogin bean为空");
            hindProgress();
            Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    /**
     * 显示进度条
     */
    private void showProgress() {
        mKProgressHUD.show();
    }
    /**
     * 隐藏进度条
     */
    private void hindProgress() {
        mKProgressHUD.dismiss();
    }

    /**
     * 缓存UID
     * @param uId
     */
    private void cacheUid(String uId) {
        PreferencesUtils.putString(LoginActivity.this,"uid",uId);
    }

    /**
     * 缓存VIP信息
     * @param isVip
     * @param vipDayCount
     * @param vipLevel
     */
    private void cacheVip(int isVip, int vipDayCount, int vipLevel) {
        try {
            LoginVIPInfo loginVIPInfo = new LoginVIPInfo();
            loginVIPInfo.setIsVip(isVip);
            loginVIPInfo.setVipDayCount(vipDayCount);
            loginVIPInfo.setVipLevel(vipLevel);
            boolean vip_info = FileUtils.saveCachDataToFile(LoginActivity.this, "vip_info", loginVIPInfo);
            if (vip_info) {
                Log.e(TAG,"setDataAppLogin 缓存VIP信息成功");
            } else {
                Log.e(TAG,"setDataAppLogin 缓存VIP信息失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"setDataAppLogin 缓存VIP信息异常");
        }
    }

    public class QQCallBack implements IUiListener {

        @Override
        public void onComplete(Object o) {
            try {
                //openid:qq登陆的唯一标示
                String openid = ((JSONObject) o).getString("openid");
                String access_token = ((JSONObject) o).getString("access_token");
                String expires_in = ((JSONObject) o).getString("expires_in");
                // TODO: 2018/1/4 保存openId和token
                Log.e(TAG,"qq登录时获取的openid="+openid);
                Log.e(TAG,"qq登录时获取的access_token="+access_token);
                Log.e(TAG,"qq登录时获取的expires_in="+expires_in);
                BeautyApplication.mTencent.setOpenId(openid);
                BeautyApplication.mTencent.setAccessToken(access_token,expires_in);
                getQQUserInfo(openid);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("==zxl==","qq登录成功的回调异常执行");
                hindProgress();
                Toast.makeText(LoginActivity.this, "qq登录成功的回调异常执行", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        @Override
        public void onError(UiError uiError) {
            Log.e(TAG,"onError qq登录错误："+uiError.errorDetail);
            hindProgress();
            Toast.makeText(LoginActivity.this, "onError qq登录错误："+uiError.errorDetail, Toast.LENGTH_SHORT).show();
            return;
        }

        @Override
        public void onCancel() {
            Log.e(TAG,"onCancel qq登录取消");
            hindProgress();
            Toast.makeText(LoginActivity.this, "qq登录取消", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    /**
     * QQ登录获取用户信息
     //                             "ret":0,
     //                             "msg":"",
     //                             "is_lost":0,
     //                             "nickname":"短发夏天",
     //                             "gender":"男",
     //                             "province":"四川",
     //                             "city":"南充",
     //                             "figureurl":"http://qzapp.qlogo.cn/qzapp/1106316379/E05BCDD15CA15E0D0662CB9863281E27/30",
     //                             "figureurl_1":"http://qzapp.qlogo.cn/qzapp/1106316379/E05BCDD15CA15E0D0662CB9863281E27/50",
     //                             "figureurl_2":"http://qzapp.qlogo.cn/qzapp/1106316379/E05BCDD15CA15E0D0662CB9863281E27/100",
     //                             "figureurl_qq_1":"http://q.qlogo.cn/qqapp/1106316379/E05BCDD15CA15E0D0662CB9863281E27/40",
     //                             "figureurl_qq_2":"http://q.qlogo.cn/qqapp/1106316379/E05BCDD15CA15E0D0662CB9863281E27/100",
     //                             "is_yellow_vip":"0",
     //                             "vip":"0",
     //                             "yellow_vip_level":"0",
     //                             "level":"0",
     //                             "is_yellow_year_vip":"0"
     * @param openid:qq登陆的唯一标示
     */
    private void getQQUserInfo(final String openid) {
        UserInfo userInfo = new UserInfo(LoginActivity.this, BeautyApplication.mTencent.getQQToken());
        userInfo.getUserInfo(new IUiListener() {
            @Override
            public void onComplete(Object o) {
                try {

                    JSONObject object = new JSONObject(o.toString());
                    String nickname = object.optString("nickname");
                    String headImgUrl = object.optString("figureurl_qq_2");
                    String gender = object.optString("gender");
                    Log.e(TAG,"nickName："+nickname);
                    Log.e(TAG,"headImgUrl："+headImgUrl);
                    // TODO: 2018/1/4 用户信息保存到文件中 ——退出登录需要清空一下
                    mLocalUserInfo = new LocalUserInfo();
                    mLocalUserInfo.setHeadUrl(headImgUrl);
                    mLocalUserInfo.setNickName(nickname);
                    mLocalUserInfo.setSex(gender);
                    boolean user_info = FileUtils.saveCachDataToFile(LoginActivity.this, "user_info", mLocalUserInfo);
                    if (user_info) {
                        Log.e(TAG,"qq登录缓存用户昵称头像性别成功");
                    } else {
                        Log.e(TAG,"qq登录缓存用户昵称头像性别失败");
                    }
                    // TODO: 2018/1/4 登录APP服务器 返回UID和token
                    loginAppServer(openid,nickname,headImgUrl,gender);


                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG,"getQQUserInfo qq登录获取用户信息异常");
                    // TODO: 2018/1/4
                    hindProgress();
                    Toast.makeText(LoginActivity.this, "qq登录获取用户信息异常", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onError(UiError uiError) {
                hindProgress();
                Toast.makeText(LoginActivity.this, "qq登录获取用户信息错误", Toast.LENGTH_SHORT).show();
                return;
            }

            @Override
            public void onCancel() {
                hindProgress();
                Toast.makeText(LoginActivity.this, "qq登录获取用户信息取消", Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }

    /**
     * 登录访问APP服务器
     * @param openid
     * @param nickname
     * @param headImgUrl
     * @param gender
     */
    private void loginAppServer(String openid, String nickname, String headImgUrl, String gender) {
        LoginReqBean bean = new LoginReqBean();
        bean.setAction("login");
        bean.setAge("24");
        bean.setAvatarUrl(headImgUrl);
        //0女 1男
        if (gender.equals("男")) {
            bean.setGender("1");
        } else {
            bean.setGender("0");
        }
        //0QQ 1微信登录
        bean.setLoginType("0");
        bean.setUserName(nickname);
        bean.setOpenId(openid);
        mAppLoginPresenter.getDataAppLogin(bean);
    }

    /**
     * 重写activity页面的onActivityResult()方法，否则没有回调内容
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode,resultCode,data,mQQCallBack);
    }

    /**
     * 调起微信登录:回调在微信专门的页面
     */
    private void weChatLogin() {
        // TODO: 2017/12/27 进度条
        if (!BeautyApplication.weiXinApi.isWXAppInstalled()) {
            Toast.makeText(LoginActivity.this, "主人，你还没安装微信客户端哟~", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "beautiful_picture_house_wx_login";
            //设置是微信登陆
            BeautyApplication.weiXinApi.sendReq(req);
            Log.e(TAG,"至此，就可以调用微信登录界面来进行登录认证了");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"至此，调用微信登录界面来进行登录认证异常");
            return;
        }
    }
}
