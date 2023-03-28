package com.tencent.wxcloudrun.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.CommercialBuildingDTO;
import com.tencent.wxcloudrun.dto.CommercialBuildingDetail;
import com.tencent.wxcloudrun.dto.RequestEntity;
import com.tencent.wxcloudrun.dto.PageVo;
import com.tencent.wxcloudrun.mapper.CommercialBuildingDetailMapper;
import com.tencent.wxcloudrun.pto.CommercialBuildingDetailPTO;
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
    @Autowired
    CommercialBuildingDetailMapper commercialBuildingDetailMapper;

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

    public ApiResponse createBuildingDetail(CommercialBuildingDetail commercialBuildingDetail) {
        CommercialBuildingDetailPTO commercialBuildingDetailPTO = new CommercialBuildingDetailPTO();
        BeanUtils.copyProperties(commercialBuildingDetail,commercialBuildingDetailPTO);
        int i = commercialBuildingDetailMapper.insert(commercialBuildingDetailPTO);
        if(i>0){
            apiResponse.setCode(200);
            apiResponse.setMsg("添加成功");
        }else{
            apiResponse.setCode(400);
            apiResponse.setMsg("添加失败");
        }
        return apiResponse;
    }

    public ApiResponse queryBuildingDetail(PageVo<CommercialBuildingDetail> pageVo) {
        CommercialBuildingDetail commercialBuildingDetail = pageVo.getType();
        CommercialBuildingDetailPTO commercialBuildingDetailPTO = new CommercialBuildingDetailPTO();
        BeanUtils.copyProperties(commercialBuildingDetail,commercialBuildingDetailPTO);
        ApiResponse apiResponse = new ApiResponse();

        int pageNo = 0;
        int pageSize = 0;
        if (pageVo.getPage() == 0) {
            pageNo = 1;
        } else {
            pageNo = pageVo.getPage();
        }
        if (pageVo.getSize() == 0) {
            pageSize = 10;
        } else {
            pageSize = pageVo.getSize();
        }

        QueryWrapper<CommercialBuildingDetailPTO> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(commercialBuildingDetailPTO.getSubstation())){
            wrapper.like("substation",commercialBuildingDetailPTO.getSubstation());
        }
        if (StringUtils.isNotBlank(commercialBuildingDetailPTO.getCustomerManager())){
            wrapper.eq("customerManager",commercialBuildingDetailPTO.getCustomerManager());
        }
        if (StringUtils.isNotBlank(commercialBuildingDetailPTO.getOwnerBuilding())){
            wrapper.like("ownerBuilding",commercialBuildingDetailPTO.getOwnerBuilding());
        }
        if (StringUtils.isNotBlank(commercialBuildingDetailPTO.getEnterpriseName())){
            wrapper.like("enterpriseName",commercialBuildingDetailPTO.getEnterpriseName());
        }

        Page<CommercialBuildingDetailPTO> page = new Page<>();
        page.setCurrent(pageNo);
        page.setSize(pageSize);
        Page<CommercialBuildingDetailPTO> commercialBuildingDetailPage = commercialBuildingDetailMapper.selectPage(page, wrapper);

        apiResponse.setData(commercialBuildingDetailPage);
        apiResponse.setCode(200);
        apiResponse.setMsg("查询成功");
        return apiResponse;
    }

    public ApiResponse deleteBuildingDetail(Integer id) {
        int i = commercialBuildingDetailMapper.deleteById(id);
        if(i>0){
            apiResponse.setCode(200);
            apiResponse.setMsg("删除成功");
        }else{
            apiResponse.setCode(400);
            apiResponse.setMsg("删除失败");
        }
        return apiResponse;
    }

    public ApiResponse updateBuildingDetail(CommercialBuildingDetail commercialBuildingDetail) {
        CommercialBuildingDetailPTO commercialBuildingDetailPTO = new CommercialBuildingDetailPTO();
        BeanUtils.copyProperties(commercialBuildingDetail,commercialBuildingDetailPTO);
        int i = commercialBuildingDetailMapper.updateById(commercialBuildingDetailPTO);
        if(i>0){
            apiResponse.setCode(200);
            apiResponse.setMsg("添加成功");
        }else{
            apiResponse.setCode(400);
            apiResponse.setMsg("添加失败");
        }
        return apiResponse;
    }
}
