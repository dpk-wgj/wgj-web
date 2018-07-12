package com.dpk.wgj.service;

import com.dpk.wgj.bean.ComplaintInfo;

public interface ComplaintInfoService {

    public int addComplaintInfoByOrderId(ComplaintInfo complaintInfo) throws Exception;

    public int deleteComplaintInfoByCommentId(int complaintId) throws Exception;

    public ComplaintInfo getComplaintInfoByOrderId(int orderId) throws Exception;


}
