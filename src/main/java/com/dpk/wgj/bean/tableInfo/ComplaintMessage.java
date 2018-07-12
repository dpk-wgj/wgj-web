package com.dpk.wgj.bean.tableInfo;

import com.dpk.wgj.bean.ComplaintInfo;

/**
 * Created by zhoulin on 2018/7/12.
 * 说明:
 */
public class ComplaintMessage extends TableMessage {

    private ComplaintInfo complaintInfo = new ComplaintInfo();

    public ComplaintInfo getComplaintInfo() {
        return complaintInfo;
    }

    public void setComplaintInfo(ComplaintInfo complaintInfo) {
        this.complaintInfo = complaintInfo;
    }
}
