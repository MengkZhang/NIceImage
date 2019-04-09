package com.xlkj.beautifulpicturehouse.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 1 on 2017/10/18.
 */
public class TimeUtil {
    public static boolean compareTime(String serverTime, long systemTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-DD");
        Date date;
        try {
            date = sdf.parse(serverTime);
            Date date2 = new Date();
            return date.before(date2);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String formatTime() {
        long time= System.currentTimeMillis();//long now = android.os.SystemClock.uptimeMillis();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1=new Date(time);
        String t1=format.format(d1);
        return t1;
    }
    public static long getTodayZero() {
        Date date = new Date();
        long l = 24 * 60 * 60 * 1000; //每天的毫秒数
        //date.getTime()是现在的毫秒数，它 减去 当天零点到现在的毫秒数（ 现在的毫秒数%一天总的毫秒数，取余。），理论上等于零点的毫秒数，不过这个毫秒数是UTC+0时区的。
        //减8个小时的毫秒值是为了解决时区的问题。
        return (date.getTime() - (date.getTime() % l) - 8 * 60 * 60 * 1000);
    }

    /**
     * 格式化当前时间到秒
     * @param time
     * @return
     */
    public static String getCurrentTime2SS(long time) {
        //php的时间戳是10位 java的时间戳是13位
        time = time * 1000;
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1=new Date(time);
        return format.format(d1);
    }

    /**
     * 把格式化的时间转换成时间戳
     * @param formatTime：格式化的时间
     * @return：时间戳
     */
    public static long getTimeCuo(String formatTime) {
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long dateTime = 0;
        try {
            Date date = format.parse(formatTime);
            dateTime = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateTime;
    }


    /**
     * 比较两个日期的大小，日期格式为yyyy-MM-dd
     *
     * @param str1 the first date
     * @param str2 the second date
     * @return true <br/>false
     */
    public static boolean isDateOneBigger(String str1, String str2) {
        boolean isBigger = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1 = null;
        Date dt2 = null;
        try {
            dt1 = sdf.parse(str1);
            dt2 = sdf.parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dt1.getTime() > dt2.getTime()) {
            isBigger = true;
        } else if (dt1.getTime() < dt2.getTime()) {
            isBigger = false;
        }
        return isBigger;
    }

    /**
     * 比较两个日期的大小，日期格式为yyyy-MM-dd
     *
     * @param str1 the first date
     * @param str2 the second date
     * @return true <br/>false
     */
    public static boolean isDate2Bigger(String str1, String str2) {
        boolean isBigger = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1 = null;
        Date dt2 = null;
        try {
            dt1 = sdf.parse(str1);
            dt2 = sdf.parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dt1.getTime() > dt2.getTime()) {
            isBigger = false;
        } else if (dt1.getTime() <= dt2.getTime()) {
            isBigger = true;
        }
        return isBigger;
    }
}
