package com.rzfk.app.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rzfk.common.utils.StringUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.rzfk.app.domain.bo.AppBookCategoryBo;
import com.rzfk.app.domain.vo.AppBookCategoryVo;
import com.rzfk.app.domain.AppBookCategory;
import com.rzfk.app.mapper.AppBookCategoryMapper;
import com.rzfk.app.service.IAppBookCategoryService;

import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 书籍分类Service业务层处理
 *
 * @author streaker
 * @date 2023-02-03
 */
@Service
public class AppBookCategoryServiceImpl extends ServiceImpl<AppBookCategoryMapper, AppBookCategory> implements IAppBookCategoryService {

    @Override
    public AppBookCategoryVo queryById(Long id){
        return getVoById(id, AppBookCategoryVo.class);
    }


    @Override
    public List<AppBookCategoryVo> queryList(AppBookCategoryBo bo) {
        return listVo(buildQueryWrapper(bo), AppBookCategoryVo.class);
    }

    private LambdaQueryWrapper<AppBookCategory> buildQueryWrapper(AppBookCategoryBo bo) {
        LambdaQueryWrapper<AppBookCategory> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getId() != null, AppBookCategory::getId, bo.getId());
        lqw.like(StrUtil.isNotBlank(bo.getName()), AppBookCategory::getName, bo.getName());
        lqw.eq(StrUtil.isNotBlank(bo.getImg()), AppBookCategory::getImg, bo.getImg());
        lqw.eq(bo.getParentId() != null, AppBookCategory::getParentId, bo.getParentId());
        lqw.eq(StrUtil.isNotBlank(bo.getHref()), AppBookCategory::getHref, bo.getHref());
        lqw.eq(StrUtil.isNotBlank(bo.getLevel()), AppBookCategory::getLevel, bo.getLevel());
        return lqw;
    }

    @Override
    public Boolean insertByBo(AppBookCategoryBo bo) {
        AppBookCategory add = BeanUtil.toBean(bo, AppBookCategory.class);
        validEntityBeforeSave(add);
        boolean flag = save(add);
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    @Override
    public Boolean updateByBo(AppBookCategoryBo bo) {
        AppBookCategory update = BeanUtil.toBean(bo, AppBookCategory.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(AppBookCategory entity){
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
