package com.rzfk.app.controller;

import java.util.List;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

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
import com.rzfk.app.domain.vo.AppLoginRecordVo;
import com.rzfk.app.domain.bo.AppLoginRecordBo;
import com.rzfk.app.service.IAppLoginRecordService;
import com.rzfk.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiOperation;

/**
 * app用户登录记录Controller
 *
 * @author streaker
 * @date 2022-08-10
 */
@Validated
@Api(value = "app用户登录记录控制器", tags = {"app用户登录记录管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/app/appLoginRecord")
public class AppLoginRecordController extends BaseController {

    private final IAppLoginRecordService iAppLoginRecordService;

    /**
     * 查询app用户登录记录列表
     */
    @ApiOperation("查询app用户登录记录列表")
    @PreAuthorize("@ss.hasPermi('app:appLoginRecord:list')")
    @GetMapping("/list")
    public TableDataInfo<AppLoginRecordVo> list(@Validated(QueryGroup.class) AppLoginRecordBo bo) {
        return iAppLoginRecordService.queryPageList(bo);
    }

    /**
     * 导出app用户登录记录列表
     */
    @ApiOperation("导出app用户登录记录列表")
    @PreAuthorize("@ss.hasPermi('app:appLoginRecord:export')")
    @Log(title = "app用户登录记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public R<AppLoginRecordVo> export(@Validated AppLoginRecordBo bo, HttpServletResponse response) {
        List<AppLoginRecordVo> list = iAppLoginRecordService.queryList(bo);
        ExcelUtil<AppLoginRecordVo> util = new ExcelUtil<AppLoginRecordVo>(AppLoginRecordVo.class);
        return util.exportExcel(list, "app用户登录记录");
    }

    /**
     * 获取app用户登录记录详细信息
     */
    @ApiOperation("获取app用户登录记录详细信息")
    @PreAuthorize("@ss.hasPermi('app:appLoginRecord:query')")
    @GetMapping("/{id}")
    public R<AppLoginRecordVo> getInfo(@ApiParam("主键")
                                                  @NotNull(message = "主键不能为空")
                                                  @PathVariable("id") Long id) {
        return R.success(iAppLoginRecordService.queryById(id));
    }

    /**
     * 新增app用户登录记录
     */
    @ApiOperation("新增app用户登录记录")
    @PreAuthorize("@ss.hasPermi('app:appLoginRecord:add')")
    @Log(title = "app用户登录记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody AppLoginRecordBo bo) {
        return toAjax(iAppLoginRecordService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改app用户登录记录
     */
    @ApiOperation("修改app用户登录记录")
    @PreAuthorize("@ss.hasPermi('app:appLoginRecord:edit')")
    @Log(title = "app用户登录记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody AppLoginRecordBo bo) {
        return toAjax(iAppLoginRecordService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除app用户登录记录
     */
    @ApiOperation("删除app用户登录记录")
    @PreAuthorize("@ss.hasPermi('app:appLoginRecord:remove')")
    @Log(title = "app用户登录记录" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@ApiParam("主键串")
                                       @NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] ids) {
        return toAjax(iAppLoginRecordService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
}
