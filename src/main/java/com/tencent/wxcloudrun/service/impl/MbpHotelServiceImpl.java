package com.tencent.wxcloudrun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tencent.wxcloudrun.MbpType;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.RequestEntity;
import com.tencent.wxcloudrun.dto.PageVo;
import com.tencent.wxcloudrun.mapper.HotelMapper;
import com.tencent.wxcloudrun.pto.HotelPTO;
import com.tencent.wxcloudrun.service.MbpHotelService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MbpHotelServiceImpl implements MbpHotelService {
    @Resource
    private HotelMapper hotelMapper;

    @Override
    public ApiResponse create(HotelPTO hotelPTO) {
        ApiResponse apiResponse = ApiResponse.ok();

        int i = hotelMapper.insert(hotelPTO);
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
        RequestEntity requestEntity = pageVo.getType();
        HotelPTO hotelPTO = new HotelPTO();
        BeanUtils.copyProperties(requestEntity,hotelPTO);
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

        QueryWrapper<HotelPTO> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(hotelPTO.getSubstation())){
            wrapper.like("substation",hotelPTO.getSubstation());
        }
        if (StringUtils.isNotBlank(hotelPTO.getCustomerManager())){
            wrapper.eq("customer_manager",hotelPTO.getCustomerManager());
        }
        if (StringUtils.isNotBlank(hotelPTO.getHotelName())){
            wrapper.like("hotel_name",hotelPTO.getHotelName());
        }
        if (StringUtils.isNotBlank(hotelPTO.getHotelType())){
            wrapper.eq("hotel_type",hotelPTO.getHotelType());
        }
        wrapper.orderByDesc("id");

        Page<HotelPTO> page = new Page<>();
        page.setCurrent(pageNo);
        page.setSize(pageSize);
        Page<HotelPTO> hotelPTOPage = hotelMapper.selectPage(page, wrapper);
        Page<RequestEntity> requestEntityPage = new Page<>();
        List<RequestEntity> list = new ArrayList<>();
        RequestEntity entity = null;
        for (HotelPTO hotelPTO1:hotelPTOPage.getRecords()) {
            entity = new RequestEntity();
            BeanUtils.copyProperties(hotelPTO1,entity);
            entity.setMarketType(MbpType.HOTEL.getCode());
            list.add(entity);
        }
        BeanUtils.copyProperties(hotelPTOPage,requestEntityPage);
        requestEntityPage.setRecords(list);

        apiResponse.setData(requestEntityPage);
        apiResponse.setCode(200);
        apiResponse.setMsg("查询成功");
        return apiResponse;
    }

    @Override
    public ApiResponse delete(Integer id) {
        ApiResponse apiResponse = new ApiResponse();
        int i = hotelMapper.deleteById(id);
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
    public ApiResponse update(HotelPTO hotelPTO) {
        ApiResponse apiResponse = new ApiResponse();
        int i = hotelMapper.updateById(hotelPTO);
        if(i>0){
            apiResponse.setCode(200);
            apiResponse.setMsg("修改成功");
        }else{
            apiResponse.setCode(400);
            apiResponse.setMsg("修改失败");
        }
        return apiResponse;
    }

    @Override
    public List<HotelPTO> queryAllNameAndID() {
        QueryWrapper<HotelPTO> wrapper = new QueryWrapper<>();
        wrapper.select("id", "hotel_name");
        return hotelMapper.selectList(wrapper);

    }

    @Transactional
    public ApiResponse asyncSaveHotel(List<HotelPTO> hotelPTOS) {
        log.info("酒店宾馆批量存储开始，存储条数{}",hotelPTOS.size());
        ApiResponse apiResponse = ApiResponse.ok();
        try{
            Integer saveCount=hotelMapper.insertBatchSomeColumn(hotelPTOS);
            if(saveCount<=0){
                apiResponse.setMsg("酒店宾馆保存失败");
                apiResponse.setCode(400);
                return apiResponse;
            }
            log.info("酒店宾馆保存成功,成功条数：{"+saveCount+"}");
            hotelPTOS.clear();
        }catch (Exception e){
            apiResponse.setMsg(e.getMessage());
            apiResponse.setCode(400);
            return apiResponse;
        }
        return apiResponse;

    }

}
