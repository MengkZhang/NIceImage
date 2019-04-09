package com.xlkj.beautifulpicturehouse.common.load.callback;


import com.kingja.loadsir.callback.Callback;
import com.xlkj.beautifulpicturehouse.R;


/**
 * 数据加载失败的布局
 */

public class ErrorCallback extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.layout_error;
    }
}
