package com.rzfk.app.service;

import com.rzfk.app.domain.AppFeedback;
import com.rzfk.app.domain.vo.AppFeedbackVo;
import com.rzfk.app.domain.bo.AppFeedbackBo;
import com.rzfk.common.core.page.IServicePlus;
import com.rzfk.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 意见反馈Service接口
 *
 * @author streaker
 * @date 2022-08-18
 */
public interface IAppFeedbackService extends IServicePlus<AppFeedback> {
	/**
	 * 查询单个
	 * @return
	 */
	AppFeedbackVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<AppFeedbackVo> queryPageList(AppFeedbackBo bo);

	/**
	 * 查询列表
	 */
	List<AppFeedbackVo> queryList(AppFeedbackBo bo);

	/**
	 * 根据新增业务对象插入意见反馈
	 * @param bo 意见反馈新增业务对象
	 * @return
	 */
	Boolean insertByBo(AppFeedbackBo bo);

	/**
	 * 根据编辑业务对象修改意见反馈
	 * @param bo 意见反馈编辑业务对象
	 * @return
	 */
	Boolean updateByBo(AppFeedbackBo bo);

	/**
	 * 校验并删除数据
	 * @param ids 主键集合
	 * @param isValid 是否校验,true-删除前校验,false-不校验
	 * @return
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
