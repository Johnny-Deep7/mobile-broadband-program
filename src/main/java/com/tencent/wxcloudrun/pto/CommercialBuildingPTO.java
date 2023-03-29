package com.tencent.wxcloudrun.pto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

//商务楼宇
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "mbp_commercial_building")
public class CommercialBuildingPTO implements Serializable {

    @TableId(value = "id" ,type = IdType.AUTO)
    private Integer id;
    //分局
    private String substation;
    //客户经理
    private String customerManager;
    //联系方式
    private String contactWay;
    //名称
    private String hotelName;
//    覆盖名称（或未覆盖）
    private String isCovered;

    private int roomNumber;
//    集团编号
    private String groupNumber;
//    地址
    private String address;
//    酒店类型
    private String type;
    //现用运营商（移动、电信、联通）
    private String operator;
    //本网到期时间）
    private String endTime1;
    //异网资费
    private String internetCharge;
    //异网业务到期时间
    private String endTime2;
//    物业联系人
    private String responsiblePerson;
//    联系人职务
    private String position;
//    联系人电话
    private String phoneNumber;
//    走访日期
    private String visitDate;
//    走访情况
    private String visitInformation;

    private String difficultPoint;
//    入驻企业数
    private int enterpriseNumber;
//    楼栋数
    private int buildingNum;
//    GIMS编号（本地数据）
    private String gims;
//    若覆盖，信息点数，根据GIMS系统填写
    private String gimsCover;
//    物业名称
    private String propertyName;
//    物业集团是否已建
    private String isProperty;
}
