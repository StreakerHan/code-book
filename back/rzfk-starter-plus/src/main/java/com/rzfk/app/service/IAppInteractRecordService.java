package com.rzfk.app.service;

import com.rzfk.app.domain.AppInteractRecord;
import com.rzfk.app.domain.vo.AppInteractRecordVo;
import com.rzfk.app.domain.bo.AppInteractRecordBo;
import com.rzfk.common.core.page.IServicePlus;
import com.rzfk.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * App资源互动记录Service接口
 *
 * @author streaker
 * @date 2022-08-15
 */
public interface IAppInteractRecordService extends IServicePlus<AppInteractRecord> {
	/**
	 * 查询单个
	 * @return
	 */
	AppInteractRecordVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<AppInteractRecordVo> queryPageList(AppInteractRecordBo bo);

	/**
	 * 查询列表
	 */
	List<AppInteractRecordVo> queryList(AppInteractRecordBo bo);

	/**
	 * 根据新增业务对象插入App资源互动记录
	 * @param bo App资源互动记录新增业务对象
	 * @return
	 */
	Boolean insertByBo(AppInteractRecordBo bo);

	/**
	 * 根据编辑业务对象修改App资源互动记录
	 * @param bo App资源互动记录编辑业务对象
	 * @return
	 */
	Boolean updateByBo(AppInteractRecordBo bo);

	/**
	 * 校验并删除数据
	 * @param ids 主键集合
	 * @param isValid 是否校验,true-删除前校验,false-不校验
	 * @return
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
