package com.rzfk.app.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rzfk.common.utils.IDUtils;
import com.rzfk.common.utils.PageUtils;
import com.rzfk.common.core.page.PagePlus;
import com.rzfk.common.core.page.TableDataInfo;
import com.rzfk.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.rzfk.app.domain.bo.AppCommentBo;
import com.rzfk.app.domain.vo.AppCommentVo;
import com.rzfk.app.domain.AppComment;
import com.rzfk.app.mapper.AppCommentMapper;
import com.rzfk.app.service.IAppCommentService;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 文章评论Service业务层处理
 *
 * @author streaker
 * @date 2022-08-17
 */
@Service
public class AppCommentServiceImpl extends ServiceImpl<AppCommentMapper, AppComment> implements IAppCommentService {

    @Override
    public AppCommentVo queryById(Long id){
        return getVoById(id, AppCommentVo.class);
    }

    @Override
    public TableDataInfo<AppCommentVo> queryPageList(AppCommentBo bo) {
        PagePlus<AppComment, AppCommentVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo), AppCommentVo.class);
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<AppCommentVo> queryList(AppCommentBo bo) {
        return listVo(buildQueryWrapper(bo), AppCommentVo.class);
    }

    private QueryWrapper<AppComment> buildQueryWrapper(AppCommentBo bo) {

        QueryWrapper<AppComment> lqw = new QueryWrapper<>();
        lqw.select("*,(select b.title from app_post b where b.id = app_comment.post_id) as title," +
                "(select b.img from app_source_info b where b.id = app_comment.post_id) as image1," +
                "(select b.name from app_source_info b where b.id = app_comment.post_id) as title1," +
                "(select b.img from app_post b where b.id = app_comment.post_id) as image ");
        lqw.eq(bo.getUserId() != null, "user_id", bo.getUserId());
        lqw.eq(bo.getPostId() != null, "post_id", bo.getPostId());
        lqw.eq(StrUtil.isNotBlank(bo.getContent()), "content", bo.getContent());
        lqw.orderByDesc("create_time");
        return lqw;
    }

    @Override
    public Boolean insertByBo(AppCommentBo bo) {
        AppComment add = BeanUtil.toBean(bo, AppComment.class);
        add.setId(IDUtils.getId());
        add.setUserId(SecurityUtils.getLoginUser().getAppUser().getId());
        add.setCreateTime(new Date());
        validEntityBeforeSave(add);
        boolean flag = save(add);
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    @Override
    public Boolean updateByBo(AppCommentBo bo) {
        AppComment update = BeanUtil.toBean(bo, AppComment.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(AppComment entity){
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
