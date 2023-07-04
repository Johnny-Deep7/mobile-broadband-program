package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.config.QueryResponse;
import com.tencent.wxcloudrun.pto.LoginPTO;

import javax.servlet.http.HttpServletRequest;

public interface LoginService {
    public ApiResponse create(LoginPTO loginPTO);
    public ApiResponse regin(LoginPTO loginPTO);
    public ApiResponse login(LoginPTO loginPTO);
    public ApiResponse delete(Integer id);
    public ApiResponse update(LoginPTO loginPTO);
    public ApiResponse updatePassWord(Integer id,String passWord);
    public QueryResponse query(LoginPTO loginPTO);
}
