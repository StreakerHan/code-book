package com.rzfk.wallpaper.service;

import com.rzfk.wallpaper.domain.AppWallpapers;
import com.rzfk.wallpaper.domain.vo.AppWallpapersVo;
import com.rzfk.wallpaper.domain.bo.AppWallpapersBo;
import com.rzfk.common.core.page.IServicePlus;
import com.rzfk.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 壁纸Service接口
 *
 * @author streaker
 * @date 2022-11-03
 */
public interface IAppWallpapersService extends IServicePlus<AppWallpapers> {
	/**
	 * 查询单个
	 * @return
	 */
	AppWallpapersVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<AppWallpapersVo> queryPageList(AppWallpapersBo bo);

	/**
	 * 查询列表
	 */
	List<AppWallpapersVo> queryList(AppWallpapersBo bo);

	/**
	 * 根据新增业务对象插入壁纸
	 * @param bo 壁纸新增业务对象
	 * @return
	 */
	Boolean insertByBo(AppWallpapersBo bo);

	/**
	 * 根据编辑业务对象修改壁纸
	 * @param bo 壁纸编辑业务对象
	 * @return
	 */
	Boolean updateByBo(AppWallpapersBo bo);

	/**
	 * 校验并删除数据
	 * @param ids 主键集合
	 * @param isValid 是否校验,true-删除前校验,false-不校验
	 * @return
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
