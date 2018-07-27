package com.dpk.wgj.utils;

import com.dpk.wgj.bean.DriverInfo;
import com.dpk.wgj.service.DriverInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BestPathUtil {

    @Autowired
    private DriverInfoService driverInfoService;

    /**
     * 得到最近 司机id
     * @param passengerLocation
     * @return
     */
    public Integer getBestDriver(String passengerLocation){

        Integer driverId = null;

        List<DriverInfo> list;
        try {
            list = driverInfoService.getAllDriverInfo();

            String[] pLocations = passengerLocation.split(",");
            double[] pPoints = new double[2];
            pPoints[0] = Double.valueOf(pLocations[0]);
            pPoints[1] = Double.valueOf(pLocations[1]);

            for (int i = 0;i < 3;i ++){
                for (DriverInfo driverInfo : list) {
                    String[] locations = driverInfo.getDriverLocation().split(",");
                    if (Math.abs(Double.valueOf(locations[0]) - pPoints[0]) <= 0.035
                            && Math.abs(Double.valueOf(locations[1]) - pPoints[1]) <= 0.035);
                    driverId = driverInfo.getDriverId();
                    break;
                }
                if (driverId != null){
                    return driverId;
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }

    }

}
