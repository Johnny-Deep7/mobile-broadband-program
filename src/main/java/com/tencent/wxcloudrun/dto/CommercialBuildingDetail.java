package com.tencent.wxcloudrun.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommercialBuildingDetail implements Serializable {
    private Integer id;
    private String floor;
    private String roomNumber;
    private String address;
    private String enterpriseName;
    private String crossNetworkService;
    private String endTime2;
    private String responsiblePerson;
    private String position;
    private String phoneNumber;
    private String groupNumber;
    private String ownerBuilding;
    private String substation;
    private String customerManager;
    private String contactWay;
    private String hotelName;
    private String isCovered;
    private String industryType;
    private String employeeNumber;
    private String operator;
    private String endTime1;
    private String businessSituation;
    private String negotiationStatus;
    private String visitDate;
    private String visitInformation;
    private String difficultPoint;
    private Integer buildingId;
}
