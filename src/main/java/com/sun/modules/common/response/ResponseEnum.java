package com.sun.modules.common.response;

import lombok.Getter;
import lombok.Setter;

public enum ResponseEnum {
    SUCCESS(200, "成功"),
    PARA_ERROR(402, "参数错误"),
    FAIL(500, "服务器错误");

    @Setter @Getter private int code;
    @Setter @Getter private String desc;

    ResponseEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
