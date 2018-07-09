package com.dpk.wgj.service;

import com.dpk.wgj.bean.OrderInfo;
import com.dpk.wgj.bean.tableInfo.LocationMessage;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderInfoService {

    public List<OrderInfo> getLocationInfoByDate(LocationMessage locationMessage) throws Exception;

}
