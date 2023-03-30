package com.tencent.wxcloudrun.service.impl;

import com.alibaba.excel.EasyExcel;
import com.tencent.wxcloudrun.MbpType;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.excelEntity.*;
import com.tencent.wxcloudrun.pto.CommercialBuildingPTO;
import com.tencent.wxcloudrun.pto.HotelPTO;
import com.tencent.wxcloudrun.pto.IndustrialParkPTO;
import com.tencent.wxcloudrun.service.MbpService;
import com.tencent.wxcloudrun.utils.CopyList;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    private static ApiResponse apiResponse = ApiResponse.ok();
    @Override
    public ApiResponse parsingTable(MultipartFile multipartFile,String marketType,Integer id) {
        File file = null;
        if (multipartFile!=null){
            try {
                file = multipartFileToFile(multipartFile);
                if(!file.exists() || file.length() == 0) {
                    throw new RuntimeException("文件为空！");
                }
                apiResponse = parseExcel(file, marketType, id);
                return apiResponse;

            } catch (Exception e) {
                throw new RuntimeException("文件解析失败！");
            }
        }
            return apiResponse;

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

    public ApiResponse parseExcel(File file,String marketType,Integer id) throws Exception{
        InputStream inputStream = new FileInputStream(file);
        List<T> list = new ArrayList<>();
        switch (marketType){
            case "酒店宾馆":
                list = EasyExcel.read(inputStream)
                        // 设置与Excel表映射的类
                        .head(ExcelHotel.class)
                        // 设置sheet,默认读取第一个
                        .sheet()
                        // 设置标题所在行数
                        .headRowNumber(1)
                        // 异步读取
                        .doReadSync();
                List<HotelPTO> hotels = CopyList.convertList2List( list,HotelPTO.class);
                List<HotelPTO> hotelPTOS = mbpHotelService.queryAllNameAndID();
                // 查找到excel中的 hotelName相同的记录
                List<HotelPTO> hotelPTOS1 = hotelPTOS.stream()
                        .map(t -> hotels.stream()
                                .filter(s -> Objects.nonNull(t.getHotelName()) && Objects.nonNull(s.getHotelName()) && Objects.equals(t.getHotelName(), s.getHotelName()))
                                .findAny()
                                .orElse(null))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
                if (hotelPTOS1.size()>0){


                for (HotelPTO hotelPTO:hotelPTOS1) {
                    hotelPTO.setId( hotelPTOS.stream().filter(item -> item.getHotelName().equals(hotelPTO.getHotelName())).findFirst().get().getId());
                    hotels.removeIf(hotel -> hotel.getHotelName().equals(hotelPTO.getHotelName()));
                }
                    apiResponse.setData(hotelPTOS1);
                }
                mbpHotelService.asyncSaveHotel(hotels);

                break;
            case "商务楼宇":
                list = EasyExcel.read(inputStream)
                        // 设置与Excel表映射的类
                        .head(ExcelBuilding.class)
                        // 设置sheet,默认读取第一个
                        .sheet()
                        // 设置标题所在行数
                        .headRowNumber(1)
                        // 异步读取
                        .doReadSync();
                break;
            case "商务楼宇二级明细":
                list = EasyExcel.read(inputStream)
                        // 设置与Excel表映射的类
                        .head(ExcelBuildingDel.class)
                        // 设置sheet,默认读取第一个
                        .sheet()
                        // 设置标题所在行数
                        .headRowNumber(1)
                        // 异步读取
                        .doReadSync();
                break;
            case "产业园区":
                list = EasyExcel.read(inputStream)
                        // 设置与Excel表映射的类
                        .head(ExcelInPark.class)
                        // 设置sheet,默认读取第一个
                        .sheet()
                        // 设置标题所在行数
                        .headRowNumber(1)
                        // 异步读取
                        .doReadSync();
                break;
            case "产业园区二级明细":
                list = EasyExcel.read(inputStream)
                        // 设置与Excel表映射的类
                        .head(ExcelInParkDel.class)
                        // 设置sheet,默认读取第一个
                        .sheet()
                        // 设置标题所在行数
                        .headRowNumber(1)
                        // 异步读取
                        .doReadSync();
                break;
            case "沿街商铺":
                list = EasyExcel.read(inputStream)
                        // 设置与Excel表映射的类
                        .head(ExcelShop.class)
                        // 设置sheet,默认读取第一个
                        .sheet()
                        // 设置标题所在行数
                        .headRowNumber(1)
                        // 异步读取
                        .doReadSync();
                break;
            case "沿街商铺二级明细":
                list = EasyExcel.read(inputStream)
                        // 设置与Excel表映射的类
                        .head(ExcelShopDel.class)
                        // 设置sheet,默认读取第一个
                        .sheet()
                        // 设置标题所在行数
                        .headRowNumber(1)
                        // 异步读取
                        .doReadSync();
                break;
            default:
                return null;
        }

        return apiResponse;

    }

}
