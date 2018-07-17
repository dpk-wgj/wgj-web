package com.dpk.wgj.controller.api;


import com.dpk.wgj.bean.CarInfo;
import com.dpk.wgj.bean.DTO.CarInfoDTO;
import com.dpk.wgj.bean.DTO.UserDTO;
import com.dpk.wgj.bean.DriverInfo;
import com.dpk.wgj.bean.Message;
import com.dpk.wgj.service.CarInfoService;
import com.dpk.wgj.service.DriverInfoService;
import org.springframework.beans.factory.annotation.Autowired;
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
    * 司机端  绑定手机号
     * @param driver
     * @return
    * */
    @RequestMapping(value = "bindDriverPhoneNumber",method = RequestMethod.POST)
    public Message bingDriverPhoneNumber(@RequestBody DriverInfo driver){
        UserDTO userInfo = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getDetails();
        int driverId = userInfo.getUserId();

        int upStatus = 0;
        driver.setDriverId(driverId);
        try{
            upStatus = driverInfoApiService.updateDriverPhoneNumber(driver);
            if (upStatus == 1){
                return new Message(Message.SUCCESS, "司机绑定手机号 >> 成功", upStatus);
            }
            return new Message(Message.FAILURE, "司机绑定手机号 >> 失败", upStatus);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "司机绑定手机号 >> 异常", e.getMessage());
        }

    }
}
