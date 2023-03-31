package com.tencent.wxcloudrun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.PageVo;
import com.tencent.wxcloudrun.dto.ShopDetail;
import com.tencent.wxcloudrun.mapper.ShopDetailMapper;
import com.tencent.wxcloudrun.pto.ShopDetailPTO;;
import com.tencent.wxcloudrun.service.MbpShopDetailService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MbpShopDetailServiceImpl implements MbpShopDetailService {

    @Autowired
    private ShopDetailMapper shopDetailMapper;

    private static ApiResponse  apiResponse = ApiResponse.ok();
    @Override
    public ApiResponse createShopDetail(ShopDetail shopDetail) {
        ShopDetailPTO shopDetailPTO = new ShopDetailPTO();
        BeanUtils.copyProperties(shopDetail, shopDetailPTO);
        int i = shopDetailMapper.insert(shopDetailPTO);
        if(i>0){
            apiResponse.setCode(200);
            apiResponse.setMsg("添加成功");
        }else{
            apiResponse.setCode(400);
            apiResponse.setMsg("添加失败");
        }
        return apiResponse;
    }

    @Override
    public ApiResponse queryShopDetail(PageVo<ShopDetail> pageVo) {
        ShopDetail shopDetail = pageVo.getType();
        if (shopDetail.getShopId() == null || shopDetail.getShopId() == 0){
            apiResponse.setCode(400);
            apiResponse.setMsg("没有传沿街商铺的id，沿街商铺二级明细查询失败！");
            return apiResponse;
        }
        ShopDetailPTO shopDetailPTO = new ShopDetailPTO();
        BeanUtils.copyProperties(shopDetail, shopDetailPTO);

        int pageNo = 0;
        int pageSize = 0;
        if (pageVo.getPage() == 0) {
            pageNo = 1;
        } else {
            pageNo = pageVo.getPage();
        }
        if (pageVo.getSize() == 0) {
            pageSize = 10;
        } else {
            pageSize = pageVo.getSize();
        }

        QueryWrapper<ShopDetailPTO> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(shopDetailPTO.getSubstation())){
            wrapper.like("substation",shopDetailPTO.getSubstation());
        }
        if (StringUtils.isNotBlank(shopDetailPTO.getCustomerManager())){
            wrapper.eq("customer_manager",shopDetailPTO.getCustomerManager());
        }
        if (StringUtils.isNotBlank(shopDetailPTO.getStreetBelong())){
            wrapper.like("street_belong",shopDetailPTO.getStreetBelong());
        }
        if (StringUtils.isNotBlank(shopDetailPTO.getHouseNumber())){
            wrapper.like("house_number",shopDetailPTO.getHouseNumber());
        }
        wrapper.eq("shop_id",shopDetailPTO.getShopId());
        wrapper.orderByDesc("id");

        Page<ShopDetailPTO> page = new Page<>();
        page.setCurrent(pageNo);
        page.setSize(pageSize);
        Page<ShopDetailPTO> shopDetailPTOPage = shopDetailMapper.selectPage(page, wrapper);

        apiResponse.setData(shopDetailPTOPage);
        apiResponse.setCode(200);
        apiResponse.setMsg("查询成功");
        return apiResponse;
    }

    @Override
    public ApiResponse deleteShopDetail(Integer id) {
        int i = shopDetailMapper.deleteById(id);
        if(i>0){
            apiResponse.setCode(200);
            apiResponse.setMsg("删除成功");
        }else{
            apiResponse.setCode(400);
            apiResponse.setMsg("删除失败");
        }
        return apiResponse;
    }

    @Override
    public ApiResponse updateShopDetail(ShopDetail shopDetail) {
        ShopDetailPTO shopDetailPTO = new ShopDetailPTO();
        BeanUtils.copyProperties(shopDetail, shopDetailPTO);
        int i = shopDetailMapper.updateById(shopDetailPTO);
        if(i>0){
            apiResponse.setCode(200);
            apiResponse.setMsg("添加成功");
        }else{
            apiResponse.setCode(400);
            apiResponse.setMsg("添加失败");
        }
        return apiResponse;
    }

    @Override
    public ApiResponse updateShopDetailList(List<ShopDetail> shopDTODetailList){
        ApiResponse apiResponse = new ApiResponse();
        List<ShopDetailPTO> shopDetailPTOList = new ArrayList<>();
        BeanUtils.copyProperties(shopDTODetailList,shopDetailPTOList);
        for(ShopDetailPTO shopDetailPTO : shopDetailPTOList){
            int i = shopDetailMapper.updateById(shopDetailPTO);
            if(i>0){
                apiResponse.setCode(200);
                apiResponse.setMsg("添加成功");
            }else{
                apiResponse.setCode(400);
                apiResponse.setMsg("添加失败");
                break;
            }
        }
        return apiResponse;
    }


    @Override
    public List<ShopDetailPTO> queryAllNameAndID(Integer id) {
        QueryWrapper<ShopDetailPTO> wrapper = new QueryWrapper<>();
        wrapper.eq("SHOP_ID",id);
        wrapper.select("id", "shop_name");
        return shopDetailMapper.selectList(wrapper);
    }
    @Transactional
    public ApiResponse asyncSaveShopDel(List<ShopDetailPTO> shopDetailPTOS) {
        log.info("沿街商铺二级菜单批量存储开始，存储条数{}",shopDetailPTOS.size());
        ApiResponse apiResponse = ApiResponse.ok();
        try{
            Integer saveCount=shopDetailMapper.insertBatchSomeColumn(shopDetailPTOS);
            if(saveCount<=0){
                apiResponse.setMsg("沿街商铺二级菜单保存失败");
                apiResponse.setCode(400);
                return apiResponse;
            }
            apiResponse.setMsg("沿街商铺二级菜单保存成功,成功条数：{"+saveCount+"}");
            shopDetailPTOS.clear();
        }catch (Exception e){
            apiResponse.setMsg(e.getMessage());
            apiResponse.setCode(400);
            return apiResponse;
        }
        return apiResponse;

    }
}
