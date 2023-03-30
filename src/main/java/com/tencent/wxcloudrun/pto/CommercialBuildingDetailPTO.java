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
@TableName(value = "mbp_sub_commercial_building")
@Builder
public class CommercialBuildingDetailPTO implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    //    所在楼层
    @TableField("FLOOR")
    private String floor;

    //    房间号
    @TableField("ROOM_NUMBER")
    private String roomNumber;
    private String address;

    //    企业名称
    @TableField("ENTERPRISE_NAME")
    private String enterpriseName;

    //    异网业务（一网通、专线、号卡等业务政策资费简述）
    @TableField("CROSS_NETWORK_SERVICE")
    private String crossNetworkService;

    //    异网到期时间
    @TableField("END_TIME2")
    private String endTime2;
    //    集团联系人
    @TableField("RESPONSIBLE_PERSON")
    private String responsiblePerson;
    //    联系人职务
    @TableField("POSITION")
    private String position;
    //    联系人号码
    @TableField("PHONE_NUMBER")
    private String phoneNumber;
    //    集团编码
    @TableField("GROUP_NUMBER")
    private String groupNumber;
    //    归属楼宇(按楼宇表内F列名称）
    @TableField("OWNER_BUILDING")
    private String ownerBuilding;
    //    区域
    @TableField("SUBSTATION")
    private String substation;
    //    客户经理
    @TableField("CUSTOMER_MANAGER")
    private String customerManager;
    //    客户经理联系电话
    @TableField("CONTACT_WAY")
    private String contactWay;

    private String hotelName;
    private String isCovered;
    //    行业类型
    @TableField("INDUSTRY_TYPE")
    private String industryType;
    //    员工人数
    @TableField("EMPLOYEE_NUMBER")
    private Integer employeeNumber;
    //    运营商选择（移动、电信、联通）
    @TableField("OPERATOR")
    private String operator;
    private String endTime1;
    //    目前营业状态（营业、搬迁、空置、招工）
    @TableField("BUSINESS_SITUATION")
    private String businessSituation;
    //    目前洽谈跟进情况 （策反或在谈业务）
    @TableField("NEGOTIATION_STATUS")
    private String negotiationStatus;
    //    走访日期
    @TableField("VISIT_DATE")
    private String visitDate;
    //    走访情况
    @TableField("VISIT_INFORMATION")
    private String visitInformation;
    private String difficultPoint;
    @TableField("BUILDING_ID")
    private Integer buildingId;
}
