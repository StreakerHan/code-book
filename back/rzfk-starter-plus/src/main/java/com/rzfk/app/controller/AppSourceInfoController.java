package com.rzfk.app.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rzfk.app.domain.AppInteractRecord;
import com.rzfk.app.domain.AppSourceInfo;
import com.rzfk.app.domain.vo.AppPostVo;
import com.rzfk.app.service.IAppInteractRecordService;
import com.rzfk.common.core.page.PagePlus;
import com.rzfk.common.jsonp.Flutter;
import com.rzfk.common.utils.IDUtils;
import com.rzfk.common.utils.PageUtils;
import com.rzfk.common.utils.SecurityUtils;
import com.rzfk.common.utils.StringUtils;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;

import net.sf.jsqlparser.statement.select.Distinct;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.ObjectError;
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
import com.rzfk.app.domain.vo.AppSourceInfoVo;
import com.rzfk.app.domain.bo.AppSourceInfoBo;
import com.rzfk.app.service.IAppSourceInfoService;
import com.rzfk.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiOperation;

/**
 * 资源信息Controller
 *
 * @author streaker
 * @date 2022-08-19
 */
@Validated
@Api(value = "资源信息控制器", tags = {"资源信息管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/app/appSourceInfo")
public class AppSourceInfoController extends BaseController {

    private final IAppSourceInfoService iAppSourceInfoService;

    @Autowired
    private IAppInteractRecordService appInteractRecordService;

    /**
     * app首页随机
     */
    @ApiOperation("app首页随机")
//    @PreAuthorize("@ss.hasPermi('app:appSourceInfo:list')")
    @GetMapping("/randomList")
    public R randomList(@Validated(QueryGroup.class) AppSourceInfoBo bo) {
        QueryWrapper<AppSourceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNotNull("link");
        queryWrapper.notLike("url", "/tag/");
//        queryWrapper.ge("id", "( SELECT   floor (  RAND ()  *  (( SELECT   MAX (id)  FROM  app_source_info) - ( SELECT   MIN (id)  FROM  app_source_info))  +  ( SELECT   MIN (id)  FROM  app_source_info)))   ");
        queryWrapper.last("order by RAND()  limit 20");
        List<AppSourceInfo> list = iAppSourceInfoService.list(queryWrapper);
        return R.success(list);
    }

    /**
     * 查询资源信息列表
     */
    @ApiOperation("查询资源信息列表")
//    @PreAuthorize("@ss.hasPermi('app:appSourceInfo:list')")
    @GetMapping("/list")
    public TableDataInfo<AppSourceInfoVo> list(@Validated(QueryGroup.class) AppSourceInfoBo bo) {
        return iAppSourceInfoService.queryPageList(bo);
    }

    /**
     * 查询资源信息列表
     */
    @ApiOperation("查询资源信息列表")
    @GetMapping("/v1/webList/100")
    public R webList(@RequestParam Map<String, Object> params) throws IOException {
        QueryWrapper<AppSourceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("name,img,url,upload_time,type ");
//        queryWrapper.isNull("content");
        queryWrapper.notLike("url", "/tag/");
        queryWrapper.last("limit 0,100");
        List<AppSourceInfo> list = iAppSourceInfoService.list(queryWrapper);
        return R.success(list);
    }

    /**
     * 查询资源信息列表
     */
    @ApiOperation("查询资源信息列表")
    @GetMapping("/v1/webList/info")
    public R webListInfo(@RequestParam Map<String, Object> params) throws IOException {
        QueryWrapper<AppSourceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("content,url,name,upload_time,img,link ");
        queryWrapper.eq("url", params.get("url").toString());
        queryWrapper.last("limit 1");
        List<AppSourceInfo> list = iAppSourceInfoService.list(queryWrapper);
        return R.success(list);
    }

    /**
     * 查询资源信息列表
     */
    @ApiOperation("查询资源信息列表")
    @GetMapping("/listAll2")
    public R listAll2(@RequestParam Map<String, Object> params) throws IOException {
        QueryWrapper<AppSourceInfo> queryWrapper = new QueryWrapper<>();
        Optional.ofNullable(params.get("type")).ifPresent(str -> queryWrapper.like("type", str));
        Optional.ofNullable(params.get("name")).ifPresent(str -> queryWrapper.like("name", str));
        Optional.ofNullable(params.get("status")).ifPresent(str -> queryWrapper.like("status", str));
        queryWrapper.isNull("content");
        queryWrapper.notLike("url", "/tag/");
        List<AppSourceInfo> list = iAppSourceInfoService.list(queryWrapper);
        return R.success(list);
    }

    /**
     * 查询资源信息列表
     */
    @ApiOperation("查询资源信息列表")
    @GetMapping("/listAll")
    public R listAll(@RequestParam Map<String, Object> params) throws IOException {
        QueryWrapper<AppSourceInfo> queryWrapper = new QueryWrapper<>();
        Optional.ofNullable(params.get("type")).ifPresent(str -> queryWrapper.like("type", str));
        Optional.ofNullable(params.get("name")).ifPresent(str -> queryWrapper.like("name", str));
        Optional.ofNullable(params.get("status")).ifPresent(str -> queryWrapper.like("status", str));
        queryWrapper.isNull("link");
        queryWrapper.eq("seq","vue");
        queryWrapper.notLike("url", "/tag/");
        List<AppSourceInfo> list = iAppSourceInfoService.list(queryWrapper);
        for (int i = 0; i < list.size(); i++) {
            if (StringUtils.isNotEmpty(list.get(i).getUrl())) {
                Map<String, String> map = Flutter.getDetail("https://vuejsexamples.com" + list.get(i).getUrl());
                list.get(i).setLink(map.get("git"));
                list.get(i).setContent(map.get("content"));
                iAppSourceInfoService.updateById(list.get(i));
            }
        }
        return R.success(list);
    }

    /**
     * 查询资源信息列表
     */
    @ApiOperation("查询资源信息列表")
    @GetMapping("/listAll1")
    public R listAll1(@RequestParam Map<String, Object> params) throws IOException {
        QueryWrapper<AppSourceInfo> queryWrapper = new QueryWrapper<>();
        Optional.ofNullable(params.get("type")).ifPresent(str -> queryWrapper.like("type", str));
        Optional.ofNullable(params.get("name")).ifPresent(str -> queryWrapper.like("name", str));
        Optional.ofNullable(params.get("status")).ifPresent(str -> queryWrapper.like("status", str));
        queryWrapper.isNull("link");
        queryWrapper.eq("seq","vue");
        queryWrapper.notLike("url", "/tag/");
        queryWrapper.orderByDesc("create_time");
        List<AppSourceInfo> list = iAppSourceInfoService.list(queryWrapper);
        for (int i = 0; i < list.size(); i++) {
            if (StringUtils.isNotEmpty(list.get(i).getUrl())) {
                Map<String, String> map = Flutter.getDetail("https://vuejsexamples.com" + list.get(i).getUrl());
                list.get(i).setLink(map.get("git"));
                list.get(i).setContent(map.get("content"));
                iAppSourceInfoService.updateById(list.get(i));
            }
        }
        return R.success(list);
    }

    /**
     * 导出资源信息列表
     */
    @ApiOperation("导出资源信息列表")
    @PreAuthorize("@ss.hasPermi('app:appSourceInfo:export')")
    @Log(title = "资源信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public R<AppSourceInfoVo> export(@Validated AppSourceInfoBo bo, HttpServletResponse response) {
        List<AppSourceInfoVo> list = iAppSourceInfoService.queryList(bo);
        ExcelUtil<AppSourceInfoVo> util = new ExcelUtil<AppSourceInfoVo>(AppSourceInfoVo.class);
        return util.exportExcel(list, "资源信息");
    }

    /**
     * 获取资源信息详细信息
     */
    @ApiOperation("获取资源信息详细信息")
    @GetMapping("/{id}")
    public R<AppSourceInfoVo> getInfo(@ApiParam("主键")
                                      @NotNull(message = "主键不能为空")
                                      @PathVariable("id") Long id) {
        return R.success(iAppSourceInfoService.queryById(id));
    }

    /**
     * 获取app文章详细信息
     */
    @ApiOperation("获取app文章详细信息")
    @GetMapping("/info")
    public R<AppSourceInfoVo> getInfoApp(String id) {
        return R.success(iAppSourceInfoService.queryByIdApp(Long.parseLong(id)));
    }

    /**
     * 获取app文章详细信息
     */
    @ApiOperation("获取app文章详细信息")
    @GetMapping("/infoWithToken")
    public R<AppSourceInfoVo> infoWithToken(String id) {
        return R.success(iAppSourceInfoService.getInfoAppWithToken(Long.parseLong(id)));
    }

    /**
     * 新增资源信息
     */
    @ApiOperation("新增资源信息")
    @PreAuthorize("@ss.hasPermi('app:appSourceInfo:add')")
    @Log(title = "资源信息", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody AppSourceInfoBo bo) {
        bo.setCreateTime(new Date());
        return toAjax(iAppSourceInfoService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改资源信息
     */
    @ApiOperation("修改资源信息")
    @PreAuthorize("@ss.hasPermi('app:appSourceInfo:edit')")
    @Log(title = "资源信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody AppSourceInfoBo bo) {
        return toAjax(iAppSourceInfoService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除资源信息
     */
    @ApiOperation("删除资源信息")
    @PreAuthorize("@ss.hasPermi('app:appSourceInfo:remove')")
    @Log(title = "资源信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@ApiParam("主键串")
                          @NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iAppSourceInfoService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }

    /**
     * 获取我收藏的资源
     */
    @GetMapping("/getMyCollect")
    public TableDataInfo<AppSourceInfoVo> getMyCollect(@Validated(QueryGroup.class) AppSourceInfoBo bo) {
        //获取我收藏的资源id列表
        QueryWrapper<AppInteractRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", SecurityUtils.getLoginUser().getAppUser().getId());
        queryWrapper.eq("interact_type", "collect");
        List<AppInteractRecord> list = appInteractRecordService.list(queryWrapper);
        List<Long> ids = list.stream().map(AppInteractRecord::getSourceId).distinct().collect(Collectors.toList()); // 去重
        if (ids.size() != 0) {
            bo.setIds(ids);
        }
        return iAppSourceInfoService.queryPageList(bo);
    }

    /**
     * 获取资源type集合
     */
    @GetMapping("/getTypes")
    public R getTypes() {
        //获取我收藏的资源id列表
        QueryWrapper<AppSourceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("distinct ( type )");
        List<AppSourceInfo> list = iAppSourceInfoService.list();
        List<String> types = list.stream().map(AppSourceInfo::getType).distinct().collect(Collectors.toList()); // 去重
        return R.success(types);
    }
}
