package com.dpk.wgj.bean.tableInfo;

/**
 * Created by zhoulin on 2018/7/10.
 * 订单信息类
 */
public class OrderMessage {

    // 起始位置
    private String startLocation;

    // 目的位置
    private String endLocation;

    // 订单路径
    private String locationInfo;

    public String getLocationInfo() {
        return locationInfo;
    }

    public void setLocationInfo(String locationInfo) {
        this.locationInfo = locationInfo;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public String getStartLocation() {

        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }
}
