package com.rzfk.app.service;

import com.rzfk.app.domain.AppPointsRecord;
import com.rzfk.app.domain.vo.AppPointsRecordVo;
import com.rzfk.app.domain.bo.AppPointsRecordBo;
import com.rzfk.common.core.page.IServicePlus;
import com.rzfk.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 积分记录Service接口
 *
 * @author streaker
 * @date 2022-08-13
 */
public interface IAppPointsRecordService extends IServicePlus<AppPointsRecord> {
	/**
	 * 查询单个
	 * @return
	 */
	AppPointsRecordVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<AppPointsRecordVo> queryPageList(AppPointsRecordBo bo);

	/**
	 * 查询列表
	 */
	List<AppPointsRecordVo> queryList(AppPointsRecordBo bo);

	/**
	 * 根据新增业务对象插入积分记录
	 * @param bo 积分记录新增业务对象
	 * @return
	 */
	Boolean insertByBo(AppPointsRecordBo bo);

	/**
	 * 根据编辑业务对象修改积分记录
	 * @param bo 积分记录编辑业务对象
	 * @return
	 */
	Boolean updateByBo(AppPointsRecordBo bo);

	/**
	 * 校验并删除数据
	 * @param ids 主键集合
	 * @param isValid 是否校验,true-删除前校验,false-不校验
	 * @return
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
