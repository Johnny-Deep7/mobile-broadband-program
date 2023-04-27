package com.tencent.wxcloudrun.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.excelEntity.*;
import com.tencent.wxcloudrun.mapper.*;
import com.tencent.wxcloudrun.pto.*;
import com.tencent.wxcloudrun.service.MbpService;
import com.tencent.wxcloudrun.utils.CopyListUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.System.currentTimeMillis;

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

    @Resource
    private HotelMapper hotelMapper;

    @Resource
    private CommercialBuildingMapper commercialBuildingMapper;

    @Resource
    private CommercialBuildingDetailMapper commercialBuildingDetailMapper;

    @Resource
    private IndustrialParkMapper industrialParkMapper;

    @Resource
    private IndustrialParkDetailMapper industrialParkDetailMapper;

    @Resource
    private ShopMapper shopMapper;

    @Resource
    private ShopDetailMapper shopDetailMapper;

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
    public ApiResponse downloadTable(HttpServletResponse response, String marketType, String substation, String customerManager,
                                     String startTime, String endTime, String subId) throws IOException {
        ApiResponse apiResponse = new ApiResponse();
        if(StringUtils.isBlank(marketType) || ("沿街商铺".equals(marketType) && StringUtils.isNotBlank(customerManager))){
            apiResponse.setCode(400);
            apiResponse.setMsg("场景不可为空，请注意沿街商铺不可选客户经理");
            return apiResponse;
        }
        String excelFileName = URLEncoder.encode("吴中调查情况表", "UTF-8")
                .replaceAll("\\+", "%20");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + excelFileName + ".xls");
        Set<String> excludeField = new HashSet<>();
        excludeField.add("id");
        switch (marketType){
            case "酒店宾馆":
                log.info("查询酒店宾馆数据开始！");
                QueryWrapper<HotelPTO> wrapperHotel = new QueryWrapper<>();
                if (StringUtils.isNotBlank(substation)){
                    wrapperHotel.eq("substation",substation);
                }
                if (StringUtils.isNotBlank(customerManager)){
                    wrapperHotel.eq("customer_manager",customerManager);
                }
                if (StringUtils.isNotBlank(startTime)){
                    wrapperHotel.ge("modify_time",startTime);
                }
                if (StringUtils.isNotBlank(endTime)){
                    wrapperHotel.le("modify_time",endTime);
                }
                List<HotelPTO> wrHotel = hotelMapper.selectList(wrapperHotel);
                if(ObjectUtils.isNull(wrHotel)){
                    apiResponse.setMsg("没有符合要求的数据");
                    return apiResponse;
                }
                EasyExcel.write(response.getOutputStream(),HotelPTO.class).excludeColumnFiledNames(excludeField).excelType(ExcelTypeEnum.XLS).sheet("酒店宾馆").doWrite(wrHotel);
                apiResponse.setCode(200);
                apiResponse.setMsg("正在导出");
                break;
            case "商务楼宇":
                log.info("查询商务楼宇数据开始！");
                QueryWrapper<CommercialBuildingPTO> wrapperCommercialBuilding = new QueryWrapper<>();
                if (StringUtils.isNotBlank(substation)){
                    wrapperCommercialBuilding.eq("substation",substation);
                }
                if (StringUtils.isNotBlank(customerManager)){
                    wrapperCommercialBuilding.eq("customer_manager",customerManager);
                }
                if (StringUtils.isNotBlank(startTime)){
                    wrapperCommercialBuilding.ge("modify_time",startTime);
                }
                if (StringUtils.isNotBlank(endTime)){
                    wrapperCommercialBuilding.le("modify_time",endTime);
                }
                List<CommercialBuildingPTO> wrCommercialBuilding = commercialBuildingMapper.selectList(wrapperCommercialBuilding);
                if(ObjectUtils.isNull(wrCommercialBuilding)){
                    apiResponse.setMsg("没有符合要求的数据");
                    return apiResponse;
                }
                excludeField.add("roomNumber");
                excludeField.add("difficultPoint");
                EasyExcel.write(response.getOutputStream(),CommercialBuildingPTO.class).excludeColumnFiledNames(excludeField).excelType(ExcelTypeEnum.XLS).sheet("商务楼宇").doWrite(wrCommercialBuilding);
                apiResponse.setCode(200);
                apiResponse.setMsg("正在导出");
                break;
            case "商务楼宇二级明细":
                if(StringUtils.isBlank(subId)){
                    apiResponse.setCode(400);
                    apiResponse.setMsg("请输入相关明细的id");
                    return apiResponse;
                }
                log.info("查询商务楼宇二级数据开始！");
                QueryWrapper<CommercialBuildingDetailPTO> wrapperCommercialBuildingDetail = new QueryWrapper<>();
                if (StringUtils.isNotBlank(substation)){
                    wrapperCommercialBuildingDetail.eq("substation",substation);
                }
                if (StringUtils.isNotBlank(customerManager)){
                    wrapperCommercialBuildingDetail.eq("customer_manager",customerManager);
                }
                if (StringUtils.isNotBlank(startTime)){
                    wrapperCommercialBuildingDetail.ge("modify_time",startTime);
                }
                if (StringUtils.isNotBlank(endTime)){
                    wrapperCommercialBuildingDetail.le("modify_time",endTime);
                }
                wrapperCommercialBuildingDetail.eq("building_id",subId);
                List<CommercialBuildingDetailPTO> wrCommercialBuildingDetail = commercialBuildingDetailMapper.selectList(wrapperCommercialBuildingDetail);
                if(ObjectUtils.isNull(wrCommercialBuildingDetail)){
                    apiResponse.setMsg("没有符合要求的数据");
                    return apiResponse;
                }
                excludeField.add("address");
                excludeField.add("hotelName");
                excludeField.add("isCovered");
                excludeField.add("endTime1");
                excludeField.add("difficultPoint");
                EasyExcel.write(response.getOutputStream(),CommercialBuildingDetailPTO.class).excludeColumnFiledNames(excludeField).excelType(ExcelTypeEnum.XLS).sheet("商务楼宇二级明细").doWrite(wrCommercialBuildingDetail);
                apiResponse.setCode(200);
                apiResponse.setMsg("正在导出");
                break;
            case "产业园区":
                log.info("查询产业园区数据开始！");
                QueryWrapper<IndustrialParkPTO> wrapperIndustrialPark = new QueryWrapper<>();
                if (StringUtils.isNotBlank(substation)){
                    wrapperIndustrialPark.eq("substation",substation);
                }
                if (StringUtils.isNotBlank(customerManager)){
                    wrapperIndustrialPark.eq("customer_manager",customerManager);
                }
                if (StringUtils.isNotBlank(startTime)){
                    wrapperIndustrialPark.ge("modify_time",startTime);
                }
                if (StringUtils.isNotBlank(endTime)){
                    wrapperIndustrialPark.le("modify_time",endTime);
                }
                List<IndustrialParkPTO> wrIndustrialPark = industrialParkMapper.selectList(wrapperIndustrialPark);
                if(ObjectUtils.isNull(wrIndustrialPark)){
                    apiResponse.setMsg("没有符合要求的数据");
                    return apiResponse;
                }
                excludeField.add("roomNumber");
                excludeField.add("groupNumber");
                excludeField.add("responsiblePerson");
                excludeField.add("position");
                excludeField.add("phoneNumber");
                excludeField.add("difficultPoint");
                EasyExcel.write(response.getOutputStream(),IndustrialParkPTO.class).excludeColumnFiledNames(excludeField).excelType(ExcelTypeEnum.XLS).sheet("产业园区").doWrite(wrIndustrialPark);
                apiResponse.setCode(200);
                apiResponse.setMsg("正在导出");
                break;
            case "产业园区二级明细":
                if(StringUtils.isBlank(subId)){
                    apiResponse.setCode(400);
                    apiResponse.setMsg("请输入相关明细的id");
                    return apiResponse;
                }
                log.info("查询产业园区二级数据开始！");
                QueryWrapper<IndustrialParkDetailPTO> wrapperIndustrialParkDetail = new QueryWrapper<>();
                if (StringUtils.isNotBlank(substation)){
                    wrapperIndustrialParkDetail.eq("substation",substation);
                }
                if (StringUtils.isNotBlank(customerManager)){
                    wrapperIndustrialParkDetail.eq("customer_manager",customerManager);
                }
                if (StringUtils.isNotBlank(startTime)){
                    wrapperIndustrialParkDetail.ge("modify_time",startTime);
                }
                if (StringUtils.isNotBlank(endTime)){
                    wrapperIndustrialParkDetail.le("modify_time",endTime);
                }
                wrapperIndustrialParkDetail.eq("park_id",subId);
                List<IndustrialParkDetailPTO> wrIndustrialParkDetail = industrialParkDetailMapper.selectList(wrapperIndustrialParkDetail);
                if(ObjectUtils.isNull(wrIndustrialParkDetail)){
                    apiResponse.setMsg("没有符合要求的数据");
                    return apiResponse;
                }
                EasyExcel.write(response.getOutputStream(),IndustrialParkDetailPTO.class).excludeColumnFiledNames(excludeField).excelType(ExcelTypeEnum.XLS).sheet("产业园区二级明细").doWrite(wrIndustrialParkDetail);
                apiResponse.setCode(200);
                apiResponse.setMsg("正在导出");
                break;
            case "沿街商铺":
                log.info("查询沿街商铺数据开始！");
                QueryWrapper<ShopPTO> wrapperShop = new QueryWrapper<>();
                if (StringUtils.isNotBlank(substation)){
                    wrapperShop.eq("substation",substation);
                }
                if (StringUtils.isNotBlank(customerManager)){
                    wrapperShop.eq("customer_manager",customerManager);
                }
                if (StringUtils.isNotBlank(startTime)){
                    wrapperShop.ge("modify_time",startTime);
                }
                if (StringUtils.isNotBlank(endTime)){
                    wrapperShop.le("modify_time",endTime);
                }
                List<ShopPTO> wrShop = shopMapper.selectList(wrapperShop);
                if(ObjectUtils.isNull(wrShop)){
                    apiResponse.setMsg("没有符合要求的数据");
                    return apiResponse;
                }
                EasyExcel.write(response.getOutputStream(),ShopPTO.class).excludeColumnFiledNames(excludeField).excelType(ExcelTypeEnum.XLS).sheet("沿街商铺").doWrite(wrShop);
                apiResponse.setCode(200);
                apiResponse.setMsg("正在导出");
                break;
            case "沿街商铺二级明细":
                if(StringUtils.isBlank(subId)){
                    apiResponse.setCode(400);
                    apiResponse.setMsg("请输入相关明细的id");
                    return apiResponse;
                }
                log.info("查询沿街商铺二级数据开始！");
                QueryWrapper<ShopDetailPTO> wrapperShopDetail = new QueryWrapper<>();
                if (StringUtils.isNotBlank(substation)){
                    wrapperShopDetail.eq("substation",substation);
                }
                if (StringUtils.isNotBlank(customerManager)){
                    wrapperShopDetail.eq("customer_manager",customerManager);
                }
                if (StringUtils.isNotBlank(startTime)){
                    wrapperShopDetail.ge("modify_time",startTime);
                }
                if (StringUtils.isNotBlank(endTime)){
                    wrapperShopDetail.le("modify_time",endTime);
                }
                wrapperShopDetail.eq("shop_id",subId);
                List<ShopDetailPTO> wrShopDetail = shopDetailMapper.selectList(wrapperShopDetail);
                if(ObjectUtils.isNull(wrShopDetail)){
                    apiResponse.setMsg("没有符合要求的数据");
                    return apiResponse;
                }
                EasyExcel.write(response.getOutputStream(),ShopDetailPTO.class).excludeColumnFiledNames(excludeField).excelType(ExcelTypeEnum.XLS).sheet("沿街商铺二级明细").doWrite(wrShopDetail);
                apiResponse.setCode(200);
                apiResponse.setMsg("正在导出");
                break;
        }
//        String excelFileName = URLEncoder.encode("吴中调查情况表", "UTF-8")
//                .replaceAll("\\+", "%20");
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//        response.setCharacterEncoding("utf-8");
//        response.setHeader("Content-disposition", "attachment;filename=" + excelFileName + ".xls");
//        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).build();
//        WriteSheet hotelSheet = EasyExcel.writerSheet(0, "酒店宾馆").build();
//        WriteSheet CommercialBuildingSheet = EasyExcel.writerSheet(1, "商务楼宇").build();
//        WriteSheet CommercialBuildingDetailSheet = EasyExcel.writerSheet(2, "产业园区").build();
//        WriteSheet IndustrialParkSheet = EasyExcel.writerSheet(3, "商务楼宇二级明细").build();
//        WriteSheet IndustrialParkDetailSheet = EasyExcel.writerSheet(4, "产业园区二级明细").build();
//        WriteSheet ShopSheet = EasyExcel.writerSheet(5, "沿街商铺").build();
//        WriteSheet ShopDetailSheet = EasyExcel.writerSheet(6, "沿街商铺二级明细").build();
//
//        excelWriter.write(wrHotel,hotelSheet);
//        excelWriter.write(wrCommercialBuilding,CommercialBuildingSheet);
//        excelWriter.write(wrCommercialBuildingDetail,CommercialBuildingDetailSheet);
//        excelWriter.write(wrIndustrialPark,IndustrialParkSheet);
//        excelWriter.write(wrIndustrialParkDetail,IndustrialParkDetailSheet);
//        excelWriter.write(wrShop,ShopSheet);
//        excelWriter.write(wrShopDetail,ShopDetailSheet);
//
//
//        excelWriter.finish();
//        response.flushBuffer();

//        //获取模板(模板你可以放在任何位置，前提是你能获取到。这里放在resource下)
//        ClassPathResource couponOrderTemplateResource = new ClassPathResource("userInfo.xlsx");
//
//        response.setContentType("application/vnd.ms-excel");
//        response.setCharacterEncoding("utf-8");
//        String excelFileName = URLEncoder.encode("吴中调查情况表", "UTF-8")
//                .replaceAll("\\+", "%20");
//        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
//        // attachment这个代表要下载的，如果去掉就直接打开了(attachment-作为附件下载,inline-在线打开)
//        // excelFileName是文件名，另存为或者下载时，为默认的文件名
//        response.setHeader("Content-disposition","attachment;filename=" + excelFileName + ".xlsx");
//        response.setHeader("Content-Type","application/octet-stream;charset=utf-8");
//
//        InputStream templateInputStream = null;
//
//        ExcelWriter excelWriter = null;
//        WriteSheet hotelSheet = null;
//        WriteSheet CommercialBuildingSheet = null;
//        WriteSheet CommercialBuildingDetailSheet = null;
//        WriteSheet IndustrialParkSheet = null;
//        WriteSheet IndustrialParkDetailSheet = null;
//        WriteSheet ShopSheet = null;
//        WriteSheet ShopDetailSheet = null;
//
//        ServletOutputStream outputStream = null;
//
////        try {
////            outputStream = response.getOutputStream();
////            templateInputStream = couponOrderTemplateResource.getInputStream();
////        } catch (IOException e) {
////            log.error("获取模板失败");
////        }
//
//        // 这里注意 入参用了forceNewRow 代表在写入list的时候不管list下面有没有空行 都会创建一行，然后下面的数据往后移动。默认 是false，会直接使用下一行，如果没有则创建。
//        // forceNewRow 如果设置了true,有个缺点 就是他会把所有的数据都放到内存了，所以慎用
//        // 简单的说 如果你的模板有list,且list不是最后一行，下面还有数据需要填充 就必须设置 forceNewRow=true 但是这个就会把所有数据放到内存 会很耗内存
//        // 如果数据量大 list不是最后一行 参照下一个
//        FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
////        excelWriter = EasyExcel.write(outputStream).withTemplate(templateInputStream).build();
//
//        //创建第一个sheet
//        hotelSheet = EasyExcel.writerSheet("酒店宾馆").build();
//        //填充
//        excelWriter.fill(wrapperHotel,fillConfig,hotelSheet);
//
//        //创建第二个sheet
//        CommercialBuildingSheet = EasyExcel.writerSheet("商务楼宇").build();
//        //填充
//        excelWriter.fill(wrapperCommercialBuilding,fillConfig,CommercialBuildingSheet);
//
//        IndustrialParkSheet = EasyExcel.writerSheet("产业园区").build();
//        //填充
//        excelWriter.fill(wrapperIndustrialPark,fillConfig,IndustrialParkSheet);
//
//        CommercialBuildingDetailSheet = EasyExcel.writerSheet("商务楼宇二级明细").build();
//        //填充
//        excelWriter.fill(wrapperCommercialBuildingDetail,fillConfig,CommercialBuildingDetailSheet);
//
//        IndustrialParkDetailSheet = EasyExcel.writerSheet("产业园区二级明细").build();
//        //填充
//        excelWriter.fill(wrapperIndustrialParkDetail,fillConfig,IndustrialParkDetailSheet);
//
//        ShopSheet = EasyExcel.writerSheet("沿街商铺").build();
//        //填充
//        excelWriter.fill(wrapperShop,fillConfig,ShopSheet);
//
//        ShopDetailSheet = EasyExcel.writerSheet("沿街商铺二级明细").build();
//        //填充
//        excelWriter.fill(wrapperShopDetail,fillConfig,ShopDetailSheet);
//
//        //关闭
//        excelWriter.finish();
//        IOUtils.closeQuietly(templateInputStream);
//        IOUtils.closeQuietly(outputStream);
//        IOUtils.closeQuietly((Closeable) excelWriter);

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
