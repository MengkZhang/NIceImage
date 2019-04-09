package com.xlkj.beautifulpicturehouse.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {


    /**
     * 对外提供getMD5(String)方法
     *
     * @return 加密失败返回""
     */
    public static String getMD5(String val) throws NoSuchAlgorithmException {

        byte[] hash;

        try {
            hash = MessageDigest.getInstance("MD5").digest(
                    val.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }

        try {
            StringBuilder hex = new StringBuilder(hash.length * 2);
            for (byte b : hash) {
                if ((b & 0xFF) < 0x10)
                    hex.append("0");
                hex.append(Integer.toHexString(b & 0xFF));
            }

            return hex.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
