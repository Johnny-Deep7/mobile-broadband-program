package com.tencent.wxcloudrun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.IndustrialParkDetail;
import com.tencent.wxcloudrun.dto.PageVo;
import com.tencent.wxcloudrun.mapper.IndustrialParkDetailMapper;
import com.tencent.wxcloudrun.pto.CommercialBuildingPTO;
import com.tencent.wxcloudrun.pto.IndustrialParkDetailPTO;
import com.tencent.wxcloudrun.pto.IndustrialParkPTO;
import com.tencent.wxcloudrun.service.IndustrialParkDetailService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Slf4j
public class IndustrialParkDetailServiceImpl implements IndustrialParkDetailService {
    @Autowired
    private IndustrialParkDetailMapper industrialParkDetailMapper;

    @Override
    public ApiResponse create(IndustrialParkDetailPTO industrialParkDetailPTO) {
        ApiResponse apiResponse = ApiResponse.ok();
        int i = industrialParkDetailMapper.insert(industrialParkDetailPTO);
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
    public ApiResponse query(PageVo<IndustrialParkDetail> pageVo) {
        IndustrialParkDetailPTO industrialParkDetailPTO = new IndustrialParkDetailPTO();
        IndustrialParkDetail industrialParkDetail = pageVo.getType();
        BeanUtils.copyProperties(industrialParkDetail, industrialParkDetailPTO);
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

        QueryWrapper<IndustrialParkDetailPTO> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(industrialParkDetailPTO.getSubstation())) {
            wrapper.like("substation", industrialParkDetailPTO.getSubstation());
        }
        if (StringUtils.isNotBlank(industrialParkDetailPTO.getCustomerManager())) {
            wrapper.eq("customerManager", industrialParkDetailPTO.getCustomerManager());
        }
        if (StringUtils.isNotBlank(industrialParkDetailPTO.getIndustryPark())) {
            wrapper.like("industryPark", industrialParkDetailPTO.getIndustryPark());
        }
        if (StringUtils.isNotBlank(industrialParkDetailPTO.getEnterpriseName())) {
            wrapper.eq("enterpriseName", industrialParkDetailPTO.getEnterpriseName());
        }

        Page<IndustrialParkDetailPTO> page = new Page<>();
        page.setCurrent(pageNo);
        page.setSize(pageSize);
        Page<IndustrialParkDetailPTO> industrialParkPTOPage = industrialParkDetailMapper.selectPage(page, wrapper);
        apiResponse.setData(industrialParkPTOPage);
        apiResponse.setCode(200);
        apiResponse.setMsg("查询成功");
        return apiResponse;
    }

    @Override
    public ApiResponse delete(Integer id) {
        ApiResponse apiResponse = new ApiResponse();
        int i = industrialParkDetailMapper.deleteById(id);
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
    public ApiResponse update(IndustrialParkDetailPTO industrialParkDetailPTO) {
        ApiResponse apiResponse = new ApiResponse();
        int i = industrialParkDetailMapper.updateById(industrialParkDetailPTO);
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
    public void asyncSaveBuilding(List<IndustrialParkDetailPTO> industrialParkDetailPTOS) {
        log.info("产业园区二级明细批量存储开始，存储条数{}",industrialParkDetailPTOS.size());
        Integer saveCount=industrialParkDetailMapper.insertBatchSomeColumn(industrialParkDetailPTOS);
        if(saveCount<0){
            log.error("产业园区二级明细保存失败");
            return;
        }
        log.info("产业园区二级明细保存成功,成功条数：{},批次标记:{}",saveCount);
        industrialParkDetailPTOS.clear();
    }
}
