package com.rzfk.system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rzfk.common.core.domain.entity.SysUser;
import com.rzfk.common.core.page.BaseMapperPlus;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户表 数据层
 *
 * @author ruoyi
 */
public interface SysUserMapper extends BaseMapperPlus<SysUser> {

    Page<SysUser> selectPageUserList(@Param("page") Page<SysUser> page, @Param("user") SysUser user);

    /**
     * 根据条件分页查询用户列表
     *
     * @param sysUser 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectUserList(SysUser sysUser);

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    public SysUser selectUserByUserName(String userName);

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    public SysUser selectUserById(Long userId);

    public List<Map<String,Object>> flyHandStatistic();
}
