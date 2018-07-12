package com.dpk.wgj.service.impl;

import com.dpk.wgj.bean.ComplaintInfo;
import com.dpk.wgj.mapper.ComplaintInfoMapper;
import com.dpk.wgj.service.ComplaintInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhoulin on 2018/7/12.
 * 投诉功能 Service
 */
@Service
public class ComplaintInfoServiceImpl implements ComplaintInfoService {

    @Autowired
    private ComplaintInfoMapper complaintInfoMapper;

    @Override
    public int addComplaintInfoByOrderId(ComplaintInfo complaintInfo) {
        int addStatus = 0;

        try {
            addStatus = complaintInfoMapper.addComplaintInfoByOrderId(complaintInfo);
            return addStatus;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int deleteComplaintInfoByCommentId(int complaintId) {
        int delStatus = 0;

        try {
            delStatus = complaintInfoMapper.deleteComplaintInfoByCommentId(complaintId);
            return delStatus;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public ComplaintInfo getComplaintInfoByOrderId(int orderId) {
        ComplaintInfo complaintInfo;

        try {
            complaintInfo = complaintInfoMapper.getComplaintInfoByOrderId(orderId);
            return complaintInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
