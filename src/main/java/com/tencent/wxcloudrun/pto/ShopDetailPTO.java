package com.tencent.wxcloudrun.pto;

import com.alibaba.excel.annotation.ExcelProperty;
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
    @ExcelProperty("办理号码")
    private String handlingNumber;
    //    区域
    @TableField("SUBSTATION")
    @ExcelProperty("区域")
    private String substation;
    //    客户经理
    @TableField("CUSTOMER_MANAGER")
    @ExcelProperty("客户经理")
    private String customerManager;
    //    街长
    @TableField("STREET_CHIEF")
    @ExcelProperty("街长")
    private String streetChief;
    //    归属沿街(按沿街表内F列名称）
    @TableField("STREET_BELONG")
    @ExcelProperty("归属沿街")
    private String streetBelong;
    //    门牌号
    @TableField("HOUSE_NUMBER")
    @ExcelProperty("门牌号")
    private String houseNumber;
    //    商铺名称
    @TableField("SHOP_NAME")
    @ExcelProperty("商铺名称")
    private String shopName;
    //    行业类型
    @TableField("INDUSTRY_TYPE")
    @ExcelProperty("行业类型")
    private String industryType;
    //    集团联系人
    @TableField("RESPONSIBLE_PERSON")
    @ExcelProperty("集团联系人")
    private String responsiblePerson;
    //    联系人号码
    @TableField("PHONE_NUMBER")
    @ExcelProperty("联系人号码")
    private String phoneNumber;
    //    目前营业状态（营业、搬迁、空置、招工）
    @TableField("BUSINESS_STATUS")
    @ExcelProperty("目前营业状态")
    private String businessStatus;
    //    运营商选择（移动、电信、联通）
    @TableField("OPERATOR")
    @ExcelProperty("运营商选择")
    private String operator;
    //    异网业务（一网通、专线、号卡等业务政策资费简述）
    @TableField("CROSS_NETWORK_SERVICE")
    @ExcelProperty("异网业务")
    private String crossNetworkService;
    //    异网业务到期时间
    @TableField("END_TIME2")
    @ExcelProperty("异网业务到期时间")
    private String endTime2;
    //    使用宽带账号
    @TableField("BROADBAND_ACCOUNT")
    @ExcelProperty("使用宽带账号")
    private String broadbandAccount;
    //    目前洽谈跟进情况 （策反或在谈业务）
    @TableField("TALK_STATUS")
    @ExcelProperty("目前洽谈跟进情况")
    private String talkStatus;
    //    走访日期
    @TableField("VISIT_DATE")
    @ExcelProperty("走访日期")
    private String visitDate;
    //    走访情况
    @TableField("VISIT_INFORMATION")
    @ExcelProperty("走访情况")
    private String visitInformation;
    @TableField("SHOP_ID")
    private Integer shopId;
    @TableField("WRITE_TIME")
    @ExcelProperty("创建时间")
    private String writeTime;
    @TableField("MODIFY_TIME")
    @ExcelProperty("修改时间")
    private String modifyTime;
}
