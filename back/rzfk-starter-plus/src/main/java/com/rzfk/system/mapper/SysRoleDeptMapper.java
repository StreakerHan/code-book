package com.rzfk.system.mapper;

import com.rzfk.common.core.page.BaseMapperPlus;
import com.rzfk.system.domain.SysRoleDept;

import java.util.List;

/**
 * 角色与部门关联表 数据层
 *
 * @author ruoyi
 */
public interface SysRoleDeptMapper extends BaseMapperPlus<SysRoleDept> {

	/**
	 * 批量新增角色部门信息
	 *
	 * @param roleDeptList 角色部门列表
	 * @return 结果
	 */
	public int batchRoleDept(List<SysRoleDept> roleDeptList);

}
