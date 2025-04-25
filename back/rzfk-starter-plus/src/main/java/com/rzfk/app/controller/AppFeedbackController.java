package com.rzfk.app.controller;

import java.util.Date;
import java.util.List;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import com.rzfk.common.utils.SecurityUtils;
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
import com.rzfk.app.domain.vo.AppFeedbackVo;
import com.rzfk.app.domain.bo.AppFeedbackBo;
import com.rzfk.app.service.IAppFeedbackService;
import com.rzfk.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiOperation;

/**
 * 意见反馈Controller
 *
 * @author streaker
 * @date 2022-08-18
 */
@Validated
@Api(value = "意见反馈控制器", tags = {"意见反馈管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/app/feedback")
public class AppFeedbackController extends BaseController {

    private final IAppFeedbackService iAppFeedbackService;

    /**
     * 查询意见反馈列表
     */
    @ApiOperation("查询意见反馈列表")
    @PreAuthorize("@ss.hasPermi('app:feedback:list')")
    @GetMapping("/list")
    public TableDataInfo<AppFeedbackVo> list(@Validated(QueryGroup.class) AppFeedbackBo bo) {
        return iAppFeedbackService.queryPageList(bo);
    }

    /**
     * 导出意见反馈列表
     */
    @ApiOperation("导出意见反馈列表")
    @PreAuthorize("@ss.hasPermi('app:feedback:export')")
    @Log(title = "意见反馈", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public R<AppFeedbackVo> export(@Validated AppFeedbackBo bo, HttpServletResponse response) {
        List<AppFeedbackVo> list = iAppFeedbackService.queryList(bo);
        ExcelUtil<AppFeedbackVo> util = new ExcelUtil<AppFeedbackVo>(AppFeedbackVo.class);
        return util.exportExcel(list, "意见反馈");
    }

    /**
     * 获取意见反馈详细信息
     */
    @ApiOperation("获取意见反馈详细信息")
    @PreAuthorize("@ss.hasPermi('app:feedback:query')")
    @GetMapping("/{id}")
    public R<AppFeedbackVo> getInfo(@ApiParam("主键")
                                                  @NotNull(message = "主键不能为空")
                                                  @PathVariable("id") Long id) {
        return R.success(iAppFeedbackService.queryById(id));
    }

    /**
     * 新增意见反馈
     */
    @ApiOperation("新增意见反馈")
    @PreAuthorize("@ss.hasPermi('app:feedback:add')")
    @Log(title = "意见反馈", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody AppFeedbackBo bo) {
        return toAjax(iAppFeedbackService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改意见反馈
     */
    @ApiOperation("修改意见反馈")
    @PreAuthorize("@ss.hasPermi('app:feedback:edit')")
    @Log(title = "意见反馈", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody AppFeedbackBo bo) {
        return toAjax(iAppFeedbackService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除意见反馈
     */
    @ApiOperation("删除意见反馈")
    @PreAuthorize("@ss.hasPermi('app:feedback:remove')")
    @Log(title = "意见反馈" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@ApiParam("主键串")
                                       @NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] ids) {
        return toAjax(iAppFeedbackService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }

    /**
     * app新增意见反馈
     */
    @ApiOperation("app新增意见反馈")
    @RepeatSubmit()
    @PostMapping("/add")
    public R<Void> addApp(@Validated(AddGroup.class) @RequestBody AppFeedbackBo bo) {
        return toAjax(iAppFeedbackService.insertByBo(bo) ? 1 : 0);
    }
}
