package com.rzfk.app.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import cn.hutool.core.util.StrUtil;
import com.rzfk.app.domain.AppBookCategory;
import com.rzfk.common.utils.IDUtils;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
import com.rzfk.app.domain.vo.AppBookCategoryVo;
import com.rzfk.app.domain.bo.AppBookCategoryBo;
import com.rzfk.app.service.IAppBookCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiOperation;

/**
 * 书籍分类Controller
 *
 * @author streaker
 * @date 2023-02-03
 */
@Validated
@Api(value = "书籍分类控制器", tags = {"书籍分类管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/app/bookCategory")
public class AppBookCategoryController extends BaseController {

    private final IAppBookCategoryService iAppBookCategoryService;

    /**
     * 查询书籍分类列表
     */
    @ApiOperation("查询书籍分类列表")
    @GetMapping("/list")
    public R<List<AppBookCategoryVo>> list(@Validated(QueryGroup.class) AppBookCategoryBo bo) {
        List<AppBookCategoryVo> list = iAppBookCategoryService.queryList(bo);
        return R.success(list);
    }

    /**
     * 导出书籍分类列表
     */
    @ApiOperation("导出书籍分类列表")
    @PreAuthorize("@ss.hasPermi('app:bookCategory:export')")
    @Log(title = "书籍分类", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public R<AppBookCategoryVo> export(@Validated AppBookCategoryBo bo, HttpServletResponse response) {
        List<AppBookCategoryVo> list = iAppBookCategoryService.queryList(bo);
        ExcelUtil<AppBookCategoryVo> util = new ExcelUtil<AppBookCategoryVo>(AppBookCategoryVo.class);
        return util.exportExcel(list, "书籍分类");
    }

    /**
     * 获取书籍分类详细信息
     */
    @ApiOperation("获取书籍分类详细信息")
    @PreAuthorize("@ss.hasPermi('app:bookCategory:query')")
    @GetMapping("/{id}")
    public R<AppBookCategoryVo> getInfo(@ApiParam("主键")
                                        @NotNull(message = "主键不能为空")
                                        @PathVariable("id") Long id) {
        return R.success(iAppBookCategoryService.queryById(id));
    }

    /**
     * 新增书籍分类
     */
    @ApiOperation("新增书籍分类")
    @PreAuthorize("@ss.hasPermi('app:bookCategory:add')")
    @Log(title = "书籍分类", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody AppBookCategoryBo bo) {
        return toAjax(iAppBookCategoryService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改书籍分类
     */
    @ApiOperation("修改书籍分类")
    @PreAuthorize("@ss.hasPermi('app:bookCategory:edit')")
    @Log(title = "书籍分类", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody AppBookCategoryBo bo) {
        return toAjax(iAppBookCategoryService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除书籍分类
     */
    @ApiOperation("删除书籍分类")
    @PreAuthorize("@ss.hasPermi('app:bookCategory:remove')")
    @Log(title = "书籍分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@ApiParam("主键串")
                          @NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iAppBookCategoryService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
}
