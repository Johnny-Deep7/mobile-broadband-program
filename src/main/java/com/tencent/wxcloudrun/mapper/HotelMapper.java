package com.tencent.wxcloudrun.mapper;

import com.tencent.wxcloudrun.pto.HotelPTO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface HotelMapper extends EasyBaseMapper<HotelPTO> {

//    int update(HotelPTO hotelPTO);

    List<HashMap<String,Long>> getCount(@Param("startTime")String startTime, @Param("endTime")String endTime);

    List<HashMap<String,Long>> getCountBySubstation(@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("substation")String substation);
}
