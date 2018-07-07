package com.dpk.wgj.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhoulin on 2018/7/7.
 * 订单详情信息
 */
public class OrderInfo implements Serializable {

    private int orderId;

    private int passengerId;

    private int driverId;

    private String locationInfo;

    private int orderStatus;

    private Date startTime;

    private Date endTime;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getLocationInfo() {
        return locationInfo;
    }

    public void setLocationInfo(String locationInfo) {
        this.locationInfo = locationInfo;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "orderId=" + orderId +
                ", passengerId=" + passengerId +
                ", driverId=" + driverId +
                ", locationInfo='" + locationInfo + '\'' +
                ", orderStatus=" + orderStatus +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
