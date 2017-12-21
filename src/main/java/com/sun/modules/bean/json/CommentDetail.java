package com.sun.modules.bean.json;

import java.util.List;

/**
 * Created by sunguiyong on 2017/10/26.
 */
public class CommentDetail {

    private String id;
    private String rootid;
    private String targetid;
    private String parent;
    private String timeDifference;
    private String time; //评论时间
    private String content; //评论内容
    private String title;
    private String up;
    private String rep;
    private String type;
    private String hotscale;
    private String checktype;
    private String checkstatus;
    private String isdeleted;
    private String tagself;
    private String taghost;
    private String source;
    private String location;
    private String address;
    private String rank;
    private String custom;
    private String orireplynum;
    private String richtype;
    private String userid;
    private String poke;
    private String abstract__;
    private String thirdid;
    private Integer ispick;
    private Integer ishide;
    private Integer isauthor;
    private String replyuser;
    private String replyuserid;
    private String replyhwvip;
    private String replyhwlevel;
    private String replyhwannual;
    private ExtendInfo extend;
    private UserInfo userinfo; // 对应每一个评论的 用户的信息

    public static class ExtendInfo{
        private String at;
        private String ut;
        private String ct;
        private String wt;

        public String getAt() {
            return at;
        }

        public void setAt(String at) {
            this.at = at;
        }

        public String getUt() {
            return ut;
        }

        public void setUt(String ut) {
            this.ut = ut;
        }

        public String getCt() {
            return ct;
        }

        public void setCt(String ct) {
            this.ct = ct;
        }

        public String getWt() {
            return wt;
        }

        public void setWt(String wt) {
            this.wt = wt;
        }
    }

    public static class UserInfo{
        private String userid;
        private String uidex;
        private String nick; //用户昵称
        private String head;
        private Integer gender; //用户性别
        private Integer viptype; //是否是vip
        private String mediaid;
        private String region; //住址
        private String thirdlogin;
        private String hwvip;
        private String hwlevel;
        private String hwannual;
        private String identity;
        private String certinfo;
        private String remark;
        private String fnd;
        private List<WBUserInfo> wbuserinfo;

        public static class WBUserInfo{
            public WBUserInfo(){}
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getUidex() {
            return uidex;
        }

        public void setUidex(String uidex) {
            this.uidex = uidex;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public Integer getGender() {
            return gender;
        }

        public void setGender(Integer gender) {
            this.gender = gender;
        }

        public Integer getViptype() {
            return viptype;
        }

        public void setViptype(Integer viptype) {
            this.viptype = viptype;
        }

        public String getMediaid() {
            return mediaid;
        }

        public void setMediaid(String mediaid) {
            this.mediaid = mediaid;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getThirdlogin() {
            return thirdlogin;
        }

        public void setThirdlogin(String thirdlogin) {
            this.thirdlogin = thirdlogin;
        }

        public String getHwvip() {
            return hwvip;
        }

        public void setHwvip(String hwvip) {
            this.hwvip = hwvip;
        }

        public String getHwlevel() {
            return hwlevel;
        }

        public void setHwlevel(String hwlevel) {
            this.hwlevel = hwlevel;
        }

        public String getHwannual() {
            return hwannual;
        }

        public void setHwannual(String hwannual) {
            this.hwannual = hwannual;
        }

        public String getIdentity() {
            return identity;
        }

        public void setIdentity(String identity) {
            this.identity = identity;
        }

        public String getCertinfo() {
            return certinfo;
        }

        public void setCertinfo(String certinfo) {
            this.certinfo = certinfo;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getFnd() {
            return fnd;
        }

        public void setFnd(String fnd) {
            this.fnd = fnd;
        }

        public List<WBUserInfo> getWbuserinfo() {
            return wbuserinfo;
        }

        public void setWbuserinfo(List<WBUserInfo> wbuserinfo) {
            this.wbuserinfo = wbuserinfo;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRootid() {
        return rootid;
    }

    public void setRootid(String rootid) {
        this.rootid = rootid;
    }

    public String getTargetid() {
        return targetid;
    }

    public void setTargetid(String targetid) {
        this.targetid = targetid;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getTimeDifference() {
        return timeDifference;
    }

    public void setTimeDifference(String timeDifference) {
        this.timeDifference = timeDifference;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUp() {
        return up;
    }

    public void setUp(String up) {
        this.up = up;
    }

    public String getRep() {
        return rep;
    }

    public void setRep(String rep) {
        this.rep = rep;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHotscale() {
        return hotscale;
    }

    public void setHotscale(String hotscale) {
        this.hotscale = hotscale;
    }

    public String getChecktype() {
        return checktype;
    }

    public void setChecktype(String checktype) {
        this.checktype = checktype;
    }

    public String getCheckstatus() {
        return checkstatus;
    }

    public void setCheckstatus(String checkstatus) {
        this.checkstatus = checkstatus;
    }

    public String getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(String isdeleted) {
        this.isdeleted = isdeleted;
    }

    public String getTagself() {
        return tagself;
    }

    public void setTagself(String tagself) {
        this.tagself = tagself;
    }

    public String getTaghost() {
        return taghost;
    }

    public void setTaghost(String taghost) {
        this.taghost = taghost;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    public String getOrireplynum() {
        return orireplynum;
    }

    public void setOrireplynum(String orireplynum) {
        this.orireplynum = orireplynum;
    }

    public String getRichtype() {
        return richtype;
    }

    public void setRichtype(String richtype) {
        this.richtype = richtype;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPoke() {
        return poke;
    }

    public void setPoke(String poke) {
        this.poke = poke;
    }

    public String getThirdid() {
        return thirdid;
    }

    public void setThirdid(String thirdid) {
        this.thirdid = thirdid;
    }

    public Integer getIspick() {
        return ispick;
    }

    public void setIspick(Integer ispick) {
        this.ispick = ispick;
    }

    public Integer getIshide() {
        return ishide;
    }

    public void setIshide(Integer ishide) {
        this.ishide = ishide;
    }

    public Integer getIsauthor() {
        return isauthor;
    }

    public void setIsauthor(Integer isauthor) {
        this.isauthor = isauthor;
    }

    public String getReplyuser() {
        return replyuser;
    }

    public void setReplyuser(String replyuser) {
        this.replyuser = replyuser;
    }

    public String getReplyuserid() {
        return replyuserid;
    }

    public void setReplyuserid(String replyuserid) {
        this.replyuserid = replyuserid;
    }

    public String getReplyhwvip() {
        return replyhwvip;
    }

    public void setReplyhwvip(String replyhwvip) {
        this.replyhwvip = replyhwvip;
    }

    public String getReplyhwlevel() {
        return replyhwlevel;
    }

    public void setReplyhwlevel(String replyhwlevel) {
        this.replyhwlevel = replyhwlevel;
    }

    public String getReplyhwannual() {
        return replyhwannual;
    }

    public void setReplyhwannual(String replyhwannual) {
        this.replyhwannual = replyhwannual;
    }

    public ExtendInfo getExtend() {
        return extend;
    }

    public void setExtend(ExtendInfo extend) {
        this.extend = extend;
    }

    public UserInfo getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserInfo userinfo) {
        this.userinfo = userinfo;
    }

    public String getAbstract__() {
        return abstract__;
    }

    public void setAbstract__(String abstract__) {
        this.abstract__ = abstract__;
    }
}
