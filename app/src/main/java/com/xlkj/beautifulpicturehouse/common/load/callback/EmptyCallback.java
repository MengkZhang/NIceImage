package com.xlkj.beautifulpicturehouse.common.load.callback;

import com.kingja.loadsir.callback.Callback;
import com.xlkj.beautifulpicturehouse.R;

/**
 * Created by Administrator on 2017/12/21.
 * 没有数据的布局回调
 */

public class EmptyCallback extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.layout_empty;
    }

}
