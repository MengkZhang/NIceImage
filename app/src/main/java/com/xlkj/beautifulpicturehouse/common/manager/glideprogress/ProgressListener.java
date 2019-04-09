package com.xlkj.beautifulpicturehouse.common.manager.glideprogress;

/**
 * Created by Administrator on 2018/1/23.
 * 好的，现在自定义的拦截器已经启用了，接下来就可以开始去实现下载进度监听的具体逻辑了。首先新建一个ProgressListener接口，用于作为进度监听回调的工具，如下所示：
 */

public interface ProgressListener {
    void onProgress(int progress);
}
