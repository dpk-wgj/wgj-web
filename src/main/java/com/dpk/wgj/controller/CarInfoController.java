package com.dpk.wgj.controller;

import com.dpk.wgj.bean.CarInfo;
import com.dpk.wgj.bean.DTO.CarInfoDTO;
import com.dpk.wgj.bean.DTO.UserDTO;
import com.dpk.wgj.bean.DriverInfo;
import com.dpk.wgj.bean.Message;
import com.dpk.wgj.bean.tableInfo.CarInfoTableMessage;
import com.dpk.wgj.bean.tableInfo.TableMessage;
import com.dpk.wgj.mapper.AdminGroupAuthorityMapper;
import com.dpk.wgj.service.CarInfoService;
import com.dpk.wgj.service.DriverInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/admin/car")
public class CarInfoController {

    @Autowired
    private CarInfoService carInfoService;

    @Autowired
    private DriverInfoService driverInfoService;

    @Autowired
    private AdminGroupAuthorityMapper adminGroupAuthorityMapper;
    /**
     * 根据车牌查找车辆信息(关联司机信息)
     */
    @RequestMapping(value = "/getCarInfoByCarNumber/{carNumber}", method = RequestMethod.GET)
    public Message getCarInfoByCarNumber(@PathVariable(value = "carNumber") String carNumber){
        CarInfo carInfo;
        try {
            carInfo = carInfoService.getCarInfoByCarNumber(carNumber);
            if (carInfo != null){
                DriverInfo driverInfo = driverInfoService.getDriverInfoByCarId(carInfo.getCarId());
                CarInfoDTO carInfoDTO = new CarInfoDTO(carInfo, driverInfo);
                return new Message(Message.SUCCESS, "查询车辆信息 >> 成功", carInfoDTO);
            }
            return new Message(Message.FAILURE, "查询车辆信息 >> 失败", "未查询到车牌号 [" + carNumber + "] 信息");
        } catch (Exception e) {
            return new Message(Message.ERROR, "查询车辆信息 >> 异常", e.getMessage());
        }
    }

    /**
     * 添加车辆信息
     * @param carInfo
     */
    @RequestMapping(value = "/addCarInfo", method = RequestMethod.POST)
    public Message addCarInfo(@RequestBody CarInfo carInfo){
        int addStatus = 0;
        try {
            addStatus = carInfoService.addCarInfo(carInfo);
            if (addStatus == 1){
                return new Message(Message.SUCCESS, "添加车辆信息 >> 成功", addStatus);
            }
            return new Message(Message.FAILURE, "添加车辆信息 >> 失败", addStatus);
        } catch (Exception e) {
            return new Message(Message.ERROR, "添加车辆信息 >> 异常",  e.getMessage());
        }
    }

    /**
     * 根据车辆Id删除车辆信息
     * @param carId
     */
    @RequestMapping(value = "/deleteCarInfoByCarId", method = RequestMethod.POST)
    public Message deleteCarInfoByCarId(@RequestParam(value = "carId") int carId){
        UserDTO userInfo = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getDetails();
        int delStatus = 0;
        try {
            String authorityContent = adminGroupAuthorityMapper.getAdminGroupAuthorityById(userInfo.getAuthorityId()).getAdminGroupName();
            if ( authorityContent.equals("高级管理员") || authorityContent.equals("超级管理员")) {
            delStatus = carInfoService.deleteCarInfoByCarId(carId);
            if (delStatus == 1){
                return new Message(Message.SUCCESS, "删除车辆信息 >> 成功", delStatus);
            }
            return new Message(Message.FAILURE, "删除车辆信息 >> 失败", delStatus);
            }
            return new Message(Message.NOT_LEGAL, "权限不合法",  "您是"+authorityContent+",权限不足，无法进行删除处理！");
        } catch (Exception e) {
            return new Message(Message.ERROR, "删除车辆信息 >> 异常",  e.getMessage());
        }
    }

    /**
     * 更新车辆信息
     * @param carInfo
     */
    @RequestMapping(value = "/updateCarInfoByCarId", method = RequestMethod.POST)
    public Message updateCarInfoByCarId(@RequestBody CarInfo carInfo){
        UserDTO userInfo = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getDetails();
        int upStatus = 0;
        try {
            String authorityContent = adminGroupAuthorityMapper.getAdminGroupAuthorityById(userInfo.getAuthorityId()).getAdminGroupName();
            if ( authorityContent.equals("高级管理员") || authorityContent.equals("超级管理员")) {
            upStatus = carInfoService.updateCarInfoByCarId(carInfo);
            if (upStatus == 1){
                return new Message(Message.SUCCESS, "更新车辆信息 >> 成功", upStatus);
            }
            return new Message(Message.FAILURE, "更新车辆信息 >> 失败", upStatus);
            }
            return new Message(Message.NOT_LEGAL, "权限不合法",  "您是"+authorityContent+",权限不足，无法进行修改操作！");
        } catch (Exception e) {
            return new Message(Message.ERROR, "更新车辆信息 >> 异常",  e.getMessage());
        }
    }

    /**
     * 多条件查询车辆信息
     * @param tableMessage
     * @return
     */
    @RequestMapping(value = "/findCarInfoByMultiCondition", method = RequestMethod.POST)
    public Message findCarInfoByMultiCondition(@RequestBody CarInfoTableMessage tableMessage){
        List<CarInfo> carInfos;
        DriverInfo driverInfo;

        List<CarInfoDTO> carInfoDTOList = new ArrayList<>();
        int count = 0;
        Map<String, Object> map = new HashMap<>();
        try {
            carInfos = carInfoService.findCarInfoByMultiCondition(tableMessage);
            count = carInfoService.findCarInfoByMultiConditionCount(tableMessage);
            if (carInfos != null){
                for (CarInfo carInfo : carInfos){
                    driverInfo = driverInfoService.getDriverInfoByCarId(carInfo.getCarId());
                    CarInfoDTO carInfoDTO = new CarInfoDTO(carInfo, driverInfo);
                    carInfoDTOList.add(carInfoDTO);
                }
                map.put("count", count);

                map.put("carInfos", carInfoDTOList);
                return new Message(Message.SUCCESS, "查询车辆信息 >> 成功", map);
            }
            return new Message(Message.FAILURE, "查询车辆信息 >> 失败", "无符合条件车辆");
        } catch (Exception e) {
            return new Message(Message.ERROR, "查询车辆信息 >> 异常",  e.getMessage());
        }
    }

    /**
     * 根据carId查询车辆信息
     * @param carId
     * @return
     */
    @RequestMapping(value = "/getCarInfoByCarId/{carId}", method = RequestMethod.GET)
    public Message getCarInfoByCarId(@PathVariable(value = "carId") int carId){
        CarInfo carInfo;
        Map<String, Object> map = new HashMap<>();
        try {
            carInfo = carInfoService.getCarInfoByCarId(carId);
            if (carInfo != null){
                map.put("carInfos", carInfo);
                return new Message(Message.SUCCESS, "查询车辆信息 >> 成功", map);
            }
            return new Message(Message.FAILURE, "查询车辆信息 >> 失败", "未查询到id [" + carId + "] 信息");
        } catch (Exception e) {
            return new Message(Message.ERROR, "查询车辆信息 >> 异常", e.getMessage());
        }
    }

    /**
     * 根据车牌照查询车辆的信息
     * @param carNumber
     * @return
     */
    @RequestMapping(value = "/getCarInfoNoCompatibleByCarNumber/{carNumber}", method = RequestMethod.GET)
    public Message getCarInfoNoCompatibleByCarNumber(@PathVariable(value = "carNumber")  String carNumber){
        CarInfo carInfor;
        DriverInfo  driverInfo;
        try {
            carInfor = carInfoService.getCarInfoNoCompatibleByCarNumber(carNumber);
            if (carInfor != null)
            {
//                if(carInfor.getCarDriverIdA() ==0 || carInfor.getCarDriverIdB()==0){
                return new Message(Message.SUCCESS, "查询车辆信息 >> 成功", carInfor);
//                }
//                else
//                    return new Message(Message.FAILURE, "查询车辆信息 >> 失败"," 车牌号码为[" + carNumber + "] 的车辆已经绑定了两辆车");
            }
            return new Message(Message.FAILURE, "查询车辆信息 >> 失败", "未查询到车牌号码为[" + carNumber + "] 的信息");
        } catch (Exception e) {
            return new Message(Message.ERROR, "查询车辆信息 >> 异常", e.getMessage());
        }
    }
}
