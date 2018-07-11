package com.dpk.wgj.service;

import com.dpk.wgj.bean.DriverInfo;

public interface DriverInfoApiService {

    /**
     * 根据司机Id进行司机全部信息查询
     * @param driverId
     */
    public DriverInfo getDriverInfoByDriverId(int driverId) throws Exception;

    public  int updateApiDriverInfoByDriverId(DriverInfo driverInfo) throws Exception;

}
