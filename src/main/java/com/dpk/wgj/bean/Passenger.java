package com.dpk.wgj.bean;

import java.io.Serializable;

/**
 * Created by zhoulin on 2018/7/7.
 * 乘客信息
 */
public class Passenger implements Serializable {

    private int passengerId;

    private String passengerWxId;

    private String passengerPhoneNumber;

    private String passengerLocation;

    private int passengerLevelStar;

    private int passengerStatus;

    private int userGroupId;

    public int getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(int userGroupId) {
        this.userGroupId = userGroupId;
    }

    public int getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
    }

    public String getPassengerWxId() {
        return passengerWxId;
    }

    public void setPassengerWxId(String passengerWxId) {
        this.passengerWxId = passengerWxId;
    }

    public String getPassengerPhoneNumber() {
        return passengerPhoneNumber;
    }

    public void setPassengerPhoneNumber(String passengerPhoneNumber) {
        this.passengerPhoneNumber = passengerPhoneNumber;
    }

    public String getPassengerLocation() {
        return passengerLocation;
    }

    public void setPassengerLocation(String passengerLocation) {
        this.passengerLocation = passengerLocation;
    }

    public int getPassengerLevelStar() {
        return passengerLevelStar;
    }

    public void setPassengerLevelStar(int passengerLevelStar) {
        this.passengerLevelStar = passengerLevelStar;
    }

    public int getPassengerStatus() {
        return passengerStatus;
    }

    public void setPassengerStatus(int passengerStatus) {
        this.passengerStatus = passengerStatus;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "passengerId=" + passengerId +
                ", passengerWxId='" + passengerWxId + '\'' +
                ", passengerPhoneNumber='" + passengerPhoneNumber + '\'' +
                ", passengerLocation='" + passengerLocation + '\'' +
                ", passengerLevelStar=" + passengerLevelStar +
                ", passengerStatus=" + passengerStatus +
                ", userGroupId=" + userGroupId +
                '}';
    }
}
