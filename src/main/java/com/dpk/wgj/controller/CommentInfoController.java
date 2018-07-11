package com.dpk.wgj.controller;

import com.dpk.wgj.bean.CommentInfo;
import com.dpk.wgj.bean.Message;
import com.dpk.wgj.service.CommentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhoulin on 2018/7/11.
 * 订单 -- 评价功能
 */
@RestController
@RequestMapping(value = "/api/passenger")
public class CommentInfoController {

    @Autowired
    private CommentInfoService commentInfoService;

    /**
     * 乘客端 >> 添加评价
     */
    @RequestMapping(value = "/addCommentInfoByOrderId", method = RequestMethod.POST)
    public Message addCommentInfoByOrderId(@RequestBody CommentInfo commentInfo){

        int addStatus = 0;
        try {
            addStatus = commentInfoService.addCommentInfoByOrderId(commentInfo);
            if (addStatus == 1){
                return new Message(Message.SUCCESS, "乘客端 >> 添加评价 >> 成功", addStatus);
            }
            return new Message(Message.FAILURE, "乘客端 >> 添加评价 >> 失败", addStatus);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "乘客端 >> 添加评价 >> 异常", "请求异常");
        }
    }

    /**
     * 乘客端 >> 删除评价
     */
    @RequestMapping(value = "/deleteCommentInfoByCommentId", method = RequestMethod.POST)
    public Message deleteCommentInfoByCommentId(@RequestBody CommentInfo commentInfo){

        int delStatus = 0;
        try {
            delStatus = commentInfoService.deleteCommentInfoByCommentId(commentInfo.getCommentId());
            if (delStatus == 1){
                return new Message(Message.SUCCESS, "乘客端 >> 删除评价 >> 成功", delStatus);
            }
            return new Message(Message.FAILURE, "乘客端 >> 删除评价 >> 失败", delStatus);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "乘客端 >> 删除评价 >> 异常", "请求异常");
        }
    }

    /**
     * 乘客端 >> 查看订单评价
     */
    @RequestMapping(value = "/getCommendInfoByOrderId", method = RequestMethod.POST)
    public Message getCommendInfoByOrderId(@RequestBody CommentInfo commentInfo){

        CommentInfo targetCommentInfo;
        try {
            targetCommentInfo = commentInfoService.getCommendInfoByOrderId(commentInfo.getOrderId());
            if (targetCommentInfo != null){
                return new Message(Message.SUCCESS, "乘客端 >> 查看订单评价 >> 成功", targetCommentInfo);
            }
            return new Message(Message.FAILURE, "乘客端 >> 查看订单评价 >> 失败", targetCommentInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "乘客端 >> 查看订单评价 >> 异常", "请求异常");
        }
    }

}
