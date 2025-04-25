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
import com.rzfk.app.domain.bo.AppRegionBo;
import com.rzfk.app.domain.vo.AppRegionVo;
import com.rzfk.app.domain.AppRegion;
import com.rzfk.app.mapper.AppRegionMapper;
import com.rzfk.app.service.IAppRegionService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 全国区划Service业务层处理
 *
 * @author streaker
 * @date 2022-08-10
 */
@Service
public class AppRegionServiceImpl extends ServiceImpl<AppRegionMapper, AppRegion> implements IAppRegionService {

    @Override
    public AppRegionVo queryById(Long id){
        return getVoById(id, AppRegionVo.class);
    }

    @Override
    public TableDataInfo<AppRegionVo> queryPageList(AppRegionBo bo) {
        PagePlus<AppRegion, AppRegionVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo), AppRegionVo.class);
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<AppRegionVo> queryList(AppRegionBo bo) {
        return listVo(buildQueryWrapper(bo), AppRegionVo.class);
    }

    private LambdaQueryWrapper<AppRegion> buildQueryWrapper(AppRegionBo bo) {

        LambdaQueryWrapper<AppRegion> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getDeleteFlag() != null, AppRegion::getDeleteFlag, bo.getDeleteFlag());
        lqw.eq(StrUtil.isNotBlank(bo.getAdCode()), AppRegion::getAdCode, bo.getAdCode());
        lqw.eq(StrUtil.isNotBlank(bo.getCenter()), AppRegion::getCenter, bo.getCenter());
        lqw.eq(StrUtil.isNotBlank(bo.getCityCode()), AppRegion::getCityCode, bo.getCityCode());
        lqw.eq(StrUtil.isNotBlank(bo.getLevel()), AppRegion::getLevel, bo.getLevel());
        lqw.like(StrUtil.isNotBlank(bo.getName()), AppRegion::getName, bo.getName());
        lqw.eq(bo.getOrderNum() != null, AppRegion::getOrderNum, bo.getOrderNum());
        lqw.eq(bo.getParentId() != null, AppRegion::getParentId, bo.getParentId());
        lqw.eq(StrUtil.isNotBlank(bo.getPath()), AppRegion::getPath, bo.getPath());
        return lqw;
    }

    @Override
    public Boolean insertByBo(AppRegionBo bo) {
        AppRegion add = BeanUtil.toBean(bo, AppRegion.class);
        validEntityBeforeSave(add);
        boolean flag = save(add);
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    @Override
    public Boolean updateByBo(AppRegionBo bo) {
        AppRegion update = BeanUtil.toBean(bo, AppRegion.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(AppRegion entity){
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
