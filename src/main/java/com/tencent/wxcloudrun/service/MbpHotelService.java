package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.RequestEntity;
import com.tencent.wxcloudrun.dto.PageVo;
import com.tencent.wxcloudrun.pto.HotelPTO;
import org.springframework.web.multipart.MultipartFile;

public interface MbpHotelService {

    public ApiResponse create(HotelPTO hotelPTO);
    public ApiResponse query(PageVo<RequestEntity> pageVo);
    public ApiResponse delete(Integer id);
    public ApiResponse update(HotelPTO hotelPTO);
    public ApiResponse queryAllNameAndID();
}
