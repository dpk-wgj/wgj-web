//package com.dpk.wgj.mapper;
//
//import com.dpk.wgj.WgjApplicationTests;
//import com.dpk.wgj.bean.DriverInfo;
//import com.dpk.wgj.bean.OrderInfo;
//import com.dpk.wgj.bean.Passenger;
//import com.dpk.wgj.bean.tableInfo.LocationMessage;
//import com.dpk.wgj.bean.tableInfo.OrderInfoTableMessage;
//import com.dpk.wgj.service.DriverInfoService;
//import com.dpk.wgj.service.OrderInfoService;
//import com.dpk.wgj.utils.BestPathUtil;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
///**
// * Created by zhoulin on 2018/7/9.
// * 说明:
// */
//public class OrderMapperTest extends WgjApplicationTests {
//
//    @Autowired
////    private OrderInfoMapper orderInfoMapper;
//    private OrderInfoService orderInfoService;
//
//    @Autowired
//    private BestPathUtil bestPathUtil;
//
//    @Autowired
//    private DriverInfoService driverInfoService;
//
//    @Test
//    public void testGetDriverId() throws Exception{
//        driverInfoService.getAllDriverInfo();
//        System.out.println("driverId : "
//                + driverInfoService.getAllDriverInfo());
//
//    }
//
//    @Test
//    public void testGetLocationInfoByDate() throws Exception {
//
//        OrderInfoTableMessage orderInfoTableMessage = new OrderInfoTableMessage();
//
//        orderInfoTableMessage.setLimit(10);
//        orderInfoTableMessage.setOffset(0);
//        orderInfoTableMessage.setSort("order_id");
//        orderInfoTableMessage.setOrder("desc");
//
//        OrderInfo orderInfo = new OrderInfo();
//        Passenger passenger = new Passenger();
//        orderInfo.setOrderStatus(0);
//        passenger.setPassengerId(8);
//        orderInfoTableMessage.setOrderInfo(orderInfo);
//        orderInfoTableMessage.setPassenger(passenger);
//
//
//        List<OrderInfo> list = orderInfoService.findOrderInfoByMultiCondition(orderInfoTableMessage);
//        System.out.println(list.toString());
//    }
//
//    @Test
//    public void testJavaDo() throws Exception{
//        Map<Integer, String> driverId2driverName = new HashMap<>();
//        List<DriverInfo> driverInfos = driverInfoService.getAllDriverInfo();
//        Map<Integer, String> map  = driverInfos.stream().collect(Collectors.toMap(DriverInfo::getDriverId, DriverInfo::getDriverName));
//        driverId2driverName.putAll(map);
//        System.out.println(map);
//
////         System.out.println(map1);
//    }
//
//}
