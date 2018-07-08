package com.dpk.wgj.service.impl;

import com.dpk.wgj.bean.DriverInfo;
import com.dpk.wgj.mapper.DriverInfoMapper;
import com.dpk.wgj.service.DriverInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverInfoServiceImpl implements DriverInfoService {
    @Autowired
    private DriverInfoMapper driverInfoMapper;

    private final Logger logger = LoggerFactory.getLogger(DriverInfoServiceImpl.class);

    @Override
    public DriverInfo getDriverInfo(String driverName, String driverPhoneNumber, int driverLevelStar) throws Exception{
        DriverInfo driverInfo;
        try {
            driverInfo = driverInfoMapper.getDriverInfo(driverName,driverPhoneNumber,driverLevelStar);
            return driverInfo;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }
}
