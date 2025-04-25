package com.rzfk.app.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rzfk.app.domain.AppComment;
import com.rzfk.app.domain.AppInteractRecord;
import com.rzfk.app.service.IAppCommentService;
import com.rzfk.app.service.IAppInteractRecordService;
import com.rzfk.common.utils.IDUtils;
import com.rzfk.common.utils.PageUtils;
import com.rzfk.common.core.page.PagePlus;
import com.rzfk.common.core.page.TableDataInfo;
import com.rzfk.common.utils.SecurityUtils;
import com.rzfk.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rzfk.app.domain.bo.AppSourceInfoBo;
import com.rzfk.app.domain.vo.AppSourceInfoVo;
import com.rzfk.app.domain.AppSourceInfo;
import com.rzfk.app.mapper.AppSourceInfoMapper;
import com.rzfk.app.service.IAppSourceInfoService;

import java.util.Date;
import java.util.List;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 资源信息Service业务层处理
 *
 * @author streaker
 * @date 2022-08-19
 */
@Service
public class AppSourceInfoServiceImpl extends ServiceImpl<AppSourceInfoMapper, AppSourceInfo> implements IAppSourceInfoService {

    @Autowired
    private IAppInteractRecordService appInteractRecordService;

    @Autowired
    private IAppCommentService appCommentService;

    @Override
    public AppSourceInfoVo queryById(Long id) {
        return getVoById(id, AppSourceInfoVo.class);
    }

    @Override
    public AppSourceInfoVo getInfoAppWithToken(Long id) {
        AppSourceInfoVo appSourceInfoVo = getVoById(id, AppSourceInfoVo.class);
        //查看当前动态的点赞数
        QueryWrapper<AppInteractRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("source_id", id);
        queryWrapper.eq("interact_type", "like");
        List<AppInteractRecord> list = appInteractRecordService.list(queryWrapper);
        appSourceInfoVo.setLike(list.size() + "");
        //查看当前动态的收藏数
        queryWrapper.clear();
        queryWrapper.eq("source_id", id);
        queryWrapper.eq("interact_type", "collect");
        List<AppInteractRecord> list1 = appInteractRecordService.list(queryWrapper);
        appSourceInfoVo.setCollect(list1.size() + "");
        //查看当前动态的浏览量
        queryWrapper.clear();
        queryWrapper.eq("source_id", id);
        queryWrapper.eq("interact_type", "view");
        List<AppInteractRecord> list2 = appInteractRecordService.list(queryWrapper);
        appSourceInfoVo.setView(list2.size() + "");
        //查询当前动态的评论
        QueryWrapper<AppComment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.select("*,(select b.avatar from app_user b where app_comment.user_id = b.id) as avatar,(select b.nickname from app_user b where app_comment.user_id = b.id) as nickname ");
        commentQueryWrapper.eq("post_id", id);
        commentQueryWrapper.orderByDesc("create_time");
        List<AppComment> comments = appCommentService.list(commentQueryWrapper);
        appSourceInfoVo.setComments(comments);
        appSourceInfoVo.setComment(comments.size() + "");
        //查看当前用户是否点赞
        queryWrapper.clear();
        queryWrapper.eq("source_id", id);
        queryWrapper.eq("user_id", SecurityUtils.getLoginUser().getAppUser().getId());
        queryWrapper.eq("interact_type", "like");
        List<AppInteractRecord> list3 = appInteractRecordService.list(queryWrapper);
        appSourceInfoVo.setIsLike(list3.size() + "");
        //查看当前用户是否收藏
        queryWrapper.clear();
        queryWrapper.eq("source_id", id);
        queryWrapper.eq("user_id", SecurityUtils.getLoginUser().getAppUser().getId());
        queryWrapper.eq("interact_type", "collect");
        List<AppInteractRecord> list4 = appInteractRecordService.list(queryWrapper);
        appSourceInfoVo.setIsCollect(list4.size() + "");
        //增加互动记录(先判断是否有登录用户)
        AppInteractRecord appInteractRecord = new AppInteractRecord();
        appInteractRecord.setId(IDUtils.getId());
        appInteractRecord.setCreateTime(new Date());
        appInteractRecord.setUserId(SecurityUtils.getLoginUser().getAppUser().getId());
        appInteractRecord.setType("source");
        appInteractRecord.setSourceId(id);
        appInteractRecord.setInteractType("view");
        appInteractRecordService.save(appInteractRecord);
        return appSourceInfoVo;
    }


    @Override
    public AppSourceInfoVo queryByIdApp(Long id) {
        AppSourceInfoVo appSourceInfoVo = getVoById(id, AppSourceInfoVo.class);
        //查看当前动态的点赞数
        QueryWrapper<AppInteractRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("source_id", id);
        queryWrapper.eq("interact_type", "like");
        List<AppInteractRecord> list = appInteractRecordService.list(queryWrapper);
        appSourceInfoVo.setLike(list.size() + "");
        //查看当前动态的收藏数
        queryWrapper.clear();
        queryWrapper.eq("source_id", id);
        queryWrapper.eq("interact_type", "collect");
        List<AppInteractRecord> list1 = appInteractRecordService.list(queryWrapper);
        appSourceInfoVo.setCollect(list1.size() + "");
        //查看当前动态的浏览量
        queryWrapper.clear();
        queryWrapper.eq("source_id", id);
        queryWrapper.eq("interact_type", "view");
        List<AppInteractRecord> list2 = appInteractRecordService.list(queryWrapper);
        appSourceInfoVo.setView(list2.size() + "");
        //查询当前动态的评论
        QueryWrapper<AppComment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.select("*,(select b.avatar from app_user b where app_comment.user_id = b.id) as avatar,(select b.nickname from app_user b where app_comment.user_id = b.id) as nickname ");
        commentQueryWrapper.eq("post_id", id);
        commentQueryWrapper.orderByDesc("create_time");
        List<AppComment> comments = appCommentService.list(commentQueryWrapper);
        appSourceInfoVo.setComments(comments);
        appSourceInfoVo.setComment(comments.size() + "");
        //查看当前用户是否点赞
        appSourceInfoVo.setIsLike("0");
        //查看当前用户是否收藏
        appSourceInfoVo.setIsCollect("0");
        //增加互动记录(先判断是否有登录用户)
        AppInteractRecord appInteractRecord = new AppInteractRecord();
        appInteractRecord.setId(IDUtils.getId());
        appInteractRecord.setCreateTime(new Date());
//        appInteractRecord.setUserId(SecurityUtils.getLoginUser().getAppUser().getId());
        appInteractRecord.setType("source");
        appInteractRecord.setSourceId(id);
        appInteractRecord.setInteractType("view");
        appInteractRecordService.save(appInteractRecord);
        return appSourceInfoVo;
    }

    @Override
    public TableDataInfo<AppSourceInfoVo> queryPageList(AppSourceInfoBo bo) {
        PagePlus<AppSourceInfo, AppSourceInfoVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo), AppSourceInfoVo.class);
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public TableDataInfo<AppSourceInfoVo> queryPageListRandom(AppSourceInfoBo bo) {
        PagePlus<AppSourceInfo, AppSourceInfoVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapperRandom(bo), AppSourceInfoVo.class);
        return PageUtils.buildDataInfo(result);
    }

    private QueryWrapper<AppSourceInfo> buildQueryWrapperRandom(AppSourceInfoBo bo) {
        QueryWrapper<AppSourceInfo> lqw = new QueryWrapper<>();
        lqw.select("id,name,img,type,(select count(*) from app_interact_record where interact_type = 'view' and source_id = app_source_info.id) as view ");
        lqw.like(StrUtil.isNotBlank(bo.getName()), "name", bo.getName());
        lqw.eq(bo.getTypeId() != null, "type_id", bo.getTypeId());
        lqw.eq(StrUtil.isNotBlank(bo.getSeq()), "seq", bo.getSeq());
        lqw.like(StrUtil.isNotBlank(bo.getType()), "type", bo.getType());
        lqw.eq(StrUtil.isNotBlank(bo.getUrl()), "url", bo.getUrl());
        lqw.eq(StrUtil.isNotBlank(bo.getLink()), "link", bo.getLink());
        lqw.in(CollectionUtil.isNotEmpty(bo.getIds()), "id", bo.getIds());
//        lqw.ge("id","( SELECT floor( RAND() * ( SELECT MAX( id ) FROM `app_source_info` ) ) ) ");
        lqw.isNotNull("link");
        lqw.notLike("url", "/tag/");
        lqw.last("order by RAND() ");
        return lqw;
    }

    @Override
    public List<AppSourceInfoVo> queryList(AppSourceInfoBo bo) {
        return listVo(buildQueryWrapper(bo), AppSourceInfoVo.class);
    }

    private QueryWrapper<AppSourceInfo> buildQueryWrapper(AppSourceInfoBo bo) {
        QueryWrapper<AppSourceInfo> lqw = new QueryWrapper<>();
        lqw.select("*,(select count(*) from app_interact_record where interact_type = 'view' and source_id = app_source_info.id) as view ");
        lqw.like(StrUtil.isNotBlank(bo.getName()), "name", bo.getName());
        lqw.eq(bo.getTypeId() != null, "type_id", bo.getTypeId());
        lqw.eq(StrUtil.isNotBlank(bo.getSeq()), "seq", bo.getSeq());
        lqw.like(StrUtil.isNotBlank(bo.getType()), "type", bo.getType());
        lqw.eq(StrUtil.isNotBlank(bo.getUrl()), "url", bo.getUrl());
        lqw.eq(StrUtil.isNotBlank(bo.getLink()), "link", bo.getLink());
        lqw.in(CollectionUtil.isNotEmpty(bo.getIds()), "id", bo.getIds());
        if(StrUtil.isNotBlank(bo.getSeq())){
            lqw.eq(StrUtil.isNotBlank(bo.getSeq()), "seq", bo.getSeq());
        }else{
            lqw.isNull("seq");
        }
        lqw.isNotNull("link");
        lqw.notLike("url", "/tag/");
        return lqw;
    }

    @Override
    public Boolean insertByBo(AppSourceInfoBo bo) {
        AppSourceInfo add = BeanUtil.toBean(bo, AppSourceInfo.class);
        validEntityBeforeSave(add);
        boolean flag = save(add);
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    @Override
    public Boolean updateByBo(AppSourceInfoBo bo) {
        AppSourceInfo update = BeanUtil.toBean(bo, AppSourceInfo.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(AppSourceInfo entity) {
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
