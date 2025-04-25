package com.rzfk.system.mapper;

import com.rzfk.common.core.page.BaseMapperPlus;
import com.rzfk.system.domain.SysUserPost;

import java.util.List;

/**
 * 用户与岗位关联表 数据层
 *
 * @author ruoyi
 */
public interface SysUserPostMapper extends BaseMapperPlus<SysUserPost> {

	/**
	 * 批量新增用户岗位信息
	 *
	 * @param userPostList 用户角色列表
	 * @return 结果
	 */
	public int batchUserPost(List<SysUserPost> userPostList);

}
