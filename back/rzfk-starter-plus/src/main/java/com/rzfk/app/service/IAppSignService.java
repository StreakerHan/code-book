package com.rzfk.app.service;

import com.rzfk.app.domain.AppSign;
import com.rzfk.app.domain.vo.AppSignVo;
import com.rzfk.app.domain.bo.AppSignBo;
import com.rzfk.common.core.page.IServicePlus;
import com.rzfk.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 用户签到记录Service接口
 *
 * @author streaker
 * @date 2022-08-13
 */
public interface IAppSignService extends IServicePlus<AppSign> {
	/**
	 * 查询单个
	 * @return
	 */
	AppSignVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<AppSignVo> queryPageList(AppSignBo bo);

	/**
	 * 查询列表
	 */
	List<AppSignVo> queryList(AppSignBo bo);

	/**
	 * 根据新增业务对象插入用户签到记录
	 * @param bo 用户签到记录新增业务对象
	 * @return
	 */
	Boolean insertByBo(AppSignBo bo);

	/**
	 * 根据编辑业务对象修改用户签到记录
	 * @param bo 用户签到记录编辑业务对象
	 * @return
	 */
	Boolean updateByBo(AppSignBo bo);

	/**
	 * 校验并删除数据
	 * @param ids 主键集合
	 * @param isValid 是否校验,true-删除前校验,false-不校验
	 * @return
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
