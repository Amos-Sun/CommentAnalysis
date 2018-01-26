package com.sun.modules.common.response;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by SunGuiyong
 * on 2018/1/26.
 */
public class OtherInfo {

    @Setter @Getter
    private Integer total;

    OtherInfo(Integer total) {
        this.total = total;
    }

    OtherInfo(){}
}
