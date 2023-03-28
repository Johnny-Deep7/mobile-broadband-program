package com.tencent.wxcloudrun.pto;

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
    private int id;
    private String substation;
    private String customerManager;
    private int contactWay;
    private String hotelName;
    private String isCovered;
    private int roomNumber;
    private String groupNumber;
    private String address;
    private String type;
    private String operator;
    private String endTime1;
    private String internetCharge;
    private String endTime2;
    private String responsiblePerson;
    private String position;
    private String phoneNumber;
    private String visitDate;
    private String visitInformation;
    private String difficultPoint;
    private int enterpriseNumber;
    private int buildingNum;
    private String gims;
    private String gimsCover;
    private String propertyName;
    private String isProperty;
}
