package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.dto.HotelDTO;
import org.springframework.web.multipart.MultipartFile;

public interface MbpService {

    void parsingTable(MultipartFile file);
    public void create(HotelDTO hotelDTO);
    public void listHotel(int id);
    public void delete(HotelDTO hotelDTO);
    public void update(HotelDTO hotelDTO);
}
