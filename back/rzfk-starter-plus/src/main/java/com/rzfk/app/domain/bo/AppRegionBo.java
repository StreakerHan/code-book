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
 * 全国区划业务对象 app_region
 *
 * @author streaker
 * @date 2022-08-10
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("全国区划业务对象")
public class AppRegionBo extends BaseEntity {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID", required = true)
    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 删除标志 true/false 删除/未删除
     */
    @ApiModelProperty(value = "删除标志 true/false 删除/未删除", required = true)
    @NotNull(message = "删除标志 true/false 删除/未删除不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer deleteFlag;

    /**
     * 区域编码
     */
    @ApiModelProperty(value = "区域编码", required = true)
    @NotBlank(message = "区域编码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String adCode;

    /**
     * 区域中心点经纬度
     */
    @ApiModelProperty(value = "区域中心点经纬度", required = true)
    @NotBlank(message = "区域中心点经纬度不能为空", groups = { AddGroup.class, EditGroup.class })
    private String center;

    /**
     * 城市代码
     */
    @ApiModelProperty(value = "城市代码", required = true)
    @NotBlank(message = "城市代码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String cityCode;

    /**
     * 行政区划级别
     */
    @ApiModelProperty(value = "行政区划级别", required = true)
    @NotBlank(message = "行政区划级别不能为空", groups = { AddGroup.class, EditGroup.class })
    private String level;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", required = true)
    @NotBlank(message = "名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序", required = true)
    @NotNull(message = "排序不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long orderNum;

    /**
     * 父ID
     */
    @ApiModelProperty(value = "父ID", required = true)
    @NotNull(message = "父ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long parentId;

    /**
     * 行政地区路径
     */
    @ApiModelProperty(value = "行政地区路径", required = true)
    @NotBlank(message = "行政地区路径不能为空", groups = { AddGroup.class, EditGroup.class })
    private String path;


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
