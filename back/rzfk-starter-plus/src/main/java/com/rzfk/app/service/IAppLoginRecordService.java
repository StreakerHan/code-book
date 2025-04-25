package com.rzfk.app.service;

import com.rzfk.app.domain.AppLoginRecord;
import com.rzfk.app.domain.vo.AppLoginRecordVo;
import com.rzfk.app.domain.bo.AppLoginRecordBo;
import com.rzfk.common.core.page.IServicePlus;
import com.rzfk.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * app用户登录记录Service接口
 *
 * @author streaker
 * @date 2022-08-10
 */
public interface IAppLoginRecordService extends IServicePlus<AppLoginRecord> {
	/**
	 * 查询单个
	 * @return
	 */
	AppLoginRecordVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<AppLoginRecordVo> queryPageList(AppLoginRecordBo bo);

	/**
	 * 查询列表
	 */
	List<AppLoginRecordVo> queryList(AppLoginRecordBo bo);

	/**
	 * 根据新增业务对象插入app用户登录记录
	 * @param bo app用户登录记录新增业务对象
	 * @return
	 */
	Boolean insertByBo(AppLoginRecordBo bo);

	/**
	 * 根据编辑业务对象修改app用户登录记录
	 * @param bo app用户登录记录编辑业务对象
	 * @return
	 */
	Boolean updateByBo(AppLoginRecordBo bo);

	/**
	 * 校验并删除数据
	 * @param ids 主键集合
	 * @param isValid 是否校验,true-删除前校验,false-不校验
	 * @return
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
