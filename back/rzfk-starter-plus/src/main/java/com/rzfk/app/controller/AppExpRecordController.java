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
import com.rzfk.app.domain.vo.AppExpRecordVo;
import com.rzfk.app.domain.bo.AppExpRecordBo;
import com.rzfk.app.service.IAppExpRecordService;
import com.rzfk.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiOperation;

/**
 * 用户经验记录Controller
 *
 * @author streaker
 * @date 2022-08-13
 */
@Validated
@Api(value = "用户经验记录控制器", tags = {"用户经验记录管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/app/AppExpRecord")
public class AppExpRecordController extends BaseController {

    private final IAppExpRecordService iAppExpRecordService;

    /**
     * 查询用户经验记录列表
     */
    @ApiOperation("查询用户经验记录列表")
    @PreAuthorize("@ss.hasPermi('app:AppExpRecord:list')")
    @GetMapping("/list")
    public TableDataInfo<AppExpRecordVo> list(@Validated(QueryGroup.class) AppExpRecordBo bo) {
        return iAppExpRecordService.queryPageList(bo);
    }

    /**
     * 导出用户经验记录列表
     */
    @ApiOperation("导出用户经验记录列表")
    @PreAuthorize("@ss.hasPermi('app:AppExpRecord:export')")
    @Log(title = "用户经验记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public R<AppExpRecordVo> export(@Validated AppExpRecordBo bo, HttpServletResponse response) {
        List<AppExpRecordVo> list = iAppExpRecordService.queryList(bo);
        ExcelUtil<AppExpRecordVo> util = new ExcelUtil<AppExpRecordVo>(AppExpRecordVo.class);
        return util.exportExcel(list, "用户经验记录");
    }

    /**
     * 获取用户经验记录详细信息
     */
    @ApiOperation("获取用户经验记录详细信息")
    @PreAuthorize("@ss.hasPermi('app:AppExpRecord:query')")
    @GetMapping("/{id}")
    public R<AppExpRecordVo> getInfo(@ApiParam("主键")
                                                  @NotNull(message = "主键不能为空")
                                                  @PathVariable("id") Long id) {
        return R.success(iAppExpRecordService.queryById(id));
    }

    /**
     * 新增用户经验记录
     */
    @ApiOperation("新增用户经验记录")
    @PreAuthorize("@ss.hasPermi('app:AppExpRecord:add')")
    @Log(title = "用户经验记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody AppExpRecordBo bo) {
        return toAjax(iAppExpRecordService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改用户经验记录
     */
    @ApiOperation("修改用户经验记录")
    @PreAuthorize("@ss.hasPermi('app:AppExpRecord:edit')")
    @Log(title = "用户经验记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody AppExpRecordBo bo) {
        return toAjax(iAppExpRecordService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除用户经验记录
     */
    @ApiOperation("删除用户经验记录")
    @PreAuthorize("@ss.hasPermi('app:AppExpRecord:remove')")
    @Log(title = "用户经验记录" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@ApiParam("主键串")
                                       @NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] ids) {
        return toAjax(iAppExpRecordService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
}
