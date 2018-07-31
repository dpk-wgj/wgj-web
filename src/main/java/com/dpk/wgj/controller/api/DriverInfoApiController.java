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
import org.springframework.transaction.annotation.Transactional;
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
    public Message updateApiDriverInfoByDriverId(@RequestBody DriverInfo driverInfo)  {
        int upApiStatus = 0;
        int carId = driverInfo.getCarId();
        try {
                if (carId != 0) {               //司机状态：登录成功阶段，获取到车辆Id
                    if(driverInfo.getDriverStatus()==1) {   //登录司机要切换为 上岗 状态
                       DriverInfo driverInfos = driverInfoApiService.getDriverInfoByCarId(carId);  //获取车辆Id为carId的上岗司机
                        if (driverInfos == null) {    //没有司机上岗
                        upApiStatus = driverInfoApiService.updateApiDriverInfoByDriverId(driverInfo);
                        if (upApiStatus == 1) {
                                return new Message(Message.SUCCESS, "更新司机上岗状态 >> 成功", upApiStatus);
                            }
                            return new Message(Message.ERROR, "更新司机上岗状态 >> 失败", upApiStatus);
                    }
                    else
                            return new Message(Message.FAILURE, "已有司机上岗，更新司机上岗状态 >> 失败", upApiStatus);
                }
                else{   //切换为 下岗 状态
                        upApiStatus = driverInfoApiService.updateApiDriverInfoByDriverId(driverInfo);
                        if (upApiStatus == 1) {
                            return new Message(Message.SUCCESS, "更新司机下岗状态 >> 成功", upApiStatus);
                        }
                        return new Message(Message.ERROR, "更新司机下岗状态 >> 失败", upApiStatus);
                    }
            }
        else {    //司机状态：激活阶段
                upApiStatus = driverInfoApiService.updateApiDriverInfoByDriverId(driverInfo);
                if (upApiStatus == 1) {
                    return new Message(Message.SUCCESS, "更新司机信息 >> 成功", upApiStatus);
                }
                return new Message(Message.FAILURE, "更新司机信息 >> 失败", upApiStatus);
            }
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
     * 司机端  提交验证码 {"randomNum": "XXXX"}
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

    /**
     * 司机端 ，发送验证码或者（修改绑定手机号）调用{"phoneNumber": "xxxxxx"}
     *               判断 手机号是否已经绑定了，防止一个手机号绑定多个司机
     */
    @RequestMapping(value = "/getDriverInfoByDriverPhoneNumber/{driverPhoneNumber}", method = RequestMethod.GET)
    public Message getDriverInfoByDriverPhoneNumber(@PathVariable(value = "driverPhoneNumber") String driverPhoneNumber){
        DriverInfo driverInfo;
        int status = 0;  //0是可以绑定 ，1是不能绑定
        try {
            //搜索绑定手机号为xxxxx的用户
            driverInfo = driverInfoApiService.getDriverInfoByDriverPhoneNumber(driverPhoneNumber);
            if (driverInfo != null){
               status = 1;
                return new Message(Message.SUCCESS, "查询到该号码已经绑定了司机，不能再次绑定", status);
            }
            return new Message(Message.FAILURE, "查询到该号码未绑定司机，可以绑定", status);
        } catch (Exception e) {
            return new Message(Message.ERROR, "查询司机信息 >> 异常", e.getMessage());
        }

    }

    /**
     *  司机端  上下岗切换
     *  二维码提供 carId
     *  实现二维码差异
     */
    @RequestMapping(value = "/changeDriverStatus/{carId}", method = RequestMethod.PUT)
    @Transactional(rollbackFor = Exception.class)
    public Message changeDriverStatus(@PathVariable(value = "carId") int carId){
        UserDTO userInfo = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getDetails();
        int partDriverId = 0;

        DriverInfo partDriver = null;
        try {
            DriverInfo targetDriver = driverInfoApiService.getDriverInfoByDriverId(userInfo.getUserId());

            CarInfo carInfo = carInfoService.getCarInfoByCarId(targetDriver.getCarId());

            // 判断车辆所有权是否为该司机
            if (carInfo != null && carId == targetDriver.getCarId() && (carInfo.getCarDriverIdA() == userInfo.getUserId()
                    || carInfo.getCarDriverIdB() == userInfo.getUserId())){
                // 判断司机当前状态
                if (targetDriver.getDriverStatus() == 0){
                    // 得到第二个司机id
                    if (carInfo.getCarDriverIdB() == userInfo.getUserId()){
                        partDriverId = carInfo.getCarDriverIdA();
                    }else if (carInfo.getCarDriverIdA() == userInfo.getUserId()){
                        partDriverId = carInfo.getCarDriverIdB();
                    }
                    // 得到第二个司机状态
                    if (partDriverId != 0){
                        partDriver = driverInfoApiService.getDriverInfoByDriverId(partDriverId);
                        if (partDriver != null){

                                if (partDriver.getDriverStatus() == 1){
                                    partDriver.setDriverStatus(0);
                                    targetDriver.setDriverStatus(1);
                                    int upTarStatus = driverInfoApiService.updateDriverInfoByDriverId(targetDriver);
                                    int upPartStatus = driverInfoApiService.updateDriverInfoByDriverId(partDriver);
                                    if (upTarStatus == 1 && upPartStatus == 1){
                                        return new Message(Message.SUCCESS, "司机状态切换 >> 成功",
                                                1);
                                    }
                                }
                                int upPartStatus = driverInfoApiService.updateDriverInfoByDriverId(partDriver);
                                return new Message(Message.SUCCESS, "司机状态切换 >> 成功",
                                        1);
                            }

                    }
                    targetDriver.setDriverStatus(1);
                    int upTarStatus = driverInfoApiService.updateDriverInfoByDriverId(targetDriver);
                    return new Message(Message.SUCCESS, "司机状态切换 >> 成功",
                            1);
                }
                return new Message(Message.SUCCESS, "司机状态处于上岗状态",
                        2);

            }

            return new Message(Message.FAILURE, "未授权操作！",
                    -1);

        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "异常请求！",
                    -2);
        }

    }


}
