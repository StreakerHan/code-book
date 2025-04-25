package com.rzfk.app.domain.bo;

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
 * app用户登录记录业务对象 app_login_record
 *
 * @author streaker
 * @date 2022-08-10
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("app用户登录记录业务对象")
public class AppLoginRecordBo extends BaseEntity {

    /**
     * $column.columnComment
     */
    @ApiModelProperty(value = "$column.columnComment", required = true)
    @NotNull(message = "$column.columnComment不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id", required = true)
    @NotNull(message = "用户id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 登录时间
     */
    @ApiModelProperty(value = "登录时间", required = true)
    @NotNull(message = "登录时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date loginTime;

    /**
     * 登录ip
     */
    @ApiModelProperty(value = "登录ip", required = true)
    @NotBlank(message = "登录ip不能为空", groups = { AddGroup.class, EditGroup.class })
    private String loginIp;


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
