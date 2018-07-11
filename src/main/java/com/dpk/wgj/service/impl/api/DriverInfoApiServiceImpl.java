package com.dpk.wgj.service.impl.api;


import com.dpk.wgj.bean.DriverInfo;
import com.dpk.wgj.mapper.api.DriverInfoApiMapper;
import com.dpk.wgj.service.DriverInfoApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverInfoApiServiceImpl implements DriverInfoApiService {

    @Autowired
    private DriverInfoApiMapper driverInfoApiMapper;

    private final Logger logger = LoggerFactory.getLogger(DriverInfoApiServiceImpl.class);
    @Override
    public DriverInfo getDriverInfoByDriverId(int driverId) {
        DriverInfo driverInfo;
        try {
            driverInfo = driverInfoApiMapper.getDriverInfoByDriverId(driverId);
            return driverInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public int updateApiDriverInfoByDriverId(DriverInfo driverInfo) {
        int upApiStatus = 0;

        try {
            upApiStatus = driverInfoApiMapper.updateApiDriverInfoByDriverId(driverInfo);
            return upApiStatus;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return upApiStatus;
    }

}
