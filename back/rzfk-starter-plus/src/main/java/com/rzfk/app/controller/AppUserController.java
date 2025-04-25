package com.rzfk.app.controller;

import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rzfk.app.domain.AppUser;
import com.rzfk.app.domain.bo.AppUserUpdateBo;
import com.rzfk.common.core.domain.model.LoginUser;
import com.rzfk.common.utils.EncryptUtils;
import com.rzfk.common.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;

import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.rzfk.common.annotation.RepeatSubmit;
import com.rzfk.common.annotation.Log;
import com.rzfk.common.core.controller.BaseController;
import com.rzfk.common.core.domain.R;
import com.rzfk.common.core.validate.AddGroup;
import com.rzfk.common.core.validate.EditGroup;
import com.rzfk.common.core.validate.QueryGroup;
import com.rzfk.common.enums.BusinessType;
import com.rzfk.common.utils.poi.ExcelUtil;
import com.rzfk.app.domain.vo.AppUserVo;
import com.rzfk.app.domain.bo.AppUserBo;
import com.rzfk.app.service.IAppUserService;
import com.rzfk.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiOperation;

/**
 * app用户Controller
 *
 * @author streaker
 * @date 2022-08-08
 */
@Validated
@Api(value = "app用户控制器", tags = {"app用户管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/app/appUser")
public class AppUserController extends BaseController {

    private final IAppUserService iAppUserService;

    /**
     * 查询app用户列表
     */
    @ApiOperation("查询app用户列表")
    @PreAuthorize("@ss.hasPermi('app:appUser:list')")
    @GetMapping("/list")
    public TableDataInfo<AppUserVo> list(@Validated(QueryGroup.class) AppUserBo bo) {
        return iAppUserService.queryPageList(bo);
    }

    /**
     * 导出app用户列表
     */
    @ApiOperation("导出app用户列表")
    @PreAuthorize("@ss.hasPermi('app:appUser:export')")
    @Log(title = "app用户", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public R<AppUserVo> export(@Validated AppUserBo bo, HttpServletResponse response) {
        List<AppUserVo> list = iAppUserService.queryList(bo);
        ExcelUtil<AppUserVo> util = new ExcelUtil<AppUserVo>(AppUserVo.class);
        return util.exportExcel(list, "app用户");
    }

    /**
     * 获取app用户详细信息
     */
    @ApiOperation("获取app用户详细信息")
    @PreAuthorize("@ss.hasPermi('app:appUser:query')")
    @GetMapping("/{id}")
    public R<AppUserVo> getInfo(@ApiParam("主键")
                                @NotNull(message = "主键不能为空")
                                @PathVariable("id") Long id) {
        return R.success(iAppUserService.queryById(id));
    }

    /**
     * 新增app用户
     */
    @ApiOperation("新增app用户")
    @PreAuthorize("@ss.hasPermi('app:appUser:add')")
    @Log(title = "app用户", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody AppUserBo bo) {
        return toAjax(iAppUserService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改app用户
     */
    @ApiOperation("修改app用户")
    @Log(title = "app用户", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/update")
    public R<Void> edit(@RequestBody AppUserUpdateBo bo) {
        return toAjax(iAppUserService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除app用户
     */
    @ApiOperation("删除app用户")
    @PreAuthorize("@ss.hasPermi('app:appUser:remove')")
    @Log(title = "app用户", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@ApiParam("主键串")
                          @NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iAppUserService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }

    /**
     * 退出登录
     */
    @GetMapping("/logout")
    public R logout() {
        return R.success();
    }

    /**
     * 根据token获取app用户详细信息
     */
    @ApiOperation("根据token获取app用户详细信息")
    @GetMapping("/getInfoByToken")
    public R<AppUserVo> getInfoByToken() {
        AppUser appUser = SecurityUtils.getLoginUser().getAppUser();
        return R.success(iAppUserService.queryById(appUser.getId()));
    }

    //修改密码
    @PostMapping("/modifyPass")
    public R modifyPass(@RequestBody Map<String, Object> body) {
        //校验旧密码是否正确
        AppUser appUser = SecurityUtils.getLoginUser().getAppUser();
        QueryWrapper<AppUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", appUser.getId());
        queryWrapper.eq("password", EncryptUtils.md5(body.get("password").toString()));
        List<AppUser> list = iAppUserService.list(queryWrapper);
        if (list.size() != 0) {
            appUser.setPassword(EncryptUtils.md5(body.get("newPassword").toString()));
            iAppUserService.updateById(appUser);
            return R.success();
        } else {
            return R.error("旧密码输入不正确");
        }
    }

    //app更新用户信息
    @PostMapping("/userInfoUpdate")
    public R userInfoUpdate(@RequestBody AppUserBo bo) {
        AppUser appUser = SecurityUtils.getLoginUser().getAppUser();
        bo.setId(appUser.getId());
        AppUserUpdateBo appUserUpdateBo = new AppUserUpdateBo();
        BeanUtils.copyProperties(bo,appUserUpdateBo);
        iAppUserService.updateByBo(appUserUpdateBo);
        return R.success();
    }

    /**
     * 注销用户
     */
    @PostMapping("/logoff")
    public R logoff() {
        AppUser appUser = SecurityUtils.getLoginUser().getAppUser();
        iAppUserService.removeById(appUser.getId());
        return R.success();
    }
}
