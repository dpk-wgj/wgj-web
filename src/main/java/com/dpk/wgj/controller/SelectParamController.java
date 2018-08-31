package com.dpk.wgj.controller;
import com.dpk.wgj.bean.*;
import com.dpk.wgj.service.SelectParamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.dpk.wgj.bean.Message.*;

@RestController
@RequestMapping(value = "/admin/count")
public class SelectParamController {
    @Autowired
    private SelectParamService selectParamService;

    private final Logger logger = LoggerFactory.getLogger(AdminInfoController.class);

    @RequestMapping(value = "/getMonthOrderInfoByYear", method = RequestMethod.POST)
    public Message getMonthOrderInfoByYear(@RequestBody Param year) {
        List<MonthInfo> endTime = new ArrayList<MonthInfo>();
        List<List> months = new ArrayList<List>();
        try {
            for(int i= 0;i<year.getParamNum().length;i++) {
                MonthInfo monthInfos = selectParamService.getMonthOrderInfoByYear(year.getParamNum()[i]);
                endTime.add(monthInfos);
            }
        for(MonthInfo monthInfo:endTime) {
                if(monthInfo!=null) {
                    List<Integer> month = new ArrayList<Integer>();
                    month.add(monthInfo.getJanuary());
                    month.add(monthInfo.getFebruary());
                    month.add(monthInfo.getMarch());
                    month.add(monthInfo.getApril());
                    month.add(monthInfo.getMay());
                    month.add(monthInfo.getJune());
                    month.add(monthInfo.getJuly());
                    month.add(monthInfo.getAugust());
                    month.add(monthInfo.getSeptember());
                    month.add(monthInfo.getOctober());
                    month.add(monthInfo.getNovember());
                    month.add(monthInfo.getDecember());
                    months.add(month);
                }
                else
                    months.add(null);

        }
            if (months != null) {
                return new Message(SUCCESS, "获取每月份信息 >> 成功", months);
            }
            return new Message(FAILURE, "获取每月份信息 >> 失败", "未找到每月份信息");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(ERROR, "获取每月份信息 >> 异常", "获取异常");
        }
    }

//    @RequestMapping(value = "/getOrderByYear", method = RequestMethod.POST)
//    public Message getOrderByYear(@RequestBody Param month) {
//        int[] endtime;
//        try {
//            endtime = selectParamService.getOrderByMonth(month);
//            if (endtime != null) {
//                return new Message(SUCCESS, "获取用户信息 >> 成功", endtime);
//            }
//            return new Message(FAILURE, "获取用户信息 >> 失败", "未找到该用户");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new Message(ERROR, "获取用户信息 >> 异常", "查找异常");
//        }
//    }
//
//    @RequestMapping(value = "/getAllOrderNum", method = RequestMethod.GET)
//    public Message getAllOrderNum() {
//        List<OrderInfo> orderInfo;
//        try {
//            orderInfo = selectParamService.getAllOrderNum();
//            if (orderInfo != null) {
//                return new Message(SUCCESS, "获取用户信息 >> 成功", orderInfo);
//            }
//            return new Message(FAILURE, "获取用户信息 >> 失败", "未找到该用户");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new Message(ERROR, "获取用户信息 >> 异常", "查找异常");
//        }
//    }
//
//    @RequestMapping(value = "/getOrderByDriverId/{driverId}", method = RequestMethod.POST)
//    public Message getOrderByDriverId(@PathVariable(value = "driverId") int driverId) {
//        List<OrderInfo> orderInfo;
//        try {
//            orderInfo = selectParamService.getOrderByDriverId(driverId);
//            if (orderInfo != null) {
//                return new Message(SUCCESS, "获取用户信息 >> 成功", orderInfo);
//            }
//            return new Message(FAILURE, "获取用户信息 >> 失败", "未找到该用户");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new Message(ERROR, "获取用户信息 >> 异常", "查找异常");
//        }
//    }
//
//
//    @RequestMapping(value = "/getOrderByCarNumber/{carId}", method = RequestMethod.POST)
//    public Message getOrderBycarNumber(@PathVariable(value = "carId") int carId) {
//        List<OrderInfo> orderInfo;
//        try {
//            orderInfo = selectParamService.getOrderBycarNumber(carId);
//            if (orderInfo != null) {
//                return new Message(SUCCESS, "获取用户信息 >> 成功", orderInfo);
//            }
//            return new Message(FAILURE, "获取用户信息 >> 失败", "未找到该用户");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new Message(ERROR, "获取用户信息 >> 异常", "查找异常");
//        }
//    }
}