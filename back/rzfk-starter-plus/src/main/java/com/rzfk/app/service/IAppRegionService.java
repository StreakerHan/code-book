package com.rzfk.app.service;

import com.rzfk.app.domain.AppRegion;
import com.rzfk.app.domain.vo.AppRegionVo;
import com.rzfk.app.domain.bo.AppRegionBo;
import com.rzfk.common.core.page.IServicePlus;
import com.rzfk.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 全国区划Service接口
 *
 * @author streaker
 * @date 2022-08-10
 */
public interface IAppRegionService extends IServicePlus<AppRegion> {
	/**
	 * 查询单个
	 * @return
	 */
	AppRegionVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<AppRegionVo> queryPageList(AppRegionBo bo);

	/**
	 * 查询列表
	 */
	List<AppRegionVo> queryList(AppRegionBo bo);

	/**
	 * 根据新增业务对象插入全国区划
	 * @param bo 全国区划新增业务对象
	 * @return
	 */
	Boolean insertByBo(AppRegionBo bo);

	/**
	 * 根据编辑业务对象修改全国区划
	 * @param bo 全国区划编辑业务对象
	 * @return
	 */
	Boolean updateByBo(AppRegionBo bo);

	/**
	 * 校验并删除数据
	 * @param ids 主键集合
	 * @param isValid 是否校验,true-删除前校验,false-不校验
	 * @return
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
