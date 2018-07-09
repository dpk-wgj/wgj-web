package com.dpk.wgj.mapper;

import com.dpk.wgj.bean.DriverInfo;
import com.dpk.wgj.bean.tableInfo.DriverInfoTableMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DriverInfoMapper {

    public DriverInfo getDriverInfoByCarId(int carId) throws Exception;

    public DriverInfo getDriverInfoByDriverName(String driverName) throws Exception;

    public DriverInfo getDriverInfoByDriverPhoneNumber(String driverPhoneNumber) throws Exception;

    public DriverInfo getDriveInfoByDriverLevelStar(int driverLevelStar) throws Exception;

    public DriverInfo getDriveInfoByDriverIdentity(String driverIdentity) throws Exception;

    public List<DriverInfo> getAllDriverInfo( ) throws Exception;

    public List<DriverInfo> getDriverByMultiCondition(DriverInfoTableMessage carInfoTableMessage) throws Exception;

    public int getDriverByMultiConditionCount(DriverInfoTableMessage carInfoTableMessage) throws Exception;


    public void insertDriverInfo(DriverInfo driverInfo) throws Exception;

    public List<DriverInfo> getAllCarLocation() throws Exception;

    public DriverInfo getDriverInfoByWxId(String driverWxId) throws Exception;

    public int addDriverInfo(DriverInfo driverInfo) throws Exception;


}
