package com.dpk.wgj.service.impl;

import com.dpk.wgj.bean.CarInfo;
import com.dpk.wgj.bean.DriverInfo;
import com.dpk.wgj.mapper.CarInfoMapper;
import com.dpk.wgj.mapper.DriverInfoMapper;
import com.dpk.wgj.service.CarInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarInfoServiceImpl implements CarInfoService{

    @Autowired
    private CarInfoMapper carInfoMapper;

    @Autowired
    private DriverInfoMapper driverInfoMapper;

    private final Logger logger = LoggerFactory.getLogger(CarInfoServiceImpl.class);

    @Override
    public CarInfo getCarInfoByCarNumber(String carNumber) {

        CarInfo carInfo;
        DriverInfo driverInfo;

        try {
            carInfo = carInfoMapper.getCarInfoByCarNumber(carNumber);
            driverInfo = driverInfoMapper.getDriverInfoByCarId(carInfo.getCarId());

            carInfo.setDriverInfo(driverInfo);

            return carInfo;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }
}
