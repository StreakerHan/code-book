package com.rzfk.app.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.rzfk.app.domain.AppBook;
import com.rzfk.app.domain.AppBookCategory;
import com.rzfk.app.domain.AppBookContent;
import com.rzfk.app.domain.AppInteractRecord;
import com.rzfk.app.service.IAppBookService;
import com.rzfk.app.service.IAppInteractRecordService;
import com.rzfk.common.core.page.TableDataInfo;
import com.rzfk.common.utils.IDUtils;
import com.rzfk.common.utils.PageUtils;
import com.rzfk.common.utils.SecurityUtils;
import com.rzfk.common.utils.StringUtils;
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
import com.rzfk.app.domain.vo.AppBookContentVo;
import com.rzfk.app.domain.bo.AppBookContentBo;
import com.rzfk.app.service.IAppBookContentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiOperation;

/**
 * 书籍内容Controller
 *
 * @author streaker
 * @date 2023-02-03
 */
@Validated
@Api(value = "书籍内容控制器", tags = {"书籍内容管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/app/bookContent")
public class AppBookContentController extends BaseController {

    private final IAppBookContentService iAppBookContentService;

    private final IAppBookService iAppBookService;

    private final IAppInteractRecordService iAppInteractRecordService;

    /**
     * @param list
     * @param len
     * @return
     */
    public static <T> List<List<T>> splitList(List<T> list, int len) {
        if (list == null || list.size() == 0 || len < 1) {
            return null;
        }

        List<List<T>> result = new ArrayList<List<T>>();


        int size = list.size();
        int count = (size + len - 1) / len;


        for (int i = 0; i < count; i++) {
            List<T> subList = list.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
            result.add(subList);
        }
        return result;
    }

    public AppBookContent saveContent(Long bookId, String catalogueName, String seq, String href, Long parentId, String level) {
        AppBookContent appBookContent = new AppBookContent();
        appBookContent.setId(IDUtils.getId());
        appBookContent.setBookId(bookId);
        appBookContent.setCatalogueName(catalogueName);
        appBookContent.setSeq(seq);
        appBookContent.setHref(href);
        appBookContent.setLevel(level);
        appBookContent.setParentId(parentId);
        iAppBookContentService.save(appBookContent);
        return appBookContent;
    }

    /**
     * 查询书籍内容列表
     */
    @ApiOperation("查询书籍内容列表")
    @PreAuthorize("@ss.hasPermi('app:bookContent:list')")
    @GetMapping("/list")
    public R<List<AppBookContentVo>> list(@Validated(QueryGroup.class) AppBookContentBo bo) {
        List<AppBookContentVo> list = iAppBookContentService.queryList(bo);
        return R.success(list);
    }

    /**
     * 查询书籍内容列表
     */
    @ApiOperation("查询书籍内容列表")
    @GetMapping("/chaptersList")
    public TableDataInfo<AppBookContent> chaptersList(@Validated(QueryGroup.class) AppBookContentBo bo) {
        QueryWrapper<AppBookContent> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id,catalogue_name,seq,level,parent_id ");
        queryWrapper.eq("book_id", bo.getBookId());
        queryWrapper.last("limit 1000");
        List<AppBookContent> list = iAppBookContentService.list(queryWrapper);
        return PageUtils.buildDataInfo(list);
    }

    /**
     * 查询书籍内容
     */
    @ApiOperation("查询书籍内容列表")
    @GetMapping("/chaptersContent")
    public TableDataInfo<AppBookContent> chaptersContent(@RequestParam Map<String, Object> params) throws Exception {
        QueryWrapper<AppBookContent> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id,catalogue_name,seq,level,parent_id ");
        queryWrapper.eq("book_id", params.get("bookId").toString());
        queryWrapper.last("limit 1000");
        List<AppBookContent> list = iAppBookContentService.list(queryWrapper);
        AppBookContent appBookContent = iAppBookContentService.getById(list.get(Integer.parseInt(params.get("index").toString())).getId());
        list.clear();
        list.add(appBookContent);
        return PageUtils.buildDataInfo(list);
    }

    /**
     * 查询书籍内容
     */
    @ApiOperation("查询书籍内容列表")
    @GetMapping("/chaptersContentWithToken")
    public TableDataInfo<AppBookContent> chaptersContentWithToken(@RequestParam Map<String, Object> params) throws Exception {
        QueryWrapper<AppBookContent> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id,catalogue_name,seq,level,parent_id ");
        queryWrapper.eq("book_id", params.get("bookId").toString());
        queryWrapper.last("limit 1000");
        List<AppBookContent> list = iAppBookContentService.list(queryWrapper);
        AppBookContent appBookContent = iAppBookContentService.getById(list.get(Integer.parseInt(params.get("index").toString())).getId());
        list.clear();
        list.add(appBookContent);
        //增加阅读记录
        AppInteractRecord appInteractRecord = new AppInteractRecord();
        appInteractRecord.setId(IDUtils.getId());
        appInteractRecord.setType("book");
        appInteractRecord.setCreateTime(new Date());
        appInteractRecord.setSourceId(appBookContent.getBookId());
        appInteractRecord.setInteractType("view");
        appInteractRecord.setUserId(SecurityUtils.getLoginUser().getAppUser().getId());
        iAppInteractRecordService.save(appInteractRecord);
        return PageUtils.buildDataInfo(list);
    }



    /**
     * 查询书籍内容app内跳转
     */
    @ApiOperation("查询书籍内容app内跳转")
    @GetMapping("/chaptersContentHref")
    public TableDataInfo<AppBookContent> chaptersContentHref(@RequestParam Map<String, Object> params) throws Exception {
        QueryWrapper<AppBookContent> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id,catalogue_name,seq,level,parent_id ");
        queryWrapper.eq("book_id", params.get("bookId").toString());
        queryWrapper.eq("href", params.get("href").toString());
        queryWrapper.last("limit 1");
        List<AppBookContent> list = iAppBookContentService.list(queryWrapper);
        AppBookContent appBookContent = list.get(0);
        list.clear();
        list.add(appBookContent);
        return PageUtils.buildDataInfo(list);
    }


    /**
     * 导出书籍内容列表
     */
    @ApiOperation("导出书籍内容列表")
    @PreAuthorize("@ss.hasPermi('app:bookContent:export')")
    @Log(title = "书籍内容", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public R<AppBookContentVo> export(@Validated AppBookContentBo bo, HttpServletResponse response) {
        List<AppBookContentVo> list = iAppBookContentService.queryList(bo);
        ExcelUtil<AppBookContentVo> util = new ExcelUtil<AppBookContentVo>(AppBookContentVo.class);
        return util.exportExcel(list, "书籍内容");
    }

    /**
     * 获取书籍内容详细信息
     */
    @ApiOperation("获取书籍内容详细信息")
    @PreAuthorize("@ss.hasPermi('app:bookContent:query')")
    @GetMapping("/{id}")
    public R<AppBookContentVo> getInfo(@ApiParam("主键")
                                       @NotNull(message = "主键不能为空")
                                       @PathVariable("id") Long id) {
        return R.success(iAppBookContentService.queryById(id));
    }

    /**
     * 新增书籍内容
     */
    @ApiOperation("新增书籍内容")
    @PreAuthorize("@ss.hasPermi('app:bookContent:add')")
    @Log(title = "书籍内容", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody AppBookContentBo bo) {
        return toAjax(iAppBookContentService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改书籍内容
     */
    @ApiOperation("修改书籍内容")
    @PreAuthorize("@ss.hasPermi('app:bookContent:edit')")
    @Log(title = "书籍内容", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody AppBookContentBo bo) {
        return toAjax(iAppBookContentService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除书籍内容
     */
    @ApiOperation("删除书籍内容")
    @PreAuthorize("@ss.hasPermi('app:bookContent:remove')")
    @Log(title = "书籍内容", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@ApiParam("主键串")
                          @NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iAppBookContentService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
}
