package com.tencent.wxcloudrun.service.impl;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tencent.wxcloudrun.MbpType;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.RequestEntity;
import com.tencent.wxcloudrun.dto.PageVo;
import com.tencent.wxcloudrun.mapper.HotelMapper;
import com.tencent.wxcloudrun.pto.HotelPTO;
import com.tencent.wxcloudrun.service.MbpHotelService;
import lombok.extern.slf4j.Slf4j;

//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import javax.annotation.Resource;
import java.io.*;

@Service
@Slf4j
public class MbpHotelServiceImpl implements MbpHotelService {
    @Resource
    private HotelMapper hotelMapper;




    @Override
    public ApiResponse create(HotelPTO hotelPTO) {
        ApiResponse apiResponse = ApiResponse.ok();

        int i = hotelMapper.insert(hotelPTO);
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
    public ApiResponse query(PageVo<RequestEntity> pageVo) {
        RequestEntity requestEntity = pageVo.getType();
        HotelPTO hotelPTO = new HotelPTO();
        BeanUtils.copyProperties(requestEntity,hotelPTO);
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

        QueryWrapper<HotelPTO> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(hotelPTO.getSubstation())){
            wrapper.like("substation",hotelPTO.getSubstation());
        }
        if (StringUtils.isNotBlank(hotelPTO.getCustomerManager())){
            wrapper.eq("customer_manager",hotelPTO.getCustomerManager());
        }
        if (StringUtils.isNotBlank(hotelPTO.getHotelName())){
            wrapper.like("hotel_name",hotelPTO.getHotelName());
        }
        if (StringUtils.isNotBlank(hotelPTO.getHotelType())){
            wrapper.eq("hotel_type",hotelPTO.getHotelType());
        }

        Page<HotelPTO> page = new Page<>();
        page.setCurrent(pageNo);
        page.setSize(pageSize);
        Page<HotelPTO> hotelPTOPage = hotelMapper.selectPage(page, wrapper);

        apiResponse.setData(hotelPTOPage);
        apiResponse.setCode(200);
        apiResponse.setMsg("查询成功");
        return apiResponse;
    }

    @Override
    public ApiResponse delete(Integer id) {
        ApiResponse apiResponse = new ApiResponse();
        int i = hotelMapper.deleteById(id);
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
    public ApiResponse update(HotelPTO hotelPTO) {
        ApiResponse apiResponse = new ApiResponse();
        int i = hotelMapper.updateById(hotelPTO);
        if(i>0){
            apiResponse.setCode(200);
            apiResponse.setMsg("修改成功");
        }else{
            apiResponse.setCode(400);
            apiResponse.setMsg("修改失败");
        }
        return apiResponse;
    }


    /**
     * 解析导入的文件内容
     * @param file
     * @throws Exception
     */
    public void parseExcel(File file)throws Exception{


        int sheetCount = ExcelUtil.getReader(file).getSheetCount();

        for (MbpType mbpType:MbpType.values()) {
            ExcelReader reader = ExcelUtil.getReader(file, mbpType.getCode());
            reader.read();
        }






        return ;
    }


}
