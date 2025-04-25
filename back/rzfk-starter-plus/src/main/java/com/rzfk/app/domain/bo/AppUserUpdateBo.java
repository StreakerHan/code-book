package com.rzfk.app.domain.bo;

import com.rzfk.common.core.domain.BaseEntity;
import com.rzfk.common.core.validate.AddGroup;
import com.rzfk.common.core.validate.EditGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * app用户业务对象 app_user
 *
 * @author streaker
 * @date 2022-08-08
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("app用户业务对象")
public class AppUserUpdateBo extends BaseEntity {

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
     * 性别
     */
    @ApiModelProperty(value = "性别", required = true)
    private String sex;

    /**
     * 生日
     */
    @ApiModelProperty(value = "生日", required = true)
    private String birth;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像", required = true)
    private String avatar;

    /**
     * 地区
     */
    @ApiModelProperty(value = "地区", required = true)
    private String path;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱", required = true)
    private String email;/**

     * 车队
     */
    @ApiModelProperty(value = "车队", required = true)
    private String team;

}
