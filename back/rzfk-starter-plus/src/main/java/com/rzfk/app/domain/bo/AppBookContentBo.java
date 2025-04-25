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
 * 书籍内容业务对象 app_book_content
 *
 * @author streaker
 * @date 2023-02-03
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("书籍内容业务对象")
public class AppBookContentBo extends BaseEntity {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", required = true)
    @NotNull(message = "主键不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long id;

    /**
     * 书籍主键
     */
    @ApiModelProperty(value = "书籍主键", required = true)
    @NotNull(message = "书籍主键不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long bookId;

    /**
     * 目录名称
     */
    @ApiModelProperty(value = "目录名称", required = true)
    @NotBlank(message = "目录名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String catalogueName;

    /**
     * 目录顺序
     */
    @ApiModelProperty(value = "目录顺序", required = true)
    @NotBlank(message = "目录顺序不能为空", groups = { AddGroup.class, EditGroup.class })
    private String seq;

    /**
     * 内容链接
     */
    @ApiModelProperty(value = "内容链接", required = true)
    @NotBlank(message = "内容链接不能为空", groups = { AddGroup.class, EditGroup.class })
    private String href;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容", required = true)
    @NotBlank(message = "内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String content;

    /**
     * 父目录主键
     */
    @ApiModelProperty(value = "父目录主键", required = true)
    @NotNull(message = "父目录主键不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long parentId;

    /**
     * 级别
     */
    @ApiModelProperty(value = "级别", required = true)
    @NotBlank(message = "级别不能为空", groups = { AddGroup.class, EditGroup.class })
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
