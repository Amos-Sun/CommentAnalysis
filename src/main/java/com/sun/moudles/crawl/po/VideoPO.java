package com.sun.moudles.crawl.po;

/**
 * Created by sunguiyong on 2017/10/8.
 */
public class VideoPO {

    private Integer id;
    private String vName;
    private String vPicture;
    private String vTime;
    private String vActors;
    private String vType;
    private String vGoodPercent;
    private String vBadPercent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getvName() {
        return vName;
    }

    public void setvName(String vName) {
        this.vName = vName;
    }

    public String getvPicture() {
        return vPicture;
    }

    public void setvPicture(String vPicture) {
        this.vPicture = vPicture;
    }

    public String getvTime() {
        return vTime;
    }

    public void setvTime(String vTime) {
        this.vTime = vTime;
    }

    public String getvActors() {
        return vActors;
    }

    public void setvActors(String vActors) {
        this.vActors = vActors;
    }

    public String getvType() {
        return vType;
    }

    public void setvType(String vType) {
        this.vType = vType;
    }

    public String getvGoodPercent() {
        return vGoodPercent;
    }

    public void setvGoodPercent(String vGoodPercent) {
        this.vGoodPercent = vGoodPercent;
    }

    public String getvBadPercent() {
        return vBadPercent;
    }

    public void setvBadPercent(String vBadPercent) {
        this.vBadPercent = vBadPercent;
    }
}