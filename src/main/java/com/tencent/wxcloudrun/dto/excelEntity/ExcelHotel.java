package com.tencent.wxcloudrun.dto.excelEntity;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 酒店宾馆表格字段
 */
public class ExcelHotel {
    @ExcelProperty(value = "分局")
    private String substation;

    @ExcelProperty(value = "客户经理")
    private String customerManager;
    //联系方式
    @ExcelProperty(value = "联系方式")
    private String contactWay;
    //酒店宾馆名称
    @ExcelProperty(value = "酒店宾馆名称")
    private String hotelName;
    //覆盖名称（或未覆盖）
    @ExcelProperty(value = "覆盖名称")
    private String isCovered;
    //房间数
    @ExcelProperty(value = "房间数")
    private String roomNumber;
    //集团编号（已建）
    @ExcelProperty(value = "集团编号")
    private String groupNumber;
    //地址
    @ExcelProperty(value = "地址")
    private String address;
    //酒店类型（星级、连锁、民宿）
    @ExcelProperty(value = "酒店类型")
    private String hotelType;
    //现用运营商（移动、电信、联通）
    @ExcelProperty(value = "现用运营商")
    private String operator;
    //本网到期时间）
    @ExcelProperty(value = "本网到期时间")
    private String endTime1;
    //异网资费
    @ExcelProperty(value = "异网资费")
    private String internetCharge;
    //异网业务到期时间
    @ExcelProperty(value = "异网业务到期时间")
    private String endTime2;
    //酒店负责人
    @ExcelProperty(value = "酒店负责人")
    private String responsiblePerson;
    //联系人职务
    @ExcelProperty(value = "联系人职务")
    private String position;
    //联系人电话
    @ExcelProperty(value = "联系人电话")
    private String phoneNumber;
    //备注（当前攻坚主要问题 覆盖/装机/关系/政策）
    @ExcelProperty(value = "备注")
    private String difficultPoint;
    //走访日期
    @ExcelProperty(value = "走访日期")
    private String visitDate;
    //走访情况
    @ExcelProperty(value = "走访情况")
    private String visitInformation;
    @ExcelProperty("创建时间")
    private String writeTime;

    @ExcelProperty("修改时间")
    private String modifyTime;

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

    public String getIsCovered() {
        return isCovered;
    }

    public void setIsCovered(String isCovered) {
        this.isCovered = isCovered;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHotelType() {
        return hotelType;
    }

    public void setHotelType(String hotelType) {
        this.hotelType = hotelType;
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

    public String getDifficultPoint() {
        return difficultPoint;
    }

    public void setDifficultPoint(String difficultPoint) {
        this.difficultPoint = difficultPoint;
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
