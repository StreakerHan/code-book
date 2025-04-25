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
import com.rzfk.app.domain.bo.AppSmsRecordBo;
import com.rzfk.app.domain.vo.AppSmsRecordVo;
import com.rzfk.app.domain.AppSmsRecord;
import com.rzfk.app.mapper.AppSmsRecordMapper;
import com.rzfk.app.service.IAppSmsRecordService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 短信发送记录Service业务层处理
 *
 * @author streaker
 * @date 2022-08-20
 */
@Service
public class AppSmsRecordServiceImpl extends ServiceImpl<AppSmsRecordMapper, AppSmsRecord> implements IAppSmsRecordService {

    @Override
    public AppSmsRecordVo queryById(Long id){
        return getVoById(id, AppSmsRecordVo.class);
    }

    @Override
    public TableDataInfo<AppSmsRecordVo> queryPageList(AppSmsRecordBo bo) {
        PagePlus<AppSmsRecord, AppSmsRecordVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo), AppSmsRecordVo.class);
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<AppSmsRecordVo> queryList(AppSmsRecordBo bo) {
        return listVo(buildQueryWrapper(bo), AppSmsRecordVo.class);
    }

    private LambdaQueryWrapper<AppSmsRecord> buildQueryWrapper(AppSmsRecordBo bo) {

        LambdaQueryWrapper<AppSmsRecord> lqw = Wrappers.lambdaQuery();
        lqw.eq(StrUtil.isNotBlank(bo.getPhone()), AppSmsRecord::getPhone, bo.getPhone());
        lqw.eq(StrUtil.isNotBlank(bo.getIp()), AppSmsRecord::getIp, bo.getIp());
        lqw.eq(StrUtil.isNotBlank(bo.getContent()), AppSmsRecord::getContent, bo.getContent());
        return lqw;
    }

    @Override
    public Boolean insertByBo(AppSmsRecordBo bo) {
        AppSmsRecord add = BeanUtil.toBean(bo, AppSmsRecord.class);
        validEntityBeforeSave(add);
        boolean flag = save(add);
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    @Override
    public Boolean updateByBo(AppSmsRecordBo bo) {
        AppSmsRecord update = BeanUtil.toBean(bo, AppSmsRecord.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(AppSmsRecord entity){
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
