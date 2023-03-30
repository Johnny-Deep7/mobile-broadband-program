package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.MbpType;
import com.tencent.wxcloudrun.pto.CommercialBuildingPTO;
import com.tencent.wxcloudrun.pto.HotelPTO;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.tencent.wxcloudrun.pto.IndustrialParkPTO;
import com.tencent.wxcloudrun.service.MbpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MbpServiceImpl implements MbpService {

    @Resource
    private MbpHotelServiceImpl mbpHotelService;

    @Resource
    private MbpBuildingServiceImpl mbpBuildingService;

    @Resource
    private IndustrialParkServiceImpl industrialParkService;

    @Resource
    private IndustrialParkDetailServiceImpl industrialParkDetailService;

    @Resource
    private MbpBuildingDetailServiceImpl mbpBuildingDetailService;

    @Resource
    private MbpShopServiceImpl mbpShopService;

    @Resource
    private MbpShopDetailServiceImpl mbpShopDetailService;
    @Override
    public void parsingTable(MultipartFile multipartFile) {
        File file = null;
        if (multipartFile!=null){
            try {
                file = multipartFileToFile(multipartFile);
                if(!file.exists() || file.length() == 0) {
                    throw new RuntimeException("文件为空！");
                }
                parseExcel(file);

            } catch (Exception e) {
                throw new RuntimeException("文件解析失败！");
            }
        }


    }

    /**
     * MultipartFile 转 File
     *
     * @param multipartFile
     * @throws Exception
     */
    public static File multipartFileToFile(MultipartFile multipartFile) throws Exception {

        File file = null;
        if (!(multipartFile.equals("") || multipartFile.getSize() <= 0)) {
            InputStream ins = null;
            ins = multipartFile.getInputStream();
            file = new File(multipartFile.getOriginalFilename());
            inputStreamToFile(ins, file);
            ins.close();
        }
        return file;
    }

    //获取流文件
    private static void inputStreamToFile(InputStream ins, File file) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (ins != null) {
                try {
                    ins.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

        /**
     * 解析导入的文件内容
     * @param file
     * @throws Exception
     */
    public void parseExcel(File file)throws Exception{

        //文件名
        String filename = file.getName();

        //创建一个文件对象
        Workbook workbook = null;
        InputStream inputStream = new FileInputStream(file);
        //Office 2007版本后excel.xls文件
        if (filename.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } //Office 2007版本前excel.xls文件
        else if (filename.endsWith("xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else {
            log.error("不是Excel文件");
            throw new RuntimeException("不是Excel文件");
        }

        //读取Sheet
        for (MbpType mbpType:MbpType.values()) {
            Sheet sheet = workbook.getSheet(mbpType.getCode());
            if (sheet != null){
                switch (mbpType.getCode()){
                    case "酒店宾馆":
                        getHotel(sheet);
                        break;
                    case "商务楼宇":
                        getBuilding(sheet);
                        break;
                    case "产业园区":
                        getIndustrialPark(sheet);
                        break;
                    case "商务楼宇二级明细":
                        getBuildingDel(sheet);
                        break;
                }
            }
        }

        return ;
    }


    /**
     * 获取酒店宾馆的数据
     * @param sheet
     */
    public void getHotel(Sheet sheet){

        //获取sheet下有多少行
        int rowNum = sheet.getPhysicalNumberOfRows();
        List<HotelPTO> hotelPTOS = new ArrayList<>();
        //遍历所有的Row
        for (int i = 1; i < rowNum; i++) {

            //获取当前sheet的第i行
            Row row = sheet.getRow(i);
            short cellNum = row.getLastCellNum();
            for (int j = 0; j < cellNum; j++) {
                row.getCell(j).setCellType(CellType.STRING);
            }
            hotelPTOS.add(HotelPTO.builder().substation(row.getCell(1).getStringCellValue())
                    .customerManager(row.getCell(2).getStringCellValue())
                    .contactWay(row.getCell(3).getStringCellValue())
                    .hotelName(row.getCell(4).getStringCellValue())
                    .isCovered(row.getCell(5).getStringCellValue())
                    .roomNumber(row.getCell(6).getStringCellValue())
                    .groupNumber(row.getCell(7).getStringCellValue())
                    .address(row.getCell(8).getStringCellValue())
                    .hotelType(row.getCell(9).getStringCellValue())
                    .operator(row.getCell(10).getStringCellValue())
                    .endTime1(row.getCell(11).getStringCellValue())
                    .internetCharge(row.getCell(12).getStringCellValue())
                    .endTime2(row.getCell(13).getStringCellValue())
                    .responsiblePerson(row.getCell(14).getStringCellValue())
                    .position(row.getCell(15).getStringCellValue())
                    .phoneNumber(row.getCell(16).getStringCellValue())
                    .visitDate(row.getCell(17).getStringCellValue())
                    .visitInformation(row.getCell(18).getStringCellValue())
                    .difficultPoint(row.getCell(19).getStringCellValue()).build());
        }
        mbpHotelService.asyncSaveHotel(hotelPTOS);

    }

    /**
     * 获取商务楼宇的数据
     * @param sheet
     */
    public void getBuilding(Sheet sheet){

        //获取sheet下有多少行
        int rowNum = sheet.getPhysicalNumberOfRows();
        List<CommercialBuildingPTO> commercialBuildingPTOS = new ArrayList<>();
        //遍历所有的Row
        for (int i = 1; i < rowNum; i++) {

            //获取当前sheet的第i行
            Row row = sheet.getRow(i);
            short cellNum = row.getLastCellNum();
            for (int j = 0; j < cellNum; j++) {
                row.getCell(j).setCellType(CellType.STRING);
            }
            commercialBuildingPTOS.add(CommercialBuildingPTO.builder().substation(row.getCell(1).getStringCellValue())
                    .customerManager(row.getCell(2).getStringCellValue())
                    .contactWay(row.getCell(3).getStringCellValue())
                    .hotelName(row.getCell(4).getStringCellValue())
                    .address(row.getCell(5).getStringCellValue())
                    .isCovered(row.getCell(6).getStringCellValue())
                    .enterpriseNumber(row.getCell(7).getStringCellValue())
                    .buildingNum(row.getCell(8).getStringCellValue())
                    .propertyName(row.getCell(9).getStringCellValue())
                    .isProperty(row.getCell(10).getStringCellValue())
                    .groupNumber(row.getCell(11).getStringCellValue())
                    .responsiblePerson(row.getCell(12).getStringCellValue())
                    .position(row.getCell(13).getStringCellValue())
                    .phoneNumber(row.getCell(14).getStringCellValue())
                    .operator(row.getCell(15).getStringCellValue())
                    .endTime1(row.getCell(16).getStringCellValue())
                    .internetCharge(row.getCell(17).getStringCellValue())
                    .endTime2(row.getCell(18).getStringCellValue())
                    .visitDate(row.getCell(19).getStringCellValue())
                    .visitInformation(row.getCell(20).getStringCellValue()).build());
        }
        mbpBuildingService.asyncSaveBuilding(commercialBuildingPTOS);

    }

    /**
     * 获取产业园区的数据
     * @param sheet
     */
    public void getIndustrialPark(Sheet sheet){

        //获取sheet下有多少行
        int rowNum = sheet.getPhysicalNumberOfRows();
        List<IndustrialParkPTO> industrialParkPTOS = new ArrayList<>();
        //遍历所有的Row
        for (int i = 1; i < rowNum; i++) {

            //获取当前sheet的第i行
            Row row = sheet.getRow(i);
            short cellNum = row.getLastCellNum();
            for (int j = 0; j < cellNum; j++) {
                row.getCell(j).setCellType(CellType.STRING);
            }
            industrialParkPTOS.add(IndustrialParkPTO.builder().substation(row.getCell(1).getStringCellValue())
                    .customerManager(row.getCell(2).getStringCellValue())
                    .contactWay(row.getCell(3).getStringCellValue())
                    .hotelName(row.getCell(4).getStringCellValue())
                    .address(row.getCell(5).getStringCellValue())
                    .isCovered(row.getCell(6).getStringCellValue())
                    .enterpriseNumber(row.getCell(7).getStringCellValue())
                    .type(row.getCell(8).getStringCellValue())
                    .buildingNum(row.getCell(9).getStringCellValue())
                    .operator(row.getCell(10).getStringCellValue())
                    .endTime1(row.getCell(11).getStringCellValue())
                    .internetCharge(row.getCell(12).getStringCellValue())
                    .endTime2(row.getCell(13).getStringCellValue())
                    .visitDate(row.getCell(14).getStringCellValue())
                    .visitInformation(row.getCell(15).getStringCellValue()).build());
        }
        industrialParkService.asyncSaveInpark(industrialParkPTOS);

    }

    /**
     * 获取商务楼宇二级明细的数据
     * @param sheet
     */
    public void getBuildingDel(Sheet sheet){

        //获取sheet下有多少行
        int rowNum = sheet.getPhysicalNumberOfRows();
        List<IndustrialParkPTO> industrialParkPTOS = new ArrayList<>();
        //遍历所有的Row
        for (int i = 1; i < rowNum; i++) {

            //获取当前sheet的第i行
            Row row = sheet.getRow(i);
            short cellNum = row.getLastCellNum();
            for (int j = 0; j < cellNum; j++) {
                row.getCell(j).setCellType(CellType.STRING);
            }
            industrialParkPTOS.add(IndustrialParkPTO.builder().substation(row.getCell(1).getStringCellValue())
                    .customerManager(row.getCell(2).getStringCellValue())
                    .contactWay(row.getCell(3).getStringCellValue())
                    .hotelName(row.getCell(4).getStringCellValue())
                    .address(row.getCell(5).getStringCellValue())
                    .isCovered(row.getCell(6).getStringCellValue())
                    .enterpriseNumber(row.getCell(7).getStringCellValue())
                    .type(row.getCell(8).getStringCellValue())
                    .buildingNum(row.getCell(9).getStringCellValue())
                    .operator(row.getCell(10).getStringCellValue())
                    .endTime1(row.getCell(11).getStringCellValue())
                    .internetCharge(row.getCell(12).getStringCellValue())
                    .endTime2(row.getCell(13).getStringCellValue())
                    .visitDate(row.getCell(14).getStringCellValue())
                    .visitInformation(row.getCell(15).getStringCellValue()).build());
        }
        industrialParkService.asyncSaveInpark(industrialParkPTOS);

    }
}
