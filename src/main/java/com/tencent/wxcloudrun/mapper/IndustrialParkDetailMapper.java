package com.tencent.wxcloudrun.mapper;

import com.tencent.wxcloudrun.pto.CommercialBuildingPTO;
import com.tencent.wxcloudrun.pto.IndustrialParkDetailPTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IndustrialParkDetailMapper {
    int insert(IndustrialParkDetailPTO industrialParkDetailPTO);
}
