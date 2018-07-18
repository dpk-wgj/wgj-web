package com.dpk.wgj.mapper;

import com.alibaba.fastjson.JSON;
import com.dpk.wgj.WgjApplicationTests;
import com.dpk.wgj.bean.DriverInfo;
import com.dpk.wgj.bean.OrderInfo;
import com.dpk.wgj.bean.Passenger;
import com.dpk.wgj.bean.tableInfo.OrderInfoTableMessage;
import com.dpk.wgj.utils.AliyunMessageUtil;
import com.dpk.wgj.utils.SmsTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class Test extends WgjApplicationTests {

    @Autowired
    private SmsTest smsTest;

    @org.junit.Test
    public void sendMsg() throws Exception {
        System.out.println("!!!" + smsTest.sendMsg());
    }
}
