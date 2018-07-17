package com.dpk.wgj.bean;

public class SmsInfo {

    private int userId;

    private String phoneNumber;

    private String randomNum;

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
        String randomNum = "";
        for(int i = 0; i < 4;i ++){
            int random = (int)(Math.random() * 10);
            randomNum += random;
        }
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
                '}';
    }
}
