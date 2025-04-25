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
import com.rzfk.app.domain.vo.AppSmsRecordVo;
import com.rzfk.app.domain.bo.AppSmsRecordBo;
import com.rzfk.app.service.IAppSmsRecordService;
import com.rzfk.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiOperation;

/**
 * 短信发送记录Controller
 *
 * @author streaker
 * @date 2022-08-20
 */
@Validated
@Api(value = "短信发送记录控制器", tags = {"短信发送记录管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/app/appSmsRecord")
public class AppSmsRecordController extends BaseController {

    private final IAppSmsRecordService iAppSmsRecordService;

    /**
     * 查询短信发送记录列表
     */
    @ApiOperation("查询短信发送记录列表")
    @PreAuthorize("@ss.hasPermi('app:appSmsRecord:list')")
    @GetMapping("/list")
    public TableDataInfo<AppSmsRecordVo> list(@Validated(QueryGroup.class) AppSmsRecordBo bo) {
        return iAppSmsRecordService.queryPageList(bo);
    }

    /**
     * 导出短信发送记录列表
     */
    @ApiOperation("导出短信发送记录列表")
    @PreAuthorize("@ss.hasPermi('app:appSmsRecord:export')")
    @Log(title = "短信发送记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public R<AppSmsRecordVo> export(@Validated AppSmsRecordBo bo, HttpServletResponse response) {
        List<AppSmsRecordVo> list = iAppSmsRecordService.queryList(bo);
        ExcelUtil<AppSmsRecordVo> util = new ExcelUtil<AppSmsRecordVo>(AppSmsRecordVo.class);
        return util.exportExcel(list, "短信发送记录");
    }

    /**
     * 获取短信发送记录详细信息
     */
    @ApiOperation("获取短信发送记录详细信息")
    @PreAuthorize("@ss.hasPermi('app:appSmsRecord:query')")
    @GetMapping("/{id}")
    public R<AppSmsRecordVo> getInfo(@ApiParam("主键")
                                                  @NotNull(message = "主键不能为空")
                                                  @PathVariable("id") Long id) {
        return R.success(iAppSmsRecordService.queryById(id));
    }

    /**
     * 新增短信发送记录
     */
    @ApiOperation("新增短信发送记录")
    @PreAuthorize("@ss.hasPermi('app:appSmsRecord:add')")
    @Log(title = "短信发送记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody AppSmsRecordBo bo) {
        return toAjax(iAppSmsRecordService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改短信发送记录
     */
    @ApiOperation("修改短信发送记录")
    @PreAuthorize("@ss.hasPermi('app:appSmsRecord:edit')")
    @Log(title = "短信发送记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody AppSmsRecordBo bo) {
        return toAjax(iAppSmsRecordService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除短信发送记录
     */
    @ApiOperation("删除短信发送记录")
    @PreAuthorize("@ss.hasPermi('app:appSmsRecord:remove')")
    @Log(title = "短信发送记录" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@ApiParam("主键串")
                                       @NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] ids) {
        return toAjax(iAppSmsRecordService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
}
