package com.dpk.wgj.service;

import com.dpk.wgj.bean.DriverInfo;

public interface DriverInfoService {

    public DriverInfo getDriverInfo(String driverName,String driverPhoneNumber,int driverLevelStar) throws Exception;
}
