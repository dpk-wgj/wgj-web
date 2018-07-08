package com.dpk.wgj.controller;


import com.dpk.wgj.bean.DriverInfo;
import com.dpk.wgj.bean.Message;
import com.dpk.wgj.service.DriverInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
//@RequestMapping(value = "/admin/driver")
public class DriverInfoController {

    @Autowired
    private DriverInfoService driverInfoService;

    @RequestMapping(value = "/getDriveInfo/{driverName,driverPhoneNumber,driverLevelStar}", method = RequestMethod.POST)
     public Message getDriverInfo(@PathVariable(value = "driverName") String driverName,
                                  @PathVariable(value = "driverPhoneNumber") String driverPhoneNumber,@PathVariable(value = "driverLevelStar") int driverLevelStar){
        DriverInfo driverInfo;
        try {
            driverInfo = driverInfoService.getDriverInfo(driverName,driverPhoneNumber,driverLevelStar);
            if (driverInfo != null){
                return new Message(Message.SUCCESS, "查询司机信息 >> 成功", driverInfo);
            }
            return new Message(Message.FAILURE, "查询司机信息 >> 失败", "未查询到司机 [" + driverName + "] 信息");
        } catch (Exception e) {
            return new Message(Message.ERROR, "查询司机信息 >> 异常", e.getMessage());
        }

        }
//    @RequestMapping(value="importExcel", method=RequestMethod.POST)
//    @ResponseBody
//    public Message importExcel(@RequestParam(value = "sourceRiskFile", required = false) MultipartFile sourceRiskFile,
//                                  HttpServletRequest request, Model model) {
//        String fileName = sourceRiskFile.getOriginalFilename();
//        System.out.println(fileName);
//        if (sourceRiskFile != null) {
//            //判断该文件是否为Excel文件
//            if (fileName.matches("^.+\\.(?i)((xls)|(xlsx))$")) {
//                try {
//                    int res = sourceRiskService.importExcel(sourceRiskFile, sourceRiskFile.getOriginalFilename());
//                   return new Message(Message.SUCCESS,"成功添加 [ "+ res +"] 条记录");
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
//
//    }
    }
