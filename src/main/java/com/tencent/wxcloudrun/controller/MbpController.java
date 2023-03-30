package com.tencent.wxcloudrun.controller;


import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.*;
import com.tencent.wxcloudrun.pto.IndustrialParkDetailPTO;
import com.tencent.wxcloudrun.pto.MarketingPlanPTO;
import com.tencent.wxcloudrun.service.IndustrialParkDetailService;
import com.tencent.wxcloudrun.service.MarketingPlanService;
import com.tencent.wxcloudrun.service.MbpRouteService;
import com.tencent.wxcloudrun.service.impl.*;
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
    private MbpRouteService routeService;
    @Resource
    private MbpBuildingDetailServiceImpl mbpBuildingDetailServiceImpl;
    @Resource
    private IndustrialParkDetailService industrialParkDetailService;
    @Resource
    private MbpShopServiceImpl mbpShopService;
    @Resource
    private MbpShopDetailServiceImpl mbpShopDetailService;
    @Resource
    private MbpServiceImpl mbpService;
    @Resource
    private MarketingPlanService marketingPlanService;

    @PostMapping(value = "/parsingTable", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void parsingTable(@RequestParam(value = "file") MultipartFile file) {
        log.info("处理表格数据开始！");
        mbpService.parsingTable(file);
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
        return mbpBuildingDetailServiceImpl.createBuildingDetail(commercialBuildingDetail);
    }

    @PostMapping(value = "/listBuildingDetail")
    @ResponseBody
    public ApiResponse queryBuildingDetail(@RequestBody PageVo<CommercialBuildingDetail> pageVo) {
        log.info("查询数据开始！");
        return mbpBuildingDetailServiceImpl.queryBuildingDetail(pageVo);
    }

    @DeleteMapping(value = "/deleteBuildingDetail")
    public ApiResponse deleteBuildingDetail(@RequestParam("id") Integer id) {
        log.info("删除数据开始！");
        return mbpBuildingDetailServiceImpl.deleteBuildingDetail(id);
    }

    @PutMapping(value = "/updateBuildingDetail")
    public ApiResponse updateBuildingDetail(@RequestBody CommercialBuildingDetail commercialBuildingDetail) {
        log.info("更新数据开始！");
        return mbpBuildingDetailServiceImpl.updateBuildingDetail(commercialBuildingDetail);
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
        return mbpShopService.createShop(shopDTO);
    }

    @PostMapping(value = "/listShop")
    @ResponseBody
    public ApiResponse queryShop(@RequestBody PageVo<ShopDTO> pageVo) {
        log.info("查询数据开始！");
        return mbpShopService.queryShop(pageVo);
    }

    @DeleteMapping(value = "/deleteShop")
    public ApiResponse deleteShop(@RequestParam("id") Integer id) {
        log.info("删除数据开始！");
        return mbpShopService.deleteShop(id);
    }

    @PutMapping(value = "/updateShop")
    public ApiResponse updateShop(@RequestBody ShopDTO shopDTO) {
        log.info("更新数据开始！");
        return mbpShopService.updateShop(shopDTO);
    }

    @PostMapping(value = "/createShopDetail")
    public ApiResponse createShopDetail(@RequestBody ShopDetail shopDetail) {
        log.info("添加数据开始！");
        return mbpShopDetailService.createShopDetail(shopDetail);
    }

    @PostMapping(value = "/listShopDetail")
    @ResponseBody
    public ApiResponse queryShopDetail(@RequestBody PageVo<ShopDetail> pageVo) {
        log.info("查询数据开始！");
        return mbpShopDetailService.queryShopDetail(pageVo);
    }

    @DeleteMapping(value = "/deleteShopDetail")
    public ApiResponse deleteShopDetail(@RequestParam("id") Integer id) {
        log.info("删除数据开始！");
        return mbpShopDetailService.deleteShopDetail(id);
    }

    @PutMapping(value = "/updateShopDetail")
    public ApiResponse updateShopDetail(@RequestBody ShopDetail shopDetail) {
        log.info("更新数据开始！");
        return mbpShopDetailService.updateShopDetail(shopDetail);
    }

    @PostMapping(value = "/createMarketingPlan")
    public ApiResponse createMarketingPlan(@RequestBody MarketingPlanDTO marketingPlanDTO) {
        log.info("添加数据开始！");
        MarketingPlanPTO marketingPlanPTO = new MarketingPlanPTO();
        BeanUtils.copyProperties(marketingPlanDTO, marketingPlanPTO);
        return marketingPlanService.create(marketingPlanPTO);
    }

    @PostMapping(value = "/listMarketingPlan")
    @ResponseBody
    public ApiResponse queryMarketingPlan(@RequestBody PageVo<RequestEntity> pageVo) {
        log.info("查询数据开始！");
        return marketingPlanService.query(pageVo);
    }

    @DeleteMapping(value = "/deleteMarketingPlan")
    public ApiResponse deleteMarketingPlan(@RequestParam("id") Integer id) {
        log.info("删除数据开始！");
        return marketingPlanService.delete(id);
    }

    @PutMapping(value = "/updateMarketingPlan")
    public ApiResponse updateMarketingPlan(@RequestBody MarketingPlanDTO marketingPlanDTO) {
        log.info("更新数据开始！");
        MarketingPlanPTO marketingPlanPTO = new MarketingPlanPTO();
        BeanUtils.copyProperties(marketingPlanDTO, marketingPlanPTO);
        return marketingPlanService.update(marketingPlanPTO);
    }
}
