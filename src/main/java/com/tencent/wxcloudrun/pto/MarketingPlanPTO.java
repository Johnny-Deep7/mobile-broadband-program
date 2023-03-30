package com.tencent.wxcloudrun.pto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
    @TableField("NAME")
    private String name;
    @TableField("CUSTOMER_MANAGER")
    private String customerManager;
    @TableField("VISIT_INFO")
    private String visitInfo;
    @TableField("RIVAL_INFO")
    private String rivalInfo;
    @TableField("BUSINESS_OPP")
    private String businessOpp;
    @TableField("REMARKS")
    private String remarks;
    @TableField("CLOCKING_PHOTO")
    private String clockingPhoto;
}
