package com.tencent.wxcloudrun.dto.excelEntity;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 沿街商铺表格字段
 */
public class ExcelShop {
    @ExcelProperty("小区编号")
    private String gimsPoint;
    @ExcelProperty("分局")
    private String substation;
    @ExcelProperty("包干厅店机构编号")
    private String institutionNo;
    @ExcelProperty("街长")
    private String streetChief;
    @ExcelProperty("小区名称")
    private String cellName;
    @ExcelProperty("工程状态")
    private String status;
    @ExcelProperty("标准地址")
    private String address;
    @ExcelProperty("小区信息点数量")
    private String infoPoint;
    @ExcelProperty("小区创建时间")
    private String createTime;
    @ExcelProperty("类型")
    private String shopType;
    @ExcelProperty("户数")
    private String houseHolds;
    @ExcelProperty("属性")
    private String attribute;
    @ExcelProperty("商宽")
    private String width;
    @ExcelProperty("家宽")
    private String familyWidth;
    @ExcelProperty("空白")
    private String blank;
    @ExcelProperty("渗透率")
    private String permeability;
    @ExcelProperty("目前洽谈跟进情况")
    private String talkStatus;
    @ExcelProperty("走访日期")
    private String visitDate;
    @ExcelProperty("走访情况")
    private String visitInformation;
    @ExcelProperty("创建时间")
    private String writeTime;
    @ExcelProperty("修改时间")
    private String modifyTime;

    public String getGimsPoint() {
        return gimsPoint;
    }

    public void setGimsPoint(String gimsPoint) {
        this.gimsPoint = gimsPoint;
    }

    public String getSubstation() {
        return substation;
    }

    public void setSubstation(String substation) {
        this.substation = substation;
    }

    public String getInstitutionNo() {
        return institutionNo;
    }

    public void setInstitutionNo(String institutionNo) {
        this.institutionNo = institutionNo;
    }

    public String getStreetChief() {
        return streetChief;
    }

    public void setStreetChief(String streetChief) {
        this.streetChief = streetChief;
    }

    public String getCellName() {
        return cellName;
    }

    public void setCellName(String cellName) {
        this.cellName = cellName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInfoPoint() {
        return infoPoint;
    }

    public void setInfoPoint(String infoPoint) {
        this.infoPoint = infoPoint;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    public String getHouseHolds() {
        return houseHolds;
    }

    public void setHouseHolds(String houseHolds) {
        this.houseHolds = houseHolds;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getFamilyWidth() {
        return familyWidth;
    }

    public void setFamilyWidth(String familyWidth) {
        this.familyWidth = familyWidth;
    }

    public String getBlank() {
        return blank;
    }

    public void setBlank(String blank) {
        this.blank = blank;
    }

    public String getPermeability() {
        return permeability;
    }

    public void setPermeability(String permeability) {
        this.permeability = permeability;
    }

    public String getTalkStatus() {
        return talkStatus;
    }

    public void setTalkStatus(String talkStatus) {
        this.talkStatus = talkStatus;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public String getVisitInformation() {
        return visitInformation;
    }

    public void setVisitInformation(String visitInformation) {
        this.visitInformation = visitInformation;
    }
}
