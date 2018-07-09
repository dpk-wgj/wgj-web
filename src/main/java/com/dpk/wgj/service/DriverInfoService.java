package com.dpk.wgj.service;

import com.dpk.wgj.bean.DriverInfo;
import com.dpk.wgj.bean.tableInfo.DriverInfoTableMessage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DriverInfoService {

    /**
     * 根据司机绑定的车辆Id进行司机信息查询
     * @param carId
     */
    public DriverInfo getDriverInfoByCarId(int carId) throws Exception;


    /**
     * 根据司机的名字进行司机全部信息查询
     * @param driverName
     */
    public DriverInfo getDriverInfoByDriverName(String driverName) throws Exception;

    /**
     * 全部司机信息查询
     * @param
     */
    public List<DriverInfo> getAllDriverInfo( ) throws Exception;


    /**
     * 根据司机的手机号进行司机全部信息查询
     * @param driverPhoneNumber
     */
    public DriverInfo getDriverInfoByDriverPhoneNumber(String driverPhoneNumber) throws Exception;

    /**
     * 根据司机的星级进行司机全部信息查询
     * @param driverLevelStar
     */
    public DriverInfo getDriveInfoByDriverLevelStar(int driverLevelStar) throws Exception;


    /**
     * 获取所有当前上岗司机位置
     * @return
     */
    public List<DriverInfo> getAllCarLocation() throws Exception;


    /**
     * Excel表导入数据库
     * @return
     */

    public int importExcel(MultipartFile file, String fileName)throws Exception;

    public List<DriverInfo> getDriverByMultiCondition(DriverInfoTableMessage carInfoTableMessage) throws Exception;

    public int getDriverByMultiConditionCount(DriverInfoTableMessage carInfoTableMessage) throws Exception;

}
