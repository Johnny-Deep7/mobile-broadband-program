package com.tencent.wxcloudrun.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.HotelDTO;
import com.tencent.wxcloudrun.dto.PageVo;
import com.tencent.wxcloudrun.service.MpbRouteService;
import com.tencent.wxcloudrun.service.impl.MbpHotelServiceImpl;
import lombok.extern.slf4j.Slf4j;
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

    @PostMapping(value = "/parsingTable", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void parsingTable(@RequestParam(value = "file") MultipartFile file) {
        log.info("处理表格数据开始！");

    }

    @PostMapping(value = "/create")
    public ApiResponse create(@RequestBody HotelDTO hotelDTO) {
        log.info("添加宾馆数据开始！");
        return routeService.create(hotelDTO);
    }

    @PostMapping(value = "/list")
    @ResponseBody
    public ApiResponse query(@RequestBody PageVo<HotelDTO> pageVo) {
        log.info("查询宾馆数据开始！");
        return routeService.query(pageVo);
    }

    @DeleteMapping(value = "/delete")
    public ApiResponse delete(@RequestParam("id") Integer id,@RequestParam("marketType") String marketType) {
        log.info("删除宾馆数据开始！");
        return routeService.delete(id,marketType);
    }

    @PutMapping(value = "/update")
    public ApiResponse update(@RequestBody HotelDTO hotelDTO) {
        log.info("更新宾馆数据开始！");
        return routeService.update(hotelDTO);
    }
}
