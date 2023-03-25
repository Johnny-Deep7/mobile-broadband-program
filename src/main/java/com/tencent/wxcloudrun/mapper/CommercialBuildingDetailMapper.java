package com.tencent.wxcloudrun.mapper;

import com.tencent.wxcloudrun.pto.CommercialBuildingDetailPTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommercialBuildingDetailMapper {
    int insert(CommercialBuildingDetailPTO commercialBuildingDetailPTO);

    int deleteById(int id);

}
