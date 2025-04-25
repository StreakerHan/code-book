package com.rzfk.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.rzfk.common.constant.Constants;
import com.rzfk.common.core.domain.R;
import com.rzfk.common.core.domain.entity.SysMenu;
import com.rzfk.common.core.domain.entity.SysUser;
import com.rzfk.common.core.domain.model.LoginBody;
import com.rzfk.common.core.domain.model.LoginUser;
import com.rzfk.common.utils.ServletUtils;
import com.rzfk.framework.web.service.SysLoginService;
import com.rzfk.framework.web.service.SysPermissionService;
import com.rzfk.framework.web.service.TokenService;
import com.rzfk.system.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 登录验证
 *
 * @author ruoyi
 */
@RestController
public class SysLoginController
{
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private TokenService tokenService;

    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public R login(@RequestBody LoginBody loginBody)
    {
		Map<String,Object> ajax = new HashMap<>();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return R.success(ajax);
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public R getInfo()
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
		Map<String,Object> ajax = new HashMap<>();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return R.success(ajax);
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public R getRouters()
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        // 用户信息
        SysUser user = loginUser.getUser();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(user.getUserId());
        return R.success(menuService.buildMenus(menus));
    }



    /**
     * 通过用户token获取用户权限
     * @return
     */
    @GetMapping("getUserPermission")
    public R getUserPermission() {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        return R.success(permissions);
    }

    /**
     * 查询大屏菜单
     * @return
     */
    @GetMapping("selectScreenMenu")
    public R selectScreenMenu() {
        List<JSONObject> jsonObjects = menuService.selectScreenMenu();
        return R.success(jsonObjects);
    }
}
