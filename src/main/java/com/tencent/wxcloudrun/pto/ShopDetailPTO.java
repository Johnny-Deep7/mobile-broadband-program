package com.tencent.wxcloudrun.pto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopDetailPTO implements Serializable {
    private int id;
    private String handlingNumber;
    private String substation;
    private String customerManager;
    private String streetChief;
    private String streetBelong;
    private String houseNumber;
    private String shopName;
    private String industryType;
    private String responsiblePerson;
    private String phoneNumber;
    private String businessStatus;
    private String operator;
    private String crossNetworkService;
    private String endTime2;
    private String broadbandAccount;
    private String talkStatus;
    private String visitDate;
    private String visitInformation;
}
