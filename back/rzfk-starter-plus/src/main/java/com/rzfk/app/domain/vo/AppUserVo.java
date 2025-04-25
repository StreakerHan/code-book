package com.rzfk.app.domain.vo;

import com.rzfk.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * app用户视图对象 app_user
 *
 * @author streaker
 * @date 2022-08-08
 */
@Data
@ApiModel("app用户视图对象")
public class AppUserVo {

	private static final long serialVersionUID = 1L;

	/** 主键 */
	@ApiModelProperty("主键")
	private Long id;

    /**
     * 用户名称（昵称）
     */
	@Excel(name = "用户名称" , readConverterExp = "昵=称")
	@ApiModelProperty("用户名称（昵称）")
	private String nickname;

    /**
     * 密码
     */
	@Excel(name = "密码")
	@ApiModelProperty("密码")
	private String password;

    /**
     * 真实姓名
     */
	@Excel(name = "真实姓名")
	@ApiModelProperty("真实姓名")
	private String name;

    /**
     * 第三方登录用户id
     */
	@Excel(name = "第三方登录用户id")
	@ApiModelProperty("第三方登录用户id")
	private String socialUid;

    /**
     * 第三方登录令牌
     */
	@Excel(name = "第三方登录令牌")
	@ApiModelProperty("第三方登录令牌")
	private String socialToken;

    /**
     * 手机号码
     */
	@Excel(name = "手机号码")
	@ApiModelProperty("手机号码")
	private String mobile;

    /**
     * 邮箱地址
     */
	@Excel(name = "邮箱地址")
	@ApiModelProperty("邮箱地址")
	private String email;

    /**
     * 客户端唯一标识
     */
	@Excel(name = "客户端唯一标识")
	@ApiModelProperty("客户端唯一标识")
	private String clientId;

    /**
     * 推送的令牌
     */
	@Excel(name = "推送的令牌")
	@ApiModelProperty("推送的令牌")
	private String pushToken;

    /**
     * 性别
     */
	@Excel(name = "性别")
	@ApiModelProperty("性别")
	private String sex;

    /**
     * 用户注册来源（1系统2自然）
     */
	@Excel(name = "用户注册来源" , readConverterExp = "1=系统2自然")
	@ApiModelProperty("用户注册来源（1系统2自然）")
	private String source;

    /**
     * 第三方登录来源
     */
	@Excel(name = "第三方登录来源")
	@ApiModelProperty("第三方登录来源")
	private String socialSource;

    /**
     * 头像
     */
	@Excel(name = "头像")
	@ApiModelProperty("头像")
	private String avatar;

    /**
     * 省份
     */
	@Excel(name = "省份")
	@ApiModelProperty("省份")
	private String province;

    /**
     * 城市
     */
	@Excel(name = "城市")
	@ApiModelProperty("城市")
	private String city;

    /**
     * 区县
     */
	@Excel(name = "区县")
	@ApiModelProperty("区县")
	private String area;

    /**
     * 详细地址
     */
	@Excel(name = "详细地址")
	@ApiModelProperty("详细地址")
	private String address;

    /**
     * 最后登录时间
     */
	@Excel(name = "最后登录时间" , width = 30, dateFormat = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty("最后登录时间")
	private Date lastLoginTime;

    /**
     * 生日
     */
	@Excel(name = "生日")
	@ApiModelProperty("生日")
	private String birth;

    /**
     * 邀请码
     */
	@Excel(name = "邀请码")
	@ApiModelProperty("邀请码")
	private String inviteCode;

    /**
     * 今日是否签到（0否1是）
     */
	@Excel(name = "今日是否签到" , readConverterExp = "0=否1是")
	@ApiModelProperty("今日是否签到（0否1是）")
	private String isSign;

    /**
     * 用户状态（0禁用1正常）
     */
	@Excel(name = "用户状态" , readConverterExp = "0=禁用1正常")
	@ApiModelProperty("用户状态（0禁用1正常）")
	private String status;

    /**
     * 经验值
     */
	@Excel(name = "经验值")
	@ApiModelProperty("经验值")
	private String exp;

    /**
     * 身份证号码
     */
	@Excel(name = "身份证号码")
	@ApiModelProperty("身份证号码")
	private String idcard;

    /**
     * 我的签名
     */
	@Excel(name = "我的签名")
	@ApiModelProperty("我的签名")
	private String sign;

	/**
	 * 积分
	 */
	@Excel(name = "积分")
	@ApiModelProperty("积分")
	private String point;

	/**
	 * 创建时间
	 */
	@Excel(name = "创建时间")
	@ApiModelProperty("创建时间")
	private Date createTime;

	/**
	 * 车队
	 */
	@Excel(name = "车队")
	@ApiModelProperty("车队")
	private String team;
}
