package com.tencent.wxcloudrun.pto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

//沿街商铺
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "mbp_street_shops")
public class ShopPTO implements Serializable {
    private int id;
//    小区编号（GIMS信息点）
    private String gimsPoint;
//    分局
    private String substation;
//    包干厅店机构编号
    private String institutionNo;
//    街长
    private String streetChief;
//    小区名称
    private String cellName;
//    工程状态
    private String status;
//    标准地址
    private String address;
//    小区信息点数量
    private String infoPoint;
//    小区创建时间
    private String createTime;
//    类型
    private String shopType;
//    户数
    private String houseHolds;
//    属性
    private String attribute;
//    商宽
    private String width;
//    家宽
    private String familyWidth;
//    空白
    private String blank;
//    渗透率
    private String permeability;
//    目前洽谈跟进情况 （策反或在谈业务）
    private String talkStatus;
//    走访日期
    private String visitDate;
//    走访情况
    private String visitInformation;
}
