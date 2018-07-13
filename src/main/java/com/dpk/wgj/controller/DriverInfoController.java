package com.dpk.wgj.controller;

import com.dpk.wgj.POI.Excel;
import com.dpk.wgj.bean.CarInfo;
import com.dpk.wgj.bean.DTO.CarInfoDTO;
import com.dpk.wgj.bean.DriverInfo;
import com.dpk.wgj.bean.Message;
import com.dpk.wgj.bean.tableInfo.DriverInfoTableMessage;
import com.dpk.wgj.service.CarInfoService;
import com.dpk.wgj.service.DriverInfoService;
import com.dpk.wgj.service.LogInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/admin/driver")
public class DriverInfoController {

    @Autowired
    private DriverInfoService driverInfoService;

    @Autowired
    private CarInfoService carInfoService;

    @Autowired
    private LogInfoService logInfoService;

    @Autowired
    private Excel excel;

    private final Logger logger = LoggerFactory.getLogger(DriverInfoController.class);

    /**
     * 获取全部信息
     */

    @RequestMapping(value = "/getAllDriverInfo",method = RequestMethod.GET)
    public Message getAllDriverInfo()  {
        List<CarInfoDTO> carInfoDTO = new ArrayList<CarInfoDTO>();
        try {
            List<DriverInfo> driverInfo = driverInfoService.getAllDriverInfo();

            if (driverInfo != null){
                for(DriverInfo driverInfo1 : driverInfo) {
                    CarInfo carInfo = carInfoService.getCarInfoByCarId(driverInfo1.getCarId());
                    carInfoDTO.add(new CarInfoDTO(carInfo, driverInfo1));
                }
                return new Message(Message.SUCCESS, "查询全部司机信息 >> 成功", carInfoDTO);
            }
            else{
                return new Message(Message.FAILURE, "查询司机信息 >> 失败", "未查询到司机信息");
            }

        } catch (Exception e) {
            return new Message(Message.ERROR, "查询司机信息 >> 异常", e.getMessage());
        }

    }

    /**
     * 根据司机名字查找司机信息，同时可以关联上车辆信息
     */
    @RequestMapping(value = "/getDriveInfoByDriverName/{driverName}", method = RequestMethod.GET)
    public Message getDriverInfoByDriverName(@PathVariable(value = "driverName") String driverName){
        DriverInfo driverInfo;
        try {
            driverInfo = driverInfoService.getDriverInfoByDriverName(driverName);

            if (driverInfo != null){
                CarInfo carInfo = carInfoService.getCarInfoByCarId(driverInfo.getCarId());
                CarInfoDTO carInfoDTO = new CarInfoDTO(carInfo, driverInfo);
                return new Message(Message.SUCCESS, "查询司机信息 >> 成功", carInfoDTO);
            }
            return new Message(Message.FAILURE, "查询司机信息 >> 失败", "未查询到司机姓名为 [" + driverName + "] 的信息");
        } catch (Exception e) {
            return new Message(Message.ERROR, "查询司机信息 >> 异常", e.getMessage());
        }

    }

    /**
     * 根据司机手机号查找司机信息，同时可以关联上司机的车辆信息
     */
    @RequestMapping(value = "/getDriveInfoByDriverPhoneNumber/{driverPhoneNumber}", method = RequestMethod.GET)
    public Message getDriverInfoByDriverPhoneNumber(@PathVariable(value = "driverPhoneNumber") String driverPhoneNumber){
        DriverInfo driverInfo;
        try {
            driverInfo = driverInfoService.getDriverInfoByDriverPhoneNumber(driverPhoneNumber);
            if (driverInfo != null){
                CarInfo carInfo = carInfoService.getCarInfoByCarId(driverInfo.getCarId());
                CarInfoDTO carInfoDTO = new CarInfoDTO(carInfo, driverInfo);
                return new Message(Message.SUCCESS, "查询司机信息 >> 成功", carInfoDTO);
            }
            return new Message(Message.FAILURE, "查询司机信息 >> 失败", "未查询到司机手机号码为 [" + driverPhoneNumber + "] 的信息");
        } catch (Exception e) {
            return new Message(Message.ERROR, "查询司机信息 >> 异常", e.getMessage());
        }

    }

    /**
     * 根据司机星级查找司机信息，同时可以关联上司机的车辆信息
     */
    @RequestMapping(value = "/getDriveInfoByDriverLevelStar/{driverLevelStar}", method = RequestMethod.GET)
    public Message getDriveInfoByDriverLevelStar(@PathVariable(value = "driverLevelStar") int driverLevelStar){
        DriverInfo driverInfo;
        try {
            driverInfo = driverInfoService.getDriveInfoByDriverLevelStar(driverLevelStar);
            if (driverInfo != null){
                CarInfo carInfo = carInfoService.getCarInfoByCarId(driverInfo.getCarId());
                CarInfoDTO carInfoDTO = new CarInfoDTO(carInfo, driverInfo);
                return new Message(Message.SUCCESS, "查询司机信息 >> 成功", carInfoDTO);
            }
            return new Message(Message.FAILURE, "查询司机信息 >> 失败", "未查询到司机星级为 [" + driverLevelStar + "] 的信息");
        } catch (Exception e) {
            return new Message(Message.ERROR, "查询司机信息 >> 异常", e.getMessage());
        }

    }
    /**
     * 多条件查询司机信息
     * @param tableMessage
     * @return
     */
    @RequestMapping(value = "/getDriverInfoByMultiCondition", method = RequestMethod.POST)
    public Message getDriverByMultiCondition(@RequestBody DriverInfoTableMessage tableMessage){
        List<DriverInfo> driverInfo;
        CarInfo carInfo;
        List<CarInfoDTO> carInfoDTOList = new ArrayList<>();
        int count = 0;
        Map<String, Object> map = new HashMap<>();
        tableMessage.getDriverInfo().setDriverName("%" + tableMessage.getDriverInfo().getDriverName() + "%");
        try {
            driverInfo = driverInfoService.getDriverByMultiCondition(tableMessage);
            count = driverInfoService.getDriverByMultiConditionCount(tableMessage);
            if (driverInfo != null){
                for (DriverInfo driverInfos : driverInfo){
                    carInfo = carInfoService.getCarInfoByCarId(driverInfos.getCarId());
                    CarInfoDTO carInfoDTO = new CarInfoDTO(carInfo, driverInfos);
                    carInfoDTOList.add(carInfoDTO);
                }
                map.put("count", count);
                map.put("driverInfos", carInfoDTOList);
                return new Message(Message.SUCCESS, "查询车辆信息 >> 成功", map);
            }
            return new Message(Message.FAILURE, "查询车辆信息 >> 失败", "无符合条件车辆");
        } catch (Exception e) {
            return new Message(Message.ERROR, "查询车辆信息 >> 异常",  e.getMessage());
        }
    }
    /**
     * 更新司机信息
     */
    @RequestMapping(value = "/updateDriverInfoByDriverId",method = RequestMethod.POST)
    public Message updateDriverInfoByDriverId(@RequestBody DriverInfo driverInfo) {
        int upStatus = 0;
        try {
            upStatus = driverInfoService.updateDriverInfoByDriverId(driverInfo);
            if (upStatus == 1) {
                return new Message(Message.SUCCESS, "更新司机信息 >> 成功", upStatus);
            }
            return new Message(Message.FAILURE, "更新司机信息 >> 失败", upStatus);
        } catch (Exception e) {
            return new Message(Message.ERROR, "更新司机信息 >> 异常", e.getMessage());
        }
    }

    /**
     * 根据司机Id删除司机信息
     * @param driverId
     */
    @RequestMapping(value = "/deleteDriverInfoByDriverId", method = RequestMethod.POST)
    public Message deleteDriverInfoByDriverId(@RequestParam(value = "driverId") int driverId){
        int delStatus = 0;
        try {
            delStatus = driverInfoService.deleteDriverInfoByDriverId(driverId);
            if (delStatus == 1){
                return new Message(Message.SUCCESS, "删除车辆信息 >> 成功", delStatus);
            }
            return new Message(Message.FAILURE, "删除车辆信息 >> 失败", delStatus);
        } catch (Exception e) {
            return new Message(Message.ERROR, "删除车辆信息 >> 异常",  e.getMessage());
        }
    }

    /**
     * 导入Excel表中司机的信息
     */
    @RequestMapping(value="/importExcel", method=RequestMethod.POST)
    @ResponseBody
    public Message importExcel(@RequestParam(value = "sourceRiskFile", required = false) MultipartFile sourceRiskFile) {
        String fileName = sourceRiskFile.getOriginalFilename();
        logger.info(fileName);
        //判断文件是否为空
        if (sourceRiskFile != null) {
            //判断该文件是否为Excel文件
            if (fileName.matches("^.+\\.(?i)((xls)|(xlsx))$")) {
                try {
                    int res = driverInfoService.importExcel(sourceRiskFile, sourceRiskFile.getOriginalFilename());
                    return  new Message(Message.SUCCESS,"导入信息 >> 成功","成功添加" + res + "条记录");
                } catch (Exception e) {
                    e.printStackTrace();
                    return  new Message(Message.FAILURE,"导入信息 >> 失败","添加失败，请检查文件是否符合要求");
                }
            } else {
                return  new Message(Message.FAILURE,"导入信息 >> 失败","添加失败，请检查文件是否为Excel文件");
            }
        }
        return null;

    }

    @RequestMapping(value = "/makeExcel",method = RequestMethod.POST)
    @ResponseBody
    public Message makeExcel(){
        try{
            excel.pushExcel(driverInfoService.getAllDriverInfo());
            return new Message(1,"导出成功,文件位置存放在D盘根目录下",null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(0,"导出失败",null);
        }

    }

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
