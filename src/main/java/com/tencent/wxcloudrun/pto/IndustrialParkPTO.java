package com.tencent.wxcloudrun.pto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

//产业园区
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "mbp_industrial_park")
public class IndustrialParkPTO implements Serializable {
    private int id;
    private String substation;
    private String customerManager;
    private String contactWay;
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
}
