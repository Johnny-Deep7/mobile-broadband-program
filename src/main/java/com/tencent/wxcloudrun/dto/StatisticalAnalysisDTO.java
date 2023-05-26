package com.tencent.wxcloudrun.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticalAnalysisDTO {
    private List<GetCountDTO> getCountDTOS;
    private int hotelCount;
    private int buildingCount;
    private int industrialParkCount;
    private int shopCount;
    private int totelCount;
}
