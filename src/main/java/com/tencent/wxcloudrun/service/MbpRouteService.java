package com.tencent.wxcloudrun.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.*;
import com.tencent.wxcloudrun.mapper.CommercialBuildingDetailMapper;
import com.tencent.wxcloudrun.mapper.ShopDetailMapper;
import com.tencent.wxcloudrun.mapper.ShopMapper;
import com.tencent.wxcloudrun.pto.*;
import com.tencent.wxcloudrun.service.impl.MbpHotelServiceImpl;
import com.tencent.wxcloudrun.utils.CopyListUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MbpRouteService {

    @Autowired
    private MbpHotelServiceImpl mbpHotelService;
    @Autowired
    private MbpBuildingService mbpBuildingService;
    @Autowired
    private IndustrialParkService industrialParkService;
    @Autowired
    CommercialBuildingDetailMapper commercialBuildingDetailMapper;
    @Autowired
    ShopMapper shopMapper;
    @Autowired
    ShopDetailMapper shopDetailMapper;

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
                break;
            case "商务楼宇" :
                CommercialBuildingPTO commercialBuildingPTO = new CommercialBuildingPTO();
                BeanUtils.copyProperties(requestEntity,commercialBuildingPTO);
                apiResponse = mbpBuildingService.create(commercialBuildingPTO);
                break;
            case "产业园区" :
                IndustrialParkPTO industrialParkPTO = new IndustrialParkPTO();
                BeanUtils.copyProperties(requestEntity,industrialParkPTO);
                apiResponse = industrialParkService.create(industrialParkPTO);
                break;
        }
        return apiResponse;
    }

    public ApiResponse query(PageVo<RequestEntity> pageVo) {

        String marketType = pageVo.getType().getMarketType();
        if (StringUtils.isBlank(marketType)) {
            apiResponse.setCode(400);
            apiResponse.setMsg("场景类型为空，无法查询");
            return apiResponse;
        }
        switch (marketType){
            case "酒店宾馆" :
                apiResponse = mbpHotelService.query(pageVo);
                break;
            case "商务楼宇" :
                apiResponse = mbpBuildingService.query(pageVo);
                break;
            case "产业园区" :
                apiResponse = industrialParkService.query(pageVo);
                break;
            default:
                apiResponse.setCode(400);
                apiResponse.setMsg("场景类型不存在，请重新选择");
                break;
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
                break;
            case "商务楼宇" :
                apiResponse = mbpBuildingService.delete(id);
                break;
            case "产业园区" :
                apiResponse = industrialParkService.delete(id);
                break;
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
                break;
            case "商务楼宇" :
                CommercialBuildingPTO commercialBuildingPTO = new CommercialBuildingPTO();
                BeanUtils.copyProperties(requestEntity,commercialBuildingPTO);
                apiResponse = mbpBuildingService.update(commercialBuildingPTO);
                break;
            case "产业园区" :
                IndustrialParkPTO industrialParkPTO = new IndustrialParkPTO();
                BeanUtils.copyProperties(requestEntity,industrialParkPTO);
                apiResponse = industrialParkService.update(industrialParkPTO);
                break;
        }
        return apiResponse;
    }

    public ApiResponse listUpdate(List<RequestEntity> requestEntity) {
        if (StringUtils.isBlank(requestEntity.get(0).getMarketType())) {
            apiResponse.setCode(400);
            apiResponse.setMsg("场景类型为空，无法修改");
            return apiResponse;
        }
        switch (requestEntity.get(0).getMarketType()){
            case "酒店宾馆" :
                List<HotelPTO> hotelPTOList = CopyListUtils.convertList2List(requestEntity, HotelPTO.class);
                for(HotelPTO hotelPTO : hotelPTOList){
                    apiResponse = mbpHotelService.update(hotelPTO);
                    if(200 != apiResponse.getCode()){
                        break;
                    }
                }
                break;
            case "商务楼宇" :
                List<CommercialBuildingPTO> commercialBuildingPTOList = CopyListUtils.convertList2List(requestEntity, CommercialBuildingPTO.class);
                for(CommercialBuildingPTO commercialBuildingPTO : commercialBuildingPTOList){
                    apiResponse = mbpBuildingService.update(commercialBuildingPTO);
                    if(200 != apiResponse.getCode()){
                        break;
                    }
                }
                break;
            case "产业园区" :
                List<IndustrialParkPTO> industrialParkPTOList = CopyListUtils.convertList2List(requestEntity, IndustrialParkPTO.class);
                for(IndustrialParkPTO industrialParkPTO : industrialParkPTOList){
                    apiResponse = industrialParkService.update(industrialParkPTO);
                    if(200 != apiResponse.getCode()){
                        break;
                    }
                }
                break;
        }
        return apiResponse;
    }

}
