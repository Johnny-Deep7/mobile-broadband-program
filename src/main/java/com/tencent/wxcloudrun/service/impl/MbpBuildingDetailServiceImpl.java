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
import com.tencent.wxcloudrun.service.MbpBuildingDetailService;
import com.tencent.wxcloudrun.service.MbpBuildingService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class MbpBuildingDetailServiceImpl implements MbpBuildingDetailService {
    @Autowired
    private CommercialBuildingDetailMapper commercialBuildingDetailMapper;

    private static ApiResponse  apiResponse = ApiResponse.ok();

    @Override
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

    @Override
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
            wrapper.eq("customer_manager",commercialBuildingDetailPTO.getCustomerManager());
        }
        if (StringUtils.isNotBlank(commercialBuildingDetailPTO.getOwnerBuilding())){
            wrapper.like("owner_building",commercialBuildingDetailPTO.getOwnerBuilding());
        }
        if (StringUtils.isNotBlank(commercialBuildingDetailPTO.getEnterpriseName())){
            wrapper.like("enterprise_name",commercialBuildingDetailPTO.getEnterpriseName());
        }
        wrapper.orderByDesc("id");
        Page<CommercialBuildingDetailPTO> page = new Page<>();
        page.setCurrent(pageNo);
        page.setSize(pageSize);
        Page<CommercialBuildingDetailPTO> commercialBuildingDetailPage = commercialBuildingDetailMapper.selectPage(page, wrapper);

        apiResponse.setData(commercialBuildingDetailPage);
        apiResponse.setCode(200);
        apiResponse.setMsg("查询成功");
        return apiResponse;
    }

    @Override
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

    @Override
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

    @SneakyThrows(Exception.class)
    @Transactional
    public void asyncSaveBuilding(List<CommercialBuildingDetailPTO> commercialBuildingDetailPTOS) {
        log.info("产业园区批量存储开始，存储条数{}",commercialBuildingDetailPTOS.size());
        Integer saveCount=commercialBuildingDetailMapper.insertBatchSomeColumn(commercialBuildingDetailPTOS);
        if(saveCount<0){
            log.error("产业园区保存失败");
            return;
        }
        log.info("产业园区保存成功,成功条数：{},批次标记:{}",saveCount);
        commercialBuildingDetailPTOS.clear();
    }
}
