package com.rzfk.app.service;

import com.rzfk.app.domain.AppPost;
import com.rzfk.app.domain.vo.AppPostVo;
import com.rzfk.app.domain.bo.AppPostBo;
import com.rzfk.common.core.page.IServicePlus;
import com.rzfk.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * app文章Service接口
 *
 * @author streaker
 * @date 2022-08-14
 */
public interface IAppPostService extends IServicePlus<AppPost> {
	/**
	 * 查询单个
	 * @return
	 */
	AppPostVo queryById(Long id);

	/**
	 * 查询单个
	 * @return
	 */
	AppPostVo queryByIdApp(Long id);

	/**
	 * 查询单个 token
	 */
	AppPostVo queryByIdAppWithToken(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<AppPostVo> queryPageList(AppPostBo bo);

	/**
	 * 查询列表app
	 */
	TableDataInfo<AppPostVo> queryPageListApp(AppPostBo bo);

	/**
	 * 查询列表
	 */
	List<AppPostVo> queryList(AppPostBo bo);

	/**
	 * 根据新增业务对象插入app文章
	 * @param bo app文章新增业务对象
	 * @return
	 */
	Boolean insertByBo(AppPostBo bo);

	/**
	 * 根据编辑业务对象修改app文章
	 * @param bo app文章编辑业务对象
	 * @return
	 */
	Boolean updateByBo(AppPostBo bo);

	/**
	 * 校验并删除数据
	 * @param ids 主键集合
	 * @param isValid 是否校验,true-删除前校验,false-不校验
	 * @return
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
