package com.tencent.wxcloudrun.mapper;

import com.tencent.wxcloudrun.pto.HotelPTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HotelMapper {

    int insert(HotelPTO hotelPTO);

    int deleteById(int id);

    HotelPTO listHotel(HotelPTO hotelPTO);

    int update(HotelPTO hotelPTO);
}
