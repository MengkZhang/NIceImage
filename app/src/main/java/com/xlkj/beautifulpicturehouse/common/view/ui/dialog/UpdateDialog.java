package com.xlkj.beautifulpicturehouse.common.view.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xlkj.beautifulpicturehouse.R;


/**
 * Created by zhang on 2017/8/30.
 * 自定义升级的的dialog
 */
public class UpdateDialog extends Dialog {

    private Context mContext;
    private RelativeLayout tvUpdate;
    private RelativeLayout tvCancel;
    private TextView tvNewVersionDesc;
    private String text;


    public UpdateDialog(Context context, String text) {
        super(context, R.style.loadingDialog);
        this.mContext = context;
        this.text = text;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.dialog_update_layout);
        Window dialogWindow = this.getWindow();
        if (dialogWindow != null) {
            initView(dialogWindow);
            WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics outMetrics = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(outMetrics);
            int width = outMetrics.widthPixels;
            int height = outMetrics.heightPixels;

            WindowManager.LayoutParams params = dialogWindow.getAttributes();
            dialogWindow.setGravity(Gravity.CENTER);
            params.width = width;
            params.height = height;
            dialogWindow.setAttributes(params);

            this.setCanceledOnTouchOutside(false);

        }
    }

    /**
     * 初始化数据
     * @param dialogWindow
     */
    private void initView(Window dialogWindow) {

        /**新版本特性*/
        tvNewVersionDesc = (TextView)dialogWindow.findViewById(R.id.tv_new_version_desc);
        try {

            if (text != null) {
                tvNewVersionDesc.setText(text);
            }

        } catch (Exception e) {
            tvNewVersionDesc.setText("修复了部分bug");
            e.printStackTrace();
        }

        tvUpdate = (RelativeLayout) dialogWindow.findViewById(R.id.rl_update_update_dialog);
        tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mOnUpdateListener != null) {
                    mOnUpdateListener.updateMethod();
                }

            }
        });

        tvCancel = (RelativeLayout) dialogWindow.findViewById(R.id.rl_cancel_update_dialog);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnCancelListener != null) {
                    mOnCancelListener.cancelMethod();
                }
            }
        });
    }

    public interface OnCancelListener {
        void cancelMethod();
    }

    private OnCancelListener mOnCancelListener;

    public void setOnCancelListener(OnCancelListener onCancelListener) {
        mOnCancelListener = onCancelListener;
    }

    public interface OnUpdateListener {
        void updateMethod();
    }

    private OnUpdateListener mOnUpdateListener;

    public void setOnUpdateListener(OnUpdateListener onUpdateListener) {
        mOnUpdateListener = onUpdateListener;
    }


}
