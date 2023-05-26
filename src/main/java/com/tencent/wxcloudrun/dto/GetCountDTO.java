package com.tencent.wxcloudrun.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Administrator
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCountDTO {

    private String customerManager;
    private String substation;
    private String startTime;
    private String endTime;
    private int hotel;
    private int building;
    private int industrialPark;
    private int shop;
    private int totel;
}
