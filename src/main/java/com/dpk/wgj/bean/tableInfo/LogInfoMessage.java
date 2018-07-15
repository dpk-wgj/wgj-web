package com.dpk.wgj.bean.tableInfo;

import com.dpk.wgj.bean.LogInfo;

public class LogInfoMessage extends TableMessage {

    private LogInfo logInfo = new LogInfo();

    private String startTime;

    private String endTime;

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public LogInfo getLogInfo() {
        return logInfo;
    }

    public void setLogInfo(LogInfo logInfo) {
        this.logInfo = logInfo;
    }
}
