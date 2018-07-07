package com.dpk.wgj.mapper;

import com.dpk.wgj.bean.CarInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CarInfoMapper {

    public CarInfo getCarInfoByCarNumber(String carNumber) throws Exception;

}
