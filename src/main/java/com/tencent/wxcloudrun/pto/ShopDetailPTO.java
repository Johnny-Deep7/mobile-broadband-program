package com.tencent.wxcloudrun.pto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "mbp_sub_street_shops")
@Builder
public class ShopDetailPTO implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    //    办理号码
    @TableField("HANDLING_NUMBER")
    private String handlingNumber;
    //    区域
    @TableField("SUBSTATION")
    private String substation;
    //    客户经理
    @TableField("CUSTOMER_MANAGER")
    private String customerManager;
    //    街长
    @TableField("STREET_CHIEF")
    private String streetChief;
    //    归属沿街(按沿街表内F列名称）
    @TableField("STREET_BELONG")
    private String streetBelong;
    //    门牌号
    @TableField("HOUSE_NUMBER")
    private String houseNumber;
    //    商铺名称
    @TableField("SHOP_NAME")
    private String shopName;
    //    行业类型
    @TableField("INDUSTRY_TYPE")
    private String industryType;
    //    集团联系人
    @TableField("RESPONSIBLE_PERSON")
    private String responsiblePerson;
    //    联系人号码
    @TableField("PHONE_NUMBER")
    private String phoneNumber;
    //    目前营业状态（营业、搬迁、空置、招工）
    @TableField("BUSINESS_STATUS")
    private String businessStatus;
    //    运营商选择（移动、电信、联通）
    @TableField("OPERATOR")
    private String operator;
    //    异网业务（一网通、专线、号卡等业务政策资费简述）
    @TableField("CROSS_NETWORK_SERVICE")
    private String crossNetworkService;
    //    异网业务到期时间
    @TableField("END_TIME2")
    private String endTime2;
    //    使用宽带账号
    @TableField("BROADBAND_ACCOUNT")
    private String broadbandAccount;
    //    目前洽谈跟进情况 （策反或在谈业务）
    @TableField("TALK_STATUS")
    private String talkStatus;
    //    走访日期
    @TableField("VISIT_DATE")
    private String visitDate;
    //    走访情况
    @TableField("VISIT_INFORMATION")
    private String visitInformation;
    @TableField("SHOP_ID")
    private Integer shopId;
}
