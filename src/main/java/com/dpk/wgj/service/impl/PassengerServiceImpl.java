package com.dpk.wgj.service.impl;

import com.dpk.wgj.bean.Passenger;
import com.dpk.wgj.mapper.PassengerMapper;
import com.dpk.wgj.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhoulin on 2018/7/9.
 * 说明:
 */
public class PassengerServiceImpl implements PassengerService {

    @Autowired
    private PassengerMapper passengerMapper;

    @Override
    public Passenger getPassengerByWxId(String passengerWxId) {

        Passenger passenger;

        try {
            passenger = passengerMapper.getPassengerByWxId(passengerWxId);
            return passenger;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int addPassenger(Passenger passenger) {

        int addStatus = 0;

        try {
            addStatus = passengerMapper.addPassenger(passenger);
            return addStatus;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
