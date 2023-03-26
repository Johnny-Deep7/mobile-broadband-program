package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.HotelDTO;
import org.springframework.web.multipart.MultipartFile;

public interface MbpService {

    void parsingTable(MultipartFile file);
    public ApiResponse create(HotelDTO hotelDTO);
    public ApiResponse listHotel(HotelDTO hotelDTO);
    public ApiResponse delete(HotelDTO hotelDTO);
    public ApiResponse update(HotelDTO hotelDTO);
}
