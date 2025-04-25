package com.rzfk.app.domain.bo;

import com.rzfk.common.annotation.Excel;
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
 * 积分记录业务对象 app_points_record
 *
 * @author streaker
 * @date 2022-08-13
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("积分记录业务对象")
public class AppPointsRecordBo extends BaseEntity {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID", required = true)
    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 消费之前积分
     */
    @ApiModelProperty(value = "消费之前积分", required = true)
    @NotNull(message = "消费之前积分不能为空", groups = { AddGroup.class, EditGroup.class })
    private String beforePoint;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容", required = true)
    @NotBlank(message = "内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String content;

    /**
     * 会员ID
     */
    @ApiModelProperty(value = "会员ID", required = true)
    @NotNull(message = "会员ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long memberId;

    /**
     * 会员名称
     */
    @ApiModelProperty(value = "会员名称", required = true)
    @NotBlank(message = "会员名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String memberName;

    /**
     * 当前积分
     */
    @ApiModelProperty(value = "当前积分", required = true)
    @NotNull(message = "当前积分不能为空", groups = { AddGroup.class, EditGroup.class })
    private String point;

    /**
     * 消费积分类型
     */
    @ApiModelProperty(value = "消费积分类型", required = true)
    @NotBlank(message = "消费积分类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String pointType;

    /**
     * 消费积分
     */
    @ApiModelProperty(value = "消费积分", required = true)
    @NotNull(message = "消费积分不能为空", groups = { AddGroup.class, EditGroup.class })
    private String variablePoint;

    @ApiModelProperty(value = "变化类型", required = true)
    @NotNull(message = "变化类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String changeType;

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
