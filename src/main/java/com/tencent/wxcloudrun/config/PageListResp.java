//package com.tencent.wxcloudrun.config;
//
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.math.BigInteger;
//import java.util.List;
//
///**
// * 分页返回使用的结构体
// *
// * @author Y
// */
//@NoArgsConstructor
//@AllArgsConstructor
//@Data
//public class PageListResp<T> {
//
//    /**
//     * 总个数
//     */
//    @ApiModelProperty(value = "满足查询条件的总个数", required = true)
//    private BigInteger total;
//
//    /**
//     * 分页查询的列表
//     */
//    @ApiModelProperty(value = "分页查询返回的具体结构体", required = true)
//    private List<? extends T> content;
//}
