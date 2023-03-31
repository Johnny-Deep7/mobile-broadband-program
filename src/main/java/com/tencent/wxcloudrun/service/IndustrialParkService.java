package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.PageVo;
import com.tencent.wxcloudrun.dto.RequestEntity;
import com.tencent.wxcloudrun.pto.IndustrialParkPTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IndustrialParkService {
    public ApiResponse create(IndustrialParkPTO industrialParkPTO);
    public ApiResponse query(PageVo<RequestEntity> pageVo);
    public ApiResponse delete(Integer id);
    public ApiResponse update(IndustrialParkPTO industrialParkPTO);
    public List<IndustrialParkPTO> queryAllNameAndID();
}
