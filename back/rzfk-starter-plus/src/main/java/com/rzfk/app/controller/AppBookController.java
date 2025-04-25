package com.rzfk.app.controller;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rzfk.app.domain.AppBook;
import com.rzfk.app.domain.AppBookCategory;
import com.rzfk.app.domain.AppBookContent;
import com.rzfk.app.domain.AppInteractRecord;
import com.rzfk.app.service.IAppBookCategoryService;
import com.rzfk.app.service.IAppBookContentService;
import com.rzfk.app.service.IAppInteractRecordService;
import com.rzfk.common.core.page.PagePlus;
import com.rzfk.common.utils.IDUtils;
import com.rzfk.common.utils.PageUtils;
import com.rzfk.common.utils.SecurityUtils;
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
import com.rzfk.app.domain.vo.AppBookVo;
import com.rzfk.app.domain.bo.AppBookBo;
import com.rzfk.app.service.IAppBookService;
import com.rzfk.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiOperation;

/**
 * 书籍信息Controller
 *
 * @author streaker
 * @date 2023-02-03
 */
@Validated
@Api(value = "书籍信息控制器", tags = {"书籍信息管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/app/book")
public class AppBookController extends BaseController {

    private final IAppBookService iAppBookService;

    private final IAppBookCategoryService iAppBookCategoryService;

    private final IAppBookContentService iAppBookContentService;

    private final IAppInteractRecordService iAppInteractRecordService;


    /**
     * 查询书籍信息列表
     */
    @ApiOperation("查询书籍信息列表")
    @GetMapping("/list")
    public TableDataInfo<AppBookVo> list(@Validated(QueryGroup.class) AppBookBo bo) {
        return iAppBookService.queryPageList(bo);
    }


    /**
     * 查询我的收藏列表
     */
    @ApiOperation("查询我的收藏列表")
    @GetMapping("/collectList")
    public TableDataInfo<AppBook> collectList() {
        QueryWrapper<AppInteractRecord> interactRecordQueryWrapper = new QueryWrapper<>();
        interactRecordQueryWrapper.eq("user_id", SecurityUtils.getLoginUser().getAppUser().getId());
        interactRecordQueryWrapper.eq("type", "book");
        interactRecordQueryWrapper.eq("interact_type", "collect");
        interactRecordQueryWrapper.orderByDesc("create_time");
        List<AppInteractRecord> appInteractRecordList = iAppInteractRecordService.list(interactRecordQueryWrapper);
        List<Long> ids = appInteractRecordList.stream().map(AppInteractRecord::getSourceId).distinct().collect(Collectors.toList()); // 去重
        QueryWrapper<AppBook> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("*,(select b.name from app_book_category b where b.id = type) as typeName ");
        queryWrapper.in("id", ids);
        List<AppBook> list = iAppBookService.list(queryWrapper);
        return PageUtils.buildDataInfo(list);
    }

    /**
     * 查询我的阅读记录列表
     */
    @ApiOperation("查询我的阅读记录列表")
    @GetMapping("/recordList")
    public TableDataInfo<AppBook> recordList() {
        QueryWrapper<AppInteractRecord> interactRecordQueryWrapper = new QueryWrapper<>();
        interactRecordQueryWrapper.eq("user_id", SecurityUtils.getLoginUser().getAppUser().getId());
        interactRecordQueryWrapper.eq("type", "book");
        interactRecordQueryWrapper.eq("interact_type", "view");
        interactRecordQueryWrapper.orderByDesc("create_time");
        List<AppInteractRecord> appInteractRecordList = iAppInteractRecordService.list(interactRecordQueryWrapper);
        List<Long> ids = appInteractRecordList.stream().map(AppInteractRecord::getSourceId).distinct().collect(Collectors.toList()); // 去重
        QueryWrapper<AppBook> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("*,(select b.name from app_book_category b where b.id = type) as typeName ");
        queryWrapper.in("id", ids);
        List<AppBook> list = iAppBookService.list(queryWrapper);
        return PageUtils.buildDataInfo(list);
    }

    /**
     * 推荐阅读
     */
    @GetMapping("/recommend")
    public TableDataInfo<AppBook> recommend() {
        QueryWrapper<AppBook> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("*,(select b.name from app_book_category b where b.id = type) as typeName ");
        queryWrapper.last("order by RAND() limit 4");
        List<AppBook> list = iAppBookService.list(queryWrapper);
        return PageUtils.buildDataInfo(list);
    }

    /**
     * 导出书籍信息列表
     */
    @ApiOperation("导出书籍信息列表")
    @PreAuthorize("@ss.hasPermi('app:book:export')")
    @Log(title = "书籍信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public R<AppBookVo> export(@Validated AppBookBo bo, HttpServletResponse response) {
        List<AppBookVo> list = iAppBookService.queryList(bo);
        ExcelUtil<AppBookVo> util = new ExcelUtil<AppBookVo>(AppBookVo.class);
        return util.exportExcel(list, "书籍信息");
    }

    /**
     * 收藏书
     */
    @ApiOperation("收藏书")
    @GetMapping("/collect")
    public R<AppBookVo> collect(@RequestParam Map<String, Object> params) {
        AppBookVo appBook = iAppBookService.getVoById(Long.parseLong(params.get("id").toString()), AppBookVo.class);
        //查询是否已收藏
        QueryWrapper<AppInteractRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("source_id", appBook.getId());
        queryWrapper.eq("user_id", SecurityUtils.getLoginUser().getAppUser().getId());
        queryWrapper.eq("interact_type", "collect");
        List<AppInteractRecord> records = iAppInteractRecordService.list(queryWrapper);
        if (records.size() != 0) {
            //取消收藏
            iAppInteractRecordService.remove(queryWrapper);
        } else {
            //添加收藏
            AppInteractRecord appInteractRecord = new AppInteractRecord();
            appInteractRecord.setId(IDUtils.getId());
            appInteractRecord.setType("book");
            appInteractRecord.setCreateTime(new Date());
            appInteractRecord.setSourceId(appBook.getId());
            appInteractRecord.setInteractType("collect");
            appInteractRecord.setUserId(SecurityUtils.getLoginUser().getAppUser().getId());
            iAppInteractRecordService.save(appInteractRecord);
        }
        QueryWrapper<AppInteractRecord> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("source_id", appBook.getId());
        queryWrapper1.eq("user_id", SecurityUtils.getLoginUser().getAppUser().getId());
        queryWrapper1.eq("interact_type", "collect");
        List<AppInteractRecord> list = iAppInteractRecordService.list(queryWrapper1);
        if (list.size() != 0) {
            appBook.setCollect("1");
        } else {
            appBook.setCollect("0");
        }
        return R.success(appBook);
    }

    /**
     * 获取书籍信息详细信息
     */
    @ApiOperation("获取书籍信息详细信息")
    @GetMapping("/{id}")
    public R<AppBookVo> getInfo(@ApiParam("主键")
                                @NotNull(message = "主键不能为空")
                                @PathVariable("id") Long id) {
        AppBookVo appBookVo = iAppBookService.queryById(id);
        appBookVo.setCollect("0");
        return R.success(appBookVo);
    }

    /**
     * 获取书籍信息详细信息
     */
    @ApiOperation("获取书籍信息详细信息")
    @GetMapping("/info")
    public R<AppBookVo> getInfo1(@RequestParam Map<String, Object> params) {
        AppBookVo appBookVo = iAppBookService.queryById(Long.parseLong(params.get("id").toString()));
        appBookVo.setCollect("0");
        return R.success(appBookVo);
    }

    /**
     * 获取书籍信息详细信息
     */
    @ApiOperation("获取书籍信息详细信息")
    @GetMapping("/token")
    public R<AppBookVo> getInfoToken(@RequestParam Map<String, Object> params) {
        AppBookVo appBookVo = iAppBookService.queryById(Long.parseLong(params.get("id").toString()));
        QueryWrapper<AppInteractRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("source_id", appBookVo.getId());
        queryWrapper.eq("user_id", SecurityUtils.getLoginUser().getAppUser().getId());
        queryWrapper.eq("interact_type", "collect");
        List<AppInteractRecord> list = iAppInteractRecordService.list(queryWrapper);
        if (list.size() != 0) {
            appBookVo.setCollect("1");
        } else {
            appBookVo.setCollect("0");
        }
        return R.success(appBookVo);
    }

    /**
     * 新增书籍信息
     */
    @ApiOperation("新增书籍信息")
    @PreAuthorize("@ss.hasPermi('app:book:add')")
    @Log(title = "书籍信息", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody AppBookBo bo) {
        return toAjax(iAppBookService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改书籍信息
     */
    @ApiOperation("修改书籍信息")
    @PreAuthorize("@ss.hasPermi('app:book:edit')")
    @Log(title = "书籍信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody AppBookBo bo) {
        return toAjax(iAppBookService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除书籍信息
     */
    @ApiOperation("删除书籍信息")
    @PreAuthorize("@ss.hasPermi('app:book:remove')")
    @Log(title = "书籍信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@ApiParam("主键串")
                          @NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iAppBookService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
}
