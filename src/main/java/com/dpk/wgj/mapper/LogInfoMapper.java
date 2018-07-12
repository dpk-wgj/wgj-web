package com.dpk.wgj.mapper;

import com.dpk.wgj.bean.CarInfo;
import com.dpk.wgj.bean.LogInfo;
import com.dpk.wgj.bean.tableInfo.CarInfoTableMessage;
import com.dpk.wgj.bean.tableInfo.LogInfoMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LogInfoMapper {

    public int addLogInfo(LogInfo logInfo) throws Exception;

    public List<LogInfo> findLogInfoMutiCondition(LogInfoMessage logInfoMessage) throws Exception;

    public int findLogInfoMutiConditionCount(LogInfoMessage logInfoMessage) throws Exception;

    public LogInfo getLogInfoByLogId(int logId) throws Exception;

}
