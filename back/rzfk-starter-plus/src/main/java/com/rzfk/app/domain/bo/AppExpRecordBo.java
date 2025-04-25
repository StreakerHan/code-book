package com.rzfk.app.domain.bo;

import com.rzfk.common.core.validate.AddGroup;
import com.rzfk.common.core.validate.EditGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.rzfk.common.core.domain.BaseEntity;

/**
 * 用户经验记录业务对象 app_exp_record
 *
 * @author streaker
 * @date 2022-08-13
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("用户经验记录业务对象")
public class AppExpRecordBo extends BaseEntity {

    /**
     * $column.columnComment
     */
    @ApiModelProperty(value = "$column.columnComment", required = true)
    @NotNull(message = "$column.columnComment不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 用户主键
     */
    @ApiModelProperty(value = "用户主键", required = true)
    @NotNull(message = "用户主键不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 操作前经验
     */
    @ApiModelProperty(value = "操作前经验", required = true)
    @NotBlank(message = "操作前经验不能为空", groups = { AddGroup.class, EditGroup.class })
    private String preExp;

    /**
     * 变化经验
     */
    @ApiModelProperty(value = "变化经验", required = true)
    @NotBlank(message = "变化经验不能为空", groups = { AddGroup.class, EditGroup.class })
    private String changeExp;

    /**
     * 操作后经验
     */
    @ApiModelProperty(value = "操作后经验", required = true)
    @NotBlank(message = "操作后经验不能为空", groups = { AddGroup.class, EditGroup.class })
    private String afterExp;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型", required = true)
    @NotBlank(message = "类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String type;


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
