package com.tencent.wxcloudrun.mapper;

import com.tencent.wxcloudrun.pto.IndustrialParkPTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IndustrialParkMapper {

    int insert(IndustrialParkPTO industrialParkPTO);

    int deleteById(int id);

    IndustrialParkPTO listIndustrialPark(IndustrialParkPTO industrialParkPTO);
}
