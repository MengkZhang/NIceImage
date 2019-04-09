package com.xlkj.beautifulpicturehouse.module.home.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/29.
 */

public class HomePicGoodChoiceReqBean implements Serializable {
//    http://api.app.27270.com/api/index.php?action=typeList&typeId=1&page=1
    private String action;
    private String typeId;
    private String page;
    private String uId;

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

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

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
