package com.tencent.wxcloudrun.pto;

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
    private String substation;
    //客户经理
    @TableField("CUSTOMER_MANAGER")
    private String customerManager;
    //联系方式
    @TableField("CONTACT_WAY")
    private String contactWay;
    //名称（产业园区/聚类）
    @TableField("HOTEL_NAME")
    private String hotelName;
    //覆盖名称（或未覆盖）
    @TableField("IS_COVERED")
    private String isCovered;
    private Integer roomNumber;
    private String groupNumber;
    //地址
    @TableField("ADDRESS")
    private String address;
    //类型
    @TableField("HOTEL_TYPE")
    private String hotelType;
    //现用运营商（移动、电信、联通）
    @TableField("OPERATOR")
    private String operator;
    //本网到期时间
    @TableField("END_TIME1")
    private String endTime1;
    //异网资费
    @TableField("INTERNET_CHARGE")
    private String internetCharge;
    //异网业务到期时间
    @TableField("END_TIME2")
    private String endTime2;
    private String responsiblePerson;
    private String position;
    private String phoneNumber;
    //    走访日期
    @TableField("VISIT_DATE")
    private String visitDate;
    //    走访情况
    @TableField("VISIT_INFORMATION")
    private String visitInformation;
    private String difficultPoint;
    //    实际入驻企业数
    @TableField("ENTERPRISE_NUMBER")
    private String enterpriseNumber;
    //    楼栋数
    @TableField("BUILDING_NUM")
    private String buildingNum;
    @TableField("WRITE_TIME")
    private String writeTime;
    @TableField("MODIFY_TIME")
    private String modifyTime;
}
