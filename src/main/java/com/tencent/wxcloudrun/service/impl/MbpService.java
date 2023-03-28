package com.tencent.wxcloudrun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.*;
import com.tencent.wxcloudrun.mapper.CommercialBuildingDetailMapper;
import com.tencent.wxcloudrun.mapper.ShopDetailMapper;
import com.tencent.wxcloudrun.mapper.ShopMapper;
import com.tencent.wxcloudrun.pto.*;
import com.tencent.wxcloudrun.service.IndustrialParkService;
import com.tencent.wxcloudrun.service.MbpBuildingService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class MbpService {
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

    public ApiResponse createShop(ShopDTO shopDTO) {
        ShopPTO shopPTO = new ShopPTO();
        BeanUtils.copyProperties(shopDTO,shopPTO);
        int i = shopMapper.insert(shopPTO);
        if(i>0){
            apiResponse.setCode(200);
            apiResponse.setMsg("添加成功");
        }else{
            apiResponse.setCode(400);
            apiResponse.setMsg("添加失败");
        }
        return apiResponse;
    }

    public ApiResponse queryShop(PageVo<ShopDTO> pageVo) {
        ShopDTO shopDTO = pageVo.getType();
        ShopPTO shopPTO = new ShopPTO();
        BeanUtils.copyProperties(shopDTO,shopPTO);
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

        QueryWrapper<ShopPTO> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(shopPTO.getSubstation())){
            wrapper.like("substation",shopPTO.getSubstation());
        }
        if (StringUtils.isNotBlank(shopPTO.getGimsPoint())){
            wrapper.eq("gimsPoint",shopPTO.getGimsPoint());
        }
        if (StringUtils.isNotBlank(shopPTO.getCellName())){
            wrapper.like("cellName",shopPTO.getCellName());
        }
        if (StringUtils.isNotBlank(shopPTO.getAddress())){
            wrapper.like("address",shopPTO.getAddress());
        }

        Page<ShopPTO> page = new Page<>();
        page.setCurrent(pageNo);
        page.setSize(pageSize);
        Page<ShopPTO> shopPage = shopMapper.selectPage(page, wrapper);

        apiResponse.setData(shopPage);
        apiResponse.setCode(200);
        apiResponse.setMsg("查询成功");
        return apiResponse;
    }

    public ApiResponse deleteShop(Integer id) {
        int i = shopMapper.deleteById(id);
        if(i>0){
            apiResponse.setCode(200);
            apiResponse.setMsg("删除成功");
        }else{
            apiResponse.setCode(400);
            apiResponse.setMsg("删除失败");
        }
        return apiResponse;
    }

    public ApiResponse updateShop(ShopDTO shopDTO) {
        ShopPTO shopPTO = new ShopPTO();
        BeanUtils.copyProperties(shopDTO,shopPTO);
        int i = shopMapper.updateById(shopPTO);
        if(i>0){
            apiResponse.setCode(200);
            apiResponse.setMsg("添加成功");
        }else{
            apiResponse.setCode(400);
            apiResponse.setMsg("添加失败");
        }
        return apiResponse;
    }

    public ApiResponse createShopDetail(ShopDetail shopDetail) {
        ShopDetailPTO shopDetailPTO = new ShopDetailPTO();
        BeanUtils.copyProperties(shopDetail, shopDetailPTO);
        int i = shopDetailMapper.insert(shopDetailPTO);
        if(i>0){
            apiResponse.setCode(200);
            apiResponse.setMsg("添加成功");
        }else{
            apiResponse.setCode(400);
            apiResponse.setMsg("添加失败");
        }
        return apiResponse;
    }

    public ApiResponse queryShopDetail(PageVo<ShopDetail> pageVo) {
        ShopDetail shopDetail = pageVo.getType();
        ShopDetailPTO shopDetailPTO = new ShopDetailPTO();
        BeanUtils.copyProperties(shopDetail, shopDetailPTO);
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

        QueryWrapper<ShopDetailPTO> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(shopDetailPTO.getSubstation())){
            wrapper.like("substation",shopDetailPTO.getSubstation());
        }
        if (StringUtils.isNotBlank(shopDetailPTO.getCustomerManager())){
            wrapper.eq("customerManager",shopDetailPTO.getCustomerManager());
        }
        if (StringUtils.isNotBlank(shopDetailPTO.getStreetBelong())){
            wrapper.like("streetBelong",shopDetailPTO.getStreetBelong());
        }
        if (StringUtils.isNotBlank(shopDetailPTO.getHouseNumber())){
            wrapper.like("houseNumber",shopDetailPTO.getHouseNumber());
        }

        Page<ShopDetailPTO> page = new Page<>();
        page.setCurrent(pageNo);
        page.setSize(pageSize);
        Page<ShopDetailPTO> shopDetailPTOPage = shopDetailMapper.selectPage(page, wrapper);

        apiResponse.setData(shopDetailPTOPage);
        apiResponse.setCode(200);
        apiResponse.setMsg("查询成功");
        return apiResponse;
    }

    public ApiResponse deleteShopDetail(Integer id) {
        int i = shopDetailMapper.deleteById(id);
        if(i>0){
            apiResponse.setCode(200);
            apiResponse.setMsg("删除成功");
        }else{
            apiResponse.setCode(400);
            apiResponse.setMsg("删除失败");
        }
        return apiResponse;
    }

    public ApiResponse updateShopDetail(ShopDetail shopDetail) {
        ShopDetailPTO shopDetailPTO = new ShopDetailPTO();
        BeanUtils.copyProperties(shopDetail, shopDetailPTO);
        int i = shopDetailMapper.updateById(shopDetailPTO);
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
