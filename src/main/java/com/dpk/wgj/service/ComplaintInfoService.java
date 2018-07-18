package com.dpk.wgj.service;

import com.dpk.wgj.bean.ComplaintInfo;
import com.dpk.wgj.bean.tableInfo.ComplaintMessage;

import java.util.List;

public interface ComplaintInfoService {

    public int addComplaintInfoByOrderId(ComplaintInfo complaintInfo) throws Exception;

    public int deleteComplaintInfoByCommentId(int complaintId) throws Exception;

    public ComplaintInfo getComplaintInfoByOrderId(int orderId) throws Exception;

    public List<ComplaintInfo> getComplaintInfoByPassengerId(int passengerId) throws Exception;

    public int updateComplaintInfoStatus(ComplaintInfo complaintInfo) throws Exception;

    /**
     * 投诉多条件查询功能
     */
    public List<ComplaintInfo> findComplaintInfoByMultiCondition(ComplaintMessage tableMessage) throws Exception;

    /**
     * 投诉多条件查询功能 -- 投诉总数
     */
    public int findComplaintInfoByMultiConditionCount(ComplaintMessage tableMessage) throws Exception;
}
