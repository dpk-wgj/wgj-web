package com.dpk.wgj.service.impl;

import com.dpk.wgj.bean.LogInfo;
import com.dpk.wgj.bean.tableInfo.LogInfoMessage;
import com.dpk.wgj.mapper.LogInfoMapper;
import com.dpk.wgj.service.LogInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhoulin on 2018/7/13.
 * 用户行为日志 Service
 */
@Service
public class LogInfoServiceImpl implements LogInfoService {

    @Autowired
    private LogInfoMapper logInfoMapper;

    @Override
    public int addLogInfo(LogInfo logInfo) {
        int addStatus = 0;

        try {
            addStatus = logInfoMapper.addLogInfo(logInfo);
            return addStatus;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addStatus;
    }

    @Override
    public List<LogInfo> findLogInfoMultiCondition(LogInfoMessage logInfoMessage) {
        List<LogInfo> logInfoList;

        try {
            logInfoList = logInfoMapper.findLogInfoMultiCondition(logInfoMessage);
            return logInfoList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int findLogInfoMultiConditionCount(LogInfoMessage logInfoMessage) {
        int count = 0;

        try {
            count = logInfoMapper.findLogInfoMultiConditionCount(logInfoMessage);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public LogInfo getLogInfoByLogId(int logId) {
        LogInfo logInfo;

        try {
            logInfo = logInfoMapper.getLogInfoByLogId(logId);
            return logInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
