package com.dpk.wgj.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhoulin on 2018/7/7.
 * 订单-投诉信息
 */
public class ComplaintInfo implements Serializable {

    private int complaintId;

    private String complaintContent;

    private int complaintStatus;

    private int orderId;

    private int passengerId;

    private String complaintFeedback;

    private Date complaintCreateTime;

    private Date complaintFeedbackTime;

    public String getComplaintFeedback() {
        return complaintFeedback;
    }

    public void setComplaintFeedback(String complaintFeedback) {
        this.complaintFeedback = complaintFeedback;
    }

    public Date getComplaintCreateTime() {
        return complaintCreateTime;
    }

    public void setComplaintCreateTime(Date complaintCreateTime) {
        this.complaintCreateTime = complaintCreateTime;
    }

    public Date getComplaintFeedbackTime() {
        return complaintFeedbackTime;
    }

    public void setComplaintFeedbackTime(Date complaintFeedbackTime) {
        this.complaintFeedbackTime = complaintFeedbackTime;
    }

    public int getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
    }

    public int getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(int complaintId) {
        this.complaintId = complaintId;
    }

    public String getComplaintContent() {
        return complaintContent;
    }

    public void setComplaintContent(String complaintContent) {
        this.complaintContent = complaintContent;
    }

    public int getComplaintStatus() {
        return complaintStatus;
    }

    public void setComplaintStatus(int complaintStatus) {
        this.complaintStatus = complaintStatus;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "ComplaintInfo{" +
                "complaintId=" + complaintId +
                ", complaintContent='" + complaintContent + '\'' +
                ", complaintStatus=" + complaintStatus +
                ", orderId=" + orderId +
                ", passengerId=" + passengerId +
                ", complaintFeedback='" + complaintFeedback + '\'' +
                ", complaintCreateTime=" + complaintCreateTime +
                ", complaintFeedbackTime=" + complaintFeedbackTime +
                '}';
    }
}
