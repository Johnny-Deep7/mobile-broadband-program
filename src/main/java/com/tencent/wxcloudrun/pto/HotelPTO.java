package com.tencent.wxcloudrun.pto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    //分局
    private String substation;
    //客户经理
    private String customerManager;
    //联系方式
    private String contactWay;
    //酒店宾馆名称
    private String hotelName;
    //覆盖名称（或未覆盖）
    private String isCovered;
    //房间数
    private Integer roomNumber;
    //集团编号（已建）
    private String groupNumber;
    //地址
    private String address;
    //酒店类型（星级、连锁、民宿）
    private String hotelType;
    //现用运营商（移动、电信、联通）
    private String operator;
    //本网到期时间）
    private String endTime1;
    //异网资费
    private String internetCharge;
    //异网业务到期时间
    private String endTime2;
    //酒店负责人
    private String responsiblePerson;
    //联系人职务
    private String position;
    //联系人电话
    private String phoneNumber;
    //走访日期
    private String visitDate;
    //走访情况
    private String visitInformation;
    //备注（当前攻坚主要问题 覆盖/装机/关系/政策）
    private String difficultPoint;

}
