package com.dpk.wgj.controller;

import com.dpk.wgj.bean.Message;
import com.dpk.wgj.bean.OrderInfo;
import com.dpk.wgj.bean.tableInfo.LocationMessage;
import com.dpk.wgj.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/admin/order")
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;

    /**
     * 多条件查询车辆轨迹
     * @param locationMessage
     * @return
     */
    @RequestMapping(value = "/getLocationInfoByDate", method = RequestMethod.POST)
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

}
