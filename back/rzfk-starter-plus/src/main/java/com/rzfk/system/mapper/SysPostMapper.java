package com.rzfk.system.mapper;

import com.rzfk.common.core.page.BaseMapperPlus;
import com.rzfk.system.domain.SysPost;

import java.util.List;

/**
 * 岗位信息 数据层
 *
 * @author ruoyi
 */
public interface SysPostMapper extends BaseMapperPlus<SysPost> {

    /**
     * 根据用户ID获取岗位选择框列表
     *
     * @param userId 用户ID
     * @return 选中岗位ID列表
     */
    public List<Long> selectPostListByUserId(Long userId);

    /**
     * 查询用户所属岗位组
     *
     * @param userName 用户名
     * @return 结果
     */
    public List<SysPost> selectPostsByUserName(String userName);

}
