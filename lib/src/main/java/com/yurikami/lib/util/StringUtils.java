package com.yurikami.lib.util;

/**
 * Created by WINFIELD on 2016/3/22.
 */
public class StringUtils {

    /** 拼接为yyyy/MM/dd */
    public static String splice2Date(int year, int month, int day){
        return year + "/" + month + "/" + day;
    }

    /** 字符串数组判空 */
    public static boolean isAnyEmpty(CharSequence... strs){
        if (strs == null) return true;
        for (CharSequence str : strs) {
            if (str == null || str.length() == 0)
                return true;
        }
        return false;
    }

}
