package com.rzfk.app.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.rzfk.common.utils.PageUtils;
import com.rzfk.common.core.page.PagePlus;
import com.rzfk.common.core.page.TableDataInfo;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.rzfk.app.domain.bo.AppVersionBo;
import com.rzfk.app.domain.vo.AppVersionVo;
import com.rzfk.app.domain.AppVersion;
import com.rzfk.app.mapper.AppVersionMapper;
import com.rzfk.app.service.IAppVersionService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * app版本管理Service业务层处理
 *
 * @author streaker
 * @date 2022-08-20
 */
@Service
public class AppVersionServiceImpl extends ServiceImpl<AppVersionMapper, AppVersion> implements IAppVersionService {

    @Override
    public AppVersionVo queryById(Long id){
        return getVoById(id, AppVersionVo.class);
    }

    @Override
    public TableDataInfo<AppVersionVo> queryPageList(AppVersionBo bo) {
        PagePlus<AppVersion, AppVersionVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo), AppVersionVo.class);
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<AppVersionVo> queryList(AppVersionBo bo) {
        return listVo(buildQueryWrapper(bo), AppVersionVo.class);
    }

    private LambdaQueryWrapper<AppVersion> buildQueryWrapper(AppVersionBo bo) {

        LambdaQueryWrapper<AppVersion> lqw = Wrappers.lambdaQuery();
        lqw.eq(StrUtil.isNotBlank(bo.getType()), AppVersion::getType, bo.getType());
        lqw.eq(StrUtil.isNotBlank(bo.getVersion()), AppVersion::getVersion, bo.getVersion());
        lqw.eq(StrUtil.isNotBlank(bo.getForceUpdate()), AppVersion::getForceUpdate, bo.getForceUpdate());
        lqw.like(StrUtil.isNotBlank(bo.getVersionName()), AppVersion::getVersionName, bo.getVersionName());
        return lqw;
    }

    @Override
    public Boolean insertByBo(AppVersionBo bo) {
        AppVersion add = BeanUtil.toBean(bo, AppVersion.class);
        validEntityBeforeSave(add);
        boolean flag = save(add);
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    @Override
    public Boolean updateByBo(AppVersionBo bo) {
        AppVersion update = BeanUtil.toBean(bo, AppVersion.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(AppVersion entity){
        //TODO 做一些数据校验,如唯一约束
    }

    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return removeByIds(ids);
    }
}
