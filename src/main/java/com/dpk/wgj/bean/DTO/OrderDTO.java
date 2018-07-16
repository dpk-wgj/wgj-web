package com.dpk.wgj.bean.DTO;

import com.dpk.wgj.bean.CarInfo;
import com.dpk.wgj.bean.DriverInfo;
import com.dpk.wgj.bean.OrderInfo;

/**
 * Created by zhoulin on 2018/7/13.
 * 说明:
 */
public class OrderDTO {

    private OrderInfo orderInfo;

    private DriverInfo driverInfo;

    private CarInfo carInfo;

    public CarInfo getCarInfo() {
        return carInfo;
    }

    public void setCarInfo(CarInfo carInfo) {
        this.carInfo = carInfo;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    public DriverInfo getDriverInfo() {
        return driverInfo;
    }

    public void setDriverInfo(DriverInfo driverInfo) {
        this.driverInfo = driverInfo;
    }
}
