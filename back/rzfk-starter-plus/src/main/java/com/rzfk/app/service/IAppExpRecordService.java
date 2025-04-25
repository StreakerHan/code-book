package com.rzfk.app.service;

import com.rzfk.app.domain.AppExpRecord;
import com.rzfk.app.domain.vo.AppExpRecordVo;
import com.rzfk.app.domain.bo.AppExpRecordBo;
import com.rzfk.common.core.page.IServicePlus;
import com.rzfk.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 用户经验记录Service接口
 *
 * @author streaker
 * @date 2022-08-13
 */
public interface IAppExpRecordService extends IServicePlus<AppExpRecord> {
	/**
	 * 查询单个
	 * @return
	 */
	AppExpRecordVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<AppExpRecordVo> queryPageList(AppExpRecordBo bo);

	/**
	 * 查询列表
	 */
	List<AppExpRecordVo> queryList(AppExpRecordBo bo);

	/**
	 * 根据新增业务对象插入用户经验记录
	 * @param bo 用户经验记录新增业务对象
	 * @return
	 */
	Boolean insertByBo(AppExpRecordBo bo);

	/**
	 * 根据编辑业务对象修改用户经验记录
	 * @param bo 用户经验记录编辑业务对象
	 * @return
	 */
	Boolean updateByBo(AppExpRecordBo bo);

	/**
	 * 校验并删除数据
	 * @param ids 主键集合
	 * @param isValid 是否校验,true-删除前校验,false-不校验
	 * @return
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
