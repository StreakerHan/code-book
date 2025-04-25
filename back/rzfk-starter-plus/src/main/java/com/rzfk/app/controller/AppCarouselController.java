package com.rzfk.app.controller;

import java.util.*;
import java.util.concurrent.TimeUnit;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rzfk.app.domain.AppCarousel;
import com.rzfk.app.domain.AppSourceInfo;
import com.rzfk.app.service.IAppSourceInfoService;
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
import com.rzfk.app.domain.vo.AppCarouselVo;
import com.rzfk.app.domain.bo.AppCarouselBo;
import com.rzfk.app.service.IAppCarouselService;
import com.rzfk.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiOperation;

/**
 * 轮播内容设置Controller
 *
 * @author streaker
 * @date 2022-08-14
 */
@Validated
@Api(value = "轮播内容设置控制器", tags = {"轮播内容设置管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/app/appCarousel")
public class AppCarouselController extends BaseController {

    private final IAppCarouselService iAppCarouselService;

    private final IAppSourceInfoService iAppSourceInfoService;

    /**
     * 查询轮播内容设置列表
     */
    @ApiOperation("查询轮播内容设置列表")
    @GetMapping("/list")
    public TableDataInfo<AppCarouselVo> list(@Validated(QueryGroup.class) AppCarouselBo bo) {
        return iAppCarouselService.queryPageList(bo);
    }

    /**
     * 导出轮播内容设置列表
     */
    @ApiOperation("导出轮播内容设置列表")
    @PreAuthorize("@ss.hasPermi('app:carousel:export')")
    @Log(title = "轮播内容设置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public R<AppCarouselVo> export(@Validated AppCarouselBo bo, HttpServletResponse response) {
        List<AppCarouselVo> list = iAppCarouselService.queryList(bo);
        ExcelUtil<AppCarouselVo> util = new ExcelUtil<AppCarouselVo>(AppCarouselVo.class);
        return util.exportExcel(list, "轮播内容设置");
    }

    /**
     * 获取轮播内容设置详细信息
     */
    @ApiOperation("获取轮播内容设置详细信息")
    @PreAuthorize("@ss.hasPermi('app:carousel:query')")
    @GetMapping("/{id}")
    public R<AppCarouselVo> getInfo(@ApiParam("主键")
                                    @NotNull(message = "主键不能为空")
                                    @PathVariable("id") Long id) {
        return R.success(iAppCarouselService.queryById(id));
    }

    /**
     * 新增轮播内容设置
     */
    @ApiOperation("新增轮播内容设置")
    @PreAuthorize("@ss.hasPermi('app:carousel:add')")
    @Log(title = "轮播内容设置", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody AppCarouselBo bo) {
        bo.setCreateTime(new Date());
        return toAjax(iAppCarouselService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改轮播内容设置
     */
    @ApiOperation("修改轮播内容设置")
    @PreAuthorize("@ss.hasPermi('app:carousel:edit')")
    @Log(title = "轮播内容设置", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody AppCarouselBo bo) {
        return toAjax(iAppCarouselService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除轮播内容设置
     */
    @ApiOperation("删除轮播内容设置")
    @PreAuthorize("@ss.hasPermi('app:carousel:remove')")
    @Log(title = "轮播内容设置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@ApiParam("主键串")
                          @NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iAppCarouselService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }

    /**
     * 获取app展示内容
     */
    @GetMapping("/getIndexList")
    public R getList() {
        QueryWrapper<AppCarousel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", "1");
        queryWrapper.eq("type", "top");
        List<AppCarousel> list1 = iAppCarouselService.list(queryWrapper);
        QueryWrapper<AppCarousel> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("status", "1");
        queryWrapper1.eq("type", "bottom");
        List<AppCarousel> list2 = iAppCarouselService.list(queryWrapper1);
        QueryWrapper<AppCarousel> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("status", "1");
        queryWrapper2.eq("type", "notice");
        List<AppCarousel> list3 = iAppCarouselService.list(queryWrapper2);
        Map<String, Object> res = new HashMap<>();
        res.put("top", list1);
        res.put("bottom", list2);
        res.put("notice", list3);
        return R.success(res);
    }
}
