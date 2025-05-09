package com.rzfk.system.mapper;

import com.rzfk.common.core.page.BaseMapperPlus;
import com.rzfk.system.domain.SysUserRole;

import java.util.List;

/**
 * 用户与角色关联表 数据层
 *
 * @author ruoyi
 */
public interface SysUserRoleMapper extends BaseMapperPlus<SysUserRole> {

	/**
	 * 批量新增用户角色信息
	 *
	 * @param userRoleList 用户角色列表
	 * @return 结果
	 */
	public int batchUserRole(List<SysUserRole> userRoleList);

}
