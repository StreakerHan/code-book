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
import com.rzfk.wallpaper.domain.vo.AppWallpapersTopicVo;
import com.rzfk.wallpaper.domain.bo.AppWallpapersTopicBo;
import com.rzfk.wallpaper.service.IAppWallpapersTopicService;
import com.rzfk.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiOperation;

/**
 * 壁纸主题Controller
 *
 * @author streaker
 * @date 2022-11-03
 */
@Validated
@Api(value = "壁纸主题控制器", tags = {"壁纸主题管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/wallpaper/wallpapersTopic")
public class AppWallpapersTopicController extends BaseController {

    private final IAppWallpapersTopicService iAppWallpapersTopicService;

    /**
     * 查询壁纸主题列表
     */
    @ApiOperation("查询壁纸主题列表")
    @GetMapping("/list")
    public TableDataInfo<AppWallpapersTopicVo> list(@Validated(QueryGroup.class) AppWallpapersTopicBo bo) {
        return iAppWallpapersTopicService.queryPageList(bo);
    }

    /**
     * 导出壁纸主题列表
     */
    @ApiOperation("导出壁纸主题列表")
    @PreAuthorize("@ss.hasPermi('wallpaper:wallpapersTopic:export')")
    @Log(title = "壁纸主题", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public R<AppWallpapersTopicVo> export(@Validated AppWallpapersTopicBo bo, HttpServletResponse response) {
        List<AppWallpapersTopicVo> list = iAppWallpapersTopicService.queryList(bo);
        ExcelUtil<AppWallpapersTopicVo> util = new ExcelUtil<AppWallpapersTopicVo>(AppWallpapersTopicVo.class);
        return util.exportExcel(list, "壁纸主题");
    }

    /**
     * 获取壁纸主题详细信息
     */
    @ApiOperation("获取壁纸主题详细信息")
    @PreAuthorize("@ss.hasPermi('wallpaper:wallpapersTopic:query')")
    @GetMapping("/{id}")
    public R<AppWallpapersTopicVo> getInfo(@ApiParam("主键")
                                                  @NotNull(message = "主键不能为空")
                                                  @PathVariable("id") Long id) {
        return R.success(iAppWallpapersTopicService.queryById(id));
    }

    /**
     * 新增壁纸主题
     */
    @ApiOperation("新增壁纸主题")
    @PreAuthorize("@ss.hasPermi('wallpaper:wallpapersTopic:add')")
    @Log(title = "壁纸主题", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody AppWallpapersTopicBo bo) {
        return toAjax(iAppWallpapersTopicService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改壁纸主题
     */
    @ApiOperation("修改壁纸主题")
    @PreAuthorize("@ss.hasPermi('wallpaper:wallpapersTopic:edit')")
    @Log(title = "壁纸主题", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody AppWallpapersTopicBo bo) {
        return toAjax(iAppWallpapersTopicService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除壁纸主题
     */
    @ApiOperation("删除壁纸主题")
    @PreAuthorize("@ss.hasPermi('wallpaper:wallpapersTopic:remove')")
    @Log(title = "壁纸主题" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@ApiParam("主键串")
                                       @NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] ids) {
        return toAjax(iAppWallpapersTopicService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
}
