package com.dpk.wgj.controller.api;


import com.dpk.wgj.bean.CarInfo;
import com.dpk.wgj.bean.DTO.CarInfoDTO;
import com.dpk.wgj.bean.DTO.UserDTO;
import com.dpk.wgj.bean.DriverInfo;
import com.dpk.wgj.bean.Message;
import com.dpk.wgj.bean.SmsInfo;
import com.dpk.wgj.service.CarInfoService;
import com.dpk.wgj.service.DriverInfoService;
import com.dpk.wgj.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.Driver;
import java.util.List;

@RestController
@RequestMapping(value = "/api/driver")
public class DriverInfoApiController {

    @Autowired
    private DriverInfoService driverInfoApiService;

    @Autowired
    private CarInfoService carInfoService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private RedisTemplate redisTemplate;

    private final Logger logger = LoggerFactory.getLogger(DriverInfoApiController.class);

    /**
     * 根据司机id查找司机信息，同时可以关联上车辆信息
     */
    @RequestMapping(value = "/getDriverInfoByDriverId/{driverId}", method = RequestMethod.GET)
    public Message getDriverInfoByDriverId(@PathVariable(value = "driverId") int driverId){
        DriverInfo driverInfo;
        try {
            driverInfo = driverInfoApiService.getDriverInfoByDriverId(driverId);

            if (driverInfo != null){
                CarInfo carInfo = carInfoService.getCarInfoByCarId(driverInfo.getCarId());
                CarInfoDTO carInfoDTO = new CarInfoDTO(carInfo, driverInfo);
                return new Message(Message.SUCCESS, "查询司机信息 >> 成功", carInfoDTO);
            }
            return new Message(Message.FAILURE, "查询司机信息 >> 失败", "未查询到司机Id为 [" + driverId + "] 的信息");
        } catch (Exception e) {
            return new Message(Message.ERROR, "查询司机信息 >> 异常", e.getMessage());
        }

    }

    /**
     * 查询所有在岗司机
     */
    @RequestMapping(value = "/getDriverInfoByDriverStatus/{driverStatus}", method = RequestMethod.GET)
    public Message getDriverInfoByDriverStatus(@PathVariable(value = "driverStatus") int driverStatus){
        List<DriverInfo> driverInfos;
        try {
            driverInfos = driverInfoApiService.getDriverInfoByDriverStatus(driverStatus);

            if (driverInfos != null){
                return new Message(Message.SUCCESS, "查询司机信息 >> 成功", driverInfos);
            }
            return new Message(Message.FAILURE, "查询司机信息 >> 失败", "未查询到在岗司机的信息");
        } catch (Exception e) {
            return new Message(Message.ERROR, "查询司机信息 >> 异常", e.getMessage());
        }

    }

    /**
     * 根据Id更新司机位置信息
     */
    @RequestMapping(value = "/updateApiDriverInfoByDriverId",method = RequestMethod.POST)
    public Message updateApiDriverInfoByDriverId(@RequestBody DriverInfo driverInfo) {
        int upApiStatus = 0;
        try {
            upApiStatus = driverInfoApiService.updateApiDriverInfoByDriverId(driverInfo);
            if (upApiStatus == 1) {
                return new Message(Message.SUCCESS, "更新司机信息 >> 成功", upApiStatus);
            }
            return new Message(Message.FAILURE, "更新司机信息 >> 失败", upApiStatus);
        } catch (Exception e) {
            return new Message(Message.ERROR, "更新司机信息 >> 异常", e.getMessage());
        }
    }

    /**
     * 司机端  绑定前调用 {"phoneNumber": "xxxxxx"}
     * 发送验证码
     * @param smsInfo
     * @return
     */
    @RequestMapping(value = "/sendCodeForDriver", method = RequestMethod.POST)
    public Message checkCodeDriver(@RequestBody SmsInfo smsInfo){
        UserDTO userInfo = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getDetails();
        smsInfo.setUserId(userInfo.getUserId());

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
     * 司机端  提交验证码 {"phoneNumber": "XXXX"}
     */
    @RequestMapping(value = "/bindDriverPhoneNumber",method = RequestMethod.POST)
    public Message bindDriverPhoneNumber(@RequestBody SmsInfo smsInfo){
        UserDTO userInfo = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getDetails();
        int driverId = userInfo.getUserId();

        ValueOperations<String, SmsInfo> operations = redisTemplate.opsForValue();

        // 从缓存中取出sms
        SmsInfo sms = operations.get("driver_" + driverId);
        String code = sms.getRandomNum();

        logger.info("code {}", code);

        int upStatus = 0;

        DriverInfo driverInfo = new DriverInfo();
        driverInfo.setDriverId(driverId);
        driverInfo.setDriverPhoneNumber(sms.getPhoneNumber());
        logger.info("random {}",sms.getRandomNum());
        String randomNum = smsInfo.getRandomNum();
        try{
            if (code.equals(randomNum)){
                // 执行更新操作 && 更新成功进行回调
                upStatus = driverInfoApiService.updateDriverPhoneNumber(driverInfo);
                if (upStatus == 1){
                    return new Message(Message.SUCCESS, "司机绑定手机号 >> 成功", upStatus);
                }
            }
            return new Message(Message.FAILURE, "司机绑定手机号 >> 失败", upStatus);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "司机绑定手机号 >> 异常", e.getMessage());
        }

    }
}
