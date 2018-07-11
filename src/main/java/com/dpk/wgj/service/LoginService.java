package com.dpk.wgj.service;

import com.dpk.wgj.bean.DTO.UserDTO;
import com.dpk.wgj.bean.Message;

import javax.servlet.http.HttpServletResponse;

public interface LoginService {

    public Message loginForDriver(UserDTO userInfo, HttpServletResponse response);

    public Message loginForPassenger(UserDTO userInfo, HttpServletResponse response);

}
