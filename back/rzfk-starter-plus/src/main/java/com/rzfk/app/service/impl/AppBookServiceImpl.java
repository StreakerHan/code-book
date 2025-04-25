package com.rzfk.app.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rzfk.common.utils.PageUtils;
import com.rzfk.common.core.page.PagePlus;
import com.rzfk.common.core.page.TableDataInfo;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.rzfk.app.domain.bo.AppBookBo;
import com.rzfk.app.domain.vo.AppBookVo;
import com.rzfk.app.domain.AppBook;
import com.rzfk.app.mapper.AppBookMapper;
import com.rzfk.app.service.IAppBookService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 书籍信息Service业务层处理
 *
 * @author streaker
 * @date 2023-02-03
 */
@Service
public class AppBookServiceImpl extends ServiceImpl<AppBookMapper, AppBook> implements IAppBookService {

    @Override
    public AppBookVo queryById(Long id){
        return getVoById(id, AppBookVo.class);
    }

    @Override
    public TableDataInfo<AppBookVo> queryPageList(AppBookBo bo) {
        PagePlus<AppBook, AppBookVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo), AppBookVo.class);
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<AppBookVo> queryList(AppBookBo bo) {
        return listVo(buildQueryWrapper(bo), AppBookVo.class);
    }

    private QueryWrapper<AppBook> buildQueryWrapper(AppBookBo bo) {

        QueryWrapper<AppBook> lqw =new QueryWrapper<>();
        lqw.select("*,(select b.name from app_book_category b where b.id = type) as typeName ");
        lqw.eq(bo.getId() != null, "id", bo.getId());
        lqw.like(StrUtil.isNotBlank(bo.getName()), "name", bo.getName());
        lqw.like(ObjectUtil.isNotNull(bo.getType()), "type", bo.getType());
        lqw.eq(StrUtil.isNotBlank(bo.getHref()), "href", bo.getHref());
        lqw.eq(StrUtil.isNotBlank(bo.getReadHref()), "read_href", bo.getReadHref());
        lqw.eq(StrUtil.isNotBlank(bo.getTags()), "tags", bo.getTags());
        lqw.eq(StrUtil.isNotBlank(bo.getDescb()), "descb", bo.getDescb());
        return lqw;
    }

    @Override
    public Boolean insertByBo(AppBookBo bo) {
        AppBook add = BeanUtil.toBean(bo, AppBook.class);
        validEntityBeforeSave(add);
        boolean flag = save(add);
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    @Override
    public Boolean updateByBo(AppBookBo bo) {
        AppBook update = BeanUtil.toBean(bo, AppBook.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(AppBook entity){
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
