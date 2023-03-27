package com.tencent.wxcloudrun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tencent.wxcloudrun.pto.HotelPTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HotelMapper extends BaseMapper<HotelPTO> {

    int insert(HotelPTO hotelPTO);

    int deleteById(int id);

    HotelPTO listHotel(HotelPTO hotelPTO);

    int update(HotelPTO hotelPTO);
}
