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
import com.rzfk.app.domain.bo.AppLoginRecordBo;
import com.rzfk.app.domain.vo.AppLoginRecordVo;
import com.rzfk.app.domain.AppLoginRecord;
import com.rzfk.app.mapper.AppLoginRecordMapper;
import com.rzfk.app.service.IAppLoginRecordService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * app用户登录记录Service业务层处理
 *
 * @author streaker
 * @date 2022-08-10
 */
@Service
public class AppLoginRecordServiceImpl extends ServiceImpl<AppLoginRecordMapper, AppLoginRecord> implements IAppLoginRecordService {

    @Override
    public AppLoginRecordVo queryById(Long id){
        return getVoById(id, AppLoginRecordVo.class);
    }

    @Override
    public TableDataInfo<AppLoginRecordVo> queryPageList(AppLoginRecordBo bo) {
        PagePlus<AppLoginRecord, AppLoginRecordVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo), AppLoginRecordVo.class);
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<AppLoginRecordVo> queryList(AppLoginRecordBo bo) {
        return listVo(buildQueryWrapper(bo), AppLoginRecordVo.class);
    }

    private LambdaQueryWrapper<AppLoginRecord> buildQueryWrapper(AppLoginRecordBo bo) {

        LambdaQueryWrapper<AppLoginRecord> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getUserId() != null, AppLoginRecord::getUserId, bo.getUserId());
        lqw.eq(bo.getLoginTime() != null, AppLoginRecord::getLoginTime, bo.getLoginTime());
        lqw.eq(StrUtil.isNotBlank(bo.getLoginIp()), AppLoginRecord::getLoginIp, bo.getLoginIp());
        return lqw;
    }

    @Override
    public Boolean insertByBo(AppLoginRecordBo bo) {
        AppLoginRecord add = BeanUtil.toBean(bo, AppLoginRecord.class);
        validEntityBeforeSave(add);
        boolean flag = save(add);
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    @Override
    public Boolean updateByBo(AppLoginRecordBo bo) {
        AppLoginRecord update = BeanUtil.toBean(bo, AppLoginRecord.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(AppLoginRecord entity){
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
