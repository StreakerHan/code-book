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

import com.rzfk.common.core.domain.BaseEntity;

/**
 * App资源互动记录业务对象 app_interact_record
 *
 * @author streaker
 * @date 2022-08-15
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("App资源互动记录业务对象")
public class AppInteractRecordBo extends BaseEntity {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", required = true)
    @NotNull(message = "主键不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 用户主键
     */
    @ApiModelProperty(value = "用户主键", required = true)
    @NotNull(message = "用户主键不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 资源类型
     */
    @ApiModelProperty(value = "资源类型", required = true)
    @NotBlank(message = "资源类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String type;

    /**
     * 资源id
     */
    @ApiModelProperty(value = "资源id", required = true)
    @NotNull(message = "资源id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long sourceId;

    /**
     * 互动类型（view，like，collect）
     */
    @ApiModelProperty(value = "互动类型（view，like，collect）", required = true)
    @NotBlank(message = "互动类型（view，like，collect）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String interactType;

    private String image;

    private String title;

    private String view;

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
     * 查询端
     */
    @ApiModelProperty("查询端")
    private String checkType;

    /**
     * 排序的方向desc或者asc
     */
    @ApiModelProperty(value = "排序的方向", example = "asc,desc")
    private String isAsc;

}
