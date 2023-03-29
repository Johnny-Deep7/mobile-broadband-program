package com.tencent.wxcloudrun.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//营销计划
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarketingPlanDTO {
    private Integer id;
    private String name;
    private String customerManager;
    private String visitInfo;
    private String rivalInfo;
    private String businessOpp;
    private String remarks;
    private String clockingPhoto;
}
