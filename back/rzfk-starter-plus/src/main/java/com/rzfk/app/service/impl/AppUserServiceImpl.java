package com.rzfk.app.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.rzfk.app.domain.AppExpRecord;
import com.rzfk.app.domain.AppLoginRecord;
import com.rzfk.app.domain.AppSmsRecord;
import com.rzfk.app.domain.bo.AppUserUpdateBo;
import com.rzfk.app.domain.vo.LoginVO;
import com.rzfk.app.service.IAppExpRecordService;
import com.rzfk.app.service.IAppLoginRecordService;
import com.rzfk.app.service.IAppSmsRecordService;
import com.rzfk.common.constant.ExpConstants;
import com.rzfk.common.core.domain.R;
import com.rzfk.common.core.domain.model.LoginUser;
import com.rzfk.common.core.redis.RedisCache;
import com.rzfk.common.utils.*;
import com.rzfk.common.core.page.PagePlus;
import com.rzfk.common.core.page.TableDataInfo;
import com.rzfk.common.utils.ip.IpUtils;
import com.rzfk.framework.web.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.rzfk.app.domain.bo.AppUserBo;
import com.rzfk.app.domain.vo.AppUserVo;
import com.rzfk.app.domain.AppUser;
import com.rzfk.app.mapper.AppUserMapper;
import com.rzfk.app.service.IAppUserService;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * app用户Service业务层处理
 *
 * @author streaker
 * @date 2022-08-08
 */
@Service
public class AppUserServiceImpl extends ServiceImpl<AppUserMapper, AppUser> implements IAppUserService {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private IAppLoginRecordService appLoginRecordService;

    @Autowired
    private IAppExpRecordService appExpRecordService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private IAppSmsRecordService appSmsRecordService;

    @Override
    public AppUserVo queryById(Long id) {
        return getVoById(id, AppUserVo.class);
    }

    @Override
    public TableDataInfo<AppUserVo> queryPageList(AppUserBo bo) {
        PagePlus<AppUser, AppUserVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo), AppUserVo.class);
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<AppUserVo> queryList(AppUserBo bo) {
        return listVo(buildQueryWrapper(bo), AppUserVo.class);
    }

    private LambdaQueryWrapper<AppUser> buildQueryWrapper(AppUserBo bo) {

        LambdaQueryWrapper<AppUser> lqw = Wrappers.lambdaQuery();
        lqw.like(StrUtil.isNotBlank(bo.getNickname()), AppUser::getNickname, bo.getNickname());
        lqw.eq(StrUtil.isNotBlank(bo.getPassword()), AppUser::getPassword, bo.getPassword());
        lqw.like(StrUtil.isNotBlank(bo.getName()), AppUser::getName, bo.getName());
        lqw.eq(StrUtil.isNotBlank(bo.getSocialUid()), AppUser::getSocialUid, bo.getSocialUid());
        lqw.eq(StrUtil.isNotBlank(bo.getSocialToken()), AppUser::getSocialToken, bo.getSocialToken());
        lqw.eq(StrUtil.isNotBlank(bo.getMobile()), AppUser::getMobile, bo.getMobile());
        lqw.eq(StrUtil.isNotBlank(bo.getEmail()), AppUser::getEmail, bo.getEmail());
        lqw.eq(StrUtil.isNotBlank(bo.getClientId()), AppUser::getClientId, bo.getClientId());
        lqw.eq(StrUtil.isNotBlank(bo.getPushToken()), AppUser::getPushToken, bo.getPushToken());
        lqw.eq(StrUtil.isNotBlank(bo.getSex()), AppUser::getSex, bo.getSex());
        lqw.eq(StrUtil.isNotBlank(bo.getSource()), AppUser::getSource, bo.getSource());
        lqw.eq(StrUtil.isNotBlank(bo.getSocialSource()), AppUser::getSocialSource, bo.getSocialSource());
        lqw.eq(StrUtil.isNotBlank(bo.getAvatar()), AppUser::getAvatar, bo.getAvatar());
        lqw.eq(StrUtil.isNotBlank(bo.getProvince()), AppUser::getProvince, bo.getProvince());
        lqw.eq(StrUtil.isNotBlank(bo.getCity()), AppUser::getCity, bo.getCity());
        lqw.eq(StrUtil.isNotBlank(bo.getArea()), AppUser::getArea, bo.getArea());
        lqw.eq(StrUtil.isNotBlank(bo.getAddress()), AppUser::getAddress, bo.getAddress());
        lqw.eq(bo.getLastLoginTime() != null, AppUser::getLastLoginTime, bo.getLastLoginTime());
        lqw.eq(StrUtil.isNotBlank(bo.getBirth()), AppUser::getBirth, bo.getBirth());
        lqw.eq(StrUtil.isNotBlank(bo.getInviteCode()), AppUser::getInviteCode, bo.getInviteCode());
        lqw.eq(StrUtil.isNotBlank(bo.getIsSign()), AppUser::getIsSign, bo.getIsSign());
        lqw.eq(StrUtil.isNotBlank(bo.getStatus()), AppUser::getStatus, bo.getStatus());
        lqw.eq(StrUtil.isNotBlank(bo.getExp()), AppUser::getExp, bo.getExp());
        lqw.eq(StrUtil.isNotBlank(bo.getIdcard()), AppUser::getIdcard, bo.getIdcard());
        lqw.eq(StrUtil.isNotBlank(bo.getSign()), AppUser::getSign, bo.getSign());
        return lqw;
    }

    @Override
    public Boolean insertByBo(AppUserBo bo) {
        AppUser add = BeanUtil.toBean(bo, AppUser.class);
        validEntityBeforeSave(add);
        boolean flag = save(add);
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    @Override
    public Boolean updateByBo(AppUserUpdateBo bo) {
        AppUser update = BeanUtil.toBean(bo, AppUser.class);
        if (StringUtils.isNotEmpty(bo.getPath())) {
            String path = bo.getPath();
            String[] a = path.split("-");
            update.setProvince(a[0]);
            update.setCity(a[1]);
            update.setArea(a[2]);
        }
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(AppUser entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return removeByIds(ids);
    }

    @Override
    public R loginByPwd(LoginVO login) {
        R result;
        String username = login.getUsername().trim();
        String password = login.getPassword().trim();
        password = EncryptUtils.md5(password);
        LambdaQueryWrapper<AppUser> lqw = new LambdaQueryWrapper<>();
        lqw.eq(AppUser::getNickname, username);
        List<AppUser> list = baseMapper.selectList(lqw);
        if (CollectionUtils.isEmpty(list)) {
            return R.error("账号不存在，请注册！");
        }
        lqw.eq(AppUser::getPassword, password);
        //获取登录账号信息
        AppUser user = baseMapper.selectOne(lqw);
        if (user != null) {
            // 生成令牌
            LoginUser loginUser = new LoginUser(user);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getId(), user.getNickname());
            authenticationToken.setDetails(loginUser);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            String token = tokenService.createToken(loginUser);
            //更新最后登录时间
            user.setLastLoginTime(new Date());
//            baseMapper.updateById(user);
            cn.hutool.json.JSONObject val = JSONUtil.createObj().set("token", token).set("user", user);
            //增加经验值
            //判断今日是否已登录过
            QueryWrapper<AppLoginRecord> queryWrapper = new QueryWrapper<>();
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            queryWrapper.eq("date_format(login_time,'%Y-%m-%d')", date);
            queryWrapper.eq("user_id", user.getId());
            List<AppLoginRecord> loginRecords = appLoginRecordService.list(queryWrapper);
            if (StringUtils.isNotEmpty(user.getExp()) && loginRecords.size() == 0) {
                //添加经验记录
                AppExpRecord appExpRecord = new AppExpRecord();
                appExpRecord.setId(IDUtils.getId());
                appExpRecord.setUserId(user.getId());
                appExpRecord.setCreateTime(new Date());
                appExpRecord.setPreExp(user.getExp());
                appExpRecord.setChangeExp(ExpConstants.LOGIN_EXP);
                //计算经验
                BigDecimal old = new BigDecimal(user.getExp());
                BigDecimal change = new BigDecimal(ExpConstants.LOGIN_EXP);
                BigDecimal res = old.add(change);
                user.setExp(res.toString());
                baseMapper.updateById(user);
                appExpRecord.setAfterExp(user.getExp());
                appExpRecord.setType(ExpConstants.LOGIN_EXP_TYPE);
                appExpRecordService.save(appExpRecord);
            }
            //添加登录记录
            AppLoginRecord appLoginRecord = new AppLoginRecord();
            appLoginRecord.setId(IDUtils.getId());
            appLoginRecord.setLoginIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
            appLoginRecord.setLoginTime(new Date());
            appLoginRecord.setUserId(user.getId());
            appLoginRecordService.save(appLoginRecord);
            result = R.success("登录成功", val);
        } else {
            result = R.error("密码错误！");
        }
        return result;
    }

    @Override
    public R weChatLogin(Map<String, Object> params) {
        return null;
    }

    @Override
    public R register(Map<String, Object> params) {
//        Map<String, Object> para = new HashMap<>();
        LambdaQueryWrapper<AppUser> lqw = new LambdaQueryWrapper<>();
        lqw.eq(AppUser::getNickname, params.get("username").toString().trim());
        List<AppUser> list = baseMapper.selectList(lqw);
        if (list.size() != 0) {
            return R.error("此用户名称已注册！", null);
        } else {
            AppUser appUser = new AppUser();
            appUser.setId(IDUtils.getId());
            appUser.setNickname(params.get("username").toString().trim());
            appUser.setPassword(EncryptUtils.md5(params.get("password").toString().trim()));
            appUser.setAvatar("https://cdn.nlark.com/yuque/0/2023/png/21561452/1676705011188-e962ca3d-56f9-4ba9-80d2-66cf2e37ab84.png");
//            appUser.setNickname("用户" + NameUtils.generateRandomString(8));
            appUser.setCreateTime(new Date());
            appUser.setIsSign("0");
            appUser.setExp("0");
            appUser.setStatus("1");
            appUser.setPoint("0");
            appUser.setLastLoginTime(new Date());
            //生成邀请码
            Boolean isExist = true;
            String inviteCode = null;
            while (isExist == true) {
                inviteCode = NameUtils.generateRandomString(6);
                //查询验证码是否在数据库中已经存在
                List<AppUser> users = baseMapper.selectList(new LambdaQueryWrapper<>());
                List<String> codes = users.stream().map(AppUser::getInviteCode).collect(Collectors.toList());
                isExist = codes.contains(inviteCode);
            }
            appUser.setInviteCode(inviteCode);
            baseMapper.insert(appUser);
            return R.success("注册成功！", null);
        }
    }

    @Override
    public R smsSend(Map<String, Object> params) throws Exception {
        //测试生成验证码
        String code = String.valueOf((Math.random()*9+1)*100000);
        Map<String, Object> codeMap = new HashMap<>();
        codeMap.put("code", code);
        //判断是否发送成功
        Boolean res = SmsUtil.send(params.get("mobile").toString(), codeMap);
        if (res) {
            redisCache.setCacheObject(params.get("mobile").toString() + ":" + params.get("event").toString(), code, 5, TimeUnit.MINUTES); //设置缓存时间5分钟
            //添加短信发送记录
            AppSmsRecord appSmsRecord = new AppSmsRecord();
            appSmsRecord.setId(IDUtils.getId());
            appSmsRecord.setCreateTime(new Date());
            appSmsRecord.setPhone(params.get("mobile").toString());
            appSmsRecord.setContent("");
            appSmsRecord.setIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
            appSmsRecordService.save(appSmsRecord);
            return R.success();
        } else {
            //发送失败
            return R.error();
        }
    }

    @Override
    public R loginBySms(Map<String, Object> params) {
        String mobile = params.get("mobile").toString();
        String code = params.get("code").toString();
        String getCode = redisCache.getCacheObject(mobile + ":login");
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(code)) {
            return R.error();
        }
        if (Objects.equals(getCode, code)) {
            redisCache.deleteObject(mobile + ":login");
            //登录成功
            // 判断手机号是否在数据库中存在，没有就注册，有就登录
            return R.success();
        }
        return R.error();
    }
}
