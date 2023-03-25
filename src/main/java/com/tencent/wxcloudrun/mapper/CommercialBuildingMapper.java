package com.tencent.wxcloudrun.mapper;

import com.tencent.wxcloudrun.pto.CommercialBuildingPTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommercialBuildingMapper {

    int insert(CommercialBuildingPTO commercialBuildingPTO);

    int deleteById(int id);

    CommercialBuildingPTO listCommercialBuilding(CommercialBuildingPTO commercialBuildingPTO);
}
