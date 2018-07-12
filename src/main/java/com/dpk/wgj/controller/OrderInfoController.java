package com.dpk.wgj.controller;

import com.dpk.wgj.bean.*;
import com.dpk.wgj.bean.DTO.AccessDriverDTO;
import com.dpk.wgj.bean.DTO.OrderInfoDTO;
import com.dpk.wgj.bean.DTO.UserDTO;
import com.dpk.wgj.bean.tableInfo.LocationMessage;
import com.dpk.wgj.bean.tableInfo.OrderInfoTableMessage;
import com.dpk.wgj.bean.tableInfo.OrderMessage;
import com.dpk.wgj.service.CarInfoService;
import com.dpk.wgj.service.DriverInfoService;
import com.dpk.wgj.service.OrderInfoService;
import com.dpk.wgj.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
//@RequestMapping(value = "/admin/order")
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private DriverInfoService driverInfoService;

    @Autowired
    private CarInfoService carInfoService;

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
     */
    @RequestMapping(value = "/api/passenger/addOrderInfo", method = RequestMethod.POST)
    @Transactional
    public Message addOrderInfo(@RequestBody OrderMessage orderMessage){

        OrderInfo orderInfo = new OrderInfo();
        int addStatus = 0;

        UserDTO userInfo = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getDetails();
        int passengerId = userInfo.getUserId();

        orderInfo.setStartLocation(orderMessage.getStartLocation());
        orderInfo.setEndLocation(orderMessage.getEndLocation());
        orderInfo.setPassengerId(passengerId);
        orderInfo.setLocationInfo(orderMessage.getLocationInfo());
        orderInfo.setStartTime(new Date());

        // 订单切换至 下单状态
        orderInfo.setOrderStatus(0);

        try {
            addStatus = orderInfoService.addOrderInfo(orderInfo);
            if (addStatus == 1){
                Passenger passenger = new Passenger();
                passenger.setPassengerId(passengerId);
                //乘客状态切换至 服务中
                passenger.setPassengerStatus(0);
                int upStatus = passengerService.updatePassengerStatus(passenger);
                if (upStatus == 1){
                    OrderInfo targetOrderInfo = orderInfoService.getOrderInfoByOrderId(orderInfo.getOrderId());
//                    DriverInfo driverInfo = driverInfoService.getDriverInfoByDriverId(targetOrderInfo.getDriverId());
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("OrderInfo", targetOrderInfo);
//                    map.put("DriverInfo", driverInfo);
                    return new Message(Message.SUCCESS, "创建订单信息&切换用户状态 >> 成功 >> 获得目标订单", targetOrderInfo);
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
    @Transactional
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

    /**
     * 司机端 >> 获取订单列表
     * @return
     */
    @RequestMapping(value = "/api/driver/getOrderInfoByDriverId", method = RequestMethod.GET)
    public Message getOrderInfoByDriverId(){

        List<OrderInfo> orderInfos;

        UserDTO userInfo = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getDetails();
        int driverId = userInfo.getUserId();

        try {
            orderInfos = orderInfoService.getOrderInfoByDriverId(driverId);
            if (orderInfos != null){
                return new Message(Message.SUCCESS, "司机端 >> 获取订单列表 >> 成功", orderInfos);
            } else {
                return new Message(Message.FAILURE, "司机端 >> 获取订单列表 >> 失败", orderInfos);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "司机端 >> 获取订单列表 >> 异常", e.getMessage());
        }

    }

    /**
     * 司机端 >> 申请改派
     */
    @RequestMapping(value = "/api/driver/updateOrderInfoByOrderId", method = RequestMethod.POST)
    @Transactional
    public Message updateOrderInfoByOrderId(@RequestBody OrderInfo order){
        int upStatus = 0;

        // 防止恶意注入
        UserDTO userInfo = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getDetails();
        int driverId = userInfo.getUserId();

        try {
            OrderInfo orderInfo = orderInfoService.getOrderInfoByOrderId(order.getOrderId());
            if (orderInfo != null && driverId == orderInfo.getDriverId()){
                orderInfo.setDriverId(0);
                orderInfo.setOrderStatus(0);
                upStatus = orderInfoService.updateOrderInfoByOrderId(orderInfo);
                if (upStatus == 1){
                    // 重新匹配新的司机

                    DriverInfo driverInfo = new DriverInfo();
                    driverInfo.setDriverId(driverId);
                    // 用时切换司机状态 至 服务前
                    driverInfo.setDriverStatus(1);
                    int upDriverStatus = driverInfoService.updateDriverStatus(driverInfo);
                    if(upDriverStatus == 1){
                        return new Message(Message.SUCCESS, "司机端 >> 申请改派 && 司机状态切换至 服务前 >> 成功", upStatus);
                    }
                }
            }
            return new Message(Message.FAILURE, "司机端 >> 申请改派 >> 失败", "错误请求");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "司机端 >> 申请改派 >> 异常", e.getMessage());
        }

    }

    /**
     * 司机端 >> 接到乘客后确认时 调用
     */
    @RequestMapping(value = "/api/driver/accessToServiceForDriver", method = RequestMethod.POST)
    @Transactional
    public Message accessToServiceForDriver(@RequestBody AccessDriverDTO accessDriverDTO){
        int upStatus = 0;

        // 防止恶意注入
        UserDTO userInfo = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getDetails();
        int driverId = userInfo.getUserId();
        String driverWxId = userInfo.getUsername();

        try {
            OrderInfo orderInfo = orderInfoService.getOrderInfoByOrderId(accessDriverDTO.getOrderId());

            DriverInfo driverInfo = driverInfoService.getDriverInfoByWxId(driverWxId);

            // 当司机当前位置 与 用户所定的起始位置 一致才能切换 订单状态
            if (orderInfo != null && driverId == orderInfo.getDriverId() && accessDriverDTO.getCurrentLocation().equals(accessDriverDTO.getTargetLocation())){
                orderInfo.setOrderStatus(2);
                upStatus = orderInfoService.updateOrderInfoByOrderId(orderInfo);
                if (upStatus == 1){
                    return new Message(Message.SUCCESS, "司机端 >> 已接到乘客 >> 进入派送状态", upStatus);
                }
            }
            return new Message(Message.FAILURE, "司机端 >> 未到目的地 ", "错误请求");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "司机端 >> 申请改派 >> 异常", e.getMessage());
        }

    }

    /**
     * 乘客端 >> 取消订单
     */
    @RequestMapping(value = "/api/passenger/updateOrderInfoByOrderId", method = RequestMethod.POST)
    @Transactional
    public Message cancelOfOrderForPassenger (@RequestBody int orderInfoId){
        int upStatus = 0;

        // 防止恶意注入
        UserDTO userInfo = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getDetails();
        int passengerId = userInfo.getUserId();

        try {
            OrderInfo orderInfo = orderInfoService.getOrderInfoByOrderId(orderInfoId);
            int driverId = orderInfo.getDriverId();
            if (orderInfo != null && passengerId == orderInfo.getPassengerId()){
                orderInfo.setOrderStatus(4);
                upStatus = orderInfoService.updateOrderInfoByOrderId(orderInfo);
                if (upStatus == 1){

                    //乘客状态切换至 服务后 同时也要修改司机状态为 接单前
                    DriverInfo driverInfo = new DriverInfo();
                    driverInfo.setDriverId(driverId);
                    driverInfo.setDriverStatus(0);

                    Passenger passenger = new Passenger();
                    passenger.setPassengerId(passengerId);
                    passenger.setPassengerStatus(2);

                    int upPassengerStatus = passengerService.updatePassengerStatus(passenger);
                    int upDriverStatus = driverInfoService.updateDriverStatus(driverInfo);
                    if (upPassengerStatus == 1 && upDriverStatus == 1){
                        return new Message(Message.SUCCESS, "乘客端 >> 取消订单 && 乘客/司机状态切换 >> 成功", upStatus + upPassengerStatus + upDriverStatus);
                    }
                }
            }
            return new Message(Message.FAILURE, "乘客端 >> 取消订单 >> 失败", "错误请求");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "乘客端 >> 取消订单 >> 异常", e.getMessage());
        }

    }

    /**
     * 司机端 >> 送达目的地后 确认
     * @return
     */
    @RequestMapping(value = "/api/driver/arrivedTargetLocation", method = RequestMethod.POST)
    @Transactional
    public Message arrivedTargetLocation (@RequestBody AccessDriverDTO accessDriverDTO){
        int upStatus = 0;

        // 防止恶意注入
        UserDTO userInfo = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getDetails();
        int driverId = userInfo.getUserId();
        String driverWxId = userInfo.getUsername();

        try {
            OrderInfo orderInfo = orderInfoService.getOrderInfoByOrderId(accessDriverDTO.getOrderId());

            DriverInfo driverInfo = driverInfoService.getDriverInfoByWxId(driverWxId);

            // 当司机当前位置 与 用户所定的目的位置 一致才能切换 订单状态
            if (orderInfo != null && driverId == orderInfo.getDriverId() && accessDriverDTO.getCurrentLocation().equals(accessDriverDTO.getTargetLocation())){
                orderInfo.setOrderStatus(3);
                upStatus = orderInfoService.updateOrderInfoByOrderId(orderInfo);
                if (upStatus == 1){

                    //乘客状态切换至 服务后 同时也要修改司机状态为 接单前
                    int passengerId = orderInfo.getPassengerId();
                    Passenger passenger = new Passenger();
                    passenger.setPassengerId(passengerId);
                    passenger.setPassengerStatus(2);
                    driverInfo.setDriverStatus(0);

                    int upDriverStatus = driverInfoService.updateDriverStatus(driverInfo);
                    int upPassengerStatus = passengerService.updatePassengerStatus(passenger);

                    if (upPassengerStatus == 1 && upDriverStatus == 1){
                        return new Message(Message.SUCCESS, "司机端 >> 完成订单 && 乘客/用户 状态切换 >> 成功", upStatus + upPassengerStatus);
                    }
                }
            }
            return new Message(Message.FAILURE, "司机端 >> 完成订单 && 乘客/用户 状态切换 >> 失败 ", "错误请求");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "司机端 >> 申请改派 >> 异常", e.getMessage());
        }

    }

    /**
     * 司机端 >> 获得订单id
     * @param tableMessage
     * @return
     */
    @RequestMapping(value = "/api/driver/getOrderIdForDriver", method = RequestMethod.POST)
    @Transactional
    public Message getOrderIdForDriver (@RequestBody OrderInfoTableMessage tableMessage){
        List<OrderInfo> orderInfos = new ArrayList<>();
        List<OrderInfoDTO> infoDTOList = new ArrayList<>();

        Map<String, Object> map = new HashMap<>();

//        tableMessage.getDriverInfo().setDriverName("%" + tableMessage.getDriverInfo().getDriverName() + "%");
//        tableMessage.getPassenger().setPassengerPhoneNumber("%" + tableMessage.getPassenger().getPassengerPhoneNumber() + "%");
//        tableMessage.getCarInfo().setCarNumber("%" + tableMessage.getCarInfo().getCarNumber() + "%");

        try {
            orderInfos = orderInfoService.findOrderInfoByMultiCondition(tableMessage);
            if (orderInfos != null){
                for(OrderInfo orderInfo : orderInfos){
                    int driverId = orderInfo.getDriverId();
                    int passengerId = orderInfo.getPassengerId();
                    DriverInfo driverInfo = driverInfoService.getDriverInfoByDriverId(driverId);
                    Passenger passenger = passengerService.getPassengerByPassengerId(passengerId);
                    CarInfo carInfo = carInfoService.getCarInfoByCarId(driverInfo.getCarId());
                    // 判断车辆所有权 未完成

                    OrderInfoDTO orderInfoDTO = new OrderInfoDTO(orderInfo, carInfo, driverInfo, passenger);
                    infoDTOList.add(orderInfoDTO);
                }
                int count = orderInfoService.findOrderInfoByMultiConditionCount(tableMessage);
                map.put("orderList", infoDTOList);
                map.put("count", count);
                return new Message(Message.SUCCESS, "司机端 >> 获得订单id >> 成功", map);
            }
            return new Message(Message.FAILURE, "司机端 >> 获得订单id >> 成功", "无查询结果");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "司机端 >> 获得订单id >> 异常", "请求异常 ");
        }
    }

    /**
     * 后台端 >> 多条件查询订单
     * @return
     */
    @RequestMapping(value = "/admin/findOrderInfoByMultiCondition", method = RequestMethod.POST)
    @Transactional
    public Message findOrderInfoByMultiCondition(@RequestBody OrderInfoTableMessage tableMessage){

        List<OrderInfo> orderInfos = new ArrayList<>();
        List<OrderInfoDTO> infoDTOList = new ArrayList<>();

        Map<String, Object> map = new HashMap<>();

        tableMessage.getDriverInfo().setDriverName("%" + tableMessage.getDriverInfo().getDriverName() + "%");
        tableMessage.getPassenger().setPassengerPhoneNumber("%" + tableMessage.getPassenger().getPassengerPhoneNumber() + "%");
        tableMessage.getCarInfo().setCarNumber("%" + tableMessage.getCarInfo().getCarNumber() + "%");

        try {
            orderInfos = orderInfoService.findOrderInfoByMultiCondition(tableMessage);
            if (orderInfos != null){
                for(OrderInfo orderInfo : orderInfos){
                    int driverId = orderInfo.getDriverId();
                    int passengerId = orderInfo.getPassengerId();
                    DriverInfo driverInfo = driverInfoService.getDriverInfoByDriverId(driverId);
                    Passenger passenger = passengerService.getPassengerByPassengerId(passengerId);
                    CarInfo carInfo = carInfoService.getCarInfoByCarId(driverInfo.getCarId());
                    // 判断车辆所有权 未完成

                    OrderInfoDTO orderInfoDTO = new OrderInfoDTO(orderInfo, carInfo, driverInfo, passenger);
                    infoDTOList.add(orderInfoDTO);
                }
                int count = orderInfoService.findOrderInfoByMultiConditionCount(tableMessage);
                map.put("orderList", infoDTOList);
                map.put("count", count);
                return new Message(Message.SUCCESS, "后台端 >> 多条件查询订单 >> 成功", map);
            }
            return new Message(Message.FAILURE, "后台端 >> 多条件查询订单 >> 成功", "无查询结果");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "后台端 >> 多条件查询订单 >> 异常", "请求异常 ");
        }
    }

}
