package com.sun.moudles.bean.po;

/**
 * Created by sunguiyong on 2017/10/16.
 */
public class UserPO {

    private String userName;
    private String userSex;
    private Integer userAge;
    private String addTime;
    private String motificationTime;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getMotificationTime() {
        return motificationTime;
    }

    public void setMotificationTime(String motificationTime) {
        this.motificationTime = motificationTime;
    }
}
