package com.rzfk.app.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.rzfk.common.utils.PageUtils;
import com.rzfk.common.core.page.PagePlus;
import com.rzfk.common.core.page.TableDataInfo;
import com.rzfk.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.rzfk.app.domain.bo.AppFeedbackBo;
import com.rzfk.app.domain.vo.AppFeedbackVo;
import com.rzfk.app.domain.AppFeedback;
import com.rzfk.app.mapper.AppFeedbackMapper;
import com.rzfk.app.service.IAppFeedbackService;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 意见反馈Service业务层处理
 *
 * @author streaker
 * @date 2022-08-18
 */
@Service
public class AppFeedbackServiceImpl extends ServiceImpl<AppFeedbackMapper, AppFeedback> implements IAppFeedbackService {

    @Override
    public AppFeedbackVo queryById(Long id){
        return getVoById(id, AppFeedbackVo.class);
    }

    @Override
    public TableDataInfo<AppFeedbackVo> queryPageList(AppFeedbackBo bo) {
        PagePlus<AppFeedback, AppFeedbackVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo), AppFeedbackVo.class);
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<AppFeedbackVo> queryList(AppFeedbackBo bo) {
        return listVo(buildQueryWrapper(bo), AppFeedbackVo.class);
    }

    private LambdaQueryWrapper<AppFeedback> buildQueryWrapper(AppFeedbackBo bo) {

        LambdaQueryWrapper<AppFeedback> lqw = Wrappers.lambdaQuery();
        lqw.eq(StrUtil.isNotBlank(bo.getContext()), AppFeedback::getContext, bo.getContext());
        lqw.eq(StrUtil.isNotBlank(bo.getImages()), AppFeedback::getImages, bo.getImages());
        lqw.eq(StrUtil.isNotBlank(bo.getMobile()), AppFeedback::getMobile, bo.getMobile());
        lqw.eq(StrUtil.isNotBlank(bo.getType()), AppFeedback::getType, bo.getType());
//        lqw.eq(StrUtil.isNotBlank(bo.getUserId().toString()), AppFeedback::getUserId, bo.getUserId());
        return lqw;
    }

    @Override
    public Boolean insertByBo(AppFeedbackBo bo) {
        bo.setUserId(SecurityUtils.getLoginUser().getAppUser().getId());
        bo.setCreateTime(new Date());
        AppFeedback add = BeanUtil.toBean(bo, AppFeedback.class);
        validEntityBeforeSave(add);
        boolean flag = save(add);
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    @Override
    public Boolean updateByBo(AppFeedbackBo bo) {
        AppFeedback update = BeanUtil.toBean(bo, AppFeedback.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(AppFeedback entity){
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
