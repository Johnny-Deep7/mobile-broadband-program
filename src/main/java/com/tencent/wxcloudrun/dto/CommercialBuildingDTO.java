package com.tencent.wxcloudrun.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

//商务楼宇
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommercialBuildingDTO implements Serializable {
    private Integer id;
    private String substation;
    private String customerManager;
    private Integer contactWay;
    private String hotelName;
    private String isCovered;
    private Integer roomNumber;
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
    private Integer enterpriseNumber;
    private Integer buildingNum;
    private String gims;
    private String gimsCover;
    private String propertyName;
    private String isProperty;
}
