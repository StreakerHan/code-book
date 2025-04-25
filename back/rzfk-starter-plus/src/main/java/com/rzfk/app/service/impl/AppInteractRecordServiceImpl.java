package com.rzfk.app.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rzfk.common.utils.PageUtils;
import com.rzfk.common.core.page.PagePlus;
import com.rzfk.common.core.page.TableDataInfo;
import com.rzfk.common.utils.SecurityUtils;
import com.rzfk.common.utils.StringUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.rzfk.app.domain.bo.AppInteractRecordBo;
import com.rzfk.app.domain.vo.AppInteractRecordVo;
import com.rzfk.app.domain.AppInteractRecord;
import com.rzfk.app.mapper.AppInteractRecordMapper;
import com.rzfk.app.service.IAppInteractRecordService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * App资源互动记录Service业务层处理
 *
 * @author streaker
 * @date 2022-08-15
 */
@Service
public class AppInteractRecordServiceImpl extends ServiceImpl<AppInteractRecordMapper, AppInteractRecord> implements IAppInteractRecordService {

    @Override
    public AppInteractRecordVo queryById(Long id) {
        return getVoById(id, AppInteractRecordVo.class);
    }

    @Override
    public TableDataInfo<AppInteractRecordVo> queryPageList(AppInteractRecordBo bo) {
        PagePlus<AppInteractRecord, AppInteractRecordVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo), AppInteractRecordVo.class);
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<AppInteractRecordVo> queryList(AppInteractRecordBo bo) {
        return listVo(buildQueryWrapper(bo), AppInteractRecordVo.class);
    }

    private QueryWrapper<AppInteractRecord> buildQueryWrapper(AppInteractRecordBo bo) {

        QueryWrapper<AppInteractRecord> lqw = new QueryWrapper<>();
        lqw.select("*,(select b.title from app_post b where b.id = app_interact_record.source_id) as title," +
                "(select b.img from app_post b where b.id = app_interact_record.source_id) as image," +
                "(select b.img from app_source_info b where b.id = app_interact_record.source_id) as image1," +
                "(select b.name from app_source_info b where b.id = app_interact_record.source_id) as title1," +
                "(select b.type from app_post b where b.id = app_interact_record.source_id) as postType," +
                "(select count(*) from app_interact_record c where c.source_id = app_interact_record.source_id  and c.interact_type = 'view') as view ");
        lqw.eq(bo.getCheckType() != null, "user_id", SecurityUtils.getLoginUser().getAppUser().getId());
        lqw.eq(bo.getCreateTime() != null, "create_time", bo.getCreateTime());
        lqw.eq(StrUtil.isNotBlank(bo.getType()), "type", bo.getType());
        lqw.eq(bo.getSourceId() != null, "source_id", bo.getSourceId());
        lqw.eq(StrUtil.isNotBlank(bo.getInteractType()), "interact_type", bo.getInteractType());
        lqw.orderByDesc("app_interact_record.create_time");
//        lqw.groupBy("app_interact_record.source_id");
        return lqw;
    }

    @Override
    public Boolean insertByBo(AppInteractRecordBo bo) {
        AppInteractRecord add = BeanUtil.toBean(bo, AppInteractRecord.class);
        validEntityBeforeSave(add);
        boolean flag = save(add);
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    @Override
    public Boolean updateByBo(AppInteractRecordBo bo) {
        AppInteractRecord update = BeanUtil.toBean(bo, AppInteractRecord.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(AppInteractRecord entity) {
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
