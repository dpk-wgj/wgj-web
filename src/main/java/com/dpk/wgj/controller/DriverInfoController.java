package com.dpk.wgj.controller;

import com.dpk.wgj.bean.CarInfo;
import com.dpk.wgj.bean.DTO.CarInfoDTO;
import com.dpk.wgj.bean.DriverInfo;
import com.dpk.wgj.bean.Message;
import com.dpk.wgj.service.CarInfoService;
import com.dpk.wgj.service.DriverInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/admin/driver")
public class DriverInfoController {

    @Autowired
    private DriverInfoService driverInfoService;

    @Autowired
    private CarInfoService carInfoService;

    /**
     * 获取所有当前上岗司机位置
     * @return
     */
    @RequestMapping(value = "/getAllCarLocation", method = RequestMethod.GET)
    public Message getAllCarLocation(){
        List<DriverInfo> driverInfoList;

        List<CarInfoDTO> carInfoDTOList = new ArrayList<>();

        try {
            driverInfoList = driverInfoService.getAllCarLocation();
            if (driverInfoList != null){
                for (DriverInfo driverInfo : driverInfoList) {
                    CarInfo carInfo = carInfoService.getCarInfoByCarId(driverInfo.getCarId());

                    CarInfoDTO carInfoDTO = new CarInfoDTO(carInfo, driverInfo);
                    carInfoDTOList.add(carInfoDTO);
                }
                return new Message(Message.SUCCESS, "获取所有车辆位置 >> 成功", carInfoDTOList);
            }
            return new Message(Message.FAILURE, "获取所有车辆位置 >> 失败", "未查询到数据");
        } catch (Exception e) {
            return new Message(Message.ERROR, "获取所有车辆位置 >> 异常", e.getMessage());
        }
    }

}
