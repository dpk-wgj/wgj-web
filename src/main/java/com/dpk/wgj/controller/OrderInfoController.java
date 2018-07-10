package com.dpk.wgj.controller;

import com.dpk.wgj.bean.DTO.UserDTO;
import com.dpk.wgj.bean.Message;
import com.dpk.wgj.bean.OrderInfo;
import com.dpk.wgj.bean.Passenger;
import com.dpk.wgj.bean.tableInfo.LocationMessage;
import com.dpk.wgj.service.OrderInfoService;
import com.dpk.wgj.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
//@RequestMapping(value = "/admin/order")
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private PassengerService passengerService;

    /**
     * 多条件查询车辆轨迹
     * @param locationMessage
     * @return
     */
    @RequestMapping(value = "/admin/order/getLocationInfoByDate", method = RequestMethod.POST)
    public Message getLocationInfoByDate(@RequestBody LocationMessage locationMessage){
        try {
//            String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(locationMessage.getStartTime());//将时间格式转换成符合Timestamp要求的格式.
//            Timestamp newdate = Timestamp.valueOf(nowTime);//把时间转换
//
//            locationMessage.setStartTime(newdate);

            List<OrderInfo> locations = orderInfoService.getLocationInfoByDate(locationMessage);
            if (locations != null){
                return new Message(Message.SUCCESS, "获取车辆轨迹 >> 成功", locations);
            }
            return new Message(Message.FAILURE, "获取车辆轨迹 >> 失败", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "获取车辆轨迹 >> 异常", e.getMessage());
        }
    }

    /**
     * 乘客端 一键呼车 功能
     * @param locationInfo
     */
    @RequestMapping(value = "/api/passenger/addOrderInfo", method = RequestMethod.POST)
    public Message addOrderInfo(@RequestBody String locationInfo){

        OrderInfo orderInfo = new OrderInfo();
        int addStatus = 0;

        UserDTO userInfo = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getDetails();
        int passengerId = userInfo.getUserId();

        orderInfo.setPassengerId(passengerId);
        orderInfo.setLocationInfo(locationInfo);
        orderInfo.setStartTime(new Date());

        // 订单切换至 下单状态
        orderInfo.setOrderStatus(0);

        try {
            addStatus = orderInfoService.addOrderInfo(orderInfo);
            if (addStatus == 1){
                Passenger passenger = new Passenger();
                passenger.setPassengerId(passengerId);
                //乘客状态切换至 服务中
                passenger.setPassengerStatus(1);
                int upStatus = passengerService.updatePassengerStatus(passenger);
                if (upStatus == 1){
                    return new Message(Message.SUCCESS, "创建订单信息&切换用户状态 >> 成功", addStatus + " " + upStatus);
                }
                return new Message(Message.FAILURE, "创建订单信息&切换用户状态 >> 失败 ", addStatus + " " + upStatus);
            }else {
                return new Message(Message.FAILURE, "创建订单信息 >> 失败", addStatus);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "创建订单信息 >> 异常", e.getMessage());
        }
    }

    /**
     * 乘客端 >> 获取订单列表
     * @return
     */
    @RequestMapping(value = "/api/passenger/getOrderInfoByPassengerId", method = RequestMethod.GET)
    public Message getOrderInfoByPassengerId(){

        List<OrderInfo> orderInfos;

        UserDTO userInfo = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getDetails();
        int passengerId = userInfo.getUserId();

        try {
            orderInfos = orderInfoService.getOrderInfoByPassengerId(passengerId);
            if (orderInfos != null){
                return new Message(Message.SUCCESS, "乘客端 >> 获取订单列表 >> 成功", orderInfos);
            } else {
                return new Message(Message.FAILURE, "乘客端 >> 获取订单列表 >> 失败", orderInfos);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "乘客端 >> 获取订单列表 >> 异常", e.getMessage());
        }

    }



}
