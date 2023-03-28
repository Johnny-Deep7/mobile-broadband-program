package com.tencent.wxcloudrun.service.impl;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tencent.wxcloudrun.MbpType;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.PageVo;
import com.tencent.wxcloudrun.dto.RequestEntity;
import com.tencent.wxcloudrun.mapper.CommercialBuildingMapper;
import com.tencent.wxcloudrun.pto.CommercialBuildingPTO;
import com.tencent.wxcloudrun.pto.HotelPTO;
import com.tencent.wxcloudrun.service.MbpBuildingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;

@Service
@Slf4j
public class MbpBuildingServiceImpl implements MbpBuildingService {
    @Resource
    private CommercialBuildingMapper commercialBuildingMapper;
    @Override
    public void parsingTable(MultipartFile multipartFile) {
        File file = null;
        if (multipartFile!=null){
            try {
                file = multipartFileToFile(multipartFile);
                if(!file.exists() || file.length() == 0) {
                    throw new RuntimeException("文件为空！");
                }
                parseExcel(file);

            } catch (Exception e) {
                throw new RuntimeException("文件解析失败！");
            }
        }
    }
    /**
     * MultipartFile 转 File
     *
     * @param multipartFile
     * @throws Exception
     */
    public static File multipartFileToFile(MultipartFile multipartFile) throws Exception {

        File file = null;
        if (!(multipartFile.equals("") || multipartFile.getSize() <= 0)) {
            InputStream ins = null;
            ins = multipartFile.getInputStream();
            file = new File(multipartFile.getOriginalFilename());
            inputStreamToFile(ins, file);
            ins.close();
        }
        return file;
    }

    //获取流文件
    private static void inputStreamToFile(InputStream ins, File file) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (ins != null) {
                try {
                    ins.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
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
    @Override
    public ApiResponse create(CommercialBuildingPTO commercialBuildingPTO) {
        ApiResponse apiResponse = ApiResponse.ok();

        int i = commercialBuildingMapper.insert(commercialBuildingPTO);
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
        RequestEntity hotelDTO = pageVo.getType();
        CommercialBuildingPTO commercialBuildingPTO = new CommercialBuildingPTO();
        BeanUtils.copyProperties(hotelDTO,commercialBuildingPTO);
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

        QueryWrapper<CommercialBuildingPTO> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(commercialBuildingPTO.getSubstation())){
            wrapper.like("substation",commercialBuildingPTO.getSubstation());
        }
        if (StringUtils.isNotBlank(commercialBuildingPTO.getCustomerManager())){
            wrapper.eq("customerManager",commercialBuildingPTO.getCustomerManager());
        }
        if (StringUtils.isNotBlank(commercialBuildingPTO.getHotelName())){
            wrapper.like("hotelName",commercialBuildingPTO.getHotelName());
        }
        if (StringUtils.isNotBlank(commercialBuildingPTO.getType())){
            wrapper.eq("hotelType",commercialBuildingPTO.getType());
        }

        Page<CommercialBuildingPTO> page = new Page<>();
        page.setCurrent(pageNo);
        page.setSize(pageSize);
        Page<CommercialBuildingPTO> hotelPTOPage = commercialBuildingMapper.selectPage(page, wrapper);

        apiResponse.setData(hotelPTOPage);
        apiResponse.setCode(200);
        apiResponse.setMsg("查询成功");
        return apiResponse;
    }

    @Override
    public ApiResponse delete(Integer id) {
        ApiResponse apiResponse = new ApiResponse();
        int i = commercialBuildingMapper.deleteById(id);
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
    public ApiResponse update(CommercialBuildingPTO commercialBuildingPTO) {
        ApiResponse apiResponse = new ApiResponse();

        int i = commercialBuildingMapper.updateById(commercialBuildingPTO);
        if(i>0){
            apiResponse.setCode(200);
            apiResponse.setMsg("修改成功");
        }else{
            apiResponse.setCode(400);
            apiResponse.setMsg("修改失败");
        }
        return apiResponse;
    }

}
