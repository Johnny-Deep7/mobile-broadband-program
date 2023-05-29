package com.tencent.wxcloudrun.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

public class EasyExcelUtils {

    private static final Logger log = LoggerFactory.getLogger(EasyExcelUtils.class);

    /**
     * 获取默认表头内容的样式
     * @return
     */
    private static HorizontalCellStyleStrategy getDefaultHorizontalCellStyleStrategy(){
        /** 表头样式 **/
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景色（浅灰色）
        headWriteCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        // 字体大小
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 10);
        headWriteCellStyle.setWriteFont(headWriteFont);
        //设置表头居中对齐
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        /** 内容样式 **/
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 内容字体样式（名称、大小）
        WriteFont contentWriteFont = new WriteFont();
        contentWriteFont.setFontName("宋体");
        contentWriteFont.setFontHeightInPoints((short) 10);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        //设置内容垂直居中对齐
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置内容水平居中对齐
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        //设置边框样式
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        // 头样式与内容样式合并
        return new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
    }

    public static OutputStream getOutputStream(String fileName, HttpServletResponse response) throws Exception {
        fileName = URLEncoder.encode(fileName, "UTF-8");
        //  response.setContentType("application/vnd.ms-excel"); // .xls
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); // .xlsx
        response.setCharacterEncoding("utf8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        return response.getOutputStream();
    }

    /**
     * @param out 输出流
     * @param flag 是否添加默认打印样式，为 true 添加，为 false 不添加。大批量导出去除样式可以节省更多的资源
     * @return
     */
    public static ExcelWriter buildExcelWriter(OutputStream out,Boolean flag)  {
        ExcelWriterBuilder excelWriterBuilder = EasyExcel.write(out).excelType(ExcelTypeEnum.XLSX);
        if (flag){
            excelWriterBuilder.registerWriteHandler(getDefaultHorizontalCellStyleStrategy());
        }
        return excelWriterBuilder.build();
    }

    /**
     * 默认构建带样式
     * @param out
     * @return
     * @throws Exception
     */
    public static ExcelWriter buildExcelWriter(OutputStream out)  {
        return buildExcelWriter(out,true);
    }

    /**
     * 单纯写入，适用于手动分页
     * @param excelWriter
     * @param data
     * @param clazz
     * @param sheetNo
     * @param sheetName
     * @param <T>
     * @throws Exception
     */
    public static<T> void writeOnly(ExcelWriter excelWriter,List<T> data,Class clazz, Integer sheetNo,String sheetName) {
        long exportStartTime = System.currentTimeMillis();
        log.info("报表"+sheetNo+"写入Size: "+data.size()+"条。");
        ExcelWriterSheetBuilder excelWriterSheetBuilder;
        WriteSheet writeSheet;
        excelWriterSheetBuilder = new ExcelWriterSheetBuilder(excelWriter);
        excelWriterSheetBuilder.sheetNo(sheetNo);
        excelWriterSheetBuilder.sheetName(sheetName);
        writeSheet = excelWriterSheetBuilder.build();
        writeSheet.setClazz(clazz);
        excelWriter.write(data,writeSheet);
        log.info("报表"+sheetNo+"写入耗时: "+(System.currentTimeMillis()-exportStartTime)+"ms" );
    }


    /**
     * 导出
     * @author QiuYu
     * @param out
     * @param excelWriter
     * @throws IOException
     */
    public static void finishWriter(OutputStream out,ExcelWriter excelWriter) throws IOException {
        out.flush();
        excelWriter.finish();
        out.close();
        System.out.println("报表导出结束时间:"+ new Date());
    }

}
