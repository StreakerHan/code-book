package com.rzfk.app.controller;

import java.util.Date;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rzfk.app.domain.AppPost;
import com.rzfk.app.domain.bo.AppPointsRecordBo;
import com.rzfk.app.domain.vo.AppPointsRecordVo;
import com.rzfk.common.utils.SecurityUtils;
import com.rzfk.common.utils.StringUtils;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;

import org.mybatis.spring.annotation.MapperScan;
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
import com.rzfk.app.domain.vo.AppPostVo;
import com.rzfk.app.domain.bo.AppPostBo;
import com.rzfk.app.service.IAppPostService;
import com.rzfk.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiOperation;

/**
 * app文章Controller
 *
 * @author streaker
 * @date 2022-08-14
 */
@Validated
@Api(value = "app文章控制器", tags = {"app文章管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/app/appPost")
public class AppPostController extends BaseController {

    private final IAppPostService iAppPostService;

    /**
     * 查询app文章列表
     */
    @ApiOperation("查询app文章列表")
    @PreAuthorize("@ss.hasPermi('app:appPost:list')")
    @GetMapping("/list")
    public TableDataInfo<AppPostVo> list(@Validated(QueryGroup.class) AppPostBo bo) {
        return iAppPostService.queryPageList(bo);
    }

    /**
     * 导出app文章列表
     */
    @ApiOperation("导出app文章列表")
    @PreAuthorize("@ss.hasPermi('app:appPost:export')")
    @Log(title = "app文章", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public R<AppPostVo> export(@Validated AppPostBo bo, HttpServletResponse response) {
        List<AppPostVo> list = iAppPostService.queryList(bo);
        ExcelUtil<AppPostVo> util = new ExcelUtil<AppPostVo>(AppPostVo.class);
        return util.exportExcel(list, "app文章");
    }

    /**
     * 获取app文章详细信息
     */
    @ApiOperation("获取app文章详细信息")
    @GetMapping("/{id}")
    public R<AppPostVo> getInfo(@ApiParam("主键")
                                @NotNull(message = "主键不能为空")
                                @PathVariable("id") Long id) {
        return R.success(iAppPostService.queryById(id));
    }

    /**
     * 获取app文章详细信息
     */
    @ApiOperation("获取app文章详细信息")
    @GetMapping("/app/1/{id}")
    public R<AppPostVo> getInfoApp(@ApiParam("主键")
                                @NotNull(message = "主键不能为空")
                                @PathVariable("id") Long id) {
        return R.success(iAppPostService.queryByIdApp(id));
    }

    /**
     * 获取app文章详细信息
     */
    @ApiOperation("获取app文章详细信息")
    @GetMapping("/app/2/{id}")
    public R<AppPostVo> getPostinfoWithToken(@ApiParam("主键")
                                   @NotNull(message = "主键不能为空")
                                   @PathVariable("id") Long id) {
        return R.success(iAppPostService.queryByIdAppWithToken(id));
    }

    /**
     * 新增app文章
     */
    @ApiOperation("新增app文章")
    @PreAuthorize("@ss.hasPermi('app:appPost:add')")
    @Log(title = "app文章", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody AppPostBo bo) {
        return toAjax(iAppPostService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改app文章
     */
    @ApiOperation("修改app文章")
    @PreAuthorize("@ss.hasPermi('app:appPost:edit')")
    @Log(title = "app文章", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody AppPostBo bo) {
        return toAjax(iAppPostService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除app文章
     */
    @ApiOperation("删除app文章")
    @PreAuthorize("@ss.hasPermi('app:appPost:remove')")
    @Log(title = "app文章", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@ApiParam("主键串")
                          @NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iAppPostService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }

    // list(@Validated(QueryGroup.class) AppPointsRecordBo bo) {
    @GetMapping("/appGetList")
    public TableDataInfo<AppPostVo> appGetList(@Validated(QueryGroup.class) AppPostBo bo) {
        return iAppPostService.queryPageListApp(bo);
    }

    @GetMapping("/appGetPointList")
    public TableDataInfo<AppPostVo> appGetPointList(@Validated(QueryGroup.class) AppPostBo bo) {
        return iAppPostService.queryPageList(bo);
    }

}
