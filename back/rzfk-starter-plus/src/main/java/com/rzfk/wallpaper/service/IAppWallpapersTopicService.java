package com.rzfk.wallpaper.service;

import com.rzfk.wallpaper.domain.AppWallpapersTopic;
import com.rzfk.wallpaper.domain.vo.AppWallpapersTopicVo;
import com.rzfk.wallpaper.domain.bo.AppWallpapersTopicBo;
import com.rzfk.common.core.page.IServicePlus;
import com.rzfk.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 壁纸主题Service接口
 *
 * @author streaker
 * @date 2022-11-03
 */
public interface IAppWallpapersTopicService extends IServicePlus<AppWallpapersTopic> {
	/**
	 * 查询单个
	 * @return
	 */
	AppWallpapersTopicVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<AppWallpapersTopicVo> queryPageList(AppWallpapersTopicBo bo);

	/**
	 * 查询列表
	 */
	List<AppWallpapersTopicVo> queryList(AppWallpapersTopicBo bo);

	/**
	 * 根据新增业务对象插入壁纸主题
	 * @param bo 壁纸主题新增业务对象
	 * @return
	 */
	Boolean insertByBo(AppWallpapersTopicBo bo);

	/**
	 * 根据编辑业务对象修改壁纸主题
	 * @param bo 壁纸主题编辑业务对象
	 * @return
	 */
	Boolean updateByBo(AppWallpapersTopicBo bo);

	/**
	 * 校验并删除数据
	 * @param ids 主键集合
	 * @param isValid 是否校验,true-删除前校验,false-不校验
	 * @return
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
