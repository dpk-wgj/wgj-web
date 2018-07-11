package com.dpk.wgj.mapper.api;


import com.dpk.wgj.bean.DriverInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DriverInfoApiMapper {

    public DriverInfo getDriverInfoByDriverId(int DriverId) throws Exception;

    public int updateApiDriverInfoByDriverId(DriverInfo driverInfo) throws Exception;
}
