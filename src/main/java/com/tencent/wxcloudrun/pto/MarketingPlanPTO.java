package com.tencent.wxcloudrun.pto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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

    @TableId(value = "id" ,type = IdType.AUTO)
    private Integer id;
    private String name;
    private String customerManager;
    private String visitInfo;
    private String rivalInfo;
    private String businessOpp;
    private String remarks;
    private String clockingPhoto;
}
