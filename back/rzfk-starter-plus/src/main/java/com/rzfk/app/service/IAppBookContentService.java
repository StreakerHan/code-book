package com.rzfk.app.service;

import com.rzfk.app.domain.AppBookContent;
import com.rzfk.app.domain.vo.AppBookContentVo;
import com.rzfk.app.domain.bo.AppBookContentBo;
import com.rzfk.common.core.page.IServicePlus;
import com.rzfk.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 书籍内容Service接口
 *
 * @author streaker
 * @date 2023-02-03
 */
public interface IAppBookContentService extends IServicePlus<AppBookContent> {
	/**
	 * 查询单个
	 * @return
	 */
	AppBookContentVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<AppBookContentVo> queryPageList(AppBookContentBo bo);

	/**
	 * 查询列表
	 */
	List<AppBookContentVo> queryList(AppBookContentBo bo);

	/**
	 * 根据新增业务对象插入书籍内容
	 * @param bo 书籍内容新增业务对象
	 * @return
	 */
	Boolean insertByBo(AppBookContentBo bo);

	/**
	 * 根据编辑业务对象修改书籍内容
	 * @param bo 书籍内容编辑业务对象
	 * @return
	 */
	Boolean updateByBo(AppBookContentBo bo);

	/**
	 * 校验并删除数据
	 * @param ids 主键集合
	 * @param isValid 是否校验,true-删除前校验,false-不校验
	 * @return
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
