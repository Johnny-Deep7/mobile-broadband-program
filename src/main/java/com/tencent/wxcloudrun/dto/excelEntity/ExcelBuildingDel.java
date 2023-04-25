package com.tencent.wxcloudrun.dto.excelEntity;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 商务楼宇二级明细表格字段
 */
public class ExcelBuildingDel {
    @ExcelProperty("集团编码")
    private String groupNumber;
    @ExcelProperty("区域")
    private String substation;
    @ExcelProperty("客户经理")
    private String customerManager;
    @ExcelProperty("客户经理联系电话")
    private String contactWay;
    @ExcelProperty("归属楼宇")
    private String ownerBuilding;
    @ExcelProperty("所在楼层")
    private String floor;
    @ExcelProperty("房间号")
    private String roomNumber;
    @ExcelProperty("企业名称")
    private String enterpriseName;
    @ExcelProperty("行业类型")
    private String industryType;
    @ExcelProperty("员工人数")
    private String employeeNumber;
    @ExcelProperty("集团联系人")
    private String responsiblePerson;
    @ExcelProperty("联系人职务")
    private String position;
    @ExcelProperty("联系人号码")
    private String phoneNumber;
    @ExcelProperty("目前营业状态")
    private String businessSituation;
    @ExcelProperty("运营商选择")
    private String operator;
    @ExcelProperty("异网业务")
    private String crossNetworkService;
    @ExcelProperty("异网到期时间")
    private String endTime2;
    @ExcelProperty("目前洽谈跟进情况")
    private String negotiationStatus;
    @ExcelProperty("走访日期")
    private String visitDate;
    @ExcelProperty("走访情况")
    private String visitInformation;
    @ExcelProperty("创建时间")
    private String writeTime;

    @ExcelProperty("修改时间")
    private String modifyTime;

    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

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

    public String getOwnerBuilding() {
        return ownerBuilding;
    }

    public void setOwnerBuilding(String ownerBuilding) {
        this.ownerBuilding = ownerBuilding;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
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

    public String getBusinessSituation() {
        return businessSituation;
    }

    public void setBusinessSituation(String businessSituation) {
        this.businessSituation = businessSituation;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getCrossNetworkService() {
        return crossNetworkService;
    }

    public void setCrossNetworkService(String crossNetworkService) {
        this.crossNetworkService = crossNetworkService;
    }

    public String getEndTime2() {
        return endTime2;
    }

    public void setEndTime2(String endTime2) {
        this.endTime2 = endTime2;
    }

    public String getNegotiationStatus() {
        return negotiationStatus;
    }

    public void setNegotiationStatus(String negotiationStatus) {
        this.negotiationStatus = negotiationStatus;
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
