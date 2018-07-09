package com.dpk.wgj.mapper;

import com.dpk.wgj.WgjApplicationTests;
import com.dpk.wgj.bean.OrderInfo;
import com.dpk.wgj.bean.tableInfo.LocationMessage;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by zhoulin on 2018/7/9.
 * 说明:
 */
public class OrderMapperTest extends WgjApplicationTests {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Test
    public void testGetLocationInfoByDate() throws Exception {

//        LocationMessage message = new LocationMessage();
//
////        message.setCarNumber("A12345");
//
//        String startDate = "2018-07-09 08:56:04";
//        String endDate = "2018-07-09 9:57:04";
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        Date startTime = sdf.parse(startDate);
//        Date endTime = sdf.parse(endDate);
//
//        message.setStartTime(startTime);
//        message.setEndTime(endTime);
//
//        List<OrderInfo> list = orderInfoMapper.getLocationInfoByDate(message);
//
//        System.out.println(list.toString());

    }

}
