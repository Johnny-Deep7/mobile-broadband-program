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

import javax.xml.ws.BindingType;
import java.io.Serializable;

//产业园区
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "mbp_industrial_park")
@Builder
public class IndustrialParkPTO implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
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
    //名称（产业园区/聚类）
    @TableField("HOTEL_NAME")
    @ExcelProperty("名称")
    private String hotelName;
    //覆盖名称（或未覆盖）
    @ExcelProperty("覆盖名称")
    @TableField("IS_COVERED")
    private String isCovered;
    private Integer roomNumber;
    private String groupNumber;
    //地址
    @TableField("ADDRESS")
    @ExcelProperty("地址")
    private String address;
    //类型
    @TableField("HOTEL_TYPE")
    @ExcelProperty("类型")
    private String hotelType;
    //现用运营商（移动、电信、联通）
    @ExcelProperty("现用运营商")
    @TableField("OPERATOR")
    private String operator;
    //本网到期时间
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
    private String responsiblePerson;
    private String position;
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
    //    实际入驻企业数
    @TableField("ENTERPRISE_NUMBER")
    @ExcelProperty("实际入驻企业数")
    private String enterpriseNumber;
    //    楼栋数
    @TableField("BUILDING_NUM")
    @ExcelProperty("楼栋数")
    private String buildingNum;
    @TableField("WRITE_TIME")
    @ExcelProperty("创建时间")
    private String writeTime;
    @TableField("MODIFY_TIME")
    @ExcelProperty("修改时间")
    private String modifyTime;
}
