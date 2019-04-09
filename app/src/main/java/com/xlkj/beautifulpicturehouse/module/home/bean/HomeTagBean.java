package com.xlkj.beautifulpicturehouse.module.home.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/28.
 */

public class HomeTagBean implements Serializable {
    private String imageUrl;
    private String tagName;
    private String tagId;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }
    //       "imageUrl":"http://api.app.27270.com/img/tag/tag_1.png",
//               "tagName":"大尺度人体艺",
//               "tagId":"2349"
}
