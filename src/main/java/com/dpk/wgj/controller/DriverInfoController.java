package com.dpk.wgj.controller;


import com.dpk.wgj.bean.DriverInfo;
import com.dpk.wgj.bean.Message;
import com.dpk.wgj.service.DriverInfoService;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/admin/driver")
public class DriverInfoController {

    @Autowired
    private DriverInfoService driverInfoService;

    /**
     * 根据司机名字查找司机信息，同时可以关联上司机的车辆信息
     */

    @RequestMapping(value = "/getDriveInfoByDriverName/{driverName}", method = RequestMethod.GET)
     public Message getDriverInfoByDriverName(@PathVariable(value = "driverName") String driverName){
        DriverInfo driverInfo;
        try {
            driverInfo = driverInfoService.getDriverInfoByDriverName(driverName);
            if (driverInfo != null){
                return new Message(Message.SUCCESS, "查询司机信息 >> 成功", driverInfo);
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
                return new Message(Message.SUCCESS, "查询司机信息 >> 成功", driverInfo);
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
                return new Message(Message.SUCCESS, "查询司机信息 >> 成功", driverInfo);
            }
            return new Message(Message.FAILURE, "查询司机信息 >> 失败", "未查询到司机星级为 [" + driverLevelStar + "] 的信息");
        } catch (Exception e) {
            return new Message(Message.ERROR, "查询司机信息 >> 异常", e.getMessage());
        }

    }



    @RequestMapping(value="importExcel", method=RequestMethod.POST)
    @ResponseBody
    public Message importExcel(@RequestParam(value = "sourceRiskFile", required = false) MultipartFile sourceRiskFile,
                                  HttpServletRequest request, Model model) {
        String fileName = sourceRiskFile.getOriginalFilename();
        System.out.println(fileName);
//        Message result = new Message(0,null,null);
//        if (sourceRiskFile != null) {
//            //判断该文件是否为Excel文件
//            if (fileName.matches("^.+\\.(?i)((xls)|(xlsx))$")) {
//                try {
//                    int res = sourceRiskService.importExcel(sourceRiskFile, sourceRiskFile.getOriginalFilename());
//                    result.setMessage("成功添加" + res + "条记录");
//                    result.setStatusCode(200);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    result.setStatusCode(500);
//                    result.setMessage("添加失败，请检查文件是否符合要求");
//                }
//            } else {
//                result.setStatusCode(500);
//                result.setMessage("添加失败，请检查文件是否为Excel文件");
//            }
//        }
//        return result;
        return null;
    }
    }
