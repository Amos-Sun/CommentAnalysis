package com.sun.moudles.bean.json;

import java.util.List;

/**
 * Created by sunguiyong on 2017/11/5.
 */
public class VideoCommentId {

    private String comment_id;
    private CommentResult result;
    private String srcid;
    private String srcid_type;


    public static class CommentResult{
        private Integer code;
        private String msg; // success 成功
        private Integer ret;

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Integer getRet() {
            return ret;
        }

        public void setRet(Integer ret) {
            this.ret = ret;
        }
    }
    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public CommentResult getResult() {
        return result;
    }

    public void setResult(CommentResult result) {
        this.result = result;
    }

    public String getSrcid() {
        return srcid;
    }

    public void setSrcid(String srcid) {
        this.srcid = srcid;
    }

    public String getSrcid_type() {
        return srcid_type;
    }

    public void setSrcid_type(String srcid_type) {
        this.srcid_type = srcid_type;
    }
}
