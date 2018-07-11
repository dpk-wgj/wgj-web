package com.dpk.wgj.bean.tableInfo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhoulin on 2018/7/9.
 * 轨迹查询类
 */
public class LocationMessage implements Serializable {

    private String startTime;

    private String endTime;

    private String carNumber;

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {

        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    @Override
    public String toString() {
        return "LocationMessage{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", carNumber=" + carNumber +
                '}';
    }
}
