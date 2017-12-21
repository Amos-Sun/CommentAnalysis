package com.sun.modules.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sunguiyong on 2017/10/15.
 */
public class StrUtil {

    private StrUtil() {
        throw new Error("can't get this class's instance");
    }

    private static Pattern NUMBER_PATTERN = Pattern.compile("[0-9]*");

    private static Pattern CONSTANT_ABSTRACT_PATTERN = Pattern.compile("abstract");

    private static Pattern EMOTION_PATTERN = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]");

    /**
     * 判断字符串中是否包含表情符号
     *
     * @param str
     * @return
     */
    public static boolean hasEmotion(String str) {
        Matcher hasEmotion = EMOTION_PATTERN.matcher(str);
        if (hasEmotion.matches()) {
            return true;
        }
        return false;
    }

    /**
     * 判断一个字符串是否是数字
     *
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

    /**
     * 判断数据中是否有指定的字符串
     *
     * @param data       数据
     * @param specifyStr 指定字符串
     * @return
     */
    public static boolean hasSpecifyString(String data, String specifyStr) {
        if (data.contains(specifyStr)) {
            return true;
        }
        return false;
    }

    /**
     * 替换字符串中的指定字符
     *
     * @param data        字符串
     * @param toReplace   要被替换的字符串
     * @param replaceWith 替换成指定的字符串
     * @return
     */
    public static String replaceString(String data, String toReplace, String replaceWith) {
        data = data.replaceAll(toReplace, replaceWith);
        return data;
    }

    /**
     * 按照要求，获取目的字符串
     *
     * @param str
     * @param startString
     * @param endString
     * @return
     */
    public static String getAimedString(String str, String startString, String endString) {
        int startIndex = str.indexOf(startString);
        int endIndex = str.indexOf(endString);
        return str.substring(startIndex + startString.length(), endIndex);
    }
}