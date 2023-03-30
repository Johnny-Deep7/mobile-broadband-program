package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.PageVo;
import com.tencent.wxcloudrun.dto.ShopDetail;

public interface MbpShopDetailService {
    public ApiResponse createShopDetail(ShopDetail shopDetail);
    public ApiResponse queryShopDetail(PageVo<ShopDetail> pageVo);
    public ApiResponse deleteShopDetail(Integer id);
    public ApiResponse updateShopDetail(ShopDetail shopDetail);
    public ApiResponse queryAllNameAndID();
}
