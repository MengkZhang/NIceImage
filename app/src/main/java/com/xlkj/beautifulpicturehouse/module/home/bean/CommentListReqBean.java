package com.xlkj.beautifulpicturehouse.module.home.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/15.
 * 评论列表请求参数bean
 */

public class CommentListReqBean implements Serializable {
//    http://api.app.27270.com/api/index.php?action=commentList&typeId=169781&ispicture=0&page=1
    private String action;
    private String typeId;
    private String ispicture;
    private String page;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getIspicture() {
        return ispicture;
    }

    public void setIspicture(String ispicture) {
        this.ispicture = ispicture;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
