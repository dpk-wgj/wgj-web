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
     * 根据司机的名字进行司机全部信息查询
     * @param driverName
     */
    public DriverInfo getDriverInfoByDriverName(String driverName) throws Exception;

    /**
     * 根据司机的手机号进行司机全部信息查询
     * @param driverPhoneNumber
     */
    public DriverInfo getDriverInfoByDriverPhoneNumber(String driverPhoneNumber) throws Exception;

    /**
     * 根据司机的星级进行司机全部信息查询
     * @param driverLevelStar
     */
    public DriverInfo getDriveInfoByDriverLevelStar(int driverLevelStar) throws Exception;


    /**
     * 获取所有当前上岗司机位置
     * @return
     */
    public List<DriverInfo> getAllCarLocation() throws Exception;


}
