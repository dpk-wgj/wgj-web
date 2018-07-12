package com.dpk.wgj.controller;

import com.dpk.wgj.bean.AdminInfo;
import com.dpk.wgj.bean.DTO.UserDTO;
import com.dpk.wgj.bean.Message;
import com.dpk.wgj.service.AdminInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class AdminInfoController {

    @Autowired
    private AdminInfoService adminInfoService;

    private final Logger logger = LoggerFactory.getLogger(AdminInfoController.class);

    /**
     * 根据ID名查找用户信息（例子）
     */
    @RequestMapping(value = "/getUserInfoById", method = RequestMethod.POST)
    public Message getUserInfoById(@RequestParam(value = "userId") int userId){

        try {
            AdminInfo adminInfo;
            adminInfo = adminInfoService.getUserInfoById(userId);
            if (adminInfo != null){
                return new Message(Message.SUCCESS, "获取用户信息 >> 成功", adminInfo);
            }
            return new Message(Message.FAILURE, "获取用户信息 >> 失败", "未找到该用户");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "获取用户信息 >> 异常", "查找异常");
        }
    }

    /**
     * 根据用户名查找用户信息（qinhua）
     */
    @RequestMapping(value = "/getAdminByUsername", method = RequestMethod.POST)
    public Message getAdminByUsername(@RequestParam(value = "username") String username){

        AdminInfo adminInfo;
        try {
            adminInfo = adminInfoService.getAdminByUsername(username);
            if (adminInfo != null){
                return new Message(Message.SUCCESS, "获取用户信息 >> 成功", adminInfo);
            }
            return new Message(Message.FAILURE, "获取用户信息 >> 失败", "未找到该用户");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "获取用户信息 >> 异常", "查找异常");
        }
    }

    /**
     * 根据用户名模糊查找用户信息（qinhua）
     */
    @RequestMapping(value = "/getAllAdminByUsername", method = RequestMethod.POST)
    public Message getAllAdminByUsername(@RequestParam(value = "username") String username){

        List<AdminInfo> adminInfos;
        try {
            adminInfos = adminInfoService.getAllAdminByUsername(username);
            if (adminInfos != null){
                return new Message(Message.SUCCESS, "获取用户信息 >> 成功", adminInfos);
            }
            return new Message(Message.FAILURE, "获取用户信息 >> 失败", "未找到该用户");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "获取用户信息 >> 异常", "查找异常");
        }
    }

    /**
     * 根据用户ID删除用户信息（qinhua）
     * RequestParam
     */
    @RequestMapping(value = "/deleteAdminInfoById", method = RequestMethod.POST)
    public Message deleteAdminInfoById(@RequestParam(value = "userId") int userId){
        int delStatus = 0;
        try {
            delStatus = adminInfoService.deleteAdminInfoById(userId);
            if (delStatus == 1){
                return new Message(Message.SUCCESS, "删除用户信息 >> 成功", delStatus);
            }
            return new Message(Message.FAILURE, "删除用户信息 >> 失败", delStatus);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "删除用户信息 >> 异常", e.getMessage());
        }
    }

    /**
     * 添加用户信息（qinhua）
     */
    @RequestMapping(value = "/addAdminInfo", method = RequestMethod.POST)
    public Message addAdminInfo(@RequestBody AdminInfo adminInfo){
        int addStatus = 0;
        try {
            addStatus = adminInfoService.addAdminInfo(adminInfo);
            if (addStatus == 1){
                return new Message(Message.SUCCESS, "添加用户信息 >> 成功", addStatus);
            }
            return new Message(Message.FAILURE, "添加用户信息 >> 失败", addStatus);
        } catch (Exception e) {
            return new Message(Message.ERROR, "添加用户信息 >> 异常",  e.getMessage());
        }
    }

    /**
     * 更新用户信息（qinhua）
     * 需要ById不？
     */
    @RequestMapping(value = "/updateAdminInfo", method = RequestMethod.POST)
    public Message updateAdminInfo(@RequestBody AdminInfo adminInfo){
        int upStatus = 0;
        try {
            upStatus = adminInfoService.updateAdminInfo(adminInfo);
            if (upStatus == 1){
                return new Message(Message.SUCCESS, "更新用户信息 >> 成功", upStatus);
            }
            return new Message(Message.FAILURE, "更新用户信息 >> 失败", upStatus);
        } catch (Exception e) {
            return new Message(Message.ERROR, "更新用户信息 >> 异常",  e.getMessage());
        }
    }

    /**
     * 获取所有用户信息（qinhua）
     */
    @RequestMapping(value = "/getAllAdminInfo", method = RequestMethod.GET)
    public Message getAllAdminInfo(){
        List<AdminInfo> adminInfos;
        try {
            adminInfos = adminInfoService.getAllAdminInfo();
            if (adminInfos != null){
                return new Message(Message.SUCCESS, "获取用户信息 >> 成功", adminInfos);
            }
            return new Message(Message.FAILURE, "获取用户信息 >> 失败", "无用户");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "获取用户信息 >> 异常", e.getMessage());
        }
    }

}
