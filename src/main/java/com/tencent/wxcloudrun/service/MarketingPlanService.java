package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.PageVo;
import com.tencent.wxcloudrun.dto.RequestEntity;
import com.tencent.wxcloudrun.pto.IndustrialParkPTO;
import com.tencent.wxcloudrun.pto.MarketingPlanPTO;

public interface MarketingPlanService {
    public ApiResponse create(MarketingPlanPTO marketingPlanPTO);
    public ApiResponse query(PageVo<RequestEntity> pageVo);
    public ApiResponse delete(Integer id);
    public ApiResponse update(MarketingPlanPTO marketingPlanPTO);
    public ApiResponse queryAllNameAndID();
}
