package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.config.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MbpService {
    ApiResponse parsingTable(MultipartFile multipartFile, String marketType, Integer id);

    ApiResponse downloadTable(String substation, String customerManager,
                              String startTime, String endTime) throws IOException;
}
