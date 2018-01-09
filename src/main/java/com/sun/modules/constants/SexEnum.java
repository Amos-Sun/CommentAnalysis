package com.sun.modules.constants;

/**
 * Created by sunguiyong on 2018/1/9.
 */
public enum SexEnum {
    BOY(0, "男"),
    GIRL(1, "女"),
    UNSURE(2, "不定");

    private int value;
    private String desc;

    SexEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static SexEnum getByValue(int value) {
        switch (value) {
            case 0:
                return BOY;
            case 1:
                return GIRL;
            default:
                return UNSURE;
        }
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
