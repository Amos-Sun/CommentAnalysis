package com.sun.modules.bean.domain;

/**
 * Created by sunguiyong on 2017/10/15.
 */
public class VideoDO {

    private String videoUrl;
    private String videoName;
    private String videoPicturePath;
    private String videoActors;
    private String videoType;
    private String videoDetail;
    private String videoTime;
    private String lastEvaluateTime;

    private String cid;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoPicturePath() {
        return videoPicturePath;
    }

    public void setVideoPicturePath(String videoPicturePath) {
        this.videoPicturePath = videoPicturePath;
    }

    public String getVideoActors() {
        return videoActors;
    }

    public void setVideoActors(String videoActors) {
        this.videoActors = videoActors;
    }

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    public String getVideoDetail() {
        return videoDetail;
    }

    public void setVideoDetail(String videoDetail) {
        this.videoDetail = videoDetail;
    }

    public String getVideoTime() {
        return videoTime;
    }

    public void setVideoTime(String videoTime) {
        this.videoTime = videoTime;
    }

    public String getLastEvaluateTime() {
        return lastEvaluateTime;
    }

    public void setLastEvaluateTime(String lastEvaluateTime) {
        this.lastEvaluateTime = lastEvaluateTime;
    }
}
