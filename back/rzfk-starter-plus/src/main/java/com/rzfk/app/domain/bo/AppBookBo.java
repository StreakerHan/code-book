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
 * 书籍信息业务对象 app_book
 *
 * @author streaker
 * @date 2023-02-03
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("书籍信息业务对象")
public class AppBookBo extends BaseEntity {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", required = true)
    @NotNull(message = "主键不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long id;

    /**
     * 书名
     */
    @ApiModelProperty(value = "书名", required = true)
    @NotBlank(message = "书名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 封面
     */
    @ApiModelProperty(value = "封面", required = true)
    @NotBlank(message = "封面不能为空", groups = { AddGroup.class, EditGroup.class })
    private String img;

    /**
     * 链接
     */
    @ApiModelProperty(value = "链接", required = true)
    @NotBlank(message = "链接不能为空", groups = { AddGroup.class, EditGroup.class })
    private String href;

    /**
     * 阅读链接
     */
    @ApiModelProperty(value = "阅读链接", required = true)
    @NotBlank(message = "阅读链接不能为空", groups = { AddGroup.class, EditGroup.class })
    private String readHref;

    /**
     * 标签
     */
    @ApiModelProperty(value = "标签", required = true)
    @NotBlank(message = "标签不能为空", groups = { AddGroup.class, EditGroup.class })
    private String tags;

    /**
     * 简介
     */
    @ApiModelProperty(value = "简介", required = true)
    @NotBlank(message = "简介不能为空", groups = { AddGroup.class, EditGroup.class })
    private String descb;

    /**
     * 分类
     */
    @ApiModelProperty(value = "分类", required = true)
    @NotBlank(message = "分类不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long type;

    private String chapters;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;


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
