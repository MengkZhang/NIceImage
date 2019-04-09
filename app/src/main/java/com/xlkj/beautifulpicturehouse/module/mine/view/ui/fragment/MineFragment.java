package com.xlkj.beautifulpicturehouse.module.mine.view.ui.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.didikee.donate.AlipayDonate;
import android.didikee.donate.WeiXinDonate;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xlkj.beautifulpicturehouse.BeautyApplication;
import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.common.constant.Constant;
import com.xlkj.beautifulpicturehouse.common.http.HostType;
import com.xlkj.beautifulpicturehouse.common.http.HttpMethods;
import com.xlkj.beautifulpicturehouse.common.http.RetrofitSingleton;
import com.xlkj.beautifulpicturehouse.common.rxbus.RxBus;
import com.xlkj.beautifulpicturehouse.common.util.FileUtils;
import com.xlkj.beautifulpicturehouse.common.util.PreferencesUtils;
import com.xlkj.beautifulpicturehouse.common.view.ui.dialog.VipPayToastDialog;
import com.xlkj.beautifulpicturehouse.common.view.ui.dialog.share.BottomDialog;
import com.xlkj.beautifulpicturehouse.module.mine.bean.AllUserInfo;
import com.xlkj.beautifulpicturehouse.module.mine.bean.LocalUserInfo;
import com.xlkj.beautifulpicturehouse.module.mine.bean.LogoutBean;
import com.xlkj.beautifulpicturehouse.module.mine.view.ui.activity.AboutUsActivity;
import com.xlkj.beautifulpicturehouse.module.mine.view.ui.activity.LoginActivity;
import com.xlkj.beautifulpicturehouse.module.mine.view.ui.activity.MyCollectionActivity;
import com.xlkj.beautifulpicturehouse.module.mine.view.ui.activity.MyDownLoadActivity;
import com.xlkj.beautifulpicturehouse.module.mine.view.ui.activity.MyFollowActivity;
import com.xlkj.beautifulpicturehouse.module.mine.view.ui.activity.MyFootStayActivity;
import com.xlkj.beautifulpicturehouse.module.mine.view.ui.activity.UserInfoActivity;
import com.xlkj.beautifulpicturehouse.module.mine.view.ui.activity.VipActivity;
import com.xlkj.beautifulpicturehouse.module.video.apiservice.VideoApiService;

import java.io.File;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Subscription;
import rx.functions.Action1;


/**
 * MineFragment
 */
public class MineFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "-->MineFragment-";
    private View mView;
    private RelativeLayout rlHeadLayout;
    private RelativeLayout rlFollow;
    private RelativeLayout rlCollection;
    private RelativeLayout rlDownload;
    private RelativeLayout rlVipBuy;
    private RelativeLayout rlAboutUs;
    private RelativeLayout rlMyFootStay;
    private RelativeLayout rlMyVip;
    //用户头像
    private CircleImageView ivHead;
    //本地用户信息
    private LocalUserInfo mUserInfo;
    //用户昵称
    private TextView tvName;
    //用户id
    private TextView tvUid;
    //本地获取的UID
    private String mUid;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        mView = inflater.inflate(R.layout.fragment_mine, container, false);
        mView = inflater.inflate(R.layout.fragment_mine2, container, false);
        mContext = BeautyApplication.appContext;
//        boolean user_info = FileUtils.saveCachDataToFile(LoginActivity.this, "user_info", localUserInfo);
        getLocalUserInfo();
        initView();
        testInitRxBus();
        setHeadName();
        return mView;
    }

    private void setHeadName() {
        if (mUserInfo != null) {
            Glide.with(mContext).load(mUserInfo.getHeadUrl()).into(ivHead);
            tvName.setText(mUserInfo.getNickName());
        } else {
            Log.e(TAG,"setHeadName本地获取用户信息为空");
        }
        if (mUid != null && !mUid.equals("")) {
            tvUid.setText("ID:"+mUid);
        }
    }

    /**
     * 获取本地用户信息
     */
    private void getLocalUserInfo() {
        mUid = PreferencesUtils.getString(mContext, "uid");
        mUserInfo = (LocalUserInfo) FileUtils.getCacheDataFromFile(mContext, "user_info");
    }

    /**
     * 事件总线接受消息
     */
    private void testInitRxBus() {
        //接收登录成功后的消息 做相应显示处理
        Subscription subscription = RxBus.getInstance().doSubscribe(AllUserInfo.class, new Action1<AllUserInfo>() {
            @Override
            public void call(AllUserInfo mLocalUserInfo) {
                //设置用户信息
                setUserInfo(mLocalUserInfo);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e(TAG, "是否接受数据22：" + throwable.getMessage());
            }
        });
        RxBus.getInstance().addSubscription(this, subscription);

        //接收退出登录的消息
        Subscription subscription2 = RxBus.getInstance().doSubscribe(LogoutBean.class, new Action1<LogoutBean>() {
            @Override
            public void call(LogoutBean mLocalUserInfo) {
                //删除用户信息
                Glide.with(mContext).load("").placeholder(R.drawable.shipin_remen_wuguanzhu).crossFade().into(ivHead);
                tvName.setText("点击登录");
                tvUid.setText("ID:"+"");
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e(TAG, "是否接受数据22：" + throwable.getMessage());
            }
        });
        RxBus.getInstance().addSubscription(this, subscription2);
    }

    /**
     * 设置用户信息
     * @param mLocalUserInfo
     */
    private void setUserInfo(AllUserInfo mLocalUserInfo) {
        Glide.with(mContext).load(mLocalUserInfo.getHeadUrl())/*.placeholder(R.drawable.shipin_remen_wuguanzhu).crossFade()*/.into(ivHead);
        tvName.setText(mLocalUserInfo.getNickName());
        tvUid.setText("ID:"+mLocalUserInfo.getuId());
    }

    /**
     * 初始化控件
     */
    private void initView() {
        //昵称
        tvName = (TextView) mView.findViewById(R.id.tv_name_fm);
        //用户id
        tvUid = (TextView) mView.findViewById(R.id.tv_id_fm);
        //用户头像
        ivHead = (CircleImageView) mView.findViewById(R.id.iv_head_fm);
        rlHeadLayout = (RelativeLayout) mView.findViewById(R.id.rl_head_fm);
        rlFollow = (RelativeLayout) mView.findViewById(R.id.rl_my_follow_fm);
        rlCollection = (RelativeLayout) mView.findViewById(R.id.rl_my_collection_fm);
        rlDownload = (RelativeLayout) mView.findViewById(R.id.rl_my_download_fm);
        rlVipBuy = (RelativeLayout) mView.findViewById(R.id.rl_vip_dao_fm);
        rlAboutUs = (RelativeLayout) mView.findViewById(R.id.rl_about_us_fm);
        rlMyFootStay = (RelativeLayout) mView.findViewById(R.id.rl_my_foot_stay_fm2);
        rlMyVip = (RelativeLayout) mView.findViewById(R.id.rl_my_download_fm2);
        rlMyVip.setOnClickListener(this);
        rlHeadLayout.setOnClickListener(this);
        rlFollow.setOnClickListener(this);
        rlCollection.setOnClickListener(this);
        rlDownload.setOnClickListener(this);
        rlVipBuy.setOnClickListener(this);
        rlAboutUs.setOnClickListener(this);
        rlMyFootStay.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消订阅 避免内存溢出
        RxBus.getInstance().unSubscribe(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.rl_my_download_fm2:
                jump2myVip();
                break;
            case R.id.rl_my_foot_stay_fm2:
                jump2myFootStay();
                break;
            case R.id.rl_head_fm:
                jumpToLoginOrUserInfo();
                break;
            case R.id.rl_my_follow_fm:
                jump2follow();
                break;
            case R.id.rl_my_collection_fm:
                jump2collection();
                break;
            case R.id.rl_my_download_fm:
                jump2download();
                break;
            case R.id.rl_vip_dao_fm:
//                jump2buyVip();
                jump2buy();
//                uploadfile();
                break;

            case R.id.rl_about_us_fm:
                jump2aboutUs();
                break;

            default:
                break;
        }
    }

    private void uploadfile() {
        Retrofit retrofit = RetrofitSingleton.getInstance(HostType.MTW_UPLOAD).getRetrofit();
        VideoApiService apiService = retrofit.create(VideoApiService.class);
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/meinvwu/mtw5.jpg";
        File file = new File(path);//访问手机端的文件资源，保证手机端sdcdrd中必须有这个文件
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        final MultipartBody.Part body = MultipartBody.Part.createFormData("aFile", file.getName(), requestFile);
//        String descriptionString = "This is a description";
        String descriptionString = FileUtils.getFileMd52(file);
        RequestBody description =
                RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);
        Call<ResponseBody> call = apiService.upload(description,body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(getContext(), "上传成功11", Toast.LENGTH_SHORT).show();
                ResponseBody body1 = response.body();
                Log.e(TAG,body1.toString());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "上传失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void jump2buy() {
        buy();
    }

    /**
     * 收款-不啰嗦
     */
    private void buy() {
        try {
            final VipPayToastDialog vipPayToastDialog = new VipPayToastDialog(getContext(), "");
            vipPayToastDialog.show();
            vipPayToastDialog.setOnCancelListener(new VipPayToastDialog.OnCancelListener() {
                @Override
                public void cancelMethod() {
                    vipPayToastDialog.dismiss();
                }
            });
            vipPayToastDialog.setOnUpdateListener(new VipPayToastDialog.OnUpdateListener() {
                @Override
                public void updateMethod() {
                    // 2018/1/31 立即开通
                    final BottomDialog payDialog = new BottomDialog(getContext());
                    payDialog.show();
                    vipPayToastDialog.dismiss();
                    payDialog.setTitleVisible(false);
                    payDialog.setCenterView(R.layout.dialog_bottom_donate_pay);
                    payDialog.setBottomBtnsLayVisible(false);
                    RelativeLayout alipay = (RelativeLayout) payDialog.findViewById(R.id.rl_alipay_dbdp);
                    RelativeLayout weixinpay = (RelativeLayout) payDialog.findViewById(R.id.rl_weixin_pay_dbdp);
                    RelativeLayout cancle = (RelativeLayout) payDialog.findViewById(R.id.rl_cancel_dbdp);
                    cancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            payDialog.dismiss();
                        }
                    });
                    weixinpay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            payDialog.dismiss();
                            showExitDialog();
                        }
                    });
                    alipay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            payDialog.dismiss();
                            donateAlipay();
                        }
                    });

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"jump2buy异常");
        }
    }

    /**
     * 显示微信支付提示对话框
     */
    private void showExitDialog() {
        try {
            String msg = "点击确定跳转到微信二维码扫描界面后："+"\n" + "1.点击又上角菜单按钮\n"+"2.点击从相册选取二维码\n"+"3.选择第一张二维码图片即可\n";
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            AlertDialog dialog = builder.create();
            builder.setTitle("微信打赏操作步骤")
                    .setMessage(msg)
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            donateWeixin();
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"showExitDialog异常");
        }
    }

    /**
     * 需要提前准备好 微信收款码 照片，可通过微信客户端生成
     */
    private void donateWeixin() {
        try {
            InputStream weixinQrIs = getResources().openRawResource(R.raw.wechatpaycode);
            String qrPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "AndroidDonateSample" + File.separator +
                    "wechatpaycode.jpg";
            WeiXinDonate.saveDonateQrImage2SDCard(qrPath, BitmapFactory.decodeStream(weixinQrIs));
            WeiXinDonate.donateViaWeiXin(getActivity(), qrPath);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"donateWeixin异常");
        }
    }

    /**
     * 支付宝支付
     * payCode 收款码后面的字符串；例如：收款二维码里面的字符串为 https://qr.alipay.com/stx00187oxldjvyo3ofaw60 ，则
     * payCode = stx00187oxldjvyo3ofaw60
     * 注：不区分大小写
     */
    private void donateAlipay() {
        try {
            boolean hasInstalledAlipayClient = AlipayDonate.hasInstalledAlipayClient(getActivity());
            if (hasInstalledAlipayClient) {
                AlipayDonate.startAlipayClient(getActivity(), Constant.ALI_PAY_CODE_Z);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"donateAlipay异常");
        }
    }

    /**
     * 到我的VIP
     */
    private void jump2myVip() {
        String uid = PreferencesUtils.getString(mContext, "uid");
        if (uid != null && !uid.isEmpty() && !uid.equals("")) {
            Toast.makeText(mContext, "敬请期待~", Toast.LENGTH_SHORT).show();
        } else {
            //到登录页
            testWXLogin();
        }
    }


    /**
     * 需要用UID来判断登录还是到用户信息页
     */
    private void jumpToLoginOrUserInfo() {
        String uid = PreferencesUtils.getString(mContext, "uid");
        if (uid != null && !uid.isEmpty() && !uid.equals("")) {
            //到用户详情页
            jump2userInfo();
        } else {
            //到登录页
            testWXLogin();
        }
    }

    /**
     * 测试微信登录
     */
    private void testWXLogin() {
        jump(LoginActivity.class);
    }

    /**
     * 到我的足迹页面
     */
    private void jump2myFootStay() {
        String uid = PreferencesUtils.getString(mContext, "uid");
        if (uid != null && !uid.isEmpty() && !uid.equals("")) {
            jump(MyFootStayActivity.class);
        } else {
            //到登录页
            testWXLogin();
        }
    }

    /**
     * 到用户信息页
     */
    private void jump2userInfo() {
        String uid = PreferencesUtils.getString(mContext, "uid");
        if (uid != null && !uid.isEmpty() && !uid.equals("")) {
            jump(UserInfoActivity.class);
        } else {
            //到登录页
            testWXLogin();
        }
    }

    /**
     * 跳转的方法
     * @param mClass
     */
    private void jump(Class mClass) {
        Intent intent = new Intent(mContext, mClass);
        //Calling startActivity() from outside of an Activity context requires the FLAG_ACTIVITY_NEW_TASK flag. Is this really what you want?
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    /**
     * 到关于我们
     */
    private void jump2aboutUs() {
        jump(AboutUsActivity.class);
    }

    /**
     * 到购买VIP
     */
    private void jump2buyVip() {
        String uid = PreferencesUtils.getString(mContext, "uid");
        if (uid != null && !uid.isEmpty() && !uid.equals("")) {
            jump(VipActivity.class);
        } else {
            //到登录页
            testWXLogin();
        }
    }

    /**
     * 到我的下载
     */
    private void jump2download() {
        String uid = PreferencesUtils.getString(mContext, "uid");
        if (uid != null && !uid.isEmpty() && !uid.equals("")) {
            jump(MyDownLoadActivity.class);
        } else {
            //到登录页
            testWXLogin();
        }
    }

    /**
     * 到我的收藏
     */
    private void jump2collection() {
        String uid = PreferencesUtils.getString(mContext, "uid");
        if (uid != null && !uid.isEmpty() && !uid.equals("")) {
            jump(MyCollectionActivity.class);
        } else {
            //到登录页
            testWXLogin();
        }
    }

    /**
     * 跳转到我的关注
     */
    private void jump2follow() {
        String uid = PreferencesUtils.getString(mContext, "uid");
        if (uid != null && !uid.isEmpty() && !uid.equals("")) {
            jump(MyFollowActivity.class);
        } else {
            //到登录页
            testWXLogin();
        }
    }

}
