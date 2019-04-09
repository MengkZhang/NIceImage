package com.xlkj.beautifulpicturehouse.common.http;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Administrator on 2017/12/21.
 * 请求数据host的类型<p>
 */

public class HostType {
    /**
     * 多少种Host类型
     */
    public static final int TYPE_COUNT = 5;

    /**
     * 美女屋地址：host
     */
    @HostTypeChecker
    public static final int MTW_INFO = 1;

    /**
     * 测试 魅果圈的 host
     */
    @HostTypeChecker
    public static final int TEST_PIC = 2;


    /**
     * 测试掌上愿景视频的host
     */
    @HostTypeChecker
    public static final int TEST_VIDEO = 3;

    /**
     * 微信登录官方跟地址：通过code获取token和openId
     */
    @HostTypeChecker
    public static final int WE_CHAT1 = 4;

    /**
     * 美图屋-文件上传
     */
    @HostTypeChecker
    public static final int MTW_UPLOAD = 5;



    /**
     * 替代枚举的方案，使用IntDef保证类型安全
     */
    @IntDef({MTW_INFO, TEST_PIC,TEST_VIDEO,WE_CHAT1,MTW_UPLOAD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface HostTypeChecker {

    }

}
