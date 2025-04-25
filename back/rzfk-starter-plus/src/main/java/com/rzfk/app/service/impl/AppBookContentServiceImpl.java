package com.rzfk.app.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rzfk.common.utils.PageUtils;
import com.rzfk.common.core.page.PagePlus;
import com.rzfk.common.core.page.TableDataInfo;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.rzfk.app.domain.bo.AppBookContentBo;
import com.rzfk.app.domain.vo.AppBookContentVo;
import com.rzfk.app.domain.AppBookContent;
import com.rzfk.app.mapper.AppBookContentMapper;
import com.rzfk.app.service.IAppBookContentService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 书籍内容Service业务层处理
 *
 * @author streaker
 * @date 2023-02-03
 */
@Service
public class AppBookContentServiceImpl extends ServiceImpl<AppBookContentMapper, AppBookContent> implements IAppBookContentService {

    @Override
    public AppBookContentVo queryById(Long id) {
        return getVoById(id, AppBookContentVo.class);
    }

    @Override
    public TableDataInfo<AppBookContentVo> queryPageList(AppBookContentBo bo) {
        PagePlus<AppBookContent, AppBookContentVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo), AppBookContentVo.class);
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<AppBookContentVo> queryList(AppBookContentBo bo) {
        return listVo(buildQueryWrapper(bo), AppBookContentVo.class);
    }

    private QueryWrapper<AppBookContent> buildQueryWrapper(AppBookContentBo bo) {

        QueryWrapper<AppBookContent> lqw = new QueryWrapper<>();
        lqw.select("id,book_id,catalogue_name,seq,href,parent_id,level");
        lqw.eq(bo.getId() != null, "id", bo.getId());
        lqw.eq(bo.getBookId() != null, "book_id", bo.getBookId());
        lqw.like(StrUtil.isNotBlank(bo.getCatalogueName()), "catalogue_name", bo.getCatalogueName());
        lqw.eq(StrUtil.isNotBlank(bo.getSeq()), "seq", bo.getSeq());
        lqw.eq(StrUtil.isNotBlank(bo.getHref()), "href", bo.getHref());
        lqw.eq(bo.getParentId() != null, "parent_id", bo.getParentId());
        lqw.eq(StrUtil.isNotBlank(bo.getLevel()), "level", bo.getLevel());
        return lqw;
    }

    @Override
    public Boolean insertByBo(AppBookContentBo bo) {
        AppBookContent add = BeanUtil.toBean(bo, AppBookContent.class);
        validEntityBeforeSave(add);
        boolean flag = save(add);
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    @Override
    public Boolean updateByBo(AppBookContentBo bo) {
        AppBookContent update = BeanUtil.toBean(bo, AppBookContent.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(AppBookContent entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return removeByIds(ids);
    }
}
