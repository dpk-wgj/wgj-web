package com.dpk.wgj.mapper;

import com.dpk.wgj.WgjApplicationTests;
import com.dpk.wgj.bean.OrderInfo;
import com.dpk.wgj.bean.Passenger;
import com.dpk.wgj.bean.tableInfo.LocationMessage;
import com.dpk.wgj.bean.tableInfo.OrderInfoTableMessage;
import com.dpk.wgj.service.OrderInfoService;
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
//    private OrderInfoMapper orderInfoMapper;
    private OrderInfoService orderInfoService;


    @Test
    public void testGetLocationInfoByDate() throws Exception {

        OrderInfoTableMessage orderInfoTableMessage = new OrderInfoTableMessage();

        orderInfoTableMessage.setLimit(10);
        orderInfoTableMessage.setOffset(0);
        orderInfoTableMessage.setSort("order_id");
        orderInfoTableMessage.setOrder("desc");

        OrderInfo orderInfo = new OrderInfo();
        Passenger passenger = new Passenger();
        orderInfo.setOrderStatus(0);
        passenger.setPassengerId(8);
        orderInfoTableMessage.setOrderInfo(orderInfo);
        orderInfoTableMessage.setPassenger(passenger);


        List<OrderInfo> list = orderInfoService.findOrderInfoByMultiCondition(orderInfoTableMessage);
        System.out.println(list.toString());
    }

}
