package com.rzfk.wallpaper.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rzfk.wallpaper.domain.AppWallpapersVersion;
import com.rzfk.wallpaper.domain.bo.AppWallpapersVersionBo;
import com.rzfk.wallpaper.domain.vo.AppWallpapersVersionVo;
import com.rzfk.wallpaper.mapper.AppWallpapersVersionMapper;
import com.rzfk.wallpaper.service.IAppWallpapersVersionService;
import com.rzfk.common.core.page.PagePlus;
import com.rzfk.common.core.page.TableDataInfo;
import com.rzfk.common.utils.PageUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * app版本管理Service业务层处理
 *
 * @author streaker
 * @date 2022-08-20
 */
@Service
public class AppWallpapersVersionServiceImpl extends ServiceImpl<AppWallpapersVersionMapper, AppWallpapersVersion> implements IAppWallpapersVersionService {

    @Override
    public AppWallpapersVersionVo queryById(Long id){
        return getVoById(id, AppWallpapersVersionVo.class);
    }

    @Override
    public TableDataInfo<AppWallpapersVersionVo> queryPageList(AppWallpapersVersionBo bo) {
        PagePlus<AppWallpapersVersion, AppWallpapersVersionVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo), AppWallpapersVersionVo.class);
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<AppWallpapersVersionVo> queryList(AppWallpapersVersionBo bo) {
        return listVo(buildQueryWrapper(bo), AppWallpapersVersionVo.class);
    }

    private LambdaQueryWrapper<AppWallpapersVersion> buildQueryWrapper(AppWallpapersVersionBo bo) {

        LambdaQueryWrapper<AppWallpapersVersion> lqw = Wrappers.lambdaQuery();
        lqw.eq(StrUtil.isNotBlank(bo.getType()), AppWallpapersVersion::getType, bo.getType());
        lqw.eq(StrUtil.isNotBlank(bo.getVersion()), AppWallpapersVersion::getVersion, bo.getVersion());
        lqw.eq(StrUtil.isNotBlank(bo.getForceUpdate()), AppWallpapersVersion::getForceUpdate, bo.getForceUpdate());
        lqw.like(StrUtil.isNotBlank(bo.getVersionName()), AppWallpapersVersion::getVersionName, bo.getVersionName());
        return lqw;
    }

    @Override
    public Boolean insertByBo(AppWallpapersVersionBo bo) {
        AppWallpapersVersion add = BeanUtil.toBean(bo, AppWallpapersVersion.class);
        validEntityBeforeSave(add);
        boolean flag = save(add);
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    @Override
    public Boolean updateByBo(AppWallpapersVersionBo bo) {
        AppWallpapersVersion update = BeanUtil.toBean(bo, AppWallpapersVersion.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(AppWallpapersVersion entity){
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
