package com.sun.modules.common.response;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by SunGuiyong
 * on 2018/1/26.
 */
public class MsgResponse {

    @Setter
    @Getter
    private Integer code;
    @Setter
    @Getter
    private Object data;
    @Setter
    @Getter
    private OtherInfo other;

    public static MsgResponse success() {
        return new MsgResponse(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getDesc(), null);
    }

    public static MsgResponse success(Object data) {
        return new MsgResponse(ResponseEnum.SUCCESS.getCode(), data, null);
    }

    public static MsgResponse success(Object data, OtherInfo other) {
        return new MsgResponse(ResponseEnum.SUCCESS.getCode(), data, other);
    }

    public static MsgResponse paragramError() {
        return new MsgResponse(ResponseEnum.PARA_ERROR.getCode(), ResponseEnum.PARA_ERROR.getDesc(), null);
    }

    public static MsgResponse paragramError(Object data) {
        return new MsgResponse(ResponseEnum.PARA_ERROR.getCode(), data, null);
    }


    MsgResponse(int code, Object data, OtherInfo other) {
        this.code = code;
        this.data = data;
        this.other = other;
    }
}
