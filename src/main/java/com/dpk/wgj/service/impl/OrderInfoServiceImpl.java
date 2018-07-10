package com.dpk.wgj.service.impl;

import com.dpk.wgj.bean.OrderInfo;
import com.dpk.wgj.bean.tableInfo.LocationMessage;
import com.dpk.wgj.mapper.OrderInfoMapper;
import com.dpk.wgj.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderInfoServiceImpl implements OrderInfoService{

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Override
    public List<OrderInfo> getLocationInfoByDate(LocationMessage locationMessage){

        List<OrderInfo> orderInfos = new ArrayList<>();

        try {
            orderInfos = orderInfoMapper.getLocationInfoByDate(locationMessage);
            return orderInfos;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int addOrderInfo(OrderInfo orderInfo) {

        int addStatus = 0;

        try {
            addStatus = orderInfoMapper.addOrderInfo(orderInfo);
            return addStatus;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int deleteOrderInfoByOrderId(int orderId) {
        int delStatus = 0;

        try {
            delStatus = orderInfoMapper.deleteOrderInfoByOrderId(orderId);
            return delStatus;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int updateOrderInfoByOrderId(OrderInfo orderInfo) {
        int upStatus = 0;

        try {
            upStatus = orderInfoMapper.updateOrderInfoByOrderId(orderInfo);
            return upStatus;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<OrderInfo> getOrderInfoByDriverId(int driverId) {
        List<OrderInfo> orderInfos;

        try {
            orderInfos = orderInfoMapper.getOrderInfoByDriverId(driverId);
            return orderInfos;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<OrderInfo> getOrderInfoByPassengerId(int passengerId) {
        List<OrderInfo> orderInfos;

        try {
            orderInfos = orderInfoMapper.getOrderInfoByPassengerId(passengerId);
            return orderInfos;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public OrderInfo getOrderInfoByOrderId(int orderId) {
        OrderInfo orderInfo;

        try {
            orderInfo = orderInfoMapper.getOrderInfoByOrderId(orderId);
            return orderInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
