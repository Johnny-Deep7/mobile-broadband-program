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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;


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
    public ApiResponse parsingTable(@RequestParam(value = "file") MultipartFile file,
                             @RequestParam(value = "marketType") String marketType,
                             @RequestParam(required = false,value = "id")Integer id) {
        log.info("处理{}表格数据开始！",marketType);
        return mbpService.parsingTable(file,marketType,id);
    }

    @GetMapping(value = "/downloadTable")
    public ApiResponse downloadTable(HttpServletResponse response,
                                     @RequestParam(value = "marketType") String marketType,
                                     @RequestParam(required = false,value = "substation")String substation,
                                     @RequestParam(required = false,value = "customerManager")String customerManager,
                                     @RequestParam(required = false,value = "startTime")String startTime,
                                     @RequestParam(required = false,value = "endTime")String endTime,
                                     @RequestParam(required = false,value = "subId")String subId) throws IOException {
        log.info("导出数据开始！");
        return mbpService.downloadTable(response,marketType,substation,customerManager,startTime,endTime,subId);
    }

    @PostMapping(value = "/create")
    public ApiResponse create(@RequestBody RequestEntity requestEntity) {
        log.info("添加{}数据开始！",requestEntity.getMarketType());
        return routeService.create(requestEntity);
    }

    @PostMapping(value = "/list")
    @ResponseBody
    public ApiResponse query(@RequestBody PageVo<RequestEntity> pageVo) {
        log.info("查询{}数据开始！",pageVo.getType().getMarketType());
        return routeService.query(pageVo);
    }

    @DeleteMapping(value = "/delete")
    public ApiResponse delete(@RequestParam("id") Integer id,@RequestParam("marketType") String marketType) {
        log.info("删除{}数据开始！",marketType);
        return routeService.delete(id,marketType);
    }

    @PutMapping(value = "/update")
    public ApiResponse update(@RequestBody RequestEntity requestEntity) {
        log.info("更新{}数据开始！",requestEntity.getMarketType());
        return routeService.update(requestEntity);
    }

    @PutMapping(value = "/listUpdate")
    public ApiResponse update(@RequestBody List<RequestEntity> requestEntity) {
        log.info("批量更新{}数据开始！",requestEntity.get(0).getMarketType());
        return routeService.listUpdate(requestEntity);
    }

    @PostMapping(value = "/createBuildingDetail")
    public ApiResponse createBuildingDetail(@RequestBody CommercialBuildingDetail commercialBuildingDetail) {
        log.info("添加商务楼宇二级明细数据开始！");
        return mbpBuildingDetailServiceImpl.createBuildingDetail(commercialBuildingDetail);
    }

    @PostMapping(value = "/listBuildingDetail")
    @ResponseBody
    public ApiResponse queryBuildingDetail(@RequestBody PageVo<CommercialBuildingDetail> pageVo) {
        log.info("查询商务楼宇二级明细数据开始！");
        return mbpBuildingDetailServiceImpl.queryBuildingDetail(pageVo);
    }

    @DeleteMapping(value = "/deleteBuildingDetail")
    public ApiResponse deleteBuildingDetail(@RequestParam("id") Integer id) {
        log.info("删除商务楼宇二级明细数据开始！");
        return mbpBuildingDetailServiceImpl.deleteBuildingDetail(id);
    }

    @PutMapping(value = "/updateBuildingDetail")
    public ApiResponse updateBuildingDetail(@RequestBody CommercialBuildingDetail commercialBuildingDetail) {
        log.info("更新商务楼宇二级明细数据开始！");
        return mbpBuildingDetailServiceImpl.updateBuildingDetail(commercialBuildingDetail);
    }

    @PutMapping(value = "/updateBuildingDetailList")
    public ApiResponse updateBuildingDetailList(@RequestBody List<CommercialBuildingDetail> commercialBuildingDetailList) {
        log.info("批量更新商务楼宇二级明细数据开始！");
        return mbpBuildingDetailServiceImpl.updateBuildingDetailList(commercialBuildingDetailList);
    }

    @PostMapping(value = "/createIndustrialParkDetail")
    public ApiResponse createIndustrialParkDetail(@RequestBody IndustrialParkDetail industrialParkDetail) {
        log.info("添加产业园区二级明细数据开始！");
        IndustrialParkDetailPTO industrialParkDetailPTO = new IndustrialParkDetailPTO();
        BeanUtils.copyProperties(industrialParkDetail, industrialParkDetailPTO);
        return industrialParkDetailService.create(industrialParkDetailPTO);
    }

    @PostMapping(value = "/listIndustrialParkDetail")
    @ResponseBody
    public ApiResponse queryIndustrialParkDetail(@RequestBody PageVo<IndustrialParkDetail> pageVo) {
        log.info("查询产业园区二级明细数据开始！");
        return industrialParkDetailService.query(pageVo);
    }

    @DeleteMapping(value = "/deleteIndustrialParkDetail")
    public ApiResponse deleteIndustrialParkDetail(@RequestParam("id") Integer id) {
        log.info("删除产业园区二级明细数据开始！");
        return industrialParkDetailService.delete(id);
    }
    @PutMapping(value = "/updateIndustrialParkDetail")
    public ApiResponse updateIndustrialParkDetail(@RequestBody IndustrialParkDetail industrialParkDetail) {
        log.info("更新产业园区二级明细数据开始！");
        IndustrialParkDetailPTO industrialParkDetailPTO = new IndustrialParkDetailPTO();
        BeanUtils.copyProperties(industrialParkDetail, industrialParkDetailPTO);
        return industrialParkDetailService.update(industrialParkDetailPTO);
    }

    @PutMapping(value = "/updateIndustrialParkDetailList")
    public ApiResponse updateBuildingDetail(@RequestBody List<IndustrialParkDetail> industrialParkDetailList) {
        log.info("批量更新产业园区二级明细数据开始！");
        return industrialParkDetailService.updateIndustrialParkDetailList(industrialParkDetailList);
    }

    @PostMapping(value = "/createShop")
    public ApiResponse createShop(@RequestBody ShopDTO shopDTO) {
        log.info("添加沿街商铺数据开始！");
        return mbpShopService.createShop(shopDTO);
    }

    @PostMapping(value = "/listShop")
    @ResponseBody
    public ApiResponse queryShop(@RequestBody PageVo<ShopDTO> pageVo) {
        log.info("查询沿街商铺数据开始！");
        return mbpShopService.queryShop(pageVo);
    }

    @DeleteMapping(value = "/deleteShop")
    public ApiResponse deleteShop(@RequestParam("id") Integer id) {
        log.info("删除沿街商铺数据开始！");
        return mbpShopService.deleteShop(id);
    }

    @PutMapping(value = "/updateShop")
    public ApiResponse updateShop(@RequestBody ShopDTO shopDTO) {
        log.info("更新沿街商铺数据开始！");
        return mbpShopService.updateShop(shopDTO);
    }

    @PutMapping(value = "/updateShopList")
    public ApiResponse updateShopList(@RequestBody List<ShopDTO> shopDTOList) {
        log.info("批量更新沿街商铺数据开始！");
        return mbpShopService.updateShopList(shopDTOList);
    }

    @PostMapping(value = "/createShopDetail")
    public ApiResponse createShopDetail(@RequestBody ShopDetail shopDetail) {
        log.info("添加沿街商铺二级明细数据开始！");
        return mbpShopDetailService.createShopDetail(shopDetail);
    }

    @PostMapping(value = "/listShopDetail")
    @ResponseBody
    public ApiResponse queryShopDetail(@RequestBody PageVo<ShopDetail> pageVo) {
        log.info("查询沿街商铺二级明细数据开始！");
        return mbpShopDetailService.queryShopDetail(pageVo);
    }

    @DeleteMapping(value = "/deleteShopDetail")
    public ApiResponse deleteShopDetail(@RequestParam("id") Integer id) {
        log.info("删除沿街商铺二级明细数据开始！");
        return mbpShopDetailService.deleteShopDetail(id);
    }

    @PutMapping(value = "/updateShopDetail")
    public ApiResponse updateShopDetail(@RequestBody ShopDetail shopDetail) {
        log.info("更新沿街商铺二级明细数据开始！");
        return mbpShopDetailService.updateShopDetail(shopDetail);
    }

    @PutMapping(value = "/updateShopDetailList")
    public ApiResponse updateShopDetailList(@RequestBody List<ShopDetail> shopDetailList) {
        log.info("批量更新商铺二级明细数据开始！");
        return mbpShopDetailService.updateShopDetailList(shopDetailList);
    }

    @PostMapping(value = "/createMarketingPlan")
    public ApiResponse createMarketingPlan(@RequestBody MarketingPlanDTO marketingPlanDTO) {
        log.info("添加营销计划数据开始！");
        MarketingPlanPTO marketingPlanPTO = new MarketingPlanPTO();
        BeanUtils.copyProperties(marketingPlanDTO, marketingPlanPTO);
        return marketingPlanService.create(marketingPlanPTO);
    }

    @PostMapping(value = "/listMarketingPlan")
    @ResponseBody
    public ApiResponse queryMarketingPlan(@RequestBody PageVo<MarketingPlanDTO> pageVo) {
        log.info("查询营销计划数据开始！");
        return marketingPlanService.query(pageVo);
    }

    @DeleteMapping(value = "/deleteMarketingPlan")
    public ApiResponse deleteMarketingPlan(@RequestParam("id") Integer id) {
        log.info("删除营销计划数据开始！");
        return marketingPlanService.delete(id);
    }

    @PutMapping(value = "/updateMarketingPlan")
    public ApiResponse updateMarketingPlan(@RequestBody MarketingPlanDTO marketingPlanDTO) {
        log.info("更新营销计划数据开始！");
        MarketingPlanPTO marketingPlanPTO = new MarketingPlanPTO();
        BeanUtils.copyProperties(marketingPlanDTO, marketingPlanPTO);
        return marketingPlanService.update(marketingPlanPTO);
    }

    @GetMapping(value = "/fullExport")
    public ApiResponse fullExport(@RequestParam("startTime")String startTime,@RequestParam("endTime")String endTime) {
        log.info("统计分析查询开始！");
        return mbpService.fullExport(startTime,endTime);
    }

    @GetMapping(value = "/statistics")
    public ApiResponse statistics(@RequestParam("startTime")String startTime,@RequestParam("endTime")String endTime,@RequestParam("substation")String substation) {
        log.info("按照分局查询开始！");
        return mbpService.statistics(startTime,endTime,substation);
    }

    @GetMapping(value = "/downloadFullExport")
    public ApiResponse downloadFullExport(HttpServletResponse response,
                                     @RequestParam(required = false,value = "startTime")String startTime,
                                     @RequestParam(required = false,value = "endTime")String endTime
                                     ) {
        log.info("导出数据开始！");
        return mbpService.downloadFullExport(response,startTime,endTime);
    }

    @GetMapping(value = "/downloadStatistics")
    public ApiResponse downloadStatistics(HttpServletResponse response,
                                          @RequestParam(required = false,value = "startTime")String startTime,
                                          @RequestParam(required = false,value = "endTime")String endTime,
                                          @RequestParam("substation")String substation) {
        log.info("导出数据开始！");
        return mbpService.downloadStatistics(response,startTime,endTime,substation);
    }

}
