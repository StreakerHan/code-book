package com.rzfk.wallpaper.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.rzfk.common.utils.PageUtils;
import com.rzfk.common.core.page.PagePlus;
import com.rzfk.common.core.page.TableDataInfo;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.rzfk.wallpaper.domain.bo.AppWallpapersBo;
import com.rzfk.wallpaper.domain.vo.AppWallpapersVo;
import com.rzfk.wallpaper.domain.AppWallpapers;
import com.rzfk.wallpaper.mapper.AppWallpapersMapper;
import com.rzfk.wallpaper.service.IAppWallpapersService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 壁纸Service业务层处理
 *
 * @author streaker
 * @date 2022-11-03
 */
@Service
public class AppWallpapersServiceImpl extends ServiceImpl<AppWallpapersMapper, AppWallpapers> implements IAppWallpapersService {

    @Override
    public AppWallpapersVo queryById(Long id){
        return getVoById(id, AppWallpapersVo.class);
    }

    @Override
    public TableDataInfo<AppWallpapersVo> queryPageList(AppWallpapersBo bo) {
        PagePlus<AppWallpapers, AppWallpapersVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo), AppWallpapersVo.class);
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<AppWallpapersVo> queryList(AppWallpapersBo bo) {
        return listVo(buildQueryWrapper(bo), AppWallpapersVo.class);
    }

    private LambdaQueryWrapper<AppWallpapers> buildQueryWrapper(AppWallpapersBo bo) {

        LambdaQueryWrapper<AppWallpapers> lqw = Wrappers.lambdaQuery();
        lqw.eq(StrUtil.isNotBlank(bo.getUrl()), AppWallpapers::getUrl, bo.getUrl());
        lqw.eq(StrUtil.isNotBlank(bo.getWidth()), AppWallpapers::getWidth, bo.getWidth());
        lqw.eq(StrUtil.isNotBlank(bo.getHeight()), AppWallpapers::getHeight, bo.getHeight());
        lqw.like(StrUtil.isNotBlank(bo.getTitle()), AppWallpapers::getTitle, bo.getTitle());
        lqw.eq(StrUtil.isNotBlank(bo.getDownloads()), AppWallpapers::getDownloads, bo.getDownloads());
        lqw.eq(StrUtil.isNotBlank(bo.getType()), AppWallpapers::getType, bo.getType());
        lqw.eq(StrUtil.isNotBlank(bo.getTopic()), AppWallpapers::getTopic, bo.getTopic());
        lqw.orderByDesc(AppWallpapers::getId);
        return lqw;
    }

    @Override
    public Boolean insertByBo(AppWallpapersBo bo) {
        AppWallpapers add = BeanUtil.toBean(bo, AppWallpapers.class);
        validEntityBeforeSave(add);
        boolean flag = save(add);
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    @Override
    public Boolean updateByBo(AppWallpapersBo bo) {
        AppWallpapers update = BeanUtil.toBean(bo, AppWallpapers.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(AppWallpapers entity){
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
