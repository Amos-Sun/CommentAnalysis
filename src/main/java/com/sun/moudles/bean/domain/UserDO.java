package com.sun.moudles.bean.domain;

/**
 * Created by sunguiyong on 2017/10/17.
 */
public class UserDO {

    private String userName;
    private Integer userAge;
    private String userSex;
    private String motificationTime;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getMotificationTime() {
        return motificationTime;
    }

    public void setMotificationTime(String motificationTime) {
        this.motificationTime = motificationTime;
    }
}
