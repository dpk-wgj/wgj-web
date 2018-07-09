package com.dpk.wgj.mapper;

import com.dpk.wgj.bean.OrderInfo;
import com.dpk.wgj.bean.tableInfo.LocationMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface OrderInfoMapper {

    public List<OrderInfo> getLocationInfoByDate(LocationMessage locationMessage) throws Exception;

}
