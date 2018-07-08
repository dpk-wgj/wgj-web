package com.dpk.wgj.mapper;

import com.dpk.wgj.bean.DriverInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DriverInfoMapper {

    public DriverInfo getDriverInfoByCarId(int carId) throws Exception;

    public DriverInfo getDriverInfo(String driverName, String driverPhoneNumber, int driverLevelStar) throws Exception;

}
