package com.tencent.wxcloudrun.service.impl;

import com.alibaba.excel.EasyExcel;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.excelEntity.*;
import com.tencent.wxcloudrun.pto.*;
import com.tencent.wxcloudrun.service.MbpService;
import com.tencent.wxcloudrun.utils.CopyListUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
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

    @Override
    public ApiResponse parsingTable(MultipartFile multipartFile, String marketType, Integer id) {
        ApiResponse apiResponse = new ApiResponse();
        File file = null;
        if (multipartFile != null) {
            try {
                file = multipartFileToFile(multipartFile);
                if (!file.exists() || file.length() == 0) {
                    throw new RuntimeException("文件为空！");
                }
                return parseExcel(file, marketType, id);


            } catch (Exception e) {
                throw new RuntimeException("文件解析失败！");
            }
        }
        return apiResponse;

    }

    @Override
    public ApiResponse downloadTable(String marketType, String customerManager, String startTime, String endTime) {
        ApiResponse apiResponse = new ApiResponse();
        File file = null;

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

    public ApiResponse parseExcel(File file, String marketType, Integer id) {
        ApiResponse apiResponse = ApiResponse.ok();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            List<T> list = new ArrayList<>();
            switch (marketType) {
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
                    List<HotelPTO> hotels = CopyListUtils.convertList2List(list, HotelPTO.class);
                    List<HotelPTO> hotelPTOS = mbpHotelService.queryAllNameAndID();
                    // 查找到excel中的 hotelName相同的记录
                    List<HotelPTO> hotelPTOS1 = hotelPTOS.stream()
                            .map(t -> hotels.stream()
                                    .filter(s -> Objects.nonNull(t.getHotelName()) && Objects.nonNull(s.getHotelName()) && Objects.equals(t.getHotelName(), s.getHotelName()))
                                    .findAny()
                                    .orElse(null))
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
                    if (hotelPTOS1.size() > 0) {
                        for (HotelPTO hotelPTO : hotelPTOS1) {
                            hotelPTO.setId(hotelPTOS.stream().filter(item -> item.getHotelName().equals(hotelPTO.getHotelName())).findFirst().get().getId());
                            hotels.removeIf(hotel -> hotel.getHotelName().equals(hotelPTO.getHotelName()));
                        }
                        apiResponse.setData(hotelPTOS1);
                    }
                    if (hotels.size()>0){
                        ApiResponse response = mbpHotelService.asyncSaveHotel(hotels);
                        apiResponse.setMsg(response.getMsg());
                        if (!response.getCode().equals(200)){
                            apiResponse.setData(null);
                            apiResponse.setCode(response.getCode());
                        }
                    }
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
                    List<CommercialBuildingPTO> buildings = CopyListUtils.convertList2List(list, CommercialBuildingPTO.class);
                    List<CommercialBuildingPTO> commercialBuildingPTOS = mbpBuildingService.queryAllNameAndID();
                    // 查找到excel中的 hotelName相同的记录
                    List<CommercialBuildingPTO> commercialBuildingPTOS1 = commercialBuildingPTOS.stream()
                            .map(t -> buildings.stream()
                                    .filter(s -> Objects.nonNull(t.getHotelName()) && Objects.nonNull(s.getHotelName()) && Objects.equals(t.getHotelName(), s.getHotelName()))
                                    .findAny()
                                    .orElse(null))
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
                    if (commercialBuildingPTOS1.size() > 0) {
                        for (CommercialBuildingPTO commercialBuildingPTO : commercialBuildingPTOS1) {
                            commercialBuildingPTO.setId(commercialBuildingPTOS.stream().filter(item -> item.getHotelName().equals(commercialBuildingPTO.getHotelName())).findFirst().get().getId());
                            buildings.removeIf(comBuilding -> comBuilding.getHotelName().equals(commercialBuildingPTO.getHotelName()));
                        }
                        apiResponse.setData(commercialBuildingPTOS1);
                    }
                    if (buildings.size()>0){
                        ApiResponse response = mbpBuildingService.asyncSaveBuilding(buildings);
                        apiResponse.setMsg(response.getMsg());
                        if (!response.getCode().equals(200)){
                            apiResponse.setData(null);
                            apiResponse.setCode(response.getCode());
                        }
                    }
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
                    List<CommercialBuildingDetailPTO> buildingDels = CopyListUtils.convertList2List(list, CommercialBuildingDetailPTO.class);
                    buildingDels.forEach(build->build.setBuildingId(id));
                    List<CommercialBuildingDetailPTO> commercialBuildingDetailPTOS = mbpBuildingDetailService.queryAllNameAndID(id);
                    // 查找到excel中的 hotelName相同的记录
                    List<CommercialBuildingDetailPTO> commercialBuildingDetailPTOS1 = commercialBuildingDetailPTOS.stream()
                            .map(t -> buildingDels.stream()
                                    .filter(s -> Objects.nonNull(t.getEnterpriseName()) && Objects.nonNull(s.getEnterpriseName()) && Objects.equals(t.getEnterpriseName(), s.getEnterpriseName()))
                                    .findAny()
                                    .orElse(null))
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
                    if (commercialBuildingDetailPTOS1.size() > 0) {
                        for (CommercialBuildingDetailPTO commercialBuildingDetailPTO : commercialBuildingDetailPTOS1) {
                            commercialBuildingDetailPTO.setId(commercialBuildingDetailPTOS.stream().filter(item -> item.getEnterpriseName().equals(commercialBuildingDetailPTO.getEnterpriseName())).findFirst().get().getId());
                            buildingDels.removeIf(comBuildDel -> comBuildDel.getEnterpriseName().equals(commercialBuildingDetailPTO.getEnterpriseName()));
                        }
                        apiResponse.setData(commercialBuildingDetailPTOS1);
                    }
                    if (buildingDels.size()>0){
                        ApiResponse response = mbpBuildingDetailService.asyncSaveBuildingDel(buildingDels);
                        apiResponse.setMsg(response.getMsg());
                        if (!response.getCode().equals(200)){
                            apiResponse.setData(null);
                            apiResponse.setCode(response.getCode());
                        }
                    }
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
                    List<IndustrialParkPTO> inPark = CopyListUtils.convertList2List(list, IndustrialParkPTO.class);
                    List<IndustrialParkPTO> industrialParkPTOS = industrialParkService.queryAllNameAndID();
                    // 查找到excel中的 hotelName相同的记录
                    List<IndustrialParkPTO> industrialParkPTOS1 = industrialParkPTOS.stream()
                            .map(t -> inPark.stream()
                                    .filter(s -> Objects.nonNull(t.getHotelName()) && Objects.nonNull(s.getHotelName()) && Objects.equals(t.getHotelName(), s.getHotelName()))
                                    .findAny()
                                    .orElse(null))
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
                    if (industrialParkPTOS1.size() > 0) {
                        for (IndustrialParkPTO industrialParkPTO : industrialParkPTOS1) {
                            industrialParkPTO.setId(industrialParkPTOS.stream().filter(item -> item.getHotelName().equals(industrialParkPTO.getHotelName())).findFirst().get().getId());
                            inPark.removeIf(inparkPto -> inparkPto.getHotelName().equals(industrialParkPTO.getHotelName()));
                        }
                        apiResponse.setData(industrialParkPTOS1);
                    }
                    if (inPark.size()>0){
                        ApiResponse response = industrialParkService.asyncSaveInpark(inPark);
                        apiResponse.setMsg(response.getMsg());
                        if (!response.getCode().equals(200)){
                            apiResponse.setData(null);
                            apiResponse.setCode(response.getCode());
                        }
                    }
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
                    List<IndustrialParkDetailPTO> inParkDel = CopyListUtils.convertList2List(list, IndustrialParkDetailPTO.class);
                    inParkDel.forEach(inpark->inpark.setParkId(id));
                    List<IndustrialParkDetailPTO> industrialParkDetailPTOS = industrialParkDetailService.queryAllNameAndID(id);
                    // 查找到excel中的 hotelName相同的记录
                    List<IndustrialParkDetailPTO> industrialParkDetailPTOS1 = industrialParkDetailPTOS.stream()
                            .map(t -> inParkDel.stream()
                                    .filter(s -> Objects.nonNull(t.getEnterpriseName()) && Objects.nonNull(s.getEnterpriseName()) && Objects.equals(t.getEnterpriseName(), s.getEnterpriseName()))
                                    .findAny()
                                    .orElse(null))
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
                    if (industrialParkDetailPTOS1.size() > 0) {
                        for (IndustrialParkDetailPTO industrialParkDetailPTO : industrialParkDetailPTOS1) {
                            industrialParkDetailPTO.setId(industrialParkDetailPTOS.stream().filter(item -> item.getEnterpriseName().equals(industrialParkDetailPTO.getEnterpriseName())).findFirst().get().getId());
                            inParkDel.removeIf(inparkPto -> inparkPto.getEnterpriseName().equals(industrialParkDetailPTO.getEnterpriseName()));
                        }
                        apiResponse.setData(industrialParkDetailPTOS1);
                    }
                    if (inParkDel.size()>0){
                        ApiResponse response = industrialParkDetailService.asyncSaveParkDel(inParkDel);
                        apiResponse.setMsg(response.getMsg());
                        if (!response.getCode().equals(200)){
                            apiResponse.setData(null);
                            apiResponse.setCode(response.getCode());
                        }
                    }
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
                    List<ShopPTO> shops = CopyListUtils.convertList2List(list, ShopPTO.class);
                    List<ShopPTO> shopPTOS = mbpShopService.queryAllNameAndID();
                    // 查找到excel中的 hotelName相同的记录
                    List<ShopPTO> shopPTOS1 = shopPTOS.stream()
                            .map(t -> shops.stream()
                                    .filter(s -> Objects.nonNull(t.getCellName()) && Objects.nonNull(s.getCellName()) && Objects.equals(t.getCellName(), s.getCellName()))
                                    .findAny()
                                    .orElse(null))
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
                    if (shopPTOS1.size() > 0) {
                        for (ShopPTO shopPTO : shopPTOS1) {
                            shopPTO.setId(shopPTOS.stream().filter(item -> item.getCellName().equals(shopPTO.getCellName())).findFirst().get().getId());
                            shops.removeIf(inparkPto -> inparkPto.getCellName().equals(shopPTO.getCellName()));
                        }
                        apiResponse.setData(shopPTOS1);
                    }
                    if (shops.size()>0){
                        ApiResponse response = mbpShopService.asyncSaveShop(shops);
                        apiResponse.setMsg(response.getMsg());
                        if (!response.getCode().equals(200)){
                            apiResponse.setData(null);
                            apiResponse.setCode(response.getCode());
                        }
                    }
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
                    List<ShopDetailPTO> shopDetails = CopyListUtils.convertList2List(list, ShopDetailPTO.class);
                    shopDetails.forEach(shop -> shop.setShopId(id));
                    List<ShopDetailPTO> shopDetailPTOS = mbpShopDetailService.queryAllNameAndID(id);
                    // 查找到excel中的 hotelName相同的记录
                    List<ShopDetailPTO> shopDetailPTOS1 = shopDetailPTOS.stream()
                            .map(t -> shopDetails.stream()
                                    .filter(s -> Objects.nonNull(t.getShopName()) && Objects.nonNull(s.getShopName()) && Objects.equals(t.getShopName(), s.getShopName()))
                                    .findAny()
                                    .orElse(null))
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
                    if (shopDetailPTOS1.size() > 0) {
                        for (ShopDetailPTO shopPTO : shopDetailPTOS1) {
                            shopPTO.setId(shopDetailPTOS.stream().filter(item -> item.getShopName().equals(shopPTO.getShopName())).findFirst().get().getId());
                            shopDetails.removeIf(inparkPto -> inparkPto.getShopName().equals(shopPTO.getShopName()));
                        }
                        apiResponse.setData(shopDetailPTOS1);
                    }
                    if (shopDetails.size()>0){
                        ApiResponse response = mbpShopDetailService.asyncSaveShopDel(shopDetails);
                        apiResponse.setMsg(response.getMsg());
                        if (!response.getCode().equals(200)){
                            apiResponse.setData(null);
                            apiResponse.setCode(response.getCode());
                        }
                    }
                    break;
                default:
                    apiResponse.setCode(400);
                    apiResponse.setMsg("文件导入失败，没有相关场景类型");
            }
            return apiResponse;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return apiResponse;
        }


    }

}
