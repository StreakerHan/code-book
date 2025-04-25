package com.rzfk.app.controller;

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
import com.rzfk.app.domain.vo.AppPointsRecordVo;
import com.rzfk.app.domain.bo.AppPointsRecordBo;
import com.rzfk.app.service.IAppPointsRecordService;
import com.rzfk.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiOperation;

/**
 * 积分记录Controller
 *
 * @author streaker
 * @date 2022-08-13
 */
@Validated
@Api(value = "积分记录控制器", tags = {"积分记录管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/app/AppPointsRecord")
public class AppPointsRecordController extends BaseController {

    private final IAppPointsRecordService iAppPointsRecordService;

    /**
     * 查询积分记录列表
     */
    @ApiOperation("查询积分记录列表")
    @GetMapping("/list")
    public TableDataInfo<AppPointsRecordVo> list(@Validated(QueryGroup.class) AppPointsRecordBo bo) {
        if("app".equals(bo.getType())){
            bo.setMemberId(SecurityUtils.getLoginUser().getAppUser().getId());
        }
        return iAppPointsRecordService.queryPageList(bo);
    }

    /**
     * 导出积分记录列表
     */
    @ApiOperation("导出积分记录列表")
    @PreAuthorize("@ss.hasPermi('app:AppPointsRecord:export')")
    @Log(title = "积分记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public R<AppPointsRecordVo> export(@Validated AppPointsRecordBo bo, HttpServletResponse response) {
        List<AppPointsRecordVo> list = iAppPointsRecordService.queryList(bo);
        ExcelUtil<AppPointsRecordVo> util = new ExcelUtil<AppPointsRecordVo>(AppPointsRecordVo.class);
        return util.exportExcel(list, "积分记录");
    }

    /**
     * 获取积分记录详细信息
     */
    @ApiOperation("获取积分记录详细信息")
    @PreAuthorize("@ss.hasPermi('app:AppPointsRecord:query')")
    @GetMapping("/{id}")
    public R<AppPointsRecordVo> getInfo(@ApiParam("主键")
                                                  @NotNull(message = "主键不能为空")
                                                  @PathVariable("id") Long id) {
        return R.success(iAppPointsRecordService.queryById(id));
    }

    /**
     * 新增积分记录
     */
    @ApiOperation("新增积分记录")
    @PreAuthorize("@ss.hasPermi('app:AppPointsRecord:add')")
    @Log(title = "积分记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody AppPointsRecordBo bo) {
        return toAjax(iAppPointsRecordService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改积分记录
     */
    @ApiOperation("修改积分记录")
    @PreAuthorize("@ss.hasPermi('app:AppPointsRecord:edit')")
    @Log(title = "积分记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody AppPointsRecordBo bo) {
        return toAjax(iAppPointsRecordService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除积分记录
     */
    @ApiOperation("删除积分记录")
    @PreAuthorize("@ss.hasPermi('app:AppPointsRecord:remove')")
    @Log(title = "积分记录" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@ApiParam("主键串")
                                       @NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] ids) {
        return toAjax(iAppPointsRecordService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
}
