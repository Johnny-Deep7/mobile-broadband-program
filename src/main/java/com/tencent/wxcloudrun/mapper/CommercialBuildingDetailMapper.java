package com.tencent.wxcloudrun.mapper;

import com.tencent.wxcloudrun.pto.CommercialBuildingDetailPTO;
import com.tencent.wxcloudrun.pto.HotelPTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommercialBuildingDetailMapper {
    int insert(CommercialBuildingDetailPTO commercialBuildingDetailPTO);
}
