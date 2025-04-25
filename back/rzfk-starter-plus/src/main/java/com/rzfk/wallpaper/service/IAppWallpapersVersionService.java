package com.rzfk.wallpaper.service;

import com.rzfk.wallpaper.domain.AppWallpapersVersion;
import com.rzfk.wallpaper.domain.bo.AppWallpapersVersionBo;
import com.rzfk.wallpaper.domain.vo.AppWallpapersVersionVo;
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
public interface IAppWallpapersVersionService extends IServicePlus<AppWallpapersVersion> {
	/**
	 * 查询单个
	 * @return
	 */
	AppWallpapersVersionVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<AppWallpapersVersionVo> queryPageList(AppWallpapersVersionBo bo);

	/**
	 * 查询列表
	 */
	List<AppWallpapersVersionVo> queryList(AppWallpapersVersionBo bo);

	/**
	 * 根据新增业务对象插入app版本管理
	 * @param bo app版本管理新增业务对象
	 * @return
	 */
	Boolean insertByBo(AppWallpapersVersionBo bo);

	/**
	 * 根据编辑业务对象修改app版本管理
	 * @param bo app版本管理编辑业务对象
	 * @return
	 */
	Boolean updateByBo(AppWallpapersVersionBo bo);

	/**
	 * 校验并删除数据
	 * @param ids 主键集合
	 * @param isValid 是否校验,true-删除前校验,false-不校验
	 * @return
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
