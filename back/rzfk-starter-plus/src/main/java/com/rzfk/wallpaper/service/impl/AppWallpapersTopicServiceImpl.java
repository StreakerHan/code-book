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
import com.rzfk.wallpaper.domain.bo.AppWallpapersTopicBo;
import com.rzfk.wallpaper.domain.vo.AppWallpapersTopicVo;
import com.rzfk.wallpaper.domain.AppWallpapersTopic;
import com.rzfk.wallpaper.mapper.AppWallpapersTopicMapper;
import com.rzfk.wallpaper.service.IAppWallpapersTopicService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 壁纸主题Service业务层处理
 *
 * @author streaker
 * @date 2022-11-03
 */
@Service
public class AppWallpapersTopicServiceImpl extends ServiceImpl<AppWallpapersTopicMapper, AppWallpapersTopic> implements IAppWallpapersTopicService {

    @Override
    public AppWallpapersTopicVo queryById(Long id){
        return getVoById(id, AppWallpapersTopicVo.class);
    }

    @Override
    public TableDataInfo<AppWallpapersTopicVo> queryPageList(AppWallpapersTopicBo bo) {
        PagePlus<AppWallpapersTopic, AppWallpapersTopicVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo), AppWallpapersTopicVo.class);
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<AppWallpapersTopicVo> queryList(AppWallpapersTopicBo bo) {
        return listVo(buildQueryWrapper(bo), AppWallpapersTopicVo.class);
    }

    private LambdaQueryWrapper<AppWallpapersTopic> buildQueryWrapper(AppWallpapersTopicBo bo) {

        LambdaQueryWrapper<AppWallpapersTopic> lqw = Wrappers.lambdaQuery();
        lqw.eq(StrUtil.isNotBlank(bo.getUrl()), AppWallpapersTopic::getUrl, bo.getUrl());
        lqw.eq(StrUtil.isNotBlank(bo.getWidth()), AppWallpapersTopic::getWidth, bo.getWidth());
        lqw.eq(StrUtil.isNotBlank(bo.getHeight()), AppWallpapersTopic::getHeight, bo.getHeight());
        lqw.eq(StrUtil.isNotBlank(bo.getTitle()), AppWallpapersTopic::getTitle, bo.getTitle());
        lqw.eq(StrUtil.isNotBlank(bo.getDownloads()), AppWallpapersTopic::getDownloads, bo.getDownloads());
        lqw.eq(StrUtil.isNotBlank(bo.getType()), AppWallpapersTopic::getType, bo.getType());
        return lqw;
    }

    @Override
    public Boolean insertByBo(AppWallpapersTopicBo bo) {
        AppWallpapersTopic add = BeanUtil.toBean(bo, AppWallpapersTopic.class);
        validEntityBeforeSave(add);
        boolean flag = save(add);
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    @Override
    public Boolean updateByBo(AppWallpapersTopicBo bo) {
        AppWallpapersTopic update = BeanUtil.toBean(bo, AppWallpapersTopic.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(AppWallpapersTopic entity){
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
