package com.dpk.wgj.service.impl;

import com.dpk.wgj.bean.DriverInfo;
import com.dpk.wgj.bean.tableInfo.DriverInfoTableMessage;
import com.dpk.wgj.mapper.DriverInfoMapper;
import com.dpk.wgj.service.DriverInfoService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * Created by zhoulin on 2018/7/8.
 * 说明:
 */
@Service
public class DriverInfoServiceImpl implements DriverInfoService {

    @Autowired
    private DriverInfoMapper driverInfoMapper;

    private final Logger logger = LoggerFactory.getLogger(DriverInfoServiceImpl.class);
    @Override
    public DriverInfo getDriverInfoByCarId(int carId) {
        DriverInfo driverInfo;
        try {
            driverInfo = driverInfoMapper.getDriverInfoByCarId(carId);
            return driverInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DriverInfo> getAllCarLocation() {

        List<DriverInfo> driverInfoList;

        try {
            driverInfoList = driverInfoMapper.getAllCarLocation();
            return driverInfoList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public DriverInfo getDriverInfoByWxId(String driverWxId) {

        DriverInfo driverInfo;

        try {
            driverInfo = driverInfoMapper.getDriverInfoByWxId(driverWxId);
            return driverInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int addDriverInfo(DriverInfo driverInfo) {

        int addStatus = 0;

        try {
            addStatus = driverInfoMapper.addDriverInfo(driverInfo);
            return addStatus;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  0;
    }

    @Override
    public int updateDriverStatus(DriverInfo driverInfo) {
        int upStatus = 0;

        try {
            upStatus = driverInfoMapper.updateDriverStatus(driverInfo);
            return upStatus;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  0;
    }



    /**
     * Created by hlx on 2018/7/8.
     * 说明:
     */
    @Override
    @Transactional
    public DriverInfo getDriverInfoByDriverName(String driverName){
        DriverInfo driverInfo;
        try {
            driverInfo = driverInfoMapper.getDriverInfoByDriverName(driverName);
            return driverInfo;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    @Transactional
    public DriverInfo getDriverInfoByDriverPhoneNumber(String driverPhoneNumber){
        DriverInfo driverInfo;
        try {
            driverInfo = driverInfoMapper.getDriverInfoByDriverPhoneNumber(driverPhoneNumber);
            return driverInfo;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }
    @Override
    @Transactional
    public DriverInfo getDriveInfoByDriverLevelStar(int driverLevelStar){
        DriverInfo driverInfo;
        try {
            driverInfo = driverInfoMapper.getDriveInfoByDriverLevelStar(driverLevelStar);
            return driverInfo;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<DriverInfo> getDriverByMultiCondition(DriverInfoTableMessage driverInfoTableMessage) {

        List<DriverInfo> driverInfos;

        try {
            driverInfos = driverInfoMapper.getDriverByMultiCondition(driverInfoTableMessage);
            return driverInfos;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getDriverByMultiConditionCount(DriverInfoTableMessage driverInfoTableMessage) {

        int count = 0;

        try {
            count = driverInfoMapper.getDriverByMultiConditionCount(driverInfoTableMessage);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
    @Override
    public int updateDriverInfoByDriverId(DriverInfo driverInfo) {
        int upStatus = 0;

        try {
            upStatus = driverInfoMapper.updateDriverInfoByDriverId(driverInfo);
            return upStatus;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return upStatus;
    }
    @Override
    public int deleteDriverInfoByDriverId(int driverId){
        int deStatus = 0;
        try {
            deStatus = driverInfoMapper.deleteDriverInfoByDriverId(driverId);
            return deStatus;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return deStatus;
    }

    @Override
    @Transactional
    public int importExcel(MultipartFile file, String fileName){
        //记录成功添加的记录数
        int res = 0;
        logger.info("000000");
        try {
            InputStream fileInputStream = file.getInputStream();
//            //判断是否是03版本的Excel(还是07的)
//            boolean is03Excel = fileName.matches("^.+\\.(?i)(xls)$");
//            //1、读取工作簿
//            Workbook workbook = is03Excel ? new HSSFWorkbook(fileInputStream)
//                    : new XSSFWorkbook(fileInputStream);
            Workbook workbook =new HSSFWorkbook(fileInputStream);
            //2、读取工作表
            Sheet sheet=workbook.getSheetAt(0);
            //3、读取行
            System.out.println(sheet.getPhysicalNumberOfRows());
            if(sheet.getPhysicalNumberOfRows()>1){
                DriverInfo driverInfo= null;
                for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                    //4、读取单元格
                    Row row=sheet.getRow(i);
                    driverInfo = new DriverInfo();
                    if(row==null || row.getCell(0) == null){
                        continue;
                    }

                    row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                    driverInfo.setDriverName(row.getCell(0).getStringCellValue());

                    row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                    driverInfo.setDriverWxId(row.getCell(1).getStringCellValue());

                    row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                    driverInfo.setDriverPhoneNumber(row.getCell(1).getStringCellValue());

                    row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                    driverInfo.setDriverIdentity(row.getCell(1).getStringCellValue());

                    row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                    driverInfo.setDriverLicence(row.getCell(1).getStringCellValue());
                    //风险源
                    try {
                        //先查询是否存在，然后再添加
                        DriverInfo temp = driverInfoMapper.getDriveInfoByDriverIdentity(driverInfo.getDriverIdentity());
                        if(temp == null){
                            driverInfoMapper.insertDriverInfo(driverInfo);
                            res++;
                        }
                    } catch (Exception e) {
                        logger.error(e.getMessage());

                    }
                }
            }
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public DriverInfo getDriverInfoByDriverId(int driverId) {
        DriverInfo driverInfo;
        try {
            driverInfo = driverInfoMapper.getDriverInfoByDriverId(driverId);
            return driverInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public int updateApiDriverInfoByDriverId(DriverInfo driverInfo) {
        int upApiStatus = 0;

        try {
            upApiStatus = driverInfoMapper.updateApiDriverInfoByDriverId(driverInfo);
            return upApiStatus;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return upApiStatus;
    }

    /**
     * Created by hlx on 2018/7/9.
     * 说明:用于查询所有司机信息
     */
    @Override
    public List<DriverInfo> getAllDriverInfo() {
        List<DriverInfo> driverInfo;
        try {
            driverInfo = driverInfoMapper.getAllDriverInfo();
            return driverInfo;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }


}
