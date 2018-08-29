package com.dpk.wgj.controller;

import com.dpk.wgj.bean.AdminGroup;
import com.dpk.wgj.bean.AdminGroupAuthority;
import com.dpk.wgj.bean.AdminInfo;
import com.dpk.wgj.bean.DTO.AdminInfoDTO;

import com.dpk.wgj.bean.DTO.UserDTO;
import com.dpk.wgj.bean.Message;
import com.dpk.wgj.bean.tableInfo.AdminInfoMessage;
import com.dpk.wgj.mapper.AdminGroupAuthorityMapper;
import com.dpk.wgj.service.AdminGroupService;
import com.dpk.wgj.service.AdminInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/admin")
public class AdminInfoController {

    @Autowired
    private AdminInfoService adminInfoService;

    @Autowired
    private AdminGroupService adminGroupService;

    @Autowired
    private AdminGroupAuthorityMapper adminGroupAuthorityMapper;

    private final Logger logger = LoggerFactory.getLogger(AdminInfoController.class);

    @RequestMapping(value = "/getUserInfoById/{userId}", method = RequestMethod.GET)
    public Message getUserInfoById(@PathVariable(value = "userId") int userId){

        try {
            AdminInfo adminInfo = adminInfoService.getUserInfoById(userId);
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
     * 根据用户名查找用户信息(多条件查询)
     */
    @RequestMapping(value = "/getAllAdminInfo", method = RequestMethod.POST)
    public Message getAllAdminInfo(@RequestBody AdminInfoMessage adminInfoMessage){
        List<AdminInfo> adminInfo;
        AdminGroup adminGroups;
        AdminGroupAuthority adminGroupAuthority;
        List<AdminInfoDTO> adminInfoDTOS =  new ArrayList<>();
        int count = 0;
        Map<String ,Object> map = new HashMap<>();
        try {
            adminInfo = adminInfoService.getAllAdminInfo(adminInfoMessage);
            count  = adminInfoService.getAllAdminInfoCount(adminInfoMessage);
            if (adminInfo != null){
                for(AdminInfo adminInfo1:adminInfo)
                {
                    System.out.println(adminInfo1.getUserGroupId());
                    adminGroups = adminGroupService.getAdminGroupById(adminInfo1.getUserGroupId());
                    adminGroupAuthority = adminGroupAuthorityMapper.getAdminGroupAuthorityById(adminInfo1.getAuthorityId());
                    AdminInfoDTO adminInfoDTO= new AdminInfoDTO(adminInfo1, adminGroups,adminGroupAuthority);
                    adminInfoDTOS.add(adminInfoDTO);

                }
                map.put("adminInfos",adminInfoDTOS);
                map.put("count",count);
                return new Message(Message.SUCCESS, "获取用户信息 >> 成功", map);
            }
            return new Message(Message.FAILURE, "获取用户信息 >> 失败", "未找到该用户");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "获取用户信息 >> 异常", "查找异常");
        }
    }


    /**
     * 根据用户ID删除用户信息
     *
     */
    @RequestMapping(value = "/deleteAdminInfoById", method = RequestMethod.POST)
    public Message deleteAdminInfoById(@RequestParam(value = "userId") int userId){
        UserDTO userInfo = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getDetails();
        int delStatus = 0;
        try {
            String authorityContent = adminGroupAuthorityMapper.getAdminGroupAuthorityById(userInfo.getAuthorityId()).getAdminGroupName();
            if ( authorityContent.equals("超级管理员")) {
            delStatus = adminInfoService.deleteAdminInfoById(userId);
            if (delStatus == 1){
                return new Message(Message.SUCCESS, "删除用户信息 >> 成功", delStatus);
            }
            return new Message(Message.FAILURE, "删除用户信息 >> 失败", delStatus);
            }
            return new Message(Message.NOT_LEGAL, "权限不合法",  "您是"+authorityContent+",权限不足，无法进行删除操作！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "删除用户信息 >> 异常", e.getMessage());
        }
    }

    /**
     * 添加用户信息
     */
    @RequestMapping(value = "/addAdminInfo", method = RequestMethod.POST)
    public Message addAdminInfo(@RequestBody AdminInfo adminInfo){
        UserDTO userInfo = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getDetails();
        int addStatus = 0;
        try {
            String authorityContent = adminGroupAuthorityMapper.getAdminGroupAuthorityById(userInfo.getAuthorityId()).getAdminGroupName();
            if ( authorityContent.equals("超级管理员")) {
            addStatus = adminInfoService.addAdminInfo(adminInfo);
            if (addStatus == 1){
                return new Message(Message.SUCCESS, "添加用户信息 >> 成功", addStatus);
            }
            return new Message(Message.FAILURE, "添加用户信息 >> 失败", addStatus);
            }
            return new Message(Message.NOT_LEGAL, "权限不合法",  "您是"+authorityContent+",权限不足，无法进行添加操作！");
        } catch (Exception e) {
            return new Message(Message.ERROR, "添加用户信息 >> 异常",  e.getMessage());
        }
    }
    /**
     * 更新用户信息
     *
     */
    @RequestMapping(value = "/updateAdminInfo", method = RequestMethod.POST)
    public Message updateAdminInfo(@RequestBody AdminInfo adminInfo){
        UserDTO userInfo = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getDetails();
        int upStatus = 0;
        try {
            String authorityContent = adminGroupAuthorityMapper.getAdminGroupAuthorityById(userInfo.getAuthorityId()).getAdminGroupName();
            if ( authorityContent.equals("超级管理员")) {
            upStatus = adminInfoService.updateAdminInfo(adminInfo);
            if (upStatus == 1){
                return new Message(Message.SUCCESS, "更新用户信息 >> 成功", upStatus);
            }
            return new Message(Message.FAILURE, "更新用户信息 >> 失败", upStatus);
            }
            return new Message(Message.NOT_LEGAL, "权限不合法",  "您是"+authorityContent+",权限不足，无法进行修改操作！");
        } catch (Exception e) {
            return new Message(Message.ERROR, "更新用户信息 >> 异常",  e.getMessage());
        }
    }

}
