package com.tencent.wxcloudrun.pto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    //    小区编号（GIMS信息点）
    @TableField("GIMS_POINT")
    private String gimsPoint;
    //    分局
    @TableField("SUBSTATION")
    private String substation;
    //    包干厅店机构编号
    @TableField("INSTITUTION_NO")
    private String institutionNo;
    //    街长
    @TableField("STREET_CHIEF")
    private String streetChief;
    //    小区名称
    @TableField("CELL_NAME")
    private String cellName;
    //    工程状态
    @TableField("STATUS")
    private String status;
    //    标准地址
    @TableField("ADDRESS")
    private String address;
    //    小区信息点数量
    @TableField("INFO_POINT")
    private String infoPoint;
    //    小区创建时间
    @TableField("CREATE_TIME")
    private String createTime;
    //    类型
    @TableField("SHOP_TYPE")
    private String shopType;
    //    户数
    @TableField("HOUSE_HOLDS")
    private String houseHolds;
    //    属性
    @TableField("ATTRIBUTE")
    private String attribute;
    //    商宽
    @TableField("WIDTH")
    private String width;
    //    家宽
    @TableField("FAMILY_WIDTH")
    private String familyWidth;
    //    空白
    @TableField("BLANK")
    private String blank;
    //    渗透率
    @TableField("PERMEABILITY")
    private String permeability;
    //    目前洽谈跟进情况 （策反或在谈业务）
    @TableField("TALK_STATUS")
    private String talkStatus;
    //    走访日期
    @TableField("VISIT_DATE")
    private String visitDate;
    //    走访情况
    @TableField("VISIT_INFORMATION")
    private String visitInformation;
    @TableField("WRITE_TIME")
    private String writeTime;
    @TableField("MODIFY_TIME")
    private String modifyTime;
}

