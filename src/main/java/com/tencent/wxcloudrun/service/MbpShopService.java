package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.PageVo;
import com.tencent.wxcloudrun.dto.ShopDTO;

public interface MbpShopService {
    public ApiResponse createShop(ShopDTO shopDTO);

    public ApiResponse queryShop(PageVo<ShopDTO> pageVo);

    public ApiResponse deleteShop(Integer id);

    public ApiResponse updateShop(ShopDTO shopDTO);
}
