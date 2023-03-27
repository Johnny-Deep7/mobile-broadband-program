package com.tencent.wxcloudrun.service.impl;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tencent.wxcloudrun.MbpType;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.config.PageListResp;
import com.tencent.wxcloudrun.dto.HotelDTO;
import com.tencent.wxcloudrun.mapper.HotelMapper;
import com.tencent.wxcloudrun.pto.HotelPTO;
import com.tencent.wxcloudrun.service.MbpService;
import lombok.extern.slf4j.Slf4j;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import javax.annotation.Resource;
import java.io.*;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class MbpServiceImpl implements MbpService {
    @Resource
    private HotelMapper hotelMapper;
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

    @Override
    public ApiResponse create(HotelDTO hotelDTO) {
        ApiResponse apiResponse = new ApiResponse();
        HotelPTO hotelPTO = new HotelPTO();
        BeanUtils.copyProperties(hotelDTO,hotelPTO);

        int i = hotelMapper.insert(hotelPTO);
        if(i>0){
            apiResponse.setMsg("添加成功");
        }else{
            apiResponse.setMsg("添加失败");
        }
        return apiResponse;
    }

    @Override
    public ApiResponse listHotel(HotelDTO hotelDTO) {
        ApiResponse apiResponse = new ApiResponse();
        HotelPTO hotelPTO = new HotelPTO();
        BeanUtils.copyProperties(hotelDTO,hotelPTO);
//        HotelPTO hotelPto = hotelMapper.listHotel(hotelPTO);
        QueryWrapper<HotelPTO> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(hotelPTO.getSubstation())){
            wrapper.like("substation",hotelPTO.getSubstation());
        }
        if (StringUtils.isNotBlank(hotelPTO.getCustomerManager())){
            wrapper.eq("customerManager",hotelPTO.getCustomerManager());
        }
        if (StringUtils.isNotBlank(hotelPTO.getHotelName())){
            wrapper.like("hotelName",hotelPTO.getHotelName());
        }
        if (StringUtils.isNotBlank(hotelPTO.getHotelType())){
            wrapper.eq("hotelType",hotelPTO.getHotelType());
        }
        List<HotelPTO> userList = hotelMapper.selectList(wrapper);
        apiResponse.setData(userList);
        apiResponse.setMsg("查询成功");
        return apiResponse;
    }

    @Override
    public ApiResponse delete(Integer id) {
        ApiResponse apiResponse = new ApiResponse();
        int i = hotelMapper.deleteById(id);
        if(i>0){
            apiResponse.setMsg("删除成功");
        }else{
            apiResponse.setMsg("删除失败");
        }
        return apiResponse;
    }

    @Override
    public ApiResponse update(HotelDTO hotelDTO) {
        ApiResponse apiResponse = new ApiResponse();
        HotelPTO hotelPTO = new HotelPTO();
        BeanUtils.copyProperties(hotelDTO,hotelPTO);
        int i = hotelMapper.update(hotelPTO);
        if(i>0){
            apiResponse.setMsg("修改成功");
        }else{
            apiResponse.setMsg("修改失败");
        }
        return apiResponse;
    }
    public PageListResp<SubnetResp> listSubnetResp(SubnetListReq subnetListReq) {
        subnetListReq.setQueryKey(QueryWordEscapingUtil.escaping(subnetListReq.getQueryKey()));
        subnetListReq.setNetworkId(null);
        subnetListReq.setEcStatus(State.ACTIVE);
        log.info("子网分页查询请求体:{}", subnetListReq);
        PageListResp<SubnetResp> pageListResp = new PageListResp<>();

        pageListResp.setTotal(subnetDao.count(subnetListReq));
        if (pageListResp.getTotal().equals(BigInteger.ZERO)) {
            pageListResp.setContent(Collections.emptyList());
        } else {
            subnetListReq.setSize(QueryPageUtil.generateQueryPageSize(subnetListReq.getSize()));
            subnetListReq.setStart(QueryPageUtil.generateQueryPageStart(
                    subnetListReq.getPage(), subnetListReq.getSize()));
            pageListResp.setContent(subnetDao.listSubnetResp(subnetListReq));
        }
        return pageListResp;
    }

//    /**
//     * 解析导入的文件内容
//     * @param file
//     * @throws Exception
//     */
//    public Map<String,List<List<ResultValue>>> parseExcel(File file)throws Exception{
//
//        //文件名
//        String filename = file.getName();
//
//        //创建一个文件对象
//        Workbook workbook = null;
//        InputStream inputStream = new FileInputStream(file);
//        //Office 2007版本后excel.xls文件
//        if (filename.endsWith("xlsx")) {
//            workbook = new XSSFWorkbook(inputStream);
//        } //Office 2007版本前excel.xls文件
//        else if (filename.endsWith("xls")) {
//            workbook = new HSSFWorkbook(inputStream);
//        } else {
//            log.error("不是Excel文件");
//            throw new RuntimeException("不是Excel文件");
//        }
//
//        //读取Sheet
//        Sheet sheet = workbook.getSheet("params");
//        List<List<Object>> paramList = new ArrayList<>();
//        List<List<Object>> bodyList = new ArrayList<>();
//
//        if (sheet!=null){
//
//            paramList = getSheetData(sheet,paramColumns);
//        }
//        Sheet bodySheet = workbook.getSheet("body");
//        if (bodySheet!= null){
//
//            bodyList = getSheetData(bodySheet,bodyColumns);
//        }
//
//        return map;
//    }
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

    /**
     * 获取不同sheet的数据
     * @param sheet
     */
    public void getSheetData(Sheet sheet){


        //获取sheet名
        String sheetName = sheet.getSheetName();

        //获取sheet下有多少行
        int rowNum = sheet.getPhysicalNumberOfRows();
        //遍历所有的Row
        for (int i = 1; i <= rowNum; i++) {

            //获取当前sheet的第i行
            Row row = sheet.getRow(i);
            Cell cell = row.getCell(1);

            //读取Cell的值
            for (int j = 1; j <row.getLastCellNum(); j++) {

            }

        }
        return ;
    }
}
