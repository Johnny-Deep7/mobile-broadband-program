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
    private String Id;
    private String Substation;
    private String CustomerManager;
    private String ContactWay;
    private String HotelName;
    private String IsCovered;
    private String RoomNumber;
    private String GroupNumber;
    private String Address;
    private String Type;
    private String Operator;
    private String EndNime1;
    private String InternetCharge;
    private String EndTime2;
    private String ResponsiblePerson;
    private String Position;
    private String PhoneNumber;
    private String VisitDate;
    private String VisitInformation;
    private String DifficultPoint;
}
