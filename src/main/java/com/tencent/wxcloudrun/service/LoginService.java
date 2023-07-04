package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.config.QueryResponse;
import com.tencent.wxcloudrun.pto.LoginPTO;

public interface LoginService {
    public ApiResponse create(LoginPTO loginPTO);
    public ApiResponse register(LoginPTO loginPTO);
    public ApiResponse login(LoginPTO loginPTO);
    public ApiResponse delete(Integer id);
    public ApiResponse update(LoginPTO loginPTO);
    public QueryResponse query(LoginPTO loginPTO);
}
