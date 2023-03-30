package com.tencent.wxcloudrun.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndustrialParkDetail implements Serializable {

    private Integer id;
    private String substation;
    private String customerManager;
    private String industryPark;
    private String contactWay;
    private String enterpriseName;
    private String floor;
    private String industryType;
    private String roomNumber;
    private String groupNumber;
    private String employeeNumber;
    private String operator;
    private String crossNetworkService;
    private String endTime2;
    private String broadbandAccount;
    private String negotiationStatus;
    private String responsiblePerson;
    private String position;
    private String phoneNumber;
    private String businessSituation;
    private String visitDate;
    private String visitInformation;
    private Integer parkId;

}
