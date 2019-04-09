package com.xlkj.beautifulpicturehouse.module.home.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/15.
 * 提交评论请求参数bean
 */

public class CommentSubmitReqBean implements Serializable {
//    http://api.app.27270.com/api/index.php?action=comment&userId=1&typeId=169781&isPicture=0&content=%E2%80%9C%E5%BE%88%E5%8E%89%E5%AE%B3%E2%80%9D
    private String action;
    private String userId;
    private String typeId;
    private String isPicture;
    private String content;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getIsPicture() {
        return isPicture;
    }

    public void setIsPicture(String isPicture) {
        this.isPicture = isPicture;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
