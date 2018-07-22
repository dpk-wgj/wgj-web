package com.dpk.wgj.service.impl;

import com.dpk.wgj.bean.ComplaintInfo;
import com.dpk.wgj.bean.tableInfo.ComplaintMessage;
import com.dpk.wgj.mapper.ComplaintInfoMapper;
import com.dpk.wgj.service.ComplaintInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhoulin on 2018/7/12.
 * 订单投诉功能 Service
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

    @Override
    public List<ComplaintInfo> getComplaintInfoByPassengerId(int passengerId) {
        List<ComplaintInfo> complaintInfos;

        try {
            complaintInfos = complaintInfoMapper.getComplaintInfoByPassengerId(passengerId);
            return complaintInfos;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int updateComplaintInfoStatus(ComplaintInfo complaintInfo) {
        int upStatus = 0;

        try {
            upStatus = complaintInfoMapper.updateComplaintInfoStatus(complaintInfo);
            return upStatus;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<ComplaintInfo> findComplaintInfoByMultiCondition(ComplaintMessage complaintMessage) {
        List<ComplaintInfo> complaintInfoList;
        try {
            complaintInfoList = complaintInfoMapper.findComplaintInfoByMultiCondition(complaintMessage);
            return complaintInfoList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int findComplaintInfoByMultiConditionCount(ComplaintMessage complaintMessage) {
        int count = 0;
        try {
            count = complaintInfoMapper.findComplaintInfoByMultiConditionCount(complaintMessage);
            return count;
        }catch (Exception e){
            e.printStackTrace();

        }

        return count;
    }



}
