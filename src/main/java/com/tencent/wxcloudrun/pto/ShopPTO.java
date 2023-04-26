package com.tencent.wxcloudrun.pto;

import com.alibaba.excel.annotation.ExcelProperty;
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
    @ExcelProperty("小区编号")
    private String gimsPoint;
    //    分局
    @TableField("SUBSTATION")
    @ExcelProperty("分局")
    private String substation;
    //    包干厅店机构编号
    @TableField("INSTITUTION_NO")
    @ExcelProperty("包干厅店机构编号")
    private String institutionNo;
    //    街长
    @TableField("STREET_CHIEF")
    @ExcelProperty("街长")
    private String streetChief;
    //    小区名称
    @TableField("CELL_NAME")
    @ExcelProperty("小区名称")
    private String cellName;
    //    工程状态
    @TableField("STATUS")
    @ExcelProperty("工程状态")
    private String status;
    //    标准地址
    @TableField("ADDRESS")
    @ExcelProperty("标准地址")
    private String address;
    //    小区信息点数量
    @TableField("INFO_POINT")
    @ExcelProperty("小区信息点数量")
    private String infoPoint;
    //    小区创建时间
    @TableField("CREATE_TIME")
    @ExcelProperty("小区创建时间")
    private String createTime;
    //    类型
    @TableField("SHOP_TYPE")
    @ExcelProperty("类型")
    private String shopType;
    //    户数
    @TableField("HOUSE_HOLDS")
    @ExcelProperty("户数")
    private String houseHolds;
    //    属性
    @TableField("ATTRIBUTE")
    @ExcelProperty("属性")
    private String attribute;
    //    商宽
    @TableField("WIDTH")
    @ExcelProperty("商宽")
    private String width;
    //    家宽
    @TableField("FAMILY_WIDTH")
    @ExcelProperty("家宽")
    private String familyWidth;
    //    空白
    @TableField("BLANK")
    @ExcelProperty("空白")
    private String blank;
    //    渗透率
    @TableField("PERMEABILITY")
    @ExcelProperty("渗透率")
    private String permeability;
    //    目前洽谈跟进情况 （策反或在谈业务）
    @TableField("TALK_STATUS")
    @ExcelProperty("目前洽谈跟进情况")
    private String talkStatus;
    //    走访日期
    @TableField("VISIT_DATE")
    @ExcelProperty("走访日期")
    private String visitDate;
    //    走访情况
    @TableField("VISIT_INFORMATION")
    @ExcelProperty("走访情况")
    private String visitInformation;
    @TableField("WRITE_TIME")
    @ExcelProperty("创建时间")
    private String writeTime;
    @TableField("MODIFY_TIME")
    @ExcelProperty("修改时间")
    private String modifyTime;
}

