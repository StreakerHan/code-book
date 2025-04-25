package com.rzfk.app.service;

import com.rzfk.app.domain.AppSourceInfo;
import com.rzfk.app.domain.vo.AppSourceInfoVo;
import com.rzfk.app.domain.bo.AppSourceInfoBo;
import com.rzfk.common.core.page.IServicePlus;
import com.rzfk.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 资源信息Service接口
 *
 * @author streaker
 * @date 2022-08-19
 */
public interface IAppSourceInfoService extends IServicePlus<AppSourceInfo> {
	/**
	 * 查询单个
	 * @return
	 */
	AppSourceInfoVo queryById(Long id);

	AppSourceInfoVo getInfoAppWithToken(Long id);

	AppSourceInfoVo queryByIdApp(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<AppSourceInfoVo> queryPageList(AppSourceInfoBo bo);

	/**
	 * 首页随机
	 * @param bo
	 * @return
	 */
	TableDataInfo<AppSourceInfoVo> queryPageListRandom(AppSourceInfoBo bo);

	/**
	 * 查询列表
	 */
	List<AppSourceInfoVo> queryList(AppSourceInfoBo bo);

	/**
	 * 根据新增业务对象插入资源信息
	 * @param bo 资源信息新增业务对象
	 * @return
	 */
	Boolean insertByBo(AppSourceInfoBo bo);

	/**
	 * 根据编辑业务对象修改资源信息
	 * @param bo 资源信息编辑业务对象
	 * @return
	 */
	Boolean updateByBo(AppSourceInfoBo bo);

	/**
	 * 校验并删除数据
	 * @param ids 主键集合
	 * @param isValid 是否校验,true-删除前校验,false-不校验
	 * @return
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
