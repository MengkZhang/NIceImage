package com.xlkj.beautifulpicturehouse.common.view.ui.dialog.share;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.xlkj.beautifulpicturehouse.BeautyApplication;
import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.common.constant.Constant;

import java.util.ArrayList;

/**
 * Created by zxl on 2017/9/28 0028.
 */

public class ToolsUtil {

    /**
     * 分享到QQ
     */
    public static void share2Qq(final Context context, String title, String content, String url, int type) {
        Bundle bundle = new Bundle();
        // 这条分享消息被好友点击后的跳转URL。
        bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, url);
        // 分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_ SUMMARY不能全为空，最少必须有一个是有值的。
        bundle.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        // 分享的图片URL
        bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, Constant.QQ_SHARE_ICON);
        // 分享的消息摘要，最长50个字
        bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, content);
        // 手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
        // bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, "我在测试_appname");
        // 标识该消息的来源应用，值为应用名称+AppId。
        // bundle.putString(QQShare.SHARE_TO_QQ_KEY_TYPE, "星期几");
        if (type == 0) {
            BeautyApplication.getTencentApi().shareToQQ((Activity) context, bundle, new IUiListener() {
                @Override
                public void onComplete(Object o) {
                    Toast.makeText(context, "分享成功", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(UiError e) {
                    Toast.makeText(context,
                            "code:" + e.errorCode + ", msg:"
                                    + e.errorMessage + ", detail:" + e.errorDetail,
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancel() {

                }
            });
        } else if (type == 1) {
            bundle.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
            //下面这个必须加上  不然无法调动 qq空间
            ArrayList<String> imageUrls = new ArrayList<String>();
            imageUrls.add(Constant.QQ_SHARE_ICON);
            bundle.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
            Tencent.createInstance(Constant.QQ_APP_ID, context).shareToQzone((Activity) context, bundle, new IUiListener() {
                @Override
                public void onComplete(Object o) {
                    Toast.makeText(context, "分享到空间成功", Toast.LENGTH_SHORT).show();
                    Log.e("-->ToolsUtil","分享到空间成功啦");
                }

                @Override
                public void onError(UiError e) {
                    Toast.makeText(context,
                            "code:" + e.errorCode + ", msg:"
                                    + e.errorMessage + ", detail:" + e.errorDetail,
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancel() {

                }
            });
        }
    }

    /**
     * 分享到微信
     *
     * @param flag 0=分享给朋友，1=分享到朋友圈
     */
    public static void share2weixin(Context context, int flag, String title,
                                    String description, String url) {
        IWXAPI weiXinApi = BeautyApplication.getWeiXinApi();
        if (!weiXinApi.isWXAppInstalled()) {
            Toast.makeText(
                    context,
                    "请先安装微信",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webpage);

        msg.title = title;
        msg.description = description;
        Bitmap thumb = BitmapFactory.decodeResource(context.getResources(),
                R.mipmap.icon);
        msg.setThumbImage(thumb);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = flag;
        weiXinApi.sendReq(req);
    }
}
