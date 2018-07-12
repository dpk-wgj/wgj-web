package com.dpk.wgj.controller;

import com.dpk.wgj.bean.Message;
import com.dpk.wgj.bean.Passenger;
import com.dpk.wgj.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhoulin on 2018/7/12.
 * 乘客端
 */
@RestController
@RequestMapping(value = "/api/passenger")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    /**
     * 乘客端 绑定手机号功能
     * @param passenger
     * @return
     */
    @RequestMapping(value = "/bindPassengerPhoneNumber", method = RequestMethod.POST)
    public Message bindPassengerPhoneNumber(@RequestBody Passenger passenger){

        int upStatus = 0;

        try {
            upStatus = passengerService.updatePassengerPhoneNumber(passenger);
            if (upStatus == 1){
                return new Message(Message.SUCCESS, "乘客绑定手机号 >> 成功", upStatus);
            }
            return new Message(Message.FAILURE, "乘客绑定手机号 >> 失败", upStatus);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "乘客绑定手机号 >> 异常", e.getMessage());
        }

    }

}
