package com.tencent.wxcloudrun.service;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.RequestEntity;
import com.tencent.wxcloudrun.dto.PageVo;
import com.tencent.wxcloudrun.pto.HotelPTO;
import com.tencent.wxcloudrun.service.impl.MbpHotelServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MpbRouteService {

    @Autowired
    private MbpHotelServiceImpl mbpHotelService;

    private static ApiResponse  apiResponse = ApiResponse.ok();

    public ApiResponse create(RequestEntity requestEntity) {

        if (StringUtils.isBlank(requestEntity.getMarketType())) {
            apiResponse.setCode(400);
            apiResponse.setMsg("场景类型为空，无法新增！");
            return apiResponse;
        }
        HotelPTO hotelPTO = new HotelPTO();
        BeanUtils.copyProperties(requestEntity,hotelPTO);
        switch (requestEntity.getMarketType()){
            case "酒店宾馆" :
                apiResponse = mbpHotelService.create(hotelPTO);
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
        }
        return apiResponse;
    }

    public ApiResponse update(RequestEntity requestEntity) {
        if (StringUtils.isBlank(requestEntity.getMarketType())) {
            apiResponse.setCode(400);
            apiResponse.setMsg("场景类型为空，无法修改");
            return apiResponse;
        }
        HotelPTO hotelPTO = new HotelPTO();
        BeanUtils.copyProperties(requestEntity,hotelPTO);
        switch (requestEntity.getMarketType()){
            case "酒店宾馆" :
                apiResponse = mbpHotelService.update(hotelPTO);
        }
        return apiResponse;
    }
}
