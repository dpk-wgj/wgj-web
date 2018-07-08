package com.dpk.wgj.bean;

import java.io.Serializable;

/**
 * Created by zhoulin on 2018/7/7.
 * 电瓶车信息
 */
public class CarInfo implements Serializable {

    private int carId;

    private String carNumber;

    private String carType;

    private DriverInfo driverInfo;

    private int carSeat;

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public int getCarSeat() {
        return carSeat;
    }

    public void setCarSeat(int carSeat) {
        this.carSeat = carSeat;
    }

    public DriverInfo getDriverInfo() {
        return driverInfo;
    }

    public void setDriverInfo(DriverInfo driverInfo) {
        this.driverInfo = driverInfo;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    @Override
    public String toString() {
        return "CarInfo{" +
                "carId=" + carId +
                ", carNumber='" + carNumber + '\'' +
                ", carType='" + carType + '\'' +
                ", driverInfo=" + driverInfo +
                ", carSeat=" + carSeat +
                '}';
    }

}
