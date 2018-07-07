package com.dpk.wgj.service;

import com.dpk.wgj.bean.CarInfo;

public interface CarInfoService {

    public CarInfo getCarInfoByCarNumber(String carNumber) throws Exception;

}
