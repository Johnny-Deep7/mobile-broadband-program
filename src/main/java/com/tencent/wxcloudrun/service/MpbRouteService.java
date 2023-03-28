package com.tencent.wxcloudrun.service;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.CommercialBuildingDTO;
import com.tencent.wxcloudrun.dto.RequestEntity;
import com.tencent.wxcloudrun.dto.PageVo;
import com.tencent.wxcloudrun.pto.CommercialBuildingPTO;
import com.tencent.wxcloudrun.pto.HotelPTO;
import com.tencent.wxcloudrun.pto.IndustrialParkPTO;
import com.tencent.wxcloudrun.service.impl.MbpHotelServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MpbRouteService {

    @Autowired
    private MbpHotelServiceImpl mbpHotelService;
    @Autowired
    private MbpBuildingService mbpBuildingService;
    @Autowired
    private IndustrialParkService industrialParkService;

    private static ApiResponse  apiResponse = ApiResponse.ok();

    public ApiResponse create(RequestEntity requestEntity) {

        if (StringUtils.isBlank(requestEntity.getMarketType())) {
            apiResponse.setCode(400);
            apiResponse.setMsg("场景类型为空，无法新增！");
            return apiResponse;
        }
        switch (requestEntity.getMarketType()){
            case "酒店宾馆" :
                HotelPTO hotelPTO = new HotelPTO();
                BeanUtils.copyProperties(requestEntity,hotelPTO);
                apiResponse = mbpHotelService.create(hotelPTO);
            case "商务楼宇" :
                CommercialBuildingPTO commercialBuildingPTO = new CommercialBuildingPTO();
                BeanUtils.copyProperties(requestEntity,commercialBuildingPTO);
                apiResponse = mbpBuildingService.create(commercialBuildingPTO);
            case "产业园区" :
                IndustrialParkPTO industrialParkPTO = new IndustrialParkPTO();
                BeanUtils.copyProperties(requestEntity,industrialParkPTO);
                apiResponse = industrialParkService.create(industrialParkPTO);
        }
        return apiResponse;
    }

    public ApiResponse query(PageVo<RequestEntity> pageVo) {

        RequestEntity requestEntity = pageVo.getType();
        if (StringUtils.isBlank(requestEntity.getMarketType())) {
            apiResponse.setCode(400);
            apiResponse.setMsg("场景类型为空，无法查询");
            return apiResponse;
        }
        switch (requestEntity.getMarketType()){
            case "酒店宾馆" :
                apiResponse = mbpHotelService.query(pageVo);
            case "商务楼宇" :
                apiResponse = mbpBuildingService.query(pageVo);
            case "产业园区" :
                apiResponse = industrialParkService.query(pageVo);
            default:
                apiResponse.setCode(400);
                apiResponse.setMsg("场景类型不存在，请重新选择");
        }
        return apiResponse;
    }

    public ApiResponse delete(Integer id,String marketType) {
        if (StringUtils.isBlank(marketType)) {
            apiResponse.setCode(400);
            apiResponse.setMsg("场景类型为空，无法删除");
            return apiResponse;
        }
        switch (marketType){
            case "酒店宾馆" :
                apiResponse = mbpHotelService.delete(id);
            case "商务楼宇" :
                apiResponse = mbpBuildingService.delete(id);
            case "产业园区" :
                apiResponse = industrialParkService.delete(id);
        }
        return apiResponse;
    }

    public ApiResponse update(RequestEntity requestEntity) {
        if (StringUtils.isBlank(requestEntity.getMarketType())) {
            apiResponse.setCode(400);
            apiResponse.setMsg("场景类型为空，无法修改");
            return apiResponse;
        }
        switch (requestEntity.getMarketType()){
            case "酒店宾馆" :
                HotelPTO hotelPTO = new HotelPTO();
                BeanUtils.copyProperties(requestEntity,hotelPTO);
                apiResponse = mbpHotelService.update(hotelPTO);
            case "商务楼宇" :
                CommercialBuildingPTO commercialBuildingPTO = new CommercialBuildingPTO();
                BeanUtils.copyProperties(requestEntity,commercialBuildingPTO);
                apiResponse = mbpBuildingService.update(commercialBuildingPTO);
            case "产业园区" :
                IndustrialParkPTO industrialParkPTO = new IndustrialParkPTO();
                BeanUtils.copyProperties(requestEntity,industrialParkPTO);
                apiResponse = industrialParkService.update(industrialParkPTO);
        }
        return apiResponse;
    }
}
