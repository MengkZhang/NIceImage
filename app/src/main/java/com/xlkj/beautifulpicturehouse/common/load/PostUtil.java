package com.xlkj.beautifulpicturehouse.common.load;

import android.os.Handler;
import android.os.Looper;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;

/**
 * 设置请求超时时间
 */
public class PostUtil {
    private static final int DELAY_TIME = 0;

    public static void postCallbackDelayed(final LoadService loadService, final Class<? extends Callback> clazz) {
        postCallbackDelayed(loadService, clazz, DELAY_TIME);
    }

    public static void postCallbackDelayed(final LoadService loadService, final Class<? extends Callback> clazz, long
            delay) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                loadService.showCallback(clazz);
            }
        }, delay);
    }

    public static void postSuccessDelayed(final LoadService loadService) {
        postSuccessDelayed(loadService, DELAY_TIME);
    }

    public static void postSuccessDelayed(final LoadService loadService, long delay) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                loadService.showSuccess();
            }
        }, delay);
    }
}
