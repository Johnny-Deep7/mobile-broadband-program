package com.tencent.wxcloudrun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.*;
import com.tencent.wxcloudrun.mapper.CommercialBuildingDetailMapper;
import com.tencent.wxcloudrun.pto.*;
import com.tencent.wxcloudrun.service.MbpBuildingDetailService;
import com.tencent.wxcloudrun.utils.CopyListUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        if (commercialBuildingDetail.getBuildingId() == null || commercialBuildingDetail.getBuildingId() == 0){
            apiResponse.setCode(400);
            apiResponse.setMsg("没有传商务大楼的id，商务大楼二级明细查询失败！");
            return apiResponse;
        }
        CommercialBuildingDetailPTO commercialBuildingDetailPTO = new CommercialBuildingDetailPTO();
        BeanUtils.copyProperties(commercialBuildingDetail,commercialBuildingDetailPTO);

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
        wrapper.eq("building_id",commercialBuildingDetailPTO.getBuildingId());

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
            apiResponse.setMsg("更新成功");
        }else{
            apiResponse.setCode(400);
            apiResponse.setMsg("更新失败");
        }
        return apiResponse;
    }

    public ApiResponse updateBuildingDetailList(List<CommercialBuildingDetail> commercialBuildingDetailList){
        List<CommercialBuildingDetailPTO> commercialBuildingDetailPTOList = CopyListUtils.convertList2List(commercialBuildingDetailList, CommercialBuildingDetailPTO.class);
        for(CommercialBuildingDetailPTO commercialBuildingDetailPTO : commercialBuildingDetailPTOList){
            int i = commercialBuildingDetailMapper.updateById(commercialBuildingDetailPTO);
            if(i>0){
                apiResponse.setCode(200);
                apiResponse.setMsg("更新成功");
            }else{
                apiResponse.setCode(400);
                apiResponse.setMsg("更新失败");
                break;
            }
        }
        return apiResponse;
    }

    @Override
    public List<CommercialBuildingDetailPTO> queryAllNameAndID(Integer id) {
        QueryWrapper<CommercialBuildingDetailPTO> wrapper = new QueryWrapper<>();
        wrapper.eq("BUILDING_ID",id);
        wrapper.select("id", "enterprise_name");
        return commercialBuildingDetailMapper.selectList(wrapper);
    }

    @Transactional
    public ApiResponse asyncSaveBuildingDel(List<CommercialBuildingDetailPTO> commercialBuildingDetailPTOS) {
        log.info("商业楼宇批量存储开始，存储条数{}",commercialBuildingDetailPTOS.size());
        ApiResponse apiResponse = ApiResponse.ok();
        try {
            Integer saveCount=commercialBuildingDetailMapper.insertBatchSomeColumn(commercialBuildingDetailPTOS);
            if(saveCount<=0){
                apiResponse.setMsg("商业楼宇保存失败");
                apiResponse.setCode(400);
                return apiResponse;
            }
            apiResponse.setMsg("商业楼宇保存成功,成功条数：{"+saveCount+"}");
            commercialBuildingDetailPTOS.clear();
        }catch (Exception e){
            apiResponse.setMsg(e.getMessage());
            apiResponse.setCode(400);
            return apiResponse;
        }
        return apiResponse;

    }
}
