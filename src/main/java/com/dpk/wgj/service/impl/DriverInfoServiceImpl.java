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

    @Override
    public DriverInfo getDriverInfoByWxId(String driverWxId) {

        DriverInfo driverInfo;

        try {
            driverInfo = driverInfoMapper.getDriverInfoByWxId(driverWxId);
            return driverInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int addDriverInfo(DriverInfo driverInfo) {

        int addStatus = 0;

        try {
            addStatus = driverInfoMapper.addDriverInfo(driverInfo);
            return addStatus;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  0;
    }

    @Override
    public int updateDriverStatus(DriverInfo driverInfo) {
        int upStatus = 0;

        try {
            upStatus = driverInfoMapper.updateDriverStatus(driverInfo);
            return upStatus;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  0;
    }

}
