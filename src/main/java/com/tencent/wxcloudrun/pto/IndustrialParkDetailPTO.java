package com.tencent.wxcloudrun.pto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "mbp_sub_industrial_park")
@Builder
@Getter
@Setter
public class IndustrialParkDetailPTO implements Serializable {
    @TableId(value = "id" ,type = IdType.AUTO)
    private Integer id;
//    区域
    @TableField("SUBSTATION")
    @ExcelProperty("区域")
    private String substation;
//    客户经理
    @TableField("CUSTOMER_MANAGER")
    @ExcelProperty("客户经理")
    private String customerManager;
//    产业园区(按产业园区、聚类卖场表内F列名称）
    @TableField("INDUSTRY_PARK")
    @ExcelProperty("产业园区")
    private String industryPark;
//    客户经理联系电话
    @TableField("CONTACT_WAY")
    @ExcelProperty("客户经理联系电话")
    private String contactWay;
//    企业名称
    @TableField("ENTERPRISE_NAME")
    @ExcelProperty("企业名称")
    private String enterpriseName;
//    所在楼层
    @TableField("FLOOR")
    @ExcelProperty("所在楼层")
    private String floor;
//    行业类型
    @TableField("INDUSTRY_TYPE")
    @ExcelProperty("行业类型")
    private String industryType;
//    房间号
    @TableField("ROOM_NUMBER")
    @ExcelProperty("房间号")
    private String roomNumber;
//     集团编码
    @TableField("GROUP_NUMBER")
    @ExcelProperty("集团编码")
    private String groupNumber;
//    员工人数
    @TableField("EMPLOYEE_NUMBER")
    @ExcelProperty("员工人数")
    private String employeeNumber;
//    运营商选择（移动、电信、联通）
    @TableField("OPERATOR")
    @ExcelProperty("运营商选择")
    private String operator;
//    异网业务（宽带、专线、号卡等业务政策资费简述）
    @TableField("CROSS_NETWORK_SERVICE")
    @ExcelProperty("异网业务")
    private String crossNetworkService;
//    异网业务到期时间
    @TableField("END_TIME2")
    @ExcelProperty("异网业务到期时间")
    private String endTime2;
//    使用宽带账号
    @TableField("BROADBAND_ACCOUNT")
    @ExcelProperty("使用宽带账号")
    private String broadbandAccount;
//    目前洽谈跟进情况 （策反或在谈业务）
    @TableField("NEGOTIATION_STATUS")
    @ExcelProperty("目前洽谈跟进情况")
    private String negotiationStatus;
//    集团联系人
    @TableField("RESPONSIBLE_PERSON")
    @ExcelProperty("集团联系人")
    private String responsiblePerson;
//    联系人职务
    @TableField("POSITION")
    @ExcelProperty("联系人职务")
    private String position;
//    联系人号码
    @TableField("PHONE_NUMBER")
    @ExcelProperty("联系人号码")
    private String phoneNumber;
//    目前营业状态（营业、搬迁、空置、招工）
    @TableField("BUSINESS_SITUATION")
    @ExcelProperty("目前营业状态")
    private String businessSituation;
//    走访日期
    @TableField("VISIT_DATE")
    @ExcelProperty("走访日期")
    private String visitDate;
//    走访情况
    @TableField("VISIT_INFORMATION")
    @ExcelProperty("走访情况")
    private String visitInformation;
    @TableField("PARK_ID")
    private Integer parkId;
    @TableField("WRITE_TIME")
    @ExcelProperty("创建时间")
    private String writeTime;
    @TableField("MODIFY_TIME")
    @ExcelProperty("修改时间")
    private String modifyTime;
}
