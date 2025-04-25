package com.rzfk.app.domain.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
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
 * app文章业务对象 app_post
 *
 * @author streaker
 * @date 2022-08-14
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("app文章业务对象")
public class AppPostBo extends BaseEntity {

    /**
     * $column.columnComment
     */
    @ApiModelProperty(value = "$column.columnComment", required = true)
    @NotNull(message = "$column.columnComment不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题", required = true)
    @NotBlank(message = "标题不能为空", groups = { AddGroup.class, EditGroup.class })
    private String title;

    /**
     * 发布时间
     */
    @ApiModelProperty(value = "发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容", required = true)
    private String content;

    /**
     * 封面
     */
    @ApiModelProperty(value = "封面", required = true)
    @NotBlank(message = "封面不能为空", groups = { AddGroup.class, EditGroup.class })
    private String img;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型", required = true)
    @NotBlank(message = "类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String type;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态", required = true)
    @NotBlank(message = "状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private String status;

    /**
     * 视频
     */
    @ApiModelProperty(value = "视频", required = true)
    private String video;

    /**
     * 链接
     */
    @ApiModelProperty(value = "链接", required = true)
    private String link;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述", required = true)
    private String description;

    /**
     * 图片
     */
    @ApiModelProperty(value = "图片", required = true)
    private String image;

    private Long createBy;

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
