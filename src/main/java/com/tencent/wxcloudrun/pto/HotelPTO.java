package com.tencent.wxcloudrun.pto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "mbp_hotel")
@Builder
public class HotelPTO implements Serializable {

    private Integer id;
    private String substation;
    private String customerManager;
    private String contactWay;
    private String hotelName;
    private String isCovered;
    private Integer roomNumber;
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

}
