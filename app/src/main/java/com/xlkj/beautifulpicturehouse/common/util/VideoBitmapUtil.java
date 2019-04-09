package com.xlkj.beautifulpicturehouse.common.util;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by Administrator on 2018/1/23.
 */

public class VideoBitmapUtil {
    public static Bitmap getVideoThumbnail(String url) {
        Bitmap bitmap = null;
        //MediaMetadataRetriever 是android中定义好的一个类，提供了统一
        //的接口，用于从输入的媒体文件中取得帧和元数据；
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //（）根据文件路径获取缩略图
            //retriever.setDataSource(filePath);
            retriever.setDataSource(url, new HashMap());
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        Log.v("bitmap", "bitmap=" + bitmap);
        return bitmap;
    }
}
