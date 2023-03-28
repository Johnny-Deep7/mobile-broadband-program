package com.tencent.wxcloudrun.pto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "mbp_sub_commercial_building")
public class CommercialBuildingDetailPTO implements Serializable {
    private int id;
    private int floor;
    private int roomNumber;
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
    private int employeeNumber;
    private String operator;
    private String endTime1;
    private String businessSituation;
    private String negotiationStatus;
    private String visitDate;
    private String visitInformation;
    private String difficultPoint;
    private int buildingId;
}
