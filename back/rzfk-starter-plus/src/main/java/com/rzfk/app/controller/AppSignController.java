package com.rzfk.app.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.rzfk.app.domain.AppExpRecord;
import com.rzfk.app.domain.AppPointsRecord;
import com.rzfk.app.domain.AppSign;
import com.rzfk.app.domain.AppUser;
import com.rzfk.app.service.IAppExpRecordService;
import com.rzfk.app.service.IAppPointsRecordService;
import com.rzfk.app.service.IAppUserService;
import com.rzfk.common.constant.ExpConstants;
import com.rzfk.common.constant.PointConstants;
import com.rzfk.common.core.domain.model.LoginUser;
import com.rzfk.common.utils.IDUtils;
import com.rzfk.common.utils.SecurityUtils;
import com.rzfk.common.utils.SignUtils;
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
import com.rzfk.app.domain.vo.AppSignVo;
import com.rzfk.app.domain.bo.AppSignBo;
import com.rzfk.app.service.IAppSignService;
import com.rzfk.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiOperation;

/**
 * 用户签到记录Controller
 *
 * @author streaker
 * @date 2022-08-13
 */
@Validated
@Api(value = "用户签到记录控制器", tags = {"用户签到记录管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/app/AppSign")
public class AppSignController extends BaseController {

    private final IAppSignService iAppSignService;

    @Autowired
    private IAppUserService appUserService;

    @Autowired
    private IAppExpRecordService appExpRecordService;

    @Autowired
    private IAppPointsRecordService appPointsRecordService;

    /**
     * 查询用户签到记录列表
     */
    @ApiOperation("查询用户签到记录列表")
    @GetMapping("/list")
    public TableDataInfo<AppSignVo> list(@Validated(QueryGroup.class) AppSignBo bo) {
        return iAppSignService.queryPageList(bo);
    }

    /**
     * 导出用户签到记录列表
     */
    @ApiOperation("导出用户签到记录列表")
    @Log(title = "用户签到记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public R<AppSignVo> export(@Validated AppSignBo bo, HttpServletResponse response) {
        List<AppSignVo> list = iAppSignService.queryList(bo);
        ExcelUtil<AppSignVo> util = new ExcelUtil<AppSignVo>(AppSignVo.class);
        return util.exportExcel(list, "用户签到记录");
    }

    /**
     * 获取用户签到记录详细信息
     */
    @ApiOperation("获取用户签到记录详细信息")
    @GetMapping("/{id}")
    public R<AppSignVo> getInfo(@ApiParam("主键")
                                @NotNull(message = "主键不能为空")
                                @PathVariable("id") Long id) {
        return R.success(iAppSignService.queryById(id));
    }

    /**
     * 新增用户签到记录
     */
    @ApiOperation("新增用户签到记录")
    @Log(title = "用户签到记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody AppSignBo bo) {
        return toAjax(iAppSignService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改用户签到记录
     */
    @ApiOperation("修改用户签到记录")
    @Log(title = "用户签到记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody AppSignBo bo) {
        return toAjax(iAppSignService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除用户签到记录
     */
    @ApiOperation("删除用户签到记录")
    @Log(title = "用户签到记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@ApiParam("主键串")
                          @NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iAppSignService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }

    /**
     * 用户签到
     */
    @GetMapping("/sign")
    public R sign() throws ParseException {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        AppUser user = appUserService.getById(loginUser.getAppUser().getId());
        //查询当天是否已经签到
        QueryWrapper<AppSign> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("member_id", loginUser.getAppUser().getId());
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        queryWrapper.eq("date_format(create_time,'%Y-%m-%d')", date);
        List<AppSign> list = iAppSignService.list(queryWrapper);
        //查询连续签到天数
        List<String> listDate = list.stream().map(AppSign::getDay).distinct().collect(Collectors.toList());
        int num = SignUtils.getContinuousSignInDay(SignUtils.change(listDate));
        if (CollectionUtils.isEmpty(list)) {
            AppSign appSign = new AppSign();
            appSign.setId(IDUtils.getId());
            appSign.setCreateTime(new Date());
            appSign.setMemberId(loginUser.getAppUser().getId());
            appSign.setDay(date);
            appSign.setSignDay(String.valueOf(num + 1));
            iAppSignService.save(appSign);
            //添加经验记录
            AppExpRecord appExpRecord = new AppExpRecord();
            appExpRecord.setId(IDUtils.getId());
            appExpRecord.setUserId(user.getId());
            appExpRecord.setCreateTime(new Date());
            appExpRecord.setPreExp(user.getExp());
            appExpRecord.setChangeExp(ExpConstants.SIGN_EXP);
            //计算经验
            BigDecimal old = new BigDecimal(user.getExp());
            BigDecimal change = new BigDecimal(ExpConstants.SIGN_EXP);
            BigDecimal res = old.add(change);
            appExpRecord.setAfterExp(res.toString());
            appExpRecord.setType(ExpConstants.SIGN_EXP_TYPE);
            appExpRecordService.save(appExpRecord);
            //添加积分记录
            AppPointsRecord appPointsRecord = new AppPointsRecord();
            appPointsRecord.setId(IDUtils.getId());
            appPointsRecord.setCreateTime(new Date());
            appPointsRecord.setMemberId(user.getId());
            appPointsRecord.setBeforePoint(user.getPoint());
            appPointsRecord.setVariablePoint(PointConstants.SIGN_POINT);
            appPointsRecord.setPointType(PointConstants.SIGN_TYPE);
            BigDecimal old1 = new BigDecimal(user.getPoint());
            BigDecimal change1 = new BigDecimal(PointConstants.SIGN_POINT);
            BigDecimal res1 = old1.add(change1);
            appPointsRecord.setPoint(res1.toString());
            appPointsRecord.setContent("签到送积分");
            appPointsRecord.setChangeType("add");
            appPointsRecordService.save(appPointsRecord);
            //更新用户信息
            user.setPoint(res1.toString());
            user.setExp(res.toString());
            user.setIsSign("1");
            appUserService.updateById(user);
            return R.success("签到成功！");
        } else {
            return R.success("您今天已签到！");
        }
    }

    /**
     * 获取当月签到列表
     */
    @GetMapping("/monthSignList")
    public R monthSignList() {
        String month = new SimpleDateFormat("yyyy-MM").format(new Date());
        System.out.println(month);
        QueryWrapper<AppSign> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("date_format(create_time,'%Y-%m')", month);
        queryWrapper.eq("member_id", SecurityUtils.getLoginUser().getAppUser().getId());
        List<AppSign> list = iAppSignService.list(queryWrapper);
        return R.success(list);
    }
}
