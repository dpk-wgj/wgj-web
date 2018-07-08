package com.dpk.wgj.service;

import com.dpk.wgj.bean.DriverInfo;

public interface DriverInfoService {

    /**
     * 根据司机绑定的车辆Id进行司机信息查询
     * @param carId
     */
    public DriverInfo getDriverInfoByCarId(int carId) throws Exception;

}
