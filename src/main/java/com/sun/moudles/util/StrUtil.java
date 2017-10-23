package com.sun.moudles.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sunguiyong on 2017/10/15.
 */
public class StrUtil {

    private static Pattern NUMBER_PATTERN = Pattern.compile("[0-9]*");
    /**
     * 判断一个字符串是否是数字
     * @param str
     * @return
     */
    public static boolean judgeIsNum(String str) {
        Matcher isNum = NUMBER_PATTERN.matcher(str);
        if (isNum.matches()) {
            return true;
        }
        return false;
    }
}
