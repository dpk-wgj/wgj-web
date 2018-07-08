package com.dpk.wgj.service.impl;

import com.dpk.wgj.bean.DriverInfo;
import com.dpk.wgj.mapper.DriverInfoMapper;
import com.dpk.wgj.service.DriverInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhoulin on 2018/7/8.
 * 说明:
 */
@Service
public class DriverInfoServiceImpl implements DriverInfoService {

    @Autowired
    private DriverInfoMapper driverInfoMapper;

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

    @Override
    public List<DriverInfo> getAllCarLocation() {

        List<DriverInfo> driverInfoList;

        try {
            driverInfoList = driverInfoMapper.getAllCarLocation();
            return driverInfoList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
