package com.rzfk.app.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.rzfk.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.rzfk.common.core.domain.BaseEntity;

/**
 * app用户对象 app_user
 *
 * @author streaker
 * @date 2022-08-08
 */
@Data
@Accessors(chain = true)
@TableName("app_user")
public class AppUser extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 用户名称（昵称）
     */
    private String nickname;
    /**
     * 密码
     */
    private String password;
    /**
     * 真实姓名
     */
    private String name;
    /**
     * 第三方登录用户id
     */
    private String socialUid;
    /**
     * 第三方登录令牌
     */
    private String socialToken;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 邮箱地址
     */
    private String email;
    /**
     * 客户端唯一标识
     */
    private String clientId;
    /**
     * 推送的令牌
     */
    private String pushToken;
    /**
     * 性别
     */
    private String sex;
    /**
     * 用户注册来源（1系统2自然）
     */
    private String source;
    /**
     * 第三方登录来源
     */
    private String socialSource;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 省份
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 区县
     */
    private String area;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 最后登录时间
     */
    private Date lastLoginTime;
    /**
     * 生日
     */
    private String birth;
    /**
     * 邀请码
     */
    private String inviteCode;
    /**
     * 今日是否签到（0否1是）
     */
    private String isSign;
    /**
     * 用户状态（0禁用1正常）
     */
    private String status;
    /**
     * 经验值
     */
    private String exp;
    /**
     * 身份证号码
     */
    private String idcard;
    /**
     * 我的签名
     */
    private String sign;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 积分
     */
    private String point;
    /**
     * 车队
     */
    private String team;
}
