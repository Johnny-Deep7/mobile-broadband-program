package com.tencent.wxcloudrun.controller;


import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.*;
import com.tencent.wxcloudrun.pto.IndustrialParkDetailPTO;
import com.tencent.wxcloudrun.service.IndustrialParkDetailService;
import com.tencent.wxcloudrun.service.MpbRouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;


@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/mbp")
public class MbpController {

    @Resource
    MpbRouteService routeService;
    @Resource
    IndustrialParkDetailService industrialParkDetailService;

    @PostMapping(value = "/parsingTable", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void parsingTable(@RequestParam(value = "file") MultipartFile file) {
        log.info("处理表格数据开始！");

    }

    @PostMapping(value = "/create")
    public ApiResponse create(@RequestBody RequestEntity requestEntity) {
        log.info("添加数据开始！");
        return routeService.create(requestEntity);
    }

    @PostMapping(value = "/list")
    @ResponseBody
    public ApiResponse query(@RequestBody PageVo<RequestEntity> pageVo) {
        log.info("查询数据开始！");
        return routeService.query(pageVo);
    }

    @DeleteMapping(value = "/delete")
    public ApiResponse delete(@RequestParam("id") Integer id,@RequestParam("marketType") String marketType) {
        log.info("删除数据开始！");
        return routeService.delete(id,marketType);
    }

    @PutMapping(value = "/update")
    public ApiResponse update(@RequestBody RequestEntity requestEntity) {
        log.info("更新数据开始！");
        return routeService.update(requestEntity);
    }

    @PostMapping(value = "/createBuildingDetail")
    public ApiResponse createBuildingDetail(@RequestBody CommercialBuildingDetail commercialBuildingDetail) {
        log.info("添加数据开始！");
        return routeService.createBuildingDetail(commercialBuildingDetail);
    }

    @PostMapping(value = "/listBuildingDetail")
    @ResponseBody
    public ApiResponse queryBuildingDetail(@RequestBody PageVo<CommercialBuildingDetail> pageVo) {
        log.info("查询数据开始！");
        return routeService.queryBuildingDetail(pageVo);
    }

    @DeleteMapping(value = "/deleteBuildingDetail")
    public ApiResponse deleteBuildingDetail(@RequestParam("id") Integer id) {
        log.info("删除数据开始！");
        return routeService.deleteBuildingDetail(id);
    }

    @PutMapping(value = "/updateBuildingDetail")
    public ApiResponse updateBuildingDetail(@RequestBody CommercialBuildingDetail commercialBuildingDetail) {
        log.info("更新数据开始！");
        return routeService.updateBuildingDetail(commercialBuildingDetail);
    }

    @PostMapping(value = "/createIndustrialParkDetail")
    public ApiResponse createIndustrialParkDetail(@RequestBody IndustrialParkDetail industrialParkDetail) {
        log.info("添加数据开始！");
        IndustrialParkDetailPTO industrialParkDetailPTO = new IndustrialParkDetailPTO();
        BeanUtils.copyProperties(industrialParkDetail, industrialParkDetailPTO);
        return industrialParkDetailService.create(industrialParkDetailPTO);
    }

    @PostMapping(value = "/listIndustrialParkDetail")
    @ResponseBody
    public ApiResponse queryIndustrialParkDetail(@RequestBody PageVo<IndustrialParkDetail> pageVo) {
        log.info("查询数据开始！");
        return industrialParkDetailService.query(pageVo);
    }

    @DeleteMapping(value = "/deleteIndustrialParkDetail")
    public ApiResponse deleteIndustrialParkDetail(@RequestParam("id") Integer id) {
        log.info("删除数据开始！");
        return industrialParkDetailService.delete(id);
    }
    @PutMapping(value = "/updateIndustrialParkDetail")
    public ApiResponse updateIndustrialParkDetail(@RequestBody IndustrialParkDetail industrialParkDetail) {
        log.info("更新数据开始！");
        IndustrialParkDetailPTO industrialParkDetailPTO = new IndustrialParkDetailPTO();
        BeanUtils.copyProperties(industrialParkDetail, industrialParkDetailPTO);
        return industrialParkDetailService.update(industrialParkDetailPTO);
    }

    @PostMapping(value = "/createShop")
    public ApiResponse createShop(@RequestBody ShopDTO shopDTO) {
        log.info("添加数据开始！");
        return routeService.createShop(shopDTO);
    }

    @PostMapping(value = "/listShop")
    @ResponseBody
    public ApiResponse queryShop(@RequestBody PageVo<ShopDTO> pageVo) {
        log.info("查询数据开始！");
        return routeService.queryShop(pageVo);
    }

    @DeleteMapping(value = "/deleteShop")
    public ApiResponse deleteShop(@RequestParam("id") Integer id) {
        log.info("删除数据开始！");
        return routeService.deleteShop(id);
    }

    @PutMapping(value = "/updateShop")
    public ApiResponse updateShop(@RequestBody ShopDTO shopDTO) {
        log.info("更新数据开始！");
        return routeService.updateShop(shopDTO);
    }

    @PostMapping(value = "/createShopDetail")
    public ApiResponse createShopDetail(@RequestBody ShopDetail shopDetail) {
        log.info("添加数据开始！");
        return routeService.createShopDetail(shopDetail);
    }

    @PostMapping(value = "/listShopDetail")
    @ResponseBody
    public ApiResponse queryShopDetail(@RequestBody PageVo<ShopDetail> pageVo) {
        log.info("查询数据开始！");
        return routeService.queryShopDetail(pageVo);
    }

    @DeleteMapping(value = "/deleteShopDetail")
    public ApiResponse deleteShopDetail(@RequestParam("id") Integer id) {
        log.info("删除数据开始！");
        return routeService.deleteShopDetail(id);
    }

    @PutMapping(value = "/updateShopDetail")
    public ApiResponse updateShopDetail(@RequestBody ShopDetail shopDetail) {
        log.info("更新数据开始！");
        return routeService.updateShopDetail(shopDetail);
    }
}
