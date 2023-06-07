package com.tencent.wxcloudrun.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.GetCountDTO;
import com.tencent.wxcloudrun.dto.StatisticalAnalysisDTO;
import com.tencent.wxcloudrun.dto.excelEntity.*;
import com.tencent.wxcloudrun.mapper.*;
import com.tencent.wxcloudrun.pto.*;
import com.tencent.wxcloudrun.service.MbpService;
import com.tencent.wxcloudrun.utils.CopyListUtils;
import com.tencent.wxcloudrun.utils.EasyExcelUtils;
import com.tencent.wxcloudrun.utils.SubstationType;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;
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
                excludeField.add("roomNumber");
                excludeField.add("difficultPoint");
                excludeField.add("hotelType");
                excludeField.add("gims");
                excludeField.add("gimsCover");
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
                excludeField.add("address");
                excludeField.add("hotelName");
                excludeField.add("isCovered");
                excludeField.add("endTime1");
                excludeField.add("difficultPoint");
                excludeField.add("buildingId");
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
                excludeField.add("parkId");
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
                    wrapperShop.eq("street_chief",customerManager);
                }
                if (StringUtils.isNotBlank(startTime)){
                    wrapperShop.ge("modify_time",startTime);
                }
                if (StringUtils.isNotBlank(endTime)){
                    wrapperShop.le("modify_time",endTime);
                }
                List<ShopPTO> wrShop = shopMapper.selectList(wrapperShop);
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
                excludeField.add("shopId");
                EasyExcel.write(response.getOutputStream(),ShopDetailPTO.class).excludeColumnFiledNames(excludeField).excelType(ExcelTypeEnum.XLS).sheet("沿街商铺二级明细").doWrite(wrShopDetail);
                apiResponse.setCode(200);
                apiResponse.setMsg("正在导出");
                break;
        }

        return apiResponse;
    }

    @Override
    public ApiResponse fullExport (String startTime,String endTime) {
        List<HashMap<String, Long>> hotel = hotelMapper.getCount(startTime, endTime);
        List<HashMap<String, Long>> commercialBuilding = commercialBuildingDetailMapper.getCount(startTime, endTime);
        List<HashMap<String, Long>> industrialPark = industrialParkDetailMapper.getCount(startTime, endTime);
        List<HashMap<String, Long>> shop = shopDetailMapper.getCount(startTime, endTime);
        List<String> substations = new ArrayList<>();

        for (HashMap h:hotel) {
            substations.add(h.get("substation").toString());
        }

        for (HashMap h:commercialBuilding) {
            substations.add(h.get("substation").toString());
        }

        for (HashMap h:industrialPark) {
            substations.add(h.get("substation").toString());
        }
        for (HashMap h:shop) {
            substations.add(h.get("substation").toString());
        }
        List<String> collect = substations.stream().distinct().collect(Collectors.toList());
        StatisticalAnalysisDTO statisticalAnalysisDTO = new StatisticalAnalysisDTO();
        List<GetCountDTO> list = new ArrayList<>();
        GetCountDTO getCountDTO = null;
        for (String substation:collect) {
            Long hotelCount = 0L;
            Long buildingCount = 0L;
            Long park = 0L;
            Long shopCount = 0L;
            getCountDTO = new GetCountDTO();
            getCountDTO.setSubstation(substation);
            getCountDTO.setStartTime(startTime);
            getCountDTO.setEndTime(endTime);
            for (HashMap hashMap:hotel) {
                if (!hashMap.get("substation").equals(substation)){
                    continue;
                }else {
                    hotelCount = (Long) hashMap.get("count");
                }
            }

            for (HashMap hashMap:commercialBuilding) {
                if (!hashMap.get("substation").equals(substation)){
                    continue;
                }else {
                    buildingCount = (Long) hashMap.get("count");
                }
            }

            for (HashMap hashMap:industrialPark) {
                if (!hashMap.get("substation").equals(substation)){
                    continue;
                }else {
                    park = (Long) hashMap.get("count");
                }
            }

            for (HashMap hashMap:shop) {
                if (!hashMap.get("substation").equals(substation)){
                    continue;
                }else {
                    shopCount = (Long) hashMap.get("count");
                }
            }
            getCountDTO.setHotel(hotelCount.intValue());
            getCountDTO.setBuilding(buildingCount.intValue());
            getCountDTO.setIndustrialPark(park.intValue());
            getCountDTO.setShop(shopCount.intValue());
            getCountDTO.setTotel(hotelCount.intValue()+buildingCount.intValue()+park.intValue()+shopCount.intValue());
            list.add(getCountDTO);
        }
        statisticalAnalysisDTO.setGetCountDTOS(list);
        statisticalAnalysisDTO.setHotelCount(list.stream()
                .map(e -> e.getHotel()).reduce(0, Integer::sum));
        statisticalAnalysisDTO.setBuildingCount(list.stream()
                .map(e -> e.getBuilding()).reduce(0, Integer::sum));
        statisticalAnalysisDTO.setShopCount(list.stream()
                .map(e -> e.getShop()).reduce(0, Integer::sum));
        statisticalAnalysisDTO.setIndustrialParkCount(list.stream()
                .map(e -> e.getIndustrialPark()).reduce(0, Integer::sum));
        statisticalAnalysisDTO.setTotelCount(list.stream()
                .map(e -> e.getTotel()).reduce(0, Integer::sum));
        ApiResponse apiResponse = ApiResponse.ok();
        apiResponse.setData(statisticalAnalysisDTO);
        return apiResponse;
    }

    @Override
    public ApiResponse statistics (String startTime,String endTime,String substation) {
        List<HashMap<String, Long>> hotel = hotelMapper.getCountBySubstation(startTime, endTime,substation);
        List<HashMap<String, Long>> commercialBuilding = commercialBuildingDetailMapper.getCountBySubstation(startTime, endTime,substation);
        List<HashMap<String, Long>> industrialPark = industrialParkDetailMapper.getCountBySubstation(startTime, endTime,substation);
        List<HashMap<String, Long>> shop = shopDetailMapper.getCountBySubstation(startTime, endTime,substation);

        List<String> list1 = new ArrayList<>();

        for (HashMap h:hotel) {
            list1.add(h.get("customerManager").toString());
        }

        for (HashMap h:commercialBuilding) {
            list1.add(h.get("customerManager").toString());
        }

        for (HashMap h:industrialPark) {
            list1.add(h.get("customerManager").toString());
        }
        for (HashMap h:shop) {
            list1.add(h.get("customerManager").toString());
        }
        List<String> customerManagers = list1.stream().distinct().collect(Collectors.toList());

        StatisticalAnalysisDTO statisticalAnalysisDTO = new StatisticalAnalysisDTO();
        List<GetCountDTO> list = new ArrayList<>();
        GetCountDTO getCountDTO = null;

        for (String customerManager:customerManagers) {

            Long hotelCount = 0L;
            Long buildingCount = 0L;
            Long park = 0L;
            Long shopCount = 0L;

            getCountDTO = new GetCountDTO();
            getCountDTO.setSubstation(substation);
            getCountDTO.setCustomerManager(customerManager);
            getCountDTO.setStartTime(startTime);
            getCountDTO.setEndTime(endTime);

            for (HashMap hashMap:hotel) {
                if (!hashMap.get("customerManager").equals(customerManager)){
                    continue;
                }else {
                    hotelCount = (Long) hashMap.get("count");
                }
            }

            for (HashMap hashMap:commercialBuilding) {
                if (!hashMap.get("customerManager").equals(customerManager)){
                    continue;
                }else {
                    buildingCount = (Long) hashMap.get("count");
                }
            }

            for (HashMap hashMap:industrialPark) {
                if (!hashMap.get("customerManager").equals(customerManager)){
                    continue;
                }else {
                    park = (Long) hashMap.get("count");
                }
            }

            for (HashMap hashMap:shop) {
                if (!hashMap.get("customerManager").equals(customerManager)){
                    continue;
                }else {
                    shopCount = (Long) hashMap.get("count");
                }
            }

            getCountDTO.setHotel(hotelCount.intValue());
            getCountDTO.setBuilding(buildingCount.intValue());
            getCountDTO.setIndustrialPark(park.intValue());
            getCountDTO.setShop(shopCount.intValue());
            getCountDTO.setTotel(hotelCount.intValue()+buildingCount.intValue()+park.intValue()+shopCount.intValue());
            list.add(getCountDTO);
        }
        statisticalAnalysisDTO.setGetCountDTOS(list);
        statisticalAnalysisDTO.setHotelCount(list.stream()
                .map(e -> e.getHotel()).reduce(0, Integer::sum));
        statisticalAnalysisDTO.setBuildingCount(list.stream()
                .map(e -> e.getBuilding()).reduce(0, Integer::sum));
        statisticalAnalysisDTO.setShopCount(list.stream()
                .map(e -> e.getShop()).reduce(0, Integer::sum));
        statisticalAnalysisDTO.setIndustrialParkCount(list.stream()
                .map(e -> e.getIndustrialPark()).reduce(0, Integer::sum));
        statisticalAnalysisDTO.setTotelCount(list.stream()
                .map(e -> e.getTotel()).reduce(0, Integer::sum));
        ApiResponse apiResponse = ApiResponse.ok();
        apiResponse.setData(statisticalAnalysisDTO);
        return apiResponse;
    }

    @Override
    public ApiResponse fullExportByCM (String startTime,String endTime) {
        List<HashMap<String, Long>> hotel = hotelMapper.getCountAndCm(startTime, endTime);
        List<HashMap<String, Long>> commercialBuilding = commercialBuildingDetailMapper.getCountAndCm(startTime, endTime);
        List<HashMap<String, Long>> industrialPark = industrialParkDetailMapper.getCountAndCm(startTime, endTime);
        List<HashMap<String, Long>> shop = shopDetailMapper.getCountAndCm(startTime, endTime);
//        List<String> customerManagers = new ArrayList<>();

        HashMap<String, String> map = new HashMap<>();

        for (HashMap h:hotel) {
            map.put(h.get("customerManager").toString(),h.get("substation").toString());
        }

        for (HashMap h:commercialBuilding) {
            map.put(h.get("customerManager").toString(),h.get("substation").toString());
        }

        for (HashMap h:industrialPark) {
            map.put(h.get("customerManager").toString(),h.get("substation").toString());
        }
        for (HashMap h:shop) {
            map.put(h.get("customerManager").toString(),h.get("substation").toString());
        }

        StatisticalAnalysisDTO statisticalAnalysisDTO = new StatisticalAnalysisDTO();
        List<GetCountDTO> list = new ArrayList<>();
        GetCountDTO getCountDTO = null;
        for (String customerManager:map.keySet()) {
            Long hotelCount = 0L;
            Long buildingCount = 0L;
            Long park = 0L;
            Long shopCount = 0L;
            getCountDTO = new GetCountDTO();
            getCountDTO.setCustomerManager(customerManager);
            getCountDTO.setStartTime(startTime);
            getCountDTO.setEndTime(endTime);
            for (HashMap hashMap:hotel) {
                if (!hashMap.get("customerManager").equals(customerManager)){
                    continue;
                }else {
                    hotelCount = (Long) hashMap.get("count");
                }
            }

            for (HashMap hashMap:commercialBuilding) {
                if (!hashMap.get("customerManager").equals(customerManager)){
                    continue;
                }else {
                    buildingCount = (Long) hashMap.get("count");
                }
            }

            for (HashMap hashMap:industrialPark) {
                if (!hashMap.get("customerManager").equals(customerManager)){
                    continue;
                }else {
                    park = (Long) hashMap.get("count");
                }
            }

            for (HashMap hashMap:shop) {
                if (!hashMap.get("customerManager").equals(customerManager)){
                    continue;
                }else {
                    shopCount = (Long) hashMap.get("count");
                }
            }
            getCountDTO.setSubstation(map.get(customerManager));
            getCountDTO.setHotel(hotelCount.intValue());
            getCountDTO.setBuilding(buildingCount.intValue());
            getCountDTO.setIndustrialPark(park.intValue());
            getCountDTO.setShop(shopCount.intValue());
            getCountDTO.setTotel(hotelCount.intValue()+buildingCount.intValue()+park.intValue()+shopCount.intValue());
            list.add(getCountDTO);
        }
        statisticalAnalysisDTO.setGetCountDTOS(list);
        statisticalAnalysisDTO.setHotelCount(list.stream()
                .map(e -> e.getHotel()).reduce(0, Integer::sum));
        statisticalAnalysisDTO.setBuildingCount(list.stream()
                .map(e -> e.getBuilding()).reduce(0, Integer::sum));
        statisticalAnalysisDTO.setShopCount(list.stream()
                .map(e -> e.getShop()).reduce(0, Integer::sum));
        statisticalAnalysisDTO.setIndustrialParkCount(list.stream()
                .map(e -> e.getIndustrialPark()).reduce(0, Integer::sum));
        statisticalAnalysisDTO.setTotelCount(list.stream()
                .map(e -> e.getTotel()).reduce(0, Integer::sum));
        ApiResponse apiResponse = ApiResponse.ok();
        apiResponse.setData(statisticalAnalysisDTO);
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

    @Override
    public ApiResponse downloadFullExport(HttpServletResponse response,String startTime, String endTime) {
        ApiResponse apiResponse = new ApiResponse();

        try {
            OutputStream outputStream = EasyExcelUtils.getOutputStream("吴中调查情况表-按日期", response);
            ExcelWriter excelWriter = EasyExcelUtils.buildExcelWriter(outputStream);
            int i = 0;
            log.info("查询酒店宾馆数据开始！");
            QueryWrapper<HotelPTO> wrapperHotel = new QueryWrapper<>();
            if (StringUtils.isNotBlank(startTime)){
                wrapperHotel.ge("modify_time",startTime);
            }
            if (StringUtils.isNotBlank(endTime)){
                wrapperHotel.le("modify_time",endTime);
            }
            List<HotelPTO> wrHotel = hotelMapper.selectList(wrapperHotel);
            if (wrHotel.size()>0){

                EasyExcelUtils.writeOnly(excelWriter,wrHotel,HotelPTO.class,i,"酒店宾馆");
                i++;
            }

            log.info("查询商务楼宇数据开始！");
            QueryWrapper<CommercialBuildingDetailPTO> wrapperCommercialBuilding = new QueryWrapper<>();

            if (StringUtils.isNotBlank(startTime)){
                wrapperCommercialBuilding.ge("modify_time",startTime);
            }
            if (StringUtils.isNotBlank(endTime)){
                wrapperCommercialBuilding.le("modify_time",endTime);
            }
            List<CommercialBuildingDetailPTO> wrCommercialBuilding = commercialBuildingDetailMapper.selectList(wrapperCommercialBuilding);
            if (wrCommercialBuilding.size()>0){
                List<Integer> buildingIds = new ArrayList<>();
                for (CommercialBuildingDetailPTO building:wrCommercialBuilding) {
                    buildingIds.add(building.getBuildingId());
                }
                buildingIds = buildingIds.stream().distinct().collect(Collectors.toList());
                List<CommercialBuildingPTO> ptos = commercialBuildingMapper.selectBatchIds(buildingIds);
                for (CommercialBuildingDetailPTO building:wrCommercialBuilding) {
                    Optional<String> any = ptos.stream().filter(o -> o.getId().equals(building.getBuildingId())).map(CommercialBuildingPTO::getHotelName).findAny();
                    building.setOwnerBuilding(any.get());
                }
                EasyExcelUtils.writeOnly(excelWriter,wrCommercialBuilding,CommercialBuildingDetailPTO.class,i,"商务楼宇");
                i++;
            }

            log.info("查询产业园区二级数据开始！");
            QueryWrapper<IndustrialParkDetailPTO> wrapperIndustrialParkDetail = new QueryWrapper<>();
            if (StringUtils.isNotBlank(startTime)){
                wrapperIndustrialParkDetail.ge("modify_time",startTime);
            }
            if (StringUtils.isNotBlank(endTime)){
                wrapperIndustrialParkDetail.le("modify_time",endTime);
            }
            List<IndustrialParkDetailPTO> wrIndustrialParkDetail = industrialParkDetailMapper.selectList(wrapperIndustrialParkDetail);
            if (wrIndustrialParkDetail.size()>0){
                List<Integer> parkIds = new ArrayList<>();
                for (IndustrialParkDetailPTO park:wrIndustrialParkDetail) {
                    parkIds.add(park.getParkId());
                }
                parkIds = parkIds.stream().distinct().collect(Collectors.toList());
                List<IndustrialParkPTO> ptos = industrialParkMapper.selectBatchIds(parkIds);
                for (IndustrialParkDetailPTO park:wrIndustrialParkDetail) {
                    Optional<String> any = ptos.stream().filter(o -> o.getId().equals(park.getParkId())).map(IndustrialParkPTO::getHotelName).findAny();
                    park.setIndustryPark(any.get());
                }
                EasyExcelUtils.writeOnly(excelWriter,wrIndustrialParkDetail,IndustrialParkDetailPTO.class,i,"产业园区");
                i++;
            }

            log.info("查询沿街商铺二级数据开始！");
            QueryWrapper<ShopDetailPTO> wrapperShopDetail = new QueryWrapper<>();

            if (StringUtils.isNotBlank(startTime)){
                wrapperShopDetail.ge("modify_time",startTime);
            }
            if (StringUtils.isNotBlank(endTime)){
                wrapperShopDetail.le("modify_time",endTime);
            }
            List<ShopDetailPTO> wrShopDetail = shopDetailMapper.selectList(wrapperShopDetail);
            if (wrShopDetail.size()>0){
                EasyExcelUtils.writeOnly(excelWriter,wrShopDetail,ShopDetailPTO.class,i,"沿街商铺");
            }
            EasyExcelUtils.finishWriter(outputStream,excelWriter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        apiResponse.setCode(200);
        apiResponse.setMsg("正在导出");
        return apiResponse;
    }

    @Override
    public ApiResponse downloadStatistics(HttpServletResponse response,String startTime, String endTime,String substation) {
        ApiResponse apiResponse = new ApiResponse();

        try {
            OutputStream outputStream = EasyExcelUtils.getOutputStream("吴中调查情况表-按分局", response);
            ExcelWriter excelWriter = EasyExcelUtils.buildExcelWriter(outputStream);
            int i = 0;
            log.info("查询酒店宾馆数据开始！");
            QueryWrapper<HotelPTO> wrapperHotel = new QueryWrapper<>();

                wrapperHotel.ge("modify_time",startTime);
                wrapperHotel.le("modify_time",endTime);
                wrapperHotel.eq("substation",substation);

            List<HotelPTO> wrHotel = hotelMapper.selectList(wrapperHotel);
            if (wrHotel.size()>0){

                EasyExcelUtils.writeOnly(excelWriter,wrHotel,HotelPTO.class,i,"酒店宾馆");
                i++;
            }

            log.info("查询商务楼宇数据开始！");
            QueryWrapper<CommercialBuildingDetailPTO> wrapperCommercialBuilding = new QueryWrapper<>();

            if (StringUtils.isNotBlank(startTime)){
                wrapperCommercialBuilding.ge("modify_time",startTime);
            }
            if (StringUtils.isNotBlank(endTime)){
                wrapperCommercialBuilding.le("modify_time",endTime);
            }
            wrapperCommercialBuilding.eq("substation",substation);
            List<CommercialBuildingDetailPTO> wrCommercialBuilding = commercialBuildingDetailMapper.selectList(wrapperCommercialBuilding);
            if (wrCommercialBuilding.size()>0){
                List<Integer> buildingIds = new ArrayList<>();
                for (CommercialBuildingDetailPTO building:wrCommercialBuilding) {
                    buildingIds.add(building.getBuildingId());
                }
                buildingIds = buildingIds.stream().distinct().collect(Collectors.toList());
                List<CommercialBuildingPTO> ptos = commercialBuildingMapper.selectBatchIds(buildingIds);
                for (CommercialBuildingDetailPTO building:wrCommercialBuilding) {
                    Optional<String> any = ptos.stream().filter(o -> o.getId().equals(building.getBuildingId())).map(CommercialBuildingPTO::getHotelName).findAny();
                    building.setOwnerBuilding(any.get());
                }
                EasyExcelUtils.writeOnly(excelWriter,wrCommercialBuilding,CommercialBuildingDetailPTO.class,i,"商务楼宇");
                i++;
            }

            log.info("查询产业园区二级数据开始！");
            QueryWrapper<IndustrialParkDetailPTO> wrapperIndustrialParkDetail = new QueryWrapper<>();
            if (StringUtils.isNotBlank(startTime)){
                wrapperIndustrialParkDetail.ge("modify_time",startTime);
            }
            if (StringUtils.isNotBlank(endTime)){
                wrapperIndustrialParkDetail.le("modify_time",endTime);
            }
            wrapperIndustrialParkDetail.eq("substation",substation);
            List<IndustrialParkDetailPTO> wrIndustrialParkDetail = industrialParkDetailMapper.selectList(wrapperIndustrialParkDetail);
            if (wrIndustrialParkDetail.size()>0){
                List<Integer> parkIds = new ArrayList<>();
                for (IndustrialParkDetailPTO park:wrIndustrialParkDetail) {
                    parkIds.add(park.getParkId());
                }
                parkIds = parkIds.stream().distinct().collect(Collectors.toList());
                List<IndustrialParkPTO> ptos = industrialParkMapper.selectBatchIds(parkIds);
                for (IndustrialParkDetailPTO park:wrIndustrialParkDetail) {
                    Optional<String> any = ptos.stream().filter(o -> o.getId().equals(park.getParkId())).map(IndustrialParkPTO::getHotelName).findAny();
                    park.setIndustryPark(any.get());
                }
                EasyExcelUtils.writeOnly(excelWriter,wrIndustrialParkDetail,IndustrialParkDetailPTO.class,i,"产业园区");
                i++;
            }

            log.info("查询沿街商铺二级数据开始！");
            QueryWrapper<ShopDetailPTO> wrapperShopDetail = new QueryWrapper<>();

            if (StringUtils.isNotBlank(startTime)){
                wrapperShopDetail.ge("modify_time",startTime);
            }
            if (StringUtils.isNotBlank(endTime)){
                wrapperShopDetail.le("modify_time",endTime);
            }
            wrapperShopDetail.eq("substation",substation);
            List<ShopDetailPTO> wrShopDetail = shopDetailMapper.selectList(wrapperShopDetail);
            if (wrShopDetail.size()>0){
                EasyExcelUtils.writeOnly(excelWriter,wrShopDetail,ShopDetailPTO.class,i,"沿街商铺");
            }
            EasyExcelUtils.finishWriter(outputStream,excelWriter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        apiResponse.setCode(200);
        apiResponse.setMsg("正在导出");
        return apiResponse;
    }

}
