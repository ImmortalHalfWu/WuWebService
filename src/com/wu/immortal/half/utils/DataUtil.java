package com.wu.immortal.half.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataUtil {

    private static final int TIME_DEFAULT_VIP_ORDINARY_MONTH = 120;
    private static final String TIME_FORMAT_ALL = "yyyy-MM-dd HH:mm:ss";
    private static final String TIME_FORMAT_YMD = "yyyy-MM-dd";
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT_ALL = new SimpleDateFormat(TIME_FORMAT_ALL);
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT_YMD = new SimpleDateFormat(TIME_FORMAT_YMD);

    /**
     * long 转yyyy-MM-dd HH:mm:ss
     * @param milles 时间戳
     * @return yyyy-MM-dd HH:mm:ss格式
     */
    public static String timeFormatAllToString(long milles) {
        return SIMPLE_DATE_FORMAT_ALL.format(new Date(milles)).trim();
    }

    /**
     * long 转yyyy-MM-dd
     * @param milles 时间戳
     * @return yyyy-MM-dd 格式
     */
    public static String timeFormatYMDToString(long milles) {
        return SIMPLE_DATE_FORMAT_YMD.format(new Date(milles)).trim();

    }

    public static long timeFormatToLong(String time) throws ParseException {
        return SIMPLE_DATE_FORMAT_ALL.parse(time).getTime();
    }

    public static String getNowTimeToString() {
        return SIMPLE_DATE_FORMAT_ALL.format(new Date()).trim();
    }

    public static long getNowTimeToLong() {
        return new Date().getTime();
    }

    /**
     * 获取之后几天的时间
     * @param dealyDay 几天
     * @return long
     */
    public static long getDelayTimeForDay(int dealyDay) {
        return getDelayTimeForField(Calendar.DAY_OF_YEAR, dealyDay);
    }


    public static long getDelayTimeForMonth(int dealyMonth) {
        return getDelayTimeForField(Calendar.MONTH, dealyMonth);
    }

    public static long getDefaultOrdidnaryTimeToLong() {
        return getDelayTimeForMonth(TIME_DEFAULT_VIP_ORDINARY_MONTH);
    }

    private static long getDelayTimeForField(int calendarField, int delay) {
        Calendar calendar = Calendar.getInstance();
        try {
            if (delay > 0) {
                calendar.set(calendarField, calendar.get(calendarField) + delay);
            }
            return calendar.getTime().getTime();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(calendarField + "__" + delay, e);
        }
        return calendar.getTime().getTime();
    }
}
