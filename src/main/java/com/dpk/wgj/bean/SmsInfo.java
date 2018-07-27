package com.dpk.wgj.bean;

import java.io.Serializable;

public class SmsInfo implements Serializable {

    private int userId;

    private String phoneNumber;

    private String driverWxId;

    private String randomNum;

    public String getDriverWxId() {
        return driverWxId;
    }

    public void setDriverWxId(String driverWxId) {
        this.driverWxId = driverWxId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRandomNum() {

        return randomNum;
    }

    public void setRandomNum(String randomNum) {
        this.randomNum = randomNum;
    }

    @Override
    public String toString() {
        return "SmsInfo{" +
                "userId=" + userId +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", randomNum='" + randomNum + '\'' +
                ", driverWxId='" + driverWxId + '\'' +
                '}';
    }
}
