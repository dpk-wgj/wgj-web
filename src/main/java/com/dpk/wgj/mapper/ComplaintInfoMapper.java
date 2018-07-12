package com.dpk.wgj.mapper;

import com.dpk.wgj.bean.ComplaintInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ComplaintInfoMapper {

    public int addComplaintInfoByOrderId(ComplaintInfo complaintInfo) throws Exception;

    public int deleteComplaintInfoByCommentId(int complaintId) throws Exception;

    public ComplaintInfo getComplaintInfoByOrderId(int orderId) throws Exception;

    public List<ComplaintInfo> getComplaintInfoByPassengerId(int passengerId) throws Exception;

    public int updateComplaintInfoStatus(ComplaintInfo complaintInfo) throws Exception;

}
