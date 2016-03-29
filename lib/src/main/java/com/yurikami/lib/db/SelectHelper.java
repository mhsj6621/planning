package com.yurikami.lib.db;

import com.yurikami.lib.util.DateUtils;

/**
 * Created by WINFIELD on 2016/3/24.
 */
public class SelectHelper {

    /**
     * 生成条件子句,将给定字段timestamp里的时间戳包裹在给定时间中
     * @param timestamp 给定字段时间戳字段名
     * @param year 给定年
     * @return 时间戳包裹条件子句
     */
    public static String in(String timestamp,int year){
        long start = DateUtils.newDateTimestamp(year ,1 , 1);
        long end = start + DateUtils.millisecondsOfYear(year);
        return "( "+ timestamp +" >= "+ start +" AND "+ timestamp +" < "+ end +" )";
    }
    public static String in(int year){
        long start = DateUtils.newDateTimestamp(year ,1 , 1);
        long end = start + DateUtils.millisecondsOfYear(year);
        return "( ? >= "+ start +" AND ? < "+ end +" )";
    }

    /**
     * 生成条件子句,将给定字段timestamp里的时间戳包裹在给定时间中
     * @param timestamp 给定字段时间戳字段名
     * @param year 给定年
     * @param month 给定月
     * @return 时间戳包裹条件子句
     */
    public static String in(String timestamp,int year,int month){
        long start = DateUtils.newDateTimestamp(year, month, 1);
        long end = start + DateUtils.millisecondsOfMonth(year, month);
        return  "( "+ timestamp +" >= "+ start +" AND "+ timestamp +" < "+ end +" )";
    }
    public static String in(int year,int month){
        long start = DateUtils.newDateTimestamp(year, month, 1);
        long end = start + DateUtils.millisecondsOfMonth(year, month);
        return  "( ? >= "+ start +" AND ? < "+ end +" )";
    }

    /**
     * 生成条件子句,将给定字段timestamp里的时间戳包裹在给定时间中
     * @param timestamp 给定字段时间戳字段名
     * @param year 给定年
     * @param month 给定月
     * @param day 给定天
     * @return 时间戳包裹条件子句
     */
    public static String in(String timestamp,int year,int month,int day){
        long start = DateUtils.newDateTimestamp(year, month, day);
        long end = start + DateUtils.DAY_MILLISECONDS;
        return "( "+ timestamp +" >= "+ start +" AND "+ timestamp +" < "+ end +" )";
    }
    public static String in(int year,int month,int day){
        long start = DateUtils.newDateTimestamp(year, month, day);
        long end = start + DateUtils.DAY_MILLISECONDS;
        return "( ? >= "+ start +" AND ? < "+ end +" )";
    }

    /**
     * 生成条件子句,将目标时间戳字段包含(前闭后闭)在指定范围中
     * @param timestamp 目标时间戳字段
     * @param start 起始时间戳
     * @param end 结束时间戳
     * @return
     */
    public static String in(String timestamp,long start, long end){
        return "( "+ timestamp +" >= "+ start +" AND "+ timestamp +" <= "+ end +")";
    }

}