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
import com.rzfk.app.domain.bo.AppSignBo;
import com.rzfk.app.domain.vo.AppSignVo;
import com.rzfk.app.domain.AppSign;
import com.rzfk.app.mapper.AppSignMapper;
import com.rzfk.app.service.IAppSignService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 用户签到记录Service业务层处理
 *
 * @author streaker
 * @date 2022-08-13
 */
@Service
public class AppSignServiceImpl extends ServiceImpl<AppSignMapper, AppSign> implements IAppSignService {

    @Override
    public AppSignVo queryById(Long id){
        return getVoById(id, AppSignVo.class);
    }

    @Override
    public TableDataInfo<AppSignVo> queryPageList(AppSignBo bo) {
        PagePlus<AppSign, AppSignVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo), AppSignVo.class);
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<AppSignVo> queryList(AppSignBo bo) {
        return listVo(buildQueryWrapper(bo), AppSignVo.class);
    }

    private LambdaQueryWrapper<AppSign> buildQueryWrapper(AppSignBo bo) {

        LambdaQueryWrapper<AppSign> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getMemberId() != null, AppSign::getMemberId, bo.getMemberId());
        lqw.like(StrUtil.isNotBlank(bo.getMemberName()), AppSign::getMemberName, bo.getMemberName());
        lqw.eq(bo.getSignDay() != null, AppSign::getSignDay, bo.getSignDay());
        lqw.eq(bo.getDay() != null, AppSign::getDay, bo.getDay());
        return lqw;
    }

    @Override
    public Boolean insertByBo(AppSignBo bo) {
        AppSign add = BeanUtil.toBean(bo, AppSign.class);
        validEntityBeforeSave(add);
        boolean flag = save(add);
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    @Override
    public Boolean updateByBo(AppSignBo bo) {
        AppSign update = BeanUtil.toBean(bo, AppSign.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(AppSign entity){
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
