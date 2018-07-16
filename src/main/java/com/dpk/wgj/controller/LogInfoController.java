package com.dpk.wgj.controller;

import com.dpk.wgj.bean.LogInfo;
import com.dpk.wgj.bean.Message;
import com.dpk.wgj.bean.tableInfo.LogInfoMessage;
import com.dpk.wgj.service.LogInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhoulin on 2018/7/13.
 * 用户日志处理
 */
@RestController
@RequestMapping(value = "/admin/logInfo")
public class LogInfoController {

    @Autowired
    private LogInfoService logInfoService;

    /**
     * 用户日志信息 多条件查询
     * @param logInfoMessage
     * @return
     */
    @RequestMapping(value = "/findLogInfoMultiCondition", method = RequestMethod.POST)
    public Message findLogInfoMultiCondition(@RequestBody LogInfoMessage logInfoMessage){
        List<LogInfo> logInfoList;
        int count = 0;
        Map<String, Object> map = new HashMap<>();
        try {
            logInfoList = logInfoService.findLogInfoMultiCondition(logInfoMessage);
            count = logInfoService.findLogInfoMultiConditionCount(logInfoMessage);
            if (logInfoList != null){
                map.put("logInfoList", logInfoList);
                map.put("count", count);
                return new Message(Message.SUCCESS, "用户日志信息 >> 多条件查询 >> 成功", map);
            }
            return new Message(Message.FAILURE, "用户日志信息 >> 多条件查询 >> 失败", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "用户日志信息 >> 多条件查询 >> 异常", e.getMessage());
        }
    }

    /**
     * 查看用户日志详情
     * @return
     */
    @RequestMapping(value = "/getLogInfoByLogId", method = RequestMethod.POST)
    public Message getLogInfoByLogId(@RequestParam(value = "logInfoId") int logInfoId){
        LogInfo logInfo;

        try {
            logInfo = logInfoService.getLogInfoByLogId(logInfoId);
            if (logInfo != null){
                return new Message(Message.SUCCESS, "用户日志信息 >> 多条件查询 >> 成功", logInfo);
            }
            return new Message(Message.FAILURE, "用户日志信息 >> 多条件查询 >> 失败", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "用户日志信息 >> 多条件查询 >> 异常", e.getMessage());
        }
    }

}
