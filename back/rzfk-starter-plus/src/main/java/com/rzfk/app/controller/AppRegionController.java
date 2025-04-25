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
import com.rzfk.app.domain.vo.AppRegionVo;
import com.rzfk.app.domain.bo.AppRegionBo;
import com.rzfk.app.service.IAppRegionService;
import com.rzfk.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiOperation;

/**
 * 全国区划Controller
 *
 * @author streaker
 * @date 2022-08-10
 */
@Validated
@Api(value = "全国区划控制器", tags = {"全国区划管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/app/appRegion")
public class AppRegionController extends BaseController {

    private final IAppRegionService iAppRegionService;

    /**
     * 查询全国区划列表
     */
    @ApiOperation("查询全国区划列表")
    @GetMapping("/list")
    public TableDataInfo<AppRegionVo> list(@Validated(QueryGroup.class) AppRegionBo bo) {
        return iAppRegionService.queryPageList(bo);
    }

    /**
     * 导出全国区划列表
     */
    @ApiOperation("导出全国区划列表")
    @PreAuthorize("@ss.hasPermi('app:appRegion:export')")
    @Log(title = "全国区划", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public R<AppRegionVo> export(@Validated AppRegionBo bo, HttpServletResponse response) {
        List<AppRegionVo> list = iAppRegionService.queryList(bo);
        ExcelUtil<AppRegionVo> util = new ExcelUtil<AppRegionVo>(AppRegionVo.class);
        return util.exportExcel(list, "全国区划");
    }

    /**
     * 获取全国区划详细信息
     */
    @ApiOperation("获取全国区划详细信息")
    @PreAuthorize("@ss.hasPermi('app:appRegion:query')")
    @GetMapping("/{id}")
    public R<AppRegionVo> getInfo(@ApiParam("主键")
                                                  @NotNull(message = "主键不能为空")
                                                  @PathVariable("id") Long id) {
        return R.success(iAppRegionService.queryById(id));
    }

    /**
     * 新增全国区划
     */
    @ApiOperation("新增全国区划")
    @PreAuthorize("@ss.hasPermi('app:appRegion:add')")
    @Log(title = "全国区划", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody AppRegionBo bo) {
        return toAjax(iAppRegionService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改全国区划
     */
    @ApiOperation("修改全国区划")
    @PreAuthorize("@ss.hasPermi('app:appRegion:edit')")
    @Log(title = "全国区划", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody AppRegionBo bo) {
        return toAjax(iAppRegionService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除全国区划
     */
    @ApiOperation("删除全国区划")
    @PreAuthorize("@ss.hasPermi('app:appRegion:remove')")
    @Log(title = "全国区划" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@ApiParam("主键串")
                                       @NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] ids) {
        return toAjax(iAppRegionService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
}
