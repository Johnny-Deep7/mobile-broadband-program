package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.HotelDTO;
import com.tencent.wxcloudrun.dto.PageVo;
import com.tencent.wxcloudrun.pto.HotelPTO;
import org.springframework.web.multipart.MultipartFile;

public interface MbpHotelService {

    void parsingTable(MultipartFile multipartFile);
    public ApiResponse create(HotelPTO hotelPTO);
    public ApiResponse query(PageVo<HotelDTO> pageVo);
    public ApiResponse delete(Integer id);
    public ApiResponse update(HotelPTO hotelPTO);
}
