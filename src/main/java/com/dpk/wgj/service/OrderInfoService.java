package com.dpk.wgj.service;

import com.dpk.wgj.bean.OrderInfo;
import com.dpk.wgj.bean.tableInfo.LocationMessage;
import com.dpk.wgj.bean.tableInfo.OrderInfoTableMessage;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderInfoService {

    /**
     * 获取所有车辆的地理位置
     */
    public List<OrderInfo> getLocationInfoByDate(LocationMessage locationMessage) throws Exception;

    /**
     * 添加订单信息
     */
    public int addOrderInfo(OrderInfo orderInfo) throws Exception;

    /**
     * 删除订单信息
     */
    public int deleteOrderInfoByOrderId(int orderId) throws Exception;

    /**
     * 更新订单信息
     */
    public int updateOrderInfoByOrderId(OrderInfo orderInfo) throws Exception;

    /**
     * 根据司机Id获取订单信息
     */
    public List<OrderInfo> getOrderInfoByDriverId(int driverId) throws Exception;

    /**
     * 根据乘客Id获取订单信息
     */
    public List<OrderInfo> getOrderInfoByPassengerId(int passengerId) throws Exception;

    /**
     * 根据订单Id获取订单信息
     */
    public OrderInfo getOrderInfoByOrderId(int orderId) throws Exception;

    /**
     * 订单多条件查询功能
     */
    public List<OrderInfo> findOrderInfoByMultiCondition(OrderInfoTableMessage orderInfoTableMessage) throws Exception;

    /**
     * 订单多条件查询功能 -- 订单总数
     */
    public int findOrderInfoByMultiConditionCount(OrderInfoTableMessage orderInfoTableMessage) throws Exception;

    /**
     * 根据状态查询订单
     */
    public List<OrderInfo> findOrderInfoByOrderStatus(int orderStatus) throws Exception;

    /**
     * 根据订单信息查询订单
     */
    public List<OrderInfo> findOrderListByOrderInfo(OrderInfo orderInfo) throws Exception;


}
