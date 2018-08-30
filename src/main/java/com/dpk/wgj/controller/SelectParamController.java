package com.dpk.wgj.controller;

import com.dpk.wgj.bean.*;
import com.dpk.wgj.bean.DTO.CarInfoDTO;
import com.dpk.wgj.bean.tableInfo.CarInfoTableMessage;
//import com.dpk.wgj.bean.tableInfo.MultiCondition;
import com.dpk.wgj.service.SelectParamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.dpk.wgj.bean.Message.*;

/**

 * @Description:    前后端均已跑通

 * @Author:      iauhsoaix

 * @CreateDate:     2018/7/10 7:31

 */
@RestController
@RequestMapping(value = "/public/count")
public class SelectParamController {
    @Autowired
    private SelectParamService selectParamService;

    private final Logger logger = LoggerFactory.getLogger(AdminInfoController.class);
    @RequestMapping(value = "/getOrderByYear", method = RequestMethod.POST)
    public Message getOrderByYear(@RequestBody Param year){
        int[] endtime;
        System.out.println(Arrays.toString(year.getParamNum()));
        try {
            endtime =  selectParamService.getOrderByYear(year);
            if (endtime!=null){
                return new Message(SUCCESS, "获取用户信息 >> 成功", endtime);
            }
            return new Message(FAILURE, "获取用户信息 >> 失败", "未找到该用户");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(ERROR, "获取用户信息 >> 异常", "查找异常");
        }
    }

    /**

     * @Description:    未实现 有bug


     * @Author:      iauhsoaix

     * @CreateDate:     2018/7/10 7:36

     */
    @RequestMapping(value = "/getOrderByMonth", method = RequestMethod.POST)
    public Message getOrderByMonth(@RequestBody Param month){
        int[] endtime;
        try {
            endtime =  selectParamService.getOrderByMonth(month);
            if (endtime != null){
                return new Message(SUCCESS, "获取用户信息 >> 成功", endtime);
            }
            return new Message(FAILURE, "获取用户信息 >> 失败", "未找到该用户");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(ERROR, "获取用户信息 >> 异常", "查找异常");
        }
    }
    /**

     * @Description:    前后端均已实现

     * @Author:      iauhsoaix

     * @CreateDate:     2018/7/10 7:42

     */
    @RequestMapping(value = "/getAllOrderNum", method = RequestMethod.GET)
    public Message getAllOrderNum(){
        List<OrderInfo> orderInfo;
        try {
            orderInfo = selectParamService.getAllOrderNum();
            if (orderInfo != null){
                return new Message(SUCCESS, "获取用户信息 >> 成功", orderInfo);
            }
            return new Message(FAILURE, "获取用户信息 >> 失败", "未找到该用户");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(ERROR, "获取用户信息 >> 异常", "查找异常");
        }
    }
    /**

     * @Description:    前端未实现  只实现了后台单条件查询

     * @Author:      iauhsoaix

     * @CreateDate:     2018/7/14 12:36

     */

    @RequestMapping(value = "/getOrderByDriverId/{driverId}", method = RequestMethod.POST)
    public Message getOrderByDriverId(@PathVariable(value = "driverId") int driverId){
        List<OrderInfo> orderInfo;
        try {
            orderInfo =  selectParamService.getOrderByDriverId(driverId);
            if (orderInfo != null){
                return new Message(SUCCESS, "获取用户信息 >> 成功", orderInfo);
            }
            return new Message(FAILURE, "获取用户信息 >> 失败", "未找到该用户");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(ERROR, "获取用户信息 >> 异常", "查找异常");
        }
    }

    /**

     * @Description:    前端未实现  只实现了后台单条件查询

     * @Author:      iauhsoaix

     * @CreateDate:     2018/7/9 16:10

     */
    @RequestMapping(value = "/getOrderByCarNumber/{carId}", method = RequestMethod.POST)
    public Message getOrderBycarNumber(@PathVariable(value = "carId") int carId){
        List<OrderInfo> orderInfo;
        try {
            orderInfo =  selectParamService.getOrderBycarNumber(carId);
            if (orderInfo != null){
                return new Message(SUCCESS, "获取用户信息 >> 成功", orderInfo);
            }
            return new Message(FAILURE, "获取用户信息 >> 失败", "未找到该用户");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(ERROR, "获取用户信息 >> 异常", "查找异常");
        }
    }
//    /**
//
//     * @Description:多条件查询接口    前后端均未实现
//
//     * @Author:      iauhsoaix
//
//     * @CreateDate:     2018/7/13 13:01
//
//     */
//    @RequestMapping(value = "/findCarInfoByMultiCondition", method = RequestMethod.POST)
//    public Message findOrderInfoByMultiCondition(@RequestBody MultiCondition multiCondition){
//        List<OrderInfo> orderInfo;
//        Map<String, Object> map = new HashMap<>();
//        try {
////        tableMessage.getCarInfo().setCarType("%" + tableMessage.getCarInfo().getCarType() + "%");
////
//            orderInfo = selectParamService.findOrderInfoByMultiCondition(multiCondition);
//            // count = selectOrderByParamService.findCarInfoByMultiConditionCount(tableMessage);
////            if (carInfos != null){
////                for (CarInfo carInfo : carInfos){
////                    driverInfo = selectOrderByParamService.getOrderByDriverId(carInfo.getCarId());
////
////                    CarInfoDTO carInfoDTO = new CarInfoDTO(carInfo, driverInfo);
////                    carInfoDTOList.add(carInfoDTO);
////                }
////                map.put("count", count);
////                map.put("carInfos", carInfoDTOList);
////                return new Message(SUCCESS, "查询车辆信息 >> 成功", map);
////            }
//            return new Message(FAILURE, "查询车辆信息 >> 失败", "无符合条件车辆");
//        } catch (Exception e) {
//            return new Message(ERROR, "查询车辆信息 >> 异常",  "查找异常");
//        }
//    }





}
