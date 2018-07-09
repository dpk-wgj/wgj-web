package com.dpk.wgj.mapper;

import com.dpk.wgj.bean.OrderInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface OrderInfoMapper {

    public List<OrderInfo> getLocationInfoByDate(Date startTime, Date endTime) throws Exception;

}
