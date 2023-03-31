package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.PageVo;
import com.tencent.wxcloudrun.dto.ShopDTO;
import com.tencent.wxcloudrun.pto.ShopPTO;

import java.util.List;


public interface MbpShopService {
    public ApiResponse createShop(ShopDTO shopDTO);

    public ApiResponse queryShop(PageVo<ShopDTO> pageVo);

    public ApiResponse deleteShop(Integer id);

    public ApiResponse updateShop(ShopDTO shopDTO);

    public List<ShopPTO> queryAllNameAndID();

    public ApiResponse updateShopList(List<ShopDTO> ShopDTOList);
}
