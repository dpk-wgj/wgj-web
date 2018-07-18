package com.dpk.wgj.bean.tableInfo;

import com.dpk.wgj.bean.ComplaintInfo;
import com.dpk.wgj.bean.OrderInfo;
import com.dpk.wgj.bean.Passenger;

/**
 * Created by zhoulin on 2018/7/12.
 * 说明:
 */
public class ComplaintMessage extends TableMessage {

    private ComplaintInfo complaintInfo = new ComplaintInfo();

//    private OrderInfo orderInfo = new  OrderInfo();
//
//    private Passenger passenger = new  Passenger();
//
//    public OrderInfo getOrderInfo() { return orderInfo; }
//
//    public void setOrderInfo(OrderInfo orderInfo) { this.orderInfo = orderInfo; }
//
//    public Passenger getPassenger() { return passenger; }
//
//    public void setPassenger(Passenger passenger) { this.passenger = passenger; }

    public ComplaintInfo getComplaintInfo() {
        return complaintInfo;
    }

    public void setComplaintInfo(ComplaintInfo complaintInfo) {
        this.complaintInfo = complaintInfo;
    }
}
