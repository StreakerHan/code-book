package com.rzfk.framework.config;

import com.rzfk.framework.security.filter.JwtAuthenticationTokenFilter;
import com.rzfk.framework.security.handle.AuthenticationEntryPointImpl;
import com.rzfk.framework.security.handle.LogoutSuccessHandlerImpl;
import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.filter.CorsFilter;

/**
 * spring security配置
 *
 * @author ruoyi
 */
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    /**
     * 自定义用户认证逻辑
     */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 认证失败处理类
     */
    @Autowired
    private AuthenticationEntryPointImpl unauthorizedHandler;

    /**
     * 退出处理类
     */
    @Autowired
    private LogoutSuccessHandlerImpl logoutSuccessHandler;

    /**
     * token认证过滤器
     */
    @Autowired
    private JwtAuthenticationTokenFilter authenticationTokenFilter;

    /**
     * 跨域过滤器
     */
    @Autowired
    private CorsFilter corsFilter;

    @Autowired
    private AdminServerProperties adminServerProperties;

    /**
     * 解决 无法直接注入 AuthenticationManager
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }

    /**
     * anyRequest          |   匹配所有请求路径
     * access              |   SpringEl表达式结果为true时可以访问
     * anonymous           |   匿名可以访问
     * denyAll             |   用户不能访问
     * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
     * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
     * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
     * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
     * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
     * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
     * permitAll           |   用户可以任意访问
     * rememberMe          |   允许通过remember-me登录的用户访问
     * authenticated       |   用户登录后可访问
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception
    {
        httpSecurity
                // CSRF禁用，因为不使用session
                .csrf().disable()
                // 认证失败处理类
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 过滤请求
                .authorizeRequests()
                // 对于登录login 验证码captchaImage 允许匿名访问
                .antMatchers("/login", "/captchaImage").anonymous()
                .antMatchers(
                        HttpMethod.GET,
                        "/*.html",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                ).permitAll()
                .antMatchers("/profile/**").anonymous()
				// websocket
				.antMatchers("/ws/**").permitAll()
				// 对象存储
				.antMatchers("/oss/**").permitAll()
				// ai 识别
				.antMatchers("/ai/**").permitAll()
                // 轮播
                .antMatchers("/app/appCarousel/getIndexList**").permitAll()
                // 动态
                .antMatchers("/app/appPost/appGetList**").permitAll()
                //积分榜
                .antMatchers("/app/appPost/appGetPointList**").permitAll()
                // 动态详情
                .antMatchers("/app/appPost/app/1/**").permitAll()
                //赛程
                .antMatchers("/app/appCalender/allList**").permitAll()
                .antMatchers("/app/appCalenderNew/allList**").permitAll()
                //赛事类型
                .antMatchers("/app/appResultCategory/allList**").permitAll()
                //赛项信息
                .antMatchers("/app/appCalenderEvent/allList**").permitAll()
                //比赛结果
                .antMatchers("/app/appCalenderResult/allList**").permitAll()
                //积分榜
                .antMatchers("/app/appWorldStanding/allList**").permitAll()
                //资源类型列表
                .antMatchers("/app/appSourceType/list**").permitAll()
                //资源信息列表
                .antMatchers("/app/appSourceInfo/list**").permitAll()
                .antMatchers("/app/appSourceInfo/randomList**").permitAll()
                .antMatchers("/app/appSourceInfo/getTypes**").permitAll()
                .antMatchers("/app/appSourceInfo/listAll**").permitAll()
                .antMatchers("/app/appSourceInfo/listAll1**").permitAll()
                .antMatchers("/app/appSourceInfo/listAll2**").permitAll()
                .antMatchers("/app/appSourceInfo/v1/webList/100**").permitAll()
                .antMatchers("/app/appSourceInfo/v1/webList/info**").permitAll()
                //资源信息详情
                .antMatchers("/app/appSourceInfo/info**").permitAll()
                //版本信息
                .antMatchers("/app/appVersion/appVersion**").permitAll()
                .antMatchers("/app/bookCategory/addContent**").permitAll()
                .antMatchers("/app/bookCategory/list**").permitAll()
                .antMatchers("/app/book/addContent**").permitAll()
                .antMatchers("/app/book/recommend**").permitAll()
                .antMatchers("/app/book/list**").permitAll()
                .antMatchers("/app/book/info**").permitAll()
                .antMatchers("/app/bookContent/addContent**").permitAll()
                .antMatchers("/app/bookContent/addContent1**").permitAll()
                .antMatchers("/app/bookContent/addContent2**").permitAll()
                .antMatchers("/app/bookContent/addContent3**").permitAll()
                .antMatchers("/app/bookContent/addContent4**").permitAll()
                .antMatchers("/app/bookContent/addContent6**").permitAll()
                .antMatchers("/app/bookContent/addContent5**").permitAll()
                .antMatchers("/app/bookContent/chaptersList**").permitAll()
                .antMatchers("/app/bookContent/chaptersContent**").permitAll()
                .antMatchers("/app/bookContent/chaptersContentHref**").permitAll()
                .antMatchers("/wallpaper/appVersion/appVersion**").permitAll()
                //壁纸列表
                .antMatchers("/wallpaper/wallpapers/**").permitAll()
                .antMatchers("/wallpaper/wallpapersTopic/**").permitAll()

				// api 用于大屏无权限
				.antMatchers("/api/**").anonymous()
                .antMatchers("/app/login/**").permitAll()
                .antMatchers("/common/download**").anonymous()
                .antMatchers("/common/download/resource**").anonymous()
                .antMatchers("/swagger-ui.html").anonymous()
                .antMatchers("/swagger-resources/**").anonymous()
                .antMatchers("/webjars/**").anonymous()
                .antMatchers("/*/api-docs").anonymous()
                .antMatchers("/druid/**").anonymous()
                // Spring Boot Admin Server 的安全配置
                .antMatchers(adminServerProperties.getContextPath()).anonymous()
                .antMatchers(adminServerProperties.getContextPath() + "/**").anonymous()
                // Spring Boot Actuator 的安全配置
                .antMatchers("/actuator").anonymous()
                .antMatchers("/actuator/**").anonymous()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable();
        httpSecurity.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);
        // 添加JWT filter
        httpSecurity.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        // 添加CORS filter
        httpSecurity.addFilterBefore(corsFilter, JwtAuthenticationTokenFilter.class);
        httpSecurity.addFilterBefore(corsFilter, LogoutFilter.class);
    }


    /**
     * 强散列哈希加密实现
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    /**
     * 身份认证接口
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
}
