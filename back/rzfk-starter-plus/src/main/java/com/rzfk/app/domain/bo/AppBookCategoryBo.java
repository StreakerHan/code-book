package com.rzfk.app.domain.bo;

import com.rzfk.common.core.domain.BaseEntity;
import com.rzfk.common.core.validate.AddGroup;
import com.rzfk.common.core.validate.EditGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.rzfk.common.core.domain.TreeEntity;

/**
 * 书籍分类业务对象 app_book_category
 *
 * @author streaker
 * @date 2023-02-03
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("书籍分类业务对象")
public class AppBookCategoryBo extends BaseEntity {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", required = true)
    @NotNull(message = "主键不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long id;

    /**
     * 分类名称
     */
    @ApiModelProperty(value = "分类名称", required = true)
    @NotBlank(message = "分类名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 父id
     */
    @ApiModelProperty(value = "父id", required = true)
    @NotNull(message = "父id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long parentId;

    /**
     * 封面图
     */
    @ApiModelProperty(value = "封面图", required = true)
    @NotBlank(message = "封面图不能为空", groups = { AddGroup.class, EditGroup.class })
    private String img;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述", required = true)
    @NotBlank(message = "描述不能为空", groups = { AddGroup.class, EditGroup.class })
    private String descb;

    /**
     * 链接
     */
    @ApiModelProperty(value = "链接", required = true)
    @NotBlank(message = "链接不能为空", groups = { AddGroup.class, EditGroup.class })
    private String href;

    /**
     * 等级
     */
    @ApiModelProperty(value = "等级", required = true)
    @NotBlank(message = "等级不能为空", groups = { AddGroup.class, EditGroup.class })
    private String level;


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
