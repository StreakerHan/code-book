package com.rzfk.app.service;

import com.rzfk.app.domain.AppBook;
import com.rzfk.app.domain.vo.AppBookVo;
import com.rzfk.app.domain.bo.AppBookBo;
import com.rzfk.common.core.page.IServicePlus;
import com.rzfk.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 书籍信息Service接口
 *
 * @author streaker
 * @date 2023-02-03
 */
public interface IAppBookService extends IServicePlus<AppBook> {
	/**
	 * 查询单个
	 * @return
	 */
	AppBookVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<AppBookVo> queryPageList(AppBookBo bo);

	/**
	 * 查询列表
	 */
	List<AppBookVo> queryList(AppBookBo bo);

	/**
	 * 根据新增业务对象插入书籍信息
	 * @param bo 书籍信息新增业务对象
	 * @return
	 */
	Boolean insertByBo(AppBookBo bo);

	/**
	 * 根据编辑业务对象修改书籍信息
	 * @param bo 书籍信息编辑业务对象
	 * @return
	 */
	Boolean updateByBo(AppBookBo bo);

	/**
	 * 校验并删除数据
	 * @param ids 主键集合
	 * @param isValid 是否校验,true-删除前校验,false-不校验
	 * @return
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
