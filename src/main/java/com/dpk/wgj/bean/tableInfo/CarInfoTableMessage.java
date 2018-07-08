package com.dpk.wgj.bean.tableInfo;

import com.dpk.wgj.bean.CarInfo;

/**
 * Created by zhoulin on 2018/7/8.
 * 车辆管理表信息
 */
public class CarInfoTableMessage extends TableMessage{

    private CarInfo carInfo = new CarInfo();

    public CarInfo getCarInfo() {
        return carInfo;
    }

    public void setCarInfo(CarInfo carInfo) {
        this.carInfo = carInfo;
    }

}
