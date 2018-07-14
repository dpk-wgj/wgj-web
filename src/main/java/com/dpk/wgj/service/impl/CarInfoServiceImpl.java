package com.dpk.wgj.service.impl;

import com.dpk.wgj.bean.CarInfo;
import com.dpk.wgj.bean.DTO.CarInfoDTO;
import com.dpk.wgj.bean.DriverInfo;
import com.dpk.wgj.bean.tableInfo.CarInfoTableMessage;
import com.dpk.wgj.mapper.CarInfoMapper;
import com.dpk.wgj.mapper.DriverInfoMapper;
import com.dpk.wgj.service.CarInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarInfoServiceImpl implements CarInfoService{

    @Autowired
    private CarInfoMapper carInfoMapper;

    private final Logger logger = LoggerFactory.getLogger(CarInfoServiceImpl.class);

    @Override
    public CarInfo getCarInfoByCarNumber(String carNumber) {

        CarInfo carInfo;

        try {
            carInfo = carInfoMapper.getCarInfoByCarNumber(carNumber);

            return carInfo;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public int addCarInfo(CarInfo carInfo) {

        int addStatus = 0;

        try {
            addStatus = carInfoMapper.addCarInfo(carInfo);
            return addStatus;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addStatus;
    }

    @Override
    public int deleteCarInfoByCarId(int carId) {

        int delStatus = 0;

        try {
            delStatus = carInfoMapper.deleteCarInfoByCarId(carId);
            return delStatus;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return delStatus;
    }

    @Override
    public CarInfo getCarInfoByCarId(int carId) {

        CarInfo carInfo;

        try {
            carInfo = carInfoMapper.getCarInfoByCarId(carId);
            return carInfo;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }


    @Override
    public int updateCarInfoByCarId(CarInfo carInfo) {
        int upStatus = 0;

        try {
            upStatus = carInfoMapper.updateCarInfoByCarId(carInfo);
            return upStatus;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return upStatus;
    }

    @Override
    public List<CarInfo> findCarInfoByMultiCondition(CarInfoTableMessage carInfoTableMessage) {

        List<CarInfo> carInfos;

        try {
            carInfos = carInfoMapper.findCarInfoByMultiCondition(carInfoTableMessage);
            return carInfos;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int findCarInfoByMultiConditionCount(CarInfoTableMessage carInfoTableMessage) {

        int count = 0;

        try {
            count = carInfoMapper.findCarInfoByMultiConditionCount(carInfoTableMessage);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public List<CarInfo> getCarDriverIdInfo(){
        List<CarInfo> carInfo;
        try{
            carInfo = carInfoMapper.getCarDriverIdInfo();
            return carInfo;

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public CarInfo getCarInfoNoCompatibleByCarNumber(String carNumber) {
        CarInfo carInfo;
        try {
            carInfo = carInfoMapper.getCarInfoNoCompatibleByCarNumber(carNumber);
            return carInfo;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }
    @Override
    public CarInfo getCarInfoByDriverId(int DriverId) {

        CarInfo carInfo;

        try {
            carInfo = carInfoMapper.getCarInfoByDriverId(DriverId);
            return carInfo;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }
    @Override
    public int updateCarInfoDriverIdByCarId(CarInfo carInfo) {
        int upStatus = 0;

        try {
            upStatus = carInfoMapper.updateCarInfoDriverIdByCarId(carInfo);
            return upStatus;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return upStatus;
    }
    @Override
    public int updateDeleteCarCarInfoDriverIdByCarId(CarInfo carInfo) {
        int upStatus = 0;

        try {
            upStatus = carInfoMapper.updateDeleteCarCarInfoDriverIdByCarId(carInfo);
            return upStatus;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return upStatus;
    }

}
