package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.IndustrialParkDetail;
import com.tencent.wxcloudrun.dto.PageVo;
import com.tencent.wxcloudrun.pto.IndustrialParkDetailPTO;
import org.springframework.web.multipart.MultipartFile;

public interface IndustrialParkDetailService {
    public ApiResponse create(IndustrialParkDetailPTO industrialParkDetailPTO);
    public ApiResponse query(PageVo<IndustrialParkDetail> pageVo);
    public ApiResponse delete(Integer id);
    public ApiResponse update(IndustrialParkDetailPTO industrialParkDetailPTO);
}
