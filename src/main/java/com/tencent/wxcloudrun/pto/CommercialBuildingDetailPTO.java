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
@TableName(value = "mbp_sub_commercial_building")
@Builder
public class CommercialBuildingDetailPTO implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    //    所在楼层
    private int floor;
    //    房间号
    private int roomNumber;
    private String address;
    //    企业名称
    private String enterpriseName;
    //    异网业务（一网通、专线、号卡等业务政策资费简述）
    private String crossNetworkService;
    //    异网到期时间
    private String endTime2;
    //    集团联系人
    private String responsiblePerson;
    //    联系人职务
    private String position;
    //    联系人号码
    private String phoneNumber;
    //    集团编码
    private String groupNumber;
    //    归属楼宇(按楼宇表内F列名称）
    private String ownerBuilding;
    //    区域
    private String substation;
    //    客户经理
    private String customerManager;
    //    客户经理联系电话
    private String contactWay;

    private String hotelName;
    private String isCovered;
    //    制造业
    private String industryType;
    //    员工人数
    private int employeeNumber;
    //    运营商选择（移动、电信、联通）
    private String operator;
    private String endTime1;
    //    目前营业状态（营业、搬迁、空置、招工）
    private String businessSituation;
    //    目前洽谈跟进情况 （策反或在谈业务）
    private String negotiationStatus;
    //    走访日期
    private String visitDate;
    //    走访情况
    private String visitInformation;
    private String difficultPoint;
    private int buildingId;
}
