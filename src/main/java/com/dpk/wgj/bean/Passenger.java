package com.dpk.wgj.bean;

import java.io.Serializable;

/**
 * Created by zhoulin on 2018/7/7.
 * 乘客信息
 */
public class Passenger implements Serializable {

    private int passenger_id;

    private String passengerWxId;

    private String passengerPhoneNumber;

    private String passengerLocation;

    private int passengerLevelStar;

    private int passengerStatus;

    public int getPassenger_id() {
        return passenger_id;
    }

    public void setPassenger_id(int passenger_id) {
        this.passenger_id = passenger_id;
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
                "passenger_id=" + passenger_id +
                ", passengerWxId='" + passengerWxId + '\'' +
                ", passengerPhoneNumber='" + passengerPhoneNumber + '\'' +
                ", passengerLocation='" + passengerLocation + '\'' +
                ", passengerLevelStar=" + passengerLevelStar +
                ", passengerStatus=" + passengerStatus +
                '}';
    }
}
