package com.tencent.wxcloudrun.pto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "mbp_marketing_plan")
@Builder
public class MarketingPlanPTO {
    int id;
    String name;
    String customerManager;
    String visitInfo;
    String rivalInfo;
    String businessOpp;
    String remarks;
    String clockingPhoto;
}
