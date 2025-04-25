package com.rzfk.app.service;

import com.rzfk.app.domain.AppCarousel;
import com.rzfk.app.domain.vo.AppCarouselVo;
import com.rzfk.app.domain.bo.AppCarouselBo;
import com.rzfk.common.core.page.IServicePlus;
import com.rzfk.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 轮播内容设置Service接口
 *
 * @author streaker
 * @date 2022-08-14
 */
public interface IAppCarouselService extends IServicePlus<AppCarousel> {
	/**
	 * 查询单个
	 * @return
	 */
	AppCarouselVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<AppCarouselVo> queryPageList(AppCarouselBo bo);

	/**
	 * 查询列表
	 */
	List<AppCarouselVo> queryList(AppCarouselBo bo);

	/**
	 * 根据新增业务对象插入轮播内容设置
	 * @param bo 轮播内容设置新增业务对象
	 * @return
	 */
	Boolean insertByBo(AppCarouselBo bo);

	/**
	 * 根据编辑业务对象修改轮播内容设置
	 * @param bo 轮播内容设置编辑业务对象
	 * @return
	 */
	Boolean updateByBo(AppCarouselBo bo);

	/**
	 * 校验并删除数据
	 * @param ids 主键集合
	 * @param isValid 是否校验,true-删除前校验,false-不校验
	 * @return
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
