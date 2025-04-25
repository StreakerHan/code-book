package com.rzfk.app.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rzfk.app.domain.AppComment;
import com.rzfk.app.domain.AppInteractRecord;
import com.rzfk.app.domain.vo.AppPointsRecordVo;
import com.rzfk.app.service.IAppCommentService;
import com.rzfk.app.service.IAppInteractRecordService;
import com.rzfk.common.core.domain.entity.SysUser;
import com.rzfk.common.utils.IDUtils;
import com.rzfk.common.utils.PageUtils;
import com.rzfk.common.core.page.PagePlus;
import com.rzfk.common.core.page.TableDataInfo;
import com.rzfk.common.utils.SecurityUtils;
import com.rzfk.common.utils.StringUtils;
import com.rzfk.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.rzfk.app.domain.bo.AppPostBo;
import com.rzfk.app.domain.vo.AppPostVo;
import com.rzfk.app.domain.AppPost;
import com.rzfk.app.mapper.AppPostMapper;
import com.rzfk.app.service.IAppPostService;

import java.nio.file.Watchable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * app文章Service业务层处理
 *
 * @author streaker
 * @date 2022-08-14
 */
@Service
public class AppPostServiceImpl extends ServiceImpl<AppPostMapper, AppPost> implements IAppPostService {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private IAppInteractRecordService appInteractRecordService;

    @Autowired
    private IAppCommentService appCommentService;

    @Override
    public AppPostVo queryById(Long id) {
        return getVoById(id, AppPostVo.class);
    }

    @Override
    public AppPostVo queryByIdApp(Long id) {
        AppPostVo appPostVo = getVoById(id, AppPostVo.class);
        //查询作者
        SysUser sysUser = userService.selectUserById(appPostVo.getCreateBy());
        appPostVo.setAuthor(sysUser.getNickName());
        //查看当前动态的点赞数
        QueryWrapper<AppInteractRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("source_id", id);
        queryWrapper.eq("interact_type", "like");
        List<AppInteractRecord> list = appInteractRecordService.list(queryWrapper);
        appPostVo.setLike(list.size() + "");
        //查看当前动态的收藏数
        queryWrapper.clear();
        queryWrapper.eq("source_id", id);
        queryWrapper.eq("interact_type", "collect");
        List<AppInteractRecord> list1 = appInteractRecordService.list(queryWrapper);
        appPostVo.setCollect(list1.size() + "");
        //查看当前动态的浏览量
        queryWrapper.clear();
        queryWrapper.eq("source_id", id);
        queryWrapper.eq("interact_type", "view");
        List<AppInteractRecord> list2 = appInteractRecordService.list(queryWrapper);
        appPostVo.setView(list2.size() + "");
        //查询当前动态的评论
        QueryWrapper<AppComment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.select("*,(select b.avatar from app_user b where app_comment.user_id = b.id) as avatar,(select b.nickname from app_user b where app_comment.user_id = b.id) as nickname ");
        commentQueryWrapper.eq("post_id", id);
        commentQueryWrapper.orderByDesc("create_time");
        List<AppComment> comments = appCommentService.list(commentQueryWrapper);
        appPostVo.setComments(comments);
        appPostVo.setComment(comments.size() + "");
        //查看当前用户是否点赞
        appPostVo.setIsLike("0");
        //查看当前用户是否收藏
        appPostVo.setIsCollect("0");
        //增加互动记录(先判断是否有登录用户)
        AppInteractRecord appInteractRecord = new AppInteractRecord();
        appInteractRecord.setId(IDUtils.getId());
        appInteractRecord.setCreateTime(new Date());
//        appInteractRecord.setUserId(SecurityUtils.getLoginUser().getAppUser().getId());
        appInteractRecord.setType("post");
        appInteractRecord.setSourceId(id);
        appInteractRecord.setInteractType("view");
        appInteractRecordService.save(appInteractRecord);
        return appPostVo;
    }

    @Override
    public AppPostVo queryByIdAppWithToken(Long id) {
        AppPostVo appPostVo = getVoById(id, AppPostVo.class);
        //查询作者
        SysUser sysUser = userService.selectUserById(appPostVo.getCreateBy());
        appPostVo.setAuthor(sysUser.getNickName());
        //查看当前动态的点赞数
        QueryWrapper<AppInteractRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("source_id", id);
        queryWrapper.eq("interact_type", "like");
        List<AppInteractRecord> list = appInteractRecordService.list(queryWrapper);
        appPostVo.setLike(list.size() + "");
        //查看当前动态的收藏数
        queryWrapper.clear();
        queryWrapper.eq("source_id", id);
        queryWrapper.eq("interact_type", "collect");
        List<AppInteractRecord> list1 = appInteractRecordService.list(queryWrapper);
        appPostVo.setCollect(list1.size() + "");
        //查看当前动态的浏览量
        queryWrapper.clear();
        queryWrapper.eq("source_id", id);
        queryWrapper.eq("interact_type", "view");
        List<AppInteractRecord> list2 = appInteractRecordService.list(queryWrapper);
        appPostVo.setView(list2.size() + "");
        //查询当前动态的评论
        QueryWrapper<AppComment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.select("*,(select b.avatar from app_user b where app_comment.user_id = b.id) as avatar,(select b.nickname from app_user b where app_comment.user_id = b.id) as nickname ");
        commentQueryWrapper.eq("post_id", id);
        commentQueryWrapper.orderByDesc("create_time");
        List<AppComment> comments = appCommentService.list(commentQueryWrapper);
        appPostVo.setComments(comments);
        appPostVo.setComment(comments.size() + "");
        //查看当前用户是否点赞
        queryWrapper.clear();
        queryWrapper.eq("source_id", id);
        queryWrapper.eq("user_id", SecurityUtils.getLoginUser().getAppUser().getId());
        queryWrapper.eq("interact_type", "like");
        List<AppInteractRecord> list3 = appInteractRecordService.list(queryWrapper);
        appPostVo.setIsLike(list3.size() + "");
        //查看当前用户是否收藏
        queryWrapper.clear();
        queryWrapper.eq("source_id", id);
        queryWrapper.eq("user_id", SecurityUtils.getLoginUser().getAppUser().getId());
        queryWrapper.eq("interact_type", "collect");
        List<AppInteractRecord> list4 = appInteractRecordService.list(queryWrapper);
        appPostVo.setIsCollect(list4.size() + "");
        //增加互动记录(先判断是否有登录用户)
        AppInteractRecord appInteractRecord = new AppInteractRecord();
        appInteractRecord.setId(IDUtils.getId());
        appInteractRecord.setCreateTime(new Date());
        appInteractRecord.setUserId(SecurityUtils.getLoginUser().getAppUser().getId());
        appInteractRecord.setType("post");
        appInteractRecord.setSourceId(id);
        appInteractRecord.setInteractType("view");
        appInteractRecordService.save(appInteractRecord);
        return appPostVo;
    }

    @Override
    public TableDataInfo<AppPostVo> queryPageList(AppPostBo bo) {
        PagePlus<AppPost, AppPostVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo), AppPostVo.class);
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<AppPostVo> queryList(AppPostBo bo) {
        return listVo(buildQueryWrapper(bo), AppPostVo.class);
    }

    private QueryWrapper<AppPost> buildQueryWrapper(AppPostBo bo) {

        QueryWrapper<AppPost> lqw = new QueryWrapper<>();
        lqw.select("*,(select count(*) from app_interact_record b where b.source_id = app_post.id and b.type = 'post' and b.interact_type = 'view' ) as view ");
        lqw.eq(StrUtil.isNotBlank(bo.getTitle()), "title", bo.getTitle());
        lqw.eq(StrUtil.isNotBlank(bo.getContent()), "content", bo.getContent());
        lqw.eq(StrUtil.isNotBlank(bo.getType()), "type", bo.getType());
        lqw.eq(StrUtil.isNotBlank(bo.getStatus()), "status", bo.getStatus());
        return lqw;
    }

    @Override
    public TableDataInfo<AppPostVo> queryPageListApp(AppPostBo bo) {
        PagePlus<AppPost, AppPostVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapperApp(bo), AppPostVo.class);
        return PageUtils.buildDataInfo(result);
    }

    private QueryWrapper<AppPost> buildQueryWrapperApp(AppPostBo bo) {

        QueryWrapper<AppPost> lqw = new QueryWrapper<>();
        lqw.select("*,(select count(*) from app_interact_record b where b.source_id = app_post.id and b.type = 'post' and b.interact_type = 'view' ) as \"view\"" +
                ",(select count(*) from app_interact_record b where b.source_id = app_post.id and b.type = 'post' and b.interact_type = 'like' ) as \"like\"" +
                ",(select count(*) from app_interact_record b where b.source_id = app_post.id and b.type = 'post' and b.interact_type = 'collect' ) as \"collect\"");
        if (StringUtils.isNotEmpty(bo.getType())) {
            lqw.eq("type", bo.getType());
        } else {
            lqw.and(wrapper -> wrapper.eq("type", "赛事").or().eq("type", "推荐").or().eq("type", "车手"));
        }
        lqw.eq("status", "1");
        lqw.like(StrUtil.isNotBlank(bo.getTitle()), "title", bo.getTitle());
        lqw.orderByDesc("create_time");
        return lqw;
    }

    @Override
    public Boolean insertByBo(AppPostBo bo) {
        bo.setCreateTime(new Date());
        bo.setCreateBy(SecurityUtils.getLoginUser().getUser().getUserId());
        AppPost add = BeanUtil.toBean(bo, AppPost.class);
        validEntityBeforeSave(add);
        boolean flag = save(add);
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    @Override
    public Boolean updateByBo(AppPostBo bo) {
        AppPost update = BeanUtil.toBean(bo, AppPost.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(AppPost entity) {
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
