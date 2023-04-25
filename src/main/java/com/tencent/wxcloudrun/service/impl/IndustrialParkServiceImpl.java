package com.tencent.wxcloudrun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tencent.wxcloudrun.MbpType;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.PageVo;
import com.tencent.wxcloudrun.dto.RequestEntity;
import com.tencent.wxcloudrun.mapper.IndustrialParkMapper;
import com.tencent.wxcloudrun.pto.IndustrialParkPTO;
import com.tencent.wxcloudrun.service.IndustrialParkService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class IndustrialParkServiceImpl implements IndustrialParkService {
    @Autowired
    private IndustrialParkMapper industrialParkMapper;

    @Override
    public ApiResponse create(IndustrialParkPTO industrialParkPTO) {
        ApiResponse apiResponse = ApiResponse.ok();
        Date writeTime = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        industrialParkPTO.setWriteTime(format.format(writeTime));
        industrialParkPTO.setModifyTime(format.format(writeTime));
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
        if (StringUtils.isNotBlank(industrialParkPTO.getHotelType())) {
            wrapper.like("hotel_type", industrialParkPTO.getHotelType());
        }
        wrapper.orderByDesc("id");

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
        BeanUtils.copyProperties(industrialParkPTOPage,requestEntityPage);
        requestEntityPage.setRecords(list);

        apiResponse.setData(requestEntityPage);
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
        Date modifyTime = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        industrialParkPTO.setModifyTime(format.format(modifyTime));
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

    @Override
    public List<IndustrialParkPTO> queryAllNameAndID() {
        QueryWrapper<IndustrialParkPTO> wrapper = new QueryWrapper<>();
        wrapper.select("id", "hotel_name");
        return industrialParkMapper.selectList(wrapper);
    }

    @Transactional
    public ApiResponse asyncSaveInpark(List<IndustrialParkPTO> industrialParkPTOS) {
        log.info("产业园区批量存储开始，存储条数{}",industrialParkPTOS.size());
        ApiResponse apiResponse = ApiResponse.ok();
        try {
            Integer saveCount=industrialParkMapper.insertBatchSomeColumn(industrialParkPTOS);
            if(saveCount<=0){
                apiResponse.setMsg("产业园区保存失败");
                apiResponse.setCode(400);
                return apiResponse;
            }
            apiResponse.setMsg("产业园区保存成功,成功条数：{"+saveCount+"}");
            industrialParkPTOS.clear();
        }catch (Exception e){
            String message = e.getMessage();
            int i = message.indexOf("###",10);
            String substring = message.substring(0, i);
            apiResponse.setMsg(substring);
            apiResponse.setCode(400);
            return apiResponse;
        }
        return apiResponse;

    }
}
