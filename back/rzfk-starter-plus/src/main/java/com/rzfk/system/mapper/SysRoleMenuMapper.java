package com.rzfk.system.mapper;

import com.rzfk.common.core.page.BaseMapperPlus;
import com.rzfk.system.domain.SysRoleMenu;

import java.util.List;

/**
 * 角色与菜单关联表 数据层
 *
 * @author ruoyi
 */
public interface SysRoleMenuMapper extends BaseMapperPlus<SysRoleMenu> {

	/**
	 * 批量新增角色菜单信息
	 *
	 * @param roleMenuList 角色菜单列表
	 * @return 结果
	 */
	public int batchRoleMenu(List<SysRoleMenu> roleMenuList);

}
