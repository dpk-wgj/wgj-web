package com.dpk.wgj.controller;

import com.dpk.wgj.bean.ComplaintInfo;
import com.dpk.wgj.bean.DTO.UserDTO;
import com.dpk.wgj.bean.Message;
import com.dpk.wgj.bean.OrderInfo;
import com.dpk.wgj.service.ComplaintInfoService;
import com.dpk.wgj.service.OrderInfoService;
import com.dpk.wgj.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhoulin on 2018/7/12.
 * 乘客端 >> 订单投诉信息
 */
@RestController
@RequestMapping(value = "/api/passenger")
public class ComplaintInfoController {

    @Autowired
    private ComplaintInfoService complaintInfoService;

    @Autowired
    private OrderInfoService orderInfoService;

    /**
     * 提交订单投诉
     * @param complaintInfo
     * @return
     */
    @RequestMapping(value = "/addComplaintInfoByOrderId", method = RequestMethod.POST)
    @Transactional
    public Message addComplaintInfoByOrderId(@RequestBody ComplaintInfo complaintInfo){

        // 防止恶意提交投诉
        UserDTO userInfo = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getDetails();
        int passengerId = userInfo.getUserId();
//        boolean flag = false;

        int addStatus = 0;
        complaintInfo.setPassengerId(passengerId);
        try {
            // 校验
//            List<OrderInfo> orderInfoList = orderInfoService.getOrderInfoByPassengerId(passengerId);
//            for (OrderInfo orderInfo : orderInfoList){
//                if (orderInfo.getOrderId() == complaintInfo.getOrderId()){
//                    flag = true;
//                    break;
//                }
//            }
//            if (flag = true){
                addStatus = complaintInfoService.addComplaintInfoByOrderId(complaintInfo);
                if (addStatus == 1){
                    return new Message(Message.SUCCESS, "提交 >> 订单投诉 >> 成功", addStatus);
                }
                return new Message(Message.FAILURE, "提交 >> 订单投诉 >> 失败", addStatus);
//            }
//            return new Message(Message.FAILURE, "提交 >> 订单投诉 >> 错误提交", "校验错误");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.FAILURE, "提交 >> 订单投诉 >> 失败", addStatus);
        }
    }

    /**
     * 删除订单投诉
     * @param complaintInfo
     * @return
     */
    @RequestMapping(value = "/deleteComplaintInfoByCommentId", method = RequestMethod.POST)
    @Transactional
    public Message deleteComplaintInfoByCommentId(@RequestBody ComplaintInfo complaintInfo){
        // 防止恶意提交投诉
//        UserDTO userInfo = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getDetails();
//        int passengerId = userInfo.getUserId();
//        boolean flag = false;

        int delStatus = 0;

        try {
            // 校验
//            List<OrderInfo> orderInfoList = orderInfoService.getOrderInfoByPassengerId(passengerId);
//            for (OrderInfo orderInfo : orderInfoList){
//                if (orderInfo.getOrderId() == complaintInfo.getOrderId()){
//                    flag = true;
//                    break;
//                }
//            }
//            if (flag = true){
            delStatus = complaintInfoService.deleteComplaintInfoByCommentId(complaintInfo.getComplaintId());
            if (delStatus == 1){
                return new Message(Message.SUCCESS, "删除 >> 订单投诉 >> 成功", delStatus);
            }
            return new Message(Message.FAILURE, "删除 >> 订单投诉 >> 失败", delStatus);
//            }
//            return new Message(Message.FAILURE, "提交 >> 订单投诉 >> 错误提交", "校验错误");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.FAILURE, "删除 >> 订单投诉 >> 失败", delStatus);
        }
    }

    /**
     * 查看订单投诉
     * @param complaintInfo
     * @return
     */
    @RequestMapping(value = "/getComplaintInfoByOrderId", method = RequestMethod.GET)
    @Transactional
    public Message getComplaintInfoByOrderId(@RequestBody ComplaintInfo complaintInfo){
        // 防止恶意提交投诉
//        UserDTO userInfo = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getDetails();
//        int passengerId = userInfo.getUserId();
//        boolean flag = false;

        ComplaintInfo targetComplaintInfo;
        try {
            // 校验
//            List<OrderInfo> orderInfoList = orderInfoService.getOrderInfoByPassengerId(passengerId);
//            for (OrderInfo orderInfo : orderInfoList){
//                if (orderInfo.getOrderId() == complaintInfo.getOrderId()){
//                    flag = true;
//                    break;
//                }
//            }
//            if (flag = true){
            targetComplaintInfo = complaintInfoService.getComplaintInfoByOrderId(complaintInfo.getComplaintId());
            if (targetComplaintInfo != null){
                return new Message(Message.SUCCESS, "查看 >> 订单投诉 >> 成功", targetComplaintInfo);
            }
            return new Message(Message.FAILURE, "查看 >> 订单投诉 >> 失败", targetComplaintInfo);
//            }
//            return new Message(Message.FAILURE, "提交 >> 订单投诉 >> 错误提交", "校验错误");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.FAILURE, "查看 >> 订单投诉 >> 失败", e.getMessage());
        }
    }

    @RequestMapping(value = "/getComplaintInfoByPassengerId", method = RequestMethod.POST)
    @Transactional
    public Message getComplaintInfoByPassengerId(){

        // 防止恶意提交投诉
        UserDTO userInfo = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getDetails();
        int passengerId = userInfo.getUserId();

        List<ComplaintInfo> complaintInfos;
        try {
            complaintInfos = complaintInfoService.getComplaintInfoByPassengerId(passengerId);
            if (complaintInfos != null){
                return new Message(Message.SUCCESS, "获取 >> 用户订单投诉列表 >> 成功", complaintInfos);
            }
            return new Message(Message.FAILURE, "获取 >> 用户订单投诉列表 >> 失败", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.FAILURE, "获取 >> 用户订单投诉列表 >> 异常", e.getMessage());
        }
    }


}
