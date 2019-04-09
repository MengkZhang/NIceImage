package com.xlkj.beautifulpicturehouse.common.view.ui.dialog.share;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.common.constant.BeautyContent;
import com.xlkj.beautifulpicturehouse.common.rxbus.RxBus;
import com.xlkj.beautifulpicturehouse.module.home.bean.RxPostCommentBean;

import java.lang.reflect.Field;


/**
 * dp、sp 转换为 px 的工具类
 *
 * @author fxsky 2012.home_1.12
 */
public class DisplayUtil {
    public static final String TAG = "-->>DisplayUtil";
    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取状态栏的高度
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 动态获取控件的高度
     * @param view：要测量的控件
     * @return
     */
    public static int getViewHeight(final View view) {
        try {
            final int[] measuredHeight = {0};
            ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
            viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    measuredHeight[0] = view.getMeasuredHeight();
                    return true;
                }
            });
            return measuredHeight[0];
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"getViewHeight获取控件高度异常");
            return 0;
        }
    }

    /**
     * 获取屏幕高度
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        try {
            int height = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight();
            return height;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"获取屏幕高度失败");
            return 800;
        }
    }

    /**
     * 切换软键盘的状态 如当前为收起变为弹出,若当前为弹出变为收起
     */
    public static void toggleInput(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0,
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 强制隐藏输入法键盘
     */
    public static void hideInput(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 显示分享的Dialog
     *
     * @param context
     */
    public static void showShareDialog(final Context context,
                                       final String shareTitle, final String shareContent,
                                       final String shareUrl) {
        final BottomDialog shareDialog = new BottomDialog(context);
        shareDialog.show();
        shareDialog.setTitleVisible(false);
        shareDialog.setCenterView(R.layout.dialog_bottom_share);
        shareDialog.setBottomBtnsLayVisible(false);
        shareDialog.setBottomBtnsLay1Visible(true);
        shareDialog.setBottomBtnText("取消");

        shareDialog.setBottomBtnOnclickLinstener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                shareDialog.dismiss();
            }
        });
        TextView QQFriendsBtn = (TextView) shareDialog
                .findViewById(R.id.share_qq_item);
        QQFriendsBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                shareDialog.dismiss();
                ToolsUtil.share2Qq(context, shareTitle, shareContent, shareUrl, 0);
            }
        });
        TextView WeixinFriendBtn = (TextView) shareDialog
                .findViewById(R.id.share_weixin_friends_item);
        WeixinFriendBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                shareDialog.dismiss();
                ToolsUtil.share2weixin(context, 0, shareTitle, shareContent,
                        shareUrl);

            }
        });
        TextView weixinFriendCircle = (TextView) shareDialog
                .findViewById(R.id.share_weixin_friends_circle_item);
        weixinFriendCircle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                shareDialog.dismiss();
                ToolsUtil.share2weixin(context, 1, shareTitle, shareContent,
                        shareUrl);
            }
        });

        TextView qZoneBtn = (TextView) shareDialog
                .findViewById(R.id.share_qzone_item);
        qZoneBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                shareDialog.dismiss();
                ToolsUtil.share2Qq(context, shareTitle, shareContent, shareUrl, 1);

            }
        });
        TextView shareMoreBtn = (TextView) shareDialog
                .findViewById(R.id.share_more_item);
        shareMoreBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                shareDialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, shareContent);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(Intent.createChooser(intent, shareTitle));
            }
        });
    }

    /**
     * 显示评论的dialog
     * @param context
     */
    public static void showComment(final Context context) {
        final BottomDialog commentDialog = new BottomDialog(context);
        commentDialog.show();
        commentDialog.setTitleVisible(false);
        commentDialog.setCenterView(R.layout.dialog_bottom_comment);
        commentDialog.setBottomBtnsLayVisible(false);
        //输入的内容
        final EditText etContent = (EditText) commentDialog.findViewById(R.id.et_comment_context_dbc);
        //提交评论
        RelativeLayout rlSubmit = (RelativeLayout) commentDialog.findViewById(R.id.rl_item_dbc);
        //只用下面这一行弹出对话框时需要点击输入框才能弹出软键盘
        commentDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        //加上下面这一行弹出对话框时软键盘随之弹出
        commentDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        rlSubmit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = etContent.getText().toString();
                if (!content.equals(" ")&& !content.equals("") && content != null && !content.isEmpty()) {
                    //给详情页发送消息 让他请求数据 发表评论
                    RxPostCommentBean bean = new RxPostCommentBean();
                    bean.setContent(content);
                    bean.setSubmitComment(true);
                    bean.setBottomDialog(commentDialog);
                    RxBus.getInstance().post(bean);
                } else {
                    Toast.makeText(context, "请输入评论内容", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

    }

    /**
     * 显示支付的Dialog
     *
     * @param context
     */
    public static void showPayDialog(final Context context, String price) {
        final BottomDialog shareDialog = new BottomDialog(context);
        shareDialog.show();
        //shareDialog.setCancelable(false);
        shareDialog.setTitleVisible(false);
        shareDialog.setCenterView(R.layout.dialog_bottom_pay);
        shareDialog.setBottomBtnsLayVisible(false);
        //价格
        TextView tv_price_dbp = (TextView) shareDialog.findViewById(R.id.tv_price_dbp);
        TextView mPayOneTV = (TextView) shareDialog.findViewById(R.id.tv_pay_one);
        TextView mPaySvipTV = (TextView) shareDialog.findViewById(R.id.tv_pay_svip);
        tv_price_dbp.setText("￥"+price);
        mPayOneTV.setText(price+"元，黄金VIP");
        mPayOneTV.setOnClickListener(new OnClickListener() {

            private Intent intent;

            @Override
            public void onClick(View view) {
//                intent = new Intent(context, VipRewardActivity.class);
//                intent.putExtra("isMine",false);
//                intent.putExtra("isAloneBuy",true);
//                context.startActivity(intent);
            }
        });
        mPaySvipTV.setOnClickListener(new OnClickListener() {

            private Intent intent;

            @Override
            public void onClick(View v) {
//                intent = new Intent(context, VipRewardActivity.class);
//                intent.putExtra("isMine",false);
//                intent.putExtra("isAloneBuy",false);
//                context.startActivity(intent);
            }
        });
    }
}