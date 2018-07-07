package com.dpk.wgj.bean;

import java.io.Serializable;

/**
 * Created by zhoulin on 2018/7/7.
 * 电瓶车信息
 */
public class CarInfo implements Serializable {

    private int carId;

    private String carNumber;

    private String catType;

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

    public String getCatType() {
        return catType;
    }

    public void setCatType(String catType) {
        this.catType = catType;
    }

    @Override
    public String toString() {
        return "CarInfo{" +
                "carId=" + carId +
                ", carNumber='" + carNumber + '\'' +
                ", catType='" + catType + '\'' +
                '}';
    }

}
