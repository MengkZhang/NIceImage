package com.xlkj.beautifulpicturehouse.common.http;

/**
 * Created by Administrator on 2017/12/20.
 * 服务器跟地址
 */

public class HttpMethods {
    //测试环境(美女屋-公司自己的)  http://api.app.27270.com/api/index.php?action=getInfoList
    public static final String BASE_URL = "http://api.app.27270.com/api/";

    //文件上传第一步-跟地址
    public static final String FILE_UPLOAD_BASE_URL = "http://t2.hddhhn.com/video/";

    // TODO: 2017/12/20 内侧环境 魅果圈的host
    //http://s.3987.com/beauty-api-1.1/Public/demo/index.php?canal=original&userId=5275&token=0e7a8963241611da87cb90d14cbf2a84&plat=38&service=VipPicture.VipNewPictureList&page=1&pageSize=20&check=b0fe804a86c9c15d9ce434f5a58ba018
    public static final String PIC_BASE_URL = "http://s.3987.com/beauty-api-1.1/Public/demo/";

    // TODO: 2017/12/22 测试的视频地址 掌上远景熊猫视频壁纸项目的跟地址
    public static final String VIDEO_BASE_URL = "http://vs.zsyj.com.cn/";

    //微信登录访问官网第一步：根据code获取token和openId的跟地址
    public static final String WE_CHAT1_BASE_URL = "https://api.weixin.qq.com/sns/oauth2/";


    // TODO: 2017/12/20 公测环境
    // TODO: 2017/12/20 生产环境

    /**
     * 获取对应的host
     *
     * @param hostType host类型
     * @return host
     */
    public static String getHost(int hostType) {
        switch (hostType) {
            //美图屋
            case HostType.MTW_INFO:
                return HttpMethods.BASE_URL;

            //魅果圈
            case HostType.TEST_PIC:
                return HttpMethods.PIC_BASE_URL;

            //掌上愿景
            case HostType.TEST_VIDEO:
                return HttpMethods.VIDEO_BASE_URL;

            //微信登录访问官网第一步：根据code获取token和openId
            case HostType.WE_CHAT1:
                return HttpMethods.WE_CHAT1_BASE_URL;

            //美图屋-文件上传
            case HostType.MTW_UPLOAD:
                return HttpMethods.FILE_UPLOAD_BASE_URL;

        }
        return "";
    }


}
