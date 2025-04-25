package com.rzfk.framework.web.service;

import com.rzfk.common.constant.Constants;
import com.rzfk.common.core.domain.entity.SysUser;
import com.rzfk.common.core.domain.model.LoginUser;
import com.rzfk.common.core.redis.RedisCache;
import com.rzfk.common.exception.CustomException;
import com.rzfk.common.exception.user.CaptchaException;
import com.rzfk.common.exception.user.CaptchaExpireException;
import com.rzfk.common.exception.user.UserPasswordNotMatchException;
import com.rzfk.common.utils.DateUtils;
import com.rzfk.common.utils.MessageUtils;
import com.rzfk.common.utils.ServletUtils;
import com.rzfk.common.utils.ip.IpUtils;
import com.rzfk.framework.config.properties.CaptchaProperties;
import com.rzfk.framework.manager.AsyncManager;
import com.rzfk.framework.manager.factory.AsyncFactory;
import com.rzfk.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 登录校验方法
 *
 * @author ruoyi
 */
@Component
public class SysLoginService
{
    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

	@Autowired
	private CaptchaProperties captchaProperties;

	@Autowired
    private ISysUserService userService;

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String uuid)
    {
		if(captchaProperties.getEnabled()) {
			String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
			String captcha = redisCache.getCacheObject(verifyKey);
			redisCache.deleteObject(verifyKey);
			if (captcha == null) {
				AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
				throw new CaptchaExpireException();
			}
			if (!code.equalsIgnoreCase(captcha)) {
				AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
				throw new CaptchaException();
			}
		}
        // 用户验证
        Authentication authentication = null;
        try
        {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }
        catch (Exception e)
        {
            if (e instanceof BadCredentialsException)
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            }
            else
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new CustomException(e.getMessage());
            }
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        recordLoginInfo(loginUser.getUser());
        // 生成token
        return tokenService.createToken(loginUser);
    }

    /**
     * 记录登录信息
     */
    public void recordLoginInfo(SysUser user)
    {
        user.setLoginIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
        user.setLoginDate(DateUtils.getNowDate());
		user.setUpdateBy(user.getUserName());
        userService.updateUserProfile(user);
    }
}
