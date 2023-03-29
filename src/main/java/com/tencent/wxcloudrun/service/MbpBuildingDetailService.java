package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.CommercialBuildingDetail;
import com.tencent.wxcloudrun.dto.PageVo;

public interface MbpBuildingDetailService {
    public ApiResponse createBuildingDetail(CommercialBuildingDetail commercialBuildingDetail);

    public ApiResponse queryBuildingDetail(PageVo<CommercialBuildingDetail> pageVo);

    public ApiResponse deleteBuildingDetail(Integer id);

    public ApiResponse updateBuildingDetail(CommercialBuildingDetail commercialBuildingDetail);
}
