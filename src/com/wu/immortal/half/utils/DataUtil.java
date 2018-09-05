package com.wu.immortal.half.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtil {

    private static final String TIME_FORMAT = "yyyy-MM-dd";
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(TIME_FORMAT);

    public static String timeFormatToString(long milles) {
        return SIMPLE_DATE_FORMAT.format(new Date(milles)).trim();
    }

    public static long timeFormatToLong(String time) throws ParseException {
        return SIMPLE_DATE_FORMAT.parse(time).getTime();
    }

    public static String getNowTimeToString() {
        return SIMPLE_DATE_FORMAT.format(new Date()).trim();
    }

    public static long getNowTimeToLong() {
        return new Date().getTime();
    }
}
