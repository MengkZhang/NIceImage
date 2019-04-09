package com.xlkj.beautifulpicturehouse.common.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017/12/26.
 * 保存图片到相册的帮助类
 */

public class SaveImageUtils {
    public static void saveImageToGallery(Context context, Bitmap bmp, String imageId) {
        // 首先保存图片
        String picFileName = "meinvwu";
        File appDir = new File(Environment.getExternalStorageDirectory(), picFileName);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
//        String fileName = System.currentTimeMillis() + ".jpg";
        String fileName = "mtw_" + imageId + ".jpg";
        File file = new File(appDir, fileName);
        //如果文件存在 则不再执行下载操作
        if (file.exists()) {
            Toast.makeText(context, "图片已成功保存到相册的" + picFileName + "路径下", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context, "文件未发现", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "保存出错了...", Toast.LENGTH_SHORT).show();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context, "保存出错了...", Toast.LENGTH_SHORT).show();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getPath())));
        Toast.makeText(context, "图片已成功保存到相册的" + picFileName + "路径下", Toast.LENGTH_SHORT).show();

    }

}
