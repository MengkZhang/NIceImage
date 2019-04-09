package com.xlkj.beautifulpicturehouse.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.xlkj.beautifulpicturehouse.common.constant.BeautyContent;
import com.xlkj.beautifulpicturehouse.common.constant.Constant;
import com.xlkj.beautifulpicturehouse.common.util.FileUtils;
import com.xlkj.beautifulpicturehouse.common.util.JSONUtil;
import com.xlkj.beautifulpicturehouse.module.mine.bean.GetTokenOpenIdBean;
import com.xlkj.beautifulpicturehouse.module.mine.bean.UserNameUrlSexInfo;
import com.xlkj.beautifulpicturehouse.module.mine.bean.WXBaseUserInfo;
import com.xlkj.beautifulpicturehouse.module.mine.bean.WXNameUrlUserInfo;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler/*,MineContract.IWXLogin1View */{

    public static final String TAG = "-->WXEntryActivity";
    private IWXAPI weiXinApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        weiXinApi = WXAPIFactory.createWXAPI(this, Constant.WEIXIN_APP_ID,
                true);
        weiXinApi.handleIntent(getIntent(), this);
        Log.e(TAG,"微信页面回调了吗？");
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
        finish();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        weiXinApi.handleIntent(intent, this);
        finish();
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp resp) {
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                switch (resp.getType()) {
                    case ConstantsAPI.COMMAND_SENDAUTH:
                        //微信登录的逻辑
                        wxLoginLogic(resp);

                        break;
                    case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:
                        //分享回调,处理分享成功后的逻辑
                        Toast.makeText(WXEntryActivity.this, "分享成功!", Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        break;
                }
                break;

            default:
                break;

        }
        finish();
    }

    /**
     * 微信登录访问官方获取code
     * @param resp
     */
    private void wxLoginLogic(BaseResp resp) {
        try {
            //登录回调,处理登录成功的逻辑
            if (resp instanceof SendAuth.Resp) {
                SendAuth.Resp newResp = (SendAuth.Resp) resp;
                //获取微信传回的code
                String code = newResp.code;
                Toast.makeText(this, "code:"+code, Toast.LENGTH_SHORT).show();
                //把code存到临时变量中
                BeautyContent.setCode(code);
                getTokenOpenId(code);
                Log.e(TAG,"code="+code);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"wxLoginLogic微信登录异常");
            // TODO: 2017/12/27 RxBus发送登录失败的消息
        }
    }

    /**
     * 根据code获取token和openId
     * @param code
     */
    private void getTokenOpenId(String code) {
        // TODO: 2017/12/27 OkHttpUtils不用混淆 
        OkHttpUtils.get().url("https://api.weixin.qq.com/sns/oauth2/access_token?" + "appid=" + Constant.WEIXIN_APP_ID + "&secret=" + Constant.WEIXIN_SECRET + "&code=" + code + "&grant_type=authorization_code")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Log.e(TAG,"获取token和openId失败");
                        // TODO: 2017/12/27 RxBus发送登录失败的消息
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            GetTokenOpenIdBean tokenOpenIdBean = (GetTokenOpenIdBean)JSONUtil.toObject(response,GetTokenOpenIdBean.class);
                            if (tokenOpenIdBean != null && tokenOpenIdBean.openid != null && tokenOpenIdBean.access_token != null) {
                                Log.e(TAG,"获取的token："+tokenOpenIdBean.getOpenid());
                                Log.e(TAG,"获取的token："+tokenOpenIdBean.getAccess_token());
                                //用access_token和openid获取用户信息--访问微信服务器
                                getUserInfo(tokenOpenIdBean.openid,tokenOpenIdBean.access_token);
                            } else {
                                Log.e(TAG,"获取token和openid为空");
                                // TODO: 2017/12/27 RxBus发送登录失败的消息
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e(TAG,"onResponse json解析数据异常");
                            // TODO: 2017/12/27 RxBus发送登录失败的消息
                        }
                    }
                });
    }

    /**
     *  //用access_token和openid获取用户信息--访问微信服务器
     * @param openid
     * @param access_token
     */
    private void getUserInfo(String openid, String access_token) {
        OkHttpUtils.get().url("https://api.weixin.qq.com/sns/userinfo?" + "access_token=" + access_token + "&openid=" + openid)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Log.e(TAG,"获取用户信息失败");
                        // TODO: 2017/12/27 RxBus发送登录失败的消息
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            WXNameUrlUserInfo wxNameUrlUserInfo = (WXNameUrlUserInfo) JSONUtil.toObject(response, WXNameUrlUserInfo.class);
                            //自己定义的微信的基本用户信息
                            WXBaseUserInfo userInfo = new WXBaseUserInfo();
                            userInfo.setNickName(wxNameUrlUserInfo.getNickname());
                            userInfo.setOpenid(wxNameUrlUserInfo.getOpenid());
                            userInfo.setPhotoUrl(wxNameUrlUserInfo.getHeadimgurl());
                            try {
                                if (wxNameUrlUserInfo.sex == 0) {
                                    //0代表女  1代表男
                                    userInfo.setSex("0");
                                } else {
                                    userInfo.setSex("1");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                userInfo.setSex("1");
                            }
                            Log.e(TAG,"用户名："+wxNameUrlUserInfo.getNickname());
                            
                            //保存用户信息到文件中
                            UserNameUrlSexInfo info = new UserNameUrlSexInfo();
                            info.setHeadUrl(wxNameUrlUserInfo.getHeadimgurl());
                            info.setNickName(wxNameUrlUserInfo.getNickname());
                            info.setSex(wxNameUrlUserInfo.getSex()+"");
                            boolean user_info = FileUtils.saveCachDataToFile(WXEntryActivity.this, "user_info", info);
                            if (user_info) {
                                Log.e(TAG,"保存用户信息到user_info文件成功");
                            } else {
                                Log.e(TAG,"保存用户信息到user_info文件失败");
                            }

                            // TODO: 2017/12/27 访问APP服务器 正式登录 成功之后需要发送RxBus关闭LoginActivity
                            //保存token和uid到本地文件中
//                            PreferencesUtils.putString(WXEntryActivity.this,"uid",content.getUid());
                            Toast.makeText(WXEntryActivity.this, "登录成功", Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e(TAG,"获取用户信息异常");
                        }
                    }
                });
    }


}
