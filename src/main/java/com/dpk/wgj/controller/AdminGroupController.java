package com.dpk.wgj.controller;

import com.dpk.wgj.bean.Message;
import com.dpk.wgj.bean.AdminGroup;
import com.dpk.wgj.service.AdminGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/admin/Admingroup")
public class AdminGroupController {

    @Autowired
    private AdminGroupService adminGroupService;

    private final Logger logger = LoggerFactory.getLogger(AdminGroupController.class);

    /**
     * 根据分组ID查找分组信息（qinghua）
     */
    @RequestMapping(value = "/getAdminGroupById", method = RequestMethod.POST)
    public Message getAdminGroupById(@RequestParam(value = "AdminGroupId") int adminGroupId){

        try {
            AdminGroup adminGroup = adminGroupService.getAdminGroupById(adminGroupId);
            if (adminGroup != null){
                return new Message(Message.SUCCESS, "获取分组信息 >> 成功", adminGroup);
            }
            return new Message(Message.FAILURE, "获取分组信息 >> 失败", "未找到该用户");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "获取分组信息 >> 异常", "查找异常");
        }
    }

    /**
     * 根据分组名查找分组信息（qinhua）
     */
    @RequestMapping(value = "/getAdminGroupByName", method = RequestMethod.POST)
    public Message getAdminGroupByName(@RequestParam(value = "groupName") String groupName){

        try {
            AdminGroup adminGroup = adminGroupService.getAdminGroupByName(groupName);
            if (adminGroup != null){
                return new Message(Message.SUCCESS, "获取分组信息 >> 成功", adminGroup);
            }
            return new Message(Message.FAILURE, "获取分组信息 >> 失败", "未找到该用户");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "获取分组信息 >> 异常", "查找异常");
        }
    }

    /**
     * 根据分组ID删除分组信息（qinhua）
     */
    @RequestMapping(value = "/deleteAdminGroup", method = RequestMethod.POST)
    public Message deleteAdminGroup(@RequestParam(value = "adminGroupId") int adminGroupId){
        int delStatus = 0;
        try {
            delStatus = adminGroupService.deleteAdminGroup(adminGroupId);
            if (delStatus == 1){
                return new Message(Message.SUCCESS, "删除分组信息 >> 成功", delStatus);
            }
            return new Message(Message.FAILURE, "删除分组信息 >> 失败", delStatus);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "删除分组信息 >> 异常", e.getMessage());
        }
    }

    /**
     * 添加用分组信息（qinhua）
     */
    @RequestMapping(value = "/addAdminGroup", method = RequestMethod.POST)
    public Message addAdminGroup(@RequestBody AdminGroup adminGroup){
        int addStatus = 0;
        try {
            addStatus = adminGroupService.addAdminGroup(adminGroup);
            if (addStatus == 1){
                return new Message(Message.SUCCESS, "添加分组信息 >> 成功", addStatus);
            }
            return new Message(Message.FAILURE, "添加分组信息 >> 失败", addStatus);
        } catch (Exception e) {
            return new Message(Message.ERROR, "添加分组信息 >> 异常",  e.getMessage());
        }
    }

    /**
     * 更新分组信息（qinhua）
     *
     */
    @RequestMapping(value = "/updateAdminGroupById", method = RequestMethod.POST)
    public Message updateAdminGroupById(@RequestBody AdminGroup adminGroup){
        int upStatus = 0;
        try {
            upStatus = adminGroupService.updateAdminGroupById(adminGroup);
            if (upStatus == 1){
                return new Message(Message.SUCCESS, "更新分组信息 >> 成功", upStatus);
            }
            return new Message(Message.FAILURE, "更新分组信息 >> 失败", upStatus);
        } catch (Exception e) {
            return new Message(Message.ERROR, "更新分组信息 >> 异常",  e.getMessage());
        }
    }

    /**
     * 获取所有分组信息（qinhua）
     */
    @RequestMapping(value = "/getAllAdminGroup", method = RequestMethod.GET)
    public Message getAllAdminGroup(){
        List<AdminGroup> adminGroup;
        try {
            adminGroup = adminGroupService.getAllAdminGroup();
            if (adminGroup != null){
                return new Message(Message.SUCCESS, "获取分组信息 >> 成功", adminGroup);
            }
            return new Message(Message.FAILURE, "获取分组信息 >> 失败", "无用户");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "获取分组信息 >> 异常", e.getMessage());
        }
    }
}
