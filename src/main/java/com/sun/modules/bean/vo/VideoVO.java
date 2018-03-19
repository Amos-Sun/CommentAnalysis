package com.sun.modules.bean.vo;

import com.sun.modules.bean.po.VideoPO;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by SunGuiyong
 * on 2018/1/26.
 */
@Setter
@Getter
public class VideoVO {

    private String cid;
    private String name;
    private String videoType;
    private String actors;
    private String videoUrl;
    private String picUrl;
    private String detail;

    private String goodPercent;//好评
    private String badPercent;//差评

    private String manGoodPercent;//男生好评
    private String womanGoodPercent;//女生好评

    public VideoVO(VideoPO videoPO) {
        this.cid = videoPO.getCid();
        this.actors = videoPO.getActors();
        this.name = videoPO.getName();
        this.picUrl = videoPO.getPicturePath();
        this.videoUrl = videoPO.getUrl();
        this.videoType = videoPO.getType();
        this.detail = videoPO.getDetail();
    }

    public VideoVO(VideoPO videoPO, String cid) {
        this.cid = cid;
        this.actors = videoPO.getActors();
        this.name = videoPO.getName();
        this.picUrl = videoPO.getPicturePath();
        this.videoUrl = videoPO.getUrl();
        this.videoType = videoPO.getType();
        this.detail = videoPO.getDetail();
        this.goodPercent = videoPO.getGoodPercent();
        this.badPercent = videoPO.getBadPercent();
        this.manGoodPercent = videoPO.getManGoodPercent();
        this.womanGoodPercent = videoPO.getWomanGoodPercent();

    }
}
