package com.rzfk.app.service;

import com.rzfk.app.domain.AppBookCategory;
import com.rzfk.app.domain.vo.AppBookCategoryVo;
import com.rzfk.app.domain.bo.AppBookCategoryBo;
import com.rzfk.common.core.page.IServicePlus;

import java.util.Collection;
import java.util.List;

/**
 * 书籍分类Service接口
 *
 * @author streaker
 * @date 2023-02-03
 */
public interface IAppBookCategoryService extends IServicePlus<AppBookCategory> {
	/**
	 * 查询单个
	 * @return
	 */
	AppBookCategoryVo queryById(Long id);


	/**
	 * 查询列表
	 */
	List<AppBookCategoryVo> queryList(AppBookCategoryBo bo);

	/**
	 * 根据新增业务对象插入书籍分类
	 * @param bo 书籍分类新增业务对象
	 * @return
	 */
	Boolean insertByBo(AppBookCategoryBo bo);

	/**
	 * 根据编辑业务对象修改书籍分类
	 * @param bo 书籍分类编辑业务对象
	 * @return
	 */
	Boolean updateByBo(AppBookCategoryBo bo);

	/**
	 * 校验并删除数据
	 * @param ids 主键集合
	 * @param isValid 是否校验,true-删除前校验,false-不校验
	 * @return
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
