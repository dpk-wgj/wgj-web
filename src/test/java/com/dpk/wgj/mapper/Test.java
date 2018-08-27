//package com.dpk.wgj.mapper;
//
//import com.alibaba.fastjson.JSON;
//import com.dpk.wgj.WgjApplicationTests;
//import com.dpk.wgj.bean.DriverInfo;
//import com.dpk.wgj.bean.OrderInfo;
//import com.dpk.wgj.bean.Passenger;
//import com.dpk.wgj.bean.tableInfo.OrderInfoTableMessage;
//import com.dpk.wgj.utils.AliyunMessageUtil;
//import com.dpk.wgj.utils.SmsTest;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.List;
//
//public class Test extends WgjApplicationTests {
//
//    @Autowired
//    private SmsTest smsTest;
//
//    @org.junit.Test
//    public void sendMsg() throws Exception {
//        System.out.println("!!!" + smsTest.sendMsg());
//    }
//
//    @org.junit.Test
//    public void locationTest(){
//        String[] current = "119.90385,28.45646".split(",");
//        String[] target = "119.91284,28.44594".split(",");
//        double cx = Double.parseDouble(current[0]);
//        double tx = Double.parseDouble(target[0]);
//
//
//        double x = Math.abs(Double.parseDouble(current[0]) - Double.parseDouble(target[0]));
//        double y = Math.abs(Double.parseDouble(current[1]) - Double.parseDouble(target[1]));
//        if (x <= 0.016 && y <= 0.016){
//            System.out.println("success");
//        }else {
//            System.out.println("failure");
//        }
//    }
//}
