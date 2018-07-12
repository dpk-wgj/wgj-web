package com.dpk.wgj.service;

import com.dpk.wgj.bean.CarInfo;
import com.dpk.wgj.bean.Location;
import com.dpk.wgj.bean.tableInfo.CarInfoTableMessage;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CarInfoService {

    public CarInfo getCarInfoByCarNumber(String carNumber) throws Exception;

    public int addCarInfo(CarInfo carInfo) throws Exception;

    public int deleteCarInfoByCarId(int carId) throws Exception;

    public CarInfo getCarInfoByCarId(int carId) throws Exception;

    public int updateCarInfoByCarId(CarInfo carInfo) throws Exception;

    public List<CarInfo> findCarInfoByMultiCondition(CarInfoTableMessage carInfoTableMessage) throws Exception;

    public int findCarInfoByMultiConditionCount(CarInfoTableMessage carInfoTableMessage) throws Exception;

    public List<CarInfo> getCarDriverIdInfo() throws  Exception;

}
