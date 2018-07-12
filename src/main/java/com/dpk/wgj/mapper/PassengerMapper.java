package com.dpk.wgj.mapper;

import com.dpk.wgj.bean.Passenger;
import com.dpk.wgj.bean.UserGroup;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PassengerMapper {

    public Passenger getPassengerByWxId(String passengerWxId) throws Exception;

    public int addPassenger(Passenger passenger) throws Exception;

    public int updatePassengerStatus(Passenger passenger) throws Exception;

    public int updatePassengerPhoneNumber(Passenger passenger) throws Exception;

    public Passenger getPassengerByPassengerId(int passengerId) throws Exception;

}
