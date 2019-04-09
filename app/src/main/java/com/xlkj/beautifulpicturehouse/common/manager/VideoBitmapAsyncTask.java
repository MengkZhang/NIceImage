package com.xlkj.beautifulpicturehouse.common.manager;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by Administrator on 2018/1/23.
 */

public class VideoBitmapAsyncTask extends AsyncTask<Void,Integer,Bitmap> {

    private String videoUrl;

    public VideoBitmapAsyncTask(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    @Override
    protected Bitmap doInBackground(Void... voids) {
        return getVideoThumbnail(videoUrl);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
    }

    public Bitmap getVideoThumbnail(String url) {
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
