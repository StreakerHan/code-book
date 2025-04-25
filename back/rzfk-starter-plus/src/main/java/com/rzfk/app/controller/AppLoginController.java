package com.rzfk.app.controller;

import com.rzfk.app.domain.vo.LoginVO;
import com.rzfk.app.service.IAppUserService;
import com.rzfk.common.core.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 *
 */

@Validated
@Api(value = "app用户登录", tags = {"app用户登录"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/app/login")
public class AppLoginController {
    @Autowired
    private IAppUserService appUserService;

    /**
     * 账号注册
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "账号注册")
    @PostMapping(value = "/register")
    public R register(@RequestBody Map<String,Object> params, HttpServletRequest request) {
        return appUserService.register(params);
    }

    /**
     * 账号密码登录
     *
     * @param login
     * @param request
     * @return
     */
    @ApiOperation(value = "账号密码登录")
    @PostMapping(value = "/loginByPwd")
    public R loginByPwd(@RequestBody LoginVO login, HttpServletRequest request) {
        return appUserService.loginByPwd(login);
    }

    /**
     * 获取短信验证码
     */
    @ApiOperation(value = "发送短信验证码")
    @PostMapping(value = "/smsMobile")
    public R smsMobile(@RequestBody Map<String,Object> body, HttpServletRequest request) throws Exception {
        return appUserService.smsSend(body);
    }

    /**
     * 短信验证码登录
     */
    @ApiOperation(value = "短信验证码登录")
    @PostMapping(value = "/loginBySms")
    public R loginBySms(@RequestBody Map<String,Object> body, HttpServletRequest request){
        return R.success();
    }


    /**
     * 微信app登录
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "微信app登录")
    @GetMapping(value = "/loginByWeChat")
    public R loginByWeChat(@RequestParam Map<String, Object> params) {
        return appUserService.weChatLogin(params);
    }
}
