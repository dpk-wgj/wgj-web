package com.dpk.wgj.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhoulin on 2018/7/8.
 * 车辆轨迹信息
 */
public class PositionInfo implements Serializable {

    private int positionId;

    private int carId;

    private float positionX;

    private float positionY;

    private Date currentTime;

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public float getPositionX() {
        return positionX;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    public Date getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Date currentTime) {
        this.currentTime = currentTime;
    }

    @Override
    public String toString() {
        return "PositionInfo{" +
                "positionId=" + positionId +
                ", carId=" + carId +
                ", positionX=" + positionX +
                ", positionY=" + positionY +
                ", currentTime=" + currentTime +
                '}';
    }
}
