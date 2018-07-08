package com.dpk.wgj.mapper;

import com.dpk.wgj.bean.CarInfo;
import com.dpk.wgj.bean.tableInfo.CarInfoTableMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CarInfoMapper {

    public CarInfo getCarInfoByCarNumber(String carNumber) throws Exception;

    public int addCarInfo(CarInfo carInfo) throws Exception;

    public int deleteCarInfoByCarId(int carId) throws Exception;

    public CarInfo getCarInfoByCarId(int carId) throws Exception;

    public int updateCarInfoByCarId(CarInfo carInfo) throws Exception;

    public List<CarInfo> findCarInfoByMultiCondition(CarInfoTableMessage carInfoTableMessage) throws Exception;

}
