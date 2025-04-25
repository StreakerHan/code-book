package com.rzfk.app.controller;

import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rzfk.app.domain.AppVersion;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;

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
import com.rzfk.app.domain.vo.AppVersionVo;
import com.rzfk.app.domain.bo.AppVersionBo;
import com.rzfk.app.service.IAppVersionService;
import com.rzfk.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiOperation;

/**
 * app版本管理Controller
 *
 * @author streaker
 * @date 2022-08-20
 */
@Validated
@Api(value = "app版本管理控制器", tags = {"app版本管理管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/app/appVersion")
public class AppVersionController extends BaseController {

    private final IAppVersionService iAppVersionService;

    /**
     * 查询app版本管理列表
     */
    @ApiOperation("查询app版本管理列表")
    @GetMapping("/list")
    public TableDataInfo<AppVersionVo> list(@Validated(QueryGroup.class) AppVersionBo bo) {
        return iAppVersionService.queryPageList(bo);
    }

    /**
     * 导出app版本管理列表
     */
    @ApiOperation("导出app版本管理列表")
    @PreAuthorize("@ss.hasPermi('app:appVersion:export')")
    @Log(title = "app版本管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public R<AppVersionVo> export(@Validated AppVersionBo bo, HttpServletResponse response) {
        List<AppVersionVo> list = iAppVersionService.queryList(bo);
        ExcelUtil<AppVersionVo> util = new ExcelUtil<AppVersionVo>(AppVersionVo.class);
        return util.exportExcel(list, "app版本管理");
    }

    /**
     * 获取app版本管理详细信息
     */
    @ApiOperation("获取app版本管理详细信息")
    @PreAuthorize("@ss.hasPermi('app:appVersion:query')")
    @GetMapping("/{id}")
    public R<AppVersionVo> getInfo(@ApiParam("主键")
                                   @NotNull(message = "主键不能为空")
                                   @PathVariable("id") Long id) {
        return R.success(iAppVersionService.queryById(id));
    }

    /**
     * 新增app版本管理
     */
    @ApiOperation("新增app版本管理")
    @PreAuthorize("@ss.hasPermi('app:appVersion:add')")
    @Log(title = "app版本管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody AppVersionBo bo) {
        return toAjax(iAppVersionService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改app版本管理
     */
    @ApiOperation("修改app版本管理")
    @PreAuthorize("@ss.hasPermi('app:appVersion:edit')")
    @Log(title = "app版本管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody AppVersionBo bo) {
        return toAjax(iAppVersionService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除app版本管理
     */
    @ApiOperation("删除app版本管理")
    @PreAuthorize("@ss.hasPermi('app:appVersion:remove')")
    @Log(title = "app版本管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@ApiParam("主键串")
                          @NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iAppVersionService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }

    /**
     * app获取最新版本
     */
    @GetMapping("/appVersion")
    public R appVersion(@RequestParam Map<String, Object> params) {
        QueryWrapper<AppVersion> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",params.get("type").toString());
        List<AppVersion> list = iAppVersionService.list(queryWrapper);
        return R.success(list.get(0));
    }
}
