package com.dpk.wgj.mapper;

import com.dpk.wgj.bean.OrderInfo;
import com.dpk.wgj.bean.tableInfo.LocationMessage;
import com.dpk.wgj.bean.tableInfo.OrderInfoTableMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface OrderInfoMapper {

    public List<OrderInfo> getLocationInfoByDate(LocationMessage locationMessage) throws Exception;

    public int addOrderInfo(OrderInfo orderInfo) throws Exception;

    public int deleteOrderInfoByOrderId(int orderId) throws Exception;

    public int updateOrderInfoByOrderId(OrderInfo orderInfo) throws Exception;

    public List<OrderInfo> getOrderInfoByDriverId(int driverId) throws Exception;

    public List<OrderInfo> getOrderInfoByPassengerId(int passengerId) throws Exception;

    public OrderInfo getOrderInfoByOrderId(int orderId) throws Exception;

    public List<OrderInfo> findOrderInfoByMultiCondition(OrderInfoTableMessage orderInfoTableMessage) throws Exception;

    public int findOrderInfoByMultiConditionCount(OrderInfoTableMessage orderInfoTableMessage) throws Exception;

    public List<OrderInfo> findOrderInfoByOrderStatus(int orderStatus) throws Exception;
}
