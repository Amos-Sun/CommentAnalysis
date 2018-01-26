package com.sun.modules.bean.vo;

import com.sun.modules.bean.po.VideoPO;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by SunGuiyong
 * on 2018/1/26.
 */
public class VideoVO {

    @Setter
    @Getter
    private String cid;
    @Setter
    @Getter
    private String name;
    @Setter
    @Getter
    private String videoType;
    @Setter
    @Getter
    private String actors;
    @Setter
    @Getter
    private String videoUrl;
    @Setter
    @Getter
    private String picUrl;
    @Setter
    @Getter
    private String detail;

    public VideoVO(VideoPO videoPO) {
        this.cid = videoPO.getCid();
        this.actors = videoPO.getActors();
        this.name = videoPO.getName();
        this.picUrl = videoPO.getPicturePath();
        this.videoUrl = videoPO.getUrl();
        this.videoType = videoPO.getType();
        this.detail = videoPO.getDetail();
    }
}
