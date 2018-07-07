package com.dpk.wgj.controller;

import com.dpk.wgj.bean.CarInfo;
import com.dpk.wgj.bean.Message;
import com.dpk.wgj.service.CarInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/app/car")
public class CarInfoController {

    @Autowired
    private CarInfoService carInfoService;

    /**
     * 根据车牌大赛大声道大赛根据从catNumber
     */
    @RequestMapping(value = "/getCarInfoByCarNumber/{catNumber}", method = RequestMethod.GET)
    public Message getCarInfoByCarNumber(@PathVariable(value = "catNumber") String carNumber){
        CarInfo carInfo;
        try {
            carInfo = carInfoService.getCarInfoByCarNumber(carNumber);
            if (carInfo != null){
                return new Message(Message.SUCCESS, "查询车辆信息 >> 成功", carInfo);
            }
            return new Message(Message.FAILURE, "查询车辆信息 >> 失败", "未查询到车牌号 [" + carNumber + "] 信息");
        } catch (Exception e) {
            return new Message(Message.ERROR, "查询车辆信息 >> 异常", e.getMessage());
        }

    }

}
