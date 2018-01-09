package com.sun.modules.bean.json;

/**
 * Created by sunguiyong on 2017/10/26.
 */
public class CommentTargetInfo {

    private String orgcommentnum;
    private String commentnum;
    private String checkstatus;
    private String checktype;
    private String city;
    private String voteid;
    private String topicids;
    private String commup;

    public String getOrgcommentnum() {
        return orgcommentnum;
    }

    public void setOrgcommentnum(String orgcommentnum) {
        this.orgcommentnum = orgcommentnum;
    }

    public String getCommentnum() {
        return commentnum;
    }

    public void setCommentnum(String commentnum) {
        this.commentnum = commentnum;
    }

    public String getCheckstatus() {
        return checkstatus;
    }

    public void setCheckstatus(String checkstatus) {
        this.checkstatus = checkstatus;
    }

    public String getChecktype() {
        return checktype;
    }

    public void setChecktype(String checktype) {
        this.checktype = checktype;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getVoteid() {
        return voteid;
    }

    public void setVoteid(String voteid) {
        this.voteid = voteid;
    }

    public String getTopicids() {
        return topicids;
    }

    public void setTopicids(String topicids) {
        this.topicids = topicids;
    }

    public String getCommup() {
        return commup;
    }

    public void setCommup(String commup) {
        this.commup = commup;
    }

    @Override
    public String toString() {
        return "CommentTargetInfo{" +
                "orgcommentnum='" + orgcommentnum + '\'' +
                ", commentnum='" + commentnum + '\'' +
                ", checkstatus='" + checkstatus + '\'' +
                ", checktype='" + checktype + '\'' +
                ", city='" + city + '\'' +
                ", voteid='" + voteid + '\'' +
                ", topicids='" + topicids + '\'' +
                ", commup='" + commup + '\'' +
                '}';
    }
}
