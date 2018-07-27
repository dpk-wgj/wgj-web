package com.dpk.wgj.controller;

import com.dpk.wgj.bean.*;
import com.dpk.wgj.bean.DTO.UserDTO;
import com.dpk.wgj.controller.api.DriverInfoApiController;
import com.dpk.wgj.service.DriverInfoService;
import com.dpk.wgj.service.LoginService;
import com.dpk.wgj.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by zhoulin on 2018/7/9.
 * 说明:
 */
@RestController
@RequestMapping("/public")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DriverInfoService driverInfoApiService;

    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/driver/login",method = RequestMethod.POST)
    public Message loginForDriver(@RequestBody DriverInfo driverInfo, HttpSession session, HttpServletResponse response){

        UserDTO user = new UserDTO();

        user.setDriverInfo(driverInfo);

        Message message = loginService.loginForDriver(user,response);
        if(message != null){
            if(message.getStatus() == Message.SUCCESS){
                session.setAttribute("user",message.getResult());
            }
            else if(message.getStatus() == Message.ERROR){
                session.setAttribute("user1",message.getResult());
            }
        }
        return message;
    }
    /**
     * 激活司机信息  绑定前调用 {"phoneNumber": "xxxxxx"}
     * 发送验证码
     * @param smsInfo
     * @return
     */
    @RequestMapping(value = "/sendCodeForDriver", method = RequestMethod.POST)
    public Message checkCodeDriver(@RequestBody SmsInfo smsInfo){
//        System.out.println("666666");
//     UserDTO userInfo = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getDetails();
        smsInfo.setUserId(0);

        int status = 0;
        try {
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
     * 激活司机  提交验证码 {"randomNum": "XXXX"}
     *           提交小程序的微信id{"driverWxId": "XXXX"}
     */
    @RequestMapping(value = "/bindDriverPhoneNumber",method = RequestMethod.POST)
    public Message bindDriverPhoneNumber(@RequestBody SmsInfo smsInfo){

        int upStatus = 0;
        ValueOperations<String, SmsInfo> operations = redisTemplate.opsForValue();

        // 从缓存中取出sms
        SmsInfo sms = operations.get("driver_"+0 );
        String code = sms.getRandomNum();

        logger.info("code {}", code);

        DriverInfo driverInfos;

        logger.info("random {}",sms.getRandomNum());
        String randomNum = smsInfo.getRandomNum();
        try{
            if (code.equals(randomNum)){
                // 执行更新操作 && 更新成功进行回调
                driverInfos = driverInfoApiService.getDriverInfoByDriverPhoneNumber(sms.getPhoneNumber());
                if (driverInfos != null){
                    DriverInfo driverInfo = new DriverInfo();
                    driverInfo.setDriverId(driverInfos.getDriverId());
                    driverInfo.setDriverWxId(smsInfo.getDriverWxId());
                    upStatus = driverInfoApiService.updateDriverInfoByDriverId(driverInfo);
                    if(upStatus==1){
                        return new Message(Message.SUCCESS, "激活司机 >> 成功", upStatus);
                    }
                    else{
                        return new Message(Message.SUCCESS, "激活司机 >> 失败", upStatus);
                    }
                }
                else
                    return new Message(Message.SUCCESS, "不存在该司机信息，非法登陆 >> 失败", null);
            }
            return new Message(Message.FAILURE, "激活司机 >> 失败", upStatus);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "激活司机 >> 异常", e.getMessage());
        }

    }
    @RequestMapping(value = "/passenger/login",method = RequestMethod.POST)
    public Message loginForPassenger(@RequestBody Passenger passenger, HttpSession session, HttpServletResponse response){

        UserDTO user = new UserDTO();

        user.setPassenger(passenger);

        Message message = loginService.loginForPassenger(user,response);
        if(message != null){
            if(message.getStatus() == Message.SUCCESS){
                session.setAttribute("user",message.getResult());
            }
        }
        return message;
    }

    @RequestMapping(value = "/admin/login",method = RequestMethod.POST)
    public Message loginForAdmin(@RequestBody AdminInfo adminInfo, HttpSession session, HttpServletResponse response){

        UserDTO user = new UserDTO();

        user.setAdminInfo(adminInfo);

        Message message = loginService.loginForAdminInfo(user,response);
        if(message != null){
            if(message.getStatus() == Message.SUCCESS){
                session.setAttribute("user",message.getResult());
            }
        }
        return message;
    }


}
