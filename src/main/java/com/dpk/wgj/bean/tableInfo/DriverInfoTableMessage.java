package com.dpk.wgj.bean.tableInfo;

import com.dpk.wgj.bean.DriverInfo;

/**
 * 司机管理表信息
 */
public class DriverInfoTableMessage extends TableMessage{

    private DriverInfo driverInfo = new DriverInfo();

    public DriverInfo getDriverInfo() {
        return driverInfo;
    }

    public void setDriverInfo(DriverInfo carInfo) {
        this.driverInfo = carInfo;
    }

}
