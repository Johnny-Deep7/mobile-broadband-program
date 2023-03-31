package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.PageVo;
import com.tencent.wxcloudrun.dto.ShopDetail;
import com.tencent.wxcloudrun.pto.ShopDetailPTO;

import java.util.List;

public interface MbpShopDetailService {
    public ApiResponse createShopDetail(ShopDetail shopDetail);
    public ApiResponse queryShopDetail(PageVo<ShopDetail> pageVo);
    public ApiResponse deleteShopDetail(Integer id);
    public ApiResponse updateShopDetail(ShopDetail shopDetail);
    public List<ShopDetailPTO> queryAllNameAndID(Integer id);
}
