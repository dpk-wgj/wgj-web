package com.dpk.wgj.service.impl;

import com.dpk.wgj.bean.Passenger;
import com.dpk.wgj.mapper.PassengerMapper;
import com.dpk.wgj.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhoulin on 2018/7/9.
 * 乘客
 */
@Service
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
        return addStatus;
    }

    @Override
    public int updatePassengerStatus(Passenger passenger) {
        int upStatus = 0;
        try {
            upStatus = passengerMapper.updatePassengerStatus(passenger);
            return upStatus;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return upStatus;
    }

    @Override
    public int updatePassengerPhoneNumber(Passenger passenger) {
        int upStatus = 0;
        try {
            upStatus = passengerMapper.updatePassengerPhoneNumber(passenger);
            return upStatus;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return upStatus;
    }

    @Override
    public Passenger getPassengerByPassengerId(int passengerId) {
        Passenger passenger;
        try {
            passenger = passengerMapper.getPassengerByPassengerId(passengerId);
            return passenger;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
