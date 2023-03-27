package com.tencent.wxcloudrun.pto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

//沿街商铺
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopPTO implements Serializable {
    private int id;
    private String substation;
    private String institutionNo;
    private String streetChief;
    private String callName;
    private String status;
    private String address;
    private String infoPoint;
    private String createTime;
    private String shopType;
    private String houseHolds;
    private String attribute;
    private String width;
    private String familyWidth;
    private String blank;
    private String permeability;
    private String talkStatus;
    private String visitDate;
    private String visitInformation;
}
