package com.dpk.wgj.bean.DTO;

import com.dpk.wgj.bean.CarInfo;
import com.dpk.wgj.bean.DriverInfo;

import java.io.Serializable;

/**
 * Created by zhoulin on 2018/7/8.
 * CarInfoDTO
 */
public class CarInfoDTO implements Serializable {

    private CarInfo carInfo;

    private DriverInfo driverInfo;

    public CarInfoDTO() {
    }

    public CarInfoDTO(CarInfo carInfo, DriverInfo driverInfo) {
        this.carInfo = carInfo;
        this.driverInfo = driverInfo;
    }

    public CarInfo getCarInfo() {
        return carInfo;
    }

    public void setCarInfo(CarInfo carInfo) {
        this.carInfo = carInfo;
    }

    public DriverInfo getDriverInfo() {
        return driverInfo;
    }

    public void setDriverInfo(DriverInfo driverInfo) {
        this.driverInfo = driverInfo;
    }
}
