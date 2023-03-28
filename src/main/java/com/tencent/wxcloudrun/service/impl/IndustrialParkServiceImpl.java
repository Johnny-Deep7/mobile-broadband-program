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
import com.tencent.wxcloudrun.mapper.IndustrialParkMapper;
import com.tencent.wxcloudrun.pto.IndustrialParkPTO;
import com.tencent.wxcloudrun.service.IndustrialParkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
@Service
@Slf4j
public class IndustrialParkServiceImpl implements IndustrialParkService {
    @Autowired
    private IndustrialParkMapper industrialParkMapper;

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

    public void parseExcel(File file)throws Exception{


        int sheetCount = ExcelUtil.getReader(file).getSheetCount();
        for (MbpType mbpType:MbpType.values()) {
            ExcelReader reader = ExcelUtil.getReader(file, mbpType.getCode());
            reader.read();
        }
        return ;
    }

    @Override
    public ApiResponse create(IndustrialParkPTO industrialParkPTO) {
        ApiResponse apiResponse = ApiResponse.ok();
        int i = industrialParkMapper.insert(industrialParkPTO);
        if (i > 0) {
            apiResponse.setCode(200);
            apiResponse.setMsg("添加成功");
        } else {
            apiResponse.setCode(400);
            apiResponse.setMsg("添加失败");
        }
        return apiResponse;
    }

    @Override
    public ApiResponse query(PageVo<RequestEntity> pageVo) {
        IndustrialParkPTO industrialParkPTO = new IndustrialParkPTO();
        RequestEntity hotelDTO = pageVo.getType();
        BeanUtils.copyProperties(hotelDTO, industrialParkPTO);
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

        QueryWrapper<IndustrialParkPTO> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(industrialParkPTO.getSubstation())) {
            wrapper.like("substation", industrialParkPTO.getSubstation());
        }
        if (StringUtils.isNotBlank(industrialParkPTO.getCustomerManager())) {
            wrapper.eq("customerManager", industrialParkPTO.getCustomerManager());
        }
        if (StringUtils.isNotBlank(industrialParkPTO.getHotelName())) {
            wrapper.like("hotelName", industrialParkPTO.getHotelName());
        }
        if (StringUtils.isNotBlank(industrialParkPTO.getType())) {
            wrapper.eq("hotelType", industrialParkPTO.getType());
        }

        Page<IndustrialParkPTO> page = new Page<>();
        page.setCurrent(pageNo);
        page.setSize(pageSize);
        Page<IndustrialParkPTO> industrialParkPTOPage = industrialParkMapper.selectPage(page, wrapper);

        apiResponse.setData(industrialParkPTOPage);
        apiResponse.setCode(200);
        apiResponse.setMsg("查询成功");
        return apiResponse;
    }

    @Override
    public ApiResponse delete(Integer id) {
        ApiResponse apiResponse = new ApiResponse();
        int i = industrialParkMapper.deleteById(id);
        if (i > 0) {
            apiResponse.setCode(200);
            apiResponse.setMsg("删除成功");
        } else {
            apiResponse.setCode(400);
            apiResponse.setMsg("删除失败");
        }
        return apiResponse;
    }

    @Override
    public ApiResponse update(IndustrialParkPTO industrialParkPTO) {
        ApiResponse apiResponse = new ApiResponse();

        int i = industrialParkMapper.updateById(industrialParkPTO);
        if (i > 0) {
            apiResponse.setCode(200);
            apiResponse.setMsg("修改成功");
        } else {
            apiResponse.setCode(400);
            apiResponse.setMsg("修改失败");
        }
        return apiResponse;
    }
}
