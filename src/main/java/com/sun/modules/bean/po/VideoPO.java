package com.sun.modules.bean.po;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by sunguiyong on 2017/10/8.
 */
@Getter
@Setter
public class VideoPO {

    private Integer id;
    private String name;
    private String cid;
    private String picturePath;
    private String time;
    private String actors;
    private String type;
    private String url;
    private String detail;
    private String goodPercent;
    private String badPercent;
    private String manGoodPercent;
    private String womanGoodPercent;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addTime = new Date();
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modificationTime;

    public VideoPO() {
    }
}
