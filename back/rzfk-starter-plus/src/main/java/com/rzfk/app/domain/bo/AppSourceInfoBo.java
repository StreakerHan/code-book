package com.rzfk.app.domain.bo;

import com.rzfk.common.core.validate.AddGroup;
import com.rzfk.common.core.validate.EditGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;
import java.util.List;

import com.rzfk.common.core.domain.BaseEntity;

/**
 * 资源信息业务对象 app_source_info
 *
 * @author streaker
 * @date 2022-08-19
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("资源信息业务对象")
public class AppSourceInfoBo extends BaseEntity {

    /**
     * $column.columnComment
     */
    @ApiModelProperty(value = "$column.columnComment", required = true)
    @NotNull(message = "$column.columnComment不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 资源名称
     */
    @ApiModelProperty(value = "资源名称", required = true)
    @NotBlank(message = "资源名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 类型id
     */
    @ApiModelProperty(value = "类型id", required = true)
    @NotNull(message = "类型id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long typeId;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序", required = true)
    @NotBlank(message = "排序不能为空", groups = { AddGroup.class, EditGroup.class })
    private String seq;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型", required = true)
    private String type;

    /**
     * 链接
     */
    @ApiModelProperty(value = "链接", required = true)
    private String url;

    /**
     * 百度云
     */
    @ApiModelProperty(value = "百度云", required = true)
    private String link;

    /**
     * 封面
     */
    private String img;

    /**
     * 状态
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 发布时间
     */
    private String uploadTime;

    /**
     * 内容
     */
    private String content;

    /**
     * 父分类id
     */
    private Long parentId;
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


    private List<Long> ids;

}
