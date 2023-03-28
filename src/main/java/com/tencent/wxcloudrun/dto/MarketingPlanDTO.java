package com.tencent.wxcloudrun.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//营销计划
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarketingPlanDTO {
    int id;
    String name;
    String customerManager;
    String visitInfo;
    String rivalInfo;
    String businessOpp;
    String remarks;
    String clockingPhoto;
}
