package com.sun.modules.util;

public class MathUtil {

    /**
     * 计算任意低的对数
     *
     * @param value value
     * @param base  底数
     * @return
     */
    public static double log_x(double value, double base) {
        return Math.log(value) / Math.log(base);
    }

    public static double log_x(int value, int base) {
        return Math.log(value) / Math.log(base);
    }

    public static double log_x(int value, double base) {
        return Math.log(value) / Math.log(base);
    }

    public static double log_x(double value, int base) {
        return Math.log(value) / Math.log(base);
    }
}
