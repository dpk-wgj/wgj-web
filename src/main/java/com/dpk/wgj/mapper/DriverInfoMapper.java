package com.dpk.wgj.mapper;

import com.dpk.wgj.bean.DriverInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DriverInfoMapper {

    public DriverInfo getDriverInfoByCarId(int carId) throws Exception;

    public DriverInfo getDriverInfoByDriverName(String driverName) throws Exception;


    public DriverInfo getDriverInfoByDriverPhoneNumber(String driverPhoneNumber) throws Exception;

    public DriverInfo getDriveInfoByDriverLevelStar(int driverLevelStar) throws Exception;
}
