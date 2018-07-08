package com.dpk.wgj.bean;

/**
 * Created by zhoulin on 2018/7/8.
 * 车辆位置坐标
 */
public class Location {

    private double positionX;

    private double positionY;

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    @Override
    public String toString() {
        return "[" + positionX + "," + positionY + "]";
    }
}
