package com.dpk.wgj.bean.tableInfo;

import com.dpk.wgj.bean.LogInfo;

public class LogInfoMessage extends TableMessage {

    private LogInfo logInfo = new LogInfo();

    public LogInfo getLogInfo() {
        return logInfo;
    }

    public void setLogInfo(LogInfo logInfo) {
        this.logInfo = logInfo;
    }
}
