package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.service.MbpService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MbpServiceImpl implements MbpService {
    @Override
    public void parsingTable(MultipartFile multipartFile) {
        File file = null;
        if (multipartFile!=null){
            try {
                file = multipartFileToFile(multipartFile);
            } catch (Exception e) {
                throw new RuntimeException("文件解析失败！");
            }
        }
        if(!file.exists() || file.length() == 0) {
            throw new RuntimeException("文件为空！");
        }
//        parseExcel(file);

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

//    /**
//     * 获取不同sheet的数据
//     * @param sheet
//     */
//    public List<List<ResultValue>> getSheetData(Sheet sheet,List<Column> list){
//        List<String> types = new ArrayList<>();
//        List<Map<String,String>> mapList = new ArrayList<>();
//        Map<String,String> map = new HashMap<>();
//        list.forEach(column -> {
//            map.put(column.getName(),column.getFieldtype());
//            mapList.add(map);
//        });
//        for (Column column:list) {
//            types.add(column.getFieldtype());
//        }
//        List<List<ResultValue>> lists = new ArrayList<>();
//
//        ResultValue resultValue = null;
//        List<String> names = new ArrayList<>();
//        //获取表头信息
//        Row r1 = sheet.getRow(0);
//        for (Cell c:r1){
//            names.add(c.getStringCellValue());
//        }
//
//        int rowNum = sheet.getLastRowNum();
//        //遍历所有的Row
//        for (int i = 1; i <= rowNum; i++) {
//            List<ResultValue> resultValues = new ArrayList<>();
//            Row row = sheet.getRow(i);
//            Cell cell = row.getCell(1);
//
//            //读取Cell的值
//            for (int j = 1; j <row.getLastCellNum(); j++) {
//                resultValue = new ResultValue();
//                resultValue.setName(names.get(j));
//                String s = names.get(j);
//                resultValue.setType(list.stream()
//                        .filter(column -> column.getName().equals(s))
//                        .map(Column::getFieldtype)
//                        .collect(Collectors.joining()));
//                resultValue.setValue(row.getCell(j).toString());
//                resultValues.add(resultValue);
//            }
//            lists.add(resultValues);
//
//        }
//        return lists;
//    }
}
