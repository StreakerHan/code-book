package com.rzfk.app.service;

import com.rzfk.app.domain.AppComment;
import com.rzfk.app.domain.vo.AppCommentVo;
import com.rzfk.app.domain.bo.AppCommentBo;
import com.rzfk.common.core.page.IServicePlus;
import com.rzfk.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 文章评论Service接口
 *
 * @author streaker
 * @date 2022-08-17
 */
public interface IAppCommentService extends IServicePlus<AppComment> {
	/**
	 * 查询单个
	 * @return
	 */
	AppCommentVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<AppCommentVo> queryPageList(AppCommentBo bo);

	/**
	 * 查询列表
	 */
	List<AppCommentVo> queryList(AppCommentBo bo);

	/**
	 * 根据新增业务对象插入文章评论
	 * @param bo 文章评论新增业务对象
	 * @return
	 */
	Boolean insertByBo(AppCommentBo bo);

	/**
	 * 根据编辑业务对象修改文章评论
	 * @param bo 文章评论编辑业务对象
	 * @return
	 */
	Boolean updateByBo(AppCommentBo bo);

	/**
	 * 校验并删除数据
	 * @param ids 主键集合
	 * @param isValid 是否校验,true-删除前校验,false-不校验
	 * @return
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
