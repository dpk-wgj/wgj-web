package com.dpk.wgj.service;

import com.dpk.wgj.bean.OrderInfo;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderInfoService {

    public List<OrderInfo> getLocationInfoByDate(Date startTime, Date endTime) throws Exception;

}
