package com.dpk.wgj.controller;

import com.dpk.wgj.bean.*;
import com.dpk.wgj.bean.DTO.UserDTO;
import com.dpk.wgj.bean.tableInfo.ComplaintMessage;
import com.dpk.wgj.service.ComplaintInfoService;
import com.dpk.wgj.service.LogInfoService;
import com.dpk.wgj.service.OrderInfoService;
import com.dpk.wgj.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by zhoulin on 2018/7/12.
 * 乘客端 >> 订单投诉信息
 *
 * Created by hlx on 2018/7/18
 * 后台端 >> 订单投诉信息查询和反馈更新
 */
@RestController
//@RequestMapping(value = "/api/passenger")
public class ComplaintInfoController {

    @Autowired
    private ComplaintInfoService complaintInfoService;

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private LogInfoService logInfoService;

    @Autowired
    private PassengerService passengerService;

    /**
     * 乘客端 >> 提交订单投诉
     * @param complaintInfo
     * @return
     */
    @RequestMapping(value = "/api/passenger/addComplaintInfoByOrderId", method = RequestMethod.POST)
    @Transactional
    public Message addComplaintInfoByOrderId(@RequestBody ComplaintInfo complaintInfo){

        // 防止恶意提交投诉
        UserDTO userInfo = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getDetails();
        int passengerId = userInfo.getUserId();
//        boolean flag = false;

        int addStatus = 0;
        complaintInfo.setPassengerId(passengerId);
        complaintInfo.setComplaintCreateTime(new Date());

        try {

            // 插入用户成为日志
            logInfoService.addLogInfo(new LogInfo("乘客端 >> 提交订单投诉", 2, new Date(), complaintInfo.getOrderId()));

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
     * 乘客端 >> 删除订单投诉
     * @param complaintInfo
     * @return
     */
    @RequestMapping(value = "/api/passenger/deleteComplaintInfoByCommentId", method = RequestMethod.POST)
    @Transactional
    public Message deleteComplaintInfoByCommentId(@RequestBody ComplaintInfo complaintInfo){
        // 防止恶意提交投诉
//        UserDTO userInfo = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getDetails();
//        int passengerId = userInfo.getUserId();
//        boolean flag = false;

        int delStatus = 0;

        try {

            // 插入用户成为日志
            logInfoService.addLogInfo(new LogInfo("乘客端 >> 删除订单投诉", 2, new Date(), complaintInfo.getOrderId()));

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
     * 乘客端 >> 查看订单投诉
     * @param complaintInfo
     * @return
     */
    @RequestMapping(value = "/api/passenger/getComplaintInfoByOrderId", method = RequestMethod.GET)
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

    @RequestMapping(value = "/api/passenger/getComplaintInfoByPassengerId", method = RequestMethod.POST)
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

    /***
     * 后台端 >> 多条件查询投诉
     *  @return
     */
    @RequestMapping(value = "/admin/complaintInfo/findComplaintInfoByMultiCondition", method = RequestMethod.POST)
    @Transactional
    public Message findComplaintInfoByMultiCondition(@RequestBody ComplaintMessage tableMessage){
        List<ComplaintInfo> complaintInfos = new ArrayList<>(); //获取投诉数据列表
        Map<String, Object> map = new HashMap<>();
        try {
            complaintInfos = complaintInfoService.findComplaintInfoByMultiCondition(tableMessage);
            int count = complaintInfoService.findComplaintInfoByMultiConditionCount(tableMessage);
            if (complaintInfos != null){
                map.put("complaintInfos", complaintInfos);
                map.put("count", count);
                return new Message(Message.SUCCESS, "后台端 >> 多条件查询投诉订单 >> 成功", map);
            }
            return new Message(Message.FAILURE, "后台端 >> 多条件查询投诉订单 >> 成功", "无查询结果");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "后台端 >> 多条件查询投诉订单 >> 异常", "请求异常 ");
        }
    }


    /**
     * 后台端 >> 更新投诉信息
     * @param
     * @return
     */
    @RequestMapping(value = "/admin/complaintInfo/updateComplaintInfoByComplaintId",method = RequestMethod.POST)
    public Message updateComplaintInfoByComplaintId(@RequestBody ComplaintInfo complaintInfo) {
        int upStatus = 0;
        int complaintStatus = complaintInfo.getComplaintStatus()+1;//反馈状态加1
        try {
            complaintInfo.setComplaintStatus(complaintStatus);//设置新的反馈状态
            if(complaintStatus == 2)
            {
                complaintInfo.setComplaintFeedbackTime(new Date());//设置反馈时间
            }
            upStatus = complaintInfoService.updateComplaintInfoStatus(complaintInfo);
            if (upStatus == 1)
            {
                return new Message(Message.SUCCESS, "更新投诉信息 >> 成功", upStatus);
            }
            else
                return new Message(Message.FAILURE, "更新投诉信息 >> 失败", "更新失败");

        } catch (Exception e) {
            return new Message(Message.ERROR, "更新投诉信息 >> 异常", e.getMessage());
        }
    }


}
