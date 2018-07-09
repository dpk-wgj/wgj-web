package com.dpk.wgj.service;

import com.dpk.wgj.bean.DriverInfo;

import java.util.List;

public interface DriverInfoService {

    /**
     * 根据司机绑定的车辆Id进行司机信息查询
     * @param carId
     */
    public DriverInfo getDriverInfoByCarId(int carId) throws Exception;

    /**
     * 获取所有当前上岗司机位置
     * @return
     */
    public List<DriverInfo> getAllCarLocation() throws Exception;

    public DriverInfo getDriverInfoByWxId(String driverWxId) throws Exception;

    public int addDriverInfo(DriverInfo driverInfo) throws Exception;


}
