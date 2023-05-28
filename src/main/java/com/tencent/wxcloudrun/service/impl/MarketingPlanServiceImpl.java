package com.tencent.wxcloudrun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.MarketingPlanDTO;
import com.tencent.wxcloudrun.dto.PageVo;
import com.tencent.wxcloudrun.mapper.MarketingPlanMapper;
import com.tencent.wxcloudrun.pto.MarketingPlanPTO;
import com.tencent.wxcloudrun.service.MarketingPlanService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class MarketingPlanServiceImpl implements MarketingPlanService {
    @Autowired
    private MarketingPlanMapper marketingPlanMapper;
    @Override
    public ApiResponse create(MarketingPlanPTO marketingPlanPTO) {
        ApiResponse apiResponse = ApiResponse.ok();
        QueryWrapper<MarketingPlanPTO> wrapper = new QueryWrapper<>();
        wrapper.eq("name",marketingPlanPTO.getName());
        Long count = marketingPlanMapper.selectCount(wrapper);
        if(0 != count){
            apiResponse.setCode(400);
            apiResponse.setMsg("营销计划名称重复");
            return apiResponse;
        }
        int i = marketingPlanMapper.insert(marketingPlanPTO);
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
    public ApiResponse query(PageVo<MarketingPlanDTO> pageVo) {
        MarketingPlanDTO marketingPlanDTO = pageVo.getType();
        MarketingPlanPTO marketingPlanPTO = new MarketingPlanPTO();
        BeanUtils.copyProperties(marketingPlanDTO,marketingPlanPTO);
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

        QueryWrapper<MarketingPlanPTO> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(marketingPlanPTO.getCustomerManager())){
            wrapper.eq("customer_manager",marketingPlanPTO.getCustomerManager());
        }
        if (StringUtils.isNotBlank(marketingPlanPTO.getName())){
            wrapper.eq("name",marketingPlanPTO.getName());
        }
        wrapper.orderByDesc("id");

        Page<MarketingPlanPTO> page = new Page<>();
        page.setCurrent(pageNo);
        page.setSize(pageSize);
        Page<MarketingPlanPTO> marketingPlanPTOPage = marketingPlanMapper.selectPage(page, wrapper);

        apiResponse.setData(marketingPlanPTOPage);
        apiResponse.setCode(200);
        apiResponse.setMsg("查询成功");
        return apiResponse;
    }

    @Override
    public ApiResponse delete(Integer id) {
        ApiResponse apiResponse = new ApiResponse();
        int i = marketingPlanMapper.deleteById(id);
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
    public ApiResponse update(MarketingPlanPTO marketingPlanPTO) {
        ApiResponse apiResponse = new ApiResponse();
        int i = marketingPlanMapper.updateById(marketingPlanPTO);
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
    public List<MarketingPlanPTO> queryAllNameAndID() {
        QueryWrapper<MarketingPlanPTO> wrapper = new QueryWrapper<>();
        wrapper.select("id", "name");
        return marketingPlanMapper.selectList(wrapper);
    }

    @SneakyThrows(Exception.class)
    @Transactional
    public void asyncSaveHotel(List<MarketingPlanPTO> marketingPlanPTO) {
        log.info("营销计划批量存储开始，存储条数{}",marketingPlanPTO.size());
        Integer saveCount=marketingPlanMapper.insertBatchSomeColumn(marketingPlanPTO);
        if(saveCount<0){
            log.error("营销计划保存失败");
            return;
        }
        log.info("营销计划保存成功,成功条数：{},批次标记:{}",saveCount);
        marketingPlanPTO.clear();
    }
}
