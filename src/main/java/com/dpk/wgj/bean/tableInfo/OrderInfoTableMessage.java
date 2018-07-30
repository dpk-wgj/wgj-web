package com.dpk.wgj.bean.tableInfo;

import com.dpk.wgj.bean.CarInfo;
import com.dpk.wgj.bean.DriverInfo;
import com.dpk.wgj.bean.OrderInfo;
import com.dpk.wgj.bean.Passenger;

import java.io.Serializable;

/**
 * Created by zhoulin on 2018/7/11.
 * 订单检索信息类
 */
public class OrderInfoTableMessage extends TableMessage implements Serializable{

    private OrderInfo orderInfo;

    private CarInfo carInfo;

    private DriverInfo driverInfo;

    private Passenger passenger;

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
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

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }
}
