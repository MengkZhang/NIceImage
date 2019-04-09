package com.xlkj.beautifulpicturehouse.module.home.bean;

import com.xlkj.beautifulpicturehouse.common.view.ui.dialog.share.BottomDialog;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/15.
 * 在对话框中点击评论 给picDetailActivity发送请求发表评论的消息
 */

public class RxPostCommentBean implements Serializable {
    private boolean isSubmitComment;
    private String content;
    private BottomDialog mBottomDialog;

    public BottomDialog getBottomDialog() {
        return mBottomDialog;
    }

    public void setBottomDialog(BottomDialog bottomDialog) {
        mBottomDialog = bottomDialog;
    }

    public boolean isSubmitComment() {
        return isSubmitComment;
    }

    public void setSubmitComment(boolean submitComment) {
        isSubmitComment = submitComment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
