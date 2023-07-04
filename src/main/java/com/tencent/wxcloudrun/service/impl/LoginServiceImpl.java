package com.tencent.wxcloudrun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.config.QueryResponse;
import com.tencent.wxcloudrun.mapper.LoginMapper;
import com.tencent.wxcloudrun.pto.LoginPTO;
import com.tencent.wxcloudrun.pto.MarketingPlanPTO;
import com.tencent.wxcloudrun.service.LoginService;
import com.tencent.wxcloudrun.utils.DataAddException;
import com.tencent.wxcloudrun.utils.DataMatchException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@Log4j2
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginMapper loginMapper;
    // 数据加密，在启动类中已经注入进IOC容器中
    @Autowired
    private BCryptPasswordEncoder encoder;


    @Override
    public ApiResponse create(LoginPTO loginPTO) {
        ApiResponse apiResponse = ApiResponse.ok();
        if (!isValidPhoneNumber(loginPTO.getPhoneNumber())) {
            apiResponse.setCode(400);
            apiResponse.setMsg("手机号码不符合规则");
            return apiResponse;
        }
        QueryWrapper<LoginPTO> wrapper = new QueryWrapper<>();
        wrapper.eq("phone_number",loginPTO.getPhoneNumber());
        Long count = loginMapper.selectCount(wrapper);
        if(0 != count){
            apiResponse.setCode(400);
            apiResponse.setMsg("手机号已注册");
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
    public ApiResponse register(LoginPTO loginPTO) {
        ApiResponse apiResponse = ApiResponse.ok();
        QueryWrapper<LoginPTO> wrapper = new QueryWrapper<>();
        wrapper.eq("phone_number",loginPTO.getPhoneNumber());
        Long count = loginMapper.selectCount(wrapper);
        if (count != 0){
            apiResponse.setCode(400);
            apiResponse.setMsg("手机号已注册");
            return apiResponse;
        }
        int i = loginMapper.insert(loginPTO);
        if (i == 1){
            log.info("用户{}注册成功",loginPTO);
            return apiResponse;
        }else {
            log.error("服务器发生异常，注册失败");
            throw new DataAddException("403","注册失败");
        }

    }

    @Override
    public ApiResponse login(LoginPTO loginPTO) {
        ApiResponse apiResponse = ApiResponse.ok();
        // mybatis-plus的条件构造器，这里实现根据username查询
        QueryWrapper<LoginPTO> wrapper = new QueryWrapper<LoginPTO>();
        wrapper.eq("phone_number", loginPTO.getPhoneNumber());
        LoginPTO userLogin = loginMapper.selectOne(wrapper);

        /**
         *  encoder.matches(password, userLogin.getPassword()，实现输入的密码与数据库中的密码进
         *  行匹配,如果匹配成功则返回匹配的数据给controller层，如果失败则抛异常。
         *  为什么没盐，没有解密了?因为这个已经被CryptPasswordEncoder封装好了，
         *  在encoder.matches()方进行解密匹配完全帮你封装好了，所以不必考虑，
         *  只需要将前端传入的密码与数据库中加密后的密码进行匹配就行。
         * **/
        if (userLogin != null && encoder.matches(loginPTO.getPassWord(), userLogin.getPassWord())) {
            log.info("用户{}，登录成功",loginPTO.getUserName());
            apiResponse.setMsg("登录成功");
            apiResponse.setCode(200);
            return apiResponse;
        } else {
            log.error("用户名或密码错误");
            throw new DataMatchException("405", "用户名或密码错误");
        }
    }



//    @Override
//    public ApiResponse login(LoginPTO loginPTO) {
//        String passWord = loginPTO.getPassWord();
//        ApiResponse apiResponse = new ApiResponse();
//        QueryWrapper<LoginPTO> queryWrapper = new QueryWrapper<>();
//        if (StringUtils.isNotEmpty(loginPTO.getPhoneNumber())) {
//            queryWrapper.eq("phone_number", loginPTO.getPhoneNumber());
//        }
//        LoginPTO loginPTO1 = loginMapper.selectOne(queryWrapper);
//        if (StringUtils.isBlank(loginPTO1.getPassWord())) {
//            loginMapper.update(loginPTO,queryWrapper);
//            apiResponse.setCode(200);
//            apiResponse.setMsg("第一次登录，密码设置成功");
//            return apiResponse;
//        }
//        if (passWord.equals(loginPTO1.getPassWord())){
//            apiResponse.setCode(200);
//            apiResponse.setMsg("登录成功");
//        } else {
//            apiResponse.setCode(400);
//            apiResponse.setMsg("登录失败");
//        }
//        return apiResponse;
//    }
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
        if(i>0){
            apiResponse.setCode(200);
            apiResponse.setMsg("删除成功");
        }else{
            apiResponse.setCode(400);
            apiResponse.setMsg("删除失败");
        }
        return apiResponse;
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
        int i = loginMapper.update(loginPTO,queryWrapper);
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
