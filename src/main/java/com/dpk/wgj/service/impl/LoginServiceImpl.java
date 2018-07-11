package com.dpk.wgj.service.impl;

import com.dpk.wgj.bean.*;
import com.dpk.wgj.bean.DTO.UserDTO;
import com.dpk.wgj.config.security.JwtTokenUtil;
import com.dpk.wgj.mapper.AdminInfoMapper;
import com.dpk.wgj.mapper.DriverInfoMapper;
import com.dpk.wgj.mapper.PassengerMapper;
import com.dpk.wgj.mapper.UserGroupMapper;
import com.dpk.wgj.service.AdminInfoService;
import com.dpk.wgj.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Component
public class LoginServiceImpl implements LoginService {

    @Autowired
    private DriverInfoMapper driverInfoMapper;

    @Autowired
    private PassengerMapper passengerMapper;

    @Autowired
    private UserGroupMapper userGroupMapper;

    @Autowired
    private AdminInfoMapper adminInfoMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public Message loginForDriver(UserDTO userInfo, HttpServletResponse response) {
        UserDTO user = new UserDTO();
        DriverInfo driverInfo = new DriverInfo();
        DriverInfo driverInfo1 = new DriverInfo();
        try {
            driverInfo = driverInfoMapper.getDriverInfoByWxId(userInfo.getDriverInfo().getDriverWxId());

            // 用户不存在则自动添加
            if (driverInfo == null){
                driverInfo1.setDriverWxId(userInfo.getDriverInfo().getDriverWxId());
                driverInfo1.setUserGroupId(2);
                driverInfoMapper.addDriverInfo(driverInfo1);
                user.setDriverInfo(driverInfo1);
            } else {
                user.setDriverInfo(driverInfo);
            }

            if(user != null){
//                if(userInfo.getPassword().equals(user.getPassword())&&userInfo.getUsername().equals(user.getUsername())){
                  if (true){
                    UserGroup userGroup = userGroupMapper.getByUserId(user.getDriverInfo().getUserGroupId());

                    user.setUserGroup(userGroup);

                    List<String> roles = new ArrayList<>();

                    roles.add(userGroup.getPermission());

                    user.setRoles(roles);

                    response.addHeader("refresh",jwtTokenUtil.create(user));

                    return new Message(Message.SUCCESS,"登陆成功 >> 司机", user);
                }else {
                    return new Message(Message.ERROR,"密码错误",null);
                }
            }else {
                return new Message(Message.ERROR,"用户名不存在",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"异常",null);
        }
    }

    @Override
    public Message loginForPassenger(UserDTO userInfo, HttpServletResponse response) {
        UserDTO user = new UserDTO();
        Passenger passenger = new Passenger();
        Passenger passenger1 = new Passenger();
        try {
            passenger = passengerMapper.getPassengerByWxId(userInfo.getPassenger().getPassengerWxId());

            // 用户不存在则自动添加
            if (passenger == null){
                passenger1.setPassengerWxId(userInfo.getPassenger().getPassengerWxId());
                passenger1.setUserGroupId(2);
                passengerMapper.addPassenger(passenger1);
                user.setPassenger(passenger1);
            } else {
                user.setPassenger(passenger);
            }

            user.setPassenger(passenger);
            if(user != null){
//                if(userInfo.getPassword().equals(user.getPassword())&&userInfo.getUsername().equals(user.getUsername())){
                if (true){
                    UserGroup userGroup = userGroupMapper.getByUserId(user.getPassenger().getUserGroupId());

                    user.setUserGroup(userGroup);

                    List<String> roles = new ArrayList<>();

                    roles.add(userGroup.getPermission());

                    user.setRoles(roles);

                    response.addHeader("refresh",jwtTokenUtil.create(user));

                    return new Message(Message.SUCCESS,"登陆成功 >> 乘客", user);
                }else {
                    return new Message(Message.ERROR,"密码错误",null);
                }
            }else {
                return new Message(Message.ERROR,"用户名不存在",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"异常",null);
        }
    }

    @Override
    public Message loginForAdminInfo(UserDTO userInfo, HttpServletResponse response) {
        UserDTO user = new UserDTO();

        AdminInfo adminInfo = new AdminInfo();

        try {
            adminInfo = adminInfoMapper.getAdminByUsername(userInfo.getAdminInfo().getUsername());
            if(user != null){
                user.setAdminInfo(adminInfo);
                if(userInfo.getAdminInfo().getPassword().equals(adminInfo.getPassword())&&userInfo.getAdminInfo().getUsername().equals(adminInfo.getUsername())){

                    UserGroup userGroup = userGroupMapper.getByUserId(adminInfo.getUserGroupId());

                    user.setUserGroup(userGroup);

                    List<String> roles = new ArrayList<>();

                    roles.add(userGroup.getPermission());

                    user.setRoles(roles);

                    response.addHeader("refresh",jwtTokenUtil.create(user));

                    return new Message(Message.SUCCESS,"登陆成功 >> 管理员", user);
                }else {
                    return new Message(Message.ERROR,"密码错误",null);
                }
            }else {
                return new Message(Message.ERROR,"用户名不存在",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
