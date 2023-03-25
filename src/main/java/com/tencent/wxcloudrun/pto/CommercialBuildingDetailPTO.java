package com.tencent.wxcloudrun.pto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommercialBuildingDetailPTO implements Serializable {
    private String Id;
    private String Substation;
    private String CustomerManager;
    private String ContactWay;
    private String HotelName;
    private String IsCovered;
    private String RoomNumber;
    private String GroupNumber;
    private String Address ;
    private String Type;
    private String Operator;
    private String EndTime1;
    private String InternetCharge;
    private String EndTime2;
    private String ResponsiblePerson;
    private String Position;
    private String PhoneNumber;
    private String VisitDate;
    private String VisitInformation;
    private String DifficultPoint;
}
