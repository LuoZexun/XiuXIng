package com.lzx.xiuxian.Vo;


import java.util.Date;

public class User {

    private String name;
    private String phone;
    private String psw;
    private long score;
    private long startDate;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getScore() {
        return score;
    }
    public void setScore(long score) {
        this.score = score;
    }
    public long getStartDate() {
        return startDate;
    }
    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }
}
