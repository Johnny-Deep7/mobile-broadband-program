package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.config.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

public interface MbpService {
    ApiResponse parsingTable(MultipartFile multipartFile, String marketType, Integer id);

}
