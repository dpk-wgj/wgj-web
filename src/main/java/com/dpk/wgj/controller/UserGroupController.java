package com.dpk.wgj.controller;

import com.dpk.wgj.bean.UserGroup;
import com.dpk.wgj.bean.Message;
import com.dpk.wgj.service.UserGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/admin/group")
public class UserGroupController {

    @Autowired
    private UserGroupService userGroupService;

    private final Logger logger = LoggerFactory.getLogger(UserGroupController.class);

    /**
     * 根据分组ID查找分组信息（qinghua）
     */
    @RequestMapping(value = "/getUserGroupById", method = RequestMethod.POST)
    public Message getUserGroupById(@RequestParam(value = "userGroupId") int userGroupId){

        try {
            UserGroup userGroup = userGroupService.getUserGroupById(userGroupId);
            if (userGroup != null){
                return new Message(Message.SUCCESS, "获取分组信息 >> 成功", userGroup);
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
    @RequestMapping(value = "/getUserGroupByName", method = RequestMethod.POST)
    public Message getUserGroupByName(@RequestParam(value = "groupName") String groupName){

        try {
            UserGroup userGroup = userGroupService.getUserGroupByName(groupName);
            if (userGroup != null){
                return new Message(Message.SUCCESS, "获取分组信息 >> 成功", userGroup);
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
    @RequestMapping(value = "/deleteUserGroup", method = RequestMethod.POST)
    public Message deleteUserGroup(@RequestParam(value = "userGroupId") int userGroupId){
        int delStatus = 0;
        try {
            delStatus = userGroupService.deleteUserGroup(userGroupId);
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
    @RequestMapping(value = "/addUserGroup", method = RequestMethod.POST)
    public Message addUserGroup(@RequestBody UserGroup userGroup){
        int addStatus = 0;
        try {
            addStatus = userGroupService.addUserGroup(userGroup);
            if (addStatus == 1){
                return new Message(Message.SUCCESS, "添加分组信息 >> 成功", addStatus);
            }
            return new Message(Message.FAILURE, "添加分组信息 >> 失败", addStatus);
        } catch (Exception e) {
            return new Message(Message.ERROR, "添加分组信息 >> 异常",  e.getMessage());
        }
    }

    /**
     * 更新用户信息（qinhua）
     * 需要ById不？
     */
    @RequestMapping(value = "/updateUserGroupById", method = RequestMethod.POST)
    public Message updateUserGroupById(@RequestBody UserGroup userGroup){
        int upStatus = 0;
        try {
            upStatus = userGroupService.updateUserGroupById(userGroup);
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
    @RequestMapping(value = "/getAlluserGroup", method = RequestMethod.GET)
    public Message getAlluserGroup(){
        List<UserGroup> userGroup;
        try {
            userGroup = userGroupService.getAllUserGroup();
            if (userGroup != null){
                return new Message(Message.SUCCESS, "获取分组信息 >> 成功", userGroup);
            }
            return new Message(Message.FAILURE, "获取分组信息 >> 失败", "无用户");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "获取分组信息 >> 异常", e.getMessage());
        }
    }
}
