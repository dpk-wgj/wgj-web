package com.dpk.wgj.controller;

import com.dpk.wgj.bean.DTO.UserDTO;
import com.dpk.wgj.bean.Message;
import com.dpk.wgj.bean.Passenger;
import com.dpk.wgj.bean.SmsInfo;
import com.dpk.wgj.service.PassengerService;
import com.dpk.wgj.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zhoulin on 2018/7/12.
 * 乘客端
 */
@RestController
@RequestMapping(value = "/api/passenger")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private RedisTemplate redisTemplate;

    private final Logger logger  = LoggerFactory.getLogger(PassengerController.class);

    /**
     * 乘客端  绑定前调用{phoneNumber": "xxxxxx"}
     */
    @RequestMapping(value = "sendCodeForPassenger",method = RequestMethod.POST)
    public Message checkCodeDriver(@RequestBody SmsInfo smsInfo){
        UserDTO userInfo = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getDetails();
        smsInfo.setUserId(userInfo.getUserId());

        int status = 0;
        try{
            status = smsService.sendMsg(smsInfo);
            if(status == 1){
                return new Message(Message.SUCCESS, "验证码发送成功", status);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "验证码发送异常", e.getMessage());
        }
        return new Message(Message.FAILURE, "验证码发送失败", "请重新请求");

    }

    /**
     * 乘客端 提交验证码 {"randomNum": "XXXX"}

     */
    @RequestMapping(value = "/bindPassengerPhoneNumber", method = RequestMethod.POST)
    public Message bindPassengerPhoneNumber(@RequestBody SmsInfo smsInfo){

        UserDTO userInfo = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getDetails();
        int passengerId = userInfo.getUserId();

        ValueOperations<String,SmsInfo> operations = redisTemplate.opsForValue();

         //从缓存中取出sms
        SmsInfo sms = operations.get("driver_"+passengerId);
        String code = sms.getRandomNum();
        logger.info("code {}", code);

        int upStatus = 0;

        Passenger passenger = new Passenger();
        passenger.setPassengerId(passengerId);
        passenger.setPassengerPhoneNumber(sms.getPhoneNumber());
        logger.info("random {}",sms.getRandomNum());
        String randomNum = smsInfo.getRandomNum();
        try {
            if(code.equals(randomNum)) {
                //执行更新操作&&更新成功进行回调
                upStatus = passengerService.updatePassengerPhoneNumber(passenger);
                if (upStatus == 1) {
                    return new Message(Message.SUCCESS, "乘客绑定手机号 >> 成功", upStatus);
                }
            }
            return new Message(Message.FAILURE, "乘客绑定手机号 >> 失败", upStatus);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "乘客绑定手机号 >> 异常", e.getMessage());
        }

    }
    /**
     * 乘客端 ，发送验证码或者（修改绑定手机号）调用{"phoneNumber": "xxxxxx"}
     *               判断 手机号是否已经绑定了，防止一个手机号绑定多个乘客
     */
    @RequestMapping(value = "/getPassengerByPhoneNumber/{passengerPhoneNumber}", method = RequestMethod.GET)
    public Message getPassengerByPhoneNumber(@PathVariable(value = "passengerPhoneNumber") String passengerPhoneNumber){
        Passenger passenger ;
        int status = 0;  //0是可以绑定 ，1是不能绑定
        try {
            //搜索绑定手机号为xxxxx的用户
            passenger = passengerService.getPassengerByPhoneNumber(passengerPhoneNumber);

            if (passenger != null){
                status = 1;
                return new Message(Message.SUCCESS, "查询到该号码已经绑定了乘客，不能再次绑定", status);
            }
            return new Message(Message.FAILURE, "查询到该号码未绑定乘客，可以绑定", status);
        } catch (Exception e) {
            return new Message(Message.ERROR, "查询司机信息 >> 异常", e.getMessage());
        }

    }

}
