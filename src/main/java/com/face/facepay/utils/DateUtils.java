package com.face.facepay.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String dateToString(Date date){
        return simpleDateFormat.format(date);
    }

    /**
     * 获取绝对秒数
     * @param date
     * @return
     */
    public static long getAbsoluteSeconds(Date date) throws ParseException {
        String dateStr="1970-01-01 08:00:00";
        Object d1=simpleDateFormat.parse(dateStr).getTime();
        Object t1=date.getTime();
        long d1time=Long.parseLong(d1.toString())/1000;
        long t1time=Long.parseLong(t1.toString())/1000;
        return t1time-d1time;
    }
}
