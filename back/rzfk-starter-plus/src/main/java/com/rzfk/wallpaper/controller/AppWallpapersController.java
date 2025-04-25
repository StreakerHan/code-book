package com.rzfk.wallpaper.controller;

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
import com.rzfk.wallpaper.domain.vo.AppWallpapersVo;
import com.rzfk.wallpaper.domain.bo.AppWallpapersBo;
import com.rzfk.wallpaper.service.IAppWallpapersService;
import com.rzfk.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiOperation;

/**
 * 壁纸Controller
 *
 * @author streaker
 * @date 2022-11-03
 */
@Validated
@Api(value = "壁纸控制器", tags = {"壁纸管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/wallpaper/wallpapers")
public class AppWallpapersController extends BaseController {

    private final IAppWallpapersService iAppWallpapersService;

    /**
     * 查询壁纸列表
     */
    @ApiOperation("查询壁纸列表")
    @GetMapping("/list")
    public TableDataInfo<AppWallpapersVo> list(@Validated(QueryGroup.class) AppWallpapersBo bo) {
        TableDataInfo<AppWallpapersVo> result= iAppWallpapersService.queryPageList(bo);
        return result;
    }

    /**
     * 导出壁纸列表
     */
    @ApiOperation("导出壁纸列表")
    @PreAuthorize("@ss.hasPermi('wallpaper:wallpapers:export')")
    @Log(title = "壁纸", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public R<AppWallpapersVo> export(@Validated AppWallpapersBo bo, HttpServletResponse response) {
        List<AppWallpapersVo> list = iAppWallpapersService.queryList(bo);
        ExcelUtil<AppWallpapersVo> util = new ExcelUtil<AppWallpapersVo>(AppWallpapersVo.class);
        return util.exportExcel(list, "壁纸");
    }

    /**
     * 获取壁纸详细信息
     */
    @ApiOperation("获取壁纸详细信息")
    @GetMapping("/{id}")
    public R<AppWallpapersVo> getInfo(@ApiParam("主键")
                                                  @NotNull(message = "主键不能为空")
                                                  @PathVariable("id") Long id) {
        return R.success(iAppWallpapersService.queryById(id));
    }

    /**
     * 新增壁纸
     */
    @ApiOperation("新增壁纸")
    @PreAuthorize("@ss.hasPermi('wallpaper:wallpapers:add')")
    @Log(title = "壁纸", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody AppWallpapersBo bo) {
        return toAjax(iAppWallpapersService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改壁纸
     */
    @ApiOperation("修改壁纸")
    @PreAuthorize("@ss.hasPermi('wallpaper:wallpapers:edit')")
    @Log(title = "壁纸", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody AppWallpapersBo bo) {
        return toAjax(iAppWallpapersService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除壁纸
     */
    @ApiOperation("删除壁纸")
    @PreAuthorize("@ss.hasPermi('wallpaper:wallpapers:remove')")
    @Log(title = "壁纸" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@ApiParam("主键串")
                                       @NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] ids) {
        return toAjax(iAppWallpapersService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
}
