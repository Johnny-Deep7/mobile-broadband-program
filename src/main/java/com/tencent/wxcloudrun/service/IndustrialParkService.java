package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.PageVo;
import com.tencent.wxcloudrun.dto.RequestEntity;
import com.tencent.wxcloudrun.pto.IndustrialParkPTO;
import org.springframework.web.multipart.MultipartFile;

public interface IndustrialParkService {
    void parsingTable(MultipartFile multipartFile);
    public ApiResponse create(IndustrialParkPTO industrialParkPTO);
    public ApiResponse query(PageVo<RequestEntity> pageVo);
    public ApiResponse delete(Integer id);
    public ApiResponse update(IndustrialParkPTO industrialParkPTO);
}
