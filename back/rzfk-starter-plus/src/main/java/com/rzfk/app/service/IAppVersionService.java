package com.rzfk.app.service;

import com.rzfk.app.domain.AppVersion;
import com.rzfk.app.domain.vo.AppVersionVo;
import com.rzfk.app.domain.bo.AppVersionBo;
import com.rzfk.common.core.page.IServicePlus;
import com.rzfk.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * app版本管理Service接口
 *
 * @author streaker
 * @date 2022-08-20
 */
public interface IAppVersionService extends IServicePlus<AppVersion> {
	/**
	 * 查询单个
	 * @return
	 */
	AppVersionVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<AppVersionVo> queryPageList(AppVersionBo bo);

	/**
	 * 查询列表
	 */
	List<AppVersionVo> queryList(AppVersionBo bo);

	/**
	 * 根据新增业务对象插入app版本管理
	 * @param bo app版本管理新增业务对象
	 * @return
	 */
	Boolean insertByBo(AppVersionBo bo);

	/**
	 * 根据编辑业务对象修改app版本管理
	 * @param bo app版本管理编辑业务对象
	 * @return
	 */
	Boolean updateByBo(AppVersionBo bo);

	/**
	 * 校验并删除数据
	 * @param ids 主键集合
	 * @param isValid 是否校验,true-删除前校验,false-不校验
	 * @return
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
