package com.dpk.wgj.mapper;

import com.dpk.wgj.bean.MonthInfo;
import com.dpk.wgj.bean.OrderInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SelectParamMapper {
    public MonthInfo getMonthOrderInfoByYear(int year) throws Exception;
//    public int getOrderByMonth(int month) throws Exception;
//    public List<OrderInfo> getOrderByDriverId(int driver_id) throws Exception;
//    public List<OrderInfo> getOrderBycarNumber(int car_id) throws Exception;
//    public List<OrderInfo> getAllOrderNum() throws Exception;
//    public List<OrderInfo> findCarInfoByMultiCondition( int carid,int driverid) throws Exception;
}
