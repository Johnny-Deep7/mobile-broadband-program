package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.config.ApiResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface MbpService {
    ApiResponse parsingTable(MultipartFile multipartFile, String marketType, Integer id);

    ApiResponse downloadTable(HttpServletResponse response, String marketType, String substation, String customerManager,
                              String startTime, String endTime, String subId) throws IOException;

    ApiResponse fullExport(String startTime, String endTime);

    ApiResponse statistics(String startTime, String endTime,String substation);
}
