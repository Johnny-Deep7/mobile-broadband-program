package com.tencent.wxcloudrun.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestEntity implements Serializable {

    private Integer id;
    private String substation;
    private String customerManager;
    private String contactWay;
    private String hotelName;
    private String isCovered;
    private String roomNumber;
    private String groupNumber;
    private String address;
    private String hotelType;
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
    private String enterpriseNumber;
    private String buildingNum;
    private String gims;
    private String gimsCover;
    private String propertyName;
    private String isProperty;
    private String marketType;
    private String writeTime;
    private String modifyTime;
    private String startTime;
    private String endTime;
}
