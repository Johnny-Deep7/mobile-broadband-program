package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.config.QueryResponse;
import com.tencent.wxcloudrun.dto.LoginDTO;
import com.tencent.wxcloudrun.pto.LoginPTO;
import com.tencent.wxcloudrun.service.impl.LoginServiceImpl;
import com.tencent.wxcloudrun.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/users")
public class UserLoginController {
    @Autowired
    private LoginServiceImpl loginService;

    //注册用户
    @PostMapping(value = "/regin")
    public ApiResponse regin(@RequestBody LoginPTO loginPTO) {
        return loginService.regin(loginPTO);
    }
    //登录
    @PostMapping(value = "/login")
    public  ApiResponse login(@RequestBody LoginPTO loginPTO){

        return loginService.login(loginPTO);

    }
    //更新
    @GetMapping(value = "/updatePassWord")
    public ApiResponse updatePassWord(HttpServletRequest httpServletRequest, @RequestParam("passWord") String passWord){
        ApiResponse apiResponse = ApiResponse.ok();
        //首先判断token是否有效
        if(!JwtUtil.checkToken(httpServletRequest)){
            apiResponse.setCode(500);
            apiResponse.setMsg("token无效");
            return apiResponse;
        }
        //解密userId
        Integer id = JwtUtil.getUserIdByJwtToken(httpServletRequest);
        return loginService.updatePassWord(id,passWord);
    }


//    @PostMapping(value = "/Query")
//    @ResponseBody
//    public QueryResponse query(@RequestBody LoginDTO loginDTO) {
//        log.info("验证用户登录信息");
//        LoginPTO loginPTO = new LoginPTO();
//        BeanUtils.copyProperties(loginDTO, loginPTO);
//        return loginService.query(loginPTO);
//    }

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
