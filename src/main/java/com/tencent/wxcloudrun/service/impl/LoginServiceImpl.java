package com.tencent.wxcloudrun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.config.QueryResponse;
import com.tencent.wxcloudrun.mapper.LoginMapper;
import com.tencent.wxcloudrun.pto.LoginPTO;
import com.tencent.wxcloudrun.pto.MarketingPlanPTO;
import com.tencent.wxcloudrun.service.LoginService;
import com.tencent.wxcloudrun.utils.AESUtils;
import com.tencent.wxcloudrun.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.regex.Pattern;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginMapper loginMapper;

    //        创建一个BCryptPasswordEncoder对象
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public ApiResponse regin(LoginPTO loginPTO) {
        ApiResponse apiResponse = ApiResponse.ok();
        //对密码解密
        String decode = AESUtils.decode(loginPTO.getPassWord());
        String phoneNumber = AESUtils.decode(loginPTO.getPhoneNumber());
        //对密码进行加密
        String password = passwordEncoder.encode(decode);
        loginPTO.setPassWord(password);
        loginPTO.setPhoneNumber(phoneNumber);
        QueryWrapper<LoginPTO> wrapper = new QueryWrapper<>();
        wrapper.eq("phone_number", loginPTO.getPhoneNumber());
        Long count = loginMapper.selectCount(wrapper);
        if (count > 0) {
            apiResponse.setCode(400);
            apiResponse.setMsg("当前用户已经存在");
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
        String password = AESUtils.decode(loginPTO.getPassWord());
        String phoneNumber = AESUtils.decode(loginPTO.getPhoneNumber());
        ApiResponse apiResponse = new ApiResponse();
        QueryWrapper<LoginPTO> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(loginPTO.getPhoneNumber())) {
            queryWrapper.eq("phone_number", phoneNumber);
        }
        LoginPTO loginPTO1 = loginMapper.selectOne(queryWrapper);
        if (StringUtils.checkValNull(loginPTO1)||StringUtils.isBlank(loginPTO1.getPassWord())) {
            apiResponse.setCode(400);
            apiResponse.setMsg("登录失败,当前用户不存在");
            return apiResponse;
        }
        boolean matches = passwordEncoder.matches(password, loginPTO1.getPassWord());
        //生成token
        String token = JwtUtil.getJwtToken(loginPTO1.getId(), loginPTO1.getUserName());
        if (matches) {
            apiResponse.setCode(200);
            apiResponse.setMsg("登录成功");
            apiResponse.setData(token);
        } else {
            apiResponse.setCode(400);
            apiResponse.setMsg("密码输入错误，请重新输入");
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
            queryResponse.setCode(200);
            queryResponse.setMsg("账号查询成功");
            if (StringUtils.isBlank(loginPTO1.getPassWord())) {
                queryResponse.setIsFirstLogin(Boolean.TRUE);
                queryResponse.setIsAdmin(loginPTO1.getIsAdministrator());
            } else {
                queryResponse.setIsFirstLogin(Boolean.FALSE);
                queryResponse.setIsAdmin(loginPTO1.getIsAdministrator());
            }
        } else {
            queryResponse.setCode(400);
            queryResponse.setMsg("账号查询失败");
        }
        return queryResponse;
    }

    @Override
    public ApiResponse delete(Integer id) {
        ApiResponse apiResponse = new ApiResponse();
        int i = loginMapper.deleteById(id);
        if (i > 0) {
            apiResponse.setCode(200);
            apiResponse.setMsg("删除成功");
        } else {
            apiResponse.setCode(400);
            apiResponse.setMsg("删除失败");
        }
        return apiResponse;
    }

    @Override
    public ApiResponse update(LoginPTO loginPTO) {
        ApiResponse apiResponse = new ApiResponse();
        QueryWrapper<LoginPTO> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(loginPTO.getPhoneNumber())) {
            queryWrapper.eq("phone_number", loginPTO.getPhoneNumber());
        }
        Long count = loginMapper.selectCount(queryWrapper);
        if (count == 0) {
            apiResponse.setCode(400);
            apiResponse.setMsg("手机号码输入错误");
            return apiResponse;
        }
        int i = loginMapper.update(loginPTO, queryWrapper);
        if (i > 0) {
            apiResponse.setCode(200);
            apiResponse.setMsg("登录信息修改成功");
        } else {
            apiResponse.setCode(400);
            apiResponse.setMsg("登录信息修改失败");
        }
        return apiResponse;
    }

    @Override
    public ApiResponse updatePassWord(Integer id, String passWord) {
        ApiResponse apiResponse = ApiResponse.ok();

        QueryWrapper<LoginPTO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        LoginPTO loginPTO = loginMapper.selectOne(queryWrapper);
        loginPTO.setPassWord(passwordEncoder.encode(passWord));
        int i = loginMapper.updateById(loginPTO);
        if (i > 0) {
            apiResponse.setCode(200);
            apiResponse.setMsg("密码修改成功！");
        }else {
            apiResponse.setCode(400);
            apiResponse.setMsg("密码修改失败!");
        }

        return apiResponse;
    }

    private Boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            return Pattern.matches("^1[3-9]\\d{9}$", phoneNumber);
        }
        return false;
    }
}
