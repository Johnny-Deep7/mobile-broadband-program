package com.tencent.wxcloudrun.dto.excelEntity;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 商务楼宇表格字段
 */
public class ExcelBuilding {

    @ExcelProperty("分局")
    private String substation;

    @ExcelProperty("客户经理")
    private String customerManager;

    @ExcelProperty("联系方式")
    private String contactWay;

    @ExcelProperty("名称")
    private String hotelName;

    @ExcelProperty("地址")
    private String address;

    @ExcelProperty("覆盖名称")
    private String isCovered;

    @ExcelProperty("入驻企业数")
    private String enterpriseNumber;

    @ExcelProperty("楼栋数")
    private String buildingNum;

    @ExcelProperty("物业名称")
    private String propertyName;

    @ExcelProperty("物业集团是否已建")
    private String isProperty;

    @ExcelProperty("集团编号")
    private String groupNumber;

    @ExcelProperty("物业联系人")
    private String responsiblePerson;

    @ExcelProperty("联系人职务")
    private String position;

    @ExcelProperty("联系人电话")
    private String phoneNumber;

    @ExcelProperty("现用运营商")
    private String operator;

    @ExcelProperty("本网到期时间")
    private String endTime1;

    @ExcelProperty("异网资费")
    private String internetCharge;

    @ExcelProperty("异网业务到期时间")
    private String endTime2;

    @ExcelProperty("走访日期")
    private String visitDate;

    @ExcelProperty("走访情况")
    private String visitInformation;

    public String getSubstation() {
        return substation;
    }

    public void setSubstation(String substation) {
        this.substation = substation;
    }

    public String getCustomerManager() {
        return customerManager;
    }

    public void setCustomerManager(String customerManager) {
        this.customerManager = customerManager;
    }

    public String getContactWay() {
        return contactWay;
    }

    public void setContactWay(String contactWay) {
        this.contactWay = contactWay;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIsCovered() {
        return isCovered;
    }

    public void setIsCovered(String isCovered) {
        this.isCovered = isCovered;
    }

    public String getEnterpriseNumber() {
        return enterpriseNumber;
    }

    public void setEnterpriseNumber(String enterpriseNumber) {
        this.enterpriseNumber = enterpriseNumber;
    }

    public String getBuildingNum() {
        return buildingNum;
    }

    public void setBuildingNum(String buildingNum) {
        this.buildingNum = buildingNum;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getIsProperty() {
        return isProperty;
    }

    public void setIsProperty(String isProperty) {
        this.isProperty = isProperty;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getEndTime1() {
        return endTime1;
    }

    public void setEndTime1(String endTime1) {
        this.endTime1 = endTime1;
    }

    public String getInternetCharge() {
        return internetCharge;
    }

    public void setInternetCharge(String internetCharge) {
        this.internetCharge = internetCharge;
    }

    public String getEndTime2() {
        return endTime2;
    }

    public void setEndTime2(String endTime2) {
        this.endTime2 = endTime2;
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
