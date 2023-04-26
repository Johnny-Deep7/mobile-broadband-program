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

//商务楼宇
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "mbp_commercial_building")
@Builder
public class CommercialBuildingPTO implements Serializable {

    @TableId(value = "id" ,type = IdType.AUTO)
    private Integer id;
    //分局
    @TableField("SUBSTATION")
    @ExcelProperty("分局")
    private String substation;
    //客户经理
    @TableField("CUSTOMER_MANAGER")
    @ExcelProperty("客户经理")
    private String customerManager;
    //联系方式
    @TableField("CONTACT_WAY")
    @ExcelProperty("联系方式")
    private String contactWay;
    //名称
    @TableField("HOTEL_NAME")
    @ExcelProperty("名称")
    private String hotelName;
//    覆盖名称（或未覆盖）
    @TableField("IS_COVERED")
    @ExcelProperty("覆盖名称")
    private String isCovered;

    private String roomNumber;
//    集团编号
    @TableField("GROUP_NUMBER")
    @ExcelProperty("集团编号")
    private String groupNumber;
//    地址
    @TableField("ADDRESS")
    @ExcelProperty("地址")
    private String address;
//    酒店类型
    @TableField("HOTEL_TYPE")
    private String hotelType;
    //现用运营商（移动、电信、联通）
    @TableField("OPERATOR")
    @ExcelProperty("现用运营商")
    private String operator;
    //本网到期时间）
    @TableField("END_TIME1")
    @ExcelProperty("本网到期时间")
    private String endTime1;
    //异网资费
    @TableField("INTERNET_CHARGE")
    @ExcelProperty("异网资费")
    private String internetCharge;
    //异网业务到期时间
    @TableField("END_TIME2")
    @ExcelProperty("异网业务到期时间")
    private String endTime2;
//    物业联系人
    @TableField("RESPONSIBLE_PERSON")
    @ExcelProperty("物业联系人")
    private String responsiblePerson;
//    联系人职务
    @TableField("POSITION")
    @ExcelProperty("联系人职务")
    private String position;
//    联系人电话
    @TableField("PHONE_NUMBER")
    @ExcelProperty("联系人电话")
    private String phoneNumber;
//    走访日期
    @TableField("VISIT_DATE")
    @ExcelProperty("走访日期")
    private String visitDate;
//    走访情况
    @TableField("VISIT_INFORMATION")
    @ExcelProperty("走访情况")
    private String visitInformation;

    private String difficultPoint;
//    入驻企业数
    @TableField("ENTERPRISE_NUMBER")
    @ExcelProperty("入驻企业数")
    private String enterpriseNumber;
//    楼栋数
    @TableField("BUILDING_NUM")
    @ExcelProperty("楼栋数")
    private String buildingNum;
//    GIMS编号（本地数据）
    @TableField("GIMS")
    private String gims;
//    若覆盖，信息点数，根据GIMS系统填写
    @TableField("GIMS_COVER")
    private String gimsCover;
//    物业名称
    @TableField("PROPERTY_NAME")
    @ExcelProperty("物业名称")
    private String propertyName;
//    物业集团是否已建
    @TableField("IS_PROPERTY")
    @ExcelProperty("物业集团是否已建")
    private String isProperty;
    @TableField("WRITE_TIME")
    @ExcelProperty("创建时间")
    private String writeTime;
    @TableField("MODIFY_TIME")
    @ExcelProperty("修改时间")
    private String modifyTime;
}
