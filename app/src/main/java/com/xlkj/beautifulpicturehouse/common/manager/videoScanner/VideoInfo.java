package com.xlkj.beautifulpicturehouse.common.manager.videoScanner;

import java.io.Serializable;

/**
 * Created by 1 on 2017/10/9.
 */
public class VideoInfo implements Serializable {
    private String displayName;
    private String path;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
