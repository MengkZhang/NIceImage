package com.xlkj.beautifulpicturehouse.common.load.callback;

import android.content.Context;
import android.view.View;

import com.kingja.loadsir.callback.Callback;
import com.xlkj.beautifulpicturehouse.R;

/**
 * Created by Administrator on 2017/12/21.
 * 加载中的布局回调
 */

public class LoadingCallback extends Callback{
    @Override
    protected int onCreateView() {
        return R.layout.layout_loading;
    }

    @Override
    public boolean getSuccessVisible() {
        return super.getSuccessVisible();
    }

    @Override
    protected boolean onReloadEvent(Context context, View view) {
        return true;
    }
}
