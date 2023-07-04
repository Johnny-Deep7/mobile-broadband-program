package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.config.QueryResponse;
import com.tencent.wxcloudrun.dto.LoginDTO;
import com.tencent.wxcloudrun.pto.LoginPTO;
import com.tencent.wxcloudrun.service.impl.LoginServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@CrossOrigin
public class UserLoginController {
    @Autowired
    private LoginServiceImpl loginService;

    @PostMapping(value = "/regInitialized")
    public ApiResponse createLogin(@RequestBody LoginDTO loginDTO) {
        log.info("开始创建用户登录信息");
        LoginPTO loginPTO = new LoginPTO();
        BeanUtils.copyProperties(loginDTO, loginPTO);
        return loginService.create(loginPTO);
    }

    /**
     * 注册
     */
    @PostMapping("/register")
    public ApiResponse register(@RequestBody LoginPTO loginPTO){
        log.info("注册用户信息开始！");
        return loginService.register(loginPTO);
    }

    // 登录，基于restful风格
    @PostMapping("/login")
    public ApiResponse login(@RequestBody LoginPTO loginPTO) {
        log.info("用户登录！");
        return loginService.login(loginPTO);
    }


    @PostMapping(value = "/Login")
    @ResponseBody
    public ApiResponse login(@RequestBody LoginDTO loginDTO) {
        log.info("验证用户登录信息");
        LoginPTO loginPTO = new LoginPTO();
        BeanUtils.copyProperties(loginDTO, loginPTO);
        return loginService.login(loginPTO);
    }
    @PostMapping(value = "/Query")
    @ResponseBody
    public QueryResponse query(@RequestBody LoginDTO loginDTO) {
        log.info("验证用户登录信息");
        LoginPTO loginPTO = new LoginPTO();
        BeanUtils.copyProperties(loginDTO, loginPTO);
        return loginService.query(loginPTO);
    }

    @DeleteMapping(value = "/deleteAccount")
    public ApiResponse deleteLogin(@RequestParam("id") Integer id) {
        log.info("开始删除用户登录信息！");
        return loginService.delete(id);
    }

    @PutMapping(value = "/updateAccountInformation")
    public ApiResponse updateLogin(@RequestBody LoginDTO loginDTO) {
        log.info("开始更新用户登录信息！");
        LoginPTO loginPTO = new LoginPTO();
        BeanUtils.copyProperties(loginDTO, loginPTO);
        return loginService.update(loginPTO);
    }
}
