package com.rzfk.app.domain.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.rzfk.common.core.validate.AddGroup;
import com.rzfk.common.core.validate.EditGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.rzfk.common.core.domain.BaseEntity;

/**
 * app用户业务对象 app_user
 *
 * @author streaker
 * @date 2022-08-08
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("app用户业务对象")
public class AppUserBo extends BaseEntity {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", required = true)
    @NotNull(message = "主键不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 用户名称（昵称）
     */
    @ApiModelProperty(value = "用户名称（昵称）", required = true)
    @NotBlank(message = "用户名称（昵称）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String nickname;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", required = true)
    @NotBlank(message = "密码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String password;

    /**
     * 真实姓名
     */
    @ApiModelProperty(value = "真实姓名", required = true)
    @NotBlank(message = "真实姓名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 第三方登录用户id
     */
    @ApiModelProperty(value = "第三方登录用户id", required = true)
    @NotBlank(message = "第三方登录用户id不能为空", groups = { AddGroup.class, EditGroup.class })
    private String socialUid;

    /**
     * 第三方登录令牌
     */
    @ApiModelProperty(value = "第三方登录令牌", required = true)
    @NotBlank(message = "第三方登录令牌不能为空", groups = { AddGroup.class, EditGroup.class })
    private String socialToken;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码", required = true)
    @NotBlank(message = "手机号码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String mobile;

    /**
     * 邮箱地址
     */
    @ApiModelProperty(value = "邮箱地址", required = true)
    @NotBlank(message = "邮箱地址不能为空", groups = { AddGroup.class, EditGroup.class })
    private String email;

    /**
     * 客户端唯一标识
     */
    @ApiModelProperty(value = "客户端唯一标识", required = true)
    @NotBlank(message = "客户端唯一标识不能为空", groups = { AddGroup.class, EditGroup.class })
    private String clientId;

    /**
     * 推送的令牌
     */
    @ApiModelProperty(value = "推送的令牌", required = true)
    @NotBlank(message = "推送的令牌不能为空", groups = { AddGroup.class, EditGroup.class })
    private String pushToken;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别", required = true)
    @NotBlank(message = "性别不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sex;

    /**
     * 用户注册来源（1系统2自然）
     */
    @ApiModelProperty(value = "用户注册来源（1系统2自然）", required = true)
    @NotBlank(message = "用户注册来源（1系统2自然）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String source;

    /**
     * 第三方登录来源
     */
    @ApiModelProperty(value = "第三方登录来源", required = true)
    @NotBlank(message = "第三方登录来源不能为空", groups = { AddGroup.class, EditGroup.class })
    private String socialSource;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像", required = true)
    @NotBlank(message = "头像不能为空", groups = { AddGroup.class, EditGroup.class })
    private String avatar;

    /**
     * 省份
     */
    @ApiModelProperty(value = "省份", required = true)
    @NotBlank(message = "省份不能为空", groups = { AddGroup.class, EditGroup.class })
    private String province;

    /**
     * 城市
     */
    @ApiModelProperty(value = "城市", required = true)
    @NotBlank(message = "城市不能为空", groups = { AddGroup.class, EditGroup.class })
    private String city;

    /**
     * 区县
     */
    @ApiModelProperty(value = "区县", required = true)
    @NotBlank(message = "区县不能为空", groups = { AddGroup.class, EditGroup.class })
    private String area;

    /**
     * 详细地址
     */
    @ApiModelProperty(value = "详细地址", required = true)
    @NotBlank(message = "详细地址不能为空", groups = { AddGroup.class, EditGroup.class })
    private String address;

    /**
     * 最后登录时间
     */
    @ApiModelProperty(value = "最后登录时间", required = true)
    @NotNull(message = "最后登录时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date lastLoginTime;

    /**
     * 生日
     */
    @ApiModelProperty(value = "生日", required = true)
    @NotBlank(message = "生日不能为空", groups = { AddGroup.class, EditGroup.class })
    private String birth;

    /**
     * 邀请码
     */
    @ApiModelProperty(value = "邀请码", required = true)
    @NotBlank(message = "邀请码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String inviteCode;

    /**
     * 今日是否签到（0否1是）
     */
    @ApiModelProperty(value = "今日是否签到（0否1是）", required = true)
    @NotBlank(message = "今日是否签到（0否1是）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String isSign;

    /**
     * 用户状态（0禁用1正常）
     */
    @ApiModelProperty(value = "用户状态（0禁用1正常）", required = true)
    @NotBlank(message = "用户状态（0禁用1正常）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String status;

    /**
     * 经验值
     */
    @ApiModelProperty(value = "经验值", required = true)
    @NotBlank(message = "经验值不能为空", groups = { AddGroup.class, EditGroup.class })
    private String exp;

    /**
     * 身份证号码
     */
    @ApiModelProperty(value = "身份证号码", required = true)
    @NotBlank(message = "身份证号码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String idcard;

    /**
     * 我的签名
     */
    @ApiModelProperty(value = "我的签名", required = true)
    @NotBlank(message = "我的签名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sign;

    /**
     * 地区
     */
    @ApiModelProperty(value = "地区", required = true)
    private String path;

    private String point;

    private String team;


    /**
     * 分页大小
     */
    @ApiModelProperty("分页大小")
    private Integer pageSize;

    /**
     * 当前页数
     */
    @ApiModelProperty("当前页数")
    private Integer pageNum;

    /**
     * 排序列
     */
    @ApiModelProperty("排序列")
    private String orderByColumn;

    /**
     * 排序的方向desc或者asc
     */
    @ApiModelProperty(value = "排序的方向", example = "asc,desc")
    private String isAsc;

}
