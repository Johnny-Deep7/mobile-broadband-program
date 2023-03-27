package com.tencent.wxcloudrun.controller;


import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.HotelDTO;
import com.tencent.wxcloudrun.service.impl.MbpServiceImpl;
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
    MbpServiceImpl mbpService;

    @PostMapping(value = "/parsingTable", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void parsingTable(@RequestParam(value = "file") MultipartFile file) {
        log.info("处理表格数据开始！");

    }

    @PostMapping(value = "/create")
    public ApiResponse create(@RequestBody HotelDTO hotelDTO) {
        log.info("添加宾馆数据开始！");
        return mbpService.create(hotelDTO);
    }

    @PostMapping(value = "/listHotel")
    public ApiResponse listHotel(@RequestBody HotelDTO hotelDTO) {
        log.info("查询宾馆数据开始！");
        return mbpService.listHotel(hotelDTO);
    }

    @DeleteMapping(value = "/delete")
    public ApiResponse delete(@RequestBody HotelDTO hotelDTO) {
        log.info("删除宾馆数据开始！");
        return mbpService.delete(hotelDTO);
    }

    @PutMapping(value = "/update")
    public ApiResponse update(@RequestBody HotelDTO hotelDTO) {
        log.info("更新宾馆数据开始！");
        return mbpService.update(hotelDTO);
    }
}
