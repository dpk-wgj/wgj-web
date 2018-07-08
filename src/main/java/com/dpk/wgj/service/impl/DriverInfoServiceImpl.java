package com.dpk.wgj.service.impl;

import com.dpk.wgj.bean.DriverInfo;
import com.dpk.wgj.mapper.DriverInfoMapper;
import com.dpk.wgj.service.DriverInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DriverInfoServiceImpl implements DriverInfoService {
    @Autowired
    private DriverInfoMapper driverInfoMapper;

    private final Logger logger = LoggerFactory.getLogger(DriverInfoServiceImpl.class);

    /**
     * Created by hlx on 2018/7/8.
     * 说明:
     */
    @Override
    @Transactional
    public DriverInfo getDriverInfoByDriverName(String driverName){
        DriverInfo driverInfo;
        try {
            driverInfo = driverInfoMapper.getDriverInfoByDriverName(driverName);
            return driverInfo;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }
    @Override
    @Transactional
    public DriverInfo getDriverInfoByDriverPhoneNumber(String driverPhoneNumber){
        DriverInfo driverInfo;
        try {
            driverInfo = driverInfoMapper.getDriverInfoByDriverPhoneNumber(driverPhoneNumber);
            return driverInfo;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @Transactional
    public DriverInfo getDriveInfoByDriverLevelStar(int driverLevelStar){
        DriverInfo driverInfo;
        try {
            driverInfo = driverInfoMapper.getDriveInfoByDriverLevelStar(driverLevelStar);
            return driverInfo;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

/**
 * Created by zhoulin on 2018/7/8.
 * 说明:
 */
    @Override
    public DriverInfo getDriverInfoByCarId(int carId) {
        DriverInfo driverInfo;
        try {
            driverInfo = driverInfoMapper.getDriverInfoByCarId(carId);
            return driverInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
