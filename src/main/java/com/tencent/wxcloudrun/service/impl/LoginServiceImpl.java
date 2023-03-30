package com.tencent.wxcloudrun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.config.QueryResponse;
import com.tencent.wxcloudrun.mapper.LoginMapper;
import com.tencent.wxcloudrun.pto.LoginPTO;
import com.tencent.wxcloudrun.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginMapper loginMapper;

    @Override
    public ApiResponse create(LoginPTO loginPTO) {
        ApiResponse apiResponse = ApiResponse.ok();
        if (!isValidPhoneNumber(loginPTO.getPhoneNumber())) {
            apiResponse.setCode(400);
            apiResponse.setMsg("手机号码不符合规则");
            return apiResponse;
        }
        int i = loginMapper.insert(loginPTO);
        if (i > 0) {
            apiResponse.setCode(200);
            apiResponse.setMsg("添加成功");
        } else {
            apiResponse.setCode(400);
            apiResponse.setMsg("添加失败");
        }
        return apiResponse;
    }

    @Override
    public ApiResponse login(LoginPTO loginPTO) {
        String passWord = loginPTO.getPassWord();
        Boolean isAdmin = loginPTO.getIsAdmin();
        ApiResponse apiResponse = new ApiResponse();
        QueryWrapper<LoginPTO> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(loginPTO.getPhoneNumber())) {
            queryWrapper.eq("phone_number", loginPTO.getPhoneNumber());
        }
        LoginPTO loginPTO1 = loginMapper.selectOne(queryWrapper);
        if (loginPTO1.getPassWord() == null) {
            loginMapper.update(loginPTO, queryWrapper);
            apiResponse.setCode(200);
            apiResponse.setMsg("登录失败，密码为空");
            return apiResponse;
        }
        if (passWord.equals(loginPTO1.getPassWord()) && isAdmin.equals(loginPTO1.getIsAdmin())){
            apiResponse.setCode(200);
            apiResponse.setMsg("登录成功");
        } else {
            apiResponse.setCode(400);
            apiResponse.setMsg("登录失败");
        }
        return apiResponse;
    }
    @Override
    public QueryResponse query(LoginPTO loginPTO) {
        QueryResponse queryResponse = new QueryResponse();
        QueryWrapper<LoginPTO> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(loginPTO.getPhoneNumber())) {
            queryWrapper.eq("phone_number", loginPTO.getPhoneNumber());
        }
        LoginPTO loginPTO1 = loginMapper.selectOne(queryWrapper);
        if (loginPTO1 != null) {
            queryResponse.setIsExist(Boolean.TRUE);
            if (loginPTO1.getPassWord() == null) {
                queryResponse.setIsFirstLogin(Boolean.TRUE);
                queryResponse.setIsAdmin(loginPTO.getIsAdmin());
            } else {
                queryResponse.setIsFirstLogin(Boolean.FALSE);
                queryResponse.setIsAdmin(loginPTO.getIsAdmin());
            }
        } else {
            queryResponse.setIsExist(Boolean.FALSE);
        }
        return queryResponse;
    }

    @Override
    public ApiResponse delete(Integer id) {
        return null;
    }

    @Override
    public ApiResponse update(LoginPTO loginPTO) {
        ApiResponse apiResponse = new ApiResponse();
        QueryWrapper<LoginPTO> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(loginPTO.getPhoneNumber())) {
            queryWrapper.eq("phone_number", loginPTO.getPhoneNumber());
        }
        Long count = loginMapper.selectCount(queryWrapper);
        if (count == 0) {
            apiResponse.setCode(400);
            apiResponse.setMsg("手机号码输入错误");
            return apiResponse;
        }
        int i = loginMapper.updateById(loginPTO);
        if (i > 0) {
            apiResponse.setCode(200);
            apiResponse.setMsg("登录信息修改成功");
        } else {
            apiResponse.setCode(400);
            apiResponse.setMsg("登录信息修改失败");
        }
        return apiResponse;
    }

    private Boolean isValidPhoneNumber(String phoneNumber) {
        if(phoneNumber != null && !phoneNumber.isEmpty()) {
            return Pattern.matches("^1[3-9]\\d{9}$", phoneNumber);
        }
        return false;
    }
}
