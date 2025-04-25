package com.rzfk.app.service;

import com.rzfk.app.domain.AppUser;
import com.rzfk.app.domain.bo.AppUserUpdateBo;
import com.rzfk.app.domain.vo.AppUserVo;
import com.rzfk.app.domain.bo.AppUserBo;
import com.rzfk.app.domain.vo.LoginVO;
import com.rzfk.common.core.domain.R;
import com.rzfk.common.core.page.IServicePlus;
import com.rzfk.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * app用户Service接口
 *
 * @author streaker
 * @date 2022-08-08
 */
public interface IAppUserService extends IServicePlus<AppUser> {
	/**
	 * 查询单个
	 * @return
	 */
	AppUserVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<AppUserVo> queryPageList(AppUserBo bo);

	/**
	 * 查询列表
	 */
	List<AppUserVo> queryList(AppUserBo bo);

	/**
	 * 根据新增业务对象插入app用户
	 * @param bo app用户新增业务对象
	 * @return
	 */
	Boolean insertByBo(AppUserBo bo);

	/**
	 * 根据编辑业务对象修改app用户
	 * @param bo app用户编辑业务对象
	 * @return
	 */
	Boolean updateByBo(AppUserUpdateBo bo);

	/**
	 * 校验并删除数据
	 * @param ids 主键集合
	 * @param isValid 是否校验,true-删除前校验,false-不校验
	 * @return
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

	/**
	 * 账号密码登录
	 *
	 * @param login
	 * @return
	 */
	R loginByPwd(LoginVO login);

	/**
	 * 微信app登录
	 *
	 * @param params
	 * @return
	 */
	R weChatLogin(Map<String, Object> params);

	/**
	 * 用户注册
	 *
	 * @param params
	 * @return
	 */
	R register(Map<String, Object> params);

	/**
	 * 发送短信验证码
	 */
	R smsSend(Map<String,Object> params) throws Exception;

	/**
	 * 短信验证码登录
	 */
	R loginBySms(Map<String,Object> params);

	/**
	 * 绑定手机号码验证
	 */

}
