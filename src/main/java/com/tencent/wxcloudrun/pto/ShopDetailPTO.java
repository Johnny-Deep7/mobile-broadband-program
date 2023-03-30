package com.tencent.wxcloudrun.pto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    //    办理号码
    @TableField("HANDLINGNUMBER")
    private String handlingNumber;
    //    区域
    @TableField("SUBSTATION")
    private String substation;
    //    客户经理
    @TableField("CUSTOMERMANAGER")
    private String customerManager;
    //    街长
    @TableField("STREETCHIEF")
    private String streetChief;
    //    归属沿街(按沿街表内F列名称）
    @TableField("STREETLONG")
    private String streetBelong;
    //    门牌号
    @TableField("HOUSENUMBER")
    private String houseNumber;
    //    商铺名称
    @TableField("SHOPNAME")
    private String shopName;
    //    行业类型
    @TableField("INDUSTRYTYPE")
    private String industryType;
    //    集团联系人
    @TableField("RESPONSIBLEPERSON")
    private String responsiblePerson;
    //    联系人号码
    @TableField("PHONENUMBER")
    private String phoneNumber;
    //    目前营业状态（营业、搬迁、空置、招工）
    @TableField("BUSINESSSTATUS")
    private String businessStatus;
    //    运营商选择（移动、电信、联通）
    @TableField("OPERATOR")
    private String operator;
    //    异网业务（一网通、专线、号卡等业务政策资费简述）
    @TableField("CROSSNETWORKSERVICE")
    private String crossNetworkService;
    //    异网业务到期时间
    @TableField("ENDTIME2")
    private String endTime2;
    //    使用宽带账号
    @TableField("BROADBANDACCOUNT")
    private String broadbandAccount;
    //    目前洽谈跟进情况 （策反或在谈业务）
    @TableField("TALKSTATUS")
    private String talkStatus;
    //    走访日期
    @TableField("VISITDATE")
    private String visitDate;
    //    走访情况
    @TableField("VISITINFORMATION")
    private String visitInformation;
    @TableField("SHOPID")
    private Integer shopId;
}
