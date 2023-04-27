package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.config.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface MbpService {
    ApiResponse parsingTable(MultipartFile multipartFile, String marketType, Integer id);

    ApiResponse downloadTable(HttpServletResponse response, String marketType, String substation, String customerManager,
                              String startTime, String endTime, String subId) throws IOException;
}
