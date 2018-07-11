package com.dpk.wgj.mapper;

import com.dpk.wgj.bean.DriverInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DriverInfoMapper {

    public DriverInfo getDriverInfoByCarId(int carId) throws Exception;

    public List<DriverInfo> getAllCarLocation() throws Exception;

    public DriverInfo getDriverInfoByWxId(String driverWxId) throws Exception;

    public int addDriverInfo(DriverInfo driverInfo) throws Exception;

    public int updateDriverStatus(DriverInfo driverInfo) throws Exception;

}
