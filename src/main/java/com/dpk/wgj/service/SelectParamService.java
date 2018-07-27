package com.dpk.wgj.service;

import com.dpk.wgj.bean.OrderInfo;
import com.dpk.wgj.bean.Param;

import java.util.List;

public interface SelectParamService {

        public int[] getOrderByYear(Param yarr) throws Exception;

        public int[] getOrderByMonth(Param month) throws Exception;

        public List<OrderInfo> getOrderByDriverId(int driverId) throws Exception;

        public List<OrderInfo> getOrderBycarNumber(int carId) throws Exception;

        public List<OrderInfo> getAllOrderNum() throws Exception;

//        public List<OrderInfo> findOrderInfoByMultiCondition(MultiCondition multiCondition) throws Exception;

    }

