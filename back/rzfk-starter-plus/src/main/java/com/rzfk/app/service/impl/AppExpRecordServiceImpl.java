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
import com.rzfk.app.domain.bo.AppExpRecordBo;
import com.rzfk.app.domain.vo.AppExpRecordVo;
import com.rzfk.app.domain.AppExpRecord;
import com.rzfk.app.mapper.AppExpRecordMapper;
import com.rzfk.app.service.IAppExpRecordService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 用户经验记录Service业务层处理
 *
 * @author streaker
 * @date 2022-08-13
 */
@Service
public class AppExpRecordServiceImpl extends ServiceImpl<AppExpRecordMapper, AppExpRecord> implements IAppExpRecordService {

    @Override
    public AppExpRecordVo queryById(Long id){
        return getVoById(id, AppExpRecordVo.class);
    }

    @Override
    public TableDataInfo<AppExpRecordVo> queryPageList(AppExpRecordBo bo) {
        PagePlus<AppExpRecord, AppExpRecordVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo), AppExpRecordVo.class);
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<AppExpRecordVo> queryList(AppExpRecordBo bo) {
        return listVo(buildQueryWrapper(bo), AppExpRecordVo.class);
    }

    private LambdaQueryWrapper<AppExpRecord> buildQueryWrapper(AppExpRecordBo bo) {

        LambdaQueryWrapper<AppExpRecord> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getUserId() != null, AppExpRecord::getUserId, bo.getUserId());
        lqw.eq(StrUtil.isNotBlank(bo.getPreExp()), AppExpRecord::getPreExp, bo.getPreExp());
        lqw.eq(StrUtil.isNotBlank(bo.getChangeExp()), AppExpRecord::getChangeExp, bo.getChangeExp());
        lqw.eq(StrUtil.isNotBlank(bo.getAfterExp()), AppExpRecord::getAfterExp, bo.getAfterExp());
        lqw.eq(StrUtil.isNotBlank(bo.getType()), AppExpRecord::getType, bo.getType());
        return lqw;
    }

    @Override
    public Boolean insertByBo(AppExpRecordBo bo) {
        AppExpRecord add = BeanUtil.toBean(bo, AppExpRecord.class);
        validEntityBeforeSave(add);
        boolean flag = save(add);
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    @Override
    public Boolean updateByBo(AppExpRecordBo bo) {
        AppExpRecord update = BeanUtil.toBean(bo, AppExpRecord.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(AppExpRecord entity){
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
