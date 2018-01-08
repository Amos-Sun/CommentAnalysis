package com.sun.modules.bean.po;

/**
 * Created by sunguiyong on 2017/10/8.
 */
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
    private String evaluate;
    private String lastEvaluateTime;
    private String addTime;
    private String modificationTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getGoodPercent() {
        return goodPercent;
    }

    public void setGoodPercent(String goodPercent) {
        this.goodPercent = goodPercent;
    }

    public String getBadPercent() {
        return badPercent;
    }

    public void setBadPercent(String badPercent) {
        this.badPercent = badPercent;
    }

    public String getLastEvaluateTime() {
        return lastEvaluateTime;
    }

    public void setLastEvaluateTime(String lastEvaluateTime) {
        this.lastEvaluateTime = lastEvaluateTime;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(String modificationTime) {
        this.modificationTime = modificationTime;
    }
}
