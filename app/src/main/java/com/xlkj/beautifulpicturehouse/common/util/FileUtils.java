package com.xlkj.beautifulpicturehouse.common.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Environment;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;


public class FileUtils {


    public static final String SDCARD_PATH = Environment
            .getExternalStorageDirectory().getAbsolutePath() + File.separator;

    public static void mLog(String msg) {
//        Logger.e(msg);
    }

    public static String getSdcardPathOnSys() {
        try {
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                String path = Environment.getExternalStorageDirectory()
                        .getPath();
                return path;
            }

            File path = Environment.getExternalStorageDirectory()
                    .getParentFile();

            if (path.isDirectory()) {
                File[] files = path.listFiles();

                for (int i = 0; i < files.length; i++) {
                    if (files[i].isDirectory()) {
                        if (containsAny(files[i].getPath(), "sdcard")) {

                            if (files[i].list().length > 0) {
                                return files[i].getPath();
                            } else {
                            }
                        }
                    }
                }

            }
        } catch (Exception e) {
        }

        return "/mnt/sdcard";
    }

    /**
     * 判断文件是否存在
     * @param path 文件的路径
     * @return
     */
    public static boolean isExists(String path) {
        File file = new File(path);
        return file.exists();
    }

    public static boolean containsAny(String str, String searchChars) {
        return str.contains(searchChars);
    }


    public static void installApk(Context context, String apkPath) {
        mLog(apkPath);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(new File(apkPath)),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    public static boolean sdCardIsOk() {

        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    public static String copyAssetsFile(Context mContext, String fileName) {

        String pkName = mContext.getPackageName();

        String pwdDictionaryPath = "/data/data/" + pkName + "/mAssets/";
        String pwdDictionaryAllPath = pwdDictionaryPath + fileName;

        try {
            AssetManager asset = null;
            InputStream fis = null;

            File dir = new File(pwdDictionaryPath);
            if (!dir.exists())
                dir.mkdirs();

            File pwdFile = new File(pwdDictionaryAllPath);

            if (pwdFile.exists()) {
                pwdFile.delete();
            }

            if (StringUtils.isNullStr(fileName)) {
            } else {
                asset = mContext.getAssets();
                fis = asset.open(fileName);
            }

            pwdFile.createNewFile();

            FileOutputStream fos = new FileOutputStream(pwdFile);
            byte[] buffer = new byte[4096];
            int count = 0;

            while ((count = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, count);
            }

            fos.close();
            fis.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return pwdDictionaryAllPath;
    }

    public static String copyAssetsFile(Context mContext, String srcFileName,
                                        String desFileName) {

        String pkName = mContext.getPackageName();

        String pwdDictionaryPath = "/data/data/" + pkName + "/mAssets/";
        String pwdDictionaryAllPath = pwdDictionaryPath + desFileName;

        try {
            AssetManager asset = null;
            InputStream fis = null;

            File dir = new File(pwdDictionaryPath);
            if (!dir.exists())
                dir.mkdirs();

            File pwdFile = new File(pwdDictionaryAllPath);

            if (pwdFile.exists()) {
                pwdFile.delete();
            }

            if (StringUtils.isNullStr(srcFileName)) {
            } else {
                asset = mContext.getAssets();
                fis = asset.open(srcFileName);
            }

            pwdFile.createNewFile();

            FileOutputStream fos = new FileOutputStream(pwdFile);
            byte[] buffer = new byte[4096];
            int count = 0;

            while ((count = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, count);
            }

            fos.close();
            fis.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return pwdDictionaryAllPath;
    }

    public static String assetsToSdcard(Context mContext, String filePath,
                                        String srcFileName) {

        String pwdDictionaryPath = filePath;
        String pwdDictionaryAllPath = filePath + srcFileName;

        try {
            AssetManager asset = null;
            InputStream fis = null;

            File dir = new File(pwdDictionaryPath);
            if (!dir.exists())
                dir.mkdirs();

            File pwdFile = new File(pwdDictionaryAllPath);

            if (pwdFile.exists()) {
                pwdFile.delete();
            }


            if (StringUtils.isNullStr(srcFileName)) {
                return "";
            } else {
                asset = mContext.getAssets();
                fis = asset.open(srcFileName);
            }

            pwdFile.createNewFile();

            FileOutputStream fos = new FileOutputStream(pwdFile);
            byte[] buffer = new byte[4096];
            int count = 0;

            while ((count = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, count);
            }

            fos.close();
            fis.close();

        } catch (Exception e) {
        }

        return pwdDictionaryAllPath;
    }

    public static byte[] openAssets(Context context, String fileName) {

        byte[] tempByte = null;
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            tempByte = baos.toByteArray();
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tempByte;
    }

    /**
     * FileInputStreamתInputStream
     */
    public static InputStream getInputStream(FileInputStream fileInput) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = -1;
        InputStream inputStream = null;
        try {
            while ((n = fileInput.read(buffer)) != -1) {
                baos.write(buffer, 0, n);

            }
            byte[] byteArray = baos.toByteArray();
            inputStream = new ByteArrayInputStream(byteArray);
            return inputStream;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void delFile(String filePath) {
        try {
            File temp = new File(filePath);
            temp.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); // 删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete(); // 删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + File.separator + tempList[i]);// 先删除文件夹里面的文件
                delFolder(path + File.separator + tempList[i]);// 再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    /**
     * @return true if the apk can install,or false that the apk download
     * incomplete
     */
    public static boolean isApkCanInstall(Context mContext, String apkPath) {
        try {
            PackageManager pm = mContext.getPackageManager();
            PackageInfo info = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
            if (info != null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 保存缓存数据到文件
     *
     * @param context
     * @param fileName
     * @param object
     * @return
     */
    public static boolean saveCachDataToFile(Context context, String fileName,
                                             Object object) {
        if (object == null) {
            return false;
        }
        try {
            // 需要一个文件输出流和对象输出流；文件输出流用于将字节输出到文件，对象输出流用于将对象输出为字节
            FileOutputStream fout = context.openFileOutput(fileName + ".ser",
                    Activity.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fout);
            out.writeObject(object);
            out.close();
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 从文件里面获取缓存数据
     *
     * @param context
     * @param fileName
     * @return
     */
    public static Object getCacheDataFromFile(Context context, String fileName) {
        Object object = null;
        try {
            FileInputStream fin = context.openFileInput(fileName + ".ser");
            ObjectInputStream in = new ObjectInputStream(fin);
            object = in.readObject();
            fin.close();
            in.close();
        } catch (Exception e) {
            return object;
        }
        return object;
    }

    /**
     * 清除指定文件名的缓存
     *
     * @param context
     * @param fileName
     */
    public static void clearCacheDataToFile(Context context, String fileName) {
        File file = new File(context.getFilesDir(), fileName + ".ser");
        if (file.exists()) {
            file.delete();
        }
    }


    /**
     * 获取指定路径下的.mp4文件
     * @param fileAbsolutePath
     * @return
     */
    public static List<String> getVideoFileName(String fileAbsolutePath) {
        List<String> vecFile = new ArrayList<>();
        File file = new File(fileAbsolutePath);
        File[] subFile = file.listFiles();

        for (int iFileLength = 0; iFileLength < subFile.length; iFileLength++) {
            // 判断是否为文件夹
            if (!subFile[iFileLength].isDirectory()) {
                String filename = subFile[iFileLength].getName();
                // 判断是否为MP4结尾
                if (filename.trim().toLowerCase().endsWith(".mp4")) {
                    vecFile.add(filename);
                }
            }
        }
        return vecFile;
    }


    /**
     * FileChannel 获取文件的MD5值
     *
     * @param file 文件路径
     * @return md5
     */
    public static String getFileMd52(File file) {
        MessageDigest messageDigest;
        FileInputStream fis = null;
        FileChannel ch=null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            if (file == null) {
                return "";
            }
            if (!file.exists()) {
                return "";
            }
            fis = new FileInputStream(file);
            ch = fis.getChannel();
            int size = 1024 * 1024 * 10;
            long part = file.length() / size + (file.length() % size > 0 ? 1 : 0);
            System.err.println("文件分片数" + part);
            for (int j = 0; j < part; j++) {
                MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, j * size, j == part - 1 ? file.length() : (j + 1) * size);
                messageDigest.update(byteBuffer);
                byteBuffer.clear();
            }
            BigInteger bigInt = new BigInteger(1, messageDigest.digest());
            String md5 = bigInt.toString(16);
            while (md5.length() < 32) {
                md5 = "0" + md5;
            }
            return md5;
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                    fis = null;
                }
                if (ch!=null){
                    ch.close();
                    ch=null;
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return "";
    }



}
