package com.face.facepay.utils;

import java.text.ParseException;
import java.util.Date;

public class OrderCodeUtils {
    public static String getOrderCode(String name,String username) throws ParseException {
        StringBuilder sb = new StringBuilder();
        sb.append(DateUtils.getAbsoluteSeconds(new Date()));
        sb.append(HashUtils.toHash(name));
        sb.append(HashUtils.toHash(username));
        return sb.toString();
    }
}
