package com.yurikami.lib.util;

import com.yurikami.lib.entity.Datetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by WINFIELD on 2016/2/29.
 */
public class DateUtils {
    public final long MINUTE_SECONDS = 60;
    public final long HOUR_SECONDS = MINUTE_SECONDS * 60;
    public final long DAY_SECONDS = HOUR_SECONDS * 24;

    private static long sLastTimestamp = 0l;
    private static Calendar sLastCalendar;

    private static SimpleDateFormat chnDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
    private static SimpleDateFormat hourMinuteFormat = new SimpleDateFormat("HH:mm");

    private static Date now() { return new Date(); }
    private static long nowTimestamp() { return System.currentTimeMillis(); }


    /**
     *
     * 提取时间戳之中的年月日时分秒之一的数值
     * @param timestamp 时间戳
     * @param part "year" "month" "day" "hour" "minute" "second"
     * @return 年月日时分秒之一的数值
     */
    public static Integer extractTimestamp(long timestamp, String part){
        Calendar c;
        if(timestamp == sLastTimestamp && sLastCalendar != null) {
            c = sLastCalendar;
        }else {
            c = Calendar.getInstance();
            c.setTimeInMillis(timestamp);
            sLastCalendar = c;
        }
        if("year".equals(part)){ return c.get(Calendar.YEAR); }
        else if("month".equals(part)){ return c.get(Calendar.MONTH) + 1; }
        else if("week".equals(part)){ return c.get(Calendar.DAY_OF_WEEK); }
        else if("day".equals(part)){ return c.get(Calendar.DAY_OF_MONTH); }
        else if("hour".equals(part)){ return c.get(Calendar.HOUR_OF_DAY); }
        else if("minute".equals(part)){ return c.get(Calendar.MINUTE); }
        else if("second".equals(part)){ return c.get(Calendar.SECOND); }
        return null;
    }
    public static Integer year(long timestamp){ return extractTimestamp(timestamp, "year"); }
    public static Integer month(long timestamp){ return extractTimestamp(timestamp, "month"); }
    public static Integer week(long timestamp){ return formatWeek(extractTimestamp(timestamp, "week")); }
    public static Integer day(long timestamp){ return extractTimestamp(timestamp, "day"); }
    public static Integer hour(long timestamp){ return extractTimestamp(timestamp, "hour"); }
    public static Integer minute(long timestamp){ return extractTimestamp(timestamp, "minute");}
    public static Integer second(long timestamp){ return extractTimestamp(timestamp, "second"); }

    /**
     *
     * 根据年月日时分秒来生成unix时间戳
     * @return unix时间戳
     */
    public static long newTimestamp(int year, int month, int day,
                                     int hour, int minute, int second){
        Calendar c = Calendar.getInstance();
        c.set(year,month - 1,day,hour,minute,second);
        return c.getTimeInMillis();
    }
    public static long newDateTimestamp(int year, int month, int day){
        return newTimestamp(year, month, day, 0, 0, 0);
    }
    public static long newTimeTimestamp(int hour, int minute, int second){
        return newTimestamp(0, 0, 0, hour, minute, second);
    }

    /**
     * 计算某年中某月的天数,不填返回null
     * @param datetime year,month 必填
     * @return 某年中某月的天数
     */
    public static int dayInMonth(Datetime datetime){
        if(datetime == null || datetime.getYear() == null || datetime.getMonth() == null) {
            throw new IllegalArgumentException("Illegal datetime,without year or month");
        }
        Calendar c = Calendar.getInstance();
        c.set(datetime.getYear(), datetime.getMonth() - 1, 1);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 计算某年某月某日是星期几
     * @param datetime year,month,day 必填
     * @return 周一:1 周二:2 周三:3 ...
     */
    public static int dayOfWeek(Datetime datetime){
        if(datetime == null || datetime.getYear() == null || datetime.getMonth() == null
                || datetime.getDay() == null) {
            throw new IllegalArgumentException("Illegal datetime,without year or month or day");
        }
        Calendar c = Calendar.getInstance();
        c.set(datetime.getYear(), datetime.getMonth() - 1, datetime.getDay());
        int d = c.get(Calendar.DAY_OF_WEEK) - 1;
        return (d == 0 ? 7 : d);
    }

    /**
     * 把0代表星期日的情况换成7代表星期日的情况
     * @param week 代表的星期数
     * @return 星期数,星期一到日用1~7来表示
     */
    public static int formatWeek(int week){
        if(week > 6 || week < 0)
            return -1;
        return week == 0 ? 7 : week;
    }

    /**
     * 计算今天的年份,这个系列方法效率比dateOfToday效率低
     * @return 今天的年份
     */
    public static int yearOfToday(){ return year(nowTimestamp()); }
    public static int monthOfToday(){
        return month(nowTimestamp());
    }
    public static int dayOfToday(){
        return day(nowTimestamp());
    }
    public static int weekOfToday(){
        return week(nowTimestamp());
    }

    /**
     * 计算出今天的年月日星期,效率较高
     * @return 今天的年月日星期
     */
    public static Datetime dateOfToday(){
        long nowTimestamp = nowTimestamp();
        Datetime date = Datetime.buildDate(year(nowTimestamp), month(nowTimestamp), day(nowTimestamp));
        date.setWeek(week(nowTimestamp));
        return date;
    }

    /**
     * 获取到今天日期0点的时间戳
     * @return 今天日期0点的时间戳
     */
    public static long currentDateTimestamp(){
        Datetime date = dateOfToday();
        return newDateTimestamp(date.getYear(),date.getMonth(),date.getDay());
    }

    /**
     * 获取当前时间
     * @return 格式:yyyy年MM月dd日
     */
    public static String currentChnDate(){
        return chnDateFormat.format(now());
    }
    /**
     * 将形如yyyy年MM月dd日的日期字符串转换为时间戳
     * @param chnDate 日期字符串
     * @return 正确返回时间戳,错误返回-1
     */
    public static long convertChnDate2Timestamp(String chnDate){
        try {
            Date date = chnDateFormat.parse(chnDate);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }
    /**
     * 将时间戳转换为形如yyyy年MM月dd日
     * @param timestamp 时间戳
     * @return 中文年月日字符串
     */
    public static String formatTimestamp2ChnDate(long timestamp){
        return chnDateFormat.format(timestamp);
    }

    /**
     * 将形如HH:mm的时分字符串转换为时间戳
     * @param hourMinute 时分字符串
     * @return 正确返回时间戳,错误返回-1
     */
    public static long convertHourMinute2Timestamp(String hourMinute){
        try {
            Date date = hourMinuteFormat.parse(hourMinute);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 将时间戳转换为形如HH:mm
     * @param timestamp 时间戳
     * @return 形如HH:mm的字符串
     */
    public static String formatTimestamp2HourMinute(long timestamp){
        return hourMinuteFormat.format(timestamp);
    }
}