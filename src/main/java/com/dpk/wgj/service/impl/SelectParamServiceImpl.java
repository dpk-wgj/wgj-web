package com.dpk.wgj.service.impl;

import com.dpk.wgj.bean.OrderInfo;
import com.dpk.wgj.bean.Param;
import com.dpk.wgj.mapper.SelectParamMapper;
import com.dpk.wgj.service.SelectParamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class SelectParamServiceImpl implements SelectParamService {
    @Autowired
    private SelectParamMapper selectParamMapper;

    private final Logger logger = LoggerFactory.getLogger(SelectParamServiceImpl.class);

    @Override
    public int[] getOrderByYear(Param yarr) throws Exception {
        int[] numByYear=new int[5];
        for (int i=0;i<yarr.getParamNum().length;i++) {
            int year=yarr.getParamNum()[i];
            numByYear[i]= selectParamMapper.getOrderByYear(year);
        }

        return numByYear;
    }
    @Override
    public int[] getOrderByMonth(Param monarr) throws Exception {
        int[] numByMonth=new int[12];
        for (int i=0;i<monarr.getParamNum().length;i++) {
            int month=monarr.getParamNum()[i];
            numByMonth[i]= selectParamMapper.getOrderByMonth(month);
        }
        return numByMonth;
    }

    @Override
    public List<OrderInfo> getOrderByDriverId(int driverId) throws Exception {

        List<OrderInfo> orderInfo;
        orderInfo= selectParamMapper.getOrderByDriverId(driverId);
        return orderInfo;
    }

    @Override
    public List<OrderInfo> getOrderBycarNumber(int carId) throws Exception {

        List<OrderInfo> orderInfo;
        orderInfo= selectParamMapper.getOrderBycarNumber(carId);
        return orderInfo;
    }

    @Override
    public List<OrderInfo> getAllOrderNum() throws Exception {
        List<OrderInfo> orderInfo;
        orderInfo= selectParamMapper.getAllOrderNum();
//        for(OrderInfo oiob:orderInfo){
//            Date sdate =oiob.getStartTime();
//            Date edate =oiob.getEndTime();
//            if(sdate!=null&&edate!=null){
//                oiob.setStartTime(format(sdate));
//                oiob.setEndTime(format(edate));
//            }else {
//
//            }
//
//        }
        return orderInfo;

    }/**

     * @Description:    格式化日期函数

     * @Author:      iauhsoaix

     * @CreateDate:     2018/7/13 14:57

     */
    public  Date format(Date date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//注意格式化的表达式
        String timetemp=format.format(date);
        return format.parse(timetemp);

    }

//    @Override
//    public List<OrderInfo> findOrderInfoByMultiCondition(MultiCondition multiCondition) throws Exception {
//        List<OrderInfo> orderInfo;
//        int carId=multiCondition.getCarid();
//        int driverId=multiCondition.getDriverid();
//        orderInfo= selectParamMapper.findCarInfoByMultiCondition(carId,driverId);
//        return orderInfo;
//
//    }

}
