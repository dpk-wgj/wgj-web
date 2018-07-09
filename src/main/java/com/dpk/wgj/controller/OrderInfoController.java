package com.dpk.wgj.controller;

import com.dpk.wgj.bean.Message;
import com.dpk.wgj.bean.OrderInfo;
import com.dpk.wgj.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/admin/order")
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;

    @RequestMapping(value = "/getLocationInfoByDate", method = RequestMethod.POST)
    public Message getLocationInfoByDate(@RequestParam(value = "startTime") Date startTime, @RequestParam(value = "endTime") Date endTime){
        try {
            List<OrderInfo> locations = orderInfoService.getLocationInfoByDate(startTime, endTime);
            if (locations != null){
                return new Message(Message.SUCCESS, "获取车辆轨迹 >> 成功", locations);
            }
            return new Message(Message.FAILURE, "获取车辆轨迹 >> 失败", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "获取车辆轨迹 >> 异常", e.getMessage());
        }
    }

}
