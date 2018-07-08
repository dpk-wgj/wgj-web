package com.dpk.wgj.controller;

import com.dpk.wgj.bean.AdminInfo;
import com.dpk.wgj.bean.Message;
import com.dpk.wgj.service.AdminInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class AdminInfoController {

    @Autowired
    private AdminInfoService adminInfoService;

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

}
