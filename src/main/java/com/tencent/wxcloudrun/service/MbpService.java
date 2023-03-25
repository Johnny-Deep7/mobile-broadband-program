package com.tencent.wxcloudrun.service;

import org.springframework.web.multipart.MultipartFile;

public interface MbpService {

    void parsingTable(MultipartFile file);
}
