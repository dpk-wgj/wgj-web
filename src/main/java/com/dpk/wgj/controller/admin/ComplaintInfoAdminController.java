package com.dpk.wgj.controller.admin;

import com.dpk.wgj.bean.ComplaintInfo;
import com.dpk.wgj.bean.Message;
import com.dpk.wgj.service.ComplaintInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by zhoulin on 2018/7/12.
 * 管理员端 订单投诉处理
 */
@RestController
@RequestMapping(value = "/admin/complaintInfo")
public class ComplaintInfoAdminController {

    @Autowired
    private ComplaintInfoService complaintInfoService;

    /**
     * 订单投诉处理
     * @param complaintInfo
     * @return
     */
    @RequestMapping(value = "/feedbackComplaintInfo", method = RequestMethod.POST)
    public Message feedbackComplaintInfo(@RequestBody ComplaintInfo complaintInfo) {

        complaintInfo.setComplaintFeedbackTime(new Date());

        int upStatus = 0;

        try {
            upStatus = complaintInfoService.updateComplaintInfoStatus(complaintInfo);
            if (upStatus == 1){
                return new Message(Message.SUCCESS, "订单投诉处理 >> 成功", upStatus);
            }
            return new Message(Message.SUCCESS, "订单投诉处理 >> 失败", upStatus);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.SUCCESS, "订单投诉处理 >> 异常", e.getMessage());
        }
    }

    /**
     * 订单投诉处理
     * @param complaintInfo
     * @return
     */
    @RequestMapping(value = "/findOrderInfoByMultiCondition", method = RequestMethod.POST)
    public Message findOrderInfoByMultiCondition(@RequestBody ComplaintInfo complaintInfo) {

        complaintInfo.setComplaintFeedbackTime(new Date());

        int upStatus = 0;

        try {
            upStatus = complaintInfoService.updateComplaintInfoStatus(complaintInfo);
            if (upStatus == 1){
                return new Message(Message.SUCCESS, "订单投诉处理 >> 成功", upStatus);
            }
            return new Message(Message.SUCCESS, "订单投诉处理 >> 失败", upStatus);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.SUCCESS, "订单投诉处理 >> 异常", e.getMessage());
        }
    }


}
