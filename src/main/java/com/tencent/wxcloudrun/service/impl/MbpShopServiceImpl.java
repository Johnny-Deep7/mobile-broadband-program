package com.tencent.wxcloudrun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.CommercialBuildingDetail;
import com.tencent.wxcloudrun.dto.PageVo;
import com.tencent.wxcloudrun.dto.ShopDTO;
import com.tencent.wxcloudrun.mapper.ShopMapper;
import com.tencent.wxcloudrun.pto.ShopPTO;
import com.tencent.wxcloudrun.service.MbpShopService;
import com.tencent.wxcloudrun.utils.CopyListUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class MbpShopServiceImpl implements MbpShopService {

    @Autowired
    private ShopMapper shopMapper;

    private static ApiResponse  apiResponse = ApiResponse.ok();
    @Override
    public ApiResponse createShop(ShopDTO shopDTO) {
        ShopPTO shopPTO = new ShopPTO();
        BeanUtils.copyProperties(shopDTO,shopPTO);
        Date writeTime = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        shopPTO.setWriteTime(format.format(writeTime));
        shopPTO.setModifyTime(format.format(writeTime));
        int i = shopMapper.insert(shopPTO);
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
    public ApiResponse queryShop(PageVo<ShopDTO> pageVo) {
        ShopDTO shopDTO = pageVo.getType();
        ShopPTO shopPTO = new ShopPTO();
        BeanUtils.copyProperties(shopDTO,shopPTO);
        ApiResponse apiResponse = new ApiResponse();

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

        QueryWrapper<ShopPTO> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(shopPTO.getSubstation())){
            wrapper.like("substation",shopPTO.getSubstation());
        }
        if (StringUtils.isNotBlank(shopPTO.getGimsPoint())){
            wrapper.eq("gims_point",shopPTO.getGimsPoint());
        }
        if (StringUtils.isNotBlank(shopPTO.getCellName())){
            wrapper.like("cell_name",shopPTO.getCellName());
        }
        if (StringUtils.isNotBlank(shopPTO.getAddress())){
            wrapper.like("address",shopPTO.getAddress());
        }
        wrapper.orderByDesc("id");

        Page<ShopPTO> page = new Page<>();
        page.setCurrent(pageNo);
        page.setSize(pageSize);
        Page<ShopPTO> shopPage = shopMapper.selectPage(page, wrapper);

        apiResponse.setData(shopPage);
        apiResponse.setCode(200);
        apiResponse.setMsg("查询成功");
        return apiResponse;
    }

    @Override
    public ApiResponse deleteShop(Integer id) {
        int i = shopMapper.deleteById(id);
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
    public ApiResponse updateShop(ShopDTO shopDTO) {
        ShopPTO shopPTO = new ShopPTO();
        BeanUtils.copyProperties(shopDTO,shopPTO);
        Date modifyTime = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        shopPTO.setModifyTime(format.format(modifyTime));
        int i = shopMapper.updateById(shopPTO);
        if(i>0){
            apiResponse.setCode(200);
            apiResponse.setMsg("修改成功");
        }else{
            apiResponse.setCode(400);
            apiResponse.setMsg("修改失败");
        }
        return apiResponse;
    }

    @Override
    public ApiResponse updateShopList(List<ShopDTO> ShopDTOList){
        ApiResponse apiResponse = new ApiResponse();
        for(ShopDTO shopDTO : ShopDTOList){
            apiResponse = updateShop(shopDTO);
            if(200 != apiResponse.getCode()){
                break;
            }
        }
        return apiResponse;
    }

    @Override
    public List<ShopPTO> queryAllNameAndID() {
        QueryWrapper<ShopPTO> wrapper = new QueryWrapper<>();
        wrapper.select("id", "cell_name");
        return shopMapper.selectList(wrapper);
    }

    @Transactional
    public ApiResponse asyncSaveShop(List<ShopPTO> shopPTOS) {
        log.info("沿街商铺批量存储开始，存储条数{}",shopPTOS.size());
        ApiResponse apiResponse = ApiResponse.ok();
        try{
            Integer saveCount=shopMapper.insertBatchSomeColumn(shopPTOS);
            if(saveCount<=0){
                apiResponse.setMsg("沿街商铺保存失败");
                apiResponse.setCode(400);
                return apiResponse;
            }
            apiResponse.setMsg("沿街商铺保存成功,成功条数：{"+saveCount+"}");
            shopPTOS.clear();
        }catch (Exception e){
            String message = e.getMessage();
            int i = message.indexOf("###",10);
            String substring = message.substring(0, i);
            apiResponse.setMsg(substring);
            apiResponse.setCode(400);
            return apiResponse;
        }
        return apiResponse;

    }
}
