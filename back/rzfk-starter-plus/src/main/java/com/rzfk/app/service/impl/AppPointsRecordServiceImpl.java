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
import com.rzfk.app.domain.bo.AppPointsRecordBo;
import com.rzfk.app.domain.vo.AppPointsRecordVo;
import com.rzfk.app.domain.AppPointsRecord;
import com.rzfk.app.mapper.AppPointsRecordMapper;
import com.rzfk.app.service.IAppPointsRecordService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 积分记录Service业务层处理
 *
 * @author streaker
 * @date 2022-08-13
 */
@Service
public class AppPointsRecordServiceImpl extends ServiceImpl<AppPointsRecordMapper, AppPointsRecord> implements IAppPointsRecordService {

    @Override
    public AppPointsRecordVo queryById(Long id){
        return getVoById(id, AppPointsRecordVo.class);
    }

    @Override
    public TableDataInfo<AppPointsRecordVo> queryPageList(AppPointsRecordBo bo) {
        PagePlus<AppPointsRecord, AppPointsRecordVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo), AppPointsRecordVo.class);
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<AppPointsRecordVo> queryList(AppPointsRecordBo bo) {
        return listVo(buildQueryWrapper(bo), AppPointsRecordVo.class);
    }

    private LambdaQueryWrapper<AppPointsRecord> buildQueryWrapper(AppPointsRecordBo bo) {

        LambdaQueryWrapper<AppPointsRecord> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getBeforePoint() != null, AppPointsRecord::getBeforePoint, bo.getBeforePoint());
        lqw.eq(StrUtil.isNotBlank(bo.getContent()), AppPointsRecord::getContent, bo.getContent());
        lqw.eq(bo.getMemberId() != null, AppPointsRecord::getMemberId, bo.getMemberId());
        lqw.like(StrUtil.isNotBlank(bo.getMemberName()), AppPointsRecord::getMemberName, bo.getMemberName());
        lqw.eq(bo.getPoint() != null, AppPointsRecord::getPoint, bo.getPoint());
        lqw.eq(StrUtil.isNotBlank(bo.getPointType()), AppPointsRecord::getPointType, bo.getPointType());
        lqw.eq(bo.getVariablePoint() != null, AppPointsRecord::getVariablePoint, bo.getVariablePoint());
        return lqw;
    }

    @Override
    public Boolean insertByBo(AppPointsRecordBo bo) {
        AppPointsRecord add = BeanUtil.toBean(bo, AppPointsRecord.class);
        validEntityBeforeSave(add);
        boolean flag = save(add);
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    @Override
    public Boolean updateByBo(AppPointsRecordBo bo) {
        AppPointsRecord update = BeanUtil.toBean(bo, AppPointsRecord.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(AppPointsRecord entity){
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
