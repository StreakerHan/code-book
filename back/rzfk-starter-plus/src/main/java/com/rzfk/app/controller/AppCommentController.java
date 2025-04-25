package com.rzfk.app.controller;

import java.util.List;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rzfk.app.domain.AppComment;
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
import com.rzfk.app.domain.vo.AppCommentVo;
import com.rzfk.app.domain.bo.AppCommentBo;
import com.rzfk.app.service.IAppCommentService;
import com.rzfk.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiOperation;

/**
 * 文章评论Controller
 *
 * @author streaker
 * @date 2022-08-17
 */
@Validated
@Api(value = "文章评论控制器", tags = {"文章评论管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/app/appComment")
public class AppCommentController extends BaseController {

    private final IAppCommentService iAppCommentService;

    /**
     * 查询文章评论列表
     */
    @ApiOperation("查询文章评论列表")
    @PreAuthorize("@ss.hasPermi('app:appComment:list')")
    @GetMapping("/list")
    public TableDataInfo<AppCommentVo> list(@Validated(QueryGroup.class) AppCommentBo bo) {
        return iAppCommentService.queryPageList(bo);
    }

    /**
     * 导出文章评论列表
     */
    @ApiOperation("导出文章评论列表")
    @PreAuthorize("@ss.hasPermi('app:appComment:export')")
    @Log(title = "文章评论", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public R<AppCommentVo> export(@Validated AppCommentBo bo, HttpServletResponse response) {
        List<AppCommentVo> list = iAppCommentService.queryList(bo);
        ExcelUtil<AppCommentVo> util = new ExcelUtil<AppCommentVo>(AppCommentVo.class);
        return util.exportExcel(list, "文章评论");
    }

    /**
     * 获取文章评论详细信息
     */
    @ApiOperation("获取文章评论详细信息")
    @PreAuthorize("@ss.hasPermi('app:appComment:query')")
    @GetMapping("/{id}")
    public R<AppCommentVo> getInfo(@ApiParam("主键")
                                   @NotNull(message = "主键不能为空")
                                   @PathVariable("id") Long id) {
        return R.success(iAppCommentService.queryById(id));
    }

    /**
     * 新增文章评论
     */
    @ApiOperation("新增文章评论")
    @PreAuthorize("@ss.hasPermi('app:appComment:add')")
    @Log(title = "文章评论", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody AppCommentBo bo) {
        return toAjax(iAppCommentService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 新增文章评论APP
     */
    @ApiOperation("新增文章评论")
    @PostMapping("/appInsert")
    public R<Void> appInsert(@Validated(AddGroup.class) @RequestBody AppCommentBo bo) {
        return toAjax(iAppCommentService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改文章评论
     */
    @ApiOperation("修改文章评论")
    @PreAuthorize("@ss.hasPermi('app:appComment:edit')")
    @Log(title = "文章评论", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody AppCommentBo bo) {
        return toAjax(iAppCommentService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除文章评论
     */
    @ApiOperation("删除文章评论")
    @PreAuthorize("@ss.hasPermi('app:appComment:remove')")
    @Log(title = "文章评论", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@ApiParam("主键串")
                          @NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iAppCommentService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }

    /**
     * 获取当前用户的评论列表
     */
    @GetMapping("/getComments")
    public TableDataInfo<AppCommentVo> getComments(@Validated(QueryGroup.class) AppCommentBo bo) {
        Long userId = SecurityUtils.getLoginUser().getAppUser().getId();
        bo.setUserId(userId);
        bo.setIsAsc("desc");
        bo.setOrderByColumn("create_time");
        return iAppCommentService.queryPageList(bo);
    }

    /**
     * app用户删除评论
     */
    @GetMapping("/delComments")
    public R delComments(Long id) {
        iAppCommentService.removeById(id);
        return R.success();
    }
}
