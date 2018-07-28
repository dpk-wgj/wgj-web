package com.dpk.wgj.controller.admin;

import com.dpk.wgj.bean.AdminGroupAuthority;
import com.dpk.wgj.bean.ComplaintInfo;
import com.dpk.wgj.bean.DTO.UserDTO;
import com.dpk.wgj.bean.Message;
import com.dpk.wgj.mapper.AdminGroupAuthorityMapper;
import com.dpk.wgj.service.ComplaintInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by zhoulin on 2018/7/12.
 * 管理员端 订单投诉处理 （中级/高级/超级管理员）
 */
@RestController
@RequestMapping(value = "/admin/complaintInfo")
public class ComplaintInfoAdminController {

    @Autowired
    private ComplaintInfoService complaintInfoService;

    @Autowired
    private AdminGroupAuthorityMapper adminGroupAuthorityMapper;

    /**
     * 订单投诉处理
     * @param complaintInfo
     * @return
     */
    @RequestMapping(value = "/feedbackComplaintInfo", method = RequestMethod.POST)
    public Message feedbackComplaintInfo(@RequestBody ComplaintInfo complaintInfo) {
        int upStatus = 0;
        UserDTO userInfo = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getDetails();

        complaintInfo.setComplaintFeedbackTime(new Date());
        try {
            // 判断权限
            String authorityContent = adminGroupAuthorityMapper.getAdminGroupAuthorityById(userInfo.getAuthorityId()).getContent();

            if (authorityContent.equals("中级管理员") || authorityContent.equals("高级管理员") || authorityContent.equals("超级管理员")){
                upStatus = complaintInfoService.updateComplaintInfoStatus(complaintInfo);
                if (upStatus == 1){
                    return new Message(Message.SUCCESS, "订单投诉处理 >> 成功", upStatus);
                }
                return new Message(Message.FAILURE, "订单投诉处理 >> 失败", upStatus);
            }

            return new Message(Message.NOT_LEGAL, "权限不合法", authorityContent + ": 权限不足");

        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "订单投诉处理 >> 异常", e.getMessage());
        }
    }

    /**
     * 订单投诉处理
     * @param complaintInfo
     * @return
     */
    @RequestMapping(value = "/findOrderInfoByMultiCondition", method = RequestMethod.POST)
    public Message findOrderInfoByMultiCondition(@RequestBody ComplaintInfo complaintInfo) {
        UserDTO userInfo = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getDetails();
        complaintInfo.setComplaintFeedbackTime(new Date());

        int upStatus = 0;

        try {
            // 判断权限
            String authorityContent = adminGroupAuthorityMapper.getAdminGroupAuthorityById(userInfo.getAuthorityId()).getContent();

            if (authorityContent.equals("中级管理员") || authorityContent.equals("高级管理员") || authorityContent.equals("超级管理员")) {

                upStatus = complaintInfoService.updateComplaintInfoStatus(complaintInfo);
                if (upStatus == 1) {
                    return new Message(Message.SUCCESS, "订单投诉处理 >> 成功", upStatus);
                }
                return new Message(Message.FAILURE, "订单投诉处理 >> 失败", upStatus);
            }
            return new Message(Message.NOT_LEGAL, "权限不合法", authorityContent + ": 权限不足");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "订单投诉处理 >> 异常", e.getMessage());
        }
    }


}
