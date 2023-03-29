package com.tencent.wxcloudrun.pto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "mbp_sub_street_shops")
public class ShopDetailPTO implements Serializable {

    @TableId(value = "id" ,type = IdType.AUTO)
    private Integer id;
//    办理号码
    private String handlingNumber;
//    区域
    private String substation;
//    客户经理
    private String customerManager;
//    街长
    private String streetChief;
//    归属沿街(按沿街表内F列名称）
    private String streetBelong;
//    门牌号
    private String houseNumber;
//    商铺名称
    private String shopName;
//    行业类型
    private String industryType;
//    集团联系人
    private String responsiblePerson;
//    联系人号码
    private String phoneNumber;
//    目前营业状态（营业、搬迁、空置、招工）
    private String businessStatus;
//    运营商选择（移动、电信、联通）
    private String operator;
//    异网业务（一网通、专线、号卡等业务政策资费简述）
    private String crossNetworkService;
//    异网业务到期时间
    private String endTime2;
//    使用宽带账号
    private String broadbandAccount;
//    目前洽谈跟进情况 （策反或在谈业务）
    private String talkStatus;
//    走访日期
    private String visitDate;
//    走访情况
    private String visitInformation;
    private int shopId;
}
