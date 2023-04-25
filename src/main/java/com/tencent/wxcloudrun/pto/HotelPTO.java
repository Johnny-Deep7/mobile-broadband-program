package com.tencent.wxcloudrun.pto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
    @TableField("SUBSTATION")
    private String substation;
    //客户经理
    @TableField("CUSTOMER_MANAGER")
    private String customerManager;
    //联系方式
    @TableField("CONTACT_WAY")
    private String contactWay;
    //酒店宾馆名称
    @TableField("HOTEL_NAME")
    private String hotelName;
    //覆盖名称（或未覆盖）
    @TableField("IS_COVERED")
    private String isCovered;
    //房间数
    @TableField("ROOM_NUMBER")
    private String roomNumber;
    //集团编号（已建）
    @TableField("GROUP_NUMBER")
    private String groupNumber;
    //地址
    @TableField("ADDRESS")
    private String address;
    //酒店类型（星级、连锁、民宿）
    @TableField("HOTEL_TYPE")
    private String hotelType;
    //现用运营商（移动、电信、联通）
    @TableField("OPERATOR")
    private String operator;
    //本网到期时间）
    @TableField("END_TIME1")
    private String endTime1;
    //异网资费
    @TableField("INTERNET_CHARGE")
    private String internetCharge;
    //异网业务到期时间
    @TableField("END_TIME2")
    private String endTime2;
    //酒店负责人
    @TableField("RESPONSIBLE_PERSON")
    private String responsiblePerson;
    //联系人职务
    @TableField("POSITION")
    private String position;
    //联系人电话
    @TableField("PHONE_NUMBER")
    private String phoneNumber;
    //走访日期
    @TableField("VISIT_DATE")
    private String visitDate;
    //走访情况
    @TableField("VISIT_INFORMATION")
    private String visitInformation;
    //备注（当前攻坚主要问题 覆盖/装机/关系/政策）
    @TableField("DIFFICULT_POINT")
    private String difficultPoint;
    @TableField("WRITE_TIME")
    private String writeTime;
    @TableField("MODIFY_TIME")
    private String modifyTime;
}
