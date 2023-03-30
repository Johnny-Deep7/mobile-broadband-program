package com.tencent.wxcloudrun;

public enum MbpType {
    HOTEL("酒店宾馆"),
    COMBUIL("商务楼宇"),
    COMBUILDEL("商务楼宇二级明细"),
    INDUPARK("产业园区"),
    INDUPARKDEL("产业园区二级明细"),
    SHOP("沿街商铺"),
    SHOPDEL("沿街商铺二级明细");

    private String code;

    public String getCode() {
        return code;
    }

    MbpType(String code) {
        this.code = code;
    }


}
