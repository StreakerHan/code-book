package com.rzfk.app.controller;

import java.util.*;
import java.util.concurrent.TimeUnit;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.rzfk.app.domain.AppInteractRecord;
import com.rzfk.common.utils.IDUtils;
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
import com.rzfk.app.domain.vo.AppInteractRecordVo;
import com.rzfk.app.domain.bo.AppInteractRecordBo;
import com.rzfk.app.service.IAppInteractRecordService;
import com.rzfk.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiOperation;

/**
 * App资源互动记录Controller
 *
 * @author streaker
 * @date 2022-08-15
 */
@Validated
@Api(value = "App资源互动记录控制器", tags = {"App资源互动记录管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/app/appInteractRecord")
public class AppInteractRecordController extends BaseController {

    private final IAppInteractRecordService iAppInteractRecordService;

    /**
     * 查询App资源互动记录列表
     */
    @ApiOperation("查询App资源互动记录列表")
    @GetMapping("/list")
    public TableDataInfo<AppInteractRecordVo> list(@Validated(QueryGroup.class) AppInteractRecordBo bo) {
        return iAppInteractRecordService.queryPageList(bo);
    }

    /**
     * 导出App资源互动记录列表
     */
    @ApiOperation("导出App资源互动记录列表")
    @PreAuthorize("@ss.hasPermi('app:appInteractRecord:export')")
    @Log(title = "App资源互动记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public R<AppInteractRecordVo> export(@Validated AppInteractRecordBo bo, HttpServletResponse response) {
        List<AppInteractRecordVo> list = iAppInteractRecordService.queryList(bo);
        ExcelUtil<AppInteractRecordVo> util = new ExcelUtil<AppInteractRecordVo>(AppInteractRecordVo.class);
        return util.exportExcel(list, "App资源互动记录");
    }

    /**
     * 获取App资源互动记录详细信息
     */
    @ApiOperation("获取App资源互动记录详细信息")
    @PreAuthorize("@ss.hasPermi('app:appInteractRecord:query')")
    @GetMapping("/{id}")
    public R<AppInteractRecordVo> getInfo(@ApiParam("主键")
                                          @NotNull(message = "主键不能为空")
                                          @PathVariable("id") Long id) {
        return R.success(iAppInteractRecordService.queryById(id));
    }

    /**
     * 新增App资源互动记录
     */
    @ApiOperation("新增App资源互动记录")
    @PreAuthorize("@ss.hasPermi('app:appInteractRecord:add')")
    @Log(title = "App资源互动记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody AppInteractRecordBo bo) {
        return toAjax(iAppInteractRecordService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改App资源互动记录
     */
    @ApiOperation("修改App资源互动记录")
    @PreAuthorize("@ss.hasPermi('app:appInteractRecord:edit')")
    @Log(title = "App资源互动记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody AppInteractRecordBo bo) {
        return toAjax(iAppInteractRecordService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除App资源互动记录
     */
    @ApiOperation("删除App资源互动记录")
    @PreAuthorize("@ss.hasPermi('app:appInteractRecord:remove')")
    @Log(title = "App资源互动记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@ApiParam("主键串")
                          @NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iAppInteractRecordService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }

    /**
     * 点赞/取消点赞 文章
     */
    @PostMapping("/like")
    public R like(@RequestBody Map<String, Object> params) {
        Long id = Long.parseLong(params.get("id").toString());
        Long userId = SecurityUtils.getLoginUser().getAppUser().getId();
        QueryWrapper<AppInteractRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("source_id", id);
        queryWrapper.eq("interact_type", "like");
        List<AppInteractRecord> list = iAppInteractRecordService.list(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            AppInteractRecord appInteractRecord = new AppInteractRecord();
            appInteractRecord.setId(IDUtils.getId());
            appInteractRecord.setType("post");
            appInteractRecord.setInteractType("like");
            appInteractRecord.setCreateTime(new Date());
            appInteractRecord.setUserId(userId);
            appInteractRecord.setSourceId(id);
            iAppInteractRecordService.save(appInteractRecord);
            //查看当前动态的点赞数
            Map<String, Object> res = new HashMap<>();
            queryWrapper.clear();
            queryWrapper.eq("source_id", id);
            queryWrapper.eq("interact_type", "like");
            List<AppInteractRecord> list1 = iAppInteractRecordService.list(queryWrapper);
            res.put("like", list1.size() + "");
            //查看当前动态的收藏数
            queryWrapper.clear();
            queryWrapper.eq("source_id", id);
            queryWrapper.eq("interact_type", "collect");
            List<AppInteractRecord> list2 = iAppInteractRecordService.list(queryWrapper);
            res.put("collect", list2.size() + "");
            //查看当前用户是否点赞
            queryWrapper.clear();
            queryWrapper.eq("source_id", id);
            queryWrapper.eq("user_id", SecurityUtils.getLoginUser().getAppUser().getId());
            queryWrapper.eq("interact_type", "like");
            List<AppInteractRecord> list3 = iAppInteractRecordService.list(queryWrapper);
            res.put("isLike", list3.size() + "");
            //查看当前用户是否收藏
            queryWrapper.clear();
            queryWrapper.eq("source_id", id);
            queryWrapper.eq("user_id", SecurityUtils.getLoginUser().getAppUser().getId());
            queryWrapper.eq("interact_type", "collect");
            List<AppInteractRecord> list4 = iAppInteractRecordService.list(queryWrapper);
            res.put("isCollect", list4.size() + "");
            return R.success("点赞成功", res);
        } else {
            for (int i = 0; i < list.size(); i++) {
                iAppInteractRecordService.removeById(list.get(i).getId());
            }
            //查看当前动态的点赞数
            Map<String, Object> res = new HashMap<>();
            queryWrapper.clear();
            queryWrapper.eq("source_id", id);
            queryWrapper.eq("interact_type", "like");
            List<AppInteractRecord> list1 = iAppInteractRecordService.list(queryWrapper);
            res.put("like", list1.size() + "");
            //查看当前动态的收藏数
            queryWrapper.clear();
            queryWrapper.eq("source_id", id);
            queryWrapper.eq("interact_type", "collect");
            List<AppInteractRecord> list2 = iAppInteractRecordService.list(queryWrapper);
            res.put("collect", list2.size() + "");
            //查看当前用户是否点赞
            queryWrapper.clear();
            queryWrapper.eq("source_id", id);
            queryWrapper.eq("user_id", SecurityUtils.getLoginUser().getAppUser().getId());
            queryWrapper.eq("interact_type", "like");
            List<AppInteractRecord> list3 = iAppInteractRecordService.list(queryWrapper);
            res.put("isLike", list3.size() + "");
            //查看当前用户是否收藏
            queryWrapper.clear();
            queryWrapper.eq("source_id", id);
            queryWrapper.eq("user_id", SecurityUtils.getLoginUser().getAppUser().getId());
            queryWrapper.eq("interact_type", "collect");
            List<AppInteractRecord> list4 = iAppInteractRecordService.list(queryWrapper);
            res.put("isCollect", list4.size() + "");
            return R.success("取消点赞", res);
        }
    }

    /**
     * 收藏/取消收藏 文章
     */
    @PostMapping("/collect")
    public R collect(@RequestBody Map<String, Object> params) {
        Long id = Long.parseLong(params.get("id").toString());
        Long userId = SecurityUtils.getLoginUser().getAppUser().getId();
        QueryWrapper<AppInteractRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("source_id", id);
        queryWrapper.eq("interact_type", "collect");
        List<AppInteractRecord> list = iAppInteractRecordService.list(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            AppInteractRecord appInteractRecord = new AppInteractRecord();
            appInteractRecord.setId(IDUtils.getId());
            appInteractRecord.setType("post");
            appInteractRecord.setInteractType("collect");
            appInteractRecord.setCreateTime(new Date());
            appInteractRecord.setUserId(userId);
            appInteractRecord.setSourceId(id);
            iAppInteractRecordService.save(appInteractRecord);
            //查看当前动态的点赞数
            Map<String, Object> res = new HashMap<>();
            queryWrapper.clear();
            queryWrapper.eq("source_id", id);
            queryWrapper.eq("interact_type", "like");
            List<AppInteractRecord> list1 = iAppInteractRecordService.list(queryWrapper);
            res.put("like", list1.size() + "");
            //查看当前动态的收藏数
            queryWrapper.clear();
            queryWrapper.eq("source_id", id);
            queryWrapper.eq("interact_type", "collect");
            List<AppInteractRecord> list2 = iAppInteractRecordService.list(queryWrapper);
            res.put("collect", list2.size() + "");
            //查看当前用户是否点赞
            queryWrapper.clear();
            queryWrapper.eq("source_id", id);
            queryWrapper.eq("user_id", SecurityUtils.getLoginUser().getAppUser().getId());
            queryWrapper.eq("interact_type", "like");
            List<AppInteractRecord> list3 = iAppInteractRecordService.list(queryWrapper);
            res.put("isLike", list3.size() + "");
            //查看当前用户是否收藏
            queryWrapper.clear();
            queryWrapper.eq("source_id", id);
            queryWrapper.eq("user_id", SecurityUtils.getLoginUser().getAppUser().getId());
            queryWrapper.eq("interact_type", "collect");
            List<AppInteractRecord> list4 = iAppInteractRecordService.list(queryWrapper);
            res.put("isCollect", list4.size() + "");
            return R.success("收藏成功", res);
        } else {
            for (int i = 0; i < list.size(); i++) {
                iAppInteractRecordService.removeById(list.get(i).getId());
            }
            //查看当前动态的点赞数
            Map<String, Object> res = new HashMap<>();
            queryWrapper.clear();
            queryWrapper.eq("source_id", id);
            queryWrapper.eq("interact_type", "like");
            List<AppInteractRecord> list1 = iAppInteractRecordService.list(queryWrapper);
            res.put("like", list1.size() + "");
            //查看当前动态的收藏数
            queryWrapper.clear();
            queryWrapper.eq("source_id", id);
            queryWrapper.eq("interact_type", "collect");
            List<AppInteractRecord> list2 = iAppInteractRecordService.list(queryWrapper);
            res.put("collect", list2.size() + "");
            //查看当前用户是否点赞
            queryWrapper.clear();
            queryWrapper.eq("source_id", id);
            queryWrapper.eq("user_id", SecurityUtils.getLoginUser().getAppUser().getId());
            queryWrapper.eq("interact_type", "like");
            List<AppInteractRecord> list3 = iAppInteractRecordService.list(queryWrapper);
            res.put("isLike", list3.size() + "");
            //查看当前用户是否收藏
            queryWrapper.clear();
            queryWrapper.eq("source_id", id);
            queryWrapper.eq("user_id", SecurityUtils.getLoginUser().getAppUser().getId());
            queryWrapper.eq("interact_type", "collect");
            List<AppInteractRecord> list4 = iAppInteractRecordService.list(queryWrapper);
            res.put("isCollect", list4.size() + "");
            return R.success("取消收藏", res);
        }
    }

    /**
     * 点赞/取消点赞 文章 资源
     */
    @PostMapping("/like1")
    public R like1(@RequestBody Map<String, Object> params) {
        Long id = Long.parseLong(params.get("id").toString());
        Long userId = SecurityUtils.getLoginUser().getAppUser().getId();
        QueryWrapper<AppInteractRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("source_id", id);
        queryWrapper.eq("interact_type", "like");
        List<AppInteractRecord> list = iAppInteractRecordService.list(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            AppInteractRecord appInteractRecord = new AppInteractRecord();
            appInteractRecord.setId(IDUtils.getId());
            appInteractRecord.setType("source");
            appInteractRecord.setInteractType("like");
            appInteractRecord.setCreateTime(new Date());
            appInteractRecord.setUserId(userId);
            appInteractRecord.setSourceId(id);
            iAppInteractRecordService.save(appInteractRecord);
            //查看当前动态的点赞数
            Map<String, Object> res = new HashMap<>();
            queryWrapper.clear();
            queryWrapper.eq("source_id", id);
            queryWrapper.eq("interact_type", "like");
            List<AppInteractRecord> list1 = iAppInteractRecordService.list(queryWrapper);
            res.put("like", list1.size() + "");
            //查看当前动态的收藏数
            queryWrapper.clear();
            queryWrapper.eq("source_id", id);
            queryWrapper.eq("interact_type", "collect");
            List<AppInteractRecord> list2 = iAppInteractRecordService.list(queryWrapper);
            res.put("collect", list2.size() + "");
            //查看当前用户是否点赞
            queryWrapper.clear();
            queryWrapper.eq("source_id", id);
            queryWrapper.eq("user_id", SecurityUtils.getLoginUser().getAppUser().getId());
            queryWrapper.eq("interact_type", "like");
            List<AppInteractRecord> list3 = iAppInteractRecordService.list(queryWrapper);
            res.put("isLike", list3.size() + "");
            //查看当前用户是否收藏
            queryWrapper.clear();
            queryWrapper.eq("source_id", id);
            queryWrapper.eq("user_id", SecurityUtils.getLoginUser().getAppUser().getId());
            queryWrapper.eq("interact_type", "collect");
            List<AppInteractRecord> list4 = iAppInteractRecordService.list(queryWrapper);
            res.put("isCollect", list4.size() + "");
            return R.success("点赞成功", res);
        } else {
            for (int i = 0; i < list.size(); i++) {
                iAppInteractRecordService.removeById(list.get(i).getId());
            }
            //查看当前动态的点赞数
            Map<String, Object> res = new HashMap<>();
            queryWrapper.clear();
            queryWrapper.eq("source_id", id);
            queryWrapper.eq("interact_type", "like");
            List<AppInteractRecord> list1 = iAppInteractRecordService.list(queryWrapper);
            res.put("like", list1.size() + "");
            //查看当前动态的收藏数
            queryWrapper.clear();
            queryWrapper.eq("source_id", id);
            queryWrapper.eq("interact_type", "collect");
            List<AppInteractRecord> list2 = iAppInteractRecordService.list(queryWrapper);
            res.put("collect", list2.size() + "");
            //查看当前用户是否点赞
            queryWrapper.clear();
            queryWrapper.eq("source_id", id);
            queryWrapper.eq("user_id", SecurityUtils.getLoginUser().getAppUser().getId());
            queryWrapper.eq("interact_type", "like");
            List<AppInteractRecord> list3 = iAppInteractRecordService.list(queryWrapper);
            res.put("isLike", list3.size() + "");
            //查看当前用户是否收藏
            queryWrapper.clear();
            queryWrapper.eq("source_id", id);
            queryWrapper.eq("user_id", SecurityUtils.getLoginUser().getAppUser().getId());
            queryWrapper.eq("interact_type", "collect");
            List<AppInteractRecord> list4 = iAppInteractRecordService.list(queryWrapper);
            res.put("isCollect", list4.size() + "");
            return R.success("取消点赞", res);
        }
    }

    /**
     * 收藏/取消收藏 文章 资源
     */
    @PostMapping("/collect1")
    public R collect1(@RequestBody Map<String, Object> params) {
        Long id = Long.parseLong(params.get("id").toString());
        Long userId = SecurityUtils.getLoginUser().getAppUser().getId();
        QueryWrapper<AppInteractRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("source_id", id);
        queryWrapper.eq("interact_type", "collect");
        List<AppInteractRecord> list = iAppInteractRecordService.list(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            AppInteractRecord appInteractRecord = new AppInteractRecord();
            appInteractRecord.setId(IDUtils.getId());
            appInteractRecord.setType("source");
            appInteractRecord.setInteractType("collect");
            appInteractRecord.setCreateTime(new Date());
            appInteractRecord.setUserId(userId);
            appInteractRecord.setSourceId(id);
            iAppInteractRecordService.save(appInteractRecord);
            //查看当前动态的点赞数
            Map<String, Object> res = new HashMap<>();
            queryWrapper.clear();
            queryWrapper.eq("source_id", id);
            queryWrapper.eq("interact_type", "like");
            List<AppInteractRecord> list1 = iAppInteractRecordService.list(queryWrapper);
            res.put("like", list1.size() + "");
            //查看当前动态的收藏数
            queryWrapper.clear();
            queryWrapper.eq("source_id", id);
            queryWrapper.eq("interact_type", "collect");
            List<AppInteractRecord> list2 = iAppInteractRecordService.list(queryWrapper);
            res.put("collect", list2.size() + "");
            //查看当前用户是否点赞
            queryWrapper.clear();
            queryWrapper.eq("source_id", id);
            queryWrapper.eq("user_id", SecurityUtils.getLoginUser().getAppUser().getId());
            queryWrapper.eq("interact_type", "like");
            List<AppInteractRecord> list3 = iAppInteractRecordService.list(queryWrapper);
            res.put("isLike", list3.size() + "");
            //查看当前用户是否收藏
            queryWrapper.clear();
            queryWrapper.eq("source_id", id);
            queryWrapper.eq("user_id", SecurityUtils.getLoginUser().getAppUser().getId());
            queryWrapper.eq("interact_type", "collect");
            List<AppInteractRecord> list4 = iAppInteractRecordService.list(queryWrapper);
            res.put("isCollect", list4.size() + "");
            return R.success("收藏成功", res);
        } else {
            for (int i = 0; i < list.size(); i++) {
                iAppInteractRecordService.removeById(list.get(i).getId());
            }
            //查看当前动态的点赞数
            Map<String, Object> res = new HashMap<>();
            queryWrapper.clear();
            queryWrapper.eq("source_id", id);
            queryWrapper.eq("interact_type", "like");
            List<AppInteractRecord> list1 = iAppInteractRecordService.list(queryWrapper);
            res.put("like", list1.size() + "");
            //查看当前动态的收藏数
            queryWrapper.clear();
            queryWrapper.eq("source_id", id);
            queryWrapper.eq("interact_type", "collect");
            List<AppInteractRecord> list2 = iAppInteractRecordService.list(queryWrapper);
            res.put("collect", list2.size() + "");
            //查看当前用户是否点赞
            queryWrapper.clear();
            queryWrapper.eq("source_id", id);
            queryWrapper.eq("user_id", SecurityUtils.getLoginUser().getAppUser().getId());
            queryWrapper.eq("interact_type", "like");
            List<AppInteractRecord> list3 = iAppInteractRecordService.list(queryWrapper);
            res.put("isLike", list3.size() + "");
            //查看当前用户是否收藏
            queryWrapper.clear();
            queryWrapper.eq("source_id", id);
            queryWrapper.eq("user_id", SecurityUtils.getLoginUser().getAppUser().getId());
            queryWrapper.eq("interact_type", "collect");
            List<AppInteractRecord> list4 = iAppInteractRecordService.list(queryWrapper);
            res.put("isCollect", list4.size() + "");
            return R.success("取消收藏", res);
        }
    }
}
