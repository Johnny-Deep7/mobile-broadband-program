package com.tencent.wxcloudrun.mapper;

import com.tencent.wxcloudrun.pto.ShopDetailPTO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface ShopDetailMapper extends EasyBaseMapper<ShopDetailPTO> {

    List<HashMap<String,Long>> getCount(@Param("startTime")String startTime, @Param("endTime")String endTime);

    List<HashMap<String,Long>> getCountBySubstation(@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("substation")String substation);
}
