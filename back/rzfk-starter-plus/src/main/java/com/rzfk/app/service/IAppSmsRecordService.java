package com.rzfk.app.service;

import com.rzfk.app.domain.AppSmsRecord;
import com.rzfk.app.domain.vo.AppSmsRecordVo;
import com.rzfk.app.domain.bo.AppSmsRecordBo;
import com.rzfk.common.core.page.IServicePlus;
import com.rzfk.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 短信发送记录Service接口
 *
 * @author streaker
 * @date 2022-08-20
 */
public interface IAppSmsRecordService extends IServicePlus<AppSmsRecord> {
	/**
	 * 查询单个
	 * @return
	 */
	AppSmsRecordVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<AppSmsRecordVo> queryPageList(AppSmsRecordBo bo);

	/**
	 * 查询列表
	 */
	List<AppSmsRecordVo> queryList(AppSmsRecordBo bo);

	/**
	 * 根据新增业务对象插入短信发送记录
	 * @param bo 短信发送记录新增业务对象
	 * @return
	 */
	Boolean insertByBo(AppSmsRecordBo bo);

	/**
	 * 根据编辑业务对象修改短信发送记录
	 * @param bo 短信发送记录编辑业务对象
	 * @return
	 */
	Boolean updateByBo(AppSmsRecordBo bo);

	/**
	 * 校验并删除数据
	 * @param ids 主键集合
	 * @param isValid 是否校验,true-删除前校验,false-不校验
	 * @return
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
