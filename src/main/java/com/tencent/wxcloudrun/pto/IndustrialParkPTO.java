package com.tencent.wxcloudrun.pto;

import com.baomidou.mybatisplus.annotation.IdType;
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
    private String substation;
    //客户经理
    private String customerManager;
    //联系方式
    private String contactWay;
    //名称（产业园区/聚类）
    private String hotelName;
    //覆盖名称（或未覆盖）
    private String isCovered;
    private int roomNumber;
    private String groupNumber;
    //地址
    private String address;
    //类型
    private String type;
    //现用运营商（移动、电信、联通）
    private String operator;
    //本网到期时间
    private String endTime1;
    //异网资费
    private String internetCharge;
    //异网业务到期时间
    private String endTime2;
    private String responsiblePerson;
    private String position;
    private String phoneNumber;
    //    走访日期
    private String visitDate;
    //    走访情况
    private String visitInformation;
    private String difficultPoint;
    //    实际入驻企业数
    private int enterpriseNumber;
    //    楼栋数
    private int buildingNum;
}
