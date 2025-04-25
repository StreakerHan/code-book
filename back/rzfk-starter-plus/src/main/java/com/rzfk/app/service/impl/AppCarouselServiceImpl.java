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
import com.rzfk.app.domain.bo.AppCarouselBo;
import com.rzfk.app.domain.vo.AppCarouselVo;
import com.rzfk.app.domain.AppCarousel;
import com.rzfk.app.mapper.AppCarouselMapper;
import com.rzfk.app.service.IAppCarouselService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 轮播内容设置Service业务层处理
 *
 * @author streaker
 * @date 2022-08-14
 */
@Service
public class AppCarouselServiceImpl extends ServiceImpl<AppCarouselMapper, AppCarousel> implements IAppCarouselService {

    @Override
    public AppCarouselVo queryById(Long id){
        return getVoById(id, AppCarouselVo.class);
    }

    @Override
    public TableDataInfo<AppCarouselVo> queryPageList(AppCarouselBo bo) {
        PagePlus<AppCarousel, AppCarouselVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo), AppCarouselVo.class);
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<AppCarouselVo> queryList(AppCarouselBo bo) {
        return listVo(buildQueryWrapper(bo), AppCarouselVo.class);
    }

    private LambdaQueryWrapper<AppCarousel> buildQueryWrapper(AppCarouselBo bo) {

        LambdaQueryWrapper<AppCarousel> lqw = Wrappers.lambdaQuery();
        lqw.eq(StrUtil.isNotBlank(bo.getImg()), AppCarousel::getImg, bo.getImg());
        lqw.eq(StrUtil.isNotBlank(bo.getTitle()), AppCarousel::getTitle, bo.getTitle());
        lqw.eq(StrUtil.isNotBlank(bo.getType()), AppCarousel::getType, bo.getType());
        lqw.eq(StrUtil.isNotBlank(bo.getUrl()), AppCarousel::getUrl, bo.getUrl());
        lqw.eq(StrUtil.isNotBlank(bo.getStatus()), AppCarousel::getStatus, bo.getStatus());
        lqw.eq(bo.getCreateTime() != null, AppCarousel::getCreateTime, bo.getCreateTime());
        return lqw;
    }

    @Override
    public Boolean insertByBo(AppCarouselBo bo) {
        AppCarousel add = BeanUtil.toBean(bo, AppCarousel.class);
        validEntityBeforeSave(add);
        boolean flag = save(add);
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    @Override
    public Boolean updateByBo(AppCarouselBo bo) {
        AppCarousel update = BeanUtil.toBean(bo, AppCarousel.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(AppCarousel entity){
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
