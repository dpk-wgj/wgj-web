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
}