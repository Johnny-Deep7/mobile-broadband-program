package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.PageVo;
import com.tencent.wxcloudrun.dto.RequestEntity;
import com.tencent.wxcloudrun.pto.CommercialBuildingPTO;
import org.springframework.web.multipart.MultipartFile;

public interface MbpBuildingService {
    public ApiResponse create(CommercialBuildingPTO commercialBuildingPTO);
    public ApiResponse query(PageVo<RequestEntity> pageVo);
    public ApiResponse delete(Integer id);
    public ApiResponse update(CommercialBuildingPTO commercialBuildingPTO);
    public ApiResponse queryAllNameAndID();
}
