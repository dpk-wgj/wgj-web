package com.dpk.wgj.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhoulin on 2018/7/7.
 * 行为日志信息
 */
public class LogInfo implements Serializable {

    private int logId;

    private String action;

    private int roleId;

    private Date logTime;

    private int orderId;

    public LogInfo() {
    }

    public LogInfo(String action, int roleId, Date logTime, int orderId) {
        this.action = action;
        this.roleId = roleId;
        this.logTime = logTime;
        this.orderId = orderId;
    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "LogInfo{" +
                "logId=" + logId +
                ", action='" + action + '\'' +
                ", roleId=" + roleId +
                ", logTime=" + logTime +
                ", orderId=" + orderId +
                '}';
    }
}
