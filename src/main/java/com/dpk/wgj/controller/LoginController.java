package com.dpk.wgj.controller;

import com.dpk.wgj.bean.AdminInfo;
import com.dpk.wgj.bean.DTO.UserDTO;
import com.dpk.wgj.bean.DriverInfo;
import com.dpk.wgj.bean.Message;
import com.dpk.wgj.bean.Passenger;
import com.dpk.wgj.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by zhoulin on 2018/7/9.
 * 说明:
 */
@RestController
@RequestMapping("/public")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/driver/login",method = RequestMethod.POST)
    public Message loginForDriver(@RequestBody DriverInfo driverInfo, HttpSession session, HttpServletResponse response){

        UserDTO user = new UserDTO();

        user.setDriverInfo(driverInfo);

        Message message = loginService.loginForDriver(user,response);
        if(message != null){
            if(message.getStatus() == Message.SUCCESS){
                session.setAttribute("user",message.getResult());
            }
        }
        return message;
    }

    @RequestMapping(value = "/passenger/login",method = RequestMethod.POST)
    public Message loginForPassenger(@RequestBody Passenger passenger, HttpSession session, HttpServletResponse response){

        UserDTO user = new UserDTO();

        user.setPassenger(passenger);

        Message message = loginService.loginForPassenger(user,response);
        if(message != null){
            if(message.getStatus() == Message.SUCCESS){
                session.setAttribute("user",message.getResult());
            }
        }
        return message;
    }

    @RequestMapping(value = "/admin/login",method = RequestMethod.POST)
    public Message loginForAdmin(@RequestBody AdminInfo adminInfo, HttpSession session, HttpServletResponse response){

        UserDTO user = new UserDTO();

        user.setAdminInfo(adminInfo);

        Message message = loginService.loginForAdminInfo(user,response);
        if(message != null){
            if(message.getStatus() == Message.SUCCESS){
                session.setAttribute("user",message.getResult());
            }
        }
        return message;
    }


}
