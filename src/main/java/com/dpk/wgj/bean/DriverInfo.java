package com.dpk.wgj.bean;

import java.io.Serializable;

/**
 * Created by zhoulin on 2018/7/7.
 * 司机信息
 */
public class DriverInfo implements Serializable {

    private int driverId;

    private String driverWxId;

    private String driverPhoneNumber;

    private String driverIdentity;

    private String driverLocation;

    private int driverLevelStar;

    private String driverLicence;

    private int driverStatus;

    private int flag;

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getDriverWxId() {
        return driverWxId;
    }

    public void setDriverWxId(String driverWxId) {
        this.driverWxId = driverWxId;
    }

    public String getDriverPhoneNumber() {
        return driverPhoneNumber;
    }

    public void setDriverPhoneNumber(String driverPhoneNumber) {
        this.driverPhoneNumber = driverPhoneNumber;
    }

    public String getDriverIdentity() {
        return driverIdentity;
    }

    public void setDriverIdentity(String driverIdentity) {
        this.driverIdentity = driverIdentity;
    }

    public String getDriverLocation() {
        return driverLocation;
    }

    public void setDriverLocation(String driverLocation) {
        this.driverLocation = driverLocation;
    }

    public int getDriverLevelStar() {
        return driverLevelStar;
    }

    public void setDriverLevelStar(int driverLevelStar) {
        this.driverLevelStar = driverLevelStar;
    }

    public String getDriverLicence() {
        return driverLicence;
    }

    public void setDriverLicence(String driverLicence) {
        this.driverLicence = driverLicence;
    }

    public int getDriverStatus() {
        return driverStatus;
    }

    public void setDriverStatus(int driverStatus) {
        this.driverStatus = driverStatus;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "DriverInfo{" +
                "driverId=" + driverId +
                ", driverWxId='" + driverWxId + '\'' +
                ", driverPhoneNumber='" + driverPhoneNumber + '\'' +
                ", driverIdentity='" + driverIdentity + '\'' +
                ", driverLocation='" + driverLocation + '\'' +
                ", driverLevelStar=" + driverLevelStar +
                ", driverLicence='" + driverLicence + '\'' +
                ", driverStatus=" + driverStatus +
                ", flag=" + flag +
                '}';
    }
}
