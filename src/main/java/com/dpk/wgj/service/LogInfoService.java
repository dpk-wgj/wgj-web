package com.dpk.wgj.service;

import com.dpk.wgj.bean.LogInfo;
import com.dpk.wgj.bean.tableInfo.LogInfoMessage;

import java.util.List;

public interface LogInfoService {

    public int addLogInfo(LogInfo logInfo) throws Exception;

    public List<LogInfo> findLogInfoMultiCondition(LogInfoMessage logInfoMessage) throws Exception;

    public int findLogInfoMultiConditionCount(LogInfoMessage logInfoMessage) throws Exception;

    public LogInfo getLogInfoByLogId(int logId) throws Exception;


}
