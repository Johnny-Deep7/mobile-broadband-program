package com.tencent.wxcloudrun.dto.excelEntity;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 沿街商铺二级明细表格字段
 */
public class ExcelShopDel {
    @ExcelProperty("办理号码")
    private String handlingNumber;
    @ExcelProperty("区域")
    private String substation;
    @ExcelProperty("客户经理")
    private String customerManager;
    @ExcelProperty("街长")
    private String streetChief;
    @ExcelProperty("归属沿街")
    private String streetBelong;
    @ExcelProperty("门牌号")
    private String houseNumber;
    @ExcelProperty("商铺名称")
    private String shopName;
    @ExcelProperty("行业类型")
    private String industryType;
    @ExcelProperty("集团联系人")
    private String responsiblePerson;
    @ExcelProperty("联系人号码")
    private String phoneNumber;
    @ExcelProperty("目前营业状态")
    private String businessStatus;
    @ExcelProperty("运营商选择")
    private String operator;
    @ExcelProperty("异网业务")
    private String crossNetworkService;
    @ExcelProperty("异网业务到期时间")
    private String endTime2;
    @ExcelProperty("使用宽带账号")
    private String broadbandAccount;
    @ExcelProperty("目前洽谈跟进情况")
    private String talkStatus;
    @ExcelProperty("走访日期")
    private String visitDate;
    @ExcelProperty("走访情况")
    private String visitInformation;

    public String getHandlingNumber() {
        return handlingNumber;
    }

    public void setHandlingNumber(String handlingNumber) {
        this.handlingNumber = handlingNumber;
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

    public String getStreetChief() {
        return streetChief;
    }

    public void setStreetChief(String streetChief) {
        this.streetChief = streetChief;
    }

    public String getStreetBelong() {
        return streetBelong;
    }

    public void setStreetBelong(String streetBelong) {
        this.streetBelong = streetBelong;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBusinessStatus() {
        return businessStatus;
    }

    public void setBusinessStatus(String businessStatus) {
        this.businessStatus = businessStatus;
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

    public String getBroadbandAccount() {
        return broadbandAccount;
    }

    public void setBroadbandAccount(String broadbandAccount) {
        this.broadbandAccount = broadbandAccount;
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
