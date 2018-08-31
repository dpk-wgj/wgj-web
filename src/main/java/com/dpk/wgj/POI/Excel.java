package com.dpk.wgj.POI;
import com.dpk.wgj.bean.DTO.CarInfoDTO;
import com.dpk.wgj.bean.DriverInfo;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class Excel {

    public void pushExcel(List<DriverInfo> driverInfo) throws IOException {
        //创建工作簿
        HSSFWorkbook workBook = new HSSFWorkbook();
        //创建工作表  工作表的名字叫helloWorld
        HSSFSheet sheet = workBook.createSheet("司机基本信息表");
        sheet.setColumnWidth(0,100*15);
        sheet.setColumnWidth(1,200*15);
        sheet.setColumnWidth(2,500*15);
        sheet.setColumnWidth(3,500*15);
//        sheet.setColumnWidth(4,500*15);
//        sheet.setColumnWidth(5,500*15);


        HSSFFont hssfFont2 = workBook.createFont();
        hssfFont2.setFontHeightInPoints((short)10);
        hssfFont2.setFontName("宋体");

        //设置单元格居中
        HSSFCellStyle setBorder = workBook.createCellStyle();
        setBorder.setFont(hssfFont2);
        setBorder.setWrapText(true);
        setBorder.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
        setBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平
        setBorder.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        setBorder.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        setBorder.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        setBorder.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        //setBorder.setBottomBorderColor(HSSFColor.BLUE.index2);//设置底边框颜色;


        HSSFCellStyle setBorder3 = workBook.createCellStyle();
        setBorder3.setFont(hssfFont2);
        setBorder3.setWrapText(true);
        setBorder3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
        setBorder3.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        setBorder3.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        setBorder3.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        setBorder3.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        setBorder3.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平

        //创建font实例
        HSSFFont hssfFont = workBook.createFont();
        hssfFont.setFontHeightInPoints((short)16);
        hssfFont.setFontName("黑体");
        //   hssfFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

        HSSFCellStyle setBorder4 = workBook.createCellStyle();
        setBorder4.setFont(hssfFont);
        setBorder4.setWrapText(true);
        setBorder4.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
        setBorder4.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平
        setBorder4.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        setBorder4.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        setBorder4.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        setBorder4.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        // Region region = new Region(0,0,0,10);
        //合并单元格
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,3));



        //创建第一行
        HSSFRow row = sheet.createRow(0);
        row.setHeight((short)(35*15));
        for(int i=0;i<=3;i++){
            row.createCell(i, CellType.STRING).setCellStyle(setBorder);}

        HSSFRow row1 = sheet.createRow(1);
        row1.setHeight((short)(35*15));
        for(int i=0;i<=3;i++){
            row1.createCell(i, CellType.STRING).setCellStyle(setBorder);
        }

        HSSFCell cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("司机基本信息表");
        cell.setCellStyle(setBorder4);
        HSSFCell cell1 = row1.createCell(0, CellType.STRING);
        cell1.setCellValue("序号");
        cell1.setCellStyle(setBorder3);

        HSSFCell cell2 = row1.createCell(1, CellType.STRING);
        cell2.setCellValue("姓名");
        cell2.setCellStyle(setBorder3);

//        HSSFCell cell3 = row1.createCell(2, CellType.STRING);
//        cell3.setCellValue("微信号");
//        cell3.setCellStyle(setBorder3);

        HSSFCell cell4 = row1.createCell(2, CellType.STRING);
        cell4.setCellValue("手机号");
        cell4.setCellStyle(setBorder3);

        HSSFCell cell5 = row1.createCell(3, CellType.STRING);
        cell5.setCellValue("身份证号");
        cell5.setCellStyle(setBorder3);

//        HSSFCell cell6 = row1.createCell(5, CellType.STRING);
//        cell6.setCellValue("驾驶证号");
//        cell6.setCellStyle(setBorder3);

        for (int i =0;i<driverInfo.size();i++){
            DriverInfo b = driverInfo.get(i);
            HSSFRow rowz = sheet.createRow(i+2);
            rowz.setHeight((short)(35*15));
            for(int j=0;j<=3;j++){
                rowz.createCell(j, CellType.STRING).setCellStyle(setBorder);
            }

            HSSFCell cell1_1 = rowz.createCell(0, CellType.STRING);
            cell1_1.setCellValue(b.getDriverId());
            cell1_1.setCellStyle(setBorder3);


            HSSFCell cell2_2 = rowz.createCell(1, CellType.STRING);
            cell2_2.setCellValue(b.getDriverName());
            cell2_2.setCellStyle(setBorder3);

//            HSSFCell cell3_3 = rowz.createCell(2, CellType.STRING);
//            cell3_3.setCellValue(b.getDriverWxId());
//            cell3_3.setCellStyle(setBorder3);

            HSSFCell cell4_4 = rowz.createCell(2, CellType.STRING);
            cell4_4.setCellValue(b.getDriverPhoneNumber());
            cell4_4.setCellStyle(setBorder3);

            HSSFCell cell5_5 = rowz.createCell(3, CellType.STRING);
            cell5_5.setCellValue(b.getDriverIdentity());
            cell5_5.setCellStyle(setBorder3);

//            HSSFCell cell6_6 = rowz.createCell(5, CellType.STRING);
//            cell6_6.setCellValue(b.getDriverLicence());
//            cell6_6.setCellStyle(setBorder3);

        }
        String strPath = "d:\\微公交系统\\司机个人信息报表\\";
        File file = new File(strPath);
        if(!file.exists()){
            file.mkdirs();
        }
        workBook.write(new File(strPath+"司机个人信息报表.xls"));

        workBook.close();//最后记得关闭工作簿
    }

}


