package com.sun.modules.bean.json;

import java.util.List;

/**
 * Created by sunguiyong on 2017/10/26.
 */
public class DataDetail {
    private Integer errCode;
    private Data data;
    private Info info;

    public static class Data {
        private String targetid;
        private Integer display;
        private Integer total; //一共有多少评论
        private Integer reqnum; //一次请求多少数据
        private Integer retnum; //一次返回多少数据
        private String maxid; //所有评论的最大id
        private String first; //一次返回评论数据中 第一个评论的id
        private String last; //一次返回评论数据中 最后一个评论的id
        private boolean hasnext; //是否还有下一条评论
        private CommentTargetInfo targetinfo;
        private List<CommentDetail> commentid; //评论详细信息

        public String getTargetid() {
            return targetid;
        }

        public void setTargetid(String targetid) {
            this.targetid = targetid;
        }

        public Integer getDisplay() {
            return display;
        }

        public void setDisplay(Integer display) {
            this.display = display;
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public Integer getReqnum() {
            return reqnum;
        }

        public void setReqnum(Integer reqnum) {
            this.reqnum = reqnum;
        }

        public Integer getRetnum() {
            return retnum;
        }

        public void setRetnum(Integer retnum) {
            this.retnum = retnum;
        }

        public String getMaxid() {
            return maxid;
        }

        public void setMaxid(String maxid) {
            this.maxid = maxid;
        }

        public String getFirst() {
            return first;
        }

        public void setFirst(String first) {
            this.first = first;
        }

        public String getLast() {
            return last;
        }

        public void setLast(String last) {
            this.last = last;
        }

        public boolean isHasnext() {
            return hasnext;
        }

        public void setHasnext(boolean hasnext) {
            this.hasnext = hasnext;
        }

        public List<CommentDetail> getCommentid() {
            return commentid;
        }

        public void setCommentid(List<CommentDetail> commentid) {
            this.commentid = commentid;
        }

        public CommentTargetInfo getTargetinfo() {
            return targetinfo;
        }

        public void setTargetinfo(CommentTargetInfo targetinfo) {
            this.targetinfo = targetinfo;
        }
    }

    public static class Info {
        private String time;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }
}
