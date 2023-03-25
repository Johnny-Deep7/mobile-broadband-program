package com.tencent.wxcloudrun.pto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelPTO implements Serializable {

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

}
