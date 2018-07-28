package com.dpk.wgj.controller.admin;

import com.dpk.wgj.bean.DTO.PointDTO;
import com.dpk.wgj.bean.DTO.UserDTO;
import com.dpk.wgj.bean.DriverInfo;
import com.dpk.wgj.bean.Message;
import com.dpk.wgj.mapper.AdminGroupAuthorityMapper;
import com.dpk.wgj.service.DriverInfoService;
import com.dpk.wgj.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 管理员端 交通状态查询（高级/超级管理员）
 */
@RestController
@RequestMapping(value = "/admin/point")
public class PointAdminController {

    @Autowired
    private PointService pointService;

    @Autowired
    private AdminGroupAuthorityMapper adminGroupAuthorityMapper;

    @Autowired
    private DriverInfoService driverInfoService;

    @RequestMapping(value = "/getTrafficSituation", method = RequestMethod.POST)
    public Message getTrafficSituation(){

        UserDTO userInfo = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getDetails();

        try {
            // 判断权限
            String authorityContent = adminGroupAuthorityMapper.getAdminGroupAuthorityById(userInfo.getAuthorityId()).getAdminGroupName();

            if (authorityContent.equals("高级管理员") || authorityContent.equals("超级管理员")) {
                List<DriverInfo> list = driverInfoService.getAllDriverInfo();
                //121.5273285,31.21515044
                double[][] points = new double[list.size()][2];
                for (int i = 0; i < list.size(); i++) {
                    String[] locations = list.get(i).getDriverLocation().split(",");
                    points[i][0] = Double.valueOf(locations[0]);
                    points[i][1] = Double.valueOf(locations[1]);
                }

                PointDTO point = pointService.findPoint(points, 0.01, 3);
                return new Message(Message.SUCCESS, authorityContent + ": 交通情况 >> 获取成功", point);
            }

            return new Message(Message.NOT_LEGAL, "权限不合法", authorityContent + ": 权限不足");

        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "交通情况 >> 获取异常", "请求异常");
        }
    }

}
