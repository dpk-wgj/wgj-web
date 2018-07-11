package com.dpk.wgj.bean.DTO;

/**
 * Created by zhoulin on 2018/7/10.
 * 位置判断类
 */
public class AccessDriverDTO {

    private int orderId;

    private String currentLocation;

    private String targetLocation;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public String getTargetLocation() {
        return targetLocation;
    }

    public void setTargetLocation(String targetLocation) {
        this.targetLocation = targetLocation;
    }
}
