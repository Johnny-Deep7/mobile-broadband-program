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
import com.tencent.wxcloudrun.mapper.IndustrialParkMapper;
import com.tencent.wxcloudrun.pto.HotelPTO;
import com.tencent.wxcloudrun.pto.IndustrialParkDetailPTO;
import com.tencent.wxcloudrun.pto.IndustrialParkPTO;
import com.tencent.wxcloudrun.service.IndustrialParkService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class IndustrialParkServiceImpl implements IndustrialParkService {
    @Autowired
    private IndustrialParkMapper industrialParkMapper;

    @Override
    public ApiResponse create(IndustrialParkPTO industrialParkPTO) {
        ApiResponse apiResponse = ApiResponse.ok();
        int i = industrialParkMapper.insert(industrialParkPTO);
        if (i > 0) {
            apiResponse.setCode(200);
            apiResponse.setMsg("添加成功");
        } else {
            apiResponse.setCode(400);
            apiResponse.setMsg("添加失败");
        }
        return apiResponse;
    }

    @Override
    public ApiResponse query(PageVo<RequestEntity> pageVo) {
        IndustrialParkPTO industrialParkPTO = new IndustrialParkPTO();
        RequestEntity hotelDTO = pageVo.getType();
        BeanUtils.copyProperties(hotelDTO, industrialParkPTO);
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

        QueryWrapper<IndustrialParkPTO> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(industrialParkPTO.getSubstation())) {
            wrapper.like("substation", industrialParkPTO.getSubstation());
        }
        if (StringUtils.isNotBlank(industrialParkPTO.getCustomerManager())) {
            wrapper.eq("customer_manager", industrialParkPTO.getCustomerManager());
        }
        if (StringUtils.isNotBlank(industrialParkPTO.getHotelName())) {
            wrapper.like("hotel_name", industrialParkPTO.getHotelName());
        }
        if (StringUtils.isNotBlank(industrialParkPTO.getType())) {
            wrapper.eq("hotel_type", industrialParkPTO.getType());
        }

        Page<IndustrialParkPTO> page = new Page<>();
        page.setCurrent(pageNo);
        page.setSize(pageSize);
        Page<IndustrialParkPTO> industrialParkPTOPage = industrialParkMapper.selectPage(page, wrapper);

        Page<RequestEntity> requestEntityPage = new Page<>();
        List<RequestEntity> list = new ArrayList<>();
        RequestEntity entity = null;
        for (IndustrialParkPTO industrialParkPTO1:industrialParkPTOPage.getRecords()) {
            entity = new RequestEntity();
            BeanUtils.copyProperties(industrialParkPTO1,entity);
            entity.setMarketType(MbpType.INDUPARK.getCode());
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
        int i = industrialParkMapper.deleteById(id);
        if (i > 0) {
            apiResponse.setCode(200);
            apiResponse.setMsg("删除成功");
        } else {
            apiResponse.setCode(400);
            apiResponse.setMsg("删除失败");
        }
        return apiResponse;
    }

    @Override
    public ApiResponse update(IndustrialParkPTO industrialParkPTO) {
        ApiResponse apiResponse = new ApiResponse();

        int i = industrialParkMapper.updateById(industrialParkPTO);
        if (i > 0) {
            apiResponse.setCode(200);
            apiResponse.setMsg("修改成功");
        } else {
            apiResponse.setCode(400);
            apiResponse.setMsg("修改失败");
        }
        return apiResponse;
    }

    @SneakyThrows(Exception.class)
    @Transactional
    public void asyncSaveBuilding(List<IndustrialParkPTO> industrialParkPTOS) {
        log.info("产业园区批量存储开始，存储条数{}",industrialParkPTOS.size());
        Integer saveCount=industrialParkMapper.insertBatchSomeColumn(industrialParkPTOS);
        if(saveCount<0){
            log.error("产业园区保存失败");
            return;
        }
        log.info("产业园区保存成功,成功条数：{},批次标记:{}",saveCount);
        industrialParkPTOS.clear();
    }
}
