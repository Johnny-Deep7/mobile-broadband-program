package com.tencent.wxcloudrun.service.impl;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tencent.wxcloudrun.MbpType;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.PageVo;
import com.tencent.wxcloudrun.dto.RequestEntity;
import com.tencent.wxcloudrun.mapper.CommercialBuildingMapper;
import com.tencent.wxcloudrun.pto.CommercialBuildingPTO;
import com.tencent.wxcloudrun.pto.HotelPTO;
import com.tencent.wxcloudrun.pto.IndustrialParkPTO;
import com.tencent.wxcloudrun.service.MbpBuildingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MbpBuildingServiceImpl implements MbpBuildingService {
    @Resource
    private CommercialBuildingMapper commercialBuildingMapper;

    @Override
    public ApiResponse create(CommercialBuildingPTO commercialBuildingPTO) {
        ApiResponse apiResponse = ApiResponse.ok();

        int i = commercialBuildingMapper.insert(commercialBuildingPTO);
        if(i>0){
            apiResponse.setCode(200);
            apiResponse.setMsg("添加成功");
        }else{
            apiResponse.setCode(400);
            apiResponse.setMsg("添加失败");
        }
        return apiResponse;
    }

    @Override
    public ApiResponse query(PageVo<RequestEntity> pageVo) {
        RequestEntity hotelDTO = pageVo.getType();
        CommercialBuildingPTO commercialBuildingPTO = new CommercialBuildingPTO();
        BeanUtils.copyProperties(hotelDTO,commercialBuildingPTO);
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

        QueryWrapper<CommercialBuildingPTO> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(commercialBuildingPTO.getSubstation())){
            wrapper.like("substation",commercialBuildingPTO.getSubstation());
        }
        if (StringUtils.isNotBlank(commercialBuildingPTO.getCustomerManager())){
            wrapper.eq("customer_manager",commercialBuildingPTO.getCustomerManager());
        }
        if (StringUtils.isNotBlank(commercialBuildingPTO.getHotelName())){
            wrapper.like("hotel_name",commercialBuildingPTO.getHotelName());
        }
        if (StringUtils.isNotBlank(commercialBuildingPTO.getType())){
            wrapper.eq("hotel_type",commercialBuildingPTO.getType());
        }

        Page<CommercialBuildingPTO> page = new Page<>();
        page.setCurrent(pageNo);
        page.setSize(pageSize);
        Page<CommercialBuildingPTO> commercialBuildingPTOPage = commercialBuildingMapper.selectPage(page, wrapper);

        Page<RequestEntity> requestEntityPage = new Page<>();
        List<RequestEntity> list = new ArrayList<>();
        RequestEntity entity = null;
        for (CommercialBuildingPTO commercialBuildingPTO1:commercialBuildingPTOPage.getRecords()) {
            entity = new RequestEntity();
            BeanUtils.copyProperties(commercialBuildingPTO1,entity);
            entity.setMarketType(MbpType.COMBUIL.getCode());
            list.add(entity);
        }
        requestEntityPage.setRecords(list);

        apiResponse.setData(requestEntityPage);
        apiResponse.setCode(200);
        apiResponse.setMsg("查询成功");
        return apiResponse;
    }

    @Override
    public ApiResponse delete(Integer id) {
        ApiResponse apiResponse = new ApiResponse();
        int i = commercialBuildingMapper.deleteById(id);
        if(i>0){
            apiResponse.setCode(200);
            apiResponse.setMsg("删除成功");
        }else{
            apiResponse.setCode(400);
            apiResponse.setMsg("删除失败");
        }
        return apiResponse;
    }

    @Override
    public ApiResponse update(CommercialBuildingPTO commercialBuildingPTO) {
        ApiResponse apiResponse = new ApiResponse();

        int i = commercialBuildingMapper.updateById(commercialBuildingPTO);
        if(i>0){
            apiResponse.setCode(200);
            apiResponse.setMsg("修改成功");
        }else{
            apiResponse.setCode(400);
            apiResponse.setMsg("修改失败");
        }
        return apiResponse;
    }

}
