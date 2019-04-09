package com.xlkj.beautifulpicturehouse.common.manager;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.common.http.HostType;
import com.xlkj.beautifulpicturehouse.common.http.RetrofitSingleton;
import com.xlkj.beautifulpicturehouse.common.rxbus.RxBus;
import com.xlkj.beautifulpicturehouse.common.util.PreferencesUtils;
import com.xlkj.beautifulpicturehouse.common.view.ui.dialog.share.BottomDialog;
import com.xlkj.beautifulpicturehouse.module.home.apiservice.HomeApiService;
import com.xlkj.beautifulpicturehouse.module.home.bean.CommentSubmitReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.CommentSubmitResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.RxPostCommentBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2018/1/19.
 * 发表评论的单例模式管理者
 */

public class CommentSubmitManager {

    private CommentSubmitManager() {

    }

    public static CommentSubmitManager single = new CommentSubmitManager();

    public static CommentSubmitManager getInstance() {
        return single;
    }

    public void setCommentSubmit(Context mContext,String itemId,String isPicture,CommentSubmitCallBack commentSubmitCallBack) {
        BottomDialog bottomDialog = new BottomDialog(mContext);
        initView(mContext,bottomDialog,itemId,isPicture,commentSubmitCallBack);
    }

    private void initView(final Context mContext, final BottomDialog commentDialog, final String itemId, final String isPicture, final CommentSubmitCallBack commentSubmitCallBack) {
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
        rlSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = etContent.getText().toString();
                if (!content.equals(" ")&& !content.equals("") && content != null && !content.isEmpty()) {
                    //网络请求
                    Retrofit retrofit = RetrofitSingleton.getInstance(HostType.MTW_INFO).getRetrofit();
                    HomeApiService apiService = retrofit.create(HomeApiService.class);
                    CommentSubmitReqBean bean = new CommentSubmitReqBean();
                    String uid = PreferencesUtils.getString(mContext, "uid");
                    String typeId = itemId;
                    bean.setAction("comment");
                    bean.setContent(content);
                    bean.setIsPicture(isPicture);
                    bean.setTypeId(typeId);
                    bean.setUserId(uid);
                    Call<CommentSubmitResBean> call = apiService.requestCommentSubmit(bean.getAction(), bean.getUserId(), bean.getTypeId(), bean.getIsPicture(), bean.getContent());
                    call.enqueue(new Callback<CommentSubmitResBean>() {
                        @Override
                        public void onResponse(Call<CommentSubmitResBean> call, Response<CommentSubmitResBean> response) {
                            if (response != null && response.body() != null) {
                                commentDialog.dismiss();
                                commentSubmitCallBack.onSuccess(response.body());
                            }
                        }

                        @Override
                        public void onFailure(Call<CommentSubmitResBean> call, Throwable t) {
                            commentDialog.dismiss();
                            commentSubmitCallBack.onFailed("提交评论失败");
                        }
                    });
                } else {
                    Toast.makeText(mContext, "请输入评论内容", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

    public interface CommentSubmitCallBack {
        void onFailed(String msg);
        void onSuccess(CommentSubmitResBean mCommentSubmitResBean);
    }


}
